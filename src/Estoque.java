
public class Estoque {
	
	private Integer id;
	private Integer quantidadeEstoque;
	
	public Estoque (Integer id, Integer quantidadeEstoque) {
		this.id = id;
		this.quantidadeEstoque = quantidadeEstoque;
	}
	
	public Estoque () {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}

	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}

}
