package com.khany.orchsagapilot.application.port.in;

import com.khany.orchsagapilot.domain.Saga;

public interface SagaMachineUseCase {
    Saga nextStepSaga(Saga saga);

    Saga getDiscount();
}
