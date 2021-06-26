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

import com.cliente.entidades.PeliculaOSerie;
import com.cliente.servicios.IPeliculaOSerieServicio;

@Controller
@RequestMapping("/movies")
public class PeliculaOSerieControlador {

	@Autowired
	private IPeliculaOSerieServicio peliculaoserieServicio;

	@GetMapping("/")
	public String listarPersonajes(Model model) {
		List<PeliculaOSerie> listadoPeliculaOSerie = peliculaoserieServicio.listarTodos();

		model.addAttribute("titulo", "Listado de Peliculas/Series");
		model.addAttribute("peliculaoserie", listadoPeliculaOSerie);
		return "/views/movies/listar";
	}

	@GetMapping("/create")
	public String crear(Model model) {

		PeliculaOSerie peliculaoserie = new PeliculaOSerie();

		model.addAttribute("titulo", "Formulario: Nueva Pelicula/Serie");
		model.addAttribute("peliculaoserie", peliculaoserie);

		return "views/movies/frmCrear";
	}

	@PostMapping("/save")
	public String guardar(@Valid @ModelAttribute PeliculaOSerie peliculaoserie, BindingResult result, Model model,

			@RequestParam("file") MultipartFile imagen, RedirectAttributes attribute) {

		if (result.hasErrors()) {

			model.addAttribute("titulo", "Formulario: Nueva Pelicula/Serie");
			model.addAttribute("peliculaoserie", peliculaoserie);

			System.out.print("Existieron errores en el formulario");
			return "/views/movies/frmCrear";
		}

		if (!imagen.isEmpty()) {

			String rutaAbsoluta = "C://POS//Recursos";

			try {
				byte[] bytesImg = imagen.getBytes();
				Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
				Files.write(rutaCompleta, bytesImg);

				peliculaoserie.setImagen(imagen.getOriginalFilename());
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		peliculaoserieServicio.guardar(peliculaoserie);
		attribute.addFlashAttribute("success", "Pelicula/Serie Guardada con Exito");

		return "redirect:/movies/";
	}

	@GetMapping("/details/{id}")
	public String detallePeliculaOSerie(@PathVariable("id") Long idPeliculaOSerie, Model model,
			RedirectAttributes attribute) {

		PeliculaOSerie peliculaoserie = null;

		if (idPeliculaOSerie > 0) {
			peliculaoserie = peliculaoserieServicio.buscarId(idPeliculaOSerie);

			if (peliculaoserie == null) {
				attribute.addFlashAttribute("error", "Error: El ID de la Pelicula/Serie no existe");
				return "redirect:/views/movies/";
			}
		} else {
			System.out.println("Error: Error con el ID de la Pelicula/Serie");
			attribute.addFlashAttribute("error", "Error: Error con el ID de la Pelicula/Serie");
			return "redirect:/views/movies/";
		}

		model.addAttribute("titulo", "Detalle de la Pelicula/Serie: " + peliculaoserie.getTitulo());
		model.addAttribute("peliculaoserie", peliculaoserie);

		return "views/movies/detallePOS";
	}

	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") Long idPeliculaOSerie, Model model, RedirectAttributes attribute) {

		PeliculaOSerie peliculaoserie = null;

		if (idPeliculaOSerie > 0) {
			peliculaoserie = peliculaoserieServicio.buscarId(idPeliculaOSerie);

			if (peliculaoserie == null) {
				attribute.addFlashAttribute("error", "Error: El ID de la Pelicula/Serie no existe");
				return "redirect:/views/movies/";
			}
		} else {
			System.out.println("Error: Error con el ID de la Pelicula/Serie");
			attribute.addFlashAttribute("error", "Error: Error con el ID de la Pelicula/Serie");
			return "redirect:/views/movies/";
		}

		model.addAttribute("titulo", "Formulario: Editar Pelicula/Serie");
		model.addAttribute("peliculaoserie", peliculaoserie);

		return "/views/movies/frmCrear";
	}

	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") Long idPeliculaOSerie, RedirectAttributes attribute) {

		PeliculaOSerie peliculaoserie = null;

		if (idPeliculaOSerie > 0) {
			peliculaoserie = peliculaoserieServicio.buscarId(idPeliculaOSerie);

			if (peliculaoserie == null) {
				attribute.addFlashAttribute("error", "Error: El ID de la Pelicula/Serie No Existe");
				return "redirect:/movies/";
			}
		} else {
			attribute.addFlashAttribute("error", "Error: Error Con El ID de la Pelicula/Serie");
			return "redirect:/movies/";
		}

		peliculaoserieServicio.eliminar(idPeliculaOSerie);
		attribute.addAttribute("warning", "Registro Eliminado con Éxito");

		return "redirect:/movies/";
	}

}