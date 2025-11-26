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

import com.rojas.exception.BandaNoEncontradaException;

@ControllerAdvice
public class BandaException {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> erroresPorCampo = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (a,b) -> a + "; " + b));

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("codigo", HttpStatus.BAD_REQUEST.value());
        respuesta.put("mensaje", "Errores de validación en Banda");
        respuesta.put("errores", erroresPorCampo);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }

    @ExceptionHandler(BandaNoEncontradaException.class)
    public ResponseEntity<Map<String, Object>> handleBandaNotFound(BandaNoEncontradaException ex) {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("codigo", HttpStatus.NOT_FOUND.value());
        respuesta.put("mensaje", ex.getMessage() != null ? ex.getMessage() : "Banda no encontrada");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAllBandaExceptions(Exception ex) {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("codigo", HttpStatus.INTERNAL_SERVER_ERROR.value());
        respuesta.put("mensaje", "Error interno en Banda");
        respuesta.put("detalle", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
    }
}
