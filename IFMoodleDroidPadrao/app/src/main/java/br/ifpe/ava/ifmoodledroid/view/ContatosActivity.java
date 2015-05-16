package br.ifpe.ava.ifmoodledroid.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import util.Constantes;
import util.JSONRequest;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import br.ifpe.ava.ifmoodledroid.R;
import br.ifpe.ava.ifmoodledroid.adapter.ContatoAdapter;
import br.ifpe.ava.ifmoodledroid.model.Usuario;

public class ContatosActivity extends Activity {

	private Usuario usuario;
	private TextView nomeUsuario;
	private ArrayList<Usuario> listContatos;
	private ContatoAdapter contatoAdapter;
	private AutoCompleteTextView novo;
	private Map<String, Long> idContatos;
	private long idContatoSelecionado = -1;
	private String nomeSelecionado = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onResume() {
		super.onResume();
		setContentView(R.layout.tela_contatos);

		usuario = (Usuario) getIntent().getExtras().getSerializable("usuario");

		nomeUsuario = (TextView) findViewById(R.id.nomeUsuario);
		nomeUsuario.setText(usuario.getNome());

		listContatos = (ArrayList<Usuario>) getIntent().getExtras()
				.getSerializable("listContatos");

		ListView lvContatos = (ListView) findViewById(R.id.listaContatos);
		contatoAdapter = new ContatoAdapter(this, this.listContatos, usuario);
		lvContatos.setAdapter(contatoAdapter);

		novo = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		String[] nomes = buscarTodosContatos(usuario);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, nomes);
		novo.setAdapter(adapter);

		novo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int index, long id) {
				nomeSelecionado = (String) adapter.getItemAtPosition(index);
				idContatoSelecionado = idContatos.get(nomeSelecionado);
			}

		});

		Button inserir = (Button) findViewById(R.id.adicionarContato);
		inserir.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (idContatoSelecionado != -1) {
					JSONObject o = JSONRequest.getJSONWebService(
							Constantes.URL_ADD_CONTATO, usuario.getLogin(),
							usuario.getSenha(), "1",
							new String[] { "contatoId" }, new String[] { ""
									+ idContatoSelecionado });

					if (o != null) {
						Toast.makeText(ContatosActivity.this,
								"Contato adicionado com sucesso",
								Toast.LENGTH_LONG).show();
						
						//para dar um refresh mais bonito na tela atualizando a lista de contatos
						Usuario u = new Usuario(idContatoSelecionado, "", "", nomeSelecionado, "");
						listContatos.add(u);
						getIntent().putExtra("listContatos", listContatos);
						startActivity(getIntent());
						finish();
					}else{
						Toast.makeText(ContatosActivity.this,
								"Erro ao realizar operação",
								Toast.LENGTH_LONG).show();
					}
				} else{
					Toast.makeText(ContatosActivity.this,
							"Contato não encontrado",
							Toast.LENGTH_LONG).show();
				}

				idContatoSelecionado = -1;
				nomeSelecionado = "";
			}
		});

		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
	}

	private String[] buscarTodosContatos(Usuario usuario) {
		ArrayList<String> res = new ArrayList<String>();
		idContatos = new HashMap<String, Long>();

		JSONObject o = JSONRequest.getJSONWebService(
				Constantes.URL_BUSCAR_PESSOA, usuario.getLogin(),
				usuario.getSenha(), "1", new String[] { "nome" },
				new String[] { "" });

		boolean isArray = o.toString().contains("\"usuario\":[");

		try {
			if (isArray) {
				JSONArray a = o.getJSONArray("usuario");

				for (int i = 0; i < a.length(); i++) {
					JSONObject u = a.getJSONObject(i);
					res.add(u.getString("nome"));
					idContatos.put(u.getString("nome"), u.getLong("id"));
				}
			} else {
				res.add(o.getJSONObject("usuario").getString("nome"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		String[] ret = new String[res.size()];

		int i = 0;
		for (String s : res) {
			ret[i] = s;
			i++;
		}

		return ret;
	}
}