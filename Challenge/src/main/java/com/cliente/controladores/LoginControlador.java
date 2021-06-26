package com.cliente.controladores;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginControlador {

	@GetMapping({ "/auth/login", "/login" })
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model, Principal principal,
			RedirectAttributes attribute) {

		if (error != null) {
			model.addAttribute("error", "ERROR DE ACCESO: Usuario y/o Contraseña son Incorrectos");
		}

		if (principal != null) {
			attribute.addFlashAttribute("warning", "ATENCIÓN: Ud. Ya Ha Iniciado Sesión Previamente");
			return "redirect:/index";
		}

		if (logout != null) {
			attribute.addFlashAttribute("success", "ATENCIÓN: Ha Cerrado Sesión Con Éxito");
			return "redirect:/auth/login";
		}

		return "login";
	}

}
