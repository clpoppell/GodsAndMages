package gods_and_mages_engine.Player_Char;

import gods_and_mages_engine.Ability;

public abstract class Characteristic{
	//region Variables
	private String name;
	private String desc;
	private Ability[] abilitiesGranted;
	//endregion
	
	public Characteristic(String name, String desc, Ability[] abilitiesGranted){
		this.name= name;
		this.desc= desc;
		this.abilitiesGranted= abilitiesGranted;
	}
	
	public String getName(){
		return name;
	}
	
	public String getDesc(){ return desc; }
	
	public Ability[] getAbilitiesGranted(){ return abilitiesGranted; }
	
	public String toString(){
		return this.name;
	}
}
