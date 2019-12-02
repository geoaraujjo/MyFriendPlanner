package controllers;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import enums.Genero;
import models.Compromisso;
import models.Tarefa;
import models.Usuario;
import play.cache.Cache;
import play.data.validation.Valid;
import play.mvc.Controller;
import play.mvc.With;

@With(Seguranca.class)

public class Usuarios extends Controller {

	
	public static void form() {
		List<Genero> generos = Arrays.asList(Genero.values());
		render(generos);
		
		Usuario usuario = (Usuario)Cache.get("usuario");
		Cache.clear();
		render(usuario);
	}

	public static void salvar(Usuario usuario, String senha, File foto)  {	
		
				if( senha.equals("") == false || usuario.id == null) {
					usuario.senha = senha;
			
				}
		
				if(senha.equals("") && usuario.id != null) {
					
					validation.removeErrors("usuario.senha");
					if(validation.errors().size() == 1) {
						validation.clear();
					}
				}
				
				if(validation.hasErrors()) {
				for(play.data.validation.Error error: validation.errors()) {
					System.out.println(error);
				}
				params.flash();
				validation.keep();
				flash.error("Falha ao salvar usuário");
				Cache.set("usuario", usuario);
				form();
			} 
				
				usuario.nomeFoto = foto.getName();
				usuario.save();
				
				new File("planner/uploads/" + usuario.id).mkdirs();
				foto.renameTo(new File("planner/uploads/" + usuario.id  + "/" + foto.getName()));

				flash.success("Usuário salvo com sucesso");
				Login.logar(usuario.email, usuario.senha);
						
			
			
			
			

				
			
			
			
			}
					
	
		
	public static void listar() {
		List<Usuario> usuarios = Usuario.findAll();
		render(usuarios);
	}
	
	public static void editar(Long id) {
		Usuario usuario = Usuario.findById(id);
		List<Genero> generos = Arrays.asList(Genero.values());
		render("Usuarios/form.html",usuario, generos);
	}

	public static void deletar(Long id) {
		Usuario usuario = Usuario.findById(id);
		usuario.delete();
		flash.success("Usuário removido com sucesso");
		listar();
	}
	
	public static void calendar() {
		render();
	}
	
	
}
