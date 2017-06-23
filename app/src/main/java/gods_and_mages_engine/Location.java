package gods_and_mages_engine;

import java.util.HashMap;
import java.util.Map;

public class Location{
	public final String name;
	public final String buttonName;
	public final String description;
	public final String questRequired;
	
	private final String[] adjacentLocations;
	private final String[] monsters= new String[10];
//	public final Vendor vendorWorkingHere;
//	public final BaseQuest questRequiredToEnter;
//	public final BaseQuest questAvailableHere;
	
	public final Map<String, Integer> monstersLivingHere= new HashMap<String, Integer>();
	
	public Location(String name, String buttonName, String description, String questRequired,
					String[] adjacentLocations/*, BaseQuest questAvailableHere*/){
		this.name= name;
		this.buttonName= buttonName;
		this.description= description;
		this.questRequired= questRequired;
		this.adjacentLocations= adjacentLocations;
		/*this.questRequiredToEnter= questRequiredToEnter;
		this.questAvailableHere= questAvailableHere;*/
	}
	
	@Override
	public String toString(){
		return name +": "+ description;
	}
}
