package ort.edu.ar.thp2024c1.fin;

public class Test {

	public static void main(String[] args) {
		Plataforma pla = new Plataforma("OrtStrim");
		
		darAltaUsuarios(pla, 10);
		
		darAltaJuegos(pla, 5);

		System.out.println("---------------------------------------");
		System.out.println("Venta de juegos");
		System.out.println("---------------------------------------");
		pla.venderJuego("email@3", "juego4");// ok
		pla.venderJuego("email@2", "juego5");// ok
		pla.venderJuego("email@2", "juego7");// Juego no existe
		pla.venderJuego("email@3", "juego4");// Juego duplicado
		pla.venderJuego("email@3", "juego1");// ok

		System.out.println("---------------------------------------");
		System.out.println("Registración de amistades");
		System.out.println("---------------------------------------");
		pla.registrarAmistad("email@2", "email@2");// Falla
		pla.registrarAmistad("email@2", "email@3");// ok
		pla.registrarAmistad("email@3", "email@2");// Falla
		pla.registrarAmistad("email@2", "email@3");// Falla
		pla.registrarAmistad("email@2", "email@4");// ok
		pla.prestarJuego("email@2", "email@3", "juego2");// Juego2 no pertenece a email@2
		pla.jugar("email@2", "juego5");

		System.out.println("---------------------------------------");
		System.out.println("Préstamo de juegos");
		System.out.println("---------------------------------------");
		pla.prestarJuego("email@2", "email@3", "juego5");// OK
		pla.prestarJuego("email@2", "email@4", "juego5");// Juego5 ya no pertenece a email@2
		pla.prestarJuego("email@3", "email@2", "juego5");// email@3 no es dueño de juego5

		pla.venderJuego("email@2", "juego5");		
		System.out.println("---------------------------------------");
		System.out.println("Jugar a los juegos");
		System.out.println("---------------------------------------");
		pla.jugar("email@1", "juego2");
		pla.jugar("email@3", "juego5");
		pla.jugar("email@3", "juego1");
		pla.jugar("email@2", "juego5");
		
		//Eliminar amigo
		System.out.println("---------------------------------------");
		System.out.println("Desvincular amistad: ");
		System.out.println(pla.desvincularAmistad("email@2", "email@3"));
		System.out.println("---------------------------------------");

		//Devolver juego
		System.out.println("---------------------------------------");
		System.out.println("Devolver juego: ");
		System.out.println(pla.devolverJuegoAsuPropietario("email@2", "juego5"));
		System.out.println("---------------------------------------");

	}

	private static void darAltaUsuarios(Plataforma pla, int cantidad) {
		System.out.println("---------------------------------------");
		System.out.println("Generación de usuarios en la plataforma.");
		System.out.println("---------------------------------------");

		for (int i = 1; i <= cantidad; i++) {
			pla.altaUsuario("email@" + i, "nombre usuario" + i);
		}
		pla.altaUsuario("email@2", "nombre usuario2");
	}

	private static void darAltaJuegos(Plataforma pla, int cantidad) {
		System.out.println("---------------------------------------");
		System.out.println("Generación de juegos en la plataforma.");
		System.out.println("---------------------------------------");
		for (int i = 1; i <= cantidad; i++) {
			pla.altaJuego("juego" + i, i * 1000, Genero.DEPORTE);
		}
		pla.altaJuego("juego2", 1000, Genero.DEPORTE);
	}

}