/*Class that represents ONE physical server*/

import java.util.*;

public class PhysicalServer {

	
	ArrayList<Integer> placedvmlist;
	
	float cpufilled=0;
	float cpucapacity=10;
//	float cpuweight=0;
	
	float networkfilled=0;
	float networkcapacity=100;
//	float networkweight=0;
	
	float ramfilled=0;
	float ramcapacity=100;
//	float ramweight=0;
	
	float storagefilled=0;
	float storagecapacity=100;
//	float storageweight=0;
	
	float score = 0;
	
	PhysicalServer()	//Initialize a new server
	{
		placedvmlist = new ArrayList<Integer>();
	}
	
	PhysicalServer(float cpucapacity, float networkcapacity, float ramcapacity, float storagecapacity)	//Initialize a new server
	{
		placedvmlist = new ArrayList<Integer>();

		if(cpucapacity<=100)
		{
			this.cpucapacity=cpucapacity;
		}
		else
		{
			this.cpucapacity=100;
		}
		
		if(networkcapacity<=100)
		{
			this.networkcapacity=networkcapacity;
		}
		else
		{
			this.networkcapacity=100;
		}
		
		if(ramcapacity<=100)
		{
			this.ramcapacity=ramcapacity;
		}
		else
		{
			this.ramcapacity=100;
		}
		
		if(storagecapacity<=100)
		{
			this.storagecapacity=storagecapacity;
		}
		else
		{
			this.storagecapacity=100;
		}
	}
	
	Float[] getFreeSpace()	//returns free space available in this physical server
	{
		return (new Float[]{cpucapacity-cpufilled, networkcapacity-networkfilled, ramcapacity-ramfilled, storagecapacity-storagefilled});
	}
	
	boolean checkSpace(Float vmutilization[])	//Check if a VM can be added to this server, if yes add it and return true, else return false
	{
		boolean canbeused = true;
		
		if(	!(cpucapacity >= cpufilled + vmutilization[0])	)
		{
			canbeused = false;
		}
		
		if(	!(networkcapacity >= networkfilled + vmutilization[1])	)
		{
			canbeused = false;
		}
		
		if(	!(ramcapacity >= ramfilled + vmutilization[2])	)
		{
			canbeused = false;
		}
		
		if(	!(storagecapacity >= storagefilled + vmutilization[3])	)
		{
			canbeused = false;
		}

		return canbeused;
	}
	
	void addVM(int vmnumber, Float[] vm)
	{
		placedvmlist.add(vmnumber);
		
		cpufilled += vm[0];
		
		networkfilled += vm[1];
		
		ramfilled += vm[2];
		
		storagefilled += vm[3];
	}
}
