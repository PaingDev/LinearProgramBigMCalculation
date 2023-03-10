package com.cumdy.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;
import java.util.prefs.Preferences;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

import com.cumdy.util.LookAndFeelHolder;
import com.cumdy.util.XMLFileReader;

public class TitleFrame extends JFrame {

	MainForm mainForm;

	public static void loadLookAndFeel() {

		try {
			Preferences pf = Preferences.userRoot();
			List<LookAndFeelHolder> list = XMLFileReader.readXml();

			LookAndFeelInfo[] arr = new LookAndFeelInfo[list.size()];
			for (int j = 0; j < list.size(); j++) {

				arr[j] = new LookAndFeelInfo(list.get(j).getName(), list.get(j)
						.getClassName());

			}
			UIManager.setInstalledLookAndFeels(arr);
			Properties props = new Properties();
			props.put("logoString", "Group-28");
			props.put("licenseKey", "obsolet");
			props.put("linuxStyleScrollBar", "on");
			props.put("centerWindowTitle", "on");

			String className = pf.get("ui",
					"com.jtattoo.plaf.acryl.AcrylLookAndFeel");
			System.out.println(className);
			if (!pf.getBoolean("sysui", false)) {
				Class<?> forName = Class.forName(className);
				try {
					Method method = forName.getDeclaredMethod("setTheme",
							Properties.class);
					method.invoke(null, props);
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// AcrylLookAndFeel.setTheme(props);
				catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			UIManager.setLookAndFeel(className);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	JLabel lblTitle = new JLabel();
	JButton btnNext = new JButton("Next");
	JButton btnClose = new JButton("Close");
	String title = new String(
			"Maximizing The Total Return On Investment Problem Using Big-M Method");

	public TitleFrame() {
		super();
		// loadLookAndFeel();
		setTitle(title);
		ImageIcon ico_forward = new ImageIcon(
				TitleFrame.class.getResource("ic_action_forward.png"));
		ImageIcon ico_close = new ImageIcon(
				TitleFrame.class.getResource("close.png"));
		btnNext.setFocusPainted(false);
		btnClose.setFocusPainted(false);
		btnNext.setIcon(ico_forward);
		btnClose.setIcon(ico_close);
		btnNext.setBackground(Color.black);
		btnNext.setForeground(Color.white);
		btnClose.setBackground(Color.black);
		btnClose.setForeground(Color.white);
		btnNext.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnClose.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setSize(550, 425);
		Container c = getContentPane();
		c.setBackground(new Color(193, 219, 254));
		c.setForeground(Color.white);
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(getWidth(), 50));
		panel.setLayout(null);

		// btnNext.setPreferredSize(new Dimension(200,30));
		// btnClose.setPreferredSize(new Dimension(200,30));
		btnClose.setBounds(getWidth() - 110, 5, 100, 35);
		btnNext.setBounds(getWidth() - 220, 5, 100, 35);
		panel.add(btnNext);
		panel.add(btnClose);
		add(panel, BorderLayout.SOUTH);
		add(lblTitle);

		String title = new String(
				"<html><hr><h1 align= center >Title - Maximizing The Total Return On Investment Problem Using <font color=red>Big-M Method</font></h1><hr>"
						+ "<p><font size=6>&nbsp&nbsp&nbsp&nbsp Group-28</font></p><p><font size=5>&nbsp&nbsp&nbsp&nbsp &nbsp&nbsp&nbsp&nbsp Supervisor : U Thein Htay Zaw <br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"
						+ "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp(System Engineering Department) </font></p><p><font size=6>&nbsp&nbsp&nbsp&nbsp Member List :</font> </p>"
						+ "<table>"
						+ "<tr><td><font size=5>&nbsp&nbsp&nbsp&nbsp(1) Mg Than Zaw Aung</font></td><td><font size=5>4CS-46</font></td></tr>"
						+ "<tr><td><font size=5>&nbsp&nbsp&nbsp&nbsp(2) Mg Paing Pyae Sone</font></td><td><font size=5>4CS-84</font></td></tr>"
						+ "<tr><td><font size=5>&nbsp&nbsp&nbsp&nbsp(3) Ma Lwae Sun Kyu</font></td><td><font size=5>4CS-109</font></td></tr>"
						+ "<tr><td><font size=5>&nbsp&nbsp&nbsp&nbsp(4) Ma Htet Su Shunn Lai</font></td><td><font size=5>4CS-172</font></td></tr>"
						+ "</table><p><hr>" + "</html>");
		lblTitle.setHorizontalAlignment(JLabel.CENTER);
		lblTitle.setText(title);

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(screen.width / 2 - 550 / 2, screen.height / 2 - 425 / 2);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		btnNext.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				animation();

			}
		});
		btnClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				animation();

				System.exit(0);
			}
		});

	}

	public void animation() {
		Thread tr = new Thread(new Runnable() {

			@Override
			public void run() {
				int i = getHeight();
				while (i > 0) {
					setSize(getWidth(), i);
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					i -= 3;
				}
				dispose();
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						mainForm = new MainForm();

					}
				});

			}
		});
		tr.start();

	}

	public static void main(String[] args) {
		loadLookAndFeel();
		TitleFrame titleFrame = new TitleFrame();
		titleFrame.setVisible(true);
	}
}
