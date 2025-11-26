package com.rojas.mapper;

import java.util.List;

import com.rojas.dto.BandaDTO;
import com.rojas.entity.BandaEntity;

public interface IBandaMapper {

	//Convertir Entity → DTO
	BandaDTO toDTO(BandaEntity entity);		// Recibe ENTITY, retorna DTO


	//Convertir lista de Entities -> lista de DTOs
	List<BandaDTO> toDTOList(List<BandaEntity> entities);

	//Convertir DTO → Entity
	BandaEntity toEntity(BandaDTO dto); // Recibe DTO, retorna ENTITY

	//Convertir lista de DTOs -> lista de Entities
	List<BandaEntity> toEntityList(List<BandaDTO> dtos);

}
