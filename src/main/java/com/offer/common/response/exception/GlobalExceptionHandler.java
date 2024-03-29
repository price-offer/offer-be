package com.offer.common.response.exception;

import com.offer.common.response.ApiResponse;
import com.offer.common.response.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * body의 validation 실패 시, 예외처리
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.info(exception.getAllErrors().get(0).getDefaultMessage() + " from MethodArgumentNotValidException");
        return createErrorResponse(ResponseMessage.DTO_FIELD_INVALID);
    }

    /**
     * path요소의 validation 실패 시, 예외처리
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResponse handleConstraintViolationException(ConstraintViolationException exception) {
        log.info(exception.getMessage() + " from ConstraintViolationException");
        return createErrorResponse(ResponseMessage.PATH_FIELD_INVALID);
    }

    /**
     * 데이터 바인딩 실패 시, 예외처리
     */
    @ExceptionHandler(BindException.class)
    public ApiResponse handleBindException(BindException exception) {
        log.info(exception.getAllErrors().get(0).getDefaultMessage() + " from BindException");
        return createErrorResponse(ResponseMessage.DTO_FIELD_INVALID);
    }

    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiResponse handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        log.info(exception.getMessage() + " from HttpRequestMethodNotSupportedException");
        return createErrorResponse(ResponseMessage.HTTP_REQUEST_METHOD_NOT_SUPPORTED);
    }

    /**
     * request param, body 등에서 argument 타입이 올바르지 않을 때 발생
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ApiResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        log.info(exception.getMessage() + " from MethodArgumentTypeMismatchException");
        return createErrorResponse(ResponseMessage.INVALID_REQUEST_ARGUMENT_TYPE);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ApiResponse handleMissingServletRequestParameterException(MissingServletRequestParameterException exception) {
        log.info(exception.getMessage() + " from MissingServletRequestParameterException");
        return createErrorResponse(ResponseMessage.MISSING_PARAMETER);
    }

    /**
     * 파일 용량 초과시 발생
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ApiResponse handleFileSizeLimitExceededException(MaxUploadSizeExceededException exception) {
        log.info(exception.getMessage() + " from FileSizeLimitExceededException");
        return createErrorResponse(ResponseMessage.FILE_SIZE_LIMIT_EXCEEDED);
    }

    @ExceptionHandler(BusinessException.class)
    public ApiResponse handleBusinessException(BusinessException e) {
        log.info(e.getMessage() + " from BusinessException");
        final ResponseMessage responseMessage = e.getResponseMessage();
        return createErrorResponse(responseMessage);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn(e.getMessage());
        return ApiResponse.of(400, e.getMessage());
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiResponse handleGlobalException(Exception e) {
        log.warn(e.getMessage());
        return ApiResponse.of(500, e.getMessage());
    }

    private ApiResponse createErrorResponse(ResponseMessage responseMessage) {
        return ApiResponse.of(responseMessage);
    }
}
