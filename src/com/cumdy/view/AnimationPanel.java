package com.cumdy.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import com.bric.awt.text.ExplodeTextEffect;
import com.bric.awt.text.OutlineTextEffect;
import com.bric.awt.text.PunchTextEffect;
import com.bric.awt.text.TextEffect;
import com.bric.awt.text.WaveTextEffect;

public class AnimationPanel extends JPanel {
	Timer timer;
	TextEffect effect;
	String text;
	float fr;

	public AnimationPanel(String str) {
		this.text = str;

		timer = new Timer(50, new ActionListener() {
			long startTime = -1;

			public void actionPerformed(ActionEvent e) {
				long t = System.currentTimeMillis();
				if (startTime == -1) {
					startTime = t;
					// setEnabled(false);
					return;
				}
				long elapsed = t - startTime;
				long duration;
				if (effect instanceof PunchTextEffect
						|| effect instanceof ExplodeTextEffect) {
					duration = text.length() * 90;
				} else {
					duration = text.length() * 50;
				}
				float fraction = ((float) elapsed) / ((float) duration);
				if (fraction >= 1) {
					startTime = -1;
					// timer.stop();
					// setEnabled(true);
					fraction = 1;
				}
				fr = fraction;
				repaint();
			}
		});

	}

	public void doOutline() {
		Font font = new Font("Impact", 0, 45);

		effect = new OutlineTextEffect(font, text, getWidth(), getHeight());
		timer.start();
	}

	public void doPunch() {
		Font font = new Font("Impact", 0, 45);

		effect = new PunchTextEffect(font, text, getWidth(), getHeight());
		timer.start();
	}

	public void doWave() {
		Font font = new Font("Impact", 0, 45);

		effect = new WaveTextEffect(font, text, getWidth(), getHeight());
		timer.start();
	}

	public void doExplode() {
		Font font = new Font("Impact", 0, 55);

		effect = new ExplodeTextEffect(font, text, getWidth(), getHeight());
		timer.start();
	}

	@Override
	protected void paintComponent(Graphics g0) {
		super.paintComponent(g0);
		Graphics2D g2d = (Graphics2D) g0;
		GradientPaint gp = new GradientPaint(0, 100, Color.black, 0, 0,
				Color.white);
		g2d.setPaint(gp);

		g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

		g2d.setColor(Color.red);
		g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
		if (effect != null) {
			Graphics2D g = (Graphics2D) g0;
			effect.paint(g, fr);
		}
	}

}
