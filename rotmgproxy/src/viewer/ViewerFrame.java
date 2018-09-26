package viewer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

@SuppressWarnings("serial")
public class ViewerFrame extends JFrame {
	private Viewer v;
	public ViewerFrame(Viewer viewer) {
		this.v = viewer;
		initComponents();
	}

	private void c2s(ActionEvent e) {
		v.server2client = false;
		v.load();
	}

	private void s2c(ActionEvent e) {
		v.server2client = true;
		v.load();
	}

	private void reload(ActionEvent e) {
		v.load();
	}

	private void spoof(ActionEvent e) {
		v.real = false;
		v.load();
	}

	private void real(ActionEvent e) {
		v.real = true;
		v.load();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		scrollPane1 = new JScrollPane();
		table1 = new JTable();
		button1 = new JButton();
		button2 = new JButton();
		button3 = new JButton();
		button4 = new JButton();
		button5 = new JButton();
		button6 = new JButton();

		//======== this ========
		Container contentPane = getContentPane();

		//======== scrollPane1 ========
		{
			scrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

			//---- table1 ----
			table1.setModel(new DefaultTableModel(
				new Object[][] {
					{null, null},
					{null, null},
				},
				new String[] {
					"Packet", "Content"
				}
			));
			scrollPane1.setViewportView(table1);
		}

		//---- button1 ----
		button1.setText("Packets");

		//---- button2 ----
		button2.setText("C2S");
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				c2s(e);
			}
		});

		//---- button3 ----
		button3.setText("S2C");
		button3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				s2c(e);
			}
		});

		//---- button4 ----
		button4.setText("Reload");
		button4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reload(e);
			}
		});

		//---- button5 ----
		button5.setText("Spoof");
		button5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				spoof(e);
			}
		});

		//---- button6 ----
		button6.setText("Real");
		button6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				real(e);
			}
		});

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 689, Short.MAX_VALUE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
						.addComponent(button1)
						.addComponent(button2)
						.addComponent(button3)
						.addComponent(button4)
						.addComponent(button5)
						.addComponent(button6))
					.addContainerGap())
		);
		contentPaneLayout.setVerticalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(contentPaneLayout.createParallelGroup()
						.addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addComponent(button1)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(button2)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(button3)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(button4)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 380, Short.MAX_VALUE)
							.addComponent(button6)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(button5)))
					.addContainerGap())
		);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	public JTable getTable1() {
		return table1;
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JScrollPane scrollPane1;
	private JTable table1;
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JButton button4;
	private JButton button5;
	private JButton button6;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
