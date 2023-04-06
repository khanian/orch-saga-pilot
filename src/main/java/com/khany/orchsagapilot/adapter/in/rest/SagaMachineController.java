package com.khany.orchsagapilot.adapter.in.rest;

import com.khany.orchsagapilot.application.port.in.SagaMachineUseCase;
import com.khany.orchsagapilot.domain.Saga;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class SagaMachineController {

    private final SagaMachineUseCase sagaMachineUseCase;

    @GetMapping("hello")
    public String getHello() {
        return "Hello, World";
    }

    @GetMapping("/qetDiscount")
    public Saga getDiscount() {
        log.debug("dddddddddd:::::::::::");
        return sagaMachineUseCase.getDiscount();
    }
}
