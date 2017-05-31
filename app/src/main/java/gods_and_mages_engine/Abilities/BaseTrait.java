package gods_and_mages_engine.Abilities;

// Base class for all traits
public abstract class BaseTrait{
	private final String key; // Unique name used to identify trait
	private final String traitName; // Name shared among all traits that provide a similar buff
	private final String desc;
	
	public BaseTrait(String key, String traitName, String desc){
		this.key= key;
		this.traitName= traitName;
		this.desc= desc;
	}
	
	public String getKey(){ return key; }
	
	public String getTraitName(){ return traitName; }
	
	public abstract double getPercentage();
	
	@Override
	public boolean equals(Object o){
		if(o instanceof BaseTrait && o != null){
			if(((BaseTrait)o).getTraitName().equals(getTraitName())){ return true; }
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		int result= 17;
		return 31 * result + getTraitName().hashCode();
	}
	
	@Override
	public String toString(){ return key +": "+ desc; }
}
