package com.khany.orchsagapilot.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

@Slf4j
@Configuration
@EnableStateMachine
@RequiredArgsConstructor
class SagaStateMachineConfiguration extends EnumStateMachineConfigurerAdapter<SagaStates, SagaEvents> {

    public void configure(StateMachineStateConfigurer<SagaStates, SagaEvents> states) throws Exception {
        states
                .withStates()
                .initial(SagaStates.ORDER_REQUEST)
                .state(SagaStates.DISCOUNT_QUERYING)
                .state(SagaStates.DISCOUNT_QUERY_OK)
                .state(SagaStates.DISCOUNT_QUERY_FAIL)
                .state(SagaStates.POINT_QUERYING)
                .state(SagaStates.POINT_QUERY_OK)
                .state(SagaStates.POINT_QUERY_FAIL)
                .state(SagaStates.DISCOUNT_REQUESTING)
                .state(SagaStates.DISCOUNT_REQUEST_OK)
                .state(SagaStates.DISCOUNT_REQUEST_FAIL)
                .state(SagaStates.PAYMENT_REQUESTING)
                .state(SagaStates.PAYMENT_REQUEST_OK)
                .state(SagaStates.PAYMENT_REQUEST_FAIL)
                .state(SagaStates.PAYMENT_CANCELING)
                .state(SagaStates.DISCOUNT_CANCELING)
                .end(SagaStates.ORDER_CANCELING)
                .end(SagaStates.ORDER_COMPLETING);
    }

    public void configure(StateMachineTransitionConfigurer<SagaStates, SagaEvents> transitions) throws Exception {
        transitions
                .withExternal()
                .source(SagaStates.ORDER_REQUEST).target(SagaStates.DISCOUNT_QUERYING).event(SagaEvents.DISCOUNT_QUERY)
                .and()
                .withExternal()
                .source(SagaStates.DISCOUNT_QUERY_OK).target(SagaStates.POINT_QUERYING).event(SagaEvents.POINT_QUERY)
                .and()
                .withExternal()
                .source(SagaStates.DISCOUNT_QUERY_FAIL).target(SagaStates.ORDER_CANCELING).event(SagaEvents.ORDER_CANCEL)
                .and()
                .withExternal()
                .source(SagaStates.POINT_QUERY_OK).target(SagaStates.DISCOUNT_REQUESTING).event(SagaEvents.DISCOUNT_REQUEST)
                .and()
                .withExternal()
                .source(SagaStates.POINT_QUERY_FAIL).target(SagaStates.ORDER_CANCELING).event(SagaEvents.ORDER_CANCEL)
                .and()
                .withExternal()
                .source(SagaStates.DISCOUNT_REQUEST_OK).target(SagaStates.PAYMENT_REQUESTING).event(SagaEvents.PAYMENT_REQUEST)
                .and()
                .withExternal()
                .source(SagaStates.DISCOUNT_REQUEST_FAIL).target(SagaStates.ORDER_CANCELING).event(SagaEvents.ORDER_CANCEL)
                .and()
                .withExternal()
                .source(SagaStates.PAYMENT_REQUEST_OK).target(SagaStates.ORDER_COMPLETING).event(SagaEvents.ORDER_COMPLETE)
                .and()
                .withExternal()
                .source(SagaStates.PAYMENT_REQUEST_FAIL).target(SagaStates.DISCOUNT_CANCELING).event(SagaEvents.DISCOUNT_CANCEL)
                .and()
                .withExternal()
                .source(SagaStates.ORDER_CANCEL_REQUESTING).target(SagaStates.PAYMENT_CANCELING).event(SagaEvents.PAYMENT_CANCEL)
                .and()
                .withExternal()
                .source(SagaStates.PAYMENT_CANCELING).target(SagaStates.DISCOUNT_CANCELING).event(SagaEvents.DISCOUNT_CANCEL)
                .and()
                .withExternal()
                .source(SagaStates.DISCOUNT_CANCELING).target(SagaStates.ORDER_CANCELING).event(SagaEvents.ORDER_CANCEL)
        ;
    }

//    @Bean
//    public StateMachineListener<SagaStates, SagaEvents> listener() {
//        return new StateMachineListenerAdapter<SagaStates, SagaEvents>() {
//            public void stateChanged(states<SagaStates, SagaEvents> from, states<States, SagaEvents> to) {
//                System.out.println("State changed to " + to.getId());
//            }
//        };
//    }
//
//    public void configure(StateMachineConfigurationConfigurer<SagaSagaStates, SagaEvents> config) throws Exception {
//        config.withPersistence()
//                .runtimePersister(stateMachinePersist);
//    }
//
    @Bean
    public StateMachineListener<SagaStates, SagaEvents> listener() {
        return new StateMachineListenerAdapter<SagaStates, SagaEvents>() {
            @Override
            public void transition(Transition<SagaStates, SagaEvents> transition) {
                log.debug("Transition log : source: {} -> target : {}", transition.getSource().getStates()
                        ,transition.getTarget().getStates());
                System.out.println("Transition: " + transition.getSource().getId().toString() + " -> " + transition.getTarget().getId());
            }

            @Override
            public void stateChanged(State<SagaStates, SagaEvents> fromState, State<SagaStates, SagaEvents> toState) {
                // 리스너의 동작을 구현
                log.info("State changed from {} to {}. Current status {}",
                        fromState == null ? "start": fromState.getId(),
                        toState.getId(), toState.getStates());
            }
        };
    }

//    @Bean
//    public Action<SagaStates, SagaEvents> actionDiscountQuery() {
//        return new Action<SagaStates, SagaEvents>() {
//            @Override
//            public void execute(StateContext<SagaStates, SagaEvents> context) {
//                System.out.println("Action actionDiscountQuery");
//            }
//        };
//    }
//
//    @Bean
//    public Action<SagaStates, SagaEvents> actionB() {
//        return new Action<SagaStates, SagaEvents>() {
//            @Override
//            public void execute(StateContext<SagaStates, SagaEvents> context) {
//                System.out.println("Action B");
//            }
//        };
//    }
//
//    @Bean
//    public Action<SagaStates, SagaEvents> errorAction() {
//        return new Action<SagaStates, SagaEvents>() {
//            @Override
//            public void execute(StateContext<SagaStates, SagaEvents> context) {
//                System.out.println("Error Action");
//            }
//        };
//    }
}
