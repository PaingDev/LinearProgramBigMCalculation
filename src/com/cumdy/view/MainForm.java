package com.cumdy.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.prefs.Preferences;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.bric.awt.text.TextEffect;
import com.cumdy.dbHelper.ConnectionHelper;

public class MainForm extends JFrame {
	Preferences pf = Preferences.userRoot();
	AnimationPanel preview;
	JTabbedPane tab = new JTabbedPane();
	ImageIcon img;
	/*
	 * JLabel lbl = new JLabel(){ public void paintComponent(Graphics g){
	 * super.paintComponent(g); Graphics2D g2d = (Graphics2D)g; if(effect!=null)
	 * effect.paint(g2d, 0.5f); } };
	 */
	JMenu menuFile = new JMenu("File");
	JMenu menu = new JMenu("LookAndFeel");
	JMenuBar menuBar;
	JMenuItem menuItem1 = new JMenuItem("Add new Business");
	JMenuItem menuItem2 = new JMenuItem("Show Business");
	JMenuItem menuItem3 = new JMenuItem("Exit");
	FancyBox fancyBox;
	TextEffect effect = null;
	Timer timer;
	float fraction;

	static String title = "Maximizing The Total Return On Investment Problem Using Big-M Method";

	public MainForm() {
		super(title);
		preview = new AnimationPanel(title);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setSize((int) screen.getWidth(), (int) screen.getHeight());
		setResizable(false);
		Container container = getContentPane();
		Image icon = Toolkit.getDefaultToolkit().getImage(
				MainForm.class.getResource("icon.jpg"));
		setIconImage(icon);

		menuBar = new JMenuBar();

		menuFile.setMnemonic('F');
		addComponentListener(new ComponentAdapter() {

			@Override
			public void componentMoved(ComponentEvent e) {

				// TODO Auto-generated method stub
				// super.componentMoved(e);
				e.getComponent().setLocation(0, 0);
				super.componentMoved(e);
			}

		});

		menuItem1.setIcon(new ImageIcon(MainForm.class
				.getResource("ic_action_new_black.png")));
		menuItem2.setIcon(new ImageIcon(MainForm.class
				.getResource("ic_action_dock_black.png")));
		menuItem3
				.setIcon(new ImageIcon(MainForm.class.getResource("close.png")));

		menuFile.add(menuItem1);
		menuFile.add(new JSeparator());
		menuFile.add(menuItem2);
		menuFile.add(new JSeparator());
		menuFile.add(menuItem3);

		menuItem1.setMnemonic('A');
		menuItem2.setMnemonic('S');
		menuItem3.setMnemonic('x');

		menuBar.add(menuFile);
		menuBar.add(menu);
		final LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();
		JRadioButtonMenuItem[] items = new JRadioButtonMenuItem[infos.length];
		for (int i = 0; i < items.length; i++) {
			if (i == 5)
				menu.addSeparator();

			items[i] = new JRadioButtonMenuItem(infos[i].getName());
			items[i].setActionCommand(Integer.toString(i));
			items[i].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					int index = Integer.parseInt(e.getActionCommand());

					pf.put("ui", infos[index].getClassName());
					if (index < 5)
						pf.putBoolean("sysui", true);
					else
						pf.putBoolean("sysui", false);
					pf.putInt("select", index);

					SwingUtilities.invokeLater(new Runnable() {

						@Override
						public void run() {
							Point location = getLocationOnScreen();

							dispose();
							TitleFrame.loadLookAndFeel();
							new MainForm();

						}

					});

				}

			});
			menu.add(items[i]);

		}
		items[Preferences.userRoot().getInt("select", 2)].setSelected(true);

		setJMenuBar(menuBar);

		addMenuAction();
		addWindowAction();

		/*
		 * lbl.setIcon(new ImageIcon(MainForm.class.getResource("icon.jpg")));
		 * lbl.setOpaque(true); lbl.setBackground(new Color(193,219,254));
		 * lbl.setBorder(BorderFactory.createLineBorder(Color.black));
		 * lbl.setText(
		 * "<html><body><h1 >Maximizing The Total Return On Investment Problem Using Big-M Method</h1></body></html>"
		 * );
		 */
		// lbl.setHorizontalAlignment(SwingConstants.CENTER);

		tab.addTab(
				"Investment Big-M Calculation",
				new ImageIcon(MainForm.class
						.getResource("ic_action_labels.png")), TableInputPanel
						.getInstance());

		tab.addTab(
				"Big-M Equation Solving",
				new ImageIcon(MainForm.class
						.getResource("ic_action_labels.png")),
				new EquationForm());
		tab.setFocusable(false);
		tab.setFont(new Font("Zawgyi-One", Font.PLAIN, 18));

		container.add(tab);
		preview.setPreferredSize(new Dimension(getWidth(), 80));
		container.add(preview, BorderLayout.NORTH);

		setMinimumSize(new Dimension(900, 500));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		Component[] components = menuBar.getComponents();
		for (Component component : components) {

			component.setFont(new Font("Zawgyi-One", Font.PLAIN, 17));
			JMenu menuComps = (JMenu) component;
			for (Component comp : menuComps.getMenuComponents()) {
				comp.setFont(new Font("Zawgyi-One", Font.PLAIN, 14));
			}
		}

		// setVisible(true);
		setVisible(true);
		addTitleAnimation();

	}

	private void addTitleAnimation() {
		preview.doPunch();

	}

	private void addWindowAction() {
		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent arg0) {

				close();

			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	private void addMenuAction() {
		menuItem1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new AddedBusinessForm();
				// preview.repaint();
			}
		});

		menuItem2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						fancyBox = new FancyBox(MainForm.this, null, false);
						fancyBox.setVisible(true);

					}
				});

				// TableInputPanel panel =
				// panel.businessForm = new ShowBusinessForm();
				// panel.businessForm.show();

			}
		});

		menuItem3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				close();
			}
		});

	}

	private void close() {
		try {
			ConnectionHelper.getInstance().closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.exit(0);
	}

}
