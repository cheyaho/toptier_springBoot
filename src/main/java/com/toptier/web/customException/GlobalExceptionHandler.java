package com.toptier.web.customException;

import com.toptier.web.dto.ResultResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    // 유효성 검사 예외 처리
    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<ResultResponse<Void>> handleValidation(org.springframework.web.bind.MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .orElse("입력값이 올바르지 않습니다.");
        log.warn("Validation error: {}", msg);
        return ResponseEntity.badRequest()
                .body(ResultResponse.fail(msg, "VALIDATION_ERROR"));
    }

    // DB 제약 위반 예외 처리
    @ExceptionHandler({
            org.springframework.dao.DataIntegrityViolationException.class,
            org.hibernate.exception.ConstraintViolationException.class
    })
    public ResponseEntity<ResultResponse<Void>> handleDataIntegrity(Exception e){
        log.warn("DB constraint violation", e);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ResultResponse.fail("DB 입력 중 제약 위반 오류가 발생하였습니다.", "DB_CONSTRAINT_VIOLATION"));
    }

    // 정적 리소스(파비콘, well-known 등) 없음: 404로 처리
    @ExceptionHandler(org.springframework.web.servlet.resource.NoResourceFoundException.class)
    public ResponseEntity<ResultResponse<Void>> handleNoResourceFound(
            org.springframework.web.servlet.resource.NoResourceFoundException e) {
        // 브라우저가 자동으로 요청하는 favicon, devtools 관련 경로 등이 자주 발생하므로 ERROR로 로깅하지 않음
        log.debug("Static resource not found: {}", e.getResourcePath());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResultResponse.fail("Resource Not Found", "RESOURCE_NOT_FOUND"));
    }

    // 그 외 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResultResponse<Void>> handleException(Exception e) {
        // ResponseStatusException 등은 지정된 상태코드를 존중
        if (e instanceof org.springframework.web.server.ResponseStatusException rse) {
            HttpStatus status = HttpStatus.resolve(rse.getStatusCode().value());
            if (status != null && status.is4xxClientError()) {
                log.warn("Client error: {}", rse.getMessage());
                return ResponseEntity.status(status)
                        .body(ResultResponse.fail(rse.getReason() != null ? rse.getReason() : "Client Error",
                                "CLIENT_ERROR"));
            }
        }

        // NoHandlerFoundException은 404로 처리 (설정에 따라 발생)
        if (e instanceof org.springframework.web.servlet.NoHandlerFoundException nhfe) {
            log.debug("No handler found: {} {}", nhfe.getHttpMethod(), nhfe.getRequestURL());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResultResponse.fail("Not Found", "NOT_FOUND"));
        }

        log.error("Internal Server Error", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResultResponse.fail("Internal Server Error", "INTERNAL_SERVER_ERROR"));
    }
}
