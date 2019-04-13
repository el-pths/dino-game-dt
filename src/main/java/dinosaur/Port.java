package dinosaur;

import jssc.*;

public class Port {

	public static boolean isPortSetted = false;
	private static SerialPort port;
	private StringBuffer incomingLine = new StringBuffer();
	public static Port app = new Port();
	public static String[] ports = SerialPortList.getPortNames();

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
				//Unused
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
				//if (!posfirst)
					//args[0] *= -1;
				//if (!possecond)
					//args[1] *= -1;
				//if (!posthird)
					//args[2] *= -1;
				System.out.println(str);
				System.out.println("sum : " + (args[0] + args[1] + args[2]));
				if (Math.abs(args[0] + args[1] + args[2]) > 50000 && !Dino.isNowInAir) {
					Dino.jumpHeight = 250;
					Dino.isJump = true;
				}
				incomingLine.delete(0, lineEndPos + 1);
			}
		}
	}
}
