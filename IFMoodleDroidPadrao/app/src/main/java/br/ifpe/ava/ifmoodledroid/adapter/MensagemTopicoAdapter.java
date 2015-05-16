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
import br.ifpe.ava.ifmoodledroid.model.MensagemTopico;

public class MensagemTopicoAdapter extends BaseAdapter {

	private Context ctx;
	private List<MensagemTopico> lista;

	public MensagemTopicoAdapter(Context ctx, List<MensagemTopico> lista) {
		// TODO Auto-generated constructor stub
		this.ctx = ctx;
		this.lista = lista;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return lista.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return lista.get(arg0);
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return lista.get(arg0).getIdTopico();
	}

	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder = null;

		MensagemTopico t = lista.get(arg0);

		if (arg1 == null) {
			arg1 = LayoutInflater.from(ctx).inflate(
					R.layout.geral_item_layout, null);

			holder = new ViewHolder();
			holder.txtNome = (TextView) arg1
					.findViewById(R.id.txtNome);
			holder.txtID = (TextView) arg1
					.findViewById(R.id.txtID);

			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}

		//usar Spanned apenas quando for HTML, quando não usar STRING normal
		Spanned nome = Html.fromHtml(t.getMensagem());
		int i = t.getIdDeQuemPostou();
		holder.txtNome.setText(nome);
		holder.txtID.setText(""+i);

		return arg1;
	}

	static class ViewHolder {
		TextView txtNome;
		TextView txtID;
	}

}