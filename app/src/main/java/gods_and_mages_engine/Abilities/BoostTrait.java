package gods_and_mages_engine.Abilities;


public class BoostTrait extends BaseTrait{
	private final String boostType;
	private final double boostPercentage;
	
	public BoostTrait(String key, String traitName, String desc, String boostType, double boostPercentage){
		super(key, traitName, desc);
		this.boostType= boostType;
		this.boostPercentage= boostPercentage;
	}
	
	public String getBoostType(){ return boostType; }
	
	public double getBoostPercentage(){ return boostPercentage; }
	
	public double getPercentage(){ return getBoostPercentage(); }
}
