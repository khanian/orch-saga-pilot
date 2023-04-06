package com.khany.orchsagapilot.application;

import com.khany.orchsagapilot.application.port.in.SagaMachineUseCase;
import com.khany.orchsagapilot.config.SagaEvents;
import com.khany.orchsagapilot.config.SagaStates;
import com.khany.orchsagapilot.domain.Saga;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Slf4j
@Service
@AllArgsConstructor
public class SagaMachineService implements SagaMachineUseCase {

    private final StateMachine<SagaStates, SagaEvents> stateMachine;

    @Override
    public Saga nextStepSaga(Saga saga) {
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
        System.out.println("inside service :::::::: !!!!!!!!");
        return Saga.builder()
                .eventTimestamp(new Timestamp(System.currentTimeMillis()))
                .customerId(1L)
                .orderId(1L)
                .currentState(getNextStep(stateMachine))
                .value("aaa test")
                .build();
    }

    public static String getNextStep(StateMachine<SagaStates, SagaEvents> stateMachine) {
        Transition<SagaStates, SagaEvents> transition = stateMachine.getTransitions().iterator().next();
        System.out.println("transition now status :: " + transition.getTarget().getId().toString());
        return transition.getTarget().getId().toString();
    }

}
