package models;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Past;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import enums.Status;
import play.data.validation.InFuture;
import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.db.jpa.Model;


@Entity
public class Tarefa extends Model{
	
	@Expose
	@SerializedName("title")
	@Required
	@MinSize(2)
	@MaxSize(15)
	public String titulo;
	
	
	@MinSize(2)
	@MaxSize(100)
	public String descricao;
	
	@Expose
	@SerializedName("start")
	@Required
	public String prazo;
	
	@Enumerated(EnumType.STRING)
	public Status status;
	
	@Required
	public String prioridade;
	
	@Required
	@Expose
	public String cor;
	
	public Tarefa() {
		this.status = Status.NAO_CONCLUIDO;
	}
}
