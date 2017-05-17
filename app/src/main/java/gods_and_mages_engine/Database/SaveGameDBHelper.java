package gods_and_mages_engine.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static gods_and_mages_engine.Database.SaveGameContract.SaveEntry.*;
import static gods_and_mages_engine.Database.SaveGameContract.StatsEntry.*;

public class SaveGameDBHelper extends SQLiteOpenHelper{
	public static final int DATABASE_VERSION= 2;
	public static final String DATABASE_NAME= "HomecomingSaveGame.db";
	
	//region Command Constants
	private static final String SQL_CREATE_SAVES_TABLE=
			"CREATE TABLE "+ SAVES_TABLE +" (" +
					SAVES_COLUMN_SAVE_ID +" INTEGER PRIMARY KEY,"+
					SAVES_COLUMN_NAME +" TEXT NOT NULL,"+
					SAVES_COLUMN_RACE +" TEXT,"+
					SAVES_COLUMN_CLASS +" TEXT,"+
					SAVES_COLUMN_JOB +" TEXT)";
	private static final String SQL_CREATE_STATS_TABLE=
			"CREATE TABLE "+ STATS_TABLE +" ("+
					STATS_COLUMN_SAVE_ID +" INTEGER PRIMARY KEY,"+
					STATS_COLUMN_MAXHP +" INTEGER,"+
					STATS_COLUMN_STRENGTH +" INTEGER,"+
					STATS_COLUMN_STAMINA +" INTEGER,"+
					STATS_COLUMN_AGILITY +" INTEGER,"+
					STATS_COLUMN_SPEED +" INTEGER)";
	private static final String SQL_DELETE_SAVES= " DROP TABLE IF EXISTS "+ SAVES_TABLE;
	private	static final String SQL_DELETE_STATS= " DROP TABLE IF EXISTS "+ STATS_TABLE;
	//endregion
	
	public SaveGameDBHelper(Context context){ super(context, DATABASE_NAME, null, DATABASE_VERSION); }
	
	@Override
	public void onCreate(SQLiteDatabase db){
		db.execSQL(SQL_CREATE_SAVES_TABLE);
		//db.execSQL(SQL_CREATE_STATS_TABLE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL(SQL_DELETE_SAVES);
		db.execSQL(SQL_DELETE_STATS);
		onCreate(db);
	}
	
	public void refreshDatabase(){
		SQLiteDatabase db= getWritableDatabase();
		db.execSQL(SQL_DELETE_SAVES);
		db.execSQL(SQL_DELETE_STATS);
		onCreate(db);
	}
	
	public void deleteSave(int id){
		SQLiteDatabase db= getWritableDatabase();
		String selection= SAVES_COLUMN_SAVE_ID +" LIKE ?";
		String[] selectionArgs= { Integer.toString(id) };
		db.delete(SAVES_TABLE, selection, selectionArgs);
	}
	
	public void insertCharacter(int saveID, String name, String race, String className, String job){
		SQLiteDatabase db= getWritableDatabase();
		ContentValues contentValues= new ContentValues();
		
		contentValues.put(SAVES_COLUMN_SAVE_ID, saveID);
		contentValues.put(SAVES_COLUMN_NAME, name);
		contentValues.put(SAVES_COLUMN_RACE, race);
		contentValues.put(SAVES_COLUMN_CLASS, className);
		contentValues.put(SAVES_COLUMN_JOB, job);
		
		db.insert(SAVES_TABLE, null, contentValues);
	}
	
	public String[] loadCharacter(int saveID){
		String charInfo[]= new String[4];
		SQLiteDatabase db= this.getReadableDatabase();
		
		Cursor cursor= db.rawQuery("SELECT * FROM "+ SAVES_TABLE +" WHERE "+
				SAVES_COLUMN_SAVE_ID +"=?", new String[]{ Integer.toString(saveID) });
		if(cursor.moveToNext()){
			charInfo[0]= cursor.getString(cursor.getColumnIndex(SaveGameContract.SaveEntry.SAVES_COLUMN_NAME));
			charInfo[1]= cursor.getString(cursor.getColumnIndex(SaveGameContract.SaveEntry.SAVES_COLUMN_RACE));
			charInfo[2]= cursor.getString(cursor.getColumnIndex(SaveGameContract.SaveEntry.SAVES_COLUMN_CLASS));
			charInfo[3]= cursor.getString(cursor.getColumnIndex(SaveGameContract.SaveEntry.SAVES_COLUMN_JOB));
		}
		else{ charInfo= null; }
		
		cursor.close();
		
		return charInfo;
	}
	
	
}
