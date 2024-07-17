package com.sixplus.server.api.core.exception;

import com.sixplus.server.api.model.ErrorResponse;
import com.sixplus.server.api.utils.CmmCode;
import com.sixplus.server.api.utils.ModelMapperUtils;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import java.sql.SQLException;
import java.util.stream.Collectors;

/**
 * <pre>
 * 1. 패키지명 : com.dw.mcc.core.exceptions
 * 2. 타입명 : GlobalControllerExceptionHandler.java
 * 3. 작성일 : 2021. 12. 30
 * 4. 작성자 : Mcircle 임승욱
 * 5. 설명   : GlobalControllerExceptionHandler - RestControllerAdvice
 * Copyright mcircle corp. all right reserved
 * </pre>
 **/
@Slf4j
@RestControllerAdvice
public class GlobalControllerExceptionHandler {
    @ExceptionHandler({ BindException.class, MethodArgumentNotValidException.class })
    protected ResponseEntity<ErrorResponse> handleBindException(BindException e) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY.value()).body(
                ErrorResponse.of(
                        HttpStatus.UNPROCESSABLE_ENTITY.value(),
                        e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", ")))
        );
    }

    @ExceptionHandler({ ValidationException.class, ConstraintViolationException.class })
    protected ResponseEntity<ErrorResponse> handleValidationException(ValidationException e) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY.value()).body(
                ErrorResponse.of(HttpStatus.UNPROCESSABLE_ENTITY.value(), e.getMessage())
        );
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponse> handleFeignException(FeignException e) {
        log.error("handle feign Exception : ", e);
        try {
            return ResponseEntity.status(CmmCode.SHOP_EXCEPTION_STATUS_CODE).body(ErrorResponse.of(e.status(), ModelMapperUtils.convertMap(e.contentUTF8()).get("message").toString()));
        } catch(Exception ie) {
            log.error("feign Exception : ", ie);
            return ResponseEntity.status(CmmCode.SHOP_EXCEPTION_STATUS_CODE).body(ErrorResponse.of(e.status(), "feign 오류(메세지 없음)"));
        }
    }

    @ExceptionHandler(CustomFeignException.class)
    public ResponseEntity<ErrorResponse> handleCustomFeignException(CustomFeignException e) {
        log.error("custom feign Exception : ", e);
        return ResponseEntity.status(CmmCode.SHOP_EXCEPTION_STATUS_CODE).body(ErrorResponse.of(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(CustomErrorException.class)
    public ResponseEntity<ErrorResponse> handleCustomErrorException(CustomErrorException e) {
        return ResponseEntity.status(e.getCode()).body(ErrorResponse.of(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(ShopErrorException.class)
    public ResponseEntity<ErrorResponse> handleShopErrorException(ShopErrorException e) {
    	log.info("@ExceptionHandler : ShopErrorException {}", e.getMessage());
        return ResponseEntity.status(CmmCode.SHOP_EXCEPTION_STATUS_CODE).body(ErrorResponse.of(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(ConnectionTimeoutException.class)
    public ResponseEntity<ErrorResponse> handleConnectionTimeoutException(ConnectionTimeoutException e) {
        return ResponseEntity.status(e.getCode()).body(ErrorResponse.of(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ErrorResponse> handlePaymentException(PaymentException e) {
        return ResponseEntity.status(e.getCode()).body(ErrorResponse.of(e.getCode(), e.getMessage()));
    }

    /**
     * bad reqyest
     * @param e BadRequestException
     * @return ResponseEntity<ErrorResponse>
     */
    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException e) {
        log.error("Bad Request 에러 : {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    @ExceptionHandler(UnAutholizedException.class)
    public ResponseEntity<ErrorResponse> handleUnAutholizedException(UnAutholizedException e) {
        log.error("UnAutholized Exception 에러 : {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(ErrorResponse.of(HttpStatus.UNAUTHORIZED.value(), e.getMessage()));
    }

    /**
     * not found
     * @param e handleNotFoundException
     * @return ResponseEntity<ErrorResponse>
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(RuntimeException e) {
        log.error("Request 에러 : {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(ErrorResponse.of(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }

    /**
     * SQL 문법 오류 : 프론트앤드에서 중복 오류를 빨리 catch하기 위해서 만듬
     * @param e DuplicateKeyException
     * @return ResponseEntity<ErrorResponse>
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateKeyException(DuplicateKeyException e) {
        log.error("DuplicateKeyException 에러 : {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), "중복 데이터가 존재합니다."));
    }


    /**
     * SQL 오류 : 프론트앤드에서 파라메터 전달을 잘 못하고 있을때 빨리 catch하기 위해서 만듬
     * @param e SQLException
     * @return ResponseEntity<ErrorResponse>
     */
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorResponse> handleSQLException(SQLException e) {
        log.error("SQLException 에러 : {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }

    /**
     * reqyest 파라메터 오류 : 프론트앤드에서 파라메터 전달을 잘 못하고 있을때 빨리 catch하기 위해서 만듬
     * @param e MissingServletRequestParameterException
     * @return ResponseEntity<ErrorResponse>
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("MissingServletRequestParameterException 에러 : {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY.value()).body(ErrorResponse.of(HttpStatus.UNPROCESSABLE_ENTITY.value(), e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(Exception e) {
        log.error("handle Exception : ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
}
