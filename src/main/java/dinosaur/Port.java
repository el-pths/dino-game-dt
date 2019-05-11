package dinosaur;

import java.awt.Font;
import java.util.Scanner;

import javax.swing.JFrame;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class Port {

	private static final int BAUD_RATE = Integer.parseInt(System.getProperty("baud", "115200"));

	private static Port app;
	private StringBuffer incomingLine = new StringBuffer();
	private SerialPort port;
	private String portName;

	public static String getSettedPortName() {
		if (app != null)
			return app.portName;
		else
			return "Without port";
	}

	public static void setComboBox(JFrame window) {
		String[] extports = new String[SerialPortList.getPortNames().length + 1];
		for (int i = 0; i < extports.length - 1; i++)
			extports[i + 1] = SerialPortList.getPortNames()[i];
		extports[0] = "Without port";
		DComboBox.setPortsComboBox(window, extports, "Without port", new Font("Comic Sans MS", Font.BOLD, 18));
		window.add(DComboBox.portsComboBox);
	}

	public static void setPort(String portName) {
		app = new Port();
		app.portName = portName;
		if (portName != "Without port")
			try {
				app.connect();
			} catch (Exception e) {
				System.out.println("Error during initialization: " + e.getMessage());
				return;
			}
	}

	private void connect() {
		port = openPort(portName);
		setClosePortHook();
		addReceiveHandler();
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

	private void setClosePortHook() {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			closePort();
		}));
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

	private void receiveBytes(byte[] b) {
		if (b == null) {
			return;
		}
		String s = new String(b);
		incomingLine.append(s);
		while (true) {
			int lineEndPos = incomingLine.indexOf("\n");
			if (lineEndPos < 0) {
				return;
			}
			String str = incomingLine.substring(0, lineEndPos);
			Scanner line = new Scanner(str);
			try {
				System.out.println(line.nextLine());
			} catch (Exception e) {
				// broken line, do nothing about it
			}
			line.close();
			incomingLine.delete(0, lineEndPos + 1);
		}
	}

	public static void closeAllPorts() {
		if (app != null && app.portName != "Without port")
			app.closePort();
	}

}
