import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;
/**
 * 
 * @author Anthony Turner<br>
 * <b>Description:</b> A first-come-first-serve scheduling algorithm </br>
 * <b>Date:</b> October 28, 2013<br>
 * <b>Course: </b>Operating Systems 
 * 
 */

public class Scheduler {
	
	//Member variables
	private FIFOReadyQueue fcfs_queue;
	private boolean cpu_is_idle;
	private boolean isRunning;
	
	/**
	 * Constructs a new FCFS Scheduler
	 */
	public Scheduler(){
		
		fcfs_queue = new FIFOReadyQueue();
		isRunning = true;
		cpu_is_idle = false;
		String input_file = readInput();
		startScheduler(input_file);
		startProcessing();

	}

	private void startScheduler(final String input_file) {
		
		Thread t = new Thread( new Runnable() {
			
			@Override
			public void run() {
				
				createProcesses(input_file);
				cpu_is_idle = true;		
			}
		});
		t.start();
	}

	private void startProcessing() {
		
		int total_burst_time = 0;
		double total_wait = 0.0;
		int size = 0;
		String output = "";
		
		while(isRunning){
		
			if( cpu_is_idle ){
			
				if( fcfs_queue.hasProcess() ){
					//This is where Dispatcher would remove process from Ready Queue
					Process process = fcfs_queue.removeProcess();
					
					int burst_time = process.getBurstTime();//1
					int end_time = total_burst_time + burst_time;//0+1 = 1
					
					print("Process " + process.getId());
					print("Start time is " + total_burst_time);

					//Store the start and end time, and process id in a string file for writing to disk after processing
					output+= total_burst_time + " " +  end_time + " " + process.getId() + "\n";				
					
					doWork(process);//Simulating doing work on the process

					int wait_time = total_burst_time - process.getArrivalTime();//0-0

					//1
					total_burst_time += burst_time;//Total burst time of all processes/duration

					print("Wait time is " + wait_time + "\n");
					total_wait += wait_time;
					size++;
					
				}else{
					
					//print average waiting time
					total_wait = total_wait/size;
					print("Average Wait time is " + total_wait);
					writeOut(output);
					isRunning = false;
				}
			}
			
			Thread.currentThread();
			try {
				Thread.sleep(10); //Simulating CPU doing other work in the system
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
	}

	private void writeOut(String output) {
		
		try {
			FileWriter writer = new FileWriter("output.dat");
			writer.write("My output.data\n");
			writer.write(output);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	//Simulate doing work, this is not needed
	private void doWork(Process process) {
		
		cpu_is_idle = false;
		int time = 0;
		int duration = process.getBurstTime();
		
		for(;time < duration; time++){
		
			//print("working... " + process.getId());
			
		}
		cpu_is_idle = true;
	}

	private void createProcesses(String input_file) {
		
		Scanner reader = new Scanner(input_file);//Read in the file
		
		
		if( reader.hasNextLine() ){
			
			int process_count = Integer.valueOf(reader.nextInt());//First read value should be the number of processes.
			Process[] arrival_times = new Process[process_count];

			
			if( reader.hasNextLine() ){//Read not rr or preemptive and quantum time
				//Not needed for FCFS scheduling algorithm
			}
			
			int process_id = 0;
			
			while( reader.hasNextLine()){//Read all processes
				
				String process_info_line = reader.nextLine();
				StringTokenizer tokens = new StringTokenizer(process_info_line);
				
				if( tokens.countTokens() != 3 )//We must have three tokens (Arrival and Burst Time, and priority)
					continue;//If not, then skip creating the process and advance to the next line
				
				try{
					
					int arrival_time = Integer.parseInt(tokens.nextToken());
					int burst_time = Integer.parseInt(tokens.nextToken());
					int priority = Integer.parseInt(tokens.nextToken());//First-Come-First-Serve does not use this priority
										
					Process process = new Process(process_id+1);
					process.setArrivalTime(arrival_time);
					process.setBurstTime(burst_time);
					process.setPriority(priority);
					
					arrival_times[process_id] = process;
					process_id++;

				}catch(NumberFormatException e){
					
					//If we don't have an integer value for any of the CPU process parameters then skip 
					//creating that process
					print("Invalid info for process");
				}
			}//End reading/creating all processes
			
			sortProcesses(arrival_times);
			populateFCFSQueue(arrival_times);
			
		}else{
			print("no values exiting program...");
		}		
		reader.close();
	}

	private void sortProcesses(Process[] arrival_times) {
		
		for(int i = 0; i < arrival_times.length; i++){
						
			for(int j = i+1; j < arrival_times.length; j++){
								
				if( arrival_times[i].getArrivalTime() > arrival_times[j].getArrivalTime() ){
					
					Process temp = arrival_times[i];
					arrival_times[i] = arrival_times[j];
					arrival_times[j] = temp;
				}
			}			
		}
	}

	//Adds the process in the order they arrived
	//to the first-come-first-serve queue 
	private void populateFCFSQueue(Process[] arrival_times) {
		
		for(int i = 0; i < arrival_times.length; i++){
			
			fcfs_queue.addProcess( arrival_times[i] );
		}	
	}

	private String readInput() {
		
		System.out.println("Enter input file name...");
		Scanner scan = new Scanner(System.in);
		
		BufferedReader b_reader = null;
		String process_info = "";

		File file = new File(scan.next());
		try {
			
			b_reader = new BufferedReader(new FileReader(file));
			String line = "";
			while((line = b_reader.readLine()) != null ){
				
				process_info += line + "\n";
				
			}
						
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			
			scan.close();
			try {
				
				b_reader.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return process_info;
	}
	
	
	public void print(String msg){
		
		System.out.println(msg);
	}

	public static void main(String[] args) {
		
		new Scheduler();
		

	}

}
