package ui;

import java.io.IOException;

import javax.swing.table.DefaultTableModel;

import util.Configuration;

import bot.script.ScriptData;
import bot.script.ScriptLoader;
import bot.script.ScriptManager;

public class ScriptSelector extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;
	public ScriptLoader sl;
	DefaultTableModel model;

	public ScriptSelector() {
		/*
		 * model = new DefaultTableModel(); sl = new ScriptLoader();
		 * sl.loadScripts(); String[] names = sl.getScriptNames(); for (int i =
		 * 0; i < names.length; i++){
		 * 
		 * ScriptData sd = sl.getScript(names[i]); model.insertRow(i,new
		 * Object[] {sd.name, sd.category, sd.authors[0]});
		 * /*jTable1.setValueAt(sd.name,i,0);
		 * jTable1.setValueAt(sd.category,i,1);
		 * jTable1.setValueAt(sd.authors[0],i,2);
		 * jTextArea1.setText(sd.description); }
		 */

		initComponents();

	}

	public Object[][] getData() {
		if (sl == null) {
			sl = new ScriptLoader();
		}
		sl.loadScripts();
		String[] names = sl.getScriptNames();
		Object[][] data = new Object[names.length][3];
		for (int i = 0; i < names.length; i++) {
			ScriptData sd = sl.getScript(names[i]);
			data[i] = new Object[] { sd.name, sd.category, sd.authors[0] };
		}
		return data;
	}

	private void initComponents() {

		jScrollPane1 = new javax.swing.JScrollPane();
		jTable1 = new javax.swing.JTable();
		jScrollPane2 = new javax.swing.JScrollPane();
		jTextArea1 = new javax.swing.JTextArea();
		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		jTable1.setModel(new javax.swing.table.DefaultTableModel(getData(),
				new String[] { "Name", "Category", "Author" })
        {
            boolean[] canEdit = new boolean[]{
                    false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
		jTable1.setFillsViewportHeight(true);
		jTable1.setFocusable(false);
		jTable1.setRowHeight(20);
		jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jTable1MouseClicked(evt);
			}
		});
		jScrollPane1.setViewportView(jTable1);

		jTextArea1.setColumns(20);
		jTextArea1.setRows(5);
		jTextArea1.setEnabled(false);
		jTextArea1.setPreferredSize(new java.awt.Dimension(174, 94));
		jScrollPane2.setViewportView(jTextArea1);

		jButton1.setText("Start Script");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		jButton2.setText("Cancel");
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton2ActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addComponent(jScrollPane1,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										488,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(jScrollPane2,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										196,
										javax.swing.GroupLayout.PREFERRED_SIZE))
				.addGroup(
						layout.createSequentialGroup()
								.addGap(179, 179, 179)
								.addComponent(jButton1)
								.addGap(132, 132, 132)
								.addComponent(jButton2,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										77,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(215, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.TRAILING)
												.addComponent(
														jScrollPane2,
														javax.swing.GroupLayout.Alignment.LEADING,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														212, Short.MAX_VALUE)
												.addComponent(
														jScrollPane1,
														javax.swing.GroupLayout.Alignment.LEADING,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														212,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										46, Short.MAX_VALUE)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jButton1)
												.addComponent(jButton2))
								.addGap(19, 19, 19)));

		pack();
	}

	private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {
		jTextArea1.setText(sl.getScript(sl.getScriptNames()[jTable1
				.getSelectedRow()]).description);
	}

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
		dispose();
		ScriptManager.getCurrent().setScript(
				sl.getScripts()[jTable1.getSelectedRow()]);
		try {
			MenuStrip.jButton1.setIcon(new javax.swing.ImageIcon(Configuration.getIcon("close.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
		this.dispose();
	}

	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JTable jTable1;
	private javax.swing.JTextArea jTextArea1;
}
