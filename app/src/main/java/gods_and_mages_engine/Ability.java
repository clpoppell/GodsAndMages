package gods_and_mages_engine;


public abstract class Ability{
	String name;
	String desc;
	
	public Ability(String name, String desc){
		this.name= name;
		this.desc= desc;
	}
	
	public String toString(){
		return this.name +": "+ desc;
	}
}
