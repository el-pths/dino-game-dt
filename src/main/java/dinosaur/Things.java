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
	
	public static void setButtonRemovePort() {
		removePort.setBounds(Sets.closePortX, Sets.closePortY, Sets.closePortXX, Sets.closePortYY);
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
		replay.setBounds(Sets.gameoverLabelX + (Sets.gameoverLabelXX - Sets.restartXX) / 2,
				Sets.gameoverLabelY + Sets.gameoverLabelYY + Sets.height / 16, Sets.restartXX, Sets.restartYY);
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
		FrameAndListener.frame.add(replay);
	}
	
}
