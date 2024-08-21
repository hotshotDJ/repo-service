package com.services.repo.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ErrorDetails {

    private String timestamp;

    private Integer status;

    private List<String> errors;
}
