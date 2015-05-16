package br.ifpe.ava.ifmoodledroid.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.ifpe.ava.ifmoodledroid.R;
import br.ifpe.ava.ifmoodledroid.model.Semanas;

public class SemanasAdapter extends BaseAdapter{
	
	private Context ctx;
	private List<Semanas> semanas;
	
	public SemanasAdapter(Context ctx, List<Semanas> semanas) {

		this.ctx = ctx;
		this.semanas = semanas;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return semanas.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return semanas.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return semanas.get(arg0).getId();
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		
		ViewHolder holder = null;
		
		Semanas s = semanas.get(arg0);
		
		if(arg1 == null){
			arg1 = LayoutInflater.from(ctx).inflate(R.layout.geral_item_layout, null);
			
			holder = new ViewHolder();
			holder.txtNome = (TextView) arg1.findViewById(R.id.txtNome);
			holder.txtID = (TextView) arg1.findViewById(R.id.txtID);
			
			
			arg1.setTag(holder);
		}else{
			holder = (ViewHolder)arg1.getTag();
		}
		
		String nomeSemana = s.getNameSection();
		long id = s.getId();
		holder.txtNome.setText(nomeSemana);
		holder.txtID.setText(""+id);
		
		return arg1;
	}
	
	static class ViewHolder {
		   TextView txtNome;
		   TextView txtID;
	}

}
