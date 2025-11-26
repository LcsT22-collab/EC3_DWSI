package com.rojas.service;

import java.util.List;
import java.util.Optional;

import com.rojas.dto.BandaDTO;

public interface IBandaService {

	List<BandaDTO> listarTodo();
	
	Optional<BandaDTO> buscarXId(Long id);
	
	BandaDTO guardar(BandaDTO dto);
	
	BandaDTO actualizar(Long id, BandaDTO dto);
	
	void eliminar(Long id);
	
	
}
