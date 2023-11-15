package com.example.demo.body;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class CommonRequest {
    String message;
    Object object;
    HttpStatus httpStatus;
}
