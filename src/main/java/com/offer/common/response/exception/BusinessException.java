package com.offer.common.response.exception;

import com.offer.common.response.ResponseMessage;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private ResponseMessage responseMessage;

    public BusinessException(ResponseMessage responseMessage) {
        super(responseMessage.getMessage());
        this.responseMessage = responseMessage;
    }

}
