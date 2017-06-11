package gods_and_mages_engine.Player_Char;

import android.content.res.Resources;

import com.tadbolmont.homecoming.R;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import gods_and_mages_engine.Abilities.BaseTrait;
import gods_and_mages_engine.Abilities.BoostTrait;
import gods_and_mages_engine.App;
import gods_and_mages_engine.Database.SaveGameDBHelper;
import gods_and_mages_engine.Items.*;
import gods_and_mages_engine.LivingCreature;
import gods_and_mages_engine.World;

public class Player extends LivingCreature{
	//region Default values for player character
	private static final int STARTING_LEVEL= 0;
	private static final int DEFAULT_HP= 20;
	private static final int DEFAULT_STR= 10;
	private static final int DEFAULT_STA= 10;
	private static final int DEFAULT_AGI= 10;
	private static final int DEFAULT_SPEED= 10;
	private static final int DEFAULT_GOLD= 50;
	//endregion
	
	private static final double XP_EXPONENT= 1.5;
	private static final int BASE_XP= 10;
	private static Player player= null;
	
	private static SaveGameDBHelper dbHelper= SaveGameDBHelper.getInstance();
	private static Resources RES= App.context.getResources();
	
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
	private Map<String, InventoryItem> inventory= new LinkedHashMap<String, InventoryItem>(); // Current inventory;
	// Derived Stats
	
	// Location & Quest Info
	
	//endregion
	
	//region Character Instantiation
	public static Player getPlayer(){ return player; }
	
	public static void makePlayer(int id, String[] charInfo){
		if(charInfo == null){ player= new Player(dbHelper.loadSave(id), id, true); }
		else{ player= new Player(charInfo, id); }
	}
	
	public Player(String[] charInfo, int playerID){
		super(charInfo[0], DEFAULT_HP, DEFAULT_STR, DEFAULT_STA, DEFAULT_AGI, DEFAULT_SPEED);
		this.playerID= playerID;
		
		this.charRace= World.makeCharRace(charInfo[1]);
		this.charClass= World.makeCharClass(charInfo[2]);
		this.charJob= World.makeCharJob(charInfo[3]);
		
		this.level= STARTING_LEVEL;
		this.status= "Normal";
		this.gold= DEFAULT_GOLD;
		this.exp= 0;
		
		String[] equipmentList= RES.getStringArray(R.array.default_equipment);
		this.currentWpn= (Weapon)World.getItem(equipmentList[0]);
		this.currentArmor= (Armor)World.getItem(equipmentList[1]);
		
		
		makeDefaultInventory();
		setTraits();
		setAbilities();
		dbHelper.insertCharacter(playerID, name, charRace.getName(), charClass.getName(), charJob.getName(),
				level, maximumHitPoints, str, sta, agi, speed, gold, exp, currentHitPoints,
				equipmentList[0], equipmentList[1], equipmentList[2], equipmentList[3], status, getLocationName());
	}
	
	public Player(String[] charInfo, int playerID, boolean load){
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
		this.accOne= (Accessory)World.getItem("Unenchanted Ring");
		this.accTwo= (Accessory)World.getItem("Unenchanted Ring");
		
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
	//endregion
	
	private void saveItemChangeToDB(String itemName){
		InventoryItem itemToCheck= inventory.get(itemName);
		int quan= 0;
		if(itemToCheck != null){ quan= itemToCheck.getQuantity(); }
		dbHelper.updateInventory(playerID, itemName, quan);
	}
	
	// temporary
	public String getLocationName(){ return "DEFAULT"; }
	
	private void setTraits(){
		for(String key : this.charRace.getTraitsGranted()){
			placeTrait(key);
		}
		for(String key : this.charClass.getTraitsGranted()){
			placeTrait(key);
		}
		for(String key : this.charJob.getTraitsGranted()){
			placeTrait(key);
		}
	}
	
	// If trait type is in traits map, check percentage value of trait and old trait
	// Then, if old trait's percentage is higher, make no change to map and return
	// Otherwise, add trait to traits map
	private void placeTrait(String key){
		BaseTrait trait= World.getTrait(key);
		String traitName= trait.getTraitName();
		
		if(this.traits.containsKey(traitName)){
			BaseTrait oldTrait= traits.get(traitName);
			if(trait.getPercentage() < oldTrait.getPercentage()){ return; }
		}
		this.traits.put(traitName, trait);
	}
	
	private void setAbilities(){
		/*for(String key : this.charRace.getAbilitiesGranted()){
			this.abilities.add(World.getAbility(key));
		}
		for(String key : this.charClass.getAbilitiesGranted()){
			this.abilities.add(World.getAbility(key));
		}
		for(String key : this.charJob.getAbilitiesGranted()){
			this.abilities.add(World.getAbility(key));
		}*/
	}
	
	public void addExp(int expToAdd){
		exp += expToAdd;
		dbHelper.updateStatus(playerID, gold, exp, currentHitPoints, status);
		updateLevel();
	}
	
	public void addGold(int goldToAdd){ gold += goldToAdd; }
	
	public int toNextLevel(){ return (int)Math.floor(BASE_XP*(Math.pow(level+1, XP_EXPONENT))); }
	
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
	
	//region Accessors
	public int getExp(){ return exp; }
	
	public int getLevel(){ return level; }
	
	//temporary
	public CharRace getCharRace(){ return charRace; }
	
	//temporary
	public CharClass getCharClass(){ return charClass; }
	
	//temporary
	public CharJob getCharJob(){ return charJob; }
	
	public int getGold(){ return gold; }
	
	@Override
	public int getSpeed(){
		return speed;
	}
	
	// Retrieves value for actual attack power
	// Overrides LivingCreature.getAtkPower()
	@Override
	public int getAtkPower(){
		int mod= (int)(str*(this.currentWpn.getDmgMod()));
		if(mod < 1){ mod= 1; }
		return str + mod;
	}
	
	@Override
	public int getDefValue(){
		int mod= (int)(sta*(this.currentArmor.getArmorMod()));
		if(mod < 1){ mod= 1; }
		return sta + mod;
	}
	
	@Override
	public int getAccuracy(){
		int accuracy= agi;
		BoostTrait accuracyUp= (BoostTrait)traits.get("Accuracy Up");
		BoostTrait accuracyDown= (BoostTrait)traits.get("Accuracy Down");
		
		if(!(accuracyUp == null)){ accuracy += agi*accuracyUp.getPercentage(); }
		if(!(accuracyDown == null)){ accuracy += agi*accuracyDown.getPercentage(); }
		
		return accuracy;
	}
	
	@Override
	public int getAvoidance(){
		int avoidance= agi;
		BoostTrait avoidanceUp= (BoostTrait)traits.get("Avoid Up");
		BoostTrait avoidanceDown= (BoostTrait)traits.get("Avoid Down");
		
		if(!(avoidanceUp == null)){ avoidance += agi*avoidanceUp.getPercentage(); }
		if(!(avoidanceDown == null)){ avoidance += agi*avoidanceDown.getPercentage(); }

		return avoidance;
	}
	
	//temporary
	public Weapon getCurrentWpn(){ return currentWpn; }
	
	public void setCurrentWpn(Weapon newWpn){
		this.currentWpn= newWpn;
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
		
		return true;
	}
	
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
	//endregion
	
	//temporary
	@Override
	public String toString(){
		String string= name +"\n"+
				charRace.getName() +"\n"+
				charClass.getName() +"\n"+
				charJob.getName() +"\n\n"+
				
				"Loc: "+ getLocationName() +"\n"+
				"Gold: "+ gold +"\n"+
				"Status: "+ status +"\n"+
				"Level: "+ level +" ("+ exp +"/"+ toNextLevel() +")\n\n"+
				
				"HP: "+ currentHitPoints +"/"+ maximumHitPoints +"\n"+
				"Str: "+ getAtkPower() +"\n"+
				"Sta: "+ getDefValue() +"\n"+
				"Agi: "+ agi +"\n"+
				"Speed: "+ speed +"\n\n"+
				
				"Wpn: "+ currentWpn.toString() +"\n\n"+
				"Armor: "+ currentArmor.toString() +"\n\n"+
				"Acc One: "+ accOne.toString() +"\n\n"+
				"Acc One: "+ accOne.toString() +"\n\n";
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
