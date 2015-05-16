package br.ifpe.ava.ifmoodledroid.view;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import br.ifpe.ava.ifmoodledroid.R;
import br.ifpe.ava.ifmoodledroid.adapter.NoticiasAdapter;
import br.ifpe.ava.ifmoodledroid.model.Noticia;
import br.ifpe.ava.ifmoodledroid.model.Usuario;

public class NoticiasActivity extends Activity {

	private Usuario usuario;
	private TextView nomeUsuario;
	private ArrayList<Noticia> listNoticias;
	private NoticiasAdapter noticiasAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setContentView(R.layout.noticias_layout);

		usuario = (Usuario) getIntent().getExtras().getSerializable("usuario");

		nomeUsuario = (TextView) findViewById(R.id.nomeUsuario);
		nomeUsuario.setText(usuario.getNome());

		listNoticias = (ArrayList<Noticia>) getIntent().getExtras()
				.getSerializable("listNoticias");

		ListView lvNoticias = (ListView) findViewById(R.id.listNoticias);
		noticiasAdapter = new NoticiasAdapter(this, this.listNoticias);
		lvNoticias.setAdapter(noticiasAdapter);

		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
		

	}

}
