package gods_and_mages_engine.Items;

/**
 * Stores a BaseItem object along with the quantity of the item in the player character's inventory.
 */
public class InventoryItem{
	/** The baseItem represented by this */
	private final BaseItem item;
	private int quantity;
	
	public InventoryItem(BaseItem item, int quantity){
		this.item= item;
		this.quantity= quantity;
	}
	
	/** @return {@code item.namePlural} if {@code quantity} greater than 1, otherwise {@code item.name} */
	public String getName(){ return quantity > 1 ? item.namePlural : item.name; }
	
	public int getQuantity(){ return quantity; }
	
	public void changeQuantity(int amountToChange){ quantity += amountToChange; }
	
	@Override
	public String toString(){
		return getName() +": "+ quantity;
	}
}
