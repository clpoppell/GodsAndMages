package gods_and_mages_engine.Items;

public class InventoryItem{
	private final BaseItem details;
	private int quantity;
	
	public InventoryItem(BaseItem details, int quantity){
		this.details= details;
		this.quantity= quantity;
	}
	
	public String getKey(){ return details.getKey(); }
	
	public String getName(){ return quantity > 1 ? details.getNamePlural() : details.getName(); }
	
	public int getQuantity(){ return quantity; }
	
	public void changeQuantity(int amount){ quantity += amount; }
	
	@Override
	public boolean equals(Object o){
		if(o instanceof BaseItem && o != null){
			InventoryItem item= (InventoryItem)o;
			if(item.details.equals(this)){ return true; }
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		int result= 17;
		return 31 * result + getKey().hashCode();
	}
	
	@Override
	public String toString(){
		return getName() +": "+ getQuantity();
	}
}
