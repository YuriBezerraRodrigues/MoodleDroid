package br.ifpe.ava.ifmoodledroid.handler;

import android.content.Context;
import br.ifpe.ava.ifmoodledroid.db.DBHelper;
import br.ifpe.ava.ifmoodledroid.db.RepositorioUsuario;
import br.ifpe.ava.ifmoodledroid.model.Usuario;

public class UsuarioHandler {

	
	
	public static void removerUsuarioSalvo(String login, Context context){
		
		RepositorioUsuario repositorioUsuario = new RepositorioUsuario(
				new DBHelper(context));	 
		repositorioUsuario.remover(login);
		
	}

	public static Usuario pesquisarUsuarioSalvo(String login, Context context) {

		RepositorioUsuario repositorioUsuario = new RepositorioUsuario(
				new DBHelper(context));	 
		
		return repositorioUsuario.pesquisar(login);
	}
	
	
}
