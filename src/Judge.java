import java.util.Random;
import java.util.Vector;

public class Judge implements Runnable {

	Thread thread;
	Random random = new Random();
	private final int racersTotal;
	private volatile Vector<Racer> racerQueue;
	private Vector<TimeKeeper> results;
	
	public Judge(String threadName, int racersTotal) {
		results = new Vector<>(); // this vector is used so that the judge will interrupt players in the order they finished the mountain obstacle
		thread = new Thread(this, threadName);
		racerQueue = new Vector<>();
		this.racersTotal = racersTotal;
		thread.start();
	}
	
	@Override
	public void run() {
		while( racerQueue.size() < racersTotal); // racerQueue will be < nRacers until the players finish the mountain, so we must wait
		int i = 0;
		while(i < racersTotal) {
			// getting racer object so I can interrupt if its is sleeping before river
			Racer racer = racerQueue.get(i);
			if (racer.getSleepingBeforeRiver()) {
				try {
					Thread.sleep(100 + random.nextInt(2000)); // gives some time for the racer thread to run sleep method after setting boolean isSleeping before river to true
					thread.setPriority(8); // will allow the judge to interrupt the racer asap
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				racer.getThread().interrupt();
				msg("will interrupt " + racer.getThread().getName());
				i++;
				thread.setPriority(Thread.NORM_PRIORITY); 
			}
		}
		// judge is announcing the winners, and is getting all the time records from each individual player
		while(results.size() != racersTotal);
		try {
			racerQueue.get(racersTotal -1).getThread().join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		printRaceResults();
		printObstacleResults();
	}
	
	// will help populate the Vector queue
	public void setRacerQueue(Vector<Racer> queue) {
		racerQueue = queue;
	}
	
	public void addRacerToQueue(Racer racer) {
		racerQueue.addElement(racer);
	}
	
	public void msg(String m) {
		System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+ thread.getName() +": "+m);
	}

	// is used so that Racers can share information with judge in this case to populate vector queue
	public Thread getThread() {
		return thread;
	}
	
	// players are turning in the time records to judge
	public void setResults(TimeKeeper time) {
		results.addElement(time);
	}
	
	private void printRaceResults() {
		for (int i  = 0 ; i < racersTotal; i++) {
			TimeKeeper record = results.get(i);
			msg(record.getRacerName() + "'s total race time: " + record.getTotalRaceTime() + "ms");
		}
	}
	
	public void printObstacleResults() {
		for (int i  = 0 ; i < racersTotal; i++) {
			TimeKeeper record = results.get(i);
			msg(record.getRacerName() + "'s obstacle record breakdown" + "\n" +
					"Forest obstacle: " + record.getTotalForestTime()  + "ms" + "\n" +
					"Mountain obstacle: " + record.getTotalMountainTime() + "ms" + "\n" +
					"River obstacle: " + record.getTotalRiverTime() + "ms");
		}
		
	}
}
