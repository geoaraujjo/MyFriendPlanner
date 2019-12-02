package jobs;

import java.text.SimpleDateFormat;

import models.Compromisso;
import models.Tarefa;
import models.Usuario;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

@OnApplicationStart
public class Inicializador extends Job {

	public void doJob() throws Exception {

		if (Usuario.count() == 0) {

			Usuario usuario_adm = new Usuario();
			usuario_adm.nome = "Admin";
			usuario_adm.senha = "Admin";
			usuario_adm.email = "admin@planner.com";
			usuario_adm.save();

			Tarefa tarefa_adm = new Tarefa();
			tarefa_adm.titulo = "Admin";
			tarefa_adm.save();
			usuario_adm.tarefas.add(tarefa_adm);
			tarefa_adm.save();
			
			Compromisso compromisso_adm = new Compromisso();
			compromisso_adm.titulo = "Admin";
			compromisso_adm.save();
			usuario_adm.compromissos.add(compromisso_adm);
			compromisso_adm.save();
			
			
		}

		/*
		 * Compromisso compromisso_adm = new Compromisso(); compromisso_adm.titulo =
		 * "Admin"; compromisso_adm.save();
		 * usuario_adm.compromissos.add(compromisso_adm);
		 */

	}

}
