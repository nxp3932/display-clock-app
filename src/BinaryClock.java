import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Calendar;

import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 * Implementation of Binary Clock. Instructions to read binary clock are
 * provided with attached document.
 *
 * @author nishad
 * 
 * Code modified from {@linktourl http://freesourcecode.net/javaprojects/74063/sourcecode/DisplayClock.java}
 *
 */
class BinaryClock extends JComponent implements DisplayClock {

	/**
	 * Private class extending JLabel, used to create LED simulators.
	 * 
	 * @author nishad
	 *
	 */
	private class LEDImpl extends JLabel {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		public LEDImpl(int x, int y) {
			setFont(new java.awt.Font("Times New Roman", 1, 24));
			setForeground(foreGroundOff);
			setText("\u2022");
			setBounds(x, y, 10, 16);
		}
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Calendar curTime = Calendar.getInstance();

	private final Color foreGroundOff = Color.DARK_GRAY;

	private final Color foreGroundOn = Color.CYAN;

	private JLabel h11, h12, h13, h14;
	private JLabel h21, h22, h23, h24;
	private JLabel m11, m12, m13, m14;
	private JLabel m21, m22, m23, m24;
	private JLabel s11, s12, s13, s14;
	private JLabel s21, s22, s23, s24;

	public BinaryClock() {
		initComponents();
		setPreferredSize(new Dimension(400, 120));
	}

	private void drawClockFaceValue(Graphics2D g2) {

		int hour = curTime.get(Calendar.HOUR_OF_DAY);
		int min = curTime.get(Calendar.MINUTE);
		int sec = curTime.get(Calendar.SECOND);

		int h1 = hour / 10;
		int h2 = hour % 10;

		int m1 = min / 10;
		int m2 = min % 10;

		int s1 = sec / 10;
		int s2 = sec % 10;

		turnOffEverything();
		JLabel[] labels = new JLabel[] { h11, h12, h13, h14 };
		paintTime(h1, labels);
		labels = new JLabel[] { h21, h22, h23, h24 };
		paintTime(h2, labels);
		labels = new JLabel[] { m11, m12, m13, m14 };
		paintTime(m1, labels);
		labels = new JLabel[] { m21, m22, m23, m24 };
		paintTime(m2, labels);
		labels = new JLabel[] { s11, s12, s13, s14 };
		paintTime(s1, labels);
		labels = new JLabel[] { s21, s22, s23, s24 };
		paintTime(s2, labels);
	}

	private void initComponents() {

		setLayout(null);

		setBackground(new java.awt.Color(0, 0, 0));
		setForeground(foreGroundOff);

		h24 = new LEDImpl(40, 20);
		h14 = new LEDImpl(20, 20);
		h23 = new LEDImpl(40, 40);
		h13 = new LEDImpl(20, 40);
		h12 = new LEDImpl(20, 60);
		h22 = new LEDImpl(40, 60);
		h11 = new LEDImpl(20, 80);
		h21 = new LEDImpl(40, 80);
		m14 = new LEDImpl(70, 20);
		m24 = new LEDImpl(90, 20);
		m13 = new LEDImpl(70, 40);
		m23 = new LEDImpl(90, 40);
		m12 = new LEDImpl(70, 60);
		m22 = new LEDImpl(90, 60);
		m11 = new LEDImpl(70, 80);
		m21 = new LEDImpl(90, 80);
		s14 = new LEDImpl(120, 20);
		s24 = new LEDImpl(140, 20);
		s13 = new LEDImpl(120, 40);
		s23 = new LEDImpl(140, 40);
		s12 = new LEDImpl(120, 60);
		s22 = new LEDImpl(140, 60);
		s11 = new LEDImpl(120, 80);
		s21 = new LEDImpl(140, 80);

		add(h24);
		add(h23);
		add(h12);
		add(h22);
		add(h11);
		add(h21);
		add(m14);
		add(m24);
		add(m13);
		add(m23);
		add(m12);
		add(m22);
		add(m11);
		add(m21);
		add(s14);
		add(s24);
		add(s13);
		add(s23);
		add(s12);
		add(s22);
		add(s11);
		add(s21);

	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		drawClockFaceValue(g2);

	}

	// turns on the required dots that make up num in labels.
	private void paintTime(int num, JLabel[] labels) {
		if (num % 2 == 1)
			this.turnOn(labels[0]);
		if (num == 2 || num == 3 || num == 6 || num == 7)
			this.turnOn(labels[1]);
		if (num >= 4 && num <= 7)
			this.turnOn(labels[2]);
		if ((num == 8 || num == 9))
			this.turnOn(labels[3]);

	}

	@Override
	public void refresh(long time) {
		curTime.setTimeInMillis(time);
		repaint();
	}

	// turns off all the dots
	private void turnOffEverything() {
		h11.setForeground(foreGroundOff);
		h12.setForeground(foreGroundOff);
		h13.setForeground(foreGroundOff);
		h14.setForeground(foreGroundOff);

		h21.setForeground(foreGroundOff);
		h22.setForeground(foreGroundOff);
		h23.setForeground(foreGroundOff);
		h24.setForeground(foreGroundOff);

		m11.setForeground(foreGroundOff);
		m12.setForeground(foreGroundOff);
		m13.setForeground(foreGroundOff);
		m14.setForeground(foreGroundOff);

		m21.setForeground(foreGroundOff);
		m22.setForeground(foreGroundOff);
		m23.setForeground(foreGroundOff);
		m24.setForeground(foreGroundOff);

		s11.setForeground(foreGroundOff);
		s12.setForeground(foreGroundOff);
		s13.setForeground(foreGroundOff);
		s14.setForeground(foreGroundOff);

		s21.setForeground(foreGroundOff);
		s22.setForeground(foreGroundOff);
		s23.setForeground(foreGroundOff);
		s24.setForeground(foreGroundOff);

	}

	// Turn on the dot refrenced by JLabel l
	private void turnOn(JLabel l) {
		l.setForeground(this.foreGroundOn);
	}

}
