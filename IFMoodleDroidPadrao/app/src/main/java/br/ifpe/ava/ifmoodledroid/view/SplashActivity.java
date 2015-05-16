package br.ifpe.ava.ifmoodledroid.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import br.ifpe.ava.ifmoodledroid.R;



/**
 * Activiy usada apenas para controlar a tela de Splash.
 * @author Felipe
 * @date 07/09/2013
 * 
 */
public class SplashActivity extends Activity implements Runnable{

	private Handler handler;
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		
		setContentView(R.layout.splash_layout);

		handler = new Handler();
		handler.postDelayed(this, 5000);
	}

	@Override
	protected void onPause() {
		super.onPause();
		
		handler.removeCallbacks(this);
	}

	@Override
	public void run() {
		//Chamada para a Activity de login
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
		finish();
	}
	
	public void carregaSite(View v) {
		
		String txtSite = getResources().getString( R.string.ifpe_site);
		
		Uri uri = Uri.parse(txtSite);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
		
	}

}
