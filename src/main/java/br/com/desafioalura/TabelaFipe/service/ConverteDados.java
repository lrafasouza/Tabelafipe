package br.com.desafioalura.TabelaFipe.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

public class ConverteDados implements IConverteDados{

	
	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public <T> T obterDados(String json, Class<T> classe) {
		try {
            return mapper.readValue(json, classe);
        } catch (JsonMappingException e) {
            System.err.println("Erro ao mapear JSON para a classe: " + classe.getSimpleName());
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            System.err.println("Erro ao processar JSON.");
            e.printStackTrace();
        }
        return null; 
    }

	@Override
    public <T> List<T> obterLista(String json, Class<T> classe) {
        CollectionType lista = mapper.getTypeFactory()
            .constructCollectionType(List.class, classe);
        
        try {
            return mapper.readValue(json, lista);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
	}
	

		
	
}
