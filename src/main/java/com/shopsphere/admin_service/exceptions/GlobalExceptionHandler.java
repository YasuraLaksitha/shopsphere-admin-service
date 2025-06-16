package com.shopsphere.admin_service.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopsphere.admin_service.dto.ErrorResponseDTO;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final ObjectMapper mapper;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        final HashMap<String, String> errorMap = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error ->
                errorMap.put(((FieldError) error).getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errorMap);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorResponseDTO> handleGlobalException(
            final Exception ex,
            final WebRequest webRequest
    ) {

        final ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .message(ex.getMessage())
                .path(webRequest.getDescription(false))
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {FeignException.class})
    public ResponseEntity<ErrorResponseDTO> handleFeignClientException(final FeignException ex) throws JsonProcessingException {

        final JsonNode jsonNode = mapper.readTree(ex.contentUTF8());

        final ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .status(jsonNode.path("status").asText())
                .message(jsonNode.path("message").asText())
                .path(jsonNode.path("path").asText())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }
}
