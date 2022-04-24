import java.awt.*;
import java.awt.event .*;
import javax.swing.*;
import javax.swing.event.*;
import java.sql.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.swing.border.*;

public class ShowMaxPossibleSuspect extends JDialog
{
	Vector cidvect=new Vector(2,2);
	Vector cidrepeats=new Vector(2,2);
	Vector finalVect=new Vector(2,2);
	Connection cn;
	Statement stmt;
	int presentsuspect=0;
	public ShowMaxPossibleSuspect(Frame parent, boolean modal, int cids[], boolean all)
	{
		super(parent, modal);
		initComponents();
		setSize(575,650);
		Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle frame=getBounds();
		this.setLocation((screen.width-frame.width)/2,(screen.height-frame.height)/2);
		setResizable(false);
		if(all)
			lblHeading.setText("View All Suspects");
		else
			lblHeading.setText("Most Possible Suspect(s)");
		btnNext.setEnabled(false);
		btnPrevious.setEnabled(false);
		setTitle("Suspects Mostly suitable with the Designed Photo");
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Database d1=new Database();
			cn=DriverManager.getConnection("jdbc:mysql://localhost:3306/databasename","sys","@#$5776267gc*");
			stmt=cn.createStatement();
		}catch(Exception e)
		{
		}
		int max=0;
		int count=0;
		for(int i=0;i<6;i++)
		{
			count=1;
			if(cidvect.contains(new Integer(cids[i]))==false)
			{
				for(int j=i+1;j<6;j++)
				{
					if(cids[i]==cids[j])
						count++;
				}
				cidvect.add(new Integer(cids[i]));
				cidrepeats.add(new Integer(count));
				if(count>max)
					max=count;
			}
		}
		if(all==false)
		{
			finalVect=finalList(max);
		}
		else
		{
			finalVect=cidvect;
		}
		Integer pressuspobj=(Integer)finalVect.elementAt(presentsuspect);
		displayDetails(pressuspobj.intValue());
		if(finalVect.size()==1)
		{
			btnNext.setEnabled(false);
			btnPrevious.setEnabled(false);
		}
		else
		{
			btnNext.setEnabled(true);
		}
	}

	private void initComponents()
	{
		lblCriminalId = new JLabel("Criminal Id");
		jLabel1 = new JLabel("First Name");
		jLabel2 = new JLabel("Last Name");
		jLabel3 = new JLabel("Alias Name");
		jLabel4 = new JLabel("D.O.B");
		jLabel5 = new JLabel("Age");
		jLabel6 = new JLabel("Gender");
		jLabel7 = new JLabel("Address");
		jLabel8 = new JLabel("City");
		jLabel9=new JLabel("State");
		jLabel10=new JLabel("Arrested Date");
		jLabel11=new JLabel("Crime Involved");
		lblPhoto = new JLabel();
		lblPhotoSpace = new JLabel();
		lcr=new JLabel("cr");
		lfname=new JLabel("fn");
		llast=new JLabel("la");
		lalias=new JLabel("al");
		ldob=new JLabel("dop");
		lage=new JLabel("afe");
		lgender=new JLabel("ge");
		laddress=new JLabel("add");
		lcity=new JLabel("ci");
		lstate=new JLabel("st");
		larrdate=new JLabel("ar");
		lcrimein=new JLabel("cri");
		btnClose = new JButton();
		btnPrevious = new JButton();
		btnNext = new JButton();
		lblHeading = new JLabel();

		getContentPane().setLayout(null);
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				formMouseClicked(evt);
			}
		});
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				closeDialog(evt);
			}
		});

		getContentPane().add(lblCriminalId);
		lblCriminalId.setBounds(10, 40, 90, 30);

		getContentPane().add(lcr);
		lcr.setBounds(130, 40, 90, 30);

		getContentPane().add(jLabel1);
		jLabel1.setBounds(10, 80, 90, 30);

		getContentPane().add(lfname);
		lfname.setBounds(130, 80, 90, 30);

		getContentPane().add(jLabel2);
		jLabel2.setBounds(10, 120, 90, 30);

		getContentPane().add(llast);
		llast.setBounds(130, 120, 90, 30);

		getContentPane().add(jLabel3);
		jLabel3.setBounds(10, 160, 90, 30);

		getContentPane().add(lalias);
		lalias.setBounds(130, 160, 90, 30);

		getContentPane().add(jLabel4);
		jLabel4.setBounds(10, 200, 90, 30);

		getContentPane().add(ldob);
		ldob.setBounds(130, 200, 120, 30);

		getContentPane().add(jLabel5);
		jLabel5.setBounds(10, 240, 90, 30);

		getContentPane().add(lage);
		lage.setBounds(130, 240, 120, 30);

		getContentPane().add(jLabel6);
		jLabel6.setBounds(10, 280, 90, 30);

		getContentPane().add(lgender);
		lgender.setBounds(130, 280, 90, 30);

		getContentPane().add(jLabel7);
		jLabel7.setBounds(10, 320, 90, 30);

		getContentPane().add(laddress);
		laddress.setBounds(130, 320, 90, 30);

		getContentPane().add(jLabel8);
		jLabel8.setBounds(10, 360, 90, 30);

		getContentPane().add(lcity);
		lcity.setBounds(130, 360, 90, 30);

		getContentPane().add(jLabel9);
		jLabel9.setBounds(10,400,90,30);

		getContentPane().add(lstate);
		lstate.setBounds(130,400,90,30);

		getContentPane().add(jLabel10);
		jLabel10.setBounds(10,440,90,30);

		getContentPane().add(larrdate);
		larrdate.setBounds(130,440,130,30);

		getContentPane().add(jLabel11);
		jLabel11.setBounds(10,480,90,30);
	
		getContentPane().add(lcrimein);
		lcrimein.setBounds(130,480,90,30);
		lblPhoto.setText("Photo");

		getContentPane().add(lblPhoto);
		lblPhoto.setBounds(410, 40, 33, 16);

		getContentPane().add(lblPhotoSpace);
		lblPhotoSpace.setBounds(310, 60, 240, 260);
		btnClose.setText("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnCloseActionPerformed(evt);
			}
		});
		
		getContentPane().add(btnClose);
		btnClose.setBounds(30, 540, 140, 30);
		btnPrevious.setText("Previous Suspect");
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnPreviousActionPerformed(evt);
			}
		});

		getContentPane().add(btnPrevious);
		btnPrevious.setBounds(220, 540, 140, 30);
		btnNext.setText("Next Suspect");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnNextActionPerformed(evt);
			}
		});

		getContentPane().add(btnNext);
		btnNext.setBounds(400, 540, 140, 30);
		lblHeading.setFont(new Font("Times New Roman", Font.BOLD/Font.ITALIC, 25));
		lblHeading.setText("View All Suspects");

		getContentPane().add(lblHeading);
		lblHeading.setBounds(180, 10, 280, 30);
		pack();
	}
	private void btnCloseActionPerformed(ActionEvent evt)
	{
		setVisible(false);
		dispose();
	}
	private void btnPreviousActionPerformed(ActionEvent evt)
	{
		presentsuspect--;
		Integer pressuspobj=(Integer)finalVect.get(presentsuspect);
		displayDetails(pressuspobj.intValue());
		if(presentsuspect==0)
		{
			btnPrevious.setEnabled(false);
		}
		btnNext.setEnabled(true);
	}
	private void btnNextActionPerformed(ActionEvent evt)
	{
		presentsuspect++;
		Integer pressuspobj=(Integer)finalVect.get(presentsuspect);
		displayDetails(pressuspobj.intValue());
		if(presentsuspect==(finalVect.size()-1))
		{
			btnNext.setEnabled(false);
		}
		btnPrevious.setEnabled(true);
	}
	private void formMouseClicked(MouseEvent evt)
	{
	}
	private void closeDialog(WindowEvent evt)
	{
		setVisible(false);
		dispose();
	}
	public static void main(String args[])
	{
		int cids[]={1,1,2,1,2,1};
		new ShowMaxPossibleSuspect(new JFrame(), true, cids, true).show();
	}
	
	private JButton btnClose;
	private JButton btnPrevious;
	private JButton btnNext;
	private JLabel lblCriminalId;
	private JLabel lblHeading;
	private JLabel lblPhoto;
	private JLabel jLabel11;
	private JLabel jLabel10;
	private JLabel jLabel9;
	private JLabel jLabel8;
	private JLabel jLabel7;
	private JLabel jLabel6;
	private JLabel jLabel5;
	private JLabel lblPhotoSpace;
	private JLabel jLabel4;
	private JLabel jLabel3;
	private JLabel jLabel2;
	private JLabel jLabel1;

	private JLabel lcr;
	private JLabel lfname;
	private JLabel llast;
	private JLabel lalias;
	private JLabel ldob;
	private JLabel lage;
	private JLabel lgender;
	private JLabel laddress;
	private JLabel lcity;
	private JLabel lstate;
	private JLabel larrdate;
	private JLabel lcrimein;

public void displayDetails(int cid)
{
	try
	{
	ResultSet rs=stmt.executeQuery("select cid, fname, lname, aname, dob, age, gender, address, city, state, arresteddate, crimein, photo from face where cid="+cid);
	if(rs.next())
	{
		lcr.setText(rs.getString(1));
		lfname.setText(rs.getString(2));
		llast.setText(rs.getString(3));
		lalias.setText(rs.getString(4));
		ldob.setText(rs.getString(5));
		lage.setText(rs.getString(6));
		lgender.setText(rs.getString(7));
		laddress.setText(rs.getString(8));
		lcity.setText(rs.getString(9));
		lstate.setText(rs.getString(10));
		larrdate.setText(rs.getString(11));
		lcrimein.setText(rs.getString(12));
		String photofile=rs.getString(13);
		ImageIcon icophoto=new ImageIcon(""+photofile);
		lblPhotoSpace.setIcon(icophoto);
	}
	}catch(Exception e)
	{
		JOptionPane.showMessageDialog(this, e.getMessage(), "Criminal Details", JOptionPane.ERROR_MESSAGE);
	}
	}
	public Vector finalList(int max)
	{
		Vector fv=new Vector(2,2);
		int loc=0;
		loc=cidrepeats.indexOf(new Integer(max),loc);
		while(loc!=-1)
		{
			fv.add(cidvect.elementAt(loc));
			loc=cidrepeats.indexOf(new Integer(max),loc+1);
		}
		return fv;
	}
}