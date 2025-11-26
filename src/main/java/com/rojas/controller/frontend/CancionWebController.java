package com.rojas.controller.frontend;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rojas.dto.CancionDTO;
import com.rojas.enums.Estado;
import com.rojas.exception.CancionNoEncontradaException;
import com.rojas.service.IBandaService;
import com.rojas.service.ICancionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/canciones")
@RequiredArgsConstructor
@Slf4j
public class CancionWebController {
    
    private final ICancionService cancionService;
    private final IBandaService bandaService;
    
    @GetMapping
    public String listar(Model model) {
        try {
            log.info("Obteniendo lista de canciones");
            var canciones = cancionService.findAll();
            log.info("Canciones obtenidas: {}", canciones.size());
            
            model.addAttribute("canciones", canciones);
            model.addAttribute("titulo", "Gestión de Canciones");
            return "canciones/lista";
        } catch (Exception e) {
            log.error("Error al obtener canciones: {}", e.getMessage(), e);
            model.addAttribute("error", "Error al cargar las canciones: " + e.getMessage());
            model.addAttribute("canciones", List.of()); 
            model.addAttribute("titulo", "Gestión de Canciones");
            return "canciones/lista";
        }
    }
    
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        log.info("Mostrando formulario de nueva canción");
        CancionDTO cancion = new CancionDTO();
        cancion.setEstado(Estado.ACTIVO); 
        model.addAttribute("cancion", cancion);
        model.addAttribute("bandas", bandaService.listarTodo());
        model.addAttribute("estados", Estado.values());
        model.addAttribute("titulo", "Registrar Canción");
        return "canciones/form";
    }
    
    @PostMapping
    public String guardar(
            @Valid @ModelAttribute("cancion") CancionDTO dto,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model) {
        
        if (result.hasErrors()) {
            log.error("Error de validación al guardar canción");
            model.addAttribute("bandas", bandaService.listarTodo());
            model.addAttribute("estados", Estado.values());
            model.addAttribute("titulo", "Registrar Canción");
            return "canciones/form";
        }
        
        try {
            cancionService.guardar(dto);
            log.info("Canción guardada exitosamente: {}", dto.getTitulo());
            redirectAttributes.addFlashAttribute("success", "Canción registrada exitosamente");
            return "redirect:/canciones";
        } catch (Exception e) {
            log.error("Error al guardar canción", e);
            model.addAttribute("error", "Error al registrar la canción: " + e.getMessage());
            model.addAttribute("bandas", bandaService.listarTodo());
            model.addAttribute("estados", Estado.values());
            model.addAttribute("titulo", "Registrar Canción");
            return "canciones/form";
        }
    }
    
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            CancionDTO cancion = cancionService.findXId(id)
                .orElseThrow(() -> new CancionNoEncontradaException("Canción no encontrada"));
            
            model.addAttribute("cancion", cancion);
            model.addAttribute("bandas", bandaService.listarTodo());
            model.addAttribute("estados", Estado.values());
            model.addAttribute("titulo", "Editar Canción");
            return "canciones/form";
        } catch (CancionNoEncontradaException e) {
            log.error("Canción no encontrada: {}", id);
            redirectAttributes.addFlashAttribute("error", "Canción no encontrada");
            return "redirect:/canciones";
        }
    }
    
    @PostMapping("/editar/{id}")
    public String actualizar(
            @PathVariable Long id,
            @Valid @ModelAttribute("cancion") CancionDTO dto,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model) {
        
        if (result.hasErrors()) {
            log.error("Error de validación al actualizar canción");
            model.addAttribute("bandas", bandaService.listarTodo());
            model.addAttribute("estados", Estado.values());
            model.addAttribute("titulo", "Editar Canción");
            return "canciones/form";
        }
        
        try {
            cancionService.actualizar(id, dto);
            log.info("Canción actualizada exitosamente: {}", id);
            redirectAttributes.addFlashAttribute("success", "Canción actualizada exitosamente");
            return "redirect:/canciones";
        } catch (Exception e) {
            log.error("Error al actualizar canción", e);
            model.addAttribute("error", "Error al actualizar la canción: " + e.getMessage());
            model.addAttribute("bandas", bandaService.listarTodo());
            model.addAttribute("estados", Estado.values());
            model.addAttribute("titulo", "Editar Canción");
            return "canciones/form";
        }
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            cancionService.eliminar(id);
            log.info("Canción eliminada: {}", id);
            redirectAttributes.addFlashAttribute("success", "Canción eliminada exitosamente");
        } catch (Exception e) {
            log.error("Error al eliminar canción", e);
            redirectAttributes.addFlashAttribute("error", "Error al eliminar la canción: " + e.getMessage());
        }
        return "redirect:/canciones";
    }
    
    @GetMapping("/banda/{idBanda}")
    public String listarPorBanda(@PathVariable Long idBanda, Model model, RedirectAttributes redirectAttributes) {
        try {
            var banda = bandaService.buscarXId(idBanda)
                .orElseThrow(() -> new RuntimeException("Banda no encontrada"));
            
            model.addAttribute("banda", banda);
            model.addAttribute("canciones", cancionService.findAll()
                .stream()
                .filter(c -> c.getIdBanda().equals(idBanda))
                .toList());
            model.addAttribute("titulo", "Canciones de " + banda.getNombre());
            return "canciones/lista-por-banda";
        } catch (Exception e) {
            log.error("Error al listar canciones por banda", e);
            redirectAttributes.addFlashAttribute("error", "Error al cargar las canciones");
            return "redirect:/bandas";
        }
    }
}