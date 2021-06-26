package com.cliente.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cliente.entidades.Genero;
import com.cliente.repositorios.GeneroRepositorio;

@Service
public class GeneroServicioImplement implements IGeneroServicio {

	@Autowired
	private GeneroRepositorio generoRepositorio;

	@Override
	public List<Genero> listarTodos() {
		return (List<Genero>) generoRepositorio.findAll();
	}

	@Override
	public void guardar(Genero genero) {
		generoRepositorio.save(genero);

	}

	@Override
	public Genero buscarId(Long id) {
		return generoRepositorio.findById(id).orElse(null);
	}

	@Override
	public void eliminar(Long id) {
		generoRepositorio.deleteById(id);

	}

}
