package com.rojas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rojas.dto.CancionDTO;
import com.rojas.entity.BandaEntity;
import com.rojas.entity.CancionEntity;
import com.rojas.enums.Estado;
import com.rojas.exception.BandaNoEncontradaException;
import com.rojas.exception.CancionNoEncontradaException;
import com.rojas.mapper.CancionMapper;
import com.rojas.repository.BandaRepository;
import com.rojas.repository.CancionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CancionServiceImpl implements ICancionService {

	private final CancionRepository cancionRepo;
	private final BandaRepository bandaRepo;

	@Override
	public List<CancionDTO> findAll() {
	    try {
	        log.info("Listando todas las canciones");
	        List<CancionEntity> entidades = cancionRepo.findAll();
	        log.info("Entidades encontradas en BD: {}", entidades.size());
	        
	        List<CancionDTO> dtos = CancionMapper.toDtoList(entidades);
	        log.info("DTOs mapeados: {}", dtos.size());
	        
	        return dtos;
	    } catch (Exception e) {
	        log.error("Error al listar canciones: {}", e.getMessage());
	        throw new RuntimeException("Error al obtener la lista de canciones", e);
	    }
	}


	@Override
	public Optional<CancionDTO> findXId(Long id) {
		log.info("Buscando canción con id: " + id);
		return cancionRepo.findById(id).map(CancionMapper::toDTO);
	}

	@Override
	public CancionDTO guardar(CancionDTO dto) {
		log.info("Guardando canción: {}", dto.getTitulo());

		BandaEntity banda = bandaRepo.findById(dto.getIdBanda()).orElseThrow(
				() -> new BandaNoEncontradaException("Banda con id " + dto.getIdBanda() + " no encontrada"));

		CancionEntity entidad = CancionMapper.toEntity(dto);
		entidad.setBanda(banda);

		CancionEntity guardada = cancionRepo.save(entidad);

		return CancionMapper.toDTO(guardada);
	}

	@Override
	public CancionDTO actualizar(Long id, CancionDTO dto) {
		log.info("Actualizando canción con id: {}", id);

		CancionEntity entidad = cancionRepo.findById(id)
				.orElseThrow(() -> new CancionNoEncontradaException("Canción con id " + id + " no encontrada"));

		BandaEntity banda = bandaRepo.findById(dto.getIdBanda()).orElseThrow(
				() -> new BandaNoEncontradaException("Banda con id " + dto.getIdBanda() + " no encontrada"));

		entidad.setTitulo(dto.getTitulo());
		entidad.setDuracion(dto.getDuracion());
		entidad.setEstado(dto.getEstado());
		entidad.setBanda(banda);

		CancionEntity actualizada = cancionRepo.save(entidad);
		return CancionMapper.toDTO(actualizada);
	}

	@Override
	public void eliminar(Long id) {
	    log.info("Eliminando lógicamente canción con id: {}", id);

	    CancionEntity entidad = cancionRepo.findById(id)
	            .orElseThrow(() -> new CancionNoEncontradaException("Canción con id " + id + " no encontrada"));

	    // Cambiar estado a INACTIVO en lugar de eliminar
	    entidad.setEstado(Estado.INACTIVO);
	    cancionRepo.save(entidad);
	}

}
