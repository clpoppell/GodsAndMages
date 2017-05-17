package gods_and_mages_engine.Items;

public abstract class Item{
	//region Variables
	private String name;
	private String namePlural;
	private String desc;
	private int price;
	private int salePrice;
	//endregion
	
	//region Constructors
	public Item(String name, String namePlural, String desc, int price){
		this(name, namePlural, desc, price, price/2);
	}
	
	public Item(String name, String namePlural, String desc, int price, int salePrice){
		this.name= name;
		this.namePlural= namePlural;
		this.desc= desc;
		this.price= price;
		this.salePrice= salePrice;
	}
	//endregion
	
	//region Accessors
	public String getName(){ return name; }
	
	public String getNamePlural(){ return namePlural; }
	
	public String getDesc(){ return desc; }
	
	public int getPrice(){ return price; }
	
	public void setPrice(int price){ this.price= price; }
	
	public int getSalePrice(){ return salePrice; }
	//endregion
	
	public String toString(){
		return this.name +"\n"+ this.desc +"\n"+ this.price +"\n";
	}
}
