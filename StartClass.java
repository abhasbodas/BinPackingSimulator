import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.StringTokenizer;


public class StartClass {

	public static ArrayList<Float[]> getvmValues(String cpustr, String networkstr, String ramstr, String storagestr)		//method to get VM CPU values in an arraylist from a line in the data file
    {
		int instanceNumber = 0;
		ArrayList<Float[]> vmlist = new ArrayList<Float[]>();	
		
		StringTokenizer cpustk = new StringTokenizer(cpustr,",");
		String CPU;
		
		StringTokenizer networkstk = new StringTokenizer(networkstr,",");
		String NETWORK;
		
		StringTokenizer ramstk = new StringTokenizer(ramstr,",");
		String RAM;
		
		StringTokenizer storagestk = new StringTokenizer(storagestr,",");
		String STORAGE;
		
		while(cpustk.hasMoreTokens())		//Inner loop to identify each VM in a given line of the data file
		{
			CPU = cpustk.nextToken();
			NETWORK = networkstk.nextToken();
			RAM = ramstk.nextToken();
			STORAGE = storagestk.nextToken();
			
			
			/*Format in which a VM is represented by a float array has been defined below:
			 * Float[0] is the CPU
			 * Float[1] is the Network
			 * Float[2] is the RAM
			 * Float[3] is the Storage
			 * */
			Float[] instance = new Float[]{new Float(CPU), new Float(NETWORK), new Float(RAM), new Float(STORAGE)};
			
			vmlist.add(instanceNumber, instance );
			
			instanceNumber++;
			
			System.out.println("Instance:"+instanceNumber+"\tValues:" + instance[0] + "\t" + instance[1] + "\t" + instance[2] + "\t" + instance[3]);
		}
		
		return vmlist;
	}

    static ArrayList<PhysicalServer> findWeightedFit(ArrayList<PhysicalServer> physicalservers, int vmnumber, Float[] vm, Float[] weights)
	{
		boolean enoughspace = false;
		
		boolean foundafit = false;
		
		Float highestscore = new Float(-1);
		
		PhysicalServer highestscoringserver = null;
		
		int highestscoringservernumber = -1; 
		
		Iterator<PhysicalServer> phyitr = physicalservers.iterator();
		
		int servernumber = 0;
		
		while(phyitr.hasNext())
		{
			//get the physical server which is the best fit according to given resource weights
			PhysicalServer server = phyitr.next();

			enoughspace = server.checkSpace(vm);
			
			if(enoughspace == true)
			{
				foundafit = true;
				//calculate score of the server
				server.score = getServerScore(server, vm, weights);
				
				System.out.println("Space found in Server Number: " + servernumber + "\tScore:" + server.score);
				
				if( server.score>highestscore )
				{
					highestscoringserver = server;
					
					highestscoringservernumber = servernumber;
					
					highestscore = server.score;
				}
			}
			else
			{
				server.score = Float.NEGATIVE_INFINITY;
			}
			
			servernumber++;
		}
		
		if(foundafit == false)	//if no server can fit the vm, simply allocate a new server, add it to the physicalservers list
		{
			System.out.println("No fit found, allocating a new server.");
			
			PhysicalServer newserver = new PhysicalServer(100, 100, 100, 100);
			
			newserver.addVM(vmnumber, vm);
			
			physicalservers.add(newserver);
		}
		else		//add VM to the highest scoring server
		{
			System.out.println("Best weighted fit has score:" + highestscoringserver.score);
			
			highestscoringserver.addVM(vmnumber, vm);
			physicalservers.add(highestscoringservernumber, highestscoringserver);	//add updated server
			
			physicalservers.remove(highestscoringservernumber+1);	//remove older copy that was shifted one index to the right side
		}
		
		return physicalservers;
	
	}

	public static ArrayList<PhysicalServer> fitvmlist(ArrayList<Float[]> vmlist, Float[] weights)
	{
		ArrayList<PhysicalServer> physicalservers = new ArrayList<PhysicalServer>();
		
		Iterator<Float[]> vmitr = vmlist.iterator();
		
		int vmnumber = 0;
		
		while(vmitr.hasNext())
		{
			Float[] vm = vmitr.next();
			
			System.out.println("\nPlacing VM Number " + vmnumber + ":");
			
			physicalservers = findWeightedFit(physicalservers, vmnumber, vm, weights);
			
			vmnumber++;
		}
		
		return physicalservers;
	}
	
	public static Float getServerScore(PhysicalServer server, Float[] vm, Float[] weights)
	{
		/*
		 * The rule for calculating score:
		 * 		contribution of each resource in score is:
		 * 		(weight of resource) * (space filled on resource after vm is placed on that server)
		 * 
		 * This function is chosen because the better the fit of that resource in a physical server, the more the server's score will be for that resource.
		 * 
		 * i.e. lesser the empty space after placing vm, more the score is
		 * */
		
		/*Float score = weights[0] * ((server.cpucapacity - (server.cpufilled + vm[0])));
		score += weights[1] * ((server.networkcapacity - (server.networkfilled + vm[1])));
		score += weights[2] * ((server.ramcapacity - (server.ramfilled + vm[2])));
		score += weights[3] * ((server.storagecapacity - (server.storagefilled + vm[3])));
	*/	
 		Float score = weights[0] * (((server.cpufilled + vm[0])));
		score += weights[1] * (((server.networkfilled + vm[1])));
		score += weights[2] * (((server.ramfilled + vm[2])));
		score += weights[3] * (((server.storagefilled + vm[3])));
		
		
		return score;
	}
}
