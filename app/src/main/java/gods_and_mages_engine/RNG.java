package gods_and_mages_engine;

import java.util.Random;

/**
 * Convenience class for producing pseudo-random values
 */
public class RNG{
	private static Random random= new Random();
	
	public static int numberBetween(int minimumValue, int maximumValue){
		int range= maximumValue - minimumValue + 1;
		int randomValueInRange= random.nextInt(range);
		return minimumValue + randomValueInRange;
	}
	
	public static int numberUnder(int value){ return random.nextInt(value); }
	
	public static boolean coinFlip(){ return random.nextBoolean(); }
}
