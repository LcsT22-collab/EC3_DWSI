package com.rojas.controller.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rojas.dto.CancionDTO;
import com.rojas.entity.BandaEntity;
import com.rojas.entity.CancionEntity;
import com.rojas.exception.BandaNoEncontradaException;
import com.rojas.exception.CancionNoEncontradaException;
import com.rojas.mapper.CancionMapper;
import com.rojas.repository.BandaRepository;
import com.rojas.repository.CancionRepository;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/cancion")
@Data
@Slf4j
public class CancionController {

	private final BandaRepository bandaRepo;
	private final CancionRepository cancionRepo;
	
	@GetMapping("/listar")
	public ResponseEntity<?> listarTodo(){
		List<CancionDTO> lista = CancionMapper.toDtoList(cancionRepo.findAll());
		return ResponseEntity.ok(lista);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarXId(@PathVariable Long id) throws CancionNoEncontradaException{
		log.info("Buscando cancion por id: " + id);

		return cancionRepo.findById(id)
				.map(CancionMapper::toDTO)
				.map(ResponseEntity::ok)
				.orElseThrow(() -> new CancionNoEncontradaException("Cancion con codigo: " + id + "no encontrada"));
		
	}
	
	@PostMapping
	public ResponseEntity<?> guardar(@Valid @RequestBody CancionDTO dto){
		
	    BandaEntity banda = bandaRepo.findById(dto.getIdBanda())
	        .orElseThrow(() -> new BandaNoEncontradaException("Banda con id " + dto.getIdBanda() + " no encontrada"));

		CancionEntity entidad = CancionEntity.builder()
				.titulo(dto.getTitulo())
				.duracion(dto.getDuracion())
				.estado(dto.getEstado())
				.banda(banda)
				.build();
		
		CancionEntity guardado = cancionRepo.save(entidad);
		return ResponseEntity.status(HttpStatus.CREATED).body(CancionMapper.toDTO(guardado));
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<?> actualizar(@PathVariable Long id,@Valid @RequestBody CancionDTO dto){
		
		log.info("Titutlo: " + dto.getTitulo());
		log.info("duracion: " + dto.getDuracion());
		log.info("Estado: "  + dto.getEstado());
		
		BandaEntity banda = bandaRepo.findById(dto.getIdBanda())
	            .orElseThrow(() -> new BandaNoEncontradaException("Banda con id " + dto.getIdBanda() + " no encontrada"));

		
		CancionEntity actualizado = cancionRepo.findById(id)
				.map(x -> {
					x.setTitulo(dto.getTitulo());
					x.setDuracion(dto.getDuracion());
					x.setEstado(dto.getEstado());
					x.setBanda(banda);
					return cancionRepo.save(x);
				})
				.orElseThrow(() -> new CancionNoEncontradaException("Cancion no encontrada" + id + "no encontrada"));
		
        return ResponseEntity.ok(CancionMapper.toDTO(actualizado));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id){
		
		boolean existe = cancionRepo.existsById(id);
		if(!existe) {
			throw new CancionNoEncontradaException("Cancion con id: " + id + " no encontrada");
		}
		
		cancionRepo.deleteById(id);
		return ResponseEntity.noContent().build();
		
	}
	
	
}
