package com.cliente.servicios;

import java.util.List;

import com.cliente.entidades.Personaje;

public interface IPersonajeServicio {

	public List<Personaje> listarTodos();

	public void guardar(Personaje personaje);

	public Personaje buscarId(Long id);

	public void eliminar(Long id);
}
