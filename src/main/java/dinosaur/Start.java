package dinosaur;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

public class Start {

	public static String choise = "Without";
	public static boolean choseAnyPort = true;
	private static JFrame firstFrame;
	
	public static void haveToChoosePort() {
		if (Port.ports.length < 1) {
			throw new RuntimeException("No serial ports found at all");
		}
		firstFrame = new JFrame();
		firstFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		firstFrame.setResizable(false);
		firstFrame.setSize(265, 145);
		firstFrame.setLocationRelativeTo(null);
		firstFrame.setFocusable(true);
		firstFrame.setVisible(true);
		firstFrame.setLayout(null);

		String[] extports = new String[Port.ports.length + 1];

		for (int i = 0; i < Port.ports.length; i++)
			extports[i + 1] = Port.ports[i];
		extports[0] = "Without";

		JComboBox<String> comboBox = new JComboBox<String>(extports);
		comboBox.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		comboBox.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent event) {
				choise = (String) ((JComboBox<String>) event.getSource()).getSelectedItem();
			}
		});
		comboBox.setBounds(10, 10, 240, 40);
		firstFrame.add(comboBox);

		JButton button = new JButton("I've chose");
		button.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				button.removeActionListener(this);
				ActionListener[] actl = comboBox.getActionListeners();
				for (int i = 0; i < actl.length; i++)
					comboBox.removeActionListener(actl[i]);
				firstFrame.dispose();
				if (choise == "Without") {
					choseAnyPort = false;
					Port.app.closePort();
				}
				Port.setPort();
			}
		});
		button.setBounds(10, 60, 240, 40);
		button.setVisible(true);
		firstFrame.add(button);
	}

}
