import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Main {

	public static void main(String[] args) {

		// SIMULANDO BANCO DE DADOS

		List<Produto> carrinho = new ArrayList<Produto>();
		List<Venda> vendas = new ArrayList<Venda>();

		Empresa empresa = new Empresa(1, "Level Varejo", "53239160000154", 0.05, 0.0);
		Empresa empresa2 = new Empresa(2, "SafeWay Padaria", "30021423000159", 0.15, 0.0);
		Empresa empresa3 = new Empresa(3, "SafeWay Restaurante", "41361511000116", 0.20, 0.0);

		Produto produto = new Produto(1, "Pão Frances", 0, 3.50, empresa2);
		Produto produto2 = new Produto(2, "Sonho", 0, 8.50, empresa2);
		Produto produto3 = new Produto(3, "Croissant", 0, 6.50, empresa2);
		Produto produto4 = new Produto(4, "Chá Gelado", 0, 5.50, empresa2);
		Produto produto5 = new Produto(5, "Coturno", 0, 400.0, empresa);
		Produto produto6 = new Produto(6, "Jaqueta Jeans", 0, 150.0, empresa);
		Produto produto7 = new Produto(7, "Calça Sarja", 0, 150.0, empresa);
		Produto produto8 = new Produto(8, "Prato feito - Frango", 0, 25.0, empresa3);
		Produto produto9 = new Produto(9, "Prato feito - Carne", 0, 25.0, empresa3);
		Produto produto10 = new Produto(10, "Suco Natural", 0, 10.0, empresa3);

		Estoque estoque = new Estoque(1, 5);
		Estoque estoque2 = new Estoque(2, 5);
		Estoque estoque3 = new Estoque(3, 7);
		Estoque estoque4 = new Estoque(4, 4);
		Estoque estoque5 = new Estoque(5, 10);
		Estoque estoque6 = new Estoque(6, 15);
		Estoque estoque7 = new Estoque(7, 15);
		Estoque estoque8 = new Estoque(8, 10);
		Estoque estoque9 = new Estoque(9, 10);
		Estoque estoque10 = new Estoque(10, 30);

		Cliente cliente = new Cliente("07221134049", "Allan da Silva", "cliente", 20);
		Cliente cliente2 = new Cliente("72840700050", "Samuel da Silva", "cliente2", 24);

		Usuario usuario1 = new Usuario("admin", "1234", null, null);
		Usuario usuario2 = new Usuario("empresa", "1234", null, empresa);
		Usuario usuario3 = new Usuario("cliente", "1234", cliente, null);
		Usuario usuario4 = new Usuario("cliente2", "1234", cliente2, null);
		Usuario usuario5 = new Usuario("empresa2", "1234", null, empresa2);
		Usuario usuario6 = new Usuario("empresa3", "1234", null, empresa3);

		List<Usuario> usuarios = Arrays.asList(usuario1, usuario2, usuario3, usuario4, usuario5, usuario6);
		List<Cliente> clientes = Arrays.asList(cliente, cliente2);
		List<Empresa> empresas = Arrays.asList(empresa, empresa2, empresa3);
		List<Produto> produtos = Arrays.asList(produto, produto2, produto3, produto4, produto5, produto6, produto7,
				produto8, produto9, produto10);
		List<Estoque> estoques = Arrays.asList(estoque, estoque2, estoque3, estoque4, estoque5, estoque6, estoque7,
				estoque8, estoque9, estoque10);
		executar(usuarios, clientes, empresas, produtos, carrinho, vendas, estoques);
	}

	public static void executar(List<Usuario> usuarios, List<Cliente> clientes, List<Empresa> empresas,
			List<Produto> produtos, List<Produto> carrinho, List<Venda> vendas, List<Estoque> estoques) {

		Scanner sc = new Scanner(System.in);

		System.out.println("Entre com seu usuário e senha:");
		System.out.print("Usuário: ");
		String username = sc.next();
		System.out.print("Senha: ");
		String senha = sc.next();

		List<Usuario> usuariosSearch = usuarios.stream().filter(x -> x.getUsername().equals(username))
				.collect(Collectors.toList());
		if (usuariosSearch.size() > 0) {
			Usuario usuarioLogado = usuariosSearch.get(0);
			if ((usuarioLogado.getSenha().equals(senha))) {

				System.out.println("Escolha uma opção para iniciar");
				if (usuarioLogado.IsEmpresa()) {
					System.out.println("1 - Listar vendas");
					System.out.println("2 - Ver produtos");
					System.out.println("0 - Deslogar");
					Integer escolha = sc.nextInt();

					switch (escolha) {
					case 1: {
						System.out.println();
						System.out.println("************************************************************");
						System.out.println("VENDAS EFETUADAS");
						vendas.stream().forEach(venda -> {
							if (venda.getEmpresa().getId().equals(usuarioLogado.getEmpresa().getId())) {
								System.out.println("************************************************************");
								System.out.println("Venda de código: " + venda.getCodigo() + " no CPF "+ venda.getCliente().getCpf() + ": ");
								venda.getItens().stream().forEach(x -> {
									Double valorTotalProduto = x.getPreco()*x.getQuantidade();
									System.out.println(" " + x.getNome() + "    R$" + valorTotalProduto);
								});
								System.out.printf("Total Venda: R$%.2f%n",venda.getValor());
								System.out.printf("Total Taxa a ser paga: R$%.2f%n",venda.getComissaoSistema());
								System.out.printf("Total Líquido para empresa: R$%.2f%n",(venda.getValor() - venda.getComissaoSistema()));
								System.out.println("************************************************************");
							}

						});
						System.out.println("Saldo Empresa: " + usuarioLogado.getEmpresa().getSaldo());
						System.out.println("************************************************************");

						executar(usuarios, clientes, empresas, produtos, carrinho, vendas, estoques);
					}
					case 2: {
						System.out.println();
						System.out.println("************************************************************");
						System.out.println("MEUS PRODUTOS");
						produtos.stream().forEach(produto -> {
							Estoque estoqueProduto = estoques.stream().filter(x -> x.getId().equals(produto.getId())).collect(Collectors.toList()).get(0);
							if (produto.getEmpresa().getId().equals(usuarioLogado.getEmpresa().getId())) {
								System.out.println("************************************************************");
								System.out.println("Código: " + produto.getId());
								System.out.println("Produto: " + produto.getNome());
								System.out.println("Quantidade em estoque: " + estoqueProduto.getQuantidadeEstoque());
								System.out.println("Valor: R$" + produto.getPreco());
								System.out.println("************************************************************");
							}

						});
						System.out.println("Saldo Empresa: " + usuarioLogado.getEmpresa().getSaldo());
						System.out.println("************************************************************");

						executar(usuarios, clientes, empresas, produtos, carrinho, vendas, estoques);
					}
					case 0: {
						executar(usuarios, clientes, empresas, produtos, carrinho, vendas, estoques);
					}
					}
				} else {
					System.out.println("1 - Relizar Compras");
					System.out.println("2 - Ver Compras");
					System.out.println("0 - Deslogar");
					Integer escolha = sc.nextInt();
					switch (escolha) {
					case 1: {
						System.out.println("Para realizar uma compra, escolha a empresa onde deseja comprar: ");
						empresas.stream().forEach(x -> {
							System.out.println(x.getId() + " - " + x.getNome());
						});
						Integer escolhaEmpresa = sc.nextInt();
						Integer escolhaProduto = -1;
						do {
							System.out.println("Escolha os seus produtos: ");
							produtos.stream().forEach(x -> {
								if (x.getEmpresa().getId().equals(escolhaEmpresa)) {
									System.out.println(x.getId() + " - " + x.getNome());
								}
							});
							System.out.println("0 - Finalizar compra");
							escolhaProduto = sc.nextInt();
							for (Produto produtoSearch : produtos) {
								if (produtoSearch.getId().equals(escolhaProduto)) {
									System.out.println("Insira a quantidade que deseja: ");
									Integer quantidadeEscolhida = sc.nextInt();
									Estoque estoqueProduto = estoques.stream()
											.filter(x -> produtoSearch.getId().equals(x.getId()))
											.collect(Collectors.toList()).get(0);
									if (quantidadeEscolhida > estoqueProduto.getQuantidadeEstoque()) {
										System.out.println("Quantidade indisponível no estoque");
										System.out.println(
												"Temos " + estoqueProduto.getQuantidadeEstoque() + " produtos");
										System.out.println("");
										continue;
									} else {
										produtoSearch.setQuantidade(quantidadeEscolhida);
										estoqueProduto.setQuantidadeEstoque(estoqueProduto.getQuantidadeEstoque() - quantidadeEscolhida);
										carrinho.add(produtoSearch);
										System.out.println("Adicionado ao carrinho " + quantidadeEscolhida + " "+ produtoSearch.getNome());
										continue;
									}
								}
							}

						} while (escolhaProduto != 0);
						System.out.println("************************************************************");
						System.out.println("Resumo da compra: ");
						carrinho.stream().forEach(x -> {
							if (x.getEmpresa().getId().equals(escolhaEmpresa)) {
								Double valorTotal = x.getQuantidade() * x.getPreco();
								System.out.println(
										x.getQuantidade() + " " + x.getNome() + " R$" + x.getPreco() +"    R$" + valorTotal);
							}
						});
						Empresa empresaEscolhida = empresas.stream().filter(x -> x.getId().equals(escolhaEmpresa))
								.collect(Collectors.toList()).get(0);
						Cliente clienteLogado = clientes.stream()
								.filter(x -> x.getUsername().equals(usuarioLogado.getUsername()))
								.collect(Collectors.toList()).get(0);
						Venda venda;
						try {
							venda = criarVenda(carrinho, empresaEscolhida, clienteLogado, vendas);
						} catch (Exception e) {
							e.printStackTrace();
							System.out.println("Não foi possível fazer a venda "+e.getMessage());
							return;
						}
						System.out.println("Total: R$" + venda.getValor());
						System.out.println("************************************************************");
						carrinho.clear();
						executar(usuarios, clientes, empresas, produtos, carrinho, vendas, estoques);
					}
					case 2: {
						System.out.println();
						System.out.println("************************************************************");
						System.out.println("COMPRAS EFETUADAS");
						vendas.stream().forEach(venda -> {
							if (venda.getCliente().getUsername().equals(usuarioLogado.getUsername())) {
								System.out.println("************************************************************");
								System.out.println("Compra de código: " + venda.getCodigo() + " na empresa "
										+ venda.getEmpresa().getNome() + ": ");
								venda.getItens().stream().forEach(x -> {
									System.out.println(x.getQuantidade() + " - " + x.getNome() + "    R$" + x.getPreco());
								});
								System.out.println("Total: R$" + venda.getValor());
								System.out.println("************************************************************");
							}

						});

						executar(usuarios, clientes, empresas, produtos, carrinho, vendas, estoques);
					}
					case 0: {
						executar(usuarios, clientes, empresas, produtos, carrinho, vendas, estoques);

					}

					}
				}

			} else {
				System.out.println("Senha incorreta");
				executar(usuarios, clientes, empresas, produtos, carrinho, vendas, estoques);				
			}				
			
		} else {
			System.out.println("Usuário não encontrado");
			executar(usuarios, clientes, empresas, produtos, carrinho, vendas, estoques);
		}
	}

	public static Venda criarVenda(List<Produto> carrinho, Empresa empresa, Cliente cliente, List<Venda> vendas) {
		Double total = 0.0;
		for(int i=0; i<carrinho.size(); i++) {
			Integer quantidade = carrinho.get(i).getQuantidade();
			total += quantidade*carrinho.get(i).getPreco();
		}
		Double comissaoSistema = total * empresa.getTaxa();
		int idVenda = vendas.isEmpty() ? 1 : vendas.get(vendas.size() - 1).getCodigo() + 1;
		Venda venda = new Venda(idVenda, carrinho.stream().toList(), total, comissaoSistema, empresa, cliente);
		empresa.setSaldo(empresa.getSaldo() + total);
		vendas.add(venda);
		return venda;
	}

}
