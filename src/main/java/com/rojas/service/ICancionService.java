package com.rojas.service;

import java.util.List;
import java.util.Optional;

import com.rojas.dto.CancionDTO;

public interface ICancionService {
	
	List<CancionDTO> findAll();
	
	Optional<CancionDTO> findXId(Long id);
	
	CancionDTO guardar(CancionDTO dto);
	
	CancionDTO actualizar(Long id, CancionDTO dto);
	
	void eliminar(Long id);
	
}
