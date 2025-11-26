package com.rojas.mapper;

import java.util.List;

import com.rojas.dto.CancionDTO;
import com.rojas.entity.CancionEntity;

public interface ICancionMapper {
	
	// Entity a DTO
	CancionDTO toDTO(CancionEntity entity);
	
	//Convertir lista de Entities -> lista de DTOs
	List<CancionDTO> toDtoList(List<CancionEntity> entities);
	
	// Dto a Entity
	CancionEntity toEntity(CancionDTO dto);
	
	//Convertir lista de DTOs -> lista de Entities
	List<CancionEntity> toEntityList(List<CancionDTO> dtos);
	
}
