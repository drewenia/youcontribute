package com.example.youcontribute.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
/* JSON verisinde null olmayan alanlari dondur */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private String message;
}
