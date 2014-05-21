import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleInsets;

/**

	*/
public class DynamicGraph extends JPanel {
	// /** Time series for first fit. */
	// private TimeSeries firstfit;
	/** Time series for custom fit. */
	private TimeSeries customfit;
	private TimeSeries desktopcloudfit;
	private TimeSeries videostreamingfit;
	private TimeSeries livefeedanalysisfit;
	private TimeSeries historicaldataanalysisfit;
	private TimeSeries withoutheuristicsanalysisfit;

	static File savedcpuValues = new File("cpu_utlization_input_stream.txt");
	static File savednetworkValues = new File(
			"network_utlization_input_stream.txt");
	static File savedramValues = new File("ram_utlization_input_stream.txt");
	static File savedstorageValues = new File(
			"storage_utlization_input_stream.txt");

	public static float cpuWeight = (float) 0.25;
	public static float networkWeight = (float) 0.25;
	public static float storageWeight = (float) 0.25;
	public static float ramWeight = (float) 0.25;

	BufferedReader cpubufferedreader;
	BufferedReader networkbufferedreader;
	BufferedReader rambufferedreader;
	BufferedReader storagebufferedreader;

	static boolean workloaddescribed = false;

	{
		try {
			cpubufferedreader = new BufferedReader(new FileReader(
					savedcpuValues));
			networkbufferedreader = new BufferedReader(new FileReader(
					savednetworkValues));
			rambufferedreader = new BufferedReader(new FileReader(
					savedramValues));
			storagebufferedreader = new BufferedReader(new FileReader(
					savedstorageValues));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates a new application.
	 * 
	 * @param maxAge
	 *            the maximum age (in milliseconds).
	 */
	public DynamicGraph(int maxAge) {
		super(new BorderLayout());
		// create series that automatically discard data more than 30
		// seconds old...

		this.customfit = new TimeSeries("User-Defined Weights",
				Millisecond.class);
		this.customfit.setMaximumItemAge(maxAge);

		this.desktopcloudfit = new TimeSeries("Desktop Cloud",
				Millisecond.class);
		this.desktopcloudfit.setMaximumItemAge(maxAge);

		this.videostreamingfit = new TimeSeries("Video Streaming",
				Millisecond.class);
		this.videostreamingfit.setMaximumItemAge(maxAge);

		this.livefeedanalysisfit = new TimeSeries("Live Feed Analysis",
				Millisecond.class);
		this.livefeedanalysisfit.setMaximumItemAge(maxAge);

		this.historicaldataanalysisfit = new TimeSeries(
				"Historical Data Analysis", Millisecond.class);
		this.historicaldataanalysisfit.setMaximumItemAge(maxAge);

		this.withoutheuristicsanalysisfit = new TimeSeries("Without weights",
				Millisecond.class);
		this.withoutheuristicsanalysisfit.setMaximumItemAge(maxAge);

		TimeSeriesCollection dataset = new TimeSeriesCollection();

		dataset.addSeries(this.customfit);
		dataset.addSeries(this.desktopcloudfit);
		dataset.addSeries(this.videostreamingfit);
		dataset.addSeries(this.livefeedanalysisfit);
		dataset.addSeries(this.historicaldataanalysisfit);
		dataset.addSeries(this.withoutheuristicsanalysisfit);

		DateAxis domain = new DateAxis("Time");
		NumberAxis range = new NumberAxis("Physical Servers used");
		domain.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 12));
		range.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 12));
		domain.setLabelFont(new Font("SansSerif", Font.PLAIN, 14));
		range.setLabelFont(new Font("SansSerif", Font.PLAIN, 14));
		XYItemRenderer renderer = new XYLineAndShapeRenderer(true, false);

		renderer.setSeriesPaint(0, Color.red);
		renderer.setSeriesPaint(1, Color.green);
		renderer.setSeriesPaint(2, Color.yellow);
		renderer.setSeriesPaint(3, Color.blue);
		renderer.setSeriesPaint(4, Color.black);
		renderer.setSeriesPaint(5, Color.darkGray);

		renderer.setStroke(new BasicStroke(3f, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_BEVEL));
		XYPlot plot = new XYPlot(dataset, domain, range, renderer);
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		domain.setAutoRange(true);
		domain.setLowerMargin(0.0);
		domain.setUpperMargin(0.0);
		domain.setTickLabelsVisible(true);
		range.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		JFreeChart chart = new JFreeChart(
				"Simulation of Different Workload Placement Strategies for Time Variant Workload",
				new Font("SansSerif", Font.BOLD, 24), plot, true);
		chart.setBackgroundPaint(Color.white);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(4, 4, 4, 4),
				BorderFactory.createLineBorder(Color.black)));
		add(chartPanel);
	}

	/**
	 * Adds an observation to the ’first fit’ time series.
	 * 
	 * @param y
	 *            the first fit.
	 */
	// private void addFirstFitObservation(double y)
	// {
	// this.firstfit.add(new Millisecond(), y);
	// }
	/**
	 * Adds an observation to the ’best fit’ time series.
	 * 
	 * @param y
	 *            the best fit.
	 */
	private void addCustomFitObservation(double y) {
		this.customfit.add(new Millisecond(), y);
	}

	private void addDesktopCloudObservation(double y) {
		this.desktopcloudfit.add(new Millisecond(), y);
	}

	private void addVideoStreamingObservation(double y) {
		this.videostreamingfit.add(new Millisecond(), y);
	}

	private void addLiveFeedAnalysisObservation(double y) {
		this.livefeedanalysisfit.add(new Millisecond(), y);
	}

	private void addHistoricalDataAnalysisObservation(double y) {
		this.historicaldataanalysisfit.add(new Millisecond(), y);
	}

	private void addwithoutHeuristicsAnalysisObservation(double y) {
		this.withoutheuristicsanalysisfit.add(new Millisecond(), y);
	}

	/**
	 * The data generator.
	 */

	class DataGenerator extends Timer implements ActionListener {
		/**
		 * Constructor.
		 * 
		 * @param interval
		 *            the interval (in milliseconds)
		 */
		DataGenerator(int interval) {
			super(interval, null);
			addActionListener(this);
		}

		/**
		 * Adds a new reading to the dataset.
		 * 
		 * @param event
		 *            the action event.
		 * 
		 */
		public void actionPerformed(ActionEvent event) {

			try {
				ArrayList<Float[]> vmlist = new ArrayList<Float[]>();

				// each entry in vmlist contains a Float array in the format
				// {cpu,network,ram,storage}

				String cpustr = cpubufferedreader.readLine();
				String networkstr = networkbufferedreader.readLine();
				String ramstr = rambufferedreader.readLine();
				String storagestr = storagebufferedreader.readLine();

				if (cpustr != null) // outer loop to read file line-by-line.
				{

					vmlist = StartClass.getvmValues(cpustr, networkstr, ramstr,
							storagestr); // Get values of VM's in form of a
											// float[] ArrayList one line at a
											// time.
					// System.out.println("\nList of VM's:" + vmlist + "\n");
				}

				// create an arraylist of weights
				Float[] weights = new Float[] { DynamicGraph.cpuWeight,
						DynamicGraph.networkWeight, DynamicGraph.ramWeight,
						DynamicGraph.storageWeight };

				/* Custom Weighted Fit */
				// Optimize as per user's choice of weights
				ArrayList<PhysicalServer> weightedfitserverlist = StartClass
						.fitvmlist(vmlist, weights); // pass vmlist and weights

				// Output//

				int j = 0;
				System.out.println("\nCustom Weights:\n");
				for (PhysicalServer temp : weightedfitserverlist) {
					System.out.println("\nServer " + j + ":"
							+ temp.placedvmlist);
					System.out.println("Server Utilization in % :");
					System.out.println("CPU:" + temp.cpufilled + "\tNetwork:"
							+ temp.networkfilled + "\tMemory:" + temp.ramfilled
							+ "\tStorage:" + temp.storagefilled);
					j++;
				}
				System.out.println("\nCustom Weights:\n");
				System.out.println("\nServers Used for this workload:"
						+ weightedfitserverlist.size() + "\n\n");

				/* Desktop Cloud */
				// Optimize CPU, Network and RAM
				Float[] desktopcloudweights = new Float[] { (float) 0.6,
						(float) 0.1, (float) 0.3, (float) 0.0 };
				ArrayList<PhysicalServer> desktopcloudserverlist = StartClass
						.fitvmlist(vmlist, desktopcloudweights); // pass vmlist
																	// and
																	// weights

				// Output//

				j = 0;
				System.out.println("\nDesktop Cloud:\n");
				for (PhysicalServer temp : desktopcloudserverlist) {
					System.out.println("\nServer " + j + ":"
							+ temp.placedvmlist);
					System.out.println("Server Utilization in % :");
					System.out.println("CPU:" + temp.cpufilled + "\tNetwork:"
							+ temp.networkfilled + "\tMemory:" + temp.ramfilled
							+ "\tStorage:" + temp.storagefilled);
					j++;
				}
				System.out.println("\nDesktop Cloud:\n");
				System.out.println("\nServers Used for this workload:"
						+ desktopcloudserverlist.size() + "\n\n");

				/* Video Streaming */
				// Optimize Network, Storage
				Float[] videostreamingweights = new Float[] { (float) 0.0,
						(float) 0.7, (float) 0.0, (float) 0.3 };
				ArrayList<PhysicalServer> videostreamingserverlist = StartClass
						.fitvmlist(vmlist, videostreamingweights); // pass
																	// vmlist
																	// and
																	// weights

				// Output//

				j = 0;
				System.out.println("\nVideo Streaming:\n");
				for (PhysicalServer temp : videostreamingserverlist) {
					System.out.println("\nServer " + j + ":"
							+ temp.placedvmlist);
					System.out.println("Server Utilization in % :");
					System.out.println("CPU:" + temp.cpufilled + "\tNetwork:"
							+ temp.networkfilled + "\tMemory:" + temp.ramfilled
							+ "\tStorage:" + temp.storagefilled);
					j++;
				}
				System.out.println("\nVideo Streaming:\n");
				System.out.println("\nServers Used for this workload:"
						+ videostreamingserverlist.size() + "\n\n");

				/* Live Feed Analysis */
				// Optimize RAM, Network, CPU
				Float[] livefeedanalysisweights = new Float[] { (float) 0.2,
						(float) 0.2, (float) 0.6, (float) 0.0 };
				ArrayList<PhysicalServer> livefeedanalysisserverlist = StartClass
						.fitvmlist(vmlist, livefeedanalysisweights); // pass
																		// vmlist
																		// and
																		// weights

				// Output//

				j = 0;
				System.out.println("\nLive Feed Analysis:\n");
				for (PhysicalServer temp : livefeedanalysisserverlist) {
					System.out.println("\nServer " + j + ":"
							+ temp.placedvmlist);
					System.out.println("Server Utilization in % :");
					System.out.println("CPU:" + temp.cpufilled + "\tNetwork:"
							+ temp.networkfilled + "\tMemory:" + temp.ramfilled
							+ "\tStorage:" + temp.storagefilled);
					j++;
				}
				System.out.println("\nLive Feed Analysis:\n");
				System.out.println("\nServers Used for this workload:"
						+ livefeedanalysisserverlist.size() + "\n\n");

				// Output//

				/* Historical Data Analysis */
				// Optimize Storage, CPU
				Float[] historicaldataanalysisweights = new Float[] {
						(float) 0.3, (float) 0.0, (float) 0.0, (float) 0.7 };
				ArrayList<PhysicalServer> historicaldataanalysisserverlist = StartClass
						.fitvmlist(vmlist, historicaldataanalysisweights); // pass
																			// vmlist
																			// and
																			// weights

				// Output//

				j = 0;
				System.out.println("\nHistorical Data Analysis:\n");
				for (PhysicalServer temp : historicaldataanalysisserverlist) {
					System.out.println("\nServer " + j + ":"
							+ temp.placedvmlist);
					System.out.println("Server Utilization in % :");
					System.out.println("CPU:" + temp.cpufilled + "\tNetwork:"
							+ temp.networkfilled + "\tMemory:" + temp.ramfilled
							+ "\tStorage:" + temp.storagefilled);
					j++;
				}
				System.out.println("\nHistorical Data Analysis:\n");
				System.out.println("\nServers Used for this workload:"
						+ historicaldataanalysisserverlist.size() + "\n\n");

				/* Without Heuristics */
				// Optimize all resources equally
				Float[] withoutheuristicsweights = new Float[] { (float) 0.0,
						(float) 0.0, (float) 0.0, (float) 0.0 };
				ArrayList<PhysicalServer> withoutheuristicsserverlist = StartClass
						.fitvmlist(vmlist, withoutheuristicsweights); // pass
																		// vmlist
																		// and
																		// weights

				// Output//

				j = 0;
				System.out.println("\nWithout Heuristics:\n");
				for (PhysicalServer temp : withoutheuristicsserverlist) {
					System.out.println("\nServer " + j + ":"
							+ temp.placedvmlist);
					System.out.println("Server Utilization in % :");
					System.out.println("CPU:" + temp.cpufilled + "\tNetwork:"
							+ temp.networkfilled + "\tMemory:" + temp.ramfilled
							+ "\tStorage:" + temp.storagefilled);
					j++;
				}
				System.out.println("\nwithoutheuristics:\n");
				System.out.println("\nServers Used for this workload:"
						+ desktopcloudserverlist.size() + "\n\n");

				Thread.sleep(1000);

				long cwf = weightedfitserverlist.size();
				long dcf = desktopcloudserverlist.size();
				long vsf = videostreamingserverlist.size();
				long lfaf = livefeedanalysisserverlist.size();
				long hdaf = historicaldataanalysisserverlist.size();
				long whf = withoutheuristicsserverlist.size();

				addCustomFitObservation(cwf);
				addDesktopCloudObservation(dcf);
				addVideoStreamingObservation(vsf);
				addLiveFeedAnalysisObservation(lfaf);
				addHistoricalDataAnalysisObservation(hdaf);
				addwithoutHeuristicsAnalysisObservation(whf);
			}

			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param args
	 *            ignored.
	 */
	public static void main(String[] args) {

		Object[] options = { "Yes", "No" };
		int value = JOptionPane.showOptionDialog(null,
				"Do you want to create a new input stream.\n", "Input Stream",
				JOptionPane.DEFAULT_OPTION, JOptionPane.YES_NO_OPTION, null,
				options, options[0]);

		if (value == JOptionPane.YES_OPTION) {
			String vmNumber = JOptionPane.showInputDialog(null,
					"Enter Number of Virtual Machines: ", null, 1);

			try {

				if (vmNumber.equals(""))
					throw new Exception("Enter no vms!");
			} catch (Exception e1) {
				System.out.println("No. of vms cannot be left blank!!");
				System.exit(0);
			}

			/* Code to check intensity of workload */

			new DialogBox();
			int i = 0;
			while (workloaddescribed == false) {
				//System.out.println("in while : " + i);
				i++;
			}

			System.out.println("Generating VM CPU data...");
			generatecpuData cpudata = new generatecpuData();
			cpudata.generate(vmNumber);

			System.out.println("Generating VM Network data...");
			generatenetworkData networkdata = new generatenetworkData();
			networkdata.generate(vmNumber);

			System.out.println("Generating VM RAM data...");
			generateramData ramdata = new generateramData();
			ramdata.generate(vmNumber);

			System.out.println("Generating VM Storage data...");
			generatestorageData storagedata = new generatestorageData();
			storagedata.generate(vmNumber);

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}

		JPanel weightspanel = new JPanel();
		weightspanel.add(new JLabel(
				"Enter the weightage of CPU Utilization (0-1): "));
		JTextField cpuweightfield = new JTextField(10);
		weightspanel.add(cpuweightfield);
		weightspanel.add(new JLabel(
				"Enter the weightage of network bandwidth (0-1): "));
		JTextField networkweightfield = new JTextField(10);
		weightspanel.add(networkweightfield);
		weightspanel.add(new JLabel("Enter the weightage of RAM (0-1): "));
		JTextField memoryweightfield = new JTextField(10);
		weightspanel.add(memoryweightfield);
		weightspanel
				.add(new JLabel("Enter the weightage of disk space (0-1): "));
		JTextField storageweightfield = new JTextField(10);
		weightspanel.add(storageweightfield);

		weightspanel.setLayout(new BoxLayout(weightspanel, BoxLayout.Y_AXIS));
		int valueEntered = JOptionPane.showConfirmDialog(null, weightspanel,
				"Enter text", JOptionPane.OK_CANCEL_OPTION);

		if (valueEntered == JOptionPane.OK_OPTION) {
			String cpuwt = cpuweightfield.getText();
			String networkwt = networkweightfield.getText();
			String memorywt = memoryweightfield.getText();
			String storagewt = storageweightfield.getText();
			try {
				if (cpuwt.equals("") || networkwt.equals("")
						|| storagewt.equals("") || memorywt.equals("")) {
					throw new Exception("Please enter Inputs!!!");
				}

			} catch (Exception e) {
				System.out.println("All fields are mandatory!");
				System.exit(0);
			}

			DynamicGraph.cpuWeight = Float.parseFloat(cpuwt);
			DynamicGraph.networkWeight = Float.parseFloat(networkwt);
			DynamicGraph.ramWeight = Float.parseFloat(memorywt);
			DynamicGraph.storageWeight = Float.parseFloat(storagewt);

			float sum = DynamicGraph.cpuWeight + DynamicGraph.networkWeight
					+ DynamicGraph.storageWeight + DynamicGraph.ramWeight;

			System.out.println(sum);

			try {
				if (cpuWeight > 1 || cpuWeight < 0 || networkWeight > 1
						|| networkWeight < 0 || storageWeight > 1
						|| storageWeight < 0 || ramWeight < 0 || ramWeight > 1
						|| sum != 1)
					throw new Exception("Invalid weights!");
			} catch (Exception e1) {

				System.out.println("Weights are not valid!!");
				System.exit(0);
			}

		}

		JFrame frame = new JFrame("Dynamic Graph");
		DynamicGraph graphpanel = new DynamicGraph(900000);
		frame.getContentPane().add(graphpanel, BorderLayout.CENTER);
		frame.setBounds(100, 120, 1000, 600);
		frame.setVisible(true);
		graphpanel.new DataGenerator(100).start();

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}
