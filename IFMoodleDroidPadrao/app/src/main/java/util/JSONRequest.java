package util;

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
import org.json.JSONObject;

import android.util.Log;

public class JSONRequest {
	/**
	 * @param url
	 * @param loginUsuario
	 * @param senhaUsuario
	 * @param flagUsuarioSalvo
	 * @param campos = colocar um array vazio para nada
	 * @param valores colocar um array vazio para nada
	 * @return
	 */
	public static JSONObject getJSONWebService(String url, String loginUsuario, String senhaUsuario, String flagUsuarioSalvo,
			String[] campos, String[] valores) {
		HttpClient httpclient = new DefaultHttpClient();
		
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("login", loginUsuario));
		nameValuePairs.add(new BasicNameValuePair("senha", senhaUsuario));
		nameValuePairs.add(new BasicNameValuePair("flagDecriptacao", flagUsuarioSalvo));
		
		for (int i = 0; i < campos.length; i++) {
			nameValuePairs.add(new BasicNameValuePair(campos[i], valores[i]));
		}

		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httpPost);

			HttpEntity entity = response.getEntity();

			if (entity != null) {
				InputStream instream = entity.getContent();
				String result = toString(instream);

				instream.close();
				return 	new JSONObject(result);
			}
		} catch (Exception e) {
			Log.e("IFMoodleDROID", "Falha ao acessar Web service", e);
		}
		return null;
	}
	
	private static String toString(InputStream is) throws IOException {

		byte[] bytes = new byte[1024];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int lidos;
		while ((lidos = is.read(bytes)) > 0) {
			baos.write(bytes, 0, lidos);
		}
		return new String(baos.toByteArray());
	}

}
