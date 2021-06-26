package com.cliente.repositorios;


import org.springframework.data.repository.CrudRepository;

import com.cliente.entidades.PeliculaOSerie;

public interface PeliculaOSerieRepositorio extends CrudRepository<PeliculaOSerie, Long> {
	
}
