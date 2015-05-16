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
import org.json.JSONArray;
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
import android.widget.TextView;
import android.widget.Toast;
import br.ifpe.ava.ifmoodledroid.R;
import br.ifpe.ava.ifmoodledroid.model.Curso;
import br.ifpe.ava.ifmoodledroid.model.Forum;
import br.ifpe.ava.ifmoodledroid.model.Noticia;
import br.ifpe.ava.ifmoodledroid.model.Usuario;

public class HomeActivity extends Activity {

	private Usuario usuario;
	private TextView nomeUsuario;
	String url = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setContentView(R.layout.home_layout);
		
		usuario = (Usuario) getIntent().getExtras().getSerializable("usuario");
		
		nomeUsuario = (TextView) findViewById(R.id.nomeUsuario);
		nomeUsuario.setText(usuario.getNome());
	}

	public void clickNoticias(View v){
		url = Constantes.URL_VERIFICA_NOTICIAS;
		new DownloadJsonAsyncTask().execute(Constantes.URL_VERIFICA_NOTICIAS, usuario.getLogin(), usuario.getSenha(), "0", "1");
	}
	public void clickCursos(View v){
		url = Constantes.URL_CURSOS;
		new DownloadJsonAsyncTask().execute(Constantes.URL_CURSOS, usuario.getLogin(), usuario.getSenha(), "0", "1");
	}
	
	public void clickMensagens(View v){
		url = Constantes.URL_CONTATOS;
		new DownloadJsonAsyncTask().execute(Constantes.URL_CONTATOS, usuario.getLogin(), usuario.getSenha(), "0", "1");
	}
	
	//mudar isso não faz mais sentido
	public void clickContatos(View v){
		
	}
	class DownloadJsonAsyncTask extends AsyncTask<String, Void, JSONObject> {

		private ProgressDialog dialog;
		
		
		String flagSalvar = "";
		String login = "";
		String senha = "";
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if(url.equals(Constantes.URL_VERIFICA_NOTICIAS)){
				dialog = ProgressDialog.show(HomeActivity.this, "Aguarde",
					"Buscando Notícias Recentes...");
			}else if(url.equals(Constantes.URL_CURSOS)){
				dialog = ProgressDialog.show(HomeActivity.this, "Aguarde",
						"Buscando Cursos...");
			}else if(url.equals(Constantes.URL_CONTATOS)){
				dialog = ProgressDialog.show(HomeActivity.this, "Aguarde",
						"Buscando Contatos...");
			}else if(url.equals(Constantes.URL_FORUNS)){
				dialog = ProgressDialog.show(HomeActivity.this, "Aguarde",
						"Buscando Fóruns...");
			}
		}

		@Override
		protected JSONObject doInBackground(String... params) {

			login = params[1];
			senha = params[2];
			flagSalvar = params[3];

			//testar a URL para poder montar o getRESTFileContent CORRETO
			
			String result = getRESTFileContent(url, login, senha, params[4]);

			Log.i("URL URL URL URL URL", params[0]);

			JSONObject jsonObject = null;

			if (result == null) {
				Log.e("HomeActivity", "Falha ao acessar WS");
				return null;
			} else {
				Log.i("Result Retornado", result);
			}

			try {
				jsonObject = new JSONObject(result);

			} catch (Exception e) {
				Toast.makeText(HomeActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
			}
			return jsonObject;
		}

		protected void onPostExecute(JSONObject result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog.dismiss();

			
			if (result != null) {
				if(url.equals(Constantes.URL_VERIFICA_NOTICIAS)){
					Intent intent = new Intent(HomeActivity.this,
							NoticiasActivity.class);
					JSONArray arrayPostagens;
					ArrayList<Noticia> listNoticias = new ArrayList<Noticia>();
					Noticia noticiaRetornada = new Noticia();

					boolean isArray = result.toString().contains("\"postagem\":[");
					
					try {						
						if(isArray){
							arrayPostagens = result.getJSONArray("postagem");
							
							for(int i = 0; i < arrayPostagens.length(); i++){

								JSONObject postagem = arrayPostagens.getJSONObject(i);
								noticiaRetornada = new Noticia();
								
								noticiaRetornada.setId(postagem.getLong("id"));
								noticiaRetornada.setTitulo(postagem.getString("titulo"));
								noticiaRetornada.setMensagem(postagem.getString("mensagem"));
								noticiaRetornada.setAutor(postagem.getString("autor"));
								
								listNoticias.add(noticiaRetornada);
							}
						}else {
							JSONObject post = result.getJSONObject("postagem");
							
							noticiaRetornada.setId(post.getLong("id"));
							noticiaRetornada.setTitulo(post.getString("titulo"));
							noticiaRetornada.setMensagem(post.getString("mensagem"));
							noticiaRetornada.setAutor(post.getString("autor"));
							
							listNoticias.add(noticiaRetornada);
						}
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
					intent.putExtra("usuario", usuario);
					intent.putExtra("listNoticias", listNoticias);
					
					startActivity(intent);
					
				}else if(url.equals(Constantes.URL_CURSOS)){
					
					Intent intent = new Intent(HomeActivity.this,
							CursosActivity.class);
					JSONArray arrayCursos;
					ArrayList<Curso> listCursos = new ArrayList<Curso>();
					Curso cursoRetornado;

					boolean isArray = result.toString().contains("\"curso\":[");
					
					try {						
						if(isArray){
							arrayCursos = result.getJSONArray("curso");
							
							for(int i = 0; i < arrayCursos.length(); i++){
								JSONObject curso = arrayCursos.getJSONObject(i);
								cursoRetornado = new Curso();
								
								cursoRetornado.setId(curso.getLong("id"));
								cursoRetornado.setNome(curso.getString("nome"));
								
								listCursos.add(cursoRetornado);
							}	
						}else {
								JSONObject curso  = result.getJSONObject("curso");
								cursoRetornado = new Curso();
								
								cursoRetornado.setId(curso.getLong("id"));
								cursoRetornado.setNome(curso.getString("nome"));
								
								listCursos.add(cursoRetornado);
							}
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
					intent.putExtra("usuario", usuario);
					intent.putExtra("listCursos", listCursos);
					startActivity(intent);
					
				} else if(url.equals(Constantes.URL_CONTATOS)){
					
					Intent intent = new Intent(HomeActivity.this,
							ContatosActivity.class);
					JSONArray arrayContatos;
					ArrayList<Usuario> listContatos = new ArrayList<Usuario>();
					Usuario contatoRetornado;
					
					boolean isArray = result.toString().contains("\"usuario\":[");
					
					try {						
						if(isArray){
							arrayContatos= result.getJSONArray("usuario");
							
							for(int i = 0; i < arrayContatos.length(); i++){
								JSONObject usuario = arrayContatos.getJSONObject(i);
								contatoRetornado = new Usuario();
								
								contatoRetornado.setId(usuario.getLong("id"));
								contatoRetornado.setNome(usuario.getString("nome"));
								
								listContatos.add(contatoRetornado);
							}	
						}else{
								JSONObject usuario = result.getJSONObject("usuario");
								contatoRetornado = new Usuario();
								
								contatoRetornado.setId(usuario.getLong("id"));
								contatoRetornado.setNome(usuario.getString("nome"));
								
								listContatos.add(contatoRetornado);
							}
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
					intent.putExtra("usuario", usuario);
					intent.putExtra("listContatos", listContatos);
					startActivity(intent);
					
				} else if(url.equals(Constantes.URL_FORUNS)){
					
					Intent intent = new Intent(HomeActivity.this,
							ForunsActivity.class);
					JSONArray arrayForuns;
					ArrayList<Forum> listForuns = new ArrayList<Forum>();
					Forum retornado;
					
					boolean isArray = result.toString().contains("\"forum\":[");
					
					try {						
						if(isArray){
							arrayForuns= result.getJSONArray("forum");
							
							for(int i = 0; i < arrayForuns.length(); i++){
								JSONObject forum = arrayForuns.getJSONObject(i);
								retornado = new Forum();
								
								retornado.setId(forum.getLong("id"));
								retornado.setNome(forum.getString("nome"));
								
								listForuns.add(retornado);
							}	
						}else{
							JSONObject usuario = result.getJSONObject("forum");
							retornado = new Forum();
							
							retornado.setId(usuario.getLong("id"));
							retornado.setNome(usuario.getString("nome"));
							
							listForuns.add(retornado);
							}
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
					intent.putExtra("usuario", usuario);
					intent.putExtra("listForuns", listForuns);
					startActivity(intent);
				}


			} else {

				Toast.makeText(HomeActivity.this,
						"Sem resultados.", Toast.LENGTH_SHORT)
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
