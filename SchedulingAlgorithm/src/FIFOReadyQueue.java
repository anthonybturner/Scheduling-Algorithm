import java.util.LinkedList;
import java.util.Queue;
/**
 * <b>File: </b> FIFOReadyQueue.java<br>
 * <b>Description:</b> Represents a First-in-First-out Ready Queue for an Operating System<br>
 * @author Anthony Turner
 *
 */

public  class FIFOReadyQueue extends ReadyQueue {

	private Queue<Process> fcfs_queue;
	
	public FIFOReadyQueue(){
		
		fcfs_queue = new LinkedList<Process>();
	}
	
	@Override
	public void addProcess(Process p) {
		
		fcfs_queue.add(p);
	}

	@Override
	public Process removeProcess() {
		
		return fcfs_queue.poll();
	}
	
	public boolean isEmpty(){
		
		return fcfs_queue.isEmpty();
	}

	public int getSize() {
		
		return fcfs_queue.size();
	}

	public boolean hasProcess() {
		return !isEmpty();
		
	}
	

}
