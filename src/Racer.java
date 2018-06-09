import java.util.Random;

public class Racer implements Runnable{

	private Thread thread;
	private Random random;
	private String magicWord; // this is for forest
	private Racer friend; 
	private Mountain mountain;
	private Judge judge;
	private TimeKeeper timeKeeper;
	private boolean isReadyToGoOverPassage = false;
	private boolean isSleepingBeforeRiver = false;
	
	public Racer(String threadName, String magicWord, Mountain mountain, Judge judge) {
		random = new Random();
		thread = new Thread(this, threadName);
		this.magicWord = magicWord;
		this.mountain = mountain;
		this.judge = judge; // player needs to know who is the judge to turn time record in
		timeKeeper = new TimeKeeper(thread.getName());
		thread.start();
	}
	
	@Override
	public void run(){
		// race starts
		timeKeeper.setRaceStartTime(Main.time);
		msg("Started race.");
		restAndEat(); // resting before next obstacle
		runForest(); // forest obstacle
		restAndEat(); // resting before next obstacle
		runMountain(); // mountain obstacle
		restAndEatBeforeRiver(); // resting before next obstacle
		runRiver(); // river obstacle
		timeKeeper.setRaceEndTime(System.currentTimeMillis());
		msg("Finished Race.");
		judge.setResults(timeKeeper);
		goHome();
	}
	
	
	// methods below handle the forest obstacle
	private void runForest() {
		timeKeeper.setForestStartTime(System.currentTimeMillis());
		boolean wordIsInMap;
		rush();
		msg("will start looking for magic word");
		wordIsInMap = Forest.searchForest(magicWord, this);
		if (!wordIsInMap) {
			yield();
		}
		msg("has found the map ( magic word=" + magicWord + "): " + wordIsInMap);
		defaultSpeed();
		timeKeeper.setForestEndTime(System.currentTimeMillis());
	}
	
	private void rush() {
		int newPriority = thread.getPriority() + random.nextInt(4) + 1;
		thread.setPriority(newPriority);
		msg("is rushing. (New Priority is " + newPriority + " = " + thread.getPriority() + ")" );
	}
	
	private void defaultSpeed() {
		msg("is slowing down to default priority. (Default priority = 5)");
		thread.setPriority(Thread.NORM_PRIORITY);
	}
	
	private void yield() {
		msg("is being penalized for not finding the map in the forest. (1st)");
		Thread.yield();
		msg("is being penalized for not finding the map in the forest.(2nd)");
		Thread.yield();
	}
	// end forest methods
	
	
	// methods below handle the mountain obstacle
	private void runMountain() {
		timeKeeper.setMountainStartTime(System.currentTimeMillis());
		mountain.addRacer(this);
		if (mountain.isLastRacer(this)) {
			msg("Last racer has arrived to the mountain.");
			mountain.racersStartCrossing();
//			judge.setRacerQueue(mountain.getRacerQueue());
			msg("Last racer notifies first racer in queue to start crossing mountain.");
		}
		
		while(!getIsReadyToGoOverPassage()) {
//			msg("It is not this racers turn to cross the mountain.");
			System.out.print(""); //this is just a hack to not display anything for some reason when I have an empty while loop program will not run
		};
		mountain.goOverPassage(this);
		msg("crossed passage.");
		timeKeeper.setMountainEndTime(System.currentTimeMillis());
	}
	
	public boolean getIsReadyToGoOverPassage() {
		return isReadyToGoOverPassage;
	}
	
	public void setIsReadyToGoOverPassage(boolean isReady) {
		isReadyToGoOverPassage = isReady;
	}
		
	public void crossPassage() {
		crossObstacle("It will take %d ms to cross over passage in the mountain obstacle.",500, 500);
	}
	// end mountain  methods
	
	
	private void restAndEatBeforeRiver() {
		judge.addRacerToQueue(this);
		msg("is resting before river");
//		boolean isSleeping = true;
//		while(isSleeping) {
//			isSleepingBeforeRiver = true;
//			// sleep meaning process does not move on until the judge interrupt the racer up ( process is not actually sleeping)
//			if (thread.isInterrupted()) {
//				isSleeping = false;
//			}
//		}
		try {
			isSleepingBeforeRiver = true;
			Thread.sleep(200000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			msg("The racer was interrupted by the judge. Racer is now awake");
		}
	}
	
	
	// methods below handle the river obstacle
	private void runRiver() {
		timeKeeper.setRiverStartTime(System.currentTimeMillis());
		crossObstacle("It will take %d ms to go across the river.", 7000, 6000); //allow some racers to go acrros river faster than others because some might be more tired than others 
		timeKeeper.setRiverEndTime(System.currentTimeMillis());
	}
	
	public void setSleepingBeforeRiver(boolean ibr) {
		isSleepingBeforeRiver = ibr;
	}
	
	public boolean getSleepingBeforeRiver() {
		return isSleepingBeforeRiver;
	}
	// end river  methods
	
	
	private void restAndEat() {
		sleep("Racer will rest for %d ms."); 
	}

	public void setFriend(Racer friend) {
		this.friend = friend;
	}
	
	private void goHome() {
		// last racer may have a null friend 
		if(friend != null && friend.isAlive()) {
			msg(this.thread.getName() + " is waiting for " + friend.thread.getName());
			friend.join();
			msg(this.thread.getName() + " is going home with his friend " + friend.thread.getName());
		}
		else {
			msg(this.thread.getName() + " is going home.");
		}
	}
	
	public boolean isAlive() {
		return thread.isAlive();
	}
	
	public void join() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sleep(String message) {
		try {
			int time = random.nextInt(30) + 3400;
			message = String.format(message, time);
			msg(message); 
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
	}
	
	
	public void crossObstacle(String msg, int randNoMin, int randNoMax) {
		try {
			int crossingTIme = random.nextInt(randNoMax) + randNoMin;
			if (msg != null) {
				msg(String.format(msg, crossingTIme));
			}
			Thread.sleep(crossingTIme);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void msg(String message) {
		System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+ thread.getName() +": "+message);
	}
	
	public Thread getThread() {
		return thread;
	}
}
