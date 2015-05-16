
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
import br.ifpe.ava.ifmoodledroid.adapter.SemanasAdapter;
import br.ifpe.ava.ifmoodledroid.model.Semanas;
import br.ifpe.ava.ifmoodledroid.model.Usuario;
public class SemanasActivity extends Activity {
    private Usuario usuario;
    private TextView nomeUsuario;
    private ArrayList<Semanas> lista;
    private String cursoID;
    private SemanasAdapter semanasAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
    }
    
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        setContentView(R.layout.activity_semanas);
        
        usuario = (Usuario) getIntent().getExtras().getSerializable("usuario");
        cursoID = (String) getIntent().getExtras().getSerializable("cursoID");
        
        nomeUsuario = (TextView) findViewById(R.id.nomeUsuario);
        nomeUsuario.setText(usuario.getNome());
        
        JSONObject resultado = JSONRequest.getJSONWebService(
                Constantes.URL_SEMANAS, usuario.getLogin(), usuario.getSenha(),
                "1", new String[] { "idCurso" }, new String[] { cursoID });
        if (resultado == null) {
            Toast.makeText(this, "Nenhum resultado encontrado",
                    Toast.LENGTH_LONG).show();
        } else {
            try {
                boolean isArray = resultado.toString().contains("\"semanas\":[");
                if (isArray) {
                    lista = new ArrayList<Semanas>();
                    JSONArray array = resultado.getJSONArray("semanas");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject o = array.getJSONObject(i);
                        Semanas t = new Semanas();
                        //setar os campos
                        t.setCourse(o.getLong("course"));
                        t.setId(o.getLong("id"));
                        t.setIdCourse(o.getLong("idCourse"));
                        t.setIdSection(o.getLong("idSection"));
                        t.setNameSection("Semana "+i+" - " + o.getString("nameSection").replaceAll("<p>", "").replaceAll("</p>", ""));

                        lista.add(t);
                    }
                } else {
                    lista = new ArrayList<Semanas>();
                    JSONObject o = resultado.getJSONObject("semanas");
                    Semanas t = new Semanas();
                    
                    //setar os campos
                    t.setCourse(o.getLong("course"));
                    t.setId(o.getLong("id"));
                    t.setIdCourse(o.getLong("idCourse"));
                    t.setIdSection(o.getLong("idSection"));
                    t.setNameSection("Semana 1 - " + o.getString("nameSection").replace("<p>", "").replace("</p>", ""));
                    
                    lista.add(t);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ListView lvSemanas = (ListView) findViewById(R.id.list);
            semanasAdapter = new SemanasAdapter(this, this.lista);
            lvSemanas.setAdapter(semanasAdapter);
            lvSemanas.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                        int arg2, long arg3) {
                    Intent i = new Intent(SemanasActivity.this,
                            ForunsActivity.class);
                    String semanaId = ((TextView) arg1.findViewById(R.id.txtID)).getText().toString();
                    i.putExtra("semanaID", ""+semanaId);
                    i.putExtra("usuario", usuario);
                    startActivity(i);
                }
            });
            overridePendingTransition(android.R.anim.fade_in,
                    android.R.anim.fade_out);
        }
    }
}