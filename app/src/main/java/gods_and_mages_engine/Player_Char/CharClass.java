package gods_and_mages_engine.Player_Char;

public class CharClass extends Characteristic{
	private int hpMod;
	
	public CharClass(String name, String desc, String[] traitsGranted, String[] abilitiesGranted, int hpMod){
		super(name, desc, traitsGranted, abilitiesGranted);
		this.hpMod= hpMod;
	}
	
	public int getHpMod(){
		return hpMod;
	}
}
