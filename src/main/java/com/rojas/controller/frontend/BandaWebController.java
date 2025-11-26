package com.rojas.controller.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rojas.dto.BandaDTO;
import com.rojas.enums.Estado;
import com.rojas.exception.BandaNoEncontradaException;
import com.rojas.service.IBandaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/bandas")
@RequiredArgsConstructor
@Slf4j
public class BandaWebController {
    
    private final IBandaService bandaService;
    
    @GetMapping
    public String listar(Model model) {
        log.info("Listando bandas en vista web");
        model.addAttribute("bandas", bandaService.listarTodo());
        model.addAttribute("titulo", "Gestión de Bandas");
        return "bandas/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        log.info("Mostrando formulario de nueva banda");
        BandaDTO banda = new BandaDTO();
        banda.setEstado(Estado.ACTIVO); // Estado por defecto
        model.addAttribute("banda", banda);
        model.addAttribute("titulo", "Registrar Banda");
        model.addAttribute("estados", Estado.values());
        return "bandas/form";
    }

    @PostMapping
    public String guardar(
            @Valid @ModelAttribute("banda") BandaDTO dto,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model) {
        
        if (result.hasErrors()) {
            log.error("Error de validación al guardar banda");
            model.addAttribute("titulo", "Registrar Banda");
            model.addAttribute("estados", Estado.values());
            return "bandas/form";
        }
        
        try {
            bandaService.guardar(dto);
            log.info("Banda guardada exitosamente: {}", dto.getNombre());
            redirectAttributes.addFlashAttribute("success", "Banda registrada exitosamente");
            return "redirect:/bandas";
        } catch (Exception e) {
            log.error("Error al guardar banda", e);
            model.addAttribute("error", "Error al registrar la banda: " + e.getMessage());
            model.addAttribute("titulo", "Registrar Banda");
            model.addAttribute("estados", Estado.values());
            return "bandas/form";
        }
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            BandaDTO banda = bandaService.buscarXId(id)
                .orElseThrow(() -> new BandaNoEncontradaException("Banda no encontrada"));
            
            model.addAttribute("banda", banda);
            model.addAttribute("titulo", "Editar Banda");
            model.addAttribute("estados", Estado.values());
            return "bandas/form";
        } catch (BandaNoEncontradaException e) {
            log.error("Banda no encontrada: {}", id);
            redirectAttributes.addFlashAttribute("error", "Banda no encontrada");
            return "redirect:/bandas";
        }
    }

    @PostMapping("/editar/{id}")
    public String actualizar(
            @PathVariable Long id,
            @Valid @ModelAttribute("banda") BandaDTO dto,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model) {
        
        if (result.hasErrors()) {
            log.error("Error de validación al actualizar banda");
            model.addAttribute("titulo", "Editar Banda");
            model.addAttribute("estados", Estado.values());
            return "bandas/form";
        }
        
        try {
            bandaService.actualizar(id, dto);
            log.info("Banda actualizada exitosamente: {}", id);
            redirectAttributes.addFlashAttribute("success", "Banda actualizada exitosamente");
            return "redirect:/bandas";
        } catch (Exception e) {
            log.error("Error al actualizar banda", e);
            model.addAttribute("error", "Error al actualizar la banda: " + e.getMessage());
            model.addAttribute("titulo", "Editar Banda");
            model.addAttribute("estados", Estado.values());
            return "bandas/form";
        }
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            bandaService.eliminar(id);
            log.info("Banda eliminada: {}", id);
            redirectAttributes.addFlashAttribute("success", "Banda eliminada exitosamente");
        } catch (Exception e) {
            log.error("Error al eliminar banda", e);
            redirectAttributes.addFlashAttribute("error", "Error al eliminar la banda: " + e.getMessage());
        }
        return "redirect:/bandas";
    }

    @GetMapping("/{id}")
    public String verDetalle(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            BandaDTO banda = bandaService.buscarXId(id)
                .orElseThrow(() -> new BandaNoEncontradaException("Banda no encontrada"));
            
            model.addAttribute("banda", banda);
            model.addAttribute("titulo", "Detalle de Banda");
            return "bandas/detalle";
        } catch (BandaNoEncontradaException e) {
            log.error("Banda no encontrada: {}", id);
            redirectAttributes.addFlashAttribute("error", "Banda no encontrada");
            return "redirect:/bandas";
        }
    }
}