package br.ifpe.ava.ifmoodledroid.view;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import util.Constantes;
import util.JSONRequest;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import br.ifpe.ava.ifmoodledroid.R;
import br.ifpe.ava.ifmoodledroid.adapter.MensagemTopicoAdapter;
import br.ifpe.ava.ifmoodledroid.model.MensagemTopico;
import br.ifpe.ava.ifmoodledroid.model.Usuario;

public class DiscussaoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	private Usuario usuario;
	private TextView nomeUsuario;
	private String topicoID;
	private MensagemTopicoAdapter adapter;
	private ArrayList<MensagemTopico> lista;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setContentView(R.layout.activity_topicos_forum);

		usuario = (Usuario) getIntent().getExtras().getSerializable("usuario");
		topicoID = (String) getIntent().getExtras().getSerializable("topicoID");

		nomeUsuario = (TextView) findViewById(R.id.nomeUsuario);
		nomeUsuario.setText(usuario.getNome());

		JSONObject resultado = JSONRequest.getJSONWebService(
				Constantes.URL_DISCUSSAO_TOPICOS, usuario.getLogin(),
				usuario.getSenha(), "1", new String[] { "topicoId" },
				new String[] { topicoID });

		if (resultado == null) {
			Toast.makeText(this, "Nenhum resultado encontrado",
					Toast.LENGTH_LONG).show();
		} else {

			try {

				boolean isArray = resultado.toString()
						.contains("\"mensagemTopico\":[");

				if (isArray) {
					lista = new ArrayList<MensagemTopico>();
					JSONArray array = resultado.getJSONArray("mensagemTopico");

					for (int i = 0; i < array.length(); i++) {

						JSONObject o = array.getJSONObject(i);
						MensagemTopico d = new MensagemTopico();

						d.setIdDeQuemPostou(o.getInt("idDeQuemPostou"));
						d.setIdMensagem(o.getInt("idMensagem"));
						d.setIdTopico(o.getInt("idTopico"));
						d.setMensagem(o.getString("mensagem"));

						lista.add(d);
					}
				} else {
					lista = new ArrayList<MensagemTopico>();
					JSONObject o = resultado.getJSONObject("mensagemTopico");

					MensagemTopico d = new MensagemTopico();

					d.setIdDeQuemPostou(o.getInt("idDeQuemPostou"));
					d.setIdMensagem(o.getInt("idMensagem"));
					d.setIdTopico(o.getInt("idTopico"));
					d.setMensagem(o.getString("mensagem"));

					lista.add(d);
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}

			ListView lista = (ListView) findViewById(R.id.lista);
			adapter = new MensagemTopicoAdapter(this, this.lista);
			lista.setAdapter(adapter);
			
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);
		}
	}

}