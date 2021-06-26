package com.cliente.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cliente.entidades.Personaje;
import com.cliente.repositorios.PersonajeRepositorio;

@Service
public class PersonajeServicioImplement implements IPersonajeServicio {

	@Autowired
	private PersonajeRepositorio personajeRepositorio;

	@Override
	public List<Personaje> listarTodos() {
		return (List<Personaje>) personajeRepositorio.findAll();
	}

	@Override
	public void guardar(Personaje personaje) {
		personajeRepositorio.save(personaje);

	}

	@Override
	public Personaje buscarId(Long id) {
		return personajeRepositorio.findById(id).orElse(null);
	}

	@Override
	public void eliminar(Long id) {
		personajeRepositorio.deleteById(id);

	}

}
