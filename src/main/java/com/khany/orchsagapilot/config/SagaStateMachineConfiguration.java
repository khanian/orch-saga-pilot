package com.khany.orchsagapilot.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

@Slf4j
@Configuration
@EnableStateMachine
@RequiredArgsConstructor
class SagaStateMachineConfiguration extends EnumStateMachineConfigurerAdapter<SagaStates, SagaEvents> {

    @Override
    public void configure(StateMachineStateConfigurer<SagaStates, SagaEvents> states) throws Exception {
        states
                .withStates()
                .initial(SagaStates.ORDER_REQUEST)
                .state(SagaStates.DISCOUNT_CHECKED)
                .state(SagaStates.DISCOUNT_CHECK_OK)
                .state(SagaStates.DISCOUNT_CHECK_FAIL)
                .state(SagaStates.POINT_CHECKED)
                .state(SagaStates.POINT_CHECK_OK)
                .state(SagaStates.POINT_CHECK_FAIL)
                .state(SagaStates.DISCOUNT_REQUESTED)
                .state(SagaStates.DISCOUNT_REQUEST_OK)
                .state(SagaStates.DISCOUNT_REQUEST_FAIL)
                .state(SagaStates.PAYMENT_REQUESTED)
                .state(SagaStates.PAYMENT_REQUEST_OK)
                .state(SagaStates.PAYMENT_REQUEST_FAIL)
                .state(SagaStates.PAYMENT_CANCEL_REQUEST)
                .state(SagaStates.DISCOUNT_CANCEL_REQUEST)
                .state(SagaStates.ORDER_CANCEL_REQUEST)
                .end(SagaStates.ORDER_CANCELED)
                .end(SagaStates.ORDER_COMPLETED);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<SagaStates, SagaEvents> transitions) throws Exception {
        transitions
                .withExternal()
                .source(SagaStates.ORDER_REQUEST).target(SagaStates.DISCOUNT_CHECKED).event(SagaEvents.DISCOUNT_QUERY)
                .action(actionDiscountQuery())
                .and()
                .withExternal()
                .source(SagaStates.DISCOUNT_CHECK_OK).target(SagaStates.POINT_CHECKED).event(SagaEvents.POINT_QUERY)
                .and()
                .withExternal()
                .source(SagaStates.DISCOUNT_CHECK_FAIL).target(SagaStates.ORDER_CANCELED).event(SagaEvents.ORDER_CANCEL)
                .and()
                .withExternal()
                .source(SagaStates.POINT_CHECK_OK).target(SagaStates.DISCOUNT_REQUESTED).event(SagaEvents.DISCOUNT_REQUEST)
                .and()
                .withExternal()
                .source(SagaStates.POINT_CHECK_FAIL).target(SagaStates.ORDER_CANCELED).event(SagaEvents.ORDER_CANCEL)
                .and()
                .withExternal()
                .source(SagaStates.DISCOUNT_REQUEST_OK).target(SagaStates.PAYMENT_REQUESTED).event(SagaEvents.PAYMENT_REQUEST)
                .action(context -> System.out.println("Executing transition action from DISCOUNT_REQUEST_OK to sasdfasdfasdf"))
                .and()
                .withExternal()
                .source(SagaStates.DISCOUNT_REQUEST_FAIL).target(SagaStates.ORDER_CANCELED).event(SagaEvents.ORDER_CANCEL)
                .and()
                .withExternal()
                .source(SagaStates.PAYMENT_REQUEST_OK).target(SagaStates.ORDER_COMPLETED).event(SagaEvents.ORDER_COMPLETE)
                .and()
                .withExternal()
                .source(SagaStates.PAYMENT_REQUEST_FAIL).target(SagaStates.ORDER_CANCELED).event(SagaEvents.DISCOUNT_CANCEL)
                .and()
                .withExternal()
                .source(SagaStates.ORDER_CANCEL_REQUEST).target(SagaStates.PAYMENT_CANCEL_REQUEST).event(SagaEvents.PAYMENT_CANCEL)
                .and()
                .withExternal()
                .source(SagaStates.PAYMENT_CANCEL_REQUEST).target(SagaStates.DISCOUNT_CANCEL_REQUEST).event(SagaEvents.DISCOUNT_CANCEL)
                .and()
                .withExternal()
                .source(SagaStates.DISCOUNT_CANCEL_REQUEST).target(SagaStates.ORDER_CANCELED).event(SagaEvents.ORDER_CANCEL)
        ;
    }

//    @Override
//    public void configure(StateMachineConfigurationConfigurer<SagaStates, SagaEvents> config) throws Exception {
//        config.withConfiguration()
//                .autoStartup(true)
//                .listener(listener());
//    }
//
//    @Bean
//    public StateMachineListener<SagaStates, SagaEvents> listener() {
//        return new StateMachineListenerAdapter<SagaStates, SagaEvents>() {
//            @Override
//            public void stateChanged(State<SagaStates, SagaEvents> from, State<SagaStates, SagaEvents> to) {
//                log.info("State change to {}", to.getId().toString());
//            }
//        };
//    }
    @Override
    public void configure(StateMachineConfigurationConfigurer<SagaStates, SagaEvents> config) throws Exception {
        StateMachineListenerAdapter<SagaStates, SagaEvents> adapter = new StateMachineListenerAdapter<>(){
            @Override
            public void stateChanged(State<SagaStates, SagaEvents> fromState, State<SagaStates, SagaEvents> toState) {
                // 리스너의 동작을 구현
                log.info("$$$$$$$$$$$$$$$State changed from {} to {}",
                        fromState == null ? "start": fromState.getId().toString(),
                        toState.getId().toString());
            }
        };
        config.withConfiguration().listener(adapter); // 리스너를 등록
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
//    @Bean
//    public StateMachineListener<SagaStates, SagaEvents> listener() {
//        return new StateMachineListenerAdapter<SagaStates, SagaEvents>() {
//            @Override
//            public void transition(Transition<SagaStates, SagaEvents> transition) {
//                log.debug("Transition log : source: {} -> target : {}", transition.getSource().getStates()
//                        ,transition.getTarget().getStates());
//                System.out.println("Transition: " + transition.getSource().getId().toString() + " -> " + transition.getTarget().getId());
//            }
//
//            @Override
//            public void stateChanged(State<SagaStates, SagaEvents> fromState, State<SagaStates, SagaEvents> toState) {
//                // 리스너의 동작을 구현
//                log.info("State changed from {} to {}. Current status {}",
//                        fromState == null ? "start": fromState.getId(),
//                        toState.getId(), toState.getStates());
//            }
//        };
//    }

    @Bean
    public Action<SagaStates, SagaEvents> actionDiscountQuery() {
        return new Action<SagaStates, SagaEvents>() {
            @Override
            public void execute(StateContext<SagaStates, SagaEvents> context) {
                System.out.println("Action actionDiscountQuery");
            }
        };
    }
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
