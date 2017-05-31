package gods_and_mages_engine.Items;

public class BaseItem{
	//region Variables
	private final String name;
	private final String namePlural;
	private final String desc;
	private final int price;
	private final int salePrice;
	//endregion
	
	//region Constructors
	public BaseItem(String name, String namePlural, String desc, int price){
		this(name, namePlural, desc, price, price/2);
	}
	
	public BaseItem(String name, String namePlural, String desc, int price, int salePrice){
		this.name= name;
		this.namePlural= namePlural;
		this.desc= desc;
		this.price= price;
		this.salePrice= salePrice;
	}
	//endregion
	
	//region Accessors
	// Returns key for item, which is item name
	// Differentiated from getName() for code clarity
	public String getKey(){ return name; }
	
	public boolean checkKeyItem(){
		if(this instanceof KeyItem){ return true; }
		return false;
	}
	
	public String getName(){ return name; }
	
	public String getNamePlural(){ return namePlural; }
	
	public String getDesc(){ return desc; }
	
	public int getPrice(){ return price; }
	
	public int getSalePrice(){ return salePrice; }
	//endregion
	
	public String toString(){
		return this.name +"\n"+ this.desc +"\n"+ this.price +"\n";
	}
}
