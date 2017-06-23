package gods_and_mages_engine.Items;

import gods_and_mages_engine.World;

public class Accessory extends EquipableItem{
	public final String buff;
	
	public Accessory(String name, String namePlural, String desc, int price, String buffName){
		super(name, namePlural, desc, price);
		buff= buffName;
	}
	
	@Override
	public String toString(){
		return super.toString() + World.getTrait(buff).toString();
	}
}
