package com.rojas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rojas.dto.BandaDTO;
import com.rojas.entity.BandaEntity;
import com.rojas.enums.Estado;
import com.rojas.exception.BandaNoEncontradaException;
import com.rojas.mapper.BandaMapper;
import com.rojas.repository.BandaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BandaServiceImpl implements IBandaService {

	private final BandaRepository bandaRepo;

	@Override
	@Transactional
	public List<BandaDTO> listarTodo() {
		log.info("Listando todas las bandas");
		List<BandaEntity> entidades = bandaRepo.findAll();
		return BandaMapper.toDTOList2(entidades);
	}

	@Override
	@Transactional
	public Optional<BandaDTO> buscarXId(Long id) {
		log.info("Buscando banda con id: " + id);
		return bandaRepo.findById(id).map(BandaMapper::toDTO);
	}

	@Override
	@Transactional
	public BandaDTO guardar(BandaDTO dto) {
		log.info("Guardando banda: ", dto.getNombre());

		BandaEntity entidad = BandaMapper.toEntity(dto);
		BandaEntity guardado = bandaRepo.save(entidad);

		return BandaMapper.toDTO(guardado);

	}

	@Override
	@Transactional
	public BandaDTO actualizar(Long id, BandaDTO dto) {
		log.info("Actualizando banda con id: {}", id);

		BandaEntity entidad = bandaRepo.findById(id)
				.orElseThrow(() -> new BandaNoEncontradaException("Banda con id " + id + " no encontrada"));

		entidad.setNombre(dto.getNombre());
		entidad.setGenero(dto.getGenero());
		entidad.setEstado(dto.getEstado());

		BandaEntity actualizada = bandaRepo.save(entidad);

		return BandaMapper.toDTO(actualizada);	
	}

	@Transactional
	public void eliminar(Long id) {
	    log.info("Eliminando lógicamente banda con id: {}", id);

	    BandaEntity entidad = bandaRepo.findById(id)
	            .orElseThrow(() -> new BandaNoEncontradaException("Banda con id " + id + " no encontrada"));

	    entidad.setEstado(Estado.INACTIVO);
	    bandaRepo.save(entidad);
	}

}
