package br.ifpe.ava.ifmoodledroid.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Noticia implements Serializable{

	public Noticia(long id, String titulo, String autor, String mensagem) {
		this.id = id;
		this.titulo = titulo;
		this.autor = autor;
		this.mensagem = mensagem;
	}
	
	public Noticia(){
		
	}
	
	private long id;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	private String titulo;
	private String autor;
	private String mensagem;
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}
