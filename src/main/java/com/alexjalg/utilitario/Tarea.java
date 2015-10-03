package com.alexjalg.utilitario;

public class Tarea {
	private String fechaRegistro;
	private String id;
	private String nombreServidor;
	private String nombreTarea;

	public Tarea(
			String fechaRegistro, 
			String id, 
			String nombreServidor,
			String nombreTarea) {
		this.fechaRegistro = fechaRegistro;
		this.id = id;
		this.nombreServidor = nombreServidor;
		this.nombreTarea = nombreTarea;
	}

	public String getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombreServidor() {
		return nombreServidor;
	}

	public void setNombreServidor(String nombreServidor) {
		this.nombreServidor = nombreServidor;
	}

	public String getNombreTarea() {
		return nombreTarea;
	}

	public void setNombreTarea(String nombreTarea) {
		this.nombreTarea = nombreTarea;
	}

}
