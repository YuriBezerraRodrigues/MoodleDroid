package br.ifpe.ava.ifmoodledroid.adapter;

import java.util.List;

import org.json.JSONObject;

import util.Constantes;
import util.JSONRequest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import br.ifpe.ava.ifmoodledroid.R;
import br.ifpe.ava.ifmoodledroid.model.Usuario;
import br.ifpe.ava.ifmoodledroid.view.HomeActivity;

public class ContatoAdapter extends BaseAdapter {

	private Context ctx;
	private List<Usuario> contatos;
	private Usuario logado;

	public ContatoAdapter(Context ctx, List<Usuario> contatos) {
		// TODO Auto-generated constructor stub
		this.ctx = ctx;
		this.contatos = contatos;
	}
	
	public ContatoAdapter(Context ctx, List<Usuario> contatos, Usuario logado) {
		// TODO Auto-generated constructor stub
		this.ctx = ctx;
		this.contatos = contatos;
		this.logado = logado;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return contatos.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return contatos.get(arg0);
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return contatos.get(arg0).getId();
	}

	public View getView(final int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder = null;
		final AlertDialog.Builder alerta = new AlertDialog.Builder(this.ctx);
		
		Usuario u = contatos.get(arg0);

		if (arg1 == null) {
			arg1 = LayoutInflater.from(ctx).inflate(
					R.layout.contato_item_layout, null);

			holder = new ViewHolder();
			holder.txtNomeContato = (TextView) arg1
					.findViewById(R.id.txtNomeContato);

			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}

		String nomeContato = u.getNome();
		holder.txtNomeContato.setText(nomeContato);

		//clicks nos botoes
		Button excluir = (Button) arg1.findViewById(R.id.excluir);
		Button bloquear = (Button) arg1.findViewById(R.id.bloquear);
		
		
		excluir.setOnClickListener(new View.OnClickListener() {
			long id = getItemId(arg0);
			
			@Override
			public void onClick(View v) {
			      alerta.setTitle("Confirmação");
			      alerta.setMessage("Confirma a operação?");
			      alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						JSONObject o = JSONRequest.getJSONWebService(Constantes.URL_EXCLUIR_CONTATO, logado.getLogin(), logado.getSenha(), "1", 
								new String[]{"contatoId"}, new String[]{""+id});
						
						if(o==null){
							Toast.makeText(ctx, "Falha na operação", Toast.LENGTH_LONG).show();
						}else{
							Toast.makeText(ctx, "Operação realizada com sucesso", Toast.LENGTH_LONG).show();
							Intent i = new Intent(ctx, HomeActivity.class);
							i.putExtra("usuario", logado);
							ctx.startActivity(i);
						}
					}
			      });
			      
			      alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							//não precisa fazer nada
						}
					});
			      
			      alerta.show();
			}
		});
		
		bloquear.setOnClickListener(new View.OnClickListener() {
			long id = getItemId(arg0);
			
			@Override
			public void onClick(View v) {
				 alerta.setTitle("Confirmação");
			      alerta.setMessage("Confirma a operação?");
			      alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						JSONObject o = JSONRequest.getJSONWebService(Constantes.URL_BLOQUEAR_DESBLOQUEAR_CONTATO, logado.getLogin(), logado.getSenha(), "1", 
								new String[]{"contatoId", "flagBloqueioOuDesbloqueio"}, new String[]{""+id, "true"});
						
						if(o==null){
							Toast.makeText(ctx, "Falha na operação", Toast.LENGTH_LONG).show();
						}else{
							Toast.makeText(ctx, "Operação realizada com sucesso", Toast.LENGTH_LONG).show();
						}
					}
			      });
			      
			      alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							//não precisa fazer nada
						}
					});
			      
			      alerta.show();
			}
		});
		
		
		return arg1;
	}

	static class ViewHolder {
		TextView txtNomeContato;
	}

}