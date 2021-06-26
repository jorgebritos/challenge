package com.cliente.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "peliculaoserie")
public class PeliculaOSerie implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotEmpty
	private String titulo;
	@NotNull
	private Date fechaCreacion;
	@NotNull
	private int calificacion;

	private String imagen;

	@ManyToMany
	@JoinTable(name = "pos_personaje", joinColumns = { @JoinColumn(name = "fk_pos") }, inverseJoinColumns = {
			@JoinColumn(name = "fk_personaje") })
	private List<Personaje> personajes = new ArrayList<Personaje>();

	@ManyToMany
	@JoinTable(name = "pos_genero", joinColumns = { @JoinColumn(name = "fk_pos") }, inverseJoinColumns = {
			@JoinColumn(name = "fk_genero") })
	private List<Genero> generos = new ArrayList<Genero>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public int getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(int calificacion) {
		this.calificacion = calificacion;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public List<Personaje> getPersonajes() {
		return personajes;
	}

	public void setPersonajes(List<Personaje> personajes) {
		this.personajes = personajes;
	}

	public List<Genero> getGeneros() {
		return generos;
	}

	public void setGeneros(List<Genero> generos) {
		this.generos = generos;
	}

	@Override
	public String toString() {
		return "PeliculaOSerie [id=" + id + ", titulo=" + titulo + ", fechaCreacion=" + fechaCreacion
				+ ", calificacion=" + calificacion + ", imagen=" + imagen + ", personajes=" + personajes + ", generos="
				+ generos + "]";
	}

}
