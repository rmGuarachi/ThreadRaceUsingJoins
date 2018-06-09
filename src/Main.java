public class Main {

	private Racer[] racers;
	private Forest forest;
	private Mountain mountain;
	private Judge[] judge;
	private final int MAX_WORDS_IN_FOREST= 350;
	public static long time = System.currentTimeMillis();
	public static final char[] SET_OF_WORDS = {'a','b','c','d'};
	
	Main(int numOfRacers){
		// creating a forest file
		forest = new Forest(MAX_WORDS_IN_FOREST, SET_OF_WORDS);
		// creating the mountain object
		mountain = new Mountain(numOfRacers);
		// initiating judge thread
		System.out.println("Judges have just arrived");
		initJudge(1, numOfRacers);
		// initiating racers thread
		System.out.println("Racers are ready to race");
		initRacers(numOfRacers);
		// setting racers friends for join functionality
		setRacersFriends();
		// this will cause the main thread to wait for the judge thread to end
		try {
			judge[0].getThread().join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initRacers(int numOfRacers) {
		// creating number of racers threads as entered by command line argument
		racers = new Racer[numOfRacers];
		String racer_name = "Racer #";
		for (int i=0; i < numOfRacers; i++) {
			racers[i] = new Racer(
					racer_name + " " + (i + 1), 
					forest.getMap(i),
					mountain,
					judge[0]);
		}
	}
	
	private void initJudge(int numOfJudges, int numberOfRacers) {
		// creates number of judge threads , one in this case where I call this method
		Judge[] judArr = new Judge[numOfJudges];
		for(int i=0; i < numOfJudges; i++) {
			judArr[i] = new Judge(
					"Judge #" + i , 
					numberOfRacers);
		}
		judge = judArr;
	}
	
	private void setRacersFriends() {
		// setting friendship between racers
		for (int i = 0; i < racers.length -1; i++) {
			racers[i].setFriend(racers[i+1]);
		}
	}
	
	public static void main(String[] args) {
		int numberOfRacers = 10;
		try {
			// allows users to enter any number of parameters in Terminal, however it must be an integer
			if (args.length >= 1) {
				numberOfRacers = Integer.parseInt(args[0]);
			}
		}
		catch (NumberFormatException nfe) {
			System.out.println("NumberFormatException: " + nfe.getMessage());
		}
		
		System.out.println("Race is about to start");
		// this will start the race
		Main race = new Main(numberOfRacers);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("\n\nRace is officially Over");
	}
};
