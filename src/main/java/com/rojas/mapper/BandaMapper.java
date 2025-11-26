package com.rojas.mapper;

import java.util.List;

import com.rojas.dto.BandaDTO;
import com.rojas.entity.BandaEntity;

public class BandaMapper {

	// ENTITY → DTO
	public static BandaDTO toDTO(BandaEntity entity) {
		if (entity == null) {
			return null;
		}

		return BandaDTO.builder()
				.idBanda(entity.getIdBanda())
				.nombre(entity.getNombre())
				.genero(entity.getGenero())
				.estado(entity.getEstado())
				.build();
	}

	//Convertir un dto a entity
	public static BandaEntity toEntity(BandaDTO dto) {

		if (dto == null) {
			return null;
		}

		return BandaEntity.builder()
				.idBanda(dto.getIdBanda())
				.nombre(dto.getNombre())
				.genero(dto.getGenero())
				.estado(dto.getEstado())
				.build();

	}

	//Primera Forma que nos retorne en lista
	public static List<BandaDTO> toDTOList(List<BandaEntity> lista1){
		return lista1.stream()
				.map(x ->
				new BandaDTO(
						x.getIdBanda(),
						x.getNombre(),
						x.getGenero(),
						x.getEstado())).toList();
	}

	public static List<BandaEntity> toEntityList(List<BandaDTO> lista1){
		return lista1.stream()
				.map(x ->
				new BandaEntity(
						x.getIdBanda(),
						x.getNombre(),
						x.getGenero(),
						x.getEstado(),
						null)
				).toList();
	}


	//Segunda forma que nos retorne en una lista
	public static List<BandaDTO> toDTOList2(List<BandaEntity> lista) {
		return lista.stream()
				.map(BandaMapper::toDTO)
				.toList();
	}


	public static List<BandaEntity> toEntityList2(List<BandaDTO> lista) {
		return lista.stream()
				.map(BandaMapper::toEntity)
				.toList();
	}

}
