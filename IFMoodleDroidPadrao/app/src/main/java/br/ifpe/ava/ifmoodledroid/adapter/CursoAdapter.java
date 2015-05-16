package br.ifpe.ava.ifmoodledroid.adapter;

import java.util.List;

import br.ifpe.ava.ifmoodledroid.R;
import br.ifpe.ava.ifmoodledroid.model.Curso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CursoAdapter extends BaseAdapter{
	
	private Context ctx;
	private List<Curso> cursos;
	
	public CursoAdapter(Context ctx, List<Curso> cursos) {

		this.ctx = ctx;
		this.cursos = cursos;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return cursos.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return cursos.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return cursos.get(arg0).getId();
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		
		ViewHolder holder = null;
		
		Curso curso = cursos.get(arg0);
		
		if(arg1 == null){
			arg1 = LayoutInflater.from(ctx).inflate(R.layout.cursos_item_layout, null);
			
			holder = new ViewHolder();
			holder.txtNomeCurso = (TextView) arg1.findViewById(R.id.txtNomeCurso);
			
			arg1.setTag(holder);
		}else{
			holder = (ViewHolder)arg1.getTag();
		}
		
		String nomeCurso = curso.getNome();
		holder.txtNomeCurso.setText(nomeCurso);
		
		return arg1;
	}
	
	static class ViewHolder {
		   TextView txtNomeCurso;
	}

}
