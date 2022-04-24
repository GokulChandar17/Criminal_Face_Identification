import java.awt.*;
import java.awt.event .*;
import javax.swing.*;
import javax.swing.event.*;
import java.sql.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.swing.border.*;

public class FindFace extends JFrame
{
	Connection cn;
	Statement stmt;
	boolean selectstatus=false;
	int cids[]=new int[6];
	public FindFace()
	{
		initComponents();
		setSize(650,650);
		Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle frame=getBounds();
		this.setLocation((screen.width-frame.width)/2,(screen.height-frame.height)/2);
		setTitle("Find Face & Criminal Details Screen");
		setResizable(false);
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Database d1=new Database();
			cn=DriverManager.getConnection("jdbc:mysql://localhost:3306/criminalrecords","root","@#$5776267gc*");
			stmt=cn.createStatement();
			ResultSet rs=stmt.executeQuery("select *from face_suspectphoto");
			cmbCrimeId.removeAllItems();
			cmbCrimeId.addItem("Select");
			while(rs.next())
			{
				cmbCrimeId.addItem(rs.getString(1));
			}
		}catch(Exception e)
		{
			JOptionPane.showMessageDialog(this, e.getMessage(), "Suspect Details", JOptionPane.ERROR_MESSAGE);
		}
		selectstatus=true;
	}

	private void initComponents()
	{
		lblPhoto = new JLabel();
		cmbCrimeId = new JComboBox();
		lblCrimeId = new JLabel();
		btnAllSuspects = new JButton();
		btnMostSupspect = new JButton();
		btnClose = new JButton();
		jLabel1 = new JLabel();
		getContentPane().setLayout(null);
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				formMousePressed(evt);
			}
		});

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				exitForm(evt);
			}
		});
		getContentPane().add(lblPhoto);
		lblPhoto.setBounds(200, 200, 250, 280);
		cmbCrimeId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				cmbCrimeIdActionPerformed(evt);
			}
		});
		getContentPane().add(cmbCrimeId);
		cmbCrimeId.setBounds(90, 100, 160, 25);
		lblCrimeId.setText("Crime ID");
		getContentPane().add(lblCrimeId);
		lblCrimeId.setBounds(20, 100, 150, 16);
		btnAllSuspects.setText("Show All Suspects");
		btnAllSuspects.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnAllSuspectsActionPerformed(evt);
			}
		});
		getContentPane().add(btnAllSuspects);
		btnAllSuspects.setBounds(20, 550, 200, 30);
		btnMostSupspect.setText("Show Most Suitable Supect");
		btnMostSupspect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnMostSupspectActionPerformed(evt);
			}
		});
		getContentPane().add(btnMostSupspect);
		btnMostSupspect.setBounds(230, 550, 200, 30);
		btnClose.setText("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnCloseActionPerformed(evt);
			}
		});
		getContentPane().add(btnClose);
		btnClose.setBounds(450, 550, 100, 30);
		jLabel1.setFont(new Font("Times New Roman", Font.BOLD/Font.ITALIC, 25));
		jLabel1.setText("Suspected Criminal Faces");
		getContentPane().add(jLabel1);
		jLabel1.setBounds(150, 10, 300, 30);
		pack();
	}
	private void btnCloseActionPerformed(ActionEvent evt)
	{
		setVisible(false);
		dispose();
	}
	private void btnMostSupspectActionPerformed(ActionEvent evt)
	{
		new ShowMaxPossibleSuspect(this, true, cids, false).show();
	}
	private void btnAllSuspectsActionPerformed(ActionEvent evt)
	{
		new ShowMaxPossibleSuspect(this, true, cids, true).show();
	}
	private void cmbCrimeIdActionPerformed(ActionEvent evt)
	{
		if(cmbCrimeId.getSelectedIndex()!=0)
		{
			String crimeid=cmbCrimeId.getSelectedItem().toString();
			try
			{
				ResultSet rs=stmt.executeQuery("select * from face_suspectphoto where crimeid=" +crimeid);
				String photofile="";
				int photoheight=0;
				while (rs.next())
				{
					photofile=rs.getString(2);
					photoheight=Integer.parseInt(rs.getString(3));
				}
				FileInputStream fin=new FileInputStream("" + photofile);
				DataInputStream din=new DataInputStream(fin);
				int len=fin.available();
				int pixels[]=new int[len];
				int i=0;
				while(fin.available()>0)
				{
					pixels[i]=din.readInt();
					i++;
				}
				fin.close();
				din.close();
				Image clipimg=createImage(new MemoryImageSource(200,photoheight,pixels,0,200));
				ImageIcon eyesicn=new ImageIcon(clipimg);
				lblPhoto.setIcon(eyesicn);
				rs.close();
				rs=stmt.executeQuery("select * from face_suspects where crimeid=" + crimeid);
				i=0;
				while(rs.next())
				{
					cids[i]=Integer.parseInt(rs.getString(2));
					i++;
				}
			}catch(Exception e)
			{
				JOptionPane.showMessageDialog(this, e.getMessage(), "Photo Status", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	private void formMousePressed(MouseEvent evt)
	{
	}

private void exitForm(WindowEvent evt)
{
	System.exit(0);
}


public static void main(String args[]) {
new FindFace().show();
}

private JButton btnClose;
private JButton btnMostSupspect;
private JButton btnAllSuspects;
private JLabel lblPhoto;
private JLabel lblCrimeId;
private JComboBox cmbCrimeId;
private JLabel jLabel1;
}