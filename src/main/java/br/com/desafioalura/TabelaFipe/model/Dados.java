package br.com.desafioalura.TabelaFipe.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Dados( 
	String codigo,
	String nome) {
}