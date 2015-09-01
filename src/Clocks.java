import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * Applet that runs the timer and calls the refresh on each individual clock
 * displayed drawn within it.
 *
 * @author nishad
 *
 */
public class Clocks extends JApplet {

	private static List<DisplayClock> clocks;

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private static Timer timer;

	public static void main(String[] args) {
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setTitle("Clocks");
		window.setContentPane(new Clocks());
		window.pack(); // Layout components
		window.setLocationRelativeTo(null); // Center window.
		window.setVisible(true);

	}

	protected static void updateClocks(long currentTimeMillis) {
		for (DisplayClock clock : clocks) {
			clock.refresh(currentTimeMillis);

		}

	}

	public Clocks() {

		setLayout(new BorderLayout());
		clocks = new ArrayList<DisplayClock>();

		AnalogClock analogClock = new AnalogClock();
		add(analogClock, BorderLayout.NORTH);

		DigitalClock dclock = new DigitalClock();
		add(dclock, BorderLayout.SOUTH);

		BinaryClock binaryClock = new BinaryClock();
		add(binaryClock, BorderLayout.CENTER);

		clocks.add(analogClock);
		clocks.add(dclock);
		clocks.add(binaryClock);

		timer = new Timer(500, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateClocks(System.currentTimeMillis());
			}

		});

		timer.start();

	}

}