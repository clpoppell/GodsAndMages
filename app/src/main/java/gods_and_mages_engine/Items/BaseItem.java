package gods_and_mages_engine.Items;

/**
 * Super class for all item classes, to implement shared shared fields and methods.
 * <p> Allows all item objects to be stored in the same collections. Items that do not fit into
 * any implemented categories can use {@code BaseItem} directly. </p>
 */
public class BaseItem{
	/** The number price is divided by to get default sale price (divisor= {@value}) */
	public static final int DEFAULT_SALE_PRICE_DIVISOR= 2;
	
	//region Variables
	public final String name;
	public final String namePlural;
	public final String description;
	public final int price;
	public final int salePrice;
	//endregion
	
	//region Constructors
	/**
	 * Constructs a new BaseItem by calling {@code BaseItem(String, String, String, int, int)}
	 * with {@code price/DEFAULT_SALE_PRICE_DIVISOR} as {@code salePrice}.
	 */
	public BaseItem(String name, String namePlural, String description, int price){
		this(name, namePlural, description, price, price/DEFAULT_SALE_PRICE_DIVISOR);
	}
	
	public BaseItem(String name, String namePlural, String description, int price, int salePrice){
		this.name= name;
		this.namePlural= namePlural;
		this.description = description;
		this.price= price;
		this.salePrice= salePrice;
	}
	//endregion
	
	/**
	 * Returns key for item, which is item name
	 * <p> Assists with code clarity in regards to map collections </p>
	*/
	public String getKey(){ return name; }
	
	public String toString(){
		return name +" ("+ namePlural +")\n"+ this.description +"\n"+ price +" ("+ salePrice +")\n";
	}
}
