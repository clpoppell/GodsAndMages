package gods_and_mages_engine.Abilities;

/**
 * Super class for all abilities possessed by the player character and monsters.
 * <p> Allows all abilities to be stored in the same collections. </p>
 */
public abstract class BaseAbility{
	/** Unique name used to identify ability */
	public String name;
	public String description;
	
	public BaseAbility(String name, String description){
		this.name= name;
		this.description= description;
	}
	
	@Override
	public String toString(){
		return name +": "+ description;
	}
}
