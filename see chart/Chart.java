package dino;

import java.awt.Color;
import java.awt.Graphics;
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

public class Chart extends JPanel implements ActionListener {

	private static Scanner sc;
	private static final long serialVersionUID = 42L;
	private static JFrame frame;
	private Timer timer = new Timer(1000 / 50, this);
	private static int width = 500, height = 400;
	private static BufferedImage screen = new BufferedImage(width, (height * 7) / 6, BufferedImage.TYPE_INT_RGB);

	public static void main(String... args) {
		try {
			sc = new Scanner(new File("jumps.txt"));
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

	@Override
	public void paint(Graphics g1) {
		Graphics g = screen.getGraphics();
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, screen.getWidth(), screen.getHeight());
		g.setColor(Color.YELLOW);
		for (int i = 0; i < Data.dX.length; i++) {
			if (i > 0) {
				int abs = Math.abs(Data.dX[i] - Data.dX[i - 1]);
				if (abs != 0)
					if (Data.dX[i] < Data.dX[i - 1])
						g.fillRect(i, Data.dX[i] + (int) (2 * height / 12), 1, abs);
					else
						g.fillRect(i, Data.dX[i - 1] + (int) (2 * height / 12), 1, abs);
				else
					g.fillRect(i, Data.dX[i] + (int) (2 * height / 12), 1, 2);
			} else
				g.fillRect(i, Data.dX[i] + (int) (2 * height / 12), 1, 2);
		}
		g.setColor(Color.BLUE);
		for (int i = 0; i < Data.dY.length; i++) {
			if (i > 0) {
				int abs = Math.abs(Data.dY[i] - Data.dY[i - 1]);
				if (abs != 0)
					if (Data.dY[i] < Data.dY[i - 1])
						g.fillRect(i, Data.dY[i] + height / 3 + (int) (2 * height / 12), 1, abs);
					else
						g.fillRect(i, Data.dY[i - 1] + height / 3 + (int) (2 * height / 12), 1, abs);
				else
					g.fillRect(i, Data.dY[i] + height / 3 + (int) (2 * height / 12), 1, 2);
			} else
				g.fillRect(i, Data.dY[i] + height / 3 + (int) (2 * height / 12), 1, 2);
		}
		g.setColor(Color.RED);
		for (int i = 0; i < Data.dZ.length; i++) {
			if (i > 0) {
				int abs = Math.abs(Data.dZ[i] - Data.dZ[i - 1]);
				if (abs != 0)
					if (Data.dZ[i] < Data.dZ[i - 1])
						g.fillRect(i, Data.dZ[i] + 2 * height / 3 + (int) (2 * height / 12), 1, abs);
					else
						g.fillRect(i, Data.dZ[i - 1] + 2 * height / 3 + (int) (2 * height / 12), 1, abs);
				else
					g.fillRect(i, Data.dZ[i] + 2 * height / 3 + (int) (2 * height / 12), 1, 2);
			} else
				g.fillRect(i, Data.dZ[i] + 2 * height / 3 + (int) (2 * height / 12), 1, 2);
		}
		g1.drawImage(screen, 0, 0, frame.getWidth(), frame.getHeight(), null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Data.record(App.x, App.y, App.z);
		if (sc.hasNext()) {
			if (!App.any) {
				App.x = sc.nextInt() / 2;
				App.y = sc.nextInt() / 2;
				App.z = sc.nextInt() / 2;
			}
			repaint();
		}
	}

}

class Data {
	public static int[] dX = new int[300], dY = new int[300], dZ = new int[300];

	public static void record(int newX, int newY, int newZ) {
		for (int i = 0; i < dX.length - 1; i++)
			dX[i] = dX[i + 1];
		dX[dX.length - 1] = newX;
		for (int i = 0; i < dY.length - 1; i++)
			dY[i] = dY[i + 1];
		dY[dY.length - 1] = newY;
		for (int i = 0; i < dZ.length - 1; i++)
			dZ[i] = dZ[i + 1];
		dZ[dZ.length - 1] = newZ;
	}
}

class App {

	public static int x = 0, y = 0, z = 0;
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
			if (!port.setParams(9600, 8, 1, 0)) {
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
			int lineEndPos = incomingLine.indexOf("\r");
			if (lineEndPos >= 0) {
				String str = incomingLine.substring(0, lineEndPos);
				char[] chars = str.toCharArray();
				int[] args = new int[3];
				@SuppressWarnings("unused")
				boolean posfirst = true, possecond = true, posthird = true;
				boolean first = false, second = false;
				for (int i = 0; i < chars.length; i++) {
					if (chars[i] == '0' || chars[i] == '1' || chars[i] == '2' || chars[i] == '3' || chars[i] == '4'
							|| chars[i] == '5' || chars[i] == '6' || chars[i] == '7' || chars[i] == '8'
							|| chars[i] == '9')
						if (!first)
							args[0] = args[0] * 10 + Integer.parseInt(Character.toString(chars[i]));
						else if (!second)
							args[1] = args[1] * 10 + Integer.parseInt(Character.toString(chars[i]));
						else
							args[2] = args[2] * 10 + Integer.parseInt(Character.toString(chars[i]));
					else if (chars[i] == '-')
						if (second)
							posthird = false;
						else if (first)
							possecond = false;
						else
							posfirst = false;
					else if (i > 0)
						if (args[0] != 0 && args[1] == 0 && args[2] == 0)
							first = true;
						else if (args[1] != 0 && args[2] == 0)
							second = true;
						else
							break;
				}
				x = args[0];
				y = args[1];
				z = args[2];
				incomingLine.delete(0, lineEndPos + 1);
			}
		}
	}
}
