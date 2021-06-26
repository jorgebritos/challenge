package com.cliente.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cliente.entidades.PeliculaOSerie;
import com.cliente.repositorios.PeliculaOSerieRepositorio;

@Service
public class PeliculaOSerieServicioImplement implements IPeliculaOSerieServicio {

	@Autowired
	private PeliculaOSerieRepositorio posRepositorio;

	@Override
	public List<PeliculaOSerie> listarTodos() {
		return (List<PeliculaOSerie>) posRepositorio.findAll();
	}

	@Override
	public void guardar(PeliculaOSerie pos) {
		posRepositorio.save(pos);

	}

	@Override
	public PeliculaOSerie buscarId(Long id) {
		return posRepositorio.findById(id).orElse(null);
	}

	@Override
	public void eliminar(Long id) {
		posRepositorio.deleteById(id);

	}

}
