package com.bciexercise.exercise.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetailDTO {
    private LocalDateTime timestamp;
    private int codigo;
    private String detail;
}
