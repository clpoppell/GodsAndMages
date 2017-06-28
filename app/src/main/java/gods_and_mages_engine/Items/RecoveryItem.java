package gods_and_mages_engine.Items;

import gods_and_mages_engine.LivingCreature;

/**
 * Used for items which alter current hit points and/or mana points of a {@code LivingCreature}
 */
public class RecoveryItem extends UsableItem{
	private final double healPercentage;
	
	public RecoveryItem(String name, String namePlural, String desc, int price, double healPercentage){
		super(name, namePlural, desc, price);
		this.healPercentage= healPercentage;
	}
	
	/**
	 * Calculates the amount that {@code target.currentHitPoints} should be changed by item use, then applies change
	 * <p> {@code amountToChange} is calculated as {@code target.getMaximumHitPoints multiplied by {@code healPercentage}} </p>
	 * @param target the livingCreature to be affected
	 */
	@Override
	public void UseItem(LivingCreature target){
		int amountToChange= (int)(target.getMaximumHitPoints()*healPercentage);
		target.changeCurrentHP(amountToChange);
	}
}
