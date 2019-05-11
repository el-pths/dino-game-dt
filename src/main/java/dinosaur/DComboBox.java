package dinosaur;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;

import dinosaur.DButton.buttonPurpose;

public class DComboBox extends JComboBox<String> {

	private static final long serialVersionUID = 42L;

	public static DComboBox portsComboBox;

	public String portName;
	public Font font;
	public int leftIndent, upperIndent, width, height;
	public DButton chooseButton;
	public buttonPurpose purpose;

	private DComboBox(JFrame window, String[] extports, String ifDefault, Font textFont, int leftIndent,
			int upperIndent, int width, int height, buttonPurpose purpose) {
		super(extports);
		this.portName = "Without port";
		this.font = textFont;
		this.leftIndent = leftIndent;
		this.upperIndent = upperIndent;
		this.width = width;
		this.height = height;
		this.purpose = purpose;

		setFocusable(false);
		setFont(textFont);
		setBounds(leftIndent, upperIndent, width, height);
		addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent event) {
				portName = (String) ((JComboBox<String>) event.getSource()).getSelectedItem();
				System.out.println(portName);
			}
		});
	}

	public static void setPortsComboBox(JFrame window, String[] extports, String ifDefault, Font textFont) {
		portsComboBox = new DComboBox(window, extports, ifDefault, textFont, 10, 10, 240, 40,
				buttonPurpose.SELECT_PORT);
	}

}