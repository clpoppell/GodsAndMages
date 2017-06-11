package gods_and_mages_engine.Abilities;

// Base class for all abilities possessed by the player character and monsters
public abstract class BaseAbility{
	private String key; // Unique name used to identify ability
	private String desc; // Description of ability for UI display
	
	public BaseAbility(String key, String desc){
		this.key= key;
		this.desc= desc;
	}
	
	//region Accessors
	public String getKey(){ return this.key; }
	
	public String getDesc(){ return this.desc; }
	//endregion
	
	@Override
	public String toString(){
		return this.key +": "+ desc;
	}
}
