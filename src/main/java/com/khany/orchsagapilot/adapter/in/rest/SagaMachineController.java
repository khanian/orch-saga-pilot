package com.khany.orchsagapilot.adapter.in.rest;

import com.khany.orchsagapilot.adapter.in.dto.ResponseSagaDto;
import com.khany.orchsagapilot.application.port.in.SagaMachineUseCase;
import com.khany.orchsagapilot.config.SagaStates;
import com.khany.orchsagapilot.domain.Saga;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@Slf4j
@RestController
@AllArgsConstructor
public class SagaMachineController {

    private final SagaMachineUseCase sagaMachineUseCase;

    @GetMapping("hello")
    public String getHello() {
        return "Hello, World";
    }

    @GetMapping("/discount")
    public ResponseSagaDto getDiscount() {
        log.debug("dddddddddd:::::::::::");
        Saga resultSaga = sagaMachineUseCase.getDiscount();
        return ResponseSagaDto.builder()
                .eventTimestamp(resultSaga.eventTimestamp())
                .orderId(resultSaga.orderId())
                .customerId(resultSaga.orderId())
                .currentState(resultSaga.currentState())
                .value(resultSaga.value())
                .build();

    }

    @GetMapping("/payment")
    public ResponseSagaDto getPayment() {
        log.debug("dddddddddd:::::::::::");
        Saga resultSaga = sagaMachineUseCase.getPayment();
        return ResponseSagaDto.builder()
                .eventTimestamp(resultSaga.eventTimestamp())
                .orderId(resultSaga.orderId())
                .customerId(resultSaga.orderId())
                .currentState(resultSaga.currentState())
                .value(resultSaga.value())
                .build();

    }

    @GetMapping("/next")
    public ResponseSagaDto getNextStep() {
        Saga saga = Saga.builder()
                .eventTimestamp(new Timestamp(System.currentTimeMillis()))
                .customerId(2L)
                .orderId(2L)
                .currentState(String.valueOf(SagaStates.DISCOUNT_REQUEST_OK))
                .value("")
                .build();

        Saga resultSaga = sagaMachineUseCase.nextStepSaga(saga);

        return ResponseSagaDto.builder()
                .eventTimestamp(resultSaga.eventTimestamp())
                .orderId(resultSaga.orderId())
                .customerId(resultSaga.orderId())
                .currentState(resultSaga.currentState())
                .value(resultSaga.value())
                .build();
    }
}
