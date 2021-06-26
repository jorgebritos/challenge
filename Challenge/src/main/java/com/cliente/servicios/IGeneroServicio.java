package com.cliente.servicios;

import java.util.List;

import com.cliente.entidades.Genero;

public interface IGeneroServicio {

	public List<Genero> listarTodos();

	public void guardar(Genero genero);

	public Genero buscarId(Long id);

	public void eliminar(Long id);
}
