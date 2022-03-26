/* 
  Name: Jennifer Nguyen
  Course: CNT 4714 Spring 2022 
  Assignment title: Project 2 – Multi-threaded programming in Java 
  Date:  February 16, 2022 
 
  Class: CNT 4714
*/ 

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class ShippingCenter 
{

	static int stationNum, max = 10;
	
	
	public static void main(String args[]) throws FileNotFoundException
	{
		// redirect output
		PrintStream simOut = new PrintStream("simulation-output.txt");
		System.setOut(simOut);
		
		System.out.println("************************* PACKAGE MANAGEMENT FACILITY SIMULATION BEGINS *************************\n\n");
		File config = new File("config.txt");
		Scanner ascan = new Scanner(config);
		ArrayList<RoutingStation> warehouse = new ArrayList<RoutingStation>();
		stationNum = Integer.parseInt(ascan.nextLine());
		
		// Create arraylist of stations
		int i = 0;
		while (ascan.hasNextLine())
		{
			int workload = Integer.parseInt(ascan.nextLine());
			
			int beltNum = i-1;
			if (beltNum < 0)
				beltNum = stationNum-1;
			
			RoutingStation newStation = new RoutingStation(i, workload, new Conveyor(i), new Conveyor(beltNum));
			warehouse.add(newStation);
			i++;
		}
		ascan.close();
		
		// make threads
		ExecutorService executor = Executors.newFixedThreadPool(max);
		for(int j = 0; j < stationNum; j++)
		{
			executor.execute(warehouse.get(j));
		}
		executor.shutdown();
		
		
		try {
			executor.awaitTermination(1,TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (executor.isTerminated())
			System.out.println("\n ****** *** ALL WORKLOADS COMPLETE * ** PACKAGE MANAGEMENT FACILITY SIMULATION ENDS \n");
	}
	
}
