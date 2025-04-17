package ort.edu.ar.thp2024c1.fin;

import java.util.ArrayList;

public class Usuario {

	private String email;
	private String nombre;
	private ArrayList<Juego> juegosComprados;
	private ArrayList<Usuario> amigos;
	private ArrayList<Prestamo> prestamosRealizados;
	private ArrayList<Prestamo> prestamosRecibidos;
	private ArrayList<Prestamo>historialDePrestamosRecibidos;//Aca van todos los prestamos que recibio el usuario y que ya devolvio.
	private ArrayList<Prestamo>historialDePrestamosHechos;//Aca van todos los prestamos que el usuario hizo y que le devolvieron.

	public Usuario(String email, String nombre) {
		this.email = email;
		this.nombre = nombre;
		this.juegosComprados = new ArrayList<>();
		this.amigos = new ArrayList<>();
		this.prestamosRealizados = new ArrayList<>();
		this.prestamosRecibidos = new ArrayList<>();
		this.historialDePrestamosHechos = new ArrayList<>();
		this.historialDePrestamosRecibidos = new ArrayList<>();
	}

	private void setEmail(String email) {
		this.email = email;
	}

	private void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean generarAmistad(Usuario amigo) {
		boolean amistadPrevia = yaEsAmigoDe(amigo);
		if (!amistadPrevia) {
			this.registrarAmigo(amigo);
			amigo.registrarAmigo(this);
		}
		return !amistadPrevia;
	}

	public boolean comprarJuego(Juego juego) {
		// debe buscar en los suyos (comprados o que haya prestado a un amigo)
		boolean encontre = this.buscarJuego(juego, this.prestamosRealizados);
		if (!encontre)
			this.juegosComprados.add(juego);
		// si compro es porque no lo encontré de antes
		return !encontre;
	}

	public boolean yaEsAmigoDe(Usuario amigo) {
		int i = 0;
		while (i < this.amigos.size() && !this.amigos.get(i).equals(amigo))
			i++;
		return i < this.amigos.size();
	}

	private void registrarAmigo(Usuario amigo) {
		this.amigos.add(amigo);
	}

	private boolean buscarJuego(Juego juego, ArrayList<Prestamo> prestamos) {
		return tieneComprado(juego) || tieneEnPrestamo(juego, prestamos);
	}

	private boolean tieneComprado(Juego juego) {
		int i = 0;
		while (i < this.juegosComprados.size() && !this.juegosComprados.get(i).equals(juego))
			i++;
		return i < this.juegosComprados.size();
	}

	private boolean tieneEnPrestamo(Juego juego, ArrayList<Prestamo> prestamos) {
		int i = 0;
		while (i < prestamos.size() && !prestamos.get(i).mismoJuego(juego))
			i++;
		return i < prestamos.size();
	}
	

	public ResultadoPrestamo generarPrestamo(Usuario amigo, Juego juego) {
		ResultadoPrestamo resu = ResultadoPrestamo.AMIGO_INEXISTENTE;
		if (yaEsAmigoDe(amigo))
			if (tieneComprado(juego)) {
				this.altaPrestamo(amigo, juego, this.prestamosRealizados);
				amigo.altaPrestamo(this, juego, amigo.prestamosRecibidos);
				this.juegosComprados.remove(juego);
				resu = ResultadoPrestamo.PRESTAMO_EXITOSO;
			} else
				resu = ResultadoPrestamo.JUEGO_NO_DISPONIBLE;
		return resu;
	}

	public boolean puedeJugar(Juego juego) {
		return this.buscarJuego(juego, this.prestamosRecibidos);
	}

	private void altaPrestamo(Usuario usuario, Juego juego, ArrayList<Prestamo> prestamos) {
		prestamos.add(new Prestamo(usuario, juego));
	}

	public boolean mismoEmail(String email) {
		return this.email.equals(email);
	}
	public void recibirJuego(Prestamo prestamo) {
		historialDePrestamosHechos.add(prestamo);
		prestamosRealizados.remove(prestamo);
		juegosComprados.add(prestamo.getJuego());
	}
	public void devolverJuego(Prestamo prestamo) {
		historialDePrestamosRecibidos.add(prestamo);
		prestamosRecibidos.remove(prestamo);
	}
	public void eliminarAmigo(Usuario amigo) {
		amigos.remove(amigo);
	}
	public Prestamo tienePrestado(Juego juego) {
		int i = 0;
		Prestamo prestamo = null;
		
		while (i < prestamosRealizados.size() && !prestamosRealizados.get(i).mismoJuego(juego))
			i++;
		if (i < prestamosRealizados.size()) {
			prestamo = prestamosRealizados.get(i);
		}
		return prestamo;
	}
	public Prestamo tienePrestamosCon(Usuario amigo) {
		int i = 0;
		Prestamo prestamo = null;
		
		while (i < prestamosRecibidos.size() && amigo != prestamosRecibidos.get(i).getUsuario())
			i++;
		if (i < prestamosRecibidos.size()) {
			prestamo = prestamosRecibidos.get(i);
		}
		return prestamo;
	}

	public boolean hayPrestamosActivosEntreAmigos(Usuario amigo) {
		boolean hay = false;
		
		if (tienePrestamosCon(amigo) != null) {
			hay = true;
		}
		
		return hay;
	}
}
