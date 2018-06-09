
public class TimeKeeper {
	
	// this class will keep track of time for each player
	// this just has setters and getters to set time and obtain time for race and each individual obstacle
	private String racerName;
	private long raceStartTime = 0;
	private long raceEndTime = 0;
	private long forestStartTime = 0 ;
	private long forestEndTime = 0 ;
	private long mountainStartTime = 0;
	private long mountainEndTime = 0;
	private long riverStartTime = 0;
	private long riverEndTime = 0;
	
	public TimeKeeper(String name) {
		racerName = name;
	}
	
	public String getRacerName() {
		return racerName;
	}
	
	public long getTotalRaceTime() {
		return raceEndTime - raceStartTime;
	}
	
	public long getTotalRiverTime() {
		return riverEndTime - riverStartTime;
	}
	
	public long getTotalForestTime() {
		return forestEndTime - forestStartTime;
	}
	
	public long getTotalMountainTime() {
		return mountainEndTime - mountainStartTime;
	}
	
	public void setRacerName(String racerName) {
		this.racerName = racerName;
	}

	public long getRaceStartTime() {
		return raceStartTime;
	}
	public void setRaceStartTime(long raceStartTime) {
		this.raceStartTime = raceStartTime;
	}
	public long getRaceEndTime() {
		return raceEndTime;
	}
	public void setRaceEndTime(long raceEndTime) {
		this.raceEndTime = raceEndTime;
	}
	public long getForestStartTime() {
		return forestStartTime;
	}
	public void setForestStartTime(long forestStartTime) {
		this.forestStartTime = forestStartTime;
	}
	public long getForestEndTime() {
		return forestEndTime;
	}
	public void setForestEndTime(long forestEndTime) {
		this.forestEndTime = forestEndTime;
	}
	public long getMountainStartTime() {
		return mountainStartTime;
	}
	public void setMountainStartTime(long mountainStartTime) {
		this.mountainStartTime = mountainStartTime;
	}
	public long getMountainEndTime() {
		return mountainEndTime;
	}
	public void setMountainEndTime(long mountainEndTime) {
		this.mountainEndTime = mountainEndTime;
	}
	public long getRiverStartTime() {
		return riverStartTime;
	}
	public void setRiverStartTime(long riverStartTime) {
		this.riverStartTime = riverStartTime;
	}
	public long getRiverEndTime() {
		return riverEndTime;
	}
	public void setRiverEndTime(long riverEndTime) {
		this.riverEndTime = riverEndTime;
	}
}
