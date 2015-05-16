package br.ifpe.ava.ifmoodledroid.adapter;


import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.ifpe.ava.ifmoodledroid.R;
import br.ifpe.ava.ifmoodledroid.model.Usuario;

public class UsuarioAdapter extends BaseAdapter{
	
	private Context ctx;
	private List<Usuario> usuarios;

	public UsuarioAdapter(Context ctx, List<Usuario> usuariosSalvos) {
		// TODO Auto-generated constructor stub
		this.ctx = ctx;
		this.usuarios = usuariosSalvos;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return usuarios.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return usuarios.get(arg0);
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return usuarios.get(arg0).getId();
	}

	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder;

		   Usuario u = usuarios.get(arg0);

		   if (arg1 == null){
			   arg1 = LayoutInflater.from(ctx).inflate(R.layout.item_login_salvo, null);
		 
		      holder = new ViewHolder();
		      holder.txtNome = (TextView) 
		    		  arg1.findViewById(R.id.txtNomeUsuario);
		      holder.txtLogin = (TextView) arg1.findViewById(R.id.txtLogin);
		      
		      
		      arg1.setTag(holder);
		   } else {
		      holder = (ViewHolder)arg1.getTag();
		   }
		   
		   String nome = u.getNome()+ " "+ u.getSobrenome();
		   String login = u.getLogin();
		   if(nome.length() > 12){
			   nome = nome.substring(0, 12);
		   }
		   if(login.length() > 12){
			   login = login.substring(0, 13);
		   }
		   
		   holder.txtNome.setText(nome);
		   holder.txtLogin.setText(login);
		  Log.i("TESTEEEEEEEEE", Integer.toString(usuarios.size()));
		   

		   return arg1;
	}

	static class ViewHolder {
		   TextView txtNome;
		   TextView txtLogin;
	}
	
}
