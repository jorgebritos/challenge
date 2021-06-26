package com.cliente.controladores;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cliente.entidades.Personaje;
import com.cliente.servicios.IPersonajeServicio;

@Controller
@RequestMapping("/characters")
public class PersonajeControlador {

	@Autowired
	private IPersonajeServicio personajeServicio;

	@GetMapping("/")
	public String listarPersonajes(Model model) {
		List<Personaje> listadoPersonajes = personajeServicio.listarTodos();

		model.addAttribute("titulo", "Listado de Personajes");
		model.addAttribute("personajes", listadoPersonajes);
		return "/views/personajes/listar";
	}

	@GetMapping("/create")
	public String crear(Model model) {

		Personaje personaje = new Personaje();

		model.addAttribute("titulo", "Formulario: Nuevo Personaje");
		model.addAttribute("personaje", personaje);

		return "views/personajes/frmCrear";
	}

	@PostMapping("/save")
	public String guardar(@Valid @ModelAttribute Personaje personaje, BindingResult result, Model model,
			@RequestParam("file") MultipartFile imagen, RedirectAttributes attribute) {

		if (result.hasErrors()) {

			model.addAttribute("titulo", "Formulario: Nuevo Personaje");
			model.addAttribute("personaje", personaje);

			System.out.print("Existieron errores en el formulario");
			return "/views/personajes/frmCrear";
		}

		if (!imagen.isEmpty()) {

			String rutaAbsoluta = "C://Personajes//Recursos";

			try {
				byte[] bytesImg = imagen.getBytes();
				Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
				Files.write(rutaCompleta, bytesImg);

				personaje.setImagen(imagen.getOriginalFilename());
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		personajeServicio.guardar(personaje);
		attribute.addFlashAttribute("success", "Personaje Guardado con Exito");

		return "redirect:/characters/";
	}

	@GetMapping("/details/{id}")
	public String detallePersonaje(@PathVariable("id") Long idPersonaje, Model model, RedirectAttributes attribute) {

		Personaje personaje = null;

		if (idPersonaje > 0) {
			personaje = personajeServicio.buscarId(idPersonaje);

			if (personaje == null) {
				attribute.addFlashAttribute("error", "Error: El ID del Personaje no existe");
				return "redirect:/views/personajes/";
			}
		} else {
			System.out.println("Error: Error con el ID del Personaje");
			attribute.addFlashAttribute("error", "Error: Error con el ID del Personaje");
			return "redirect:/views/personajes/";
		}

		model.addAttribute("titulo", "Detalle del Personaje: " + personaje.getNombre());
		model.addAttribute("personaje", personaje);

		return "views/personajes/detallePersonaje";
	}

	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") Long idPersonaje, Model model, RedirectAttributes attribute) {

		Personaje personaje = null;

		if (idPersonaje > 0) {
			personaje = personajeServicio.buscarId(idPersonaje);

			if (personaje == null) {
				attribute.addFlashAttribute("error", "Error: El ID del Personaje no existe");
				return "redirect:/views/personajes/";
			}
		} else {
			System.out.println("Error: Error con el ID del Personaje");
			attribute.addFlashAttribute("error", "Error: Error con el ID del Personaje");
			return "redirect:/views/personajes/";
		}

		model.addAttribute("titulo", "Formulario: Editar Personaje");
		model.addAttribute("personaje", personaje);

		return "/views/personajes/frmCrear";
	}

	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") Long idPersonaje, RedirectAttributes attribute) {

		Personaje personaje = null;

		if (idPersonaje > 0) {
			personaje = personajeServicio.buscarId(idPersonaje);

			if (personaje == null) {
				attribute.addFlashAttribute("error", "Error: El ID Del Personaje No Existe");
				return "redirect:/characters/";
			}
		} else {
			attribute.addFlashAttribute("error", "Error: Error Con El ID Del Personaje");
			return "redirect:/characters/";
		}

		personajeServicio.eliminar(idPersonaje);
		attribute.addAttribute("warning", "Registro Eliminado con Éxito");

		return "redirect:/characters/";
	}
}
