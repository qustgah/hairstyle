package com.iiijiaban.com.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper{
	/**
	 * ���ݿ�����
	 */
  private static final String DB_NAME="hairstyle.db";
	/**
	 * ���ݿ�汾
	 */
	private static final int VERSION = 1;
	private SQLiteDatabase database;
	//������
	public DbHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	public DbHelper(Context context){
		super(context, DB_NAME, null, VERSION);
	}
//������д�ĸ��෽��
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql="create table hairstyled(id integer primary key autoincrement,isrc varchar(255),msg varchar(255))";
		this.database = db;
		database.execSQL(sql);
	}
	//������д�ĸ��෽��
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists table hairstyled"); 
		onCreate(db);
	}
	public Cursor query() {
		SQLiteDatabase db = getWritableDatabase();
		Cursor c = db.query("hairstyled", null, null, null, null, null, null);
		return c;
	}
	public SQLiteDatabase getSQLiteDatabase() {
		database = getWritableDatabase();
		return database;
	}
/**
 * ����� 
 * @param contentValues
 */
	public void insert(ContentValues contentValues) {
		SQLiteDatabase db = getWritableDatabase();
		db.insert("hairstyled", null, contentValues);
		db.close();
	}
	/**
	 * ɾ���ض��ֶ�
	 * @param path
	 */
	public void del(String path) {
		database = getWritableDatabase();
		database.delete("hairstyled", "isrc=?", new String[] {path});
		database.close();
	}
	/**
	 * �����
	 */
	public void delAll(){
		database = getWritableDatabase();
		database.delete("hairstyled", null, null);
		database.close();
	} 
	
	@Override
	public synchronized void close() {
		if(database!=null){
			database.close();
		}
		super.close();
	}
	
}
