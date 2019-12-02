package controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import enums.Status;
import models.Tarefa;
import models.Usuario;
import play.cache.Cache;
import play.data.validation.Valid;
import play.mvc.Controller;
import play.mvc.With;

@With(Seguranca.class)

public class Tarefas extends Controller {

	public static void form() {
		Tarefa tarefa = (Tarefa) Cache.get("Tarefa");
		Cache.clear();
		render(tarefa);
	}

	public static void salvar(@Valid Tarefa tarefa1) {
		
		if(validation.hasErrors()) {
			for(play.data.validation.Error error: validation.errors()) {
				System.out.println(error);
			}
			params.flash();
			validation.keep();
			flash.error("Falha ao salvar tarefa");
			Cache.set("Tarefa", tarefa1);
			form();
		}else {		
			tarefa1.save();
			Usuario usuario = Usuario.findById(Long.parseLong(session.get("IDUsuario")));
			usuario.tarefas.add(tarefa1);
			usuario.save();
			flash.success("Tarefa salva com sucesso.");
			listar();
		}
	}
	
	public static void listar() {
		Usuario usuario = Usuario.findById(Long.parseLong(session.get("IDUsuario")));
		List<Tarefa> tarefasNaoConcluidas = usuario.tarefas.stream()                // convert list to stream
                .filter(tarefa -> tarefa.status.equals(Status.NAO_CONCLUIDO))     // we dont like mkyong
                .collect(Collectors.toList());
		List<Tarefa> tarefasIntermediario = usuario.tarefas.stream()                // convert list to stream
                .filter(tarefa -> tarefa.status.equals(Status.INTERMEDIARIO))     // we dont like mkyong
                .collect(Collectors.toList());
		List<Tarefa> tarefasConcluidas = usuario.tarefas.stream()                // convert list to stream
                .filter(tarefa -> tarefa.status.equals(Status.CONCLUIDO))     // we dont like mkyong
                .collect(Collectors.toList());
		render(tarefasNaoConcluidas, tarefasIntermediario, tarefasConcluidas);
	}
	
	//action para listar json
	
	public static void listarJSON() {
		Usuario usuario = Usuario.findById(Long.parseLong(session.get("IDUsuario")));
		List<Tarefa> tarefas = usuario.tarefas;
		Gson gson = new GsonBuilder().create();
		gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd").create();
		String result = gson.toJson(tarefas);
		renderJSON(result);

	}
	
	public static void editar(Long id) {
		Tarefa tarefa = Tarefa.findById(id);
		render("Tarefas/form.html", tarefa);
	}

	public static void deletar(Long id) {
		Tarefa tarefa = Tarefa.findById(id);
		tarefa.delete();
		flash.success("Tarefa removida com sucesso.");
		listar();
	}

	public static void concluir(Long id) {
		Tarefa tarefa = Tarefa.findById(id);
		tarefa.status = Status.CONCLUIDO;
		tarefa.save();
		listar();
	}
	
	public static void intermediario(Long id){
		Tarefa tarefa = Tarefa.findById(id);
		tarefa.status = Status.INTERMEDIARIO;
		tarefa.save();
		listar();
	}	
}
