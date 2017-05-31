package gods_and_mages_engine.Database;

public final class SaveGameContract{
	// This class establishes the schema for the sqlite db used to save player data for all save slots
	
	private SaveGameContract(){}
	
	public static class SaveEntry{
		/*
		Table for saving character info that remains constant after character creation
		Each row will hold the info for a single character
		This table is accessed at character creation and save game loading only
		*/
		public static final String SAVES_TABLE= "saves";
		public static final String SAVES_COLUMN_SAVE_ID= "saves_save_id";
		public static final String SAVES_COLUMN_NAME= "name";
		public static final String SAVES_COLUMN_RACE= "race";
		public static final String SAVES_COLUMN_CLASS= "class";
		public static final String SAVES_COLUMN_JOB= "job";
	}
	
	public static class StatsEntry{
		/*
		Table for storing character info that changes at character level-up
		Each row will hold the info for a single character
		This table will be accessed at character creation to add or overwrite a row,
			at save game loading to instantiate the character, and at level-up to update the data
		*/
		public static final String STATS_TABLE= "stats";
		public static final String STATS_COLUMN_SAVE_ID= "stats_save_id";
		public static final String STATS_COLUMN_LEVEL= "level";
		public static final String STATS_COLUMN_MAXHP= "maxHP";
		public static final String STATS_COLUMN_STRENGTH= "str";
		public static final String STATS_COLUMN_STAMINA= "sta";
		public static final String STATS_COLUMN_AGILITY= "agi";
		public static final String STATS_COLUMN_SPEED= "speed";
	}
	
	public static class PlayerStatusEntry{
		/*
		Table for storing character info that changes frequently
		Each row will hold the info for a single character
		This table will be accessed at character creation to add or overwrite a row,
			at save game loading to help instantiate the character, and when applicable info changes
		*/
		public static final String STATUS_TABLE= "status";
		public static final String STATUS_COLUMN_SAVE_ID= "status_save_id";
		public static final String STATUS_COLUMN_LOCATION= "location";
		public static final String STATUS_COLUMN_GOLD= "gold";
		public static final String STATUS_COLUMN_EXP= "exp";
		public static final String STATUS_COLUMN_CURRENT_HP= "current_hp";
		public static final String STATUS_COLUMN_STATUS= "status";
	}
	
	public static class EquipmentEntry{
		/*
		Table for storing names of equipped items
		Each row will hold the equipment for a single character
		This table will be accessed at character creation to add or overwrite a row,
			at save game loading to help instantiate the character, and when applicable info changes
		*/
		public static final String EQUIPMENT_TABLE= "equipment";
		public static final String EQUIPMENT_COLUMN_SAVE_ID= "equipment_save_id";
		public static final String EQUIPMENT_COLUMN_WEAPON= "weapon";
		public static final String EQUIPMENT_COLUMN_ARMOR= "armor";
		public static final String EQUIPMENT_COLUMN_ACCESSORY_1= "acc1";
		public static final String EQUIPMENT_COLUMN_ACCESSORY_2= "acc2";
	}
	
	public static class InventoryEntry{
		/*
		Table for storing name of items in inventory
		Each row will hold the name and quantity of a single item in a character's inventory
		This table will be accessed at character creation to add or overwrite a row,
			at save game loading to help instantiate the character, and when applicable info changes
		*/
		public static final String INVENTORY_TABLE= "inventory";
		public static final String INVENTORY_COLUMN_SAVE_ID= "inventory_save_id";
		public static final String INVENTORY_COLUMN_ITEM= "item";
		public static final String INVENTORY_COLUMN_QUANTITY= "quan";
	}
	
	
}
