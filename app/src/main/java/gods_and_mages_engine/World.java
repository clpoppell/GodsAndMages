package gods_and_mages_engine;

import android.content.res.Resources;

import com.tadbolmont.homecoming.R;

import java.util.HashMap;
import java.util.Map;

import gods_and_mages_engine.Items.Item;
import gods_and_mages_engine.Items.Weapon;
import gods_and_mages_engine.Player_Char.CharClass;
import gods_and_mages_engine.Player_Char.CharJob;
import gods_and_mages_engine.Player_Char.CharRace;

public final class World{
	/*
	This class provides various objects needed to create the player character,
		populate battle encounters, handle inventory and shop systems
	*/
	public static final int UNSELLABLE_ITEM_PRICE= -1;
	private static final Resources RES= App.context.getResources();
	
	// Collections
	private static final Map<String, Ability> ABILTY_LIST= populateAbilityList();
	private static final Map<String, Item> ITEM_LIST= populateItemList();
	
	//region Make Characteristics Methods
	public static CharRace makeCharRace(String charRaceName){
		CharRace charRace;
		int[] raceInfo;
		switch(charRaceName){
			case "Human": raceInfo= RES.getIntArray(R.array.stat_mods_human);
				charRace= new CharRace(charRaceName, RES.getString(R.string.desc_human),
						null, raceInfo[0], raceInfo[1], raceInfo[2], raceInfo[3]);
				break;
			case "Elf": raceInfo= RES.getIntArray(R.array.stat_mods_elf);
				charRace= new CharRace(charRaceName, RES.getString(R.string.desc_human),
						null, raceInfo[0], raceInfo[1], raceInfo[2], raceInfo[3]);
				break;
			case "Dwarf": raceInfo= RES.getIntArray(R.array.stat_mods_dwarf);
				charRace= new CharRace(charRaceName, RES.getString(R.string.desc_dwarf),
						null, raceInfo[0], raceInfo[1], raceInfo[2], raceInfo[3]);
				break;
			default: charRace= new CharRace("Needs Entry", "Needs Desc", null, 0, 0, 0, 0);
		}
		return charRace;
	}
	
	public static CharClass makeCharClass(String charClassName){
		CharClass charClass;
		switch(charClassName){
			case "Fighter": charClass= new CharClass(charClassName, RES.getString(R.string.desc_fighter),
					null, RES.getInteger(R.integer.hp_mod_fighter));
				break;
			case "Rogue": charClass= new CharClass(charClassName, RES.getString(R.string.desc_rogue),
					null, RES.getInteger(R.integer.hp_mod_rogue));
				break;
			case "Mage": charClass= new CharClass(charClassName, RES.getString(R.string.desc_mage),
					null, RES.getInteger(R.integer.hp_mod_mage));
				break;
			default: charClass= new CharClass("Needs Entry", "Needs Desc", null, 0);
		}
		return charClass;
	}
	
	public static CharJob makeCharJob(String charJobName){
		CharJob charJob;
		switch(charJobName){
			case "Soldier": charJob= new CharJob(charJobName, RES.getString(R.string.desc_soldier), null);
				break;
			case "Spy": charJob= new CharJob(charJobName, RES.getString(R.string.desc_spy), null);
				break;
			case "Scholar": charJob= new CharJob(charJobName, RES.getString(R.string.desc_scholar), null);
				break;
			default: charJob= new CharJob("Needs Entry", "Needs Desc", null);
		}
		return charJob;
	}
	//endregion
	
	//region Populate Lists Methods
	private static Map<String, Ability> populateAbilityList(){
		
		return null;
	}
	
	private static Map<String, Item> populateItemList(){
		Map<String, Item> items= new HashMap<String, Item>();
		items.put("Club", new Weapon("Club", "Clubs", "", 1, 0.15));
		return items;
	}
	//endregion
	
	//region List Accessor Methods
	public static Item getItem(String itemName){
		Item item;
		item= ITEM_LIST.get(itemName);
		return item;
	}
	//endregion
}
