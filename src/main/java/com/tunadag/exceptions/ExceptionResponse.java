package com.tunadag.exceptions;

import lombok.*;
import org.springframework.stereotype.Component;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
public class ExceptionResponse {
    private int exceptionCode;
    private String customMessage;
    private String exceptionMessage;
    private int httpStatus;
}
