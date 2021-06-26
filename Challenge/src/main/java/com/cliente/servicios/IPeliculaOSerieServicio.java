package com.cliente.servicios;

import java.util.List;

import com.cliente.entidades.PeliculaOSerie;

public interface IPeliculaOSerieServicio {

	public List<PeliculaOSerie> listarTodos();

	public void guardar(PeliculaOSerie peliculaoserie);

	public PeliculaOSerie buscarId(Long id);

	public void eliminar(Long id);
}
