package gods_and_mages_engine.Abilities;


public class AttackAbility extends BaseAbility{
	public final String attackType; // Attack's type, used  in damage calculation when a target has special resistance
	public final String dmgType; // Type of damage inflicted by attack
	public final String statusType; // Type of status inflicted by attack
	public final int numTargets; // Number of simultaneous targets
	public final double accuracyMod; // Modifier applied to user's accuracy in hit calculation
	public final double dmgRange; // Modifier applied to user's attack power in damage calculation
	public final double statusPercentage; // Percentage chance of status being inflicted
	public final double critRate; // Modifier added to user's critical hit percentage
	public final double critMultiplier; // Multiplier for critical hit damage calculation
	
	public AttackAbility(String key, String desc, String attackType, String dmgType, String statusType,
						 int numTargets, double accuracyMod, double dmgRange, double statusPercentage,
						 double critRate, double critMultiplier){
		super(key, desc);
		this.attackType= attackType;
		this.dmgType= dmgType;
		this.statusType= statusType;
		this.numTargets= numTargets;
		this.accuracyMod= accuracyMod;
		this.dmgRange= dmgRange;
		this.statusPercentage= statusPercentage;
		this.critRate= critRate;
		this.critMultiplier= critMultiplier;
	}
}
