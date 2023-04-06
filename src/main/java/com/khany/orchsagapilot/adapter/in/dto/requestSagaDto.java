package com.khany.orchsagapilot.adapter.in.dto;

import java.sql.Timestamp;

public record requestSagaDto (
    Timestamp eventTimestamp,
    Long orderId,
    Long customerId,
    String currentState,
    Object value){
}
