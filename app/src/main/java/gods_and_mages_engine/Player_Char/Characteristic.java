package gods_and_mages_engine.Player_Char;

public abstract class Characteristic{
	//region Variables
	private String name;
	private String desc;
	private String[] traitsGranted;
	private String[] abilitiesGranted;
	//endregion
	
	public Characteristic(String name, String desc, String[] traitsGranted, String[] abilitiesGranted){
		this.name= name;
		this.desc= desc;
		this.traitsGranted= traitsGranted;
		this.abilitiesGranted= abilitiesGranted;
	}
	
	public String getName(){
		return name;
	}
	
	public String getDesc(){ return desc; }
	
	public String[] getTraitsGranted(){ return traitsGranted; }
	
	public String[] getAbilitiesGranted(){ return abilitiesGranted; }
	
	public String toString(){
		return name +": "+ traitsGranted.toString();
	}
}
