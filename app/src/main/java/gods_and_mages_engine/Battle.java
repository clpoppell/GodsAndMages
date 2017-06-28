package gods_and_mages_engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import gods_and_mages_engine.Abilities.AttackAbility;
import gods_and_mages_engine.Player_Char.PlayerCharacter;

public class Battle{
	private enum battleEndState{ Continue, MonsterWin, PlayerWin }
	private battleEndState continueBattle= battleEndState.Continue;
	private int index= 0;
	
	private PlayerCharacter pc= PlayerCharacter.getPlayerCharacter();
//	private Location location;
	
	private LivingCreature currentTurn;
	private List<Monster> mob;
	private List<LivingCreature> battleList= new ArrayList<LivingCreature>();
	
	//temporary parameter
	public Battle(List<Monster> mList){
		//this.location= location;
		mob= mList;
		
		mobMaker();
		setTurnOrder();
	}
	
	private void mobMaker(){
		battleList.add(pc);
		battleList.addAll(mob);
	}
	
	private void setTurnOrder(){
		Collections.sort(battleList, new Comparator<LivingCreature>(){
			@Override
			public int compare(LivingCreature o1, LivingCreature o2){
				return o2.getSpeed() - o1.getSpeed();
			}
		});
	}
	
	//region BattleRun Operations
	private void battleLoop(){
		while(true){
			if(pc.status.equals("Dead")){ continueBattle= battleEndState.MonsterWin; break; }
			if(!MonstersAlive()){ continueBattle= battleEndState.PlayerWin; break; }
			
			if(index >= battleList.size()){ index= 0; }
			currentTurn= battleList.get(index);
			index++;
			
			if(currentTurn instanceof PlayerCharacter){ break; }
			else{
				if(currentTurn.canBattle){
					//temporary
					AttackAbility ability= (AttackAbility)World.getAbility("Whirlwind Slash");
					monsterAttack(ability);
//					ChooseMonsterAction();
				}
			}
		}
	}
	
	public void pcAttack(int attackIndex){
		if(currentTurn == null){ currentTurn= pc; }
		if(pc.canBattle){
			LivingCreature defender= mob.get(attackIndex);
			//temporary
			AttackAbility ability= (AttackAbility) World.getAbility("Whirlwind Slash");
			
			dealDamage(defender, ability);
			
			battleLoop();
		}
	}
	
	private void monsterAttack(AttackAbility chosenAttack){ dealDamage(pc, chosenAttack); }
	//endregion
	
	public String printResults(){
		String battleStr= "";
		for(LivingCreature l : battleList){
			battleStr += l.name +": "+ l.currentHitPoints +"/"+ l.maximumHitPoints +"\n";
		}
		return battleStr;
	}
	
	//region Status Check
	// Handles status conditions that effect a character at the end of a turn
	private void handleStatusEffect(LivingCreature creature){
		switch(creature.status){
			case "Dead": creature.canBattle= false; break;
			case "Paralyzed": creature.canBattle= false; break;
			case "Poisoned":
				double amt= (-0.1 * creature.maximumHitPoints);
				creature.changeCurrentHP((int)amt);
				break;
		}
	}
	
	// Checks if a monster is left alive
	// If at least one is still alive, returns true
	// If none, returns false
	private boolean MonstersAlive(){
		for(Monster m : mob){
			if(!(m.status.equals("Dead"))){ return true; }
		}
		return false;
	}
	//endregion
	
	private void dealDamage(LivingCreature defender, AttackAbility ability){
		int damage;
		
		if(calcHit(currentTurn.getAccuracy(), ability.accuracyMod, defender.getAvoidance())){
			int attackPower = currentTurn.getAtkPower();
			int defValue= defender.getDefValue();
			
			damage= RNG.numberBetween(attackPower, (int)(Math.ceil(attackPower * ability.dmgRange)));
			double reduction= defValue / (defValue + 100.00);
			
			damage -= (int) Math.ceil((damage * reduction));
			if(damage <= 0){ damage= 1; }
			
			//temporary
			defender.changeCurrentHP(-damage);
			
			//Wakes up defender if asleep
			if(defender.status.equals("Sleep")){ defender.status= "Normal"; }
		}
	}
	
	// Compares a random # < the modified accuracy (r1) with a random # < avoidance (r2)
	// Returns true if r1 < r2, a random boolean if r1 = r2, false otherwise
	private boolean calcHit(int accuracy, double accuracyMod, int avoidance){
		//consider changing how mod affects accuracy
		int modifiedAccuracy = (int) Math.ceil(accuracy * accuracyMod);
		int r1 = RNG.numberUnder(modifiedAccuracy);
		int r2 = RNG.numberUnder(avoidance);
		
		if(r1 > r2){ return true; }
		//consider changing bias
		return r1 == r2 && RNG.coinFlip();
	}
}
