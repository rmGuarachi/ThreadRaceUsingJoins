import java.util.Vector;

public class Mountain {
	
	private Vector<Racer> racerQueue;
	private int nextRacer = 0;
	private final int MAX_SIZE;
	public Mountain(int size) {
		MAX_SIZE = size;
		racerQueue = new Vector<Racer>();
	}
	
	public void addRacer(Racer racer) {
		// mountain queue to determine who will cross passage next
		racerQueue.addElement(racer);
	}
	
	public boolean isLastRacer(Racer racer) {
		// allows the last player to tell the 1st player to start crossing the passage
		boolean isLastRacer = false;
		if (racerQueue.size() == MAX_SIZE)
			isLastRacer = (racer == racerQueue.get(MAX_SIZE-1));
		return isLastRacer;
	}
	
	public void racersStartCrossing() {
		getFirstRacer().setIsReadyToGoOverPassage(true);
	}
	
	public Racer getNextRacer() {
		nextRacer++;
		return racerQueue.get(nextRacer);
	}
	
	private Racer getFirstRacer() {
		return racerQueue.firstElement();
	}
	
	public Vector<Racer> getRacerQueue(){
		return racerQueue;
	}
	
	public synchronized void goOverPassage(Racer racer) {
		// synchronized method to allow only one player to start crossing the passage
		racer.crossPassage();
		if (!isLastRacer(racer)) {
			getNextRacer().setIsReadyToGoOverPassage(true);
		}
	}
}
