package gods_and_mages_engine.Items;

/**
 * Used for equipable items that modify the attack power attribute of the player character
 * @see gods_and_mages_engine.Player_Char.PlayerCharacter#getAtkPower()
 */
public class Weapon extends EquipableItem{
	public final double dmgMod;
	
	public Weapon(String name, String namePlural, String desc, int price, double dmgMod){
		super(name, namePlural, desc, price);
		this.dmgMod= dmgMod;
	}
	
	@Override
	public String toString(){
		return super.toString() + dmgMod;
	}
}
