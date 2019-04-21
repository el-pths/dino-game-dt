package chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortException;
import jssc.SerialPortList;

import dinosaur.Filter;

public class Chart extends JPanel implements ActionListener {

	private static Scanner sc;
	private static final long serialVersionUID = 42L;
	private static JFrame frame;
	private Timer timer = new Timer(1000 / 50, this);
	private static int width = 500, height = 400;
	private static BufferedImage screen = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

	public static void main(String... args) {
		try {
			if (args.length > 1) {
    			sc = new Scanner(new File(args[1]));
			} else {
    			sc = new Scanner(Chart.class.getResourceAsStream("/jumps.txt"));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		App.main();
		setFrame();
	}

	public static void setFrame() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		frame.setContentPane(new Chart());
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				sc.close();
				if (App.any)
					App.closePort();
				System.exit(0);
			}
		});
	}

	public Chart() {
		timer.start();
		setFocusable(true);
	}
    
    void drawSignal(Graphics g, int[] data, int y0) {
		int prev = 0;
		for (int i = 0; i < data.length; i++) {
		    g.drawLine(i, y0 - prev, i + 1, y0 - data[i]);
		    prev = data[i];
		}
    }
    
	@Override
	public void paint(Graphics g1) {
		Graphics g = screen.getGraphics();
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, screen.getWidth(), screen.getHeight());
		((Graphics2D) g).setStroke(new BasicStroke(3));
		g.setColor(Color.GREEN);
		drawSignal(g, Data.rms, 9 * height / 11);
		g.setColor(Color.GRAY);
		drawSignal(g, Data.jump, height - 5);
		g.setColor(Color.YELLOW);
		drawSignal(g, Data.dX, 2 * height / 11);
		g.setColor(Color.BLUE);
		drawSignal(g, Data.dY, 4 * height / 11);
		g.setColor(Color.RED);
		drawSignal(g, Data.dZ, 6 * height / 11);
		g1.drawImage(screen, 0, 0, this.getWidth(), this.getHeight(), null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (sc.hasNext()) {
			if (!App.any) {
				Data.filter.newPoint(sc.nextInt(), sc.nextInt(), sc.nextInt());
				Data.nextPoint();
			}
			repaint();
		}
	}

}

class Data {
    
    public static Filter filter = new Filter();

	public static int[] dX = new int[300], dY = new int[300], dZ = new int[300];
	public static int[] rms = new int[300], jump = new int[300];

	public static void record(int newX, int newY, int newZ, int r, int j) {
		for (int i = 0; i < dX.length - 1; i++)
			dX[i] = dX[i + 1];
		dX[dX.length - 1] = newX;
		for (int i = 0; i < dY.length - 1; i++)
			dY[i] = dY[i + 1];
		dY[dY.length - 1] = newY;
		for (int i = 0; i < dZ.length - 1; i++)
			dZ[i] = dZ[i + 1];
		dZ[dZ.length - 1] = newZ;
		for (int i = 0; i < rms.length - 1; i++)
			rms[i] = rms[i + 1];
		rms[rms.length - 1] = r;
		for (int i = 0; i < jump.length - 1; i++)
			jump[i] = jump[i + 1];
		jump[jump.length - 1] = j;
		System.out.println(j);
	}
	
	static void nextPoint() {
		int x = filter.getX() * 50 / filter.getG();
		int y = filter.getY() * 50 / filter.getG();
		int z = filter.getZ() * 50 / filter.getG();
		Data.record(x, y, z, filter.getRMS(), filter.jumpDetected());
	}

}

class App {

	private static final int BAUD_RATE = Integer.parseInt(System.getProperty("baud", "115200"));
	
	private Scanner input;
	private SerialPort port;
	private StringBuffer incomingLine = new StringBuffer();
	private static App app = new App();
	public static boolean any = true;

	public static void main(String... args) {
		try {
			app.init();
		} catch (Exception e) {
			System.out.println("Error during initialization: " + e.getMessage());
			return;
		}

	}
	
	private void init() {
		input = new Scanner(System.in);
		String portName = choosePort();
		if (portName == "None") {
			closePort();
			any = false;
		} else {
			port = openPort(portName);
			setClosePortHook();
			addReceiveHandler();
		}
	}

	public static void closePort() {
		try {
			if (app.port != null && app.port.isOpened()) {
				System.out.println("Closing port...");
				app.port.closePort();
			}
		} catch (SerialPortException e) {
			throw new RuntimeException(e);
		}
	}

	private String choosePort() {
		String[] ports = SerialPortList.getPortNames();
		if (ports.length < 1) {
			throw new RuntimeException("No serial ports found at all");
		}
		for (int i = 0; i < ports.length; i++) {
			System.out.printf("%d) %s%n", i + 1, ports[i]);
		}
		System.out.println((ports.length + 1) + ") File");
		int choice = input.nextInt();
		if (choice == ports.length + 1) {
			return "None";
		} else {
			if (choice < 1 || choice > ports.length) {
				throw new RuntimeException("Choose better next time");
			}
			return ports[choice - 1];
		}
	}

	private SerialPort openPort(String name) {
		SerialPort port = new SerialPort(name);
		try {
			if (!port.openPort()) {
				throw new RuntimeException("Port opening returns 'false'");
			}
			if (!port.setParams(BAUD_RATE, 8, 1, 0)) {
				throw new RuntimeException("Setting port params returns 'false'");
			}
		} catch (SerialPortException e) {
			throw new RuntimeException("Oh, what a boolsheet: " + e);
		}
		return port;
	}

	private void addReceiveHandler() {
		try {
			port.addEventListener(event -> {
				try {
					if (event.getEventType() != SerialPortEvent.RXCHAR) {
						return;
					}
					receiveBytes(port.readBytes());
				} catch (SerialPortException e) {
					throw new RuntimeException("Exception on receiving: " + e);
				}
			});
		} catch (SerialPortException e) {
			throw new RuntimeException("Oh no, so much boolsheet: " + e);
		}
	}

	private void setClosePortHook() {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			closePort();
		}));
	}

	private void receiveBytes(byte[] b) {
		if (b != null) {
			String s = new String(b);
			incomingLine.append(s);
			int lineEndPos = incomingLine.indexOf("\n");
			if (lineEndPos >= 0) {
				String str = incomingLine.substring(0, lineEndPos);
				Scanner line = new Scanner(str);
				try {
    				Data.filter.newPoint(line.nextInt(), line.nextInt(), line.nextInt());
	    			Data.nextPoint();
	    		} catch (Exception e) {
	    		    // broken line, do nothing about it
	    		}
    			incomingLine.delete(0, lineEndPos + 1);
			}
		}
	}
}
