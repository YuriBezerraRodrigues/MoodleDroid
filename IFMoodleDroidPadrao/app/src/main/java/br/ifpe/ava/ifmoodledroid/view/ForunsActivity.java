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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import br.ifpe.ava.ifmoodledroid.R;
import br.ifpe.ava.ifmoodledroid.adapter.ForumAdapter;
import br.ifpe.ava.ifmoodledroid.model.Forum;
import br.ifpe.ava.ifmoodledroid.model.Usuario;

public class ForunsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	private Usuario usuario;
	private TextView nomeUsuario;
	private String semanaID;
	private ArrayList<Forum> lista;
	private ForumAdapter forumAdapter;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setContentView(R.layout.activity_foruns);

		usuario = (Usuario) getIntent().getExtras().getSerializable("usuario");
		semanaID = (String) getIntent().getExtras().getSerializable("semanaID");

		nomeUsuario = (TextView) findViewById(R.id.nomeUsuario);
		nomeUsuario.setText(usuario.getNome());

		JSONObject resultado = JSONRequest.getJSONWebService(
				Constantes.URL_FORUNS_SEMANAS, usuario.getLogin(), usuario.getSenha(),
				"1", new String[] { "semanaId" }, new String[] { semanaID });

		if (resultado == null) {
			Toast.makeText(this, "Nenhum resultado encontrado",
					Toast.LENGTH_LONG).show();
		} else {

			try {

				boolean isArray = resultado.toString().contains("\"forum\":[");

				if (isArray) {
					lista = new ArrayList<Forum>();
					JSONArray array = resultado.getJSONArray("forum");

					for (int i = 0; i < array.length(); i++) {

						JSONObject o = array.getJSONObject(i);
						Forum t = new Forum();

						t.setId(o.getLong("id"));
						t.setNome(o.getString("nome"));

						lista.add(t);
					}
				} else {
					lista = new ArrayList<Forum>();
					JSONObject o = resultado.getJSONObject("forum");
					Forum t = new Forum();

					t.setId(o.getInt("id"));
					t.setNome(o.getString("nome"));

					lista.add(t);
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}

			ListView lvForuns = (ListView) findViewById(R.id.listForuns);
			forumAdapter = new ForumAdapter(this, this.lista);
			lvForuns.setAdapter(forumAdapter);

			lvForuns.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					String ID = ((TextView) arg1.findViewById(R.id.txtID))
							.getText().toString();
					Intent i = new Intent(ForunsActivity.this,
							TopicosForum.class);
					i.putExtra("forumID", ID);
					i.putExtra("usuario", usuario);
					startActivity(i);
				}
			});

			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);
		}
	}

}