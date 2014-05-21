import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

public class generatestorageData {

	FileWriter fileWriter;
	File storageValues = new File("storage_utlization_input_stream.txt");
	BufferedWriter bufferedWriter;
	int min=1;
	static int max=10;
	int storageValue;
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

		no_of_vms = Integer.parseInt(vmNumber.trim());
		
		try 
		{
			fileWriter = new FileWriter(storageValues);
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
					storageValue = min + (int)(Math.random()*((max-min) + 1));
					Integer storage_value =new Integer(storageValue);
					bufferedWriter.write(storage_value.toString());
					System.out.print(storage_value.toString());

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
