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
		public static String SAVES_TABLE= "saves";
		public static final String COLUMN_SAVE_ID= "save_id";
		public static final String COLUMN_NAME= "name";
		public static final String COLUMN_RACE= "race";
		public static final String COLUMN_CLASS= "class";
		public static final String COLUMN_JOB= "job";
	}
	
	public static class StatsEntry{
		/*
		Table for storing character info that changes at character level-up
		Each row will hold the info for a single character
		This table will be accessed at character creation to add or overwrite a row,
			at save game loading to instantiate the character, and at level-up to update the data
		*/
		public static String STATS_TABLE= "stats";
		public static final String COLUMN_SAVE_ID= "save_id";
		public static final String COLUMN_MAXHP= "maxHP";
		public static final String COLUMN_STRENGTH= "strength";
		public static final String COLUMN_STAMINA= "stamina";
		public static final String COLUMN_AGILITY= "agility";
		public static final String COLUMN_SPEED= "speed";
	}
	
	+
}
