package org.sergey.idf_bank_microservice.error;

import lombok.Builder;

@Builder
public record ValidationError(String base, String error) {
}