package gods_and_mages_engine;

import android.content.res.Resources;

import com.tadbolmont.homecoming.R;

import java.util.HashMap;
import java.util.Map;

import gods_and_mages_engine.Abilities.BaseAbility;
import gods_and_mages_engine.Abilities.BaseTrait;
import gods_and_mages_engine.Abilities.BoostTrait;
import gods_and_mages_engine.Abilities.ResistTrait;
import gods_and_mages_engine.Items.Armor;
import gods_and_mages_engine.Items.BaseItem;
import gods_and_mages_engine.Items.KeyItem;
import gods_and_mages_engine.Items.Weapon;
import gods_and_mages_engine.Player_Char.CharClass;
import gods_and_mages_engine.Player_Char.CharJob;
import gods_and_mages_engine.Player_Char.CharRace;

public final class World{
	/*
	This class provides various objects needed to create the player character,
		populate battle encounters, handle inventory and shop systems, etc
	*/
	public static final int UNSELLABLE_ITEM_PRICE= -1;
	private static final Resources RES= App.context.getResources();
	
	// Collections
	private static final Map<String, BaseTrait> TRAIT_LIST= populateTraitList();
	private static final Map<String, BaseAbility> ABILITY_LIST = populateAbilityList();
	private static final Map<String, BaseItem> ITEM_LIST= populateItemList();
	
	//region Make Characteristics Methods
	public static CharRace makeCharRace(String charRaceName){
		CharRace charRace;
		String[] traits;
		String[] abilities;
		int[] raceInfo;
		switch(charRaceName){
			case "Human": raceInfo= RES.getIntArray(R.array.stat_mods_human);
				traits= RES.getStringArray(R.array.traits_human);
				abilities= null;
				charRace= new CharRace(charRaceName, RES.getString(R.string.desc_human), traits,
						abilities, raceInfo[0], raceInfo[1], raceInfo[2], raceInfo[3]);
				break;
			case "Elf": raceInfo= RES.getIntArray(R.array.stat_mods_elf);
				traits= RES.getStringArray(R.array.traits_elf);
				abilities= null;
				charRace= new CharRace(charRaceName, RES.getString(R.string.desc_elf), traits,
						abilities, raceInfo[0], raceInfo[1], raceInfo[2], raceInfo[3]);
				break;
			case "Dwarf": raceInfo= RES.getIntArray(R.array.stat_mods_dwarf);
				traits= RES.getStringArray(R.array.traits_dwarf);
				abilities= null;
				charRace= new CharRace(charRaceName, RES.getString(R.string.desc_dwarf), traits,
						abilities, raceInfo[0], raceInfo[1], raceInfo[2], raceInfo[3]);
				break;
			default: charRace= new CharRace("Needs Entry", "Needs Desc", null, null, 0, 0, 0, 0);
		}
		return charRace;
	}
	
	public static CharClass makeCharClass(String charClassName){
		CharClass charClass;
		String[] traits;
		String[] abilities;
		switch(charClassName){
			case "Fighter":
				traits= RES.getStringArray(R.array.traits_fighter);
				abilities= null;
				charClass= new CharClass(charClassName, RES.getString(R.string.desc_fighter), traits, abilities, RES.getInteger(R.integer.hp_mod_fighter));
				break;
			case "Rogue":
				traits= RES.getStringArray(R.array.traits_rogue);
				abilities= null;
				charClass= new CharClass(charClassName, RES.getString(R.string.desc_rogue), traits, abilities, RES.getInteger(R.integer.hp_mod_rogue));
				break;
			case "Mage":
				traits= RES.getStringArray(R.array.traits_mage);
				abilities= null;
				charClass= new CharClass(charClassName, RES.getString(R.string.desc_mage), traits, abilities, RES.getInteger(R.integer.hp_mod_mage));
				break;
			default: charClass= new CharClass("Needs Entry", "Needs Desc", null, null, 0);
		}
		return charClass;
	}
	
	public static CharJob makeCharJob(String charJobName){
		CharJob charJob;
		String[] traits;
		String[] abilities;
		switch(charJobName){
			case "Soldier":
				traits= null;
				abilities= null;
				charJob= new CharJob(charJobName, RES.getString(R.string.desc_soldier), null, null);
				break;
			case "Spy":
				traits= null;
				abilities= null;
				charJob= new CharJob(charJobName, RES.getString(R.string.desc_spy), null, null);
				break;
			case "Scholar":
				traits= null;
				abilities= null;
				charJob= new CharJob(charJobName, RES.getString(R.string.desc_scholar), null, null);
				break;
			default: charJob= new CharJob("Needs Entry", "Needs Desc", null, null);
		}
		return charJob;
	}
	//endregion
	
	//region Populate Lists Methods
	private static Map<String,BaseTrait> populateTraitList(){
		Map<String, BaseTrait> traits= new HashMap<String, BaseTrait>();
		String[] traitStrings= RES.getStringArray(R.array.trait_list);
		String[] traitInfo;
		
		for(String trait : traitStrings){
			traitInfo= trait.split(" # ");
			switch(traitInfo[0]){
				case "Resist":
					traits.put(traitInfo[1].trim(), new ResistTrait(traitInfo[1].trim(), traitInfo[2].trim(),
							traitInfo[5], traitInfo[3].trim(), Double.parseDouble(traitInfo[4])));
					break;
				case "Boost":
					traits.put(traitInfo[1].trim(), new BoostTrait(traitInfo[1].trim(), traitInfo[2].trim(),
							traitInfo[5], traitInfo[3].trim(), Double.parseDouble(traitInfo[4])));
					break;
			}
		}
		return traits;
	}
	
	private static Map<String, BaseAbility> populateAbilityList(){
		
		return null;
	}
	
	// Takes item strings from lists.xml and splits them to obtain item information,
	// constructs BaseItem objects using this information, and adds objects to ITEM_LIST
	private static Map<String, BaseItem> populateItemList(){
		Map<String, BaseItem> items= new HashMap<String, BaseItem>();
		String[] itemStrings= RES.getStringArray(R.array.item_list);
		String[] itemInfo;
		
		for(String item : itemStrings){
			itemInfo= item.split(" # ");
			switch(itemInfo[0]){
				case "Recovery":
					
					break;
				case "Usable": // possible split
					break;
				case "Weapon":
					items.put(itemInfo[1], new Weapon(itemInfo[1], itemInfo[2], itemInfo[5],
							Integer.parseInt(itemInfo[3]), Double.parseDouble(itemInfo[4])));
					break;
				case "Armor":
					items.put(itemInfo[1], new Armor(itemInfo[1], itemInfo[2], itemInfo[5],
							Integer.parseInt(itemInfo[3]), Double.parseDouble(itemInfo[4])));
					break;
				case "Accessory":
					break;
				case "Key":
					items.put(itemInfo[1], new KeyItem(itemInfo[1], itemInfo[2]));
					break;
				case "Misc": // possible split
					items.put(itemInfo[1], new BaseItem(itemInfo[1], itemInfo[2], itemInfo[4],
							Integer.parseInt(itemInfo[3])));
					break;
			}
		}
		return items;
	}
	//endregion
	
	//region List Accessor Methods
	public static BaseItem getItem(String itemName){ return ITEM_LIST.get(itemName); }
	
	public static BaseTrait getTrait(String traitName){ return TRAIT_LIST.get(traitName); }
	
	public static BaseAbility getAbility(String abilityName){ return ABILITY_LIST.get(abilityName); }
	//endregion
}
