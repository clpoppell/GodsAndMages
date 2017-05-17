package gods_and_mages_engine.Player_Char;

import gods_and_mages_engine.Ability;

public class CharClass extends Characteristic{
	private int hpMod;
	
	public CharClass(String name, String desc, Ability[] abilitiesGranted, int hpMod){
		super(name, desc, abilitiesGranted);
		this.hpMod= hpMod;
	}
	
	public int getHpMod(){
		return hpMod;
	}
}
