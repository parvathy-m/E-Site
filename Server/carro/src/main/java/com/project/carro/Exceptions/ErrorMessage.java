package com.project.carro.Exceptions;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorMessage {
    String code;
    String message;
    String filed;
}
