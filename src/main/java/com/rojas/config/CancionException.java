package com.rojas.config;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.rojas.exception.CancionNoEncontradaException;

@ControllerAdvice
public class CancionException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> erroresPorCampo = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (msg1, msg2) -> msg1 + "; " + msg2));

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("codigo", HttpStatus.BAD_REQUEST.value());
        respuesta.put("mensaje", "Errores de validación");
        respuesta.put("errores", erroresPorCampo); 

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }

    @ExceptionHandler(CancionNoEncontradaException.class)
    public ResponseEntity<Map<String, Object>> handleCancionNotFound(CancionNoEncontradaException ex) {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("codigo", HttpStatus.NOT_FOUND.value());
        respuesta.put("mensaje", ex.getMessage() != null ? ex.getMessage() : "Cancion no encontrada");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAllExceptions(Exception ex) {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("codigo", HttpStatus.INTERNAL_SERVER_ERROR.value());
        respuesta.put("mensaje", "Ocurrió un error inesperado");
        respuesta.put("detalle", ex.getMessage() != null ? ex.getMessage() : "Sin detalles");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
    }
}
