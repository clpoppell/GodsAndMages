package gods_and_mages_engine.Abilities;

/**
 * Used for traits that modify a secondary attribute of a {@code LivingCreature}.
 */
public class BoostTrait extends BaseTrait{
	public final String boostType;
	private final double boostPercentage;
	
	public BoostTrait(String key, String traitName, String desc, String boostType, double boostPercentage){
		super(key, traitName, desc);
		this.boostType= boostType;
		this.boostPercentage= boostPercentage;
	}
	
	/** @return {@code boostPercentage} */
	public double getPercentage(){ return boostPercentage; }
}
