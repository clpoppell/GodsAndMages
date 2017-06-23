package gods_and_mages_engine.Player_Char;

import android.content.res.Resources;

import com.tadbolmont.homecoming.R;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import gods_and_mages_engine.Abilities.BaseTrait;
import gods_and_mages_engine.Abilities.BoostTrait;
import gods_and_mages_engine.*;
import gods_and_mages_engine.Database.SaveGameDBHelper;
import gods_and_mages_engine.Items.*;
import gods_and_mages_engine.Quests.BaseQuest;

public class Player extends LivingCreature{
	//region Resources
	private static Player player= null;
	private static SaveGameDBHelper dbHelper= SaveGameDBHelper.getInstance();
	private static Resources RES= App.context.getResources();
	//endregion
	
	//region Starting values for player character
	private static final int STARTING_LEVEL= RES.getInteger(R.integer.starting_level);
	private static final int STARTING_HP= RES.getInteger(R.integer.starting_hp);
	private static final int STARTING_STR= RES.getInteger(R.integer.starting_str);
	private static final int STARTING_STA= RES.getInteger(R.integer.starting_sta);
	private static final int STARTING_AGI= RES.getInteger(R.integer.starting_agi);
	private static final int STARTING_SPEED= RES.getInteger(R.integer.starting_speed);
	private static final int STARTING_GOLD= RES.getInteger(R.integer.starting_gold);
	private static final String STARTING_LOCATION= RES.getString(R.string.starting_location);
	private static final String STARTING_STATUS= RES.getString(R.string.default_status);
	//endregion
	
	private static final double XP_EXPONENT= 1.5;
	private static final int BASE_XP= 10;
	
	//region Variables
	private int playerID;
	private int exp;
	private int level;
	
	private CharRace charRace;
	private CharClass charClass;
	private CharJob charJob;
	
	private Weapon currentWpn; // Weapon currently equipped
	private Armor currentArmor; // Armor currently equipped
	private Accessory accOne; // 1st Accessory currently equipped
	private Accessory accTwo; // 2nd Accessory currently equipped
	
	private int gold;
	private Map<String, InventoryItem> inventory= new LinkedHashMap<String, InventoryItem>(); // Current inventory
	private Map<String, String> baseTraits= new LinkedHashMap<String, String>(); // Traits overwritten due to equipment
	
	// Location & BaseQuest Info
	private Location currentLocation;
	private Map<String, BaseQuest> questsInProgress= new LinkedHashMap<String, BaseQuest>();
	private Map<String, BaseQuest> completedQuests= new LinkedHashMap<String, BaseQuest>();
	
	//endregion
	
	//region Character Instantiation
	public static Player getPlayer(){ return player; }
	
	public static void makePlayer(int id, String[] charInfo){
		if(charInfo == null){
			player= new Player(id, dbHelper.loadSave(id));
		}
		else{ player= new Player(id, charInfo[0], charInfo[1], charInfo[2], charInfo[3]); }
	}
	
	public Player(int playerID, String name, String charRace, String charClass, String charJob){
		super(name, STARTING_HP, STARTING_STR, STARTING_STA, STARTING_AGI, STARTING_SPEED);
		this.playerID= playerID;
		
		this.charRace= World.makeCharRace(charRace);
		this.charClass= World.makeCharClass(charClass);
		this.charJob= World.makeCharJob(charJob);
		
		this.level= STARTING_LEVEL;
		this.status= STARTING_STATUS;
		this.gold= STARTING_GOLD;
		this.exp= 0;
		
		String[] equipmentList= RES.getStringArray(R.array.default_equipment);
		this.currentWpn= (Weapon)World.getItem(equipmentList[0]);
		this.currentArmor= (Armor)World.getItem(equipmentList[1]);
		this.accOne= (Accessory)World.getItem(equipmentList[2]);
		this.accTwo= (Accessory)World.getItem(equipmentList[3]);
		
		this.currentLocation= World.getLocation(STARTING_LOCATION);
		
		makeDefaultInventory();
		setTraits();
		setAbilities();
		
		dbHelper.insertCharacter(playerID, name, charRace, charClass, charJob,
				level, maximumHitPoints, str, sta, agi, speed, gold, exp, currentHitPoints,
				equipmentList[0], equipmentList[1], equipmentList[2], equipmentList[3], status, currentLocation.name);
	}
	
	// public Player(int playerID, String name, String charRace, String charClass, String charJob){}
	public Player(int playerID, String[] charInfo){
		super(charInfo[0], 1000 /*Integer.parseInt(charInfo[5])*/, Integer.parseInt(charInfo[6]), Integer.parseInt(charInfo[7]),
				Integer.parseInt(charInfo[8]), Integer.parseInt(charInfo[9]));
		this.playerID= playerID;
		this.charRace= World.makeCharRace(charInfo[1]);
		this.charClass= World.makeCharClass(charInfo[2]);
		this.charJob= World.makeCharJob(charInfo[3]);
		
		this.level= Integer.parseInt(charInfo[4]);
		this.gold= Integer.parseInt(charInfo[11]);
		this.exp= Integer.parseInt(charInfo[12]);
		
		this.currentHitPoints= 1000;//Integer.parseInt(charInfo[13]);
		this.status= charInfo[14];
		
		this.currentWpn= (Weapon)World.getItem(charInfo[15]);
		this.currentArmor= (Armor)World.getItem(charInfo[16]);
		this.accOne= (Accessory)World.getItem(charInfo[17]);
		this.accTwo= (Accessory)World.getItem(charInfo[18]);
		
		this.currentLocation= World.getLocation(charInfo[10]);
		
		loadInventory();
		setTraits();
		setAbilities();
		
		dbHelper.updateStatus(playerID, gold, exp, currentHitPoints, status);
	}
	
	private void makeDefaultInventory(){
		String[] itemStrings= RES.getStringArray(R.array.default_inventory);
		String[] itemEntry;
		
		for(String item : itemStrings){
			itemEntry= item.split(" # ");
			addItemToInventory(itemEntry[0].trim(), Integer.parseInt(itemEntry[1]));
		}
	}
	
	private void loadInventory(){
		Map<String, Integer> itemList= dbHelper.loadInventory(playerID);
		
		Iterator<String> iterate= itemList.keySet().iterator();
		String key;
		InventoryItem item;
		while(iterate.hasNext()){
			key= iterate.next();
			item= new InventoryItem(World.getItem(key), itemList.get(key));
			inventory.put(key, item);
		}
	}
	
	private void setTraits(){
		traits= new LinkedHashMap<String, BaseTrait>();
		
		for(String key : this.charRace.getTraitsGranted()){ placeTrait(key); }
		for(String key : this.charClass.getTraitsGranted()){ placeTrait(key); }
		for(String key : this.charJob.getTraitsGranted()){ placeTrait(key); }
		
		placeTrait(accOne.buff);
		placeTrait(accTwo.buff);
	}
	
	// If trait type is in traits map, check percentage value of trait and old trait
	// Then, if old trait's percentage is higher, make no change to map and return
	// Otherwise, add trait to traits map
	private void placeTrait(String key){
		BaseTrait trait= World.getTrait(key);
		String traitClass= trait.traitClass;
		
		if(traits.containsKey(traitClass)){
			BaseTrait oldTrait= traits.get(traitClass);
			if(trait.getPercentage() < oldTrait.getPercentage()){ return; }
			traits.remove(traitClass);
		}
		traits.put(traitClass, trait);
	}
	
	private void setAbilities(){
		for(String key : this.charRace.getAbilitiesGranted()){
			abilities.put(key, World.getAbility(key));
		}
		for(String key : this.charClass.getAbilitiesGranted()){
			abilities.put(key, World.getAbility(key));
		}
		for(String key : this.charJob.getAbilitiesGranted()){
			abilities.put(key, World.getAbility(key));
		}
	}
	//endregion
	
	public void addExp(int expToAdd){
		exp += expToAdd;
		dbHelper.updateStatus(playerID, gold, exp, currentHitPoints, status);
		updateLevel();
	}
	
	private void updateLevel(){
		if(exp >= toNextLevel()){
			level++;
			
			str += charRace.getStrMod();
			sta += charRace.getStaMod();
			agi += charRace.getAgiMod();
			speed += charRace.getSpeedMod();
			
			dbHelper.updateCharacterLevel(playerID, level, maximumHitPoints, str, sta, agi, speed);
			
			updateLevel();
		}
	}
	
	public int toNextLevel(){ return (int)Math.floor(BASE_XP*(Math.pow(level+1, XP_EXPONENT))); }
	
	public void addGold(int goldToAdd){ gold += goldToAdd; }
	
	//region Accessors
	public int getExp(){ return exp; }
	
	public int getLevel(){ return level; }
	
	//temporary
	public CharRace getCharRace(){ return charRace; }
	
	//temporary
	public CharClass getCharClass(){ return charClass; }
	
	//temporary
	public CharJob getCharJob(){ return charJob; }
	
	public Location getLocation(){ return currentLocation; }
	
	public int getGold(){ return gold; }
	
	//temporary
	public Weapon getCurrentWpn(){ return currentWpn; }
	
	public Map<String, InventoryItem> getInventory(){ return inventory; }
	//endregion
	
	//region Battle Stats Calculation
	@Override
	public int getSpeed(){
		int calcSpeed= speed;
		
		BoostTrait speedUp= (BoostTrait)traits.get("Speed Up");
		BoostTrait speedDown= (BoostTrait)traits.get("Speed Down");
		
		if(!(speedUp == null)){ calcSpeed += speed * speedUp.getPercentage(); }
		if(!(speedDown == null)){ calcSpeed += speed * speedDown.getPercentage(); }
		
		return calcSpeed;
	}
	
	@Override
	public int getAtkPower(){
		int attack= str;
		
		BoostTrait attackUp= (BoostTrait)traits.get("Atk Up");
		BoostTrait attackDown= (BoostTrait)traits.get("Atk Down");
		
		if(!(attackUp == null)){ attack += str * attackUp.getPercentage(); }
		if(!(attackDown == null)){ attack += str * attackDown.getPercentage(); }
		
		int mod= (int)(attack * (this.currentWpn.getDmgMod()));
		if(mod < 1){ mod= 1; }
		
		return attack + mod;
	}
	
	@Override
	public int getDefValue(){
		int defense= sta;
		
		BoostTrait defenseUp= (BoostTrait)traits.get("Def Up");
		BoostTrait defenseDown= (BoostTrait)traits.get("Def Down");
		
		if(!(defenseUp == null)){ defense += sta * defenseUp.getPercentage(); }
		if(!(defenseDown == null)){ defense += sta * defenseDown.getPercentage(); }
		
		int mod= (int)(defense * (currentArmor.getArmorMod()));
		if(mod < 1){ mod= 1; }
		
		return defense + mod;
	}
	
	@Override
	public int getAccuracy(){
		int accuracy= agi;
		
		BoostTrait accuracyUp= (BoostTrait)traits.get("Accuracy Up");
		BoostTrait accuracyDown= (BoostTrait)traits.get("Accuracy Down");
		
		if(!(accuracyUp == null)){ accuracy += agi * accuracyUp.getPercentage(); }
		if(!(accuracyDown == null)){ accuracy += agi * accuracyDown.getPercentage(); }
		
		return accuracy;
	}
	
	@Override
	public int getAvoidance(){
		int avoidance= agi;
		
		BoostTrait avoidanceUp= (BoostTrait)traits.get("Avoid Up");
		BoostTrait avoidanceDown= (BoostTrait)traits.get("Avoid Down");
		
		if(!(avoidanceUp == null)){ avoidance += agi * avoidanceUp.getPercentage(); }
		if(!(avoidanceDown == null)){ avoidance += agi * avoidanceDown.getPercentage(); }

		return avoidance;
	}
	//endregion
	
	//region Inventory & Equipment Management
	// Called to add an item into player inventory. Returns if key does not match any items.
	// Changes quantity of item in inventory if already there, or adds item to map if not.
	public void addItemToInventory(String key, int quantity){
		if(World.getItem(key) == null){ return; }
		if(inventory.containsKey(key)){
			if(World.getItem(key) instanceof KeyItem){ return; }
			inventory.get(key).changeQuantity(quantity);
		}
		else{ inventory.put(key, new InventoryItem(World.getItem(key), quantity)); }
		saveItemChangeToDB(key);
	}
	
	// Called to remove an item from player inventory. Returns true if item can be removed.
	// Changes quantity of item in inventory if equal to parameter passed, or removes item from map.
	public boolean removeItemFromInventory(String key, int quantity){
		if(World.getItem(key).checkKeyItem()){ return false; }
		if(inventory.containsKey(key)){
			if(inventory.get(key).getQuantity() < quantity){ return false; }
			if(inventory.get(key).getQuantity() == quantity){
				inventory.remove(key);
				saveItemChangeToDB(key);
				return true;
			}
			inventory.get(key).changeQuantity(-quantity);
			saveItemChangeToDB(key);
			return true;
		}
		return false;
	}
	
	private void saveItemChangeToDB(String itemName){
		InventoryItem itemToCheck= inventory.get(itemName);
		int quan= 0;
		if(itemToCheck != null){ quan= itemToCheck.getQuantity(); }
		dbHelper.updateInventory(playerID, itemName, quan);
	}
	
	// Changes currentWpn if key is a Weapon and exists in inventory
	// Calls appropriate methods to update inventory and save file
	public boolean changeWeapon(String key){
		if(!(World.getItem(key) instanceof Weapon)){ return false; }
		if(!(inventory.containsKey(key))){ return false; }
		addItemToInventory(currentWpn.getKey(), 1);
		removeItemFromInventory(key, 1);
		currentWpn= (Weapon)World.getItem(key);
		dbHelper.updateEquipment(playerID, key, "Weapon");
		return true;
	}
	
	// Changes currentArmor if key is an Armor and exists in inventory
	// Calls appropriate methods to update inventory and save file
	public boolean changeArmor(String key){
		if(!(World.getItem(key) instanceof Armor)){ return false; }
		if(!(inventory.containsKey(key))){ return false; }
		addItemToInventory(currentArmor.getKey(), 1);
		removeItemFromInventory(key, 1);
		currentArmor= (Armor)World.getItem(key);
		dbHelper.updateEquipment(playerID, key, "Armor");
		return true;
	}
	
	// Changes Accessory in given slot if key is an Accessory and exists in inventory
	// Calls appropriate methods to update inventory and save file
	public boolean changeAccessory(String key, int slot){
		if(!(World.getItem(key) instanceof Accessory)){ return false; }
		if(!(inventory.containsKey(key))){ return false; }
		
		if(slot == 1){
			addItemToInventory(accOne.getKey(), 1);
			removeItemFromInventory(key, 1);
			accOne= (Accessory)World.getItem(key);
			dbHelper.updateEquipment(playerID, key, "Acc1");
		}
		if(slot == 2){
			addItemToInventory(accTwo.getKey(), 1);
			removeItemFromInventory(key, 1);
			accTwo= (Accessory)World.getItem(key);
			dbHelper.updateEquipment(playerID, key, "Acc2");
		}
		setTraits();
		return true;
	}
	//endregion
	
	public void changeLocation(String locationName){
		Location newLoc= World.getLocation(locationName);
		if(newLoc != null){
			currentLocation= newLoc;
			dbHelper.updateLocation(playerID, locationName);
		}
	}
	
	public void addQuest(String questName){
		BaseQuest quest= World.getQuest(questName);
		String key= quest.getKey();
		
		questsInProgress.put(key, quest);
	}
	
	
	
	private void completeQuest(String questName){
		if(questsInProgress.containsKey(questName)){
			BaseQuest quest= questsInProgress.get(questName);
			questsInProgress.remove(questName);
			completedQuests.put(questName, quest);
			
			dbHelper.setQuestCompleted(playerID, questName);
		}
	}
	
	//temporary
	@Override
	public String toString(){
		String string= name +"\n"+
				charRace.getName() +"\n"+
				charClass.getName() +"\n"+
				charJob.getName() +"\n\n"+
				
				"Loc: "+ currentLocation.toString() +"\n"+ // temp
				"Gold: "+ gold +"\n"+
				"Status: "+ status +"\n"+
				"Level: "+ level +" ("+ exp +"/"+ toNextLevel() +")\n\n"+
				
				"HP: "+ currentHitPoints +"/"+ maximumHitPoints +"\n"+
				"Str: "+ str +"("+ getAtkPower() +")\n"+
				"Sta: "+ sta +"("+ getDefValue() +")\n"+
				"Agi: "+ agi +"\n"+
				"Speed: "+ speed +"\n\n"+
				
				"Wpn: "+ currentWpn.toString() +"\n\n"+
				"Armor: "+ currentArmor.toString() +"\n\n"+
				"Acc One: "+ accOne.toString() +"\n\n"+
				"Acc Two: "+ accTwo.toString() +"\n\n";
		Iterator<BaseTrait> iterate= traits.values().iterator();
		while(iterate.hasNext()){
			BaseTrait trait= iterate.next();
			string += trait.toString() +"\n";
		}
		string += "\n";
		Iterator<InventoryItem> iterate2= inventory.values().iterator();
		while(iterate2.hasNext()){
			InventoryItem item= iterate2.next();
			string += item.toString() +"\n";
		}
		return string;
	}
}
