package com.shaunidiot.wwesuperstar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLFunctions {
/*
Set all your tables variables here!
*/
	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_INFO = "info";
	private static final String DATABASE_NAME = "databaseName";
	private static final String DATABASE_TABLE = "tableName";
	private static final int DATABASE_VERSION = 1;

	private DbHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;

	private static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			//Creating tables for the first time
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ROWID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME
					+ " TEXT NOT NULL, " + KEY_INFO + " TEXT NOT NULL, "
					+ KEY_FAV + " TEXT NOT NULL);");
			//Log.e("onCreate", "Created First DB");
			
			/* this is to add values on start up */
			ContentValues cv = new ContentValues();
			cv.put(KEY_END, "0");
			db.insert(DATABASE_ENDTABLE, null, cv);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			//Drop tables if exist, most probably when you update your application
			db.execSQL("DROP TABLE IF EXIST " + DATABASE_NAME);
			onCreate(db);
		}

	}

	public SQLFunctions(Context c) {
		ourContext = c;
	}

	public SQLFunctions open() throws SQLException {
	// open our database so we can write to it
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return null;
	}
	
	public void close() {
	// close our database so we can use it some other time
		if (ourHelper != null) {
			ourHelper.close();
		}
		ourHelper.close();
	}

	public long createEntry(String name, String info) {
	
		// Adding info to our database
		ContentValues cv = new ContentValues();
		cv.put(KEY_NAME, name);
		cv.put(KEY_INFO, info);
		return ourDatabase.insert(DATABASE_TABLE, null, cv);
	}

	public String getName(long l) {
		// TODO Auto-generated method stub
		
		//Getting values from our database
		String[] columns = new String[] { KEY_ROWID, KEY_NAME, KEY_INFO};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "="
				+ l, null, null, null, null);
	//Getting values where ID = l
		if (c != null) {
			if (c.moveToFirst()) { //if exist
				// refer to -> KEY_ROWID + "=" + l
				String name = c.getString(1);
				// id - 0, name - 1; like array
				// Example -> [0] -> ROWID, [1] -> NAME, [2] -> INFO
				c.close();
				return name;
			} else {
				return "ERROR";
			}
		}
		return null;
	}

	public String getInfo(long l) {
		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_ROWID, KEY_NAME, KEY_INFO,
				KEY_FAV };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "="
				+ l, null, null, null, null);

		if (c != null) {
			c.moveToFirst();
			// refer to -> KEY_ROWID + "=" + l
			String info = c.getString(2);
			// id - 0, name - 1; like array
			// Example -> [0] -> ROWID, [1] -> NAME, [2] -> INFO
			c.close();
			return info;
		}
		return null;
	}

	public void setName(String fcurID, String name) {
		// TODO Auto-generated method stub
		//UPdating values in database
		ContentValues cvUpdates = new ContentValues();
		cvUpdates.put(KEY_NAME, name);
		ourDatabase.update(DATABASE_TABLE, cvUpdates, KEY_ROWID + "=" + fcurID,
				null);
	}

	public int getHighestId() {
		int id = 0;
		final String MY_QUERY = "SELECT MAX(_id) FROM " + DATABASE_TABLE;
		Cursor mCursor = ourDatabase.rawQuery(MY_QUERY, null);
		try {
			if (mCursor.getCount() > 0) {
				mCursor.moveToFirst();
				id = mCursor.getInt(0);
			}
		} catch (Exception e) {
			Log.e("Get Last ID", "Error - " + e.getMessage().toString());
		}
		return id;

	}

}
