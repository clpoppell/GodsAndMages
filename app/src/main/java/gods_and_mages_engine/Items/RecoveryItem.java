package gods_and_mages_engine.Items;


import gods_and_mages_engine.LivingCreature;

public class RecoveryItem extends UsableItem{
	private double healPercentage;
	
	public RecoveryItem(String name, String namePlural, String desc, int price, double healPercentage){
		super(name, namePlural, desc, price);
		this.healPercentage= healPercentage;
	}
	
	@Override
	public void UseItem(LivingCreature target){
		int amount= (int)(target.getMaximumHitPoints()*healPercentage);
		target.changeCurrentHP(amount);
	}
}
