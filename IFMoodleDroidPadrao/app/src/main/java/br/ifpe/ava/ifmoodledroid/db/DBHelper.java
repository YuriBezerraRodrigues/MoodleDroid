package br.ifpe.ava.ifmoodledroid.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	
	public static final String DBNAME = "dbUsuarios";
	public static final int DBVERSION = 3;
	
	
	public DBHelper(Context context) {
		super(context, DBNAME, null, DBVERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE usuarios (_id INTEGER PRIMARY KEY " +
				"AUTOINCREMENT, login TEXT UNIQUE NOT NULL, senha TEXT NOT NULL, nome TEXT NOT NULL, sobrenome TEXT NOT NULL)");

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
