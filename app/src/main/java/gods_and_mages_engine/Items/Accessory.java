package gods_and_mages_engine.Items;

import gods_and_mages_engine.Abilities.BaseTrait;
import gods_and_mages_engine.World;

public class Accessory extends EquipableItem{
	public final BaseTrait buff;
	
	public Accessory(String name, String namePlural, String desc, int price, String buffName){
		super(name, namePlural, desc, price);
		buff= World.getTrait(buffName);
	}
	
	@Override
	public String toString(){
		return super.toString() + this.buff.toString();
	}
}
