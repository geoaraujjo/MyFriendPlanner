package models;


import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
public class Compromisso extends Model {

	@Expose
	@SerializedName("title")
	@Required
	@MinSize(2)
	@MaxSize(15)
	public String titulo;
	
	@MinSize(2)
	@MaxSize(100)
	public String descricao;
	
	public Status status;

	@Expose
	@SerializedName("start")
	@Required
	public String data;

	
	@Required
	@Expose
	public String cor;
	
	
	public Compromisso() {
		this.status = Status.NAO_CONCLUIDO;
		
	}
	
}
