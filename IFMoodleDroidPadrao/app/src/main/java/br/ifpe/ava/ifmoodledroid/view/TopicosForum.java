package br.ifpe.ava.ifmoodledroid.view;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import util.Constantes;
import util.JSONRequest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import br.ifpe.ava.ifmoodledroid.R;
import br.ifpe.ava.ifmoodledroid.adapter.TopicosAdapter;
import br.ifpe.ava.ifmoodledroid.model.Topicos;
import br.ifpe.ava.ifmoodledroid.model.Usuario;

public class TopicosForum extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	private Usuario usuario;
	private TextView nomeUsuario;
	private String forumID;
	private TopicosAdapter adapter;
	private ArrayList<Topicos> lista;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setContentView(R.layout.activity_topicos_forum);

		usuario = (Usuario) getIntent().getExtras().getSerializable("usuario");
		forumID = (String) getIntent().getExtras().getSerializable("forumID");

		nomeUsuario = (TextView) findViewById(R.id.nomeUsuario);
		nomeUsuario.setText(usuario.getNome());

		JSONObject resultado = JSONRequest.getJSONWebService(
				Constantes.URL_TOPICOS_FORUM, usuario.getLogin(),
				usuario.getSenha(), "1", new String[] { "forumId" },
				new String[] { forumID });

		if (resultado == null) {
			Toast.makeText(this, "Nenhum resultado encontrado",
					Toast.LENGTH_LONG).show();
		} else {

			try {

				boolean isArray = resultado.toString()
						.contains("\"topicos\":[");

				if (isArray) {
					lista = new ArrayList<Topicos>();
					JSONArray array = resultado.getJSONArray("topicos");

					for (int i = 0; i < array.length(); i++) {

						JSONObject o = array.getJSONObject(i);
						Topicos t = new Topicos();

						t.setIdCurso(o.getInt("idCurso"));
						t.setIdForum(o.getInt("idForum"));
						t.setIdTopico(o.getInt("idTopico"));
						t.setNomeTopico(o.getString("nomeTopico"));

						lista.add(t);
					}
				} else {
					lista = new ArrayList<Topicos>();
					JSONObject o = resultado.getJSONObject("topicos");

					Topicos t = new Topicos();

					t.setIdCurso(o.getInt("idCurso"));
					t.setIdForum(o.getInt("idForum"));
					t.setIdTopico(o.getInt("idTopico"));
					t.setNomeTopico(o.getString("nomeTopico"));

					lista.add(t);
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}

			ListView lista = (ListView) findViewById(R.id.lista);
			adapter = new TopicosAdapter(this, this.lista);
			lista.setAdapter(adapter);
			
			lista.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					String ID = ((TextView) arg1.findViewById(R.id.txtID))
							.getText().toString();
					Intent i = new Intent(TopicosForum.this,
							DiscussaoActivity.class);
					i.putExtra("topicoID", ID);
					i.putExtra("usuario", usuario);
					startActivity(i);
				}
			});

			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);
		}
	}

}