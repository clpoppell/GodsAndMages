package gods_and_mages_engine.Abilities;

/**
 * Used for abilities used in battles to cause damage and/or a status effect to opposing {@code LivingCreature}.
 */
public class AttackAbility extends BaseAbility{
	/** Attack's type, used  in damage calculation when a target has special resistance */
	public final String attackType;
	/** Type of damage inflicted by attack */
	public final String dmgType;
	/** Type of status inflicted by attack */
	public final String statusType;
	/** Number of simultaneous targets */
	public final int numTargets;
	/** Modifier applied to user's accuracy in hit calculation */
	public final double accuracyMod;
	/** Modifier applied to user's attack power in damage calculation */
	public final double dmgRange;
	/** Percentage chance of status being inflicted */
	public final double statusPercentage;
	/** Modifier added to user's critical hit percentage */
	public final double critRate;
	/** Multiplier for critical hit damage calculation */
	public final double critMultiplier;
	
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
