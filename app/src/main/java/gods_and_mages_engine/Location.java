package gods_and_mages_engine;

import java.util.HashMap;
import java.util.Map;

public class Location{
	private String name;
	private String buttonName;
	private String description;
//	public Vendor vendorWorkingHere;
//	public Quest questRequiredToEnter;
//	public Quest questAvailableHere;
	
	private Map<String, Integer> monstersLivingHere= new HashMap<String, Integer>();
	
	private Location locationToNorth;
	private Location locationToEast;
	private Location locationToSouth;
	private Location locationToWest;
	
	public Location(String name, String buttonName, String description/*,
			Quest questRequiredToEnter, Quest questAvailableHere*/){
		this.name= name;
		this.buttonName= buttonName;
		this.description= description;
		/*this.questRequiredToEnter= questRequiredToEnter;
		this.questAvailableHere= questAvailableHere;*/
	}
}
