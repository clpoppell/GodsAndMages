package gods_and_mages_engine;

import android.content.res.Resources;

import com.tadbolmont.homecoming.R;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import gods_and_mages_engine.Abilities.*;
import gods_and_mages_engine.Items.*;
import gods_and_mages_engine.Player_Char.CharClass;
import gods_and_mages_engine.Player_Char.CharJob;
import gods_and_mages_engine.Player_Char.CharRace;
import gods_and_mages_engine.Quests.BaseQuest;
import gods_and_mages_engine.Quests.CollectQuest;

/**
 * This class provides various objects as they are needed.
 * <p>
 *     Object collections are populated using an external xml resource, and elements are accessed
 *     using the accessor methods to create the player character, populate battle encounters,
 *     handle inventory and shop systems, etc.
 * </p>
 */
public final class World{
	public static final int UNSELLABLE_ITEM_PRICE= -1;
	private static final Resources RES= App.context.getResources();
	
	//region Collections
	private static final Map<String, BaseTrait> TRAIT_LIST= populateTraitList();
	private static final Map<String, BaseAbility> ABILITY_LIST = populateAbilityList();
	private static final Map<String, BaseItem> ITEM_LIST= populateItemList();
	
	private static final Map<String, Location> LOCATION_LIST= populateLocationList();
	private static final Map<String, BaseQuest> QUEST_LIST= populateQuestList();
	//endregion
	
	private World(){}
	
	//region Make Characteristics Methods
	/**
	 * Takes the appropriate character race string from external xml resource to create the
	 * {@code charRace} object used as part of instantiating the player character.
	 * @param charRaceName string name to define {@code charRace}
	 * @return charRace defined by {@code charRaceName}
	 */
	public static CharRace makeCharRace(String charRaceName){
		CharRace charRace;
		String[] traits;
		String[] abilities;
		int[] raceInfo;
		switch(charRaceName){
			case "Human": raceInfo= RES.getIntArray(R.array.stat_mods_human);
				traits= RES.getStringArray(R.array.traits_human);
				abilities= RES.getStringArray(R.array.abilities_human);
				charRace= new CharRace(charRaceName, RES.getString(R.string.desc_human), traits,
						abilities, raceInfo[0], raceInfo[1], raceInfo[2], raceInfo[3]);
				break;
			case "Elf": raceInfo= RES.getIntArray(R.array.stat_mods_elf);
				traits= RES.getStringArray(R.array.traits_elf);
				abilities= RES.getStringArray(R.array.abilities_elf);
				charRace= new CharRace(charRaceName, RES.getString(R.string.desc_elf), traits,
						abilities, raceInfo[0], raceInfo[1], raceInfo[2], raceInfo[3]);
				break;
			case "Dwarf": raceInfo= RES.getIntArray(R.array.stat_mods_dwarf);
				traits= RES.getStringArray(R.array.traits_dwarf);
				abilities= RES.getStringArray(R.array.abilities_dwarf);
				charRace= new CharRace(charRaceName, RES.getString(R.string.desc_dwarf), traits,
						abilities, raceInfo[0], raceInfo[1], raceInfo[2], raceInfo[3]);
				break;
			default: charRace= new CharRace("Needs Entry", "Needs Desc", null, null, 0, 0, 0, 0);
		}
		return charRace;
	}
	
	/**
	 * Takes the appropriate character class string from external xml resource to create the
	 * {@code charClass} object used as part of instantiating the player character.
	 * @param charClassName string name to define {@code charClass}
	 * @return charClass defined by {@code charClassName}
	 */
	public static CharClass makeCharClass(String charClassName){
		CharClass charClass;
		String[] traits;
		String[] abilities;
		switch(charClassName){
			case "Fighter":
				traits= RES.getStringArray(R.array.traits_fighter);
				abilities= RES.getStringArray(R.array.abilities_fighter);
				charClass= new CharClass(charClassName, RES.getString(R.string.desc_fighter), traits, abilities, RES.getInteger(R.integer.hp_mod_fighter));
				break;
			case "Rogue":
				traits= RES.getStringArray(R.array.traits_rogue);
				abilities= RES.getStringArray(R.array.abilities_rogue);
				charClass= new CharClass(charClassName, RES.getString(R.string.desc_rogue), traits, abilities, RES.getInteger(R.integer.hp_mod_rogue));
				break;
			case "Mage":
				traits= RES.getStringArray(R.array.traits_mage);
				abilities= RES.getStringArray(R.array.abilities_mage);
				charClass= new CharClass(charClassName, RES.getString(R.string.desc_mage), traits, abilities, RES.getInteger(R.integer.hp_mod_mage));
				break;
			default: charClass= new CharClass("Needs Entry", "Needs Desc", null, null, 0);
		}
		return charClass;
	}
	
	/**
	 * Takes the appropriate character job string from external xml resource to create the
	 * {@code charJob} object used as part of instantiating the player character.
	 * @param charJobName string name to define {@code charJob}
	 * @return charJob defined by {@code charJobName}
	 */
	public static CharJob makeCharJob(String charJobName){
		CharJob charJob;
		String[] traits;
		String[] abilities;
		switch(charJobName){
			case "Soldier":
				traits= RES.getStringArray(R.array.traits_soldier);
				abilities= RES.getStringArray(R.array.abilities_soldier);
				charJob= new CharJob(charJobName, RES.getString(R.string.desc_soldier), traits, abilities);
				break;
			case "Spy":
				traits= RES.getStringArray(R.array.traits_spy);
				abilities= RES.getStringArray(R.array.abilities_spy);
				charJob= new CharJob(charJobName, RES.getString(R.string.desc_spy), traits, abilities);
				break;
			case "Scholar":
				traits= RES.getStringArray(R.array.traits_scholar);
				abilities= RES.getStringArray(R.array.abilities_scholar);
				charJob= new CharJob(charJobName, RES.getString(R.string.desc_scholar), traits, abilities);
				break;
			default: charJob= new CharJob("Needs Entry", "Needs Desc", null, null);
		}
		return charJob;
	}
	//endregion
	
	//region Populate Lists Methods
	/**
	 * Takes trait strings from external xml resource and splits them to obtain trait information,
	 * constructs {@code BaseTrait} objects using this information, and adds objects to {@code TRAIT_LIST}
	 */
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

	/**
	 * Takes ability strings from external xml resource and splits them to obtain ability information,
	 * constructs {@code BaseAbility} objects using this information, and adds objects to {@code ABILITY_LIST}
	 */
	private static Map<String, BaseAbility> populateAbilityList(){
		Map<String, BaseAbility> abilities= new HashMap<String, BaseAbility>();
		String[] abilityStrings= RES.getStringArray(R.array.ability_list);
		String[] abilityInfo;
		
		for(String ability : abilityStrings){
			abilityInfo= ability.split(" # ");
			switch(abilityInfo[0]){
				case "Attack":
					abilities.put(abilityInfo[1].trim(), new AttackAbility(abilityInfo[1].trim(), abilityInfo[11], abilityInfo[2].trim(),
							abilityInfo[3].trim(), abilityInfo[4].trim(), Integer.parseInt(abilityInfo[5]), Double.parseDouble(abilityInfo[6]),
							Double.parseDouble(abilityInfo[7]), Double.parseDouble(abilityInfo[8]), Double.parseDouble(abilityInfo[9]),
							Double.parseDouble(abilityInfo[10])));
			}
		}
		return abilities;
	}
	
	/**
	 * Takes item strings from external xml resource and splits them to obtain item information,
	 * constructs {@code BaseItem} objects using this information, and adds objects to {@code ITEM_LIST}
	 */
	private static Map<String, BaseItem> populateItemList(){
		Map<String, BaseItem> items= new HashMap<String, BaseItem>();
		String[] itemStrings= RES.getStringArray(R.array.item_list);
		String[] itemInfo;
		
		for(String item : itemStrings){
			itemInfo= item.split(" # ");
			switch(itemInfo[0]){
				case "Recovery":
					items.put(itemInfo[1].trim(), new RecoveryItem(itemInfo[1].trim(), itemInfo[2].trim(), itemInfo[6],
							Integer.parseInt(itemInfo[3]), Double.parseDouble(itemInfo[4])));
					break;
				case "Usable": // possible split
					break;
				case "Weapon":
					items.put(itemInfo[1].trim(), new Weapon(itemInfo[1].trim(), itemInfo[2].trim(), itemInfo[5],
							Integer.parseInt(itemInfo[3]), Double.parseDouble(itemInfo[4])));
					break;
				case "Armor":
					items.put(itemInfo[1].trim(), new Armor(itemInfo[1].trim(), itemInfo[2].trim(), itemInfo[5],
							Integer.parseInt(itemInfo[3]), Double.parseDouble(itemInfo[4])));
					break;
				case "Accessory":
					items.put(itemInfo[1].trim(), new Accessory(itemInfo[1].trim(), itemInfo[2].trim(), itemInfo[5],
							Integer.parseInt(itemInfo[3]), itemInfo[4].trim()));
					break;
				case "Key":
					items.put(itemInfo[1].trim(), new KeyItem(itemInfo[1].trim(), itemInfo[2]));
					break;
				case "Misc": // possible split
					items.put(itemInfo[1].trim(), new BaseItem(itemInfo[1].trim(), itemInfo[2].trim(), itemInfo[4],
							Integer.parseInt(itemInfo[3])));
					break;
			}
		}
		return items;
	}
	
	/**
	 * Takes location strings from external xml resource and splits them to obtain location information,
	 * constructs {@code Location} objects using this information, and adds objects to {@code LOCATION_LIST}
	 */
	private static Map<String, Location> populateLocationList(){
		Map<String, Location> locations= new HashMap<String, Location>();
		String[] locStrings= RES.getStringArray(R.array.location_list);
		String[] locInfo;
		
		for(String loc : locStrings){
			locInfo= loc.split(" # ");
			locations.put(locInfo[0].trim(), new Location(locInfo[0].trim(), locInfo[1].trim(),
					locInfo[3], locInfo[2].trim(), locInfo[4].split(",")));
		}
		
		return locations;
	}
	
	/**
	 * Takes quest strings from external xml resource and splits them to obtain quest information,
	 * constructs BaseQuest objects using this information, and adds objects to QUEST_LIST
	 */
	private static Map<String, BaseQuest> populateQuestList(){
		Map<String, BaseQuest> quests= new HashMap<String, BaseQuest>();
		String[] questStrings= RES.getStringArray(R.array.quest_list);
		String[] questInfo;
		
		String[] rewardItemList;
		Map<String, Integer> rewardItems;
		
		for(String quest : questStrings){
			questInfo= quest.split(" # ");
			
			rewardItems= new LinkedHashMap<String, Integer>();
			rewardItemList= questInfo[7].split(",");
			
			for(String itemInfo : rewardItemList){
				String[] rewardItemInfo= itemInfo.split(" - ");
				String itemName= rewardItemInfo[0];
				int itemAmount= Integer.parseInt(rewardItemInfo[1]);
				rewardItems.put(itemName, itemAmount);
			}
			switch(questInfo[0]){
				case "Collect":
					quests.put(questInfo[1].trim(), new CollectQuest(questInfo[1].trim(), questInfo[6].trim(),
							questInfo[2].trim(), Integer.parseInt(questInfo[3]), Integer.parseInt(questInfo[4]),
							Integer.parseInt(questInfo[5]), rewardItems));
					break;
			}
		}
		return quests;
	}
	//endregion
	
	//region List Accessor Methods
	public static BaseItem getItem(String itemName){ return ITEM_LIST.get(itemName); }
	
	public static BaseTrait getTrait(String traitName){ return TRAIT_LIST.get(traitName); }
	
	public static BaseAbility getAbility(String abilityName){ return ABILITY_LIST.get(abilityName); }
	
	public static Location getLocation(String locationName){ return LOCATION_LIST.get(locationName); }
	
	public static BaseQuest getQuest(String questName){ return QUEST_LIST.get(questName); }
	//endregion
}
