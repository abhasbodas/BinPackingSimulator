import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

public class generatenetworkData {

	FileWriter fileWriter;
	File networkValues = new File("network_utlization_input_stream.txt");
	BufferedWriter bufferedWriter;
	int min=1;
	static int max=10;
	int networkValue;
	static int no_of_vms;
	String vmNumber;
	static boolean intense = false;
	
	/*
	 * Function to generate random
	 * data and write it into the file
	 */

	public void generate(String vmNumber)
	{
		this.vmNumber = vmNumber;
		
		try 
		{
			
			if(vmNumber.equals(""))
				throw new Exception("Enter no vms!");
		} 
		catch (Exception e1) 
		{
			System.out.println("No. of vms cannot be left blank!!");
			System.exit(0);
		}
		
		no_of_vms = Integer.parseInt(vmNumber.trim());
		
		try 
		{
			fileWriter = new FileWriter(networkValues);
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
					networkValue = min + (int)(Math.random()*((max-min) + 1));
					Integer network_value =new Integer(networkValue);
					bufferedWriter.write(network_value.toString());
					System.out.print(network_value.toString());

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
