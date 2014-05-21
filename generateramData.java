import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

public class generateramData {

	FileWriter fileWriter;
	File ramValues = new File("ram_utlization_input_stream.txt");
	BufferedWriter bufferedWriter;
	int min=1;
	static int max=10;
	int ramValue;
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
			fileWriter = new FileWriter(ramValues);
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
					ramValue = min + (int)(Math.random()*((max-min) + 1));
					Integer ram_value =new Integer(ramValue);
					bufferedWriter.write(ram_value.toString());
					System.out.print(ram_value.toString());

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
