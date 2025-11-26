package com.rojas.dto;

import java.math.BigDecimal;

import com.rojas.enums.Estado;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CancionDTO {

	private Long idCancion;

    @NotBlank(message = "El titulo es obligatorio")
	private String titulo;

    @NotNull(message = "La duración es obligatoria")
    @Positive(message = "La duración debe ser mayor a 0")
	private BigDecimal duracion;

    @NotNull(message = "El estado es obligatorio")
	private Estado estado;
    
    @NotNull(message = "La banda es obligatoria")
    private Long idBanda; 
    
    
    private String nombreBanda;
    private String generoBanda;

}
