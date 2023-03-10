package com.cumdy.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.RootPaneContainer;

public class FancyBox extends JComponent {
	private static final long serialVersionUID = 3L;
	private RootPaneContainer rpc;
	private JComponent panel;
	private final BackPane backpane = new BackPane();

	public FancyBox(RootPaneContainer rpc, JComponent c, boolean boo) {
		this.panel = c;
		this.rpc = rpc;
		this.panel = ShowBusinessForm.getInstance();
		this.rpc.getLayeredPane().setLayer(this, JLayeredPane.PALETTE_LAYER);
		this.rpc.getLayeredPane().add(this);

		MyLayout layout = new MyLayout();
		layout.setAbout(boo);

		setLayout(layout);
		add(panel);
		add(backpane);
		updateBounds();
		setVisible(false);

	}

	private class MyLayout implements LayoutManager {
		private boolean isAbout = true;

		public void setAbout(boolean boo) {
			isAbout = boo;
		}

		public void layoutContainer(Container c) {
			backpane.setBounds(0, 0, getWidth(), getHeight());

			Dimension panelSize = panel.getPreferredSize();
			if (panelSize.width > c.getWidth() - 20)
				panelSize.width = c.getWidth() - 20;
			if (panelSize.height > c.getHeight() - 20)
				panelSize.height = c.getHeight() - 20;
			/*
			 * if(panelSize.width < 400) panelSize.width = 400; if(isAbout){
			 * if(panelSize.height < 200) panelSize.height = 200; } else{
			 * if(panelSize.height < 400) panelSize.height = 400; }
			 */

			panel.setBounds(8, 265, panelSize.width, panelSize.height);
		}

		public Dimension minimumLayoutSize(Container c) {
			return new Dimension(700, 430);
		}

		public Dimension preferredLayoutSize(Container c) {
			return new Dimension(400, 400);
		}

		public void addLayoutComponent(String constraint, Component c) {
		}

		public void removeLayoutComponent(Component c) {
		}

	}

	private class BackPane extends JComponent {
		private static final long serialVersionUID = 31L;

		public BackPane() {
			addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent me) {
					me.consume();
					close();
				}

				public void mousePressed(MouseEvent me) {
					me.consume();
				}

				public void mouseReleased(MouseEvent me) {
					me.consume();
				}

				public void mouseEntered(MouseEvent me) {
					me.consume();
				}

				public void mouseExited(MouseEvent me) {
					me.consume();
				}

				public void mouseMoved(MouseEvent me) {
					me.consume();
				}

				public void mouseDragged(MouseEvent me) {
					me.consume();
				}
			});
		}

		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
					RenderingHints.VALUE_COLOR_RENDER_QUALITY);
			g2.setColor(new Color(0, 0, 0, 60));

			g2.fillRect(0, 0, getWidth(), getHeight());
		}
	}

	public void updateBounds() {
		setBounds(0, 0, rpc.getLayeredPane().getWidth(), rpc.getLayeredPane()
				.getHeight());
	}

	public void close() {
		setVisible(false);
	}

}
