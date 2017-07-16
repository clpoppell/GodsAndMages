package gods_and_mages_engine.Abilities;

/**
 * Used for traist that provide a resistance to a status effect or damage type.
 */
public class ResistTrait extends BaseTrait{
	public final String resisType;
	public final double resisPercentage;
	
	public ResistTrait(String key, String traitName, String desc, String resisType, double resisPercentage){
		super(key, traitName, desc);
		this.resisType= resisType;
		this.resisPercentage= resisPercentage;
	}
	
	/** @return {@code resisPercentage} */
	public double getPercentage(){ return resisPercentage; }
}
