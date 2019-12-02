package enums;

public enum Status {
	
	CONCLUIDO("Concluído"), NAO_CONCLUIDO("Não Concluído"), INTERMEDIARIO("Quase lá");
	
	String descricao;
	
	Status(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	

}
