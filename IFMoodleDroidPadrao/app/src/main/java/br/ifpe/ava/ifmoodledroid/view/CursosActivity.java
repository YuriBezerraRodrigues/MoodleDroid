package br.ifpe.ava.ifmoodledroid.view;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import br.ifpe.ava.ifmoodledroid.R;
import br.ifpe.ava.ifmoodledroid.adapter.CursoAdapter;
import br.ifpe.ava.ifmoodledroid.model.Curso;
import br.ifpe.ava.ifmoodledroid.model.Usuario;

public class CursosActivity extends Activity {

	private Usuario usuario;
	private TextView nomeUsuario;
	private ArrayList<Curso> listCursos;
	private CursoAdapter cursoAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setContentView(R.layout.cursos_layout);
		
		usuario = (Usuario) getIntent().getExtras().getSerializable("usuario");
		
		nomeUsuario = (TextView) findViewById(R.id.nomeUsuario);
		nomeUsuario.setText(usuario.getNome());
		
		listCursos = (ArrayList<Curso>) getIntent().getExtras().getSerializable("listCursos");
		
		ListView lvCursos = (ListView) findViewById(R.id.listCursos);
		cursoAdapter = new CursoAdapter(this, this.listCursos);
		lvCursos.setAdapter(cursoAdapter);
		
		lvCursos.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent i = new Intent(CursosActivity.this, SemanasActivity.class);//Semanas e o correto
				i.putExtra("cursoID", ""+arg3);
				i.putExtra("usuario", usuario);
				startActivity(i);
			}
		});
		
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}

}
