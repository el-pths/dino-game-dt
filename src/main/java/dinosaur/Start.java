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

	public static void haveToChoosePort(JFrame frame) {
		if (Port.ports.length < 1) {
			throw new RuntimeException("No serial ports found at all");
		}

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(265, 145);
		frame.setLocationRelativeTo(null);
		frame.setFocusable(true);
		frame.setVisible(true);
		frame.setLayout(null);

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
		frame.add(comboBox);

		JButton button = new JButton("I've chose");
		button.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		button.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent event) {
				button.removeActionListener(this);
				ActionListener[] actl = comboBox.getActionListeners();
				for (int i = 0; i < actl.length; i++)
					comboBox.removeActionListener(actl[i]);

				frame.hide();
				if (choise == "Without") {
					choseAnyPort = false;
					Port.app.closePort();
				}
				Port.setPort();
			}
		});
		button.setBounds(10, 60, 240, 40);
		frame.add(button);
	}

}
