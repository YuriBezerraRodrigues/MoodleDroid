package br.ifpe.ava.ifmoodledroid.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Usuario implements Serializable{

	private long id;
	private String login;
	private String senha;
	private String nome;
	private String sobrenome;
	
	public Usuario(){
		
	}
	
	public Usuario(long id, String login, String senha,String nome, String sobrenome) {
		this.id = id;
		this.login = login;
		this.senha = senha;
		this.nome = nome;
		this.sobrenome = sobrenome;
	}
	public Usuario(String login, String senha, String nome, String sobrenome){
		this(0, login, senha,nome,sobrenome);
	}
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSobrenome() {
		return sobrenome;
	}
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	public long getId() {
		return id;
	}
	public void setId(long id){
		this.id=id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
}
