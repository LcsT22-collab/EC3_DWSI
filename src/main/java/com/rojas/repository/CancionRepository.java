package com.rojas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rojas.entity.CancionEntity;
import com.rojas.enums.Estado;

@Repository
public interface CancionRepository extends JpaRepository <CancionEntity, Long>{
	
	List<CancionEntity> findByEstado(Estado estado);
    List<CancionEntity> findByTituloContainingIgnoreCase(String titulo);
    List<CancionEntity> findByBandaIdBanda(Long idBanda);
    List<CancionEntity> findByBandaIdBandaAndEstado(Long idBanda, Estado estado);
	
}
