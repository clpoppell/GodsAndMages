package gods_and_mages_engine.Items;

// -
public class Weapon extends EquipableItem{
	private final double dmgMod;
	
	public Weapon(String name, String namePlural, String desc, int price, double dmgMod){
		super(name, namePlural, desc, price);
		this.dmgMod= dmgMod;
	}
	
	public double getDmgMod(){ return this.dmgMod; }
	
	@Override
	public String toString(){
		return super.toString() + this.dmgMod;
	}
}
