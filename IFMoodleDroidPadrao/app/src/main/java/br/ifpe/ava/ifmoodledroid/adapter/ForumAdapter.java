package br.ifpe.ava.ifmoodledroid.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.ifpe.ava.ifmoodledroid.R;
import br.ifpe.ava.ifmoodledroid.model.Forum;

public class ForumAdapter extends BaseAdapter{
	
	private Context ctx;
	private List<Forum> foruns;
	
	public ForumAdapter(Context ctx, List<Forum> foruns) {

		this.ctx = ctx;
		this.foruns = foruns;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return foruns.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return foruns.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return foruns.get(arg0).getId();
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		
		ViewHolder holder = null;
		
		Forum forum = foruns.get(arg0);
		
		if(arg1 == null){
			arg1 = LayoutInflater.from(ctx).inflate(R.layout.geral_item_layout, null);
			
			holder = new ViewHolder();
			holder.txtNome = (TextView) arg1.findViewById(R.id.txtNome);
			holder.txtID = (TextView) arg1.findViewById(R.id.txtID);
			
			
			arg1.setTag(holder);
		}else{
			holder = (ViewHolder)arg1.getTag();
		}
		
		String nomeCurso = forum.getNome();
		long id = forum.getId();
		holder.txtNome.setText(nomeCurso);
		holder.txtID.setText(""+id);
		
		return arg1;
	}
	
	static class ViewHolder {
		   TextView txtNome;
		   TextView txtID;
	}

}
