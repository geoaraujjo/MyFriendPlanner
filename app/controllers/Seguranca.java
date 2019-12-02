package controllers;

import play.mvc.Before;
import play.mvc.Controller;

public class Seguranca extends Controller {
	
	@Before(unless = {"Usuarios.form", "Usuarios.salvar"})
	static void verificar(){
		if (session.contains("emailUsuario") == false){
			Login.form();
		}
		
	}
	
	
}
