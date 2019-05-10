package dinosaur;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class DButton extends JButton {

	private static final long serialVersionUID = 42L;
	public int horizontalIndent, verticalIndent, width, height;
	public Image icon;
	static double horizontalStretch = 1.0, verticalStretch = 1.0;

	public DButton(JFrame window, int horizontalIndent, int verticalIndent, int width, int height, Image icon,
			String purpose) {
		super();
		this.horizontalIndent = horizontalIndent;
		this.verticalIndent = verticalIndent;
		this.width = width;
		this.height = height;
		this.icon = icon;
		setOpaque(false);
		setContentAreaFilled(false);
		setBorderPainted(false);
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeActionListener(this);
				if (purpose == "StartGame") {
					System.out.println("In such of starting game");
				}
			}
		});
		window.add(this);
	}

	public DButton(JFrame window, int width, int height, Image icon, String purpose) {
		this(window, 672 - width / 2, 270 - height / 2, width, height, icon, purpose);
	}

	public void setTouchableLocation(int windowWidth, int windowHeight) {
		calibrateStretchParams(windowWidth, windowHeight);
		setBounds((int) ((double) (this.horizontalIndent) * horizontalStretch),
				(int) ((double) (this.verticalIndent) * verticalStretch),
				(int) ((double) (this.width) * horizontalStretch), (int) ((double) (this.height) * verticalStretch));
	}

	private static void calibrateStretchParams(int windowWidth, int windowHeight) {
		horizontalStretch = windowWidth / 1344.0;
		verticalStretch = windowHeight / 540.0;
	}

	public void draw(Graphics graphics) {
		graphics.drawImage(icon, horizontalIndent, verticalIndent, width, height, null);
	}

}
