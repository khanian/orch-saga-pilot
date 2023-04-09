package com.khany.orchsagapilot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Service;

@Slf4j
public class StatesChangeInterceptor extends StateMachineInterceptorAdapter<SagaStates, SagaEvents> {

    @Override
    public Message<SagaEvents> preEvent(Message<SagaEvents> message, StateMachine<SagaStates, SagaEvents> stateMachine) {
        log.info("[ interceptor ] message!!!!!!!!!!!!!!!{} ", message);
        return super.preEvent(message, stateMachine);
    }

    @Override
    public void preStateChange(State<SagaStates, SagaEvents> state, Message<SagaEvents> message, Transition<SagaStates, SagaEvents> transition, StateMachine<SagaStates, SagaEvents> stateMachine, StateMachine<SagaStates, SagaEvents> rootStateMachine) {
        log.info("[ interceptor ] preStateChange!!!!!!!!!!!!!!!{} ", message);
        super.preStateChange(state, message, transition, stateMachine, rootStateMachine);
    }

    @Override
    public void postStateChange(State<SagaStates, SagaEvents> state, Message<SagaEvents> message, Transition<SagaStates, SagaEvents> transition, StateMachine<SagaStates, SagaEvents> stateMachine, StateMachine<SagaStates, SagaEvents> rootStateMachine) {
        log.info("[ interceptor ] postStateChange!!!!!!!!!!!!!!!{} ", message);
        super.postStateChange(state, message, transition, stateMachine, rootStateMachine);
    }

    @Override
    public StateContext<SagaStates, SagaEvents> preTransition(StateContext<SagaStates, SagaEvents> stateContext) {
        log.info("[ interceptor ] stateContext !!!!!!!!!!!!!!!{} ", stateContext);
        return super.preTransition(stateContext);
    }

    @Override
    public StateContext<SagaStates, SagaEvents> postTransition(StateContext<SagaStates, SagaEvents> stateContext) {
        log.info("[ interceptor ] stateContext !!!!!!!!!!!!!!!post {} ", stateContext);
        return super.postTransition(stateContext);
    }

    @Override
    public Exception stateMachineError(StateMachine<SagaStates, SagaEvents> stateMachine, Exception exception) {
        log.error(exception.getMessage());
        return super.stateMachineError(stateMachine, exception);
    }
}
