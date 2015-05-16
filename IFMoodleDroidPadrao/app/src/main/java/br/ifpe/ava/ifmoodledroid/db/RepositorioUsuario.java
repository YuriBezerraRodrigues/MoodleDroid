package br.ifpe.ava.ifmoodledroid.db;

import java.util.ArrayList;
import java.util.List;

import br.ifpe.ava.ifmoodledroid.model.Usuario;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class RepositorioUsuario {
	private DBHelper dBHelper;
	
	public RepositorioUsuario(DBHelper sqLiteHelper) {
		this.dBHelper = sqLiteHelper;
	}
	
	private ContentValues getValues(Usuario usuario){
		ContentValues contentValues = new ContentValues();
		contentValues.put("login", usuario.getLogin());
		contentValues.put("senha", usuario.getSenha());
		contentValues.put("nome", usuario.getNome());
		contentValues.put("sobrenome", usuario.getSobrenome());
		return contentValues;
	}
	
	public void inserir(Usuario usuario) {
		SQLiteDatabase database = dBHelper.getWritableDatabase();
		database.insert("usuarios", null, getValues(usuario));
		database.close();
	}
	
	public void atualizar(Usuario usuario) {
		SQLiteDatabase database = dBHelper.getWritableDatabase();
		database.update("usuarios", getValues(usuario), "_id=?",
				new String[] { String.valueOf(usuario.getId()) });

		database.close();
	}
	
	public List<Usuario> getUsuarios(){
		List<Usuario> usuarios = new ArrayList<Usuario>();
		
		SQLiteDatabase database = dBHelper.getWritableDatabase();
		
		Cursor cursor = database.rawQuery("SELECT * FROM usuarios", null);
		
		long id;
		String login,nome, sobrenome, senha;
		while (cursor.moveToNext()) {
			id = cursor.getLong(0);
			login = cursor.getString(1);
			senha = cursor.getString(cursor.getColumnIndex("senha"));//exemplo
			nome =  cursor.getString(cursor.getColumnIndex("nome"));
			sobrenome =  cursor.getString(cursor.getColumnIndex("sobrenome"));
			
			
			usuarios.add(new Usuario(id, login, senha,nome,sobrenome));
		}
		
		cursor.close();
		
		database.close();
		
		return usuarios;
	}

	public void remover(String login) {
		SQLiteDatabase database = dBHelper.getWritableDatabase();
		database.delete("usuarios", "login=?", new String[] {String.valueOf(login)});
		
		database.close();
		
	}
	
	public Usuario pesquisar(String login){
		SQLiteDatabase database = dBHelper.getWritableDatabase();
		
		Cursor cursor = database.rawQuery("SELECT * FROM usuarios where login = ?", new String[] {String.valueOf(login)});
		
		Usuario usuario = null;
		if(cursor.moveToNext()){
			long id;
			String nome, sobrenome, senha;
			id = cursor.getLong(0);
			login = cursor.getString(1);
			senha = cursor.getString(cursor.getColumnIndex("senha"));//exemplo
			nome =  cursor.getString(cursor.getColumnIndex("nome"));
			sobrenome =  cursor.getString(cursor.getColumnIndex("sobrenome"));
			
			usuario = new Usuario(id, login, senha,nome,sobrenome);
		}
		
		return usuario;
	}
}
