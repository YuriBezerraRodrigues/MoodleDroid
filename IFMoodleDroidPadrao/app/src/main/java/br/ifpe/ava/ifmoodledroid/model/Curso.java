package br.ifpe.ava.ifmoodledroid.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Curso implements Serializable{
	
	private long id;
	private String nome;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	

}
