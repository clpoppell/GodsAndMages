package gods_and_mages_engine.Abilities;

/**
 * Super class for all trait classes, to implement shared fields and methods.
 * <p> Allows all trait objects to be stored in the same collections. </p>
 */
public abstract class BaseTrait{
	/** Unique name used to identify trait */
	public final String name;
	/** Name shared among all traits that provide a similar buff/debuff */
	public final String traitType;
	public final String description;
	
	public BaseTrait(String name, String traitType, String description){
		this.name = name;
		this.traitType= traitType;
		this.description= description;
	}
	
	/**
	 * Implemented classes must override this method to provide a means of uniformly accessing the
	 * percentage magnitude of said trait
	 * @see gods_and_mages_engine.Player_Char.PlayerCharacter#placeTrait(String)
	 * @return the percentage associated with the particular trait
	 */
	public abstract double getPercentage();
	
	@Override
	public String toString(){ return name +": "+ description; }
}
