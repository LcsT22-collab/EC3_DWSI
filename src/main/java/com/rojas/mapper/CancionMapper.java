package com.rojas.mapper;

import java.util.List;
import com.rojas.dto.CancionDTO;
import com.rojas.entity.CancionEntity;

public class CancionMapper {

	public static CancionDTO toDTO(CancionEntity entity) {
		if (entity == null)
			return null;

		return CancionDTO.builder().idCancion(entity.getIdCancion()).titulo(entity.getTitulo())
				.duracion(entity.getDuracion()).estado(entity.getEstado())
				.idBanda(entity.getBanda() != null ? entity.getBanda().getIdBanda() : null)
				// NUEVO: Agregar información de la banda
				.nombreBanda(entity.getBanda() != null ? entity.getBanda().getNombre() : "Sin banda")
				.generoBanda(entity.getBanda() != null ? entity.getBanda().getGenero() : "").build();
	}

	public static CancionEntity toEntity(CancionDTO dto) {
		if (dto == null)
			return null;

		return CancionEntity.builder().idCancion(dto.getIdCancion()).titulo(dto.getTitulo()).duracion(dto.getDuracion())
				.estado(dto.getEstado()).build();
	}

	public static List<CancionDTO> toDtoList(List<CancionEntity> lista) {
		if (lista == null) {
			return List.of();
		}

		return lista.stream().map(CancionMapper::toDTO).toList();
	}

	public static List<CancionEntity> toEntityList(List<CancionDTO> lista) {
		if (lista == null) {
			return List.of();
		}

		return lista.stream().map(CancionMapper::toEntity).toList();
	}
}