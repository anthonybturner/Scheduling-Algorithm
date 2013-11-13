/**
 * <b>File:</b> Process.java<br>
 * <b>Description:</b> Represents a process within an Operating System
 * @author Anthony Turner
 *
 */
public class Process {
	
	//Member variables
	private int id;
	private int arrival_time;
	private int burst_time;
	private int priority;
	private PCB pcb;
	
	
	/**
	 * Constructs a new Process with the given id
	 * @param id a unique identification number for this process
	 */
	public Process(int id){
		
		pcb = new PCB();
		setId(id);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getArrivalTime() {
		return arrival_time;
	}

	public void setArrivalTime(int arrival_time) {
		this.arrival_time = arrival_time;
	}

	public int getBurstTime() {
		return burst_time;
	}

	public void setBurstTime(int burst_time) {
		this.burst_time = burst_time;
	}
	
	public String toString(){
		
		return "ID "  + id + " Arrival Time " +  arrival_time +  " Burst Time: " + burst_time ;
	}

	public void setPriority(int priority) {
		
		this.priority = priority;
		
	}
	
	public int getPriority(){
		
		return priority;
	}

}
