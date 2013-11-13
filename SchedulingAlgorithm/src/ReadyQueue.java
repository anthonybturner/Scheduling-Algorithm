/**
 * <b>File:</b> ReadyQueue.java<br>
 * <b>Description: </b>An abstract representation of an ReadyQueue<br>
 * @author Anthony Turner
 *
 */
public abstract class ReadyQueue {
	
	abstract public void addProcess(Process p);
	abstract public Process removeProcess();
	abstract public boolean isEmpty();
	abstract public int getSize();
	abstract public boolean hasProcess();
}
