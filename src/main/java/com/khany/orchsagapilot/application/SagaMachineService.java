package com.khany.orchsagapilot.application;

import com.khany.orchsagapilot.application.port.in.SagaMachineUseCase;
import com.khany.orchsagapilot.config.SagaEvents;
import com.khany.orchsagapilot.config.SagaStates;
import com.khany.orchsagapilot.domain.Saga;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import static com.khany.orchsagapilot.config.SagaStates.*;

@Slf4j
@Service
@AllArgsConstructor
public class SagaMachineService implements SagaMachineUseCase {

    private final StateMachine<SagaStates, SagaEvents> stateMachine;
    private Set<Transition> transitions;
    @PostConstruct
    private void init() {
        stateMachine.start();
        log.info("saga machine created");
        System.out.println("saga machine created");
    }

    @Override
    public Saga nextStepSaga(Saga saga) {
        handleEvent(saga.currentState(), stateMachine);
        return saga.builder()
                .eventTimestamp(new Timestamp(System.currentTimeMillis()))
                .customerId(saga.customerId())
                .orderId(saga.orderId())
                .currentState(getNextStep(stateMachine))
                .value(saga.value())
                .build();
    }

    @Override
    public Saga getDiscount() {
        stateMachine.sendEvent(SagaEvents.DISCOUNT_QUERY);
        log.info("inside service :::::::: !!!!!!!!");
        String nextStep = getNextStep(stateMachine);
        System.out.println("next step = " + nextStep);
        return Saga.builder()
                .eventTimestamp(new Timestamp(System.currentTimeMillis()))
                .customerId(1L)
                .orderId(1L)
                .currentState(nextStep)
                .value("aaa test")
                .build();
    }

    @Override
    public Saga getPayment() {
        stateMachine.sendEvent(SagaEvents.DISCOUNT_REQUEST);
        stateMachine.sendEvent(SagaEvents.PAYMENT_REQUEST);
        log.info("inside payment request service :::::::: !!!!!!!!");
        State<SagaStates, SagaEvents> currentState = stateMachine.getState();
        log.info ("[ current state == {}]", currentState.getId().toString());
        String getIds = currentState.getId().name();;
        String nextStep = stateMachine.getState().getId().toString();
        String nextStep1 = getNextStep(stateMachine);

        log.info ("######## now you step = {}, next = {}, current get id name === {}", nextStep, nextStep1, getIds);
        return Saga.builder()
                .eventTimestamp(new Timestamp(System.currentTimeMillis()))
                .customerId(1L)
                .orderId(1L)
                .currentState(nextStep)
                .value("aaa test")
                .build();
    }

    private static void handleEvent(String sagaState, StateMachine<SagaStates, SagaEvents> handleStateMachine) {
        log.info ("case sagaState ::: {}", sagaState);
        switch (sagaState) {
            case "ORDER_REQUEST" -> handleStateMachine.sendEvent(SagaEvents.DISCOUNT_QUERY);
            case "DISCOUNT_CHECK_OK" -> handleStateMachine.sendEvent(SagaEvents.POINT_QUERY);
            case "POINT_CHECK_OK" -> handleStateMachine.sendEvent(SagaEvents.DISCOUNT_REQUEST);
            case "DISCOUNT_REQUEST_OK" -> {
                System.out.println("여기 들어왔어......!!!");
                handleStateMachine.sendEvent(SagaEvents.PAYMENT_REQUEST);
            }
            case "PAYMENT_REQUEST_OK" -> handleStateMachine.sendEvent(SagaEvents.ORDER_COMPLETE);
            case "DISCOUNT_CHECK_FAIL", "POINT_CHECK_FAIL", "DISCOUNT_REQUEST_FAIL", "DISCOUNT_CANCEL_REQUEST"
                    -> handleStateMachine.sendEvent(SagaEvents.ORDER_CANCEL);
            case "PAYMENT_REQUEST_FAIL", "PAYMENT_CANCEL_REQUEST"
                    -> handleStateMachine.sendEvent(SagaEvents.DISCOUNT_CANCEL);
            case "ORDER_CANCEL_REQUEST" -> handleStateMachine.sendEvent(SagaEvents.PAYMENT_CANCEL);
            default -> throw new IllegalStateException("Unexpected value: " + sagaState);
        }
    }

    public static String getNextStep(StateMachine<SagaStates, SagaEvents> stateMachine) {
        Transition<SagaStates, SagaEvents> transition = stateMachine.getTransitions().iterator().next();
        log.info("transition now status :: " + transition.getTarget().getId().toString());
        return transition.getTarget().getId().toString();
    }

}
