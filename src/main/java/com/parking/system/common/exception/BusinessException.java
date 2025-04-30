package com.parking.system.common.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final String errorCode;

    /**
     * Constructor for standard usage.
     * Uses the default message from the provided BusinessErrorCode.
     *
     * @param code the business error code
     */
    public BusinessException(BusinessErrorCode code) {
        super(code.getDefaultMessage());
        this.errorCode = code.getCode();
    }

    /**
     * Constructor for custom messages.
     * Allows overriding the default message while keeping the error code.
     *
     * @param code           the business error code
     * @param customMessage  the custom message
     */
    public BusinessException(BusinessErrorCode code, String customMessage) {
        super(customMessage);
        this.errorCode = code.getCode();
    }
}