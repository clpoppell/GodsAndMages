package gods_and_mages_engine.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedHashMap;
import java.util.Map;

import gods_and_mages_engine.App;

import static gods_and_mages_engine.Database.SaveGameContract.EquipmentEntry.*;
import static gods_and_mages_engine.Database.SaveGameContract.InventoryEntry.*;
import static gods_and_mages_engine.Database.SaveGameContract.PlayerStatusEntry.*;
import static gods_and_mages_engine.Database.SaveGameContract.SaveEntry.*;
import static gods_and_mages_engine.Database.SaveGameContract.StatsEntry.*;

public final class SaveGameDBHelper extends SQLiteOpenHelper{
	private static final int DATABASE_VERSION= 10;
	private static final String DATABASE_NAME= "HomecomingSaveGame.db";
	
	private static SaveGameDBHelper instance= null;
	
	private static final int SAVE_FILE_SIZE= 19;
	
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
					STATS_COLUMN_LEVEL +" INTEGER,"+
					STATS_COLUMN_MAXHP +" INTEGER,"+
					STATS_COLUMN_STRENGTH +" INTEGER,"+
					STATS_COLUMN_STAMINA +" INTEGER,"+
					STATS_COLUMN_AGILITY +" INTEGER,"+
					STATS_COLUMN_SPEED +" INTEGER)";
	private static final String SQL_CREATE_STATUS_TABLE=
			"CREATE TABLE "+ STATUS_TABLE +" ("+
					STATUS_COLUMN_SAVE_ID +" INTEGER PRIMARY KEY,"+
					STATUS_COLUMN_LOCATION +" TEXT,"+
					STATUS_COLUMN_GOLD +" INTEGER,"+
					STATUS_COLUMN_EXP +" INTEGER,"+
					STATUS_COLUMN_CURRENT_HP +" INTEGER,"+
					STATUS_COLUMN_STATUS +" TEXT)";
	private static final String SQL_CREATE_EQUIPMENT_TABLE=
			"CREATE TABLE "+ EQUIPMENT_TABLE +" ("+
					EQUIPMENT_COLUMN_SAVE_ID +" INTEGER PRIMARY KEY,"+
					EQUIPMENT_COLUMN_WEAPON +" TEXT,"+
					EQUIPMENT_COLUMN_ARMOR +" TEXT,"+
					EQUIPMENT_COLUMN_ACCESSORY_1 +" TEXT,"+
					EQUIPMENT_COLUMN_ACCESSORY_2 +" TEXT)";
	private static final String SQL_CREATE_INVENTORY_TABLE=
			"CREATE TABLE "+ INVENTORY_TABLE +" ("+
					INVENTORY_COLUMN_SAVE_ID +" INTEGER NOT NULL,"+
					INVENTORY_COLUMN_ITEM +" TEXT NOT NULL,"+
					INVENTORY_COLUMN_QUANTITY +" INTEGER,"+
					"PRIMARY KEY ("+ INVENTORY_COLUMN_SAVE_ID +","+ INVENTORY_COLUMN_ITEM +"))";
	private static final String SQL_LOAD_QUERY=
			"SELECT * FROM "+ SAVES_TABLE +","+ STATS_TABLE +","+ STATUS_TABLE +","+ EQUIPMENT_TABLE
					+" WHERE "+ SAVES_TABLE +"."+ SAVES_COLUMN_SAVE_ID +"="+ STATS_TABLE +"."+ STATS_COLUMN_SAVE_ID
					+" AND "+ SAVES_TABLE +"."+ SAVES_COLUMN_SAVE_ID +"="+ STATUS_TABLE +"."+ STATUS_COLUMN_SAVE_ID
					+" AND "+ SAVES_TABLE +"."+ SAVES_COLUMN_SAVE_ID +"="+ EQUIPMENT_TABLE +"."+ EQUIPMENT_COLUMN_SAVE_ID
					+" AND "+ SAVES_TABLE +"."+ SAVES_COLUMN_SAVE_ID +"=?";
	private static final String SQL_LOAD_INVENTORY_QUERY=
			"SELECT * FROM "+ INVENTORY_TABLE +" WHERE "+ INVENTORY_COLUMN_SAVE_ID +"=?";
	private static final String SQL_DELETE_SAVES= " DROP TABLE IF EXISTS "+ SAVES_TABLE;
	private	static final String SQL_DELETE_STATS= " DROP TABLE IF EXISTS "+ STATS_TABLE;
	private static final String SQL_DELETE_STATUS= " DROP TABLE IF EXISTS "+ STATUS_TABLE;
	private static final String SQL_DELETE_EQUIPMENT = " DROP TABLE IF EXISTS "+ EQUIPMENT_TABLE;
	private static final String SQL_DELETE_INVENTORY= " DROP TABLE IF EXISTS "+ INVENTORY_TABLE;
	//endregion
	
	private SaveGameDBHelper(Context context){ super(context, DATABASE_NAME, null, DATABASE_VERSION); }
	
	public static synchronized SaveGameDBHelper getInstance(){
		if(instance == null){ instance= new SaveGameDBHelper(App.context); }
		return instance;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db){
		db.execSQL(SQL_CREATE_SAVES_TABLE);
		db.execSQL(SQL_CREATE_STATS_TABLE);
		db.execSQL(SQL_CREATE_STATUS_TABLE);
		db.execSQL(SQL_CREATE_EQUIPMENT_TABLE);
		db.execSQL(SQL_CREATE_INVENTORY_TABLE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL(SQL_DELETE_SAVES);
		db.execSQL(SQL_DELETE_STATS);
		db.execSQL(SQL_DELETE_STATUS);
		db.execSQL(SQL_DELETE_EQUIPMENT);
		db.execSQL(SQL_DELETE_INVENTORY);
		onCreate(db);
	}
	
	// Called to erase a single save file
	public void deleteSave(int saveID){
		SQLiteDatabase db= getWritableDatabase();
		
		String selection= SAVES_COLUMN_SAVE_ID +" LIKE ?";
		String selection2= STATS_COLUMN_SAVE_ID +" LIKE ?";
		String selection3= STATUS_COLUMN_SAVE_ID +" LIKE ?";
		String selection4= EQUIPMENT_COLUMN_SAVE_ID + " LIKE ?";
		String selection5= INVENTORY_COLUMN_SAVE_ID + " LIKE ?";
		
		String[] selectionArgs= { Integer.toString(saveID) };
		
		db.delete(SAVES_TABLE, selection, selectionArgs);
		db.delete(STATS_TABLE, selection2, selectionArgs);
		db.delete(STATUS_TABLE, selection3, selectionArgs);
		db.delete(EQUIPMENT_TABLE, selection4, selectionArgs);
		db.delete(INVENTORY_TABLE, selection5, selectionArgs);
		
		db.close();
	}
	
	public void refreshDatabase(){
		SQLiteDatabase db= getWritableDatabase();
		db.execSQL(SQL_DELETE_SAVES);
		db.execSQL(SQL_DELETE_STATS);
		db.execSQL(SQL_DELETE_STATUS);
		db.execSQL(SQL_DELETE_EQUIPMENT);
		db.execSQL(SQL_DELETE_INVENTORY);
		onCreate(db);
	}
	
	// Called to populate text of buttons used to select save files
	public String[] loadCharacterInfo(int saveID){
		String charInfo[]= new String[4];
		SQLiteDatabase db= this.getReadableDatabase();
		
		Cursor cursor= db.rawQuery("SELECT * FROM "+ SAVES_TABLE +" WHERE "+
				SAVES_COLUMN_SAVE_ID +"=?", new String[]{ Integer.toString(saveID) });
		if(cursor.moveToNext()){
			charInfo[0]= cursor.getString(cursor.getColumnIndex(SAVES_COLUMN_NAME));
			charInfo[1]= cursor.getString(cursor.getColumnIndex(SAVES_COLUMN_RACE));
			charInfo[2]= cursor.getString(cursor.getColumnIndex(SAVES_COLUMN_CLASS));
			charInfo[3]= cursor.getString(cursor.getColumnIndex(SAVES_COLUMN_JOB));
		}
		else{ charInfo= null; }
		cursor.close();
		return charInfo;
	}
	
	// Called upon starting a new game to add initial character information into database
	public void insertCharacter(int saveID, String charName, String charRace, String charClass, String charJob,
								int level, int maximumHitPoints, int str, int sta, int agi, int speed,
								int gold, int exp, int currentHitPoints, String wpnName, String armorName,
								String accNameOne, String accNameTwo, String status, String location){
		SQLiteDatabase db= getWritableDatabase();
		// Inserts into Saves table
		ContentValues contentValues= new ContentValues();
		
		contentValues.put(SAVES_COLUMN_SAVE_ID, saveID);
		contentValues.put(SAVES_COLUMN_NAME, charName);
		contentValues.put(SAVES_COLUMN_RACE, charRace);
		contentValues.put(SAVES_COLUMN_CLASS, charClass);
		contentValues.put(SAVES_COLUMN_JOB, charJob);
		
		db.insert(SAVES_TABLE, null, contentValues);
		
		// Inserts into stats table
		contentValues= new ContentValues();
		
		contentValues.put(STATS_COLUMN_SAVE_ID, saveID);
		contentValues.put(STATS_COLUMN_LEVEL, level);
		contentValues.put(STATS_COLUMN_MAXHP, maximumHitPoints);
		contentValues.put(STATS_COLUMN_STRENGTH, str);
		contentValues.put(STATS_COLUMN_STAMINA, sta);
		contentValues.put(STATS_COLUMN_AGILITY, agi);
		contentValues.put(STATS_COLUMN_SPEED, speed);
		
		db.insert(STATS_TABLE, null, contentValues);
		
		// Inserts into status table
		contentValues= new ContentValues();
		
		contentValues.put(STATUS_COLUMN_SAVE_ID, saveID);
		contentValues.put(STATUS_COLUMN_LOCATION, location);
		contentValues.put(STATUS_COLUMN_GOLD, gold);
		contentValues.put(STATUS_COLUMN_EXP, exp);
		contentValues.put(STATUS_COLUMN_CURRENT_HP, currentHitPoints);
		contentValues.put(STATUS_COLUMN_STATUS, status);
		
		db.insert(STATUS_TABLE, null, contentValues);
		
		// Inserts into inventory table
		contentValues= new ContentValues();
		
		contentValues.put(EQUIPMENT_COLUMN_SAVE_ID, saveID);
		contentValues.put(EQUIPMENT_COLUMN_WEAPON, wpnName);
		contentValues.put(EQUIPMENT_COLUMN_ARMOR, armorName);
		contentValues.put(EQUIPMENT_COLUMN_ACCESSORY_1, accNameOne);
		contentValues.put(EQUIPMENT_COLUMN_ACCESSORY_2, accNameTwo);
		
		db.insert(EQUIPMENT_TABLE, null, contentValues);
		
		db.close();
	}
	
	public void updateCharacterLevel(int saveID, int level, int maxHP, int str, int sta, int agi, int speed){
		SQLiteDatabase db= getWritableDatabase();
		ContentValues contentValues= new ContentValues();
		
		contentValues.put(STATS_COLUMN_LEVEL, level);
		contentValues.put(STATS_COLUMN_MAXHP, maxHP);
		contentValues.put(STATS_COLUMN_STRENGTH, str);
		contentValues.put(STATS_COLUMN_STAMINA, sta);
		contentValues.put(STATS_COLUMN_AGILITY, agi);
		contentValues.put(STATS_COLUMN_SPEED, speed);
		
		db.update(STATS_TABLE, contentValues, STATS_COLUMN_SAVE_ID +"=?", new String[]{ Integer.toString(saveID) });
		db.close();
	}
	
	public void updateLocation(int saveID, String locationName){
		SQLiteDatabase db= getWritableDatabase();
		ContentValues contentValues= new ContentValues();
		
		contentValues.put(STATUS_COLUMN_LOCATION, locationName);
		
		db.update(STATUS_TABLE, contentValues, STATUS_COLUMN_SAVE_ID +"=?", new String[]{ Integer.toString(saveID) });
		db.close();
	}
	
	public void updateStatus(int saveID, int gold, int exp, int currentHP, String status){
		SQLiteDatabase db= getWritableDatabase();
		ContentValues contentValues= new ContentValues();
		
		contentValues.put(STATUS_COLUMN_GOLD, gold);
		contentValues.put(STATUS_COLUMN_EXP, exp);
		contentValues.put(STATUS_COLUMN_CURRENT_HP, currentHP);
		contentValues.put(STATUS_COLUMN_STATUS, status);
		
		db.update(STATUS_TABLE, contentValues, STATUS_COLUMN_SAVE_ID +"=?", new String[]{ Integer.toString(saveID) });
		db.close();
	}
	
	public void updateEquipment(int saveID, String equipmentName, String equipmentSlot){
		SQLiteDatabase db= getWritableDatabase();
		ContentValues contentValues= new ContentValues();
		
		if(equipmentSlot.equals("Weapon")){ contentValues.put(EQUIPMENT_COLUMN_WEAPON, equipmentName); }
		else if(equipmentSlot.equals("Armor")){ contentValues.put(EQUIPMENT_COLUMN_ARMOR, equipmentName); }
		else if(equipmentSlot.equals("Acc1")){ contentValues.put(EQUIPMENT_COLUMN_ACCESSORY_1, equipmentName); }
		else if(equipmentSlot.equals("Acc2")){ contentValues.put(EQUIPMENT_COLUMN_ACCESSORY_2, equipmentName); }
		
		db.update(EQUIPMENT_TABLE, contentValues, EQUIPMENT_COLUMN_SAVE_ID +"=?", new String[]{ Integer.toString(saveID) });
		db.close();
	}
	
	public void updateInventory(int saveID, String itemName, int quantity){
		SQLiteDatabase db= getWritableDatabase();
		if(quantity < 1){
			String selection= INVENTORY_COLUMN_SAVE_ID +"=? AND "+ INVENTORY_COLUMN_ITEM +"=?";
			String[] selectionArgs= new String[]{ Integer.toString(saveID), itemName };
			db.delete(INVENTORY_TABLE, selection, selectionArgs);
			return;
		}
		else{
			ContentValues contentValues= new ContentValues();
			
			contentValues.put(INVENTORY_COLUMN_SAVE_ID, saveID);
			contentValues.put(INVENTORY_COLUMN_ITEM, itemName);
			contentValues.put(INVENTORY_COLUMN_QUANTITY, quantity);
			
			db.replace(INVENTORY_TABLE, null, contentValues);
			db.close();
		}
	}
	
	public String[] loadSave(int saveID){
		String[] charInfo= new String[SAVE_FILE_SIZE];
		SQLiteDatabase db= this.getReadableDatabase();
		
		Cursor cursor= db.rawQuery(SQL_LOAD_QUERY, new String[]{ Integer.toString(saveID) });
		if(cursor.moveToNext()){
			charInfo[0]= cursor.getString(cursor.getColumnIndex(SAVES_COLUMN_NAME));
			charInfo[1]= cursor.getString(cursor.getColumnIndex(SAVES_COLUMN_RACE));
			charInfo[2]= cursor.getString(cursor.getColumnIndex(SAVES_COLUMN_CLASS));
			charInfo[3]= cursor.getString(cursor.getColumnIndex(SAVES_COLUMN_JOB));
			charInfo[4]= cursor.getString(cursor.getColumnIndex(STATS_COLUMN_LEVEL));
			charInfo[5]= cursor.getString(cursor.getColumnIndex(STATS_COLUMN_MAXHP));
			charInfo[6]= cursor.getString(cursor.getColumnIndex(STATS_COLUMN_STRENGTH));
			charInfo[7]= cursor.getString(cursor.getColumnIndex(STATS_COLUMN_STAMINA));
			charInfo[8]= cursor.getString(cursor.getColumnIndex(STATS_COLUMN_AGILITY));
			charInfo[9]= cursor.getString(cursor.getColumnIndex(STATS_COLUMN_SPEED));
			charInfo[10]= cursor.getString(cursor.getColumnIndex(STATUS_COLUMN_LOCATION));
			charInfo[11]= cursor.getString(cursor.getColumnIndex(STATUS_COLUMN_GOLD));
			charInfo[12]= cursor.getString(cursor.getColumnIndex(STATUS_COLUMN_EXP));
			charInfo[13]= cursor.getString(cursor.getColumnIndex(STATUS_COLUMN_CURRENT_HP));
			charInfo[14]= cursor.getString(cursor.getColumnIndex(STATUS_COLUMN_STATUS));
			charInfo[15]= cursor.getString(cursor.getColumnIndex(EQUIPMENT_COLUMN_WEAPON));
			charInfo[16]= cursor.getString(cursor.getColumnIndex(EQUIPMENT_COLUMN_ARMOR));
			charInfo[17]= cursor.getString(cursor.getColumnIndex(EQUIPMENT_COLUMN_ACCESSORY_1));
			charInfo[18]= cursor.getString(cursor.getColumnIndex(EQUIPMENT_COLUMN_ACCESSORY_2));
		}
		else{ charInfo= null; }
		cursor.close();
		return charInfo;
	}
	
	// Loads inventory info for specified saveID.
	public Map<String, Integer> loadInventory(int saveID){
		Map<String, Integer> itemMap= new LinkedHashMap<String, Integer>();
		SQLiteDatabase db= this.getReadableDatabase();
		
		Cursor cursor= db.rawQuery(SQL_LOAD_INVENTORY_QUERY, new String[]{ Integer.toString(saveID) });
		int itemNamePos= cursor.getColumnIndex(INVENTORY_COLUMN_ITEM);
		int quanPos= cursor.getColumnIndex(INVENTORY_COLUMN_QUANTITY);
		while(cursor.moveToNext()){
			itemMap.put(cursor.getString(itemNamePos), cursor.getInt(quanPos));
		}
		return itemMap;
	}
}
