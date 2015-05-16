package br.ifpe.ava.ifmoodledroid.adapter;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.ifpe.ava.ifmoodledroid.R;
import br.ifpe.ava.ifmoodledroid.model.Noticia;

public class NoticiasAdapter extends BaseAdapter{

	private Context ctx;
	private List<Noticia> noticias;
	
	public NoticiasAdapter(Context ctx, List<Noticia> noticias) {
		this.ctx = ctx;
		this.noticias = noticias;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return noticias.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return noticias.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return noticias.get(arg0).getId();
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		
		Noticia noticia = noticias.get(arg0);
		
		if(arg1 == null){
			arg1 = LayoutInflater.from(ctx).inflate(R.layout.noticias_item_layout, null);
			
			holder = new ViewHolder();
			holder.txtTituloNoticia = (TextView) arg1.findViewById(R.id.txtTituloNoticia);
			holder.txtAutorNoticia = (TextView) arg1.findViewById(R.id.txtAutorNoticia);
			holder.txtMsgNoticia = (TextView) arg1.findViewById(R.id.txtMsgNoticia);
			
			arg1.setTag(holder);
			
		}else{
			holder = (ViewHolder)arg1.getTag();
		}
		
		String titulo = noticia.getTitulo();
		String autor = noticia.getAutor();
		Spanned mensagem = Html.fromHtml(noticia.getMensagem());
		
		holder.txtTituloNoticia.setText(titulo);
		holder.txtAutorNoticia.setText(autor);
		holder.txtMsgNoticia.setText(mensagem);
		
		return arg1;
	}

	static class ViewHolder {
		   TextView txtTituloNoticia;
		   TextView txtAutorNoticia;
		   TextView txtMsgNoticia;
	}
	
}
