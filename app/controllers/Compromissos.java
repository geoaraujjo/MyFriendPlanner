package controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import enums.Status;
import models.Compromisso;
import models.Tarefa;
import models.Usuario;
import play.cache.Cache;
import play.data.validation.Valid;
import play.mvc.Controller;
import play.mvc.With;

@With(Seguranca.class)


public class Compromissos extends Controller {

	
	public static void form() {
		Compromisso compromisso = (Compromisso) Cache.get("Compromisso");
		Cache.clear();
		render(compromisso);
	}

	public static void salvar(@Valid Compromisso compromisso) {
		
		if(validation.hasErrors()) {
			for(play.data.validation.Error error: validation.errors()) {
				System.out.println(error);
			}

			params.flash();
			validation.keep();
			flash.error("Falha ao salvar compromisso");
			Cache.set("Compromisso", compromisso);
			form();
		} else {
			compromisso.save();
			Usuario usuario = Usuario.findById(Long.parseLong(session.get("IDUsuario")));
			usuario.compromissos.add(compromisso);
			usuario.save();		
			flash.success("Salvo com sucesso");
			listar();
			
		}
		
	}
	
	public static void listar() {
		Usuario usuario = Usuario.findById(Long.parseLong(session.get("IDUsuario")));
		List<Compromisso> compromissosNaoConcluidos = usuario.compromissos.stream()                // convert list to stream
                .filter(compromisso -> compromisso.status.equals(Status.NAO_CONCLUIDO))     // we dont like mkyong
                .collect(Collectors.toList());
		List<Compromisso> compromissosConcluidos = usuario.compromissos.stream()                // convert list to stream
                .filter(compromisso -> compromisso.status.equals(Status.CONCLUIDO))     // we dont like mkyong
                .collect(Collectors.toList());
				render(compromissosNaoConcluidos, compromissosConcluidos);
	}
	
	public static void listarJSON() {
		Usuario usuario = Usuario.findById(Long.parseLong(session.get("IDUsuario")));
		List<Compromisso> compromissos = usuario.compromissos;
		Gson gson = new GsonBuilder().create();
		gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd").create();
		String result = gson.toJson(compromissos);
		renderJSON(result);
	}
	
	public static void editar(Long id) {
		Compromisso compromisso = Compromisso.findById(id);
		List<Compromisso> compromissos = Compromisso.findAll();
		render("Compromissos/form.html", compromisso, compromissos);
	}

	public static void deletar(Long id) {
		Compromisso compromisso = Compromisso.findById(id);
		compromisso.delete();
		flash.success("Removido com sucesso");
		listar();
	}
	
	public static void concluir(Long id) {
		Compromisso compromisso = Compromisso.findById(id);
		compromisso.status = Status.CONCLUIDO;
		compromisso.save();
		listar();
	}
	
	public static void intermediario(Long id){
		Compromisso compromisso = Compromisso.findById(id);
		compromisso.status = Status.INTERMEDIARIO;
		compromisso.save();
		listar();
	}
}
