package dinosaur;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Things {

	public static JButton removePort = new JButton("");
	public static boolean isButtonRemoved = true;
	public static Image remove = Graphic.loadImage("/delite.png");
	public static JButton replay = new JButton("");
	public static double koefX = 1, koefY = 1;

	public static void setButtonRemovePort() {
		removePort.setBounds(Settings.closePortX, Settings.closePortY, Settings.closePortXX, Settings.closePortYY);
		removePort.setOpaque(false);
		removePort.setContentAreaFilled(false);
		removePort.setBorderPainted(false);
		removePort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Port.app.closePort();
				removePort.removeActionListener(this);
				FrameAndListener.frame.remove(removePort);
				isButtonRemoved = true;
			}
		});
		FrameAndListener.frame.add(removePort);
	}

	public static void setButtonReplay() {
		double koefX = (FrameAndListener.frame.getWidth() / ((double) Settings.START_WIDTH));
		double koefY = (FrameAndListener.frame.getHeight() / ((double) Settings.START_HEIGHT));
		replay.setBounds(
				(int) (koefX
						* (double) (Settings.gameoverLabelX + (Settings.gameoverLabelXX - Settings.restartXX) / 2)),
				(int) (koefY * (double) (Settings.gameoverLabelY + Settings.gameoverLabelYY + Settings.height / 16)),
				(int) (koefX * ((double) Settings.restartXX)), (int) (koefY * (double) Settings.restartYY));
		replay.setOpaque(false);
		replay.setContentAreaFilled(false);
		replay.setBorderPainted(false);
		replay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removePort.removeActionListener(this);
				FrameAndListener.frame.remove(replay);
				Main.startGame();
			}
		});
		FrameAndListener.frame.getContentPane().add(replay);
	}

}
