package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import enums.Genero;
import play.data.validation.Email;
import play.data.validation.Equals;
import play.data.validation.InFuture;
import play.data.validation.MinSize;
import play.data.validation.Password;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Usuario extends Model{
	
	@Required
	public String nome;
	
	@Required
	@Email
	public String email;
	
	@Required
	public Genero genero;
	
	@Required
	public String estudante;
	
	@Transient
	public String confirmarSenha;
	
	@Required
	@Password
	@Equals(value="confirmarSenha", message="A senha de confirmação está diferente")
	@MinSize(6)
	public String senha;
	
	@Required
	@Temporal(TemporalType.DATE)
	public Date nasc;
	
	@OneToMany
	@JoinColumn(name = "usuario_compromisso")
	public List<Compromisso> compromissos;

	@OneToMany
	@JoinColumn(name = "usuario_tarefa")
	public List<Tarefa> tarefas;
	

	public String nomeFoto;
	
	
	public Usuario() {
		this.tarefas = new ArrayList<Tarefa>();
		this.compromissos = new ArrayList<Compromisso>();
	}
}	