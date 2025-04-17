package ort.edu.ar.thp2024c1.fin;

public class Juego {

	private String nombre;
	private double precioVenta;
	private Genero genero;

	public Juego(String nombre, double precioVenta, Genero genero) {
		setNombre(nombre);
		setPrecioVenta(precioVenta);
		setGenero(genero);
	}

	private void setNombre(String nombre) {
		this.nombre = nombre;
	}

	private void setPrecioVenta(double precioVenta) {
		if (precioVenta < 0)
			this.precioVenta = 0;
		else
			this.precioVenta = precioVenta;
	}

	private void setGenero(Genero genero) {
		this.genero = genero;
	}

	public boolean mismoNombre(String nombre) {
		return this.nombre.equals(nombre);
	}

}