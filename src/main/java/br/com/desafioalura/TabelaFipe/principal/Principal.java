package br.com.desafioalura.TabelaFipe.principal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import br.com.alura.screenmatch.screenmatch.model.DadosSerie;
import br.com.desafioalura.TabelaFipe.model.Dados;
import br.com.desafioalura.TabelaFipe.model.Modelos;
import br.com.desafioalura.TabelaFipe.model.Veiculo;
import br.com.desafioalura.TabelaFipe.service.ConsumoApi;
import br.com.desafioalura.TabelaFipe.service.ConverteDados;



public class Principal {
	private Scanner sc = new Scanner(System.in);
	private ConsumoApi consumo = new ConsumoApi();
	private ConverteDados conversor = new ConverteDados();

	private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";

	
	public void ExibirMenu() {
		System.out.println("** OPÇÕES **");
		System.out.println("Carros");
		System.out.println("Motos");
		System.out.println("Caminhão");
		System.out.println();
		System.out.println("Digite uma das opções para consultar: ");
		var opcao = sc.nextLine();
		String endereco;

	if(opcao.toLowerCase().contains("carr")) {
		endereco = URL_BASE + "carros/marcas";
	} else if(opcao.toLowerCase().contains("mot")) {
		endereco = URL_BASE + "motos/marcas";
	} else {
		endereco = URL_BASE + "caminhoes/marcas";
	}
		
	var json = consumo.obterDados(endereco);
	
	var marcas = conversor.obterLista(json, Dados.class);
	marcas.stream()
	.sorted(Comparator.comparing(Dados::codigo))
	.forEach(System.out::println);
	System.out.println("Agora selecione a marca que deseja consultar: ");
	var codigoMarca = sc.nextLine();
	
	endereco = endereco + "/" + codigoMarca + "/modelos";
	json = consumo.obterDados(endereco);
	var modeloLista = conversor.obterDados(json, Modelos.class);
	
	System.out.println("\nModelos dessa marca: ");
	modeloLista.modelos().stream()
	.sorted(Comparator.comparing(Dados::codigo))
	.forEach(System.out::println);
	
	System.out.println("Digite um trecho  do nome do veiculo a ser buscado ");
	var nomeVeiculo = sc.nextLine();
	
	List<Dados> modelosFiltrados = modeloLista.modelos().stream()
			.filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
			.collect(Collectors.toList());
	
	System.out.println("\nModelos filtrados");
	modelosFiltrados.forEach(System.out::println);
	
	System.out.println("Digite por favor o codigo do modelo");
	var codigoModelo = sc.nextLine();
	
	endereco = endereco + "/" + codigoModelo + "/anos";
	json = consumo.obterDados(endereco);
	List<Dados> anos = conversor.obterLista(json, Dados.class);
	List<Veiculo> veiculos = new ArrayList();
	
	for (int i = 0; i < anos.size(); i++) {
	    var enderecoAnos = endereco + "/" + anos.get(i).codigo();
	    json = consumo.obterDados(enderecoAnos);
	    Veiculo veiculo = conversor.obterDados (json, Veiculo.class);
	    veiculos.add(veiculo);
	    }

	System.out.println("\nTodos os veiculos filtrados com avaliações por ano: "); veiculos.forEach(System.out::println);
	
	}	
}
