/* 
  Name: Jennifer Nguyen
  Course: CNT 4714 Spring 2022 
  Assignment title: Project 2 – Multi-threaded programming in Java 
  Date:  February 16, 2022 
 
  Class: CNT 4714
*/ 

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Conveyor 
{
	   // the attributes of a conveyor
	   int conveyorNum; 
	   private Lock thelock = new ReentrantLock();                                                                         
	   
	   public Conveyor(int conveyorNum){
	       this.conveyorNum = conveyorNum;
	       this.thelock = new ReentrantLock();
	   }
	   
	   public int getConveyorNum() 
		{
			return conveyorNum;
		}

		public void setConveyorNum(int conveyorNum) 
		{
			this.conveyorNum = conveyorNum;
		}
	   
	   //method for routing stations to acquire a conveyor lock
	   public boolean getLock(){
		   return this.thelock.tryLock();
	   }
	   
	   public void unlockConveyor(){
		   this.thelock.unlock();
	   }
} 