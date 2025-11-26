package com.rojas.dto;

import com.rojas.enums.Estado;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class BandaDTO {

	private Long idBanda;
	
    @NotBlank(message = "El nombre es obligatorio")
	private String nombre;
    
    @NotBlank(message = "El genero es obligatorio")
	private String genero;
    
    @NotNull(message = "El estado es obligatorio")
	private Estado estado;
}
