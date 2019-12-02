package controllers;

import models.Usuario;
import play.mvc.Controller;


public class Login extends Controller {

	public static void form() {
		render();
	}
	
	public static void logout() {
		session.clear();
		render("Login/form.html");
	}
	public static void logar(String email, String senha) {
		
		Usuario usuario = Usuario.find("email = ? and senha = ?", email, senha).first();
		
		if(usuario == null) {
			flash.error("Email e/ou senha inválidos");
			form();
		}else {
			session.put("nomeUsuario", usuario.nome);
			session.put("emailUsuario", usuario.email);
			session.put("IDUsuario", usuario.id);
			session.put("nascUsuario", usuario.nasc);
			session.put("generoUsuario",usuario.genero);
			session.put("fotoUsuario", usuario.nomeFoto);
			render("Application/index.html");
		}
		
	}
}
