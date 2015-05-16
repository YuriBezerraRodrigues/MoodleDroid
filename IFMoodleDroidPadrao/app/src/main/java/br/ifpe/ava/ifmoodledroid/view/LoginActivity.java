package br.ifpe.ava.ifmoodledroid.view;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import util.Constantes;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import br.ifpe.ava.ifmoodledroid.R;
import br.ifpe.ava.ifmoodledroid.adapter.UsuarioAdapter;
import br.ifpe.ava.ifmoodledroid.db.DBHelper;
import br.ifpe.ava.ifmoodledroid.db.RepositorioUsuario;
import br.ifpe.ava.ifmoodledroid.handler.UsuarioHandler;
import br.ifpe.ava.ifmoodledroid.model.Usuario;

public class LoginActivity extends Activity {

	private EditText edtUsername;
	private EditText edtSenha;
	private CheckBox cbLembrar;
	private String login;
	private String senha;
	private List<Usuario> usuariosSalvos = null;
	private UsuarioAdapter usuarioAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	private void carregaTelaLoginSalvo() {
		 setContentView(R.layout.login_salvo_layout);
		 
		 ListView listLogin = (ListView) findViewById(R.id.listView1);
			usuarioAdapter = new UsuarioAdapter(this, usuariosSalvos);
			listLogin.setAdapter(usuarioAdapter);		
	}

	@Override
	protected void onResume() {

		super.onResume();
		RepositorioUsuario repositorioUsuario = new RepositorioUsuario(
				new DBHelper(this));
		usuariosSalvos = repositorioUsuario.getUsuarios();

		if (usuariosSalvos != null && usuariosSalvos.size() > 0) {
			carregaTelaLoginSalvo();

		} else {
			carregaTelaLoginPrincipal();
		}
		
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}
	
	public void btConfirmarPressed(View v){
		
		LinearLayout linearLayout= (LinearLayout) v.getParent().getParent().getParent();
		TextView textLogin = (TextView) linearLayout.findViewById(R.id.txtLogin);
		
		Usuario usuario = UsuarioHandler.pesquisarUsuarioSalvo(textLogin.getText().toString(), this);
		
		new DownloadJsonAsyncTask().execute(Constantes.URL_VERIFICA_USUARIO, usuario.getLogin(), usuario.getSenha(), "0", "1");

	}
	
	public void btdeletarPressed(View view){
		
		LinearLayout linearLayout= (LinearLayout) view.getParent().getParent().getParent();
		TextView textLogin = (TextView) linearLayout.findViewById(R.id.txtLogin);
		
		UsuarioHandler.removerUsuarioSalvo(textLogin.getText().toString(), this);
		
		onResume();
	}
	
	public void outroUsuario(View view){
		
		setContentView(R.layout.login_layout_auxiliar);
		edtUsername = (EditText) findViewById(R.id.edtLogin);
		edtSenha = (EditText) findViewById(R.id.edtSenha);
		cbLembrar = (CheckBox) findViewById(R.id.cBLembrar);
		
	}
	
	private void carregaTelaLoginPrincipal(){
		setContentView(R.layout.login_layout);
		edtUsername = (EditText) findViewById(R.id.edtLogin);
		edtSenha = (EditText) findViewById(R.id.edtSenha);
		cbLembrar = (CheckBox) findViewById(R.id.cBLembrar);
	}
	
	public void clickSair(View v){
		onBackPressed();
	}
	
	public void voltarPressed(View view){
		carregaTelaLoginSalvo();
	}
	
	public void clickAcessar(View v){
		login = edtUsername.getText().toString();
		senha = edtSenha.getText().toString();
		int flagSalvar = 0;

		if (cbLembrar.isChecked()) {
			flagSalvar = 1;
		}

		if (login == null || login.equals("") || senha == null
				|| senha.equals("")) {
			Toast.makeText(this, "Por favor, digite seus dados de acesso.",
					Toast.LENGTH_SHORT).show();
		} else {
			new DownloadJsonAsyncTask().execute(Constantes.URL_VERIFICA_USUARIO, login, senha, Integer.toString(flagSalvar), "0");
		}
	}
	
	
	/*
	 * Classe interna Respons�vel por rodar em background e fazer o Download
	 * do arquivo Json
	 * 
	*/
	class DownloadJsonAsyncTask extends AsyncTask<String, Void, JSONObject> {

		private ProgressDialog dialog;
		
		
		String flagSalvar = "";
		String login = "";
		String senha = "";
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = ProgressDialog.show(LoginActivity.this, "Aguarde",
					"Fazendo login...");
		}

		@Override
		protected JSONObject doInBackground(String... params) {

			login = params[1];
			senha = params[2];
			flagSalvar = params[3];
			String result = getRESTFileContent(params[0], login, senha, params[4]);
			

			Log.i("URL URL URL URL URL", params[0]);

			JSONObject jsonObject = null;

			if (result == null) {
				Log.e("LogandoIFMoodleDroid", "Falha ao acessar WS");
				return null;
			} else {
				Log.i("Result Retornado", result);
			}

			try {
				jsonObject = new JSONObject(result);

			} catch (Exception e) {
				// TODO: handle exception
			}
			return jsonObject;
		}

		protected void onPostExecute(JSONObject result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog.dismiss();

			if (result != null) {

				Intent intent = new Intent(LoginActivity.this,
						HomeActivity.class);
				Usuario usuario = new Usuario();
				try {
					usuario.setNome(result.getString("nome"));
					usuario.setSobrenome(result.getString("sobrenome"));
					usuario.setSenha(result.getString("senha"));
					usuario.setLogin(result.getString("login"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				intent.putExtra("usuario", usuario);

				if (Integer.parseInt(flagSalvar) == 1) {
					Log.i("HORA DE SALVAR NO BANCO", "SALVAR");
					Usuario usuario2 = null;
					try {
						usuario2 = new Usuario(result.getString("login"),
								result.getString("senha"),
								result.getString("nome"),
								result.getString("sobrenome"));
						Log.i("SALVANDO NO BANCO", usuario2.getNome() + " "
								+ usuario2.getSobrenome());
					} catch (JSONException e) {
						e.printStackTrace();
					}
					RepositorioUsuario repositorioUsuario = new RepositorioUsuario(
							new DBHelper(LoginActivity.this));
					repositorioUsuario.inserir(usuario2);
				}

				startActivity(intent);
			} else {

				Toast.makeText(LoginActivity.this,
						"Dados de acesso incorretos.", Toast.LENGTH_SHORT)
						.show();
			}
		}
		
		private String toString(InputStream is) throws IOException {

			byte[] bytes = new byte[1024];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int lidos;
			while ((lidos = is.read(bytes)) > 0) {
				baos.write(bytes, 0, lidos);
			}
			return new String(baos.toByteArray());
		}
		
		public String getRESTFileContent(String url, String loginUsuario, String senhaUsuario, String flagUsuarioSalvo) {
			HttpClient httpclient = new DefaultHttpClient();
			
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
			nameValuePairs.add(new BasicNameValuePair("login", loginUsuario));
			nameValuePairs.add(new BasicNameValuePair("senha", senhaUsuario));
			nameValuePairs.add(new BasicNameValuePair("flagDecriptacao", flagUsuarioSalvo));

			try {
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(httpPost);

				HttpEntity entity = response.getEntity();

				if (entity != null) {
					InputStream instream = entity.getContent();
					String result = toString(instream);

					instream.close();
					return result;
				}
			} catch (Exception e) {
				Log.e("IFMoodleDROID", "Falha ao acessar Web service", e);
			}
			return null;
		}
	}
}
