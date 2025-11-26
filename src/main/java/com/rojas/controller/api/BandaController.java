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

import com.rojas.dto.BandaDTO;
import com.rojas.entity.BandaEntity;
import com.rojas.exception.BandaNoEncontradaException;
import com.rojas.mapper.BandaMapper;
import com.rojas.repository.BandaRepository;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/banda")
@Data
@Slf4j
public class BandaController {

	private final BandaRepository bandaRepo;

	@GetMapping("listar")
	public ResponseEntity<List<BandaDTO>> listarTodo(){
		log.info("Solicitando listado de banda");

		List<BandaDTO> lista = BandaMapper.toDTOList2(bandaRepo.findAll());
		return ResponseEntity.ok(lista);		
	}

	@GetMapping("/{id}")
	public ResponseEntity<BandaDTO> obtenerXId(@PathVariable Long id) throws BandaNoEncontradaException{
        log.info("Buscando banda con id: "+ id);

        return bandaRepo.findById(id)
                .map(BandaMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new BandaNoEncontradaException("Banda con codigo: " + id + " no encontrada"));
    }

	@PostMapping	
	public ResponseEntity<?> guardar(@Valid @RequestBody BandaDTO dto){

		log.info("Nombre de la banda: " + dto.getNombre());
		log.info("Genero" + dto.getGenero());
		log.info("Estado de la banda: " + dto.getEstado());

		BandaEntity entidad = BandaEntity.builder()
                .nombre(dto.getNombre())
                .genero(dto.getGenero())
                .estado(dto.getEstado())
                .build();

        BandaEntity guardada = bandaRepo.save(entidad);	
		return ResponseEntity.status(HttpStatus.CREATED).body(BandaMapper.toDTO(guardada));
	}


	@PutMapping("/{id}")
	public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody BandaDTO dto){
		log.info("Actualizando nombre de la banda: " + dto.getNombre());
		log.info("Actualizando genero de la banda: " + dto.getGenero());
		log.info("Actualizando estado de banda: " + dto.getEstado());

		BandaEntity actualizado = bandaRepo.findById(id)
                .map(entity -> {
                    entity.setNombre(dto.getNombre());
                    entity.setGenero(dto.getGenero());
                    entity.setEstado(dto.getEstado());
                    return bandaRepo.save(entity);
                })
                .orElseThrow(() -> new BandaNoEncontradaException("Banda con id " + id + " no encontrada"));
		
        return ResponseEntity.ok(BandaMapper.toDTO(actualizado));
	}

	@DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Eliminando banda con id: " + id);
        
        boolean existe = bandaRepo.existsById(id);
        if (!existe) {
            throw new BandaNoEncontradaException("Banda con id " + id + " no encontrada");
        }

        bandaRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}