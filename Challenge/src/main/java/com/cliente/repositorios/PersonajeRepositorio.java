package com.cliente.repositorios;

import org.springframework.data.repository.CrudRepository;

import com.cliente.entidades.Personaje;

public interface PersonajeRepositorio extends CrudRepository<Personaje, Long> {

}
