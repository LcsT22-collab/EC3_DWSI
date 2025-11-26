package com.rojas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rojas.entity.BandaEntity;
import com.rojas.enums.Estado;

@Repository
public interface BandaRepository extends JpaRepository<BandaEntity, Long> {

	
	List<BandaEntity> findByEstado(Estado estado);
    List<BandaEntity> findByNombreContainingIgnoreCase(String nombre);
    List<BandaEntity> findByGeneroContainingIgnoreCase(String genero);
	
}
