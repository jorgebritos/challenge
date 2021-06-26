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

import com.cliente.entidades.Genero;
import com.cliente.servicios.IGeneroServicio;

@Controller
@RequestMapping("/genero")
public class GeneroControlador {

	@Autowired
	private IGeneroServicio generoServicio;

	@GetMapping("/")
	public String listarGeneros(Model model) {
		List<Genero> listadoGeneros = generoServicio.listarTodos();

		model.addAttribute("titulo", "Listado de Generos");
		model.addAttribute("genero", listadoGeneros);
		return "/views/genero/listar";
	}

	@GetMapping("/create")
	public String crear(Model model) {

		Genero genero = new Genero();

		model.addAttribute("titulo", "Formulario: Nuevo Genero");
		model.addAttribute("genero", genero);

		return "views/genero/frmCrear";
	}

	@PostMapping("/save")
	public String guardar(@Valid @ModelAttribute Genero genero, BindingResult result, Model model,
			@RequestParam("file") MultipartFile imagen, RedirectAttributes attribute) {

		if (result.hasErrors()) {

			model.addAttribute("titulo", "Formulario: Nuevo Genero");
			model.addAttribute("genero", genero);

			System.out.print("Existieron errores en el formulario");
			return "/views/genero/frmCrear";
		}

		if (!imagen.isEmpty()) {

			String rutaAbsoluta = "C://Genero//Recursos";

			try {
				byte[] bytesImg = imagen.getBytes();
				Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
				Files.write(rutaCompleta, bytesImg);

				genero.setImagen(imagen.getOriginalFilename());
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		generoServicio.guardar(genero);
		attribute.addFlashAttribute("success", "Genero Guardado con Exito");

		return "redirect:/genero/";
	}

	@GetMapping("/details/{id}")
	public String detalleGenero(@PathVariable("id") Long idGenero, Model model, RedirectAttributes attribute) {

		Genero genero = null;

		if (idGenero > 0) {
			genero = generoServicio.buscarId(idGenero);

			if (genero == null) {
				attribute.addFlashAttribute("error", "Error: El ID del Genero no existe");
				return "redirect:/views/genero/";
			}
		} else {
			System.out.println("Error: Error con el ID del Genero");
			attribute.addFlashAttribute("error", "Error: Error con el ID del Genero");
			return "redirect:/views/genero/";
		}

		model.addAttribute("titulo", "Detalle del Genero: " + genero.getNombre());
		model.addAttribute("genero", genero);

		return "/views/genero/detalleGenero";
	}

	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") Long idGenero, Model model, RedirectAttributes attribute) {

		Genero genero = null;

		if (idGenero > 0) {
			genero = generoServicio.buscarId(idGenero);

			if (genero == null) {
				attribute.addFlashAttribute("error", "Error: El ID del Genero no existe");
				return "redirect:/views/genero/";
			}
		} else {
			System.out.println("Error: Error con el ID del Genero");
			attribute.addFlashAttribute("error", "Error: Error con el ID del Genero");
			return "redirect:/views/genero/";
		}

		model.addAttribute("titulo", "Formulario: Editar Genero");
		model.addAttribute("genero", genero);

		return "/views/genero/frmCrear";
	}

	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") Long idGenero, RedirectAttributes attribute) {

		Genero genero = null;

		if (idGenero > 0) {
			genero = generoServicio.buscarId(idGenero);

			if (genero == null) {
				attribute.addFlashAttribute("error", "Error: El ID Del Genero No Existe");
				return "redirect:/views/genero/";
			}
		} else {
			attribute.addFlashAttribute("error", "Error: Error Con El ID Del Genero");
			return "redirect:/views/genero/";
		}

		generoServicio.eliminar(idGenero);
		attribute.addAttribute("warning", "Registro Eliminado con Éxito");

		return "redirect:/views/genero/";
	}
}
