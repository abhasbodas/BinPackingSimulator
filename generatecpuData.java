import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

public class generatecpuData {

	FileWriter fileWriter;
	File cpuValues = new File("cpu_utlization_input_stream.txt");
	BufferedWriter bufferedWriter;
	int min=1;
	static int max=10;
	int cpuValue;
	static int no_of_vms;
	String vmNumber;
	public static boolean intense = false;
	
	/*
	 * Function to generate random
	 * data and write it into the file
	 */

	public void generate(String vmNumber)
	{
		this.vmNumber = vmNumber;
		
		no_of_vms = Integer.parseInt(vmNumber.trim());
		
		try 
		{
			fileWriter = new FileWriter(cpuValues);
			bufferedWriter = new BufferedWriter(fileWriter);
		} 
		catch (IOException e) 
		{
			
			e.printStackTrace();
		}
		
		
		try{
			
		
			for (int i=0; i<1000; i++)
			{
				for(int j=0; j<no_of_vms; j++)
				{
					cpuValue = min + (int)(Math.random()*((max-min) + 1));
					Integer cpu_value =new Integer(cpuValue);
					bufferedWriter.write(cpu_value.toString());
					System.out.print(cpu_value.toString());

					if (j < no_of_vms-1)
						{
						bufferedWriter.write(",");
						System.out.print(", ");
						}
				}
				bufferedWriter.newLine();
				System.out.print("\n");
				bufferedWriter.flush();
			}
			fileWriter.close();
		}
		catch(IOException e)
		{
			System.out.println("Caught Exception: "+e);
		}
	}
}
