package gods_and_mages_engine;

import java.util.LinkedHashMap;
import java.util.Map;

import gods_and_mages_engine.Abilities.BaseAbility;
import gods_and_mages_engine.Abilities.BaseTrait;

/**
 * Super class for all in-game entities that can enter into battles.
 * <p> Allows all such entities to be stored in the same objects. </p>
 */
public abstract class LivingCreature{
	//region Variables
	protected String name;
	protected int currentHitPoints;
	protected int maximumHitPoints;
	
	protected String status;
	protected boolean canBattle= true;
	
	protected Map<String, BaseTrait> traits;
	protected Map<String, BaseAbility> abilities= new LinkedHashMap<String, BaseAbility>();
	
	/** Physical attack modifier */
	protected int strength;
	/** Physical defensive modifier */
	protected int stamina;
	/** Goes into accuracy and avoidance calculations */
	protected int agility;
	/** Determines turn order in battle */
	protected int speed;
	//endregion
	
	public LivingCreature(String name, int maximumHitPoints, int strength, int stamina, int agility, int speed){
		this.name= name;
		this.maximumHitPoints= maximumHitPoints;
		this.currentHitPoints= maximumHitPoints;
		this.strength = strength;
		this.stamina = stamina;
		this.agility = agility;
		this.speed= speed;
	}
	
	public String getName(){ return name; }
	
	public void setName(String name){ this.name= name; }
	
	public int getCurrentHitPoints(){ return currentHitPoints; }
	
	public int getMaximumHitPoints(){ return maximumHitPoints; }
	
	public String getStatus(){ return status; }
	
	public void setStatus(String status){ this.status= status; }
	
	/**
	 * Applies a change to creature's current hit points.
	 * <p> {@code currentHitPoints} constrained to 0-{@code maximumHitPoints}. </p>
	 * @param amt the amount to add to {@code currentHitPoints}
	 */
	public void changeCurrentHP(int amt){
		if(currentHitPoints + amt >= maximumHitPoints){
			currentHitPoints= maximumHitPoints;
		}
		else if(currentHitPoints + amt <= 0){
			currentHitPoints= 0;
			status= "Dead";
		}
		else{ currentHitPoints += amt; }
	}
	
	//region Abstract methods
	/**
	 * Implementing classes must override this method to provide means of returning calculated speed.
	 * @return calculated speed
	 */
	public abstract int getSpeed();
	
	/**
	 * Implementing classes must override this method to provide means of returning calculated attack power.
	 * @return  calculated attack power
	 */
	public abstract int getAtkPower();
	
	/**
	 * Implementing classes must override this method to provide means of returning calculated defense value.
	 * @return  calculated defense value
	 */
	public abstract int getDefValue();
	
	/**
	 * Implementing classes must override this method to provide means of returning calculated accuracy.
	 * @return calculated accuracy
	 */
	public abstract int getAccuracy();
	
	/**
	 * Implementing classes must override this method to provide means of returning calculated avoidance.
	 * @return calculated avoidance
	 */
	public abstract int getAvoidance();
	//endregion
	
	@Override
	public String toString(){
		return this.name;
	}
}
