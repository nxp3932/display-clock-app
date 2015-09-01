import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Date;

import javax.swing.JComponent;

/**
 * Displays Java representation of Date (set to current time) as a string.
 *
 * @author nishad
 *
 */
class DigitalClock extends JComponent implements DisplayClock {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Date curTime;

	public DigitalClock() {
		setPreferredSize(new Dimension(500, 50));
		curTime = new Date();
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g2.drawString(curTime.toString(), 60, 30);
	}

	@Override
	public void refresh(long time) {
		curTime.setTime(time);
		repaint();
	}

}