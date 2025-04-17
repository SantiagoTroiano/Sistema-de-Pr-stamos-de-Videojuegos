package ort.edu.ar.thp2024c1.fin;

public class Prestamo {

	private Usuario usuario;
	private Juego juego;

	public Prestamo(Usuario usuario, Juego juego) {
		this.usuario = usuario;
		this.juego = juego;
	}

	public boolean mismoJuego(Juego juego) {
		return this.juego.equals(juego);
	}
	public Usuario getUsuario() {
		return this.usuario;
	}
	public Juego getJuego() {
		return this.juego;
	}

}