package ort.edu.ar.thp2024c1.fin;

import java.util.ArrayList;

public class Plataforma {

	private String nombre;
	private ArrayList<Juego> catalogoJuegos;
	private ArrayList<Usuario> usuarios;

	public Plataforma(String nombre) {
		setNombre(nombre);
		this.catalogoJuegos = new ArrayList<>();
		this.usuarios = new ArrayList<>();
	}

	private void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void registrarAmistad(String emailUsuario, String emailAmigo) {
		if (!emailUsuario.equalsIgnoreCase(emailAmigo)) {
			Usuario usuario = buscarUsuario(emailUsuario);
			if (usuario != null) {
				Usuario amigo = buscarUsuario(emailAmigo);
				if (amigo != null)
					if (usuario.generarAmistad(amigo))
						System.out.println(emailUsuario + " y " + emailAmigo + " ahora son amigos!!");
					else
						System.out.println(emailAmigo + " previamente registrado como amigo de " + emailUsuario);
				else
					System.out.println(emailAmigo + " no registrado en plataforma.");
			} else
				System.out.println(emailUsuario + " no registrado en plataforma.");
		} else
			System.out.println("El usuario y el amigo no pueden ser iguales.");
	}

	public void venderJuego(String email, String nombreJuego) {
		Usuario usuario;
		Juego juego;
		usuario = buscarUsuario(email);
		if (usuario != null) {
			juego = buscarJuego(nombreJuego);
			if (juego != null)
				if (usuario.comprarJuego(juego))
					System.out.println(email + " Compra exitosa de " + nombreJuego);
				else
					System.out.println("El usuario " + email + " ya tenia el juego " + nombreJuego);
			else
				System.out.println("Juego " + nombreJuego + " no encontrado en catálogo");
		} else
			System.out.println("Usuario " + email + " no registrado");
	}

	public void prestarJuego(String emailUsuario, String emailAmigo, String nombreJuego) {
		ResultadoPrestamo resultado;
		Usuario usuario = buscarUsuario(emailUsuario);
		if (usuario != null) {
			Usuario amigo = buscarUsuario(emailAmigo);
			if (amigo != null) {
				Juego juego = buscarJuego(nombreJuego);
				if (juego != null)
					resultado = usuario.generarPrestamo(amigo, juego);
				else
					resultado = ResultadoPrestamo.JUEGO_INEXISTENTE;
			} else
				resultado = ResultadoPrestamo.RECEPTOR_INEXISTENTE;

		} else
			resultado = ResultadoPrestamo.PRESTADOR_INEXISTENTE;
		System.out.println(resultado);
	}

	public void jugar(String email, String nombreJuego) {
		Usuario usuario = buscarUsuario(email);
		if (usuario != null) {
			Juego juego = buscarJuego(nombreJuego);
			if (juego != null)
				if (usuario.puedeJugar(juego))
					System.out.println(email + " pudo jugar a " + nombreJuego);
				else
					System.out.println(email + " no dispone del juego " + nombreJuego);
			else
				System.out.println(nombreJuego + " no se encontro en el catálogo.");
		} else
			System.out.println(email + " no registrado en la plataforma.");

	}

	private Usuario buscarUsuario(String email) {
		Usuario retornar = null;
		int i = 0;
		while (i < this.usuarios.size() && !this.usuarios.get(i).mismoEmail(email))
			i++;
		if (i < this.usuarios.size())
			retornar = this.usuarios.get(i);
		return retornar;
	}

	private Juego buscarJuego(String nombre) {
		Juego retornar = null;
		int i = 0;
		while (i < this.catalogoJuegos.size() && !this.catalogoJuegos.get(i).mismoNombre(nombre))
			i++;
		if (i < this.catalogoJuegos.size())
			retornar = this.catalogoJuegos.get(i);
		return retornar;
	}

	public void altaUsuario(String email, String nombre) {
		Usuario usuario = buscarUsuario(email);
		if (usuario == null) {
			this.usuarios.add(new Usuario(email, nombre));
			System.out.println("Usuario " + email + " registrado correctamente.");
		} else
			System.out.println("Usuario " + email + " previamente registrado en la plataforma");
	}

	public void altaJuego(String nombre, double precio, Genero genero) {
		Juego juego = buscarJuego(nombre);
		if (juego == null) {
			this.catalogoJuegos.add(new Juego(nombre, precio, genero));
			System.out.println("Juego " + nombre + " registrado correctamente.");
		} else
			System.out.println("Juego " + nombre + " previamente registrado en la plataforma");
	}
	public ResultadoPrestamo devolverJuegoAsuPropietario(String email, String nombreJuego) {
		ResultadoPrestamo resultado = ResultadoPrestamo.DEVOLUCION_OK;
		Usuario usuarioPrestamista = null;
		Usuario usuarioReceptor = null;
		Juego juego = null;
		Prestamo prestamo = null;
		
		usuarioPrestamista = buscarUsuario(email);
		juego = buscarJuego(nombreJuego);
		prestamo = usuarioPrestamista.tienePrestado(juego);
		
		if (usuarioPrestamista == null) {
			resultado = ResultadoPrestamo.USUARIO_INEXISTENTE;			
		}else if (juego == null) {
			resultado = ResultadoPrestamo.JUEGO_INEXISTENTE;
		}else if (prestamo == null) {
			resultado = ResultadoPrestamo.NO_EXISTE_PRESTAMO;
		}else {
			usuarioReceptor = prestamo.getUsuario();
			usuarioPrestamista.recibirJuego(prestamo);
			usuarioReceptor.devolverJuego(prestamo);
			resultado = ResultadoPrestamo.DEVOLUCION_OK;
		}
		return resultado;
	}
	
	public ResultadoPrestamo desvincularAmistad(String mailUsuario, String mailAmigo) {
		Usuario usuario = null;
		Usuario amigo = null;
		ResultadoPrestamo resultado = ResultadoPrestamo.USUARIO_INEXISTENTE;
		
		usuario = buscarUsuario(mailUsuario);
		amigo = buscarUsuario(mailAmigo);
		
		if (usuario == null) {
		}else if (!usuario.yaEsAmigoDe(amigo)) {
			resultado = ResultadoPrestamo.AMIGO_INEXISTENTE;
		}else if (usuario.hayPrestamosActivosEntreAmigos(amigo)) {
			resultado = ResultadoPrestamo.DEBE_DEVOLVER_JUEGO;
		} else {
			usuario.eliminarAmigo(amigo);
			resultado = ResultadoPrestamo.AMIGO_ELIMINADO;
			
		}
		return resultado;
	}

}