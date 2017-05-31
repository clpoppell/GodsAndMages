package gods_and_mages_engine.Items;

public class Armor extends EquipableItem{
	private final double armorMod;
	
	public Armor(String name, String namePlural, String desc, int price, double armorMod){
		super(name, namePlural, desc, price);
		this.armorMod= armorMod;
	}
	
	public double getArmorMod(){ return this.armorMod; }
	
	@Override
	public String toString(){
		return super.toString() + this.armorMod;
	}
}
