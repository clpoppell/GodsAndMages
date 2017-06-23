package gods_and_mages_engine.Abilities;

// Base class for all traits
public abstract class BaseTrait{
	public final String key; // Unique name used to identify trait
	public final String traitClass; // Name shared among all traits that provide a similar buff
	public final String desc;
	
	public BaseTrait(String key, String traitClass, String desc){
		this.key= key;
		this.traitClass = traitClass;
		this.desc= desc;
	}
	
	public abstract double getPercentage();
	
	@Override
	public boolean equals(Object o){
		if(o instanceof BaseTrait && o != null){
			if(((BaseTrait)o).traitClass.equals(traitClass)){ return true; }
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		int result= 17;
		return 31 * result + traitClass.hashCode();
	}
	
	@Override
	public String toString(){ return key +": "+ desc; }
}
