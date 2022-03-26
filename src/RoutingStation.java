/* 
  Name: Jennifer Nguyen
  Course: CNT 4714 Spring 2022 
  Assignment title: Project 2 – Multi-threaded programming in Java 
  Date:  February 16, 2022 
 
  Class: CNT 4714
*/ 

import java.util.Random;

public class RoutingStation implements Runnable
{
	private Random randgen = new Random();
	private int stationNum;
	private int workload;
	private Conveyor inconveyor;
	private Conveyor outconveyor;
	final int seed = 500;
   
    //RoutingStation constructor method
    public RoutingStation(int stationNum, int workload, Conveyor inconveyor, Conveyor outconveyor)
    {
        this.stationNum = stationNum;
        this.workload = workload;
        this.inconveyor = inconveyor;
        this.outconveyor = outconveyor;
        
        System.out.println("Routing Station " + stationNum + " has total workload of " + workload);
    
    }
    public int getStationNum() 
    {
		return stationNum;
	}

	public void setStationNum(int stationNum) {
		this.stationNum = stationNum;
	}

	public int getWorkload() {
		return workload;
	}

	public void setWorkload(int workload) {
		this.workload = workload;
	}

	public Conveyor getInConveyer() {
		return inconveyor;
	}

	public void setInonveyer(Conveyor inconveyor) {
		this.inconveyor = inconveyor;
	}

	public Conveyor getoutconveyor() {
		return outconveyor;
	}

	public void setoutconveyor(Conveyor outconveyor) {
		this.outconveyor = outconveyor;
	}
    
	public void goTosleep()
	{
	    try {
	       Thread.sleep(randgen.nextInt(seed)); 
	    }
	    catch(InterruptedException e) 
	    {
	       e.printStackTrace();
	    }
	}

    public void doWork()
	{	
		System.out.println("\n* * * * * * Routing Station " + this.getStationNum() + ": * * * * * CURRENTLY HARD AT WORK MOVING PACKAGES.  * * * * *");
		System.out.println("\nRouting Station " + this.getStationNum() + ": has "+ this.workload + " package groups left to move. ");
		this.setWorkload(this.getWorkload() - 1);
		goTosleep(); 
		
		if(this.getWorkload() == 0)
		{
			System.out.println("*  *  Station  " + this.getStationNum() + "  Workload  successfully  completed.  *  *  Station  " + this.getStationNum() + " releasing locks and going offline * *  ");
		}
	}
	

	
	@Override
    public void run() 
    {
		// set conveyor details
		System.out.println("\n%%%%%%%%%%%%% Routing Station " + this.getStationNum() + " Coming Online - Initializing Conveyor %%%%%%%%%%%%%\n");
		System.out.println("Routing Station " + this.getStationNum() + ": Input conveyor set to conveyor number C" + this.inconveyor.getConveyorNum() + ".");
		System.out.println("Routing Station " + this.getStationNum() + ": Output conveyor set to conveyor number C" + this.outconveyor.getConveyorNum()+ ".");
		// if workload is greater than 0
		while(this.getWorkload() > 0)
		{
			System.out.println("Routing Station " + this.getStationNum() + ": Workload set. Station " + this.getStationNum() + " has a total of " + this.getWorkload() + " package groups to move.");
			System.out.println("\nRouting Station " + this.getStationNum() +": Now Online\n");
			System.out.println("Routing Station " + this.getStationNum() + ": Entering Lock Acquisition Phase.");
			// if input lock is available
			if(this.inconveyor.getLock())
			{
				System.out.println("Routing Station " + this.getStationNum() + ": holds lock on input conveyor C" + inconveyor.getConveyorNum()+ "."); 

				// if input and output lock is available
				if(this.outconveyor.getLock())
				{
					System.out.println("Routing Station " + this.getStationNum() + ": holds lock on output conveyor C" + outconveyor.getConveyorNum()+ ".");
					doWork();
					
					// unlock after working
					System.out.println("Routing Station " + this.getStationNum() + ": Entering Lock Release Phase.");
					this.inconveyor.unlockConveyor();
					System.out.println("Routing Station " + this.getStationNum() + ": unlocks/releases input conveyor C" + inconveyor.getConveyorNum()+ ".");
					this.outconveyor.unlockConveyor();
					System.out.println("Routing Station " + this.getStationNum() + ": unlocks/releases output conveyor C" + outconveyor.getConveyorNum()+ ".");

				}
				//unlock the input conveyor if the output conveyor is busy 
				else
				{
					this.inconveyor.unlockConveyor();
					System.out.println("Routing Station " + this.getStationNum() + ": unable to lock output conveyor C" + outconveyor.getConveyorNum() + " , unlocks input " + inconveyor.getConveyorNum()+ ".");

					goTosleep(); 
				}
			}
		}
    }
}
	