package gods_and_mages_engine.Abilities;

// Provides a resistance to a status effect or damage type
public class ResistTrait extends BaseTrait{
	private final String resisType; // Status effect or damage type resistance is provided for
	private final double resisPercentage; // Percentage of protection provided
	
	public ResistTrait(String key, String traitName, String desc, String resisType, double resisPercentage){
		super(key, traitName, desc);
		this.resisType= resisType;
		this.resisPercentage= resisPercentage;
	}
	
	public String getResisType(){ return resisType; }
	
	public double getResisPercentage(){ return resisPercentage; }
	
	public double getPercentage(){ return getResisPercentage(); }
}
