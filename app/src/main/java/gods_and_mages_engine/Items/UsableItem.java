package gods_and_mages_engine.Items;

import gods_and_mages_engine.LivingCreature;

public abstract class UsableItem extends BaseItem{
	public UsableItem(String name, String namePlural, String desc, int price){
		super(name, namePlural, desc, price);
	}
	
	public abstract void UseItem(LivingCreature target);
}
