package com.khany.orchsagapilot.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;

@Configuration
@EnableStateMachine
@RequiredArgsConstructor
class OrderStateMachineConfiguration extends EnumStateMachineConfigurerAdapter<SagaStates, SagaEvents> {

    public void configure(StateMachineStateConfigurer<SagaStates, SagaEvents> States) throws Exception {
        States
                .withStates()
                .initial(SagaStates.ORDER_REQUEST)
                .state(SagaStates.DISCOUNT_QUERYING)
                .state(SagaStates.DISCOUNT_QUERY_OK)
                .state(SagaStates.DISCOUNT_QUERY_FAIL)
                .state(SagaStates.DISCOUNT_CANCELING)
                .state(SagaStates.DISCOUNT_REQUEST_OK)
                .state(SagaStates.DISCOUNT_REQUEST_FAIL)
                .state(SagaStates.PAYMENT_REQUEST_OK)
                .state(SagaStates.PAYMENT_REQUEST_FAIL)
                .end(SagaStates.ORDER_CANCELING)
                .end(SagaStates.ORDER_COMPLETED);
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
                .source(SagaStates.PAYMENT_REQUEST_OK).target(SagaStates.ORDER_COMPLETED).event(SagaEvents.ORDER_COMPLETE)
                .and()
                .withExternal()
                .source(SagaStates.PAYMENT_REQUEST_FAIL).target(SagaStates.DISCOUNT_CANCELING).event(SagaEvents.DISCOUNT_CANCEL)
                .and()
                .withExternal()
                .source(SagaStates.DISCOUNT_CANCELING).target(SagaStates.ORDER_CANCELING).event(SagaEvents.ORDER_CANCEL)
                .and()
                .withExternal()
                .source(SagaStates.ORDER_CANCELING).target(SagaStates.PAYMENT_CANCELING).event(SagaEvents.PAYMENT_CANCEL)
                .and()
                .withExternal()
                .source(SagaStates.PAYMENT_CANCELING).target(SagaStates.DISCOUNT_CANCELING).event(SagaEvents.PAYMENT_CANCEL)
        ;

    }

//    @Bean
//    public StateMachineListener<SagaStates, SagaEvents> listener() {
//        return new StateMachineListenerAdapter<SagaStates, SagaEvents>() {
//
//            public void stateChanged(States<SagaStates, SagaEvents> from, States<States, SagaEvents> to) {
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
//    @Bean
//    public StateMachineListener<SagaStates, SagaEvents> listener() {
//        return new StateMachineListenerAdapter<SagaStates, SagaEvents>() {
//            @Override
//            public void transition(Transition<SagaStates, SagaEvents> transition) {
//                System.out.println("Transition: " + transition.getSource().getId() + " -> " + transition.getTarget().getId());
//            }
//        };
//    }
//
//    @Bean
//    public Action<SagaStates, SagaEvents> actionA() {
//        return new Action<SagaStates, SagaEvents>() {
//            @Override
//            public void execute(StateContext<SagaStates, SagaEvents> context) {
//                System.out.println("Action A");
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
