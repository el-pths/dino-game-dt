package dinosaur;

import java.util.Scanner;

import jssc.*;

public class Port {

	private static final int BAUD_RATE = Integer.parseInt(System.getProperty("baud", "115200"));

	public static boolean isPortSetted = false;
	private static SerialPort port;
	private StringBuffer incomingLine = new StringBuffer();
	public static Port app = new Port();
	public static String[] ports = SerialPortList.getPortNames();
	private Filter filter = new Filter();
	private static boolean previousTime = false;

	public static void setPort() {
		if (Start.choseAnyPort) {
			try {
				app.init();
			} catch (Exception e) {
				System.out.println("Error during initialization: " + e.getMessage());
				return;
			}
		}
		isPortSetted = true;
		FrameAndListener.setFrame();
	}

	private void init() {
		String portName = Start.choise;
		port = openPort(portName);
		setClosePortHook();
		addReceiveHandler();
	}

	public void closePort() {
		try {
			if (port != null && port.isOpened()) {
				port.closePort();
			}
		} catch (SerialPortException e) {
			throw new RuntimeException(e);
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
					filter.newPoint(line.nextInt(), line.nextInt(), line.nextInt());
					int jump = filter.jumpDetected();
					if (jump > 0) {
						if (!previousTime && !Dino.isNowInAir) {
							Treatment.makeJump(jump);
							Dino.isJump = true;
						}
						previousTime = true;
					} else
						previousTime = false;

				} catch (Exception e) {
					// broken line, do nothing about it
				}
				line.close();
				incomingLine.delete(0, lineEndPos + 1);
			}
		}
	}
}