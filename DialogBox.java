import java.awt.*; import java.awt.event.*; import javax.swing.*;
public class DialogBox extends JFrame implements ActionListener,ItemListener
{
  JPanel panel=new JPanel();  // create pane content object
  JLabel prompt=new JLabel("Describe Workload:");
  JCheckBox b1=new JCheckBox("CPU Intensive",false);
  JCheckBox b2=new JCheckBox("Network Intensive",false);
  JCheckBox b3=new JCheckBox("RAM Intensive",false);
  JCheckBox b4=new JCheckBox("Storage Intensive",false);
  JButton pressme=new JButton("Continue");
  
 int intensemax = 100;
 int notintensemax = 10;
  
 // int intensemax = 100;
 // int notintensemax = 30;
  
  
  DialogBox()   // the constructor
  {
    super("Workload Description"); setBounds(250,250,400,400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Container con=this.getContentPane(); // inherit main frame
    con.add(panel);
    panel.setLayout(new GridLayout(6,1,10,4)); // reset manager
    panel.add(prompt);
    panel.add(b1);
    panel.add(b2);
    panel.add(b3);
    panel.add(b4);
    panel.add(pressme);
    b1.addItemListener(this); // register checkbox and button events
    b2.addItemListener(this);
    b3.addItemListener(this);
    b4.addItemListener(this);
    pressme.addActionListener(this);
    //pressme.setMnemonic('P');
    setVisible(true);
  }
  // now the basic event listeners
  public void itemStateChanged(ItemEvent ie)
  {
    Object source=ie.getItem();
    if (source==b1)
    {
       int select=ie.getStateChange();
       if (select==ItemEvent.SELECTED)
       {
    	   System.out.println("Workload is CPU Intensive.");
    	   generatecpuData.max = intensemax;
   	   }
       else
       {
           System.out.println("Workload is not CPU Intensive.");
           generatecpuData.max = notintensemax;
       }
    }
    
    if (source==b2)
    {
       int select=ie.getStateChange();
       if (select==ItemEvent.SELECTED)
       {
          System.out.println("Workload is Network Intensive.");
          generatenetworkData.max = intensemax;
   	   }
       else
       {
           System.out.println("Workload is not Network Intensive.");
           generatenetworkData.max = notintensemax;
       }
    }
    
    if (source==b3)
    {
       int select=ie.getStateChange();
       if (select==ItemEvent.SELECTED)
       {
    	   System.out.println("Workload is RAM Intensive.");
    	   generateramData.max = intensemax;
    	}
       else
       {
           System.out.println("Workload is not RAM Intensive.");
           generateramData.max = notintensemax;
       }
    }
    
    if (source==b4)
    {
       int select=ie.getStateChange();
       if (select==ItemEvent.SELECTED)
       {
    	   System.out.println("Workload is Storage Intensive.");
    	   generatestorageData.max = intensemax;
   	   }
       else
       {
           System.out.println("Workload is not Storage Intensive.");
           generatestorageData.max = notintensemax;
       }
    }
  }
  public void actionPerformed(ActionEvent ae)
  {
    Object source=ae.getSource();
    if (source==pressme) // button from above example
    	{
    		DynamicGraph.workloaddescribed = true;
    		this.setVisible(false);
    	}
//    JOptionPane.showMessageDialog(null,"I hear you!","Message Dialog",
//    JOptionPane.PLAIN_MESSAGE);   ;// show something
  }
}