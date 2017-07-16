package gods_and_mages_engine;

import java.util.Random;

/**
 * Convenience class for producing pseudo-random values.
 */
public class RNG{
	private static Random random= new Random();
	
	/**
	 * @param minimumValue lowest number that will be returned
	 * @param maximumValue highest number that will be returned
	 * @return pseudo-random value between {@code minimumValue} and {@code maximumValue}
	 */
	public static int numberBetween(int minimumValue, int maximumValue){
		int range= maximumValue - minimumValue + 1;
		int randomValueInRange= random.nextInt(range);
		return minimumValue + randomValueInRange;
	}
	
	/** @return pseudo-random number less than value */
	public static int numberUnder(int value){ return random.nextInt(value); }
	
	/** @return pseudo-random boolean */
	public static boolean coinFlip(){ return random.nextBoolean(); }
}
