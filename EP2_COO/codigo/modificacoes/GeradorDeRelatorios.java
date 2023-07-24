package modificacoes;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.*;

import interfaces.Interface_Filtragem;
import interfaces.Interface_Ordenacao;

/* */
public class GeradorDeRelatorios {

	public static final String ALG_INSERTIONSORT = "quick";
	public static final String ALG_QUICKSORT = "insertion";

	public static final String CRIT_DESC_CRESC = "descricao_c";
	public static final String CRIT_PRECO_CRESC = "preco_c";
	public static final String CRIT_ESTOQUE_CRESC = "estoque_c";

	public static final String FILTRO_TODOS = "todos";
	public static final String FILTRO_ESTOQUE_MENOR_OU_IQUAL_A = "estoque_menor_igual";
	public static final String FILTRO_CATEGORIA_IGUAL_A = "categoria_igual";

	// operador bit a bit "ou" pode ser usado para combinar mais de
	// um estilo de formatacao simultaneamente (veja como no main)
	public static final int FORMATO_PADRAO = 0b0000;
	public static final int FORMATO_NEGRITO = 0b0001;
	public static final int FORMATO_ITALICO = 0b0010;

	private ArrayList<Produto> produtos; // mudei para arraylist
	private String algoritmo;
	private String criterio;
	private String filtro;
	private String argFiltro;
	private Set<String> format_flags; // de int para set

	public GeradorDeRelatorios(ArrayList<Produto> produtos, String algoritmo, String criterio, String filtro,
			String argFiltro,
			Set<String> format_flags) { // Updated constructor signature

		this.produtos = new ArrayList<>(produtos); // Copy the contents to the internal ArrayList

		this.algoritmo = algoritmo;
		this.criterio = criterio;
		this.format_flags = format_flags;
		this.filtro = filtro;
		this.argFiltro = argFiltro;
	}

	// ###########################ALGORITMOS DE
	// ORDENACAO##########################################

	public class InsertionSortPorDescricao implements Interface_Ordenacao {
		public void ordenar(List<modificacoes.Produto> produtos) {
			int n = produtos.size();

			for (int i = 1; i < n; i++) {
				Produto x = produtos.get(i);
				int j = i - 1;

				while (j >= 0 && x.getDescricao().compareToIgnoreCase(produtos.get(j).getDescricao()) < 0) {
					produtos.set(j + 1, produtos.get(j));
					j--;
				}

				produtos.set(j + 1, x);
			}

		}
	}

	public class InsertionSortPorDescricaoDecrescente implements Interface_Ordenacao {
		public void ordenar(List<modificacoes.Produto> produtos) {
			int n = produtos.size();

			for (int i = 1; i < n; i++) {
				Produto x = produtos.get(i);
				int j = i - 1;

				while (j >= 0 && x.getDescricao().compareToIgnoreCase(produtos.get(j).getDescricao()) > 0) {
					produtos.set(j + 1, produtos.get(j));
					j--;
				}

				produtos.set(j + 1, x);
			}
		}
	}

	public class InsertionSortPorPreco implements Interface_Ordenacao {
		public void ordenar(List<modificacoes.Produto> produtos) {
			int n = produtos.size();
			for (int i = 1; i < n; i++) {
				Produto x = produtos.get(i);
				int j = i - 1;

				while (j >= 0 && x.getPreco() < produtos.get(j).getPreco()) {
					produtos.set(j + 1, produtos.get(j));
					j--;
				}

				produtos.set(j + 1, x);
			}

		}
	}

	public class InsertionSortPorPrecoDecrescente implements Interface_Ordenacao {
		public void ordenar(List<modificacoes.Produto> produtos) {
			int n = produtos.size();

			for (int i = 1; i < n; i++) {
				Produto x = produtos.get(i);
				int j = i - 1;

				while (j >= 0 && x.getPreco() > produtos.get(j).getPreco()) {
					produtos.set(j + 1, produtos.get(j));
					j--;
				}

				produtos.set(j + 1, x);
			}
		}
	}

	public class InsertionSortPorEstoque implements Interface_Ordenacao {
		public void ordenar(List<modificacoes.Produto> produtos) {

			int n = produtos.size();

			for (int i = 1; i < n; i++) {
				Produto x = produtos.get(i);
				int j = i - 1;

				while (j >= 0 && x.getQtdEstoque() < produtos.get(j).getQtdEstoque()) {
					produtos.set(j + 1, produtos.get(j));
					j--;
				}

				produtos.set(j + 1, x);
			}
		}
	}

	public class InsertionSortPorEstoqueDecrescente implements Interface_Ordenacao {
		public void ordenar(List<modificacoes.Produto> produtos) {
			int n = produtos.size();

			for (int i = 1; i < n; i++) {
				Produto x = produtos.get(i);
				int j = i - 1;

				while (j >= 0 && x.getQtdEstoque() > produtos.get(j).getQtdEstoque()) {
					produtos.set(j + 1, produtos.get(j));
					j--;
				}

				produtos.set(j + 1, x);
			}
		}
	}

	public class Quicksort implements Interface_Ordenacao {
		private String criterio;

		public Quicksort(String criterio) {
			this.criterio = criterio;
		}

		public void ordenar(List<Produto> produtos) {
			quicksort(produtos, 0, produtos.size() - 1);
		}

		private void quicksort(List<Produto> produtos, int ini, int fim) {
			if (ini < fim) {
				int p = particiona(produtos, ini, fim);
				quicksort(produtos, ini, p);
				quicksort(produtos, p + 1, fim);
			}
		}

		private int particiona(List<Produto> produtos, int ini, int fim) {
			Produto x = produtos.get(ini);
			int i = ini - 1;
			int j = fim + 1;

			while (true) {
				int compareResult;
				if (criterio.equals("descricao")) {
					compareResult = produtos.get(j).getDescricao().compareToIgnoreCase(x.getDescricao());
				} else if (criterio.equals("preco")) {
					compareResult = Double.compare(produtos.get(j).getPreco(), x.getPreco());
				} else if (criterio.equals("estoque")) {
					compareResult = Integer.compare(produtos.get(j).getQtdEstoque(), x.getQtdEstoque());
				} else {
					throw new RuntimeException("Criterio invalido!");
				}

				while (compareResult > 0) {
					j--;
					compareResult = produtos.get(j).getDescricao().compareToIgnoreCase(x.getDescricao());
				}

				if (i < j) {
					i++;
					Produto temp = produtos.get(i);
					produtos.set(i, produtos.get(j));
					produtos.set(j, temp);
				} else {
					return j;
				}
			}

		}
	}

	private void ordena(List<Produto> produtos) {

		if (algoritmo.equals(ALG_INSERTIONSORT)) {
			if (criterio.equals(CRIT_DESC_CRESC)) {
				InsertionSortPorDescricao estrategiaOrdenacao = new InsertionSortPorDescricao();
				estrategiaOrdenacao.ordenar(produtos);
			} else if (criterio.equals(CRIT_PRECO_CRESC)) {
				InsertionSortPorPreco estrategiaOrdenacao = new InsertionSortPorPreco();
				estrategiaOrdenacao.ordenar(produtos);
			} else if (criterio.equals(CRIT_ESTOQUE_CRESC)) {
				InsertionSortPorEstoque estrategiaOrdenacao = new InsertionSortPorEstoque();
				estrategiaOrdenacao.ordenar(produtos);
			} else {
				throw new RuntimeException("Criterio invalido!");
			}
		} else if (algoritmo.equals(ALG_QUICKSORT)) {
			Quicksort estrategiaOrdenacao = new Quicksort(criterio);
			estrategiaOrdenacao.ordenar(produtos);
		} else {
			throw new RuntimeException("Algoritmo invalido!");
		}

	}

	public class QuicksortDecrescente implements Interface_Ordenacao {
		private String criterio;

		public QuicksortDecrescente(String criterio) {
			this.criterio = criterio;
		}

		public void ordenar(List<Produto> produtos) {
			quicksort(produtos, 0, produtos.size() - 1);
		}

		private void quicksort(List<Produto> produtos, int ini, int fim) {
			if (ini < fim) {
				int p = particiona(produtos, ini, fim);
				quicksort(produtos, ini, p - 1);
				quicksort(produtos, p + 1, fim);
			}
		}

		private int particiona(List<Produto> produtos, int ini, int fim) {
			Produto x = produtos.get(ini);
			int i = ini;
			int j = fim;

			while (i < j) {
				int compareResult;
				if (criterio.equals("descricao")) {
					compareResult = produtos.get(j).getDescricao().compareToIgnoreCase(x.getDescricao());
				} else if (criterio.equals("preco")) {
					compareResult = Double.compare(produtos.get(j).getPreco(), x.getPreco());
				} else if (criterio.equals("estoque")) {
					compareResult = Integer.compare(produtos.get(j).getQtdEstoque(), x.getQtdEstoque());
				} else {
					throw new RuntimeException("Criterio invalido!");
				}

				// Inverter a ordem das comparações para ordenação decrescente
				if (compareResult <= 0) {
					Produto temp = produtos.get(i);
					produtos.set(i, produtos.get(j));
					produtos.set(j, temp);
					i++;
					j--;
				}
			}

			Produto temp = produtos.get(i);
			produtos.set(i, produtos.get(fim));
			produtos.set(fim, temp);
			return i;
		}
	}

	// ###########################ALGORITMOS DE
	// ORDENACAO##########################################

	public class FiltroTodos implements Interface_Filtragem {
		public boolean selecionado(Produto produto, String argFiltro) {
			return true;
		}
	}

	public class FiltroEstoqueMenorOuIgual implements Interface_Filtragem {
		public boolean selecionado(Produto produto, String argFiltro) {
			return produto.getQtdEstoque() <= Integer.parseInt(argFiltro);
		}
	}

	public class FiltroCategoriaIgual implements Interface_Filtragem {
		public boolean selecionado(Produto produto, String argFiltro) {
			return produto.getCategoria().equalsIgnoreCase(argFiltro);
		}
	}

	public void debug() {

		System.out.println("Gerando relatório para array contendo " + produtos.size() + " produto(s)");
		System.out.println("parametro filtro = '" + argFiltro + "'");
	}

	public void geraRelatorio(String arquivoSaida, String filtro, String argFiltro, Set<String> format_flags)
			throws IOException {
		debug();

		ordena(produtos);

		Interface_Filtragem filtroSelecionado; // Referência à interface

		// Aplicar o padrão Strategy para escolher o filtro adequado
		if (filtro.equals(FILTRO_TODOS)) {
			filtroSelecionado = new FiltroTodos();
		} else if (filtro.equals(FILTRO_ESTOQUE_MENOR_OU_IQUAL_A)) {
			filtroSelecionado = new FiltroEstoqueMenorOuIgual();
		} else if (filtro.equals(FILTRO_CATEGORIA_IGUAL_A)) {
			filtroSelecionado = new FiltroCategoriaIgual();
		} else {
			throw new RuntimeException("Filtro inválido!");
		}

		try (PrintWriter out = new PrintWriter(arquivoSaida)) {
			out.println("<!DOCTYPE html><html>");
			out.println("<head><title>Relatorio de produtos</title></head>");
			out.println("<body>");
			out.println("Relatorio de Produtos:");
			out.println("<ul>");

			int count = 0;

			for (int i = 0; i < produtos.size(); i++) {
				Produto p = produtos.get(i);

				// Chamar o método selecionado do filtro apropriado
				boolean selecionado = filtroSelecionado.selecionado(p, argFiltro);

				if (selecionado) {
					// Restante do código para gerar o relatório
					out.print("<li>");

					if (format_flags.contains(FORMATO_ITALICO)) {
						out.print("<span style=\"font-style:italic\">");
					}

					if (format_flags.contains(FORMATO_NEGRITO)) {
						out.print("<span style=\"font-weight:bold\">");
					}

					out.print(p.formataParaImpressao());

					if (format_flags.contains(FORMATO_NEGRITO)) {
						out.print("</span>");
					}

					if (format_flags.contains(FORMATO_ITALICO)) {
						out.print("</span>");
					}

					out.println("</li>");
					count++;
				}
			}

			out.println("</ul>");
			out.println(count + " produtos listados, de um total de " + produtos.size() + ".");
			out.println("</body>");
			out.println("</html>");

			out.close();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static List<Produto> carregaProdutos() {
		List<Produto> produtos = new ArrayList<>();

		produtos.add(new ProdutoPadrao(1, "O Hobbit", "Livros", 2, 34.90));
		produtos.add(new ProdutoPadrao(2, "Notebook Core i7", "Informatica", 5, 1999.90));
		produtos.add(new ProdutoPadrao(3, "Resident Evil 4", "Games", 7, 79.90));
		produtos.add(new ProdutoPadrao(4, "iPhone", "Telefonia", 8, 4999.90));
		produtos.add(new ProdutoPadrao(5, "Calculo I", "Livros", 20, 55.00));
		produtos.add(new ProdutoPadrao(6, "Power Glove", "Games", 3, 499.90));
		produtos.add(new ProdutoPadrao(7, "Microsoft HoloLens", "Informatica", 1, 19900.00));
		produtos.add(new ProdutoPadrao(8, "OpenGL Programming Guide", "Livros", 4, 89.90));
		produtos.add(new ProdutoPadrao(9, "Vectrex", "Games", 1, 799.90));
		produtos.add(new ProdutoPadrao(10, "Carregador iPhone", "Telefonia", 15, 499.90));
		produtos.add(new ProdutoPadrao(11, "Introduction to Algorithms", "Livros", 7, 315.00));
		produtos.add(new ProdutoPadrao(12, "Daytona USA (Arcade)", "Games", 1, 12000.00));
		produtos.add(new ProdutoPadrao(13, "Neuromancer", "Livros", 5, 45.00));
		produtos.add(new ProdutoPadrao(14, "Nokia 3100", "Telefonia", 4, 249.99));
		produtos.add(new ProdutoPadrao(15, "Oculus Rift", "Games", 1, 3600.00));
		produtos.add(new ProdutoPadrao(16, "Trackball Logitech", "Informatica", 1, 250.00));
		produtos.add(new ProdutoPadrao(17, "After Burner II (Arcade)", "Games", 2, 8900.0));
		produtos.add(new ProdutoPadrao(18, "Assembly for Dummies", "Livros", 30, 129.90));
		produtos.add(new ProdutoPadrao(19, "iPhone (usado)", "Telefonia", 3, 3999.90));
		produtos.add(new ProdutoPadrao(20, "Game Programming Patterns", "Livros", 1, 299.90));
		produtos.add(new ProdutoPadrao(21, "Playstation 2", "Games", 10, 499.90));
		produtos.add(new ProdutoPadrao(22, "Carregador Nokia", "Telefonia", 14, 89.00));
		produtos.add(new ProdutoPadrao(23, "Placa Aceleradora Voodoo 2", "Informatica", 4, 189.00));
		produtos.add(new ProdutoPadrao(24, "Stunts", "Games", 3, 19.90));
		produtos.add(new ProdutoPadrao(25, "Carregador Generico", "Telefonia", 9, 30.00));
		produtos.add(new ProdutoPadrao(26, "Monitor VGA 14 polegadas", "Informatica", 2, 199.90));
		produtos.add(new ProdutoPadrao(27, "Nokia N-Gage", "Telefonia", 9, 699.00));
		produtos.add(new ProdutoPadrao(28, "Disquetes Maxell 5.25 polegadas (caixa com 10 unidades)", "Informatica",
				23, 49.00));
		produtos.add(new ProdutoPadrao(29, "Alone in The Dark", "Games", 11, 59.00));
		produtos.add(new ProdutoPadrao(30, "The Art of Computer Programming Vol. 1", "Livros", 3, 240.00));
		produtos.add(new ProdutoPadrao(31, "The Art of Computer Programming Vol. 2", "Livros", 2, 200.00));
		produtos.add(new ProdutoPadrao(32, "The Art of Computer Programming Vol. 3", "Livros", 4, 270.00));

		return produtos;
	}

	public static void main(String[] args) {

		if (args.length < 4) {

			System.out.println("Uso:");
			System.out.println("\tjava " + GeradorDeRelatorios.class.getName()
					+ " <algoritmo> <critério de ordenação> <critério de filtragem> <parâmetro de filtragem> <opções de formatação>");
			System.out.println("Onde:");
			System.out.println("\talgoritmo: 'quick' ou 'insertion'");
			System.out.println("\tcriterio de ordenação: 'preco_c' ou 'descricao_c' ou 'estoque_c'");
			System.out.println("\tcriterio de filtragem: 'todos' ou 'estoque_menor_igual' ou 'categoria_igual'");
			System.out.println("\tparâmetro de filtragem: argumentos adicionais necessários para a filtragem");
			System.out.println("\topções de formatação: 'negrito' e/ou 'italico'");
			System.out.println();
			System.exit(1);
		}

		String opcao_algoritmo = args[0];
		String opcao_criterio_ord = args[1];
		String opcao_criterio_filtro = args[2];
		String opcao_parametro_filtro = args[3];

		String[] opcoes_formatacao = new String[2];
		opcoes_formatacao[0] = args.length > 4 ? args[4] : null;
		opcoes_formatacao[1] = args.length > 5 ? args[5] : null;
		int formato = FORMATO_PADRAO;

		for (int i = 0; i < opcoes_formatacao.length; i++) {

			String op = opcoes_formatacao[i];
			formato |= (op != null
					? op.equals("negrito") ? FORMATO_NEGRITO : (op.equals("italico") ? FORMATO_ITALICO : 0)
					: 0);
		}

	}
}
