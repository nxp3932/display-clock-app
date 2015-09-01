import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Calendar;

import javax.swing.JComponent;

/**
 * Draws the analog representation of a clock drawing hands (lines) for hours,
 * minutes and seconds representing current time.
 *
 * The orientation of the clock fixed as per traditional hour/minute positions.
 *
 * @author nishad
 *
 */
class AnalogClock extends JComponent implements DisplayClock {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Calendar curTime = Calendar.getInstance();

	public AnalogClock() {
		setPreferredSize(new Dimension(500, 300));
	}

	private void drawClockHand(Graphics2D g2, double percent, int length) {
		double radians = (0.5 - percent) * 2.0 * Math.PI;

		int outerX = 150 + (int) (length * Math.sin(radians));
		int outerY = 150 + (int) (length * Math.cos(radians));
		g2.drawLine(150, 150, outerX, outerY);
	}

	private void drawClockHands(Graphics2D g2) {
		// ... Get the various time elements from the Calendar object.
		int hours = curTime.get(Calendar.HOUR);
		int minutes = curTime.get(Calendar.MINUTE);
		int seconds = curTime.get(Calendar.SECOND);

		// ... second hand
		int length = 120;
		double fseconds = seconds / 60.0;
		drawClockHand(g2, fseconds, length);

		// ... minute hand
		length = 100;
		double fminutes = minutes / 60.0;
		drawClockHand(g2, fminutes, length);

		// ... hour hand
		length = 80;
		drawClockHand(g2, (hours + fminutes) / 12.0, length);
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		drawClockHands(g2);
	}

	@Override
	public void refresh(long time) {
		curTime.setTimeInMillis(time);
		repaint();
	}

}