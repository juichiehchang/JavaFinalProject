package hotel.pages;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import hotel.user.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class ModifyDatePage{
	public void createModifyDateGUI(){
        JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame MDframe = new JFrame("Modify Date");
		MDframe.setSize(350,400);
		Container cp = MDframe.getContentPane();
		cp.setLayout(new GridLayout(0, 1));
		JPanel MDpanel = new JPanel();

        MDframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MDpanel.setLayout(new BoxLayout(MDpanel, BoxLayout.Y_AXIS));

		JPanel UIDPanel = new JPanel();	
		JLabel UIDLabel = new JLabel("User ID:");

		UIDPanel.add(UIDLabel);
		
        JTextField UID = new JTextField(20);

        UIDPanel.add(UID);
		Box box = Box.createHorizontalBox();

		box.add(UIDPanel);
		MDpanel.add(box);
		
		JPanel OIDPanel = new JPanel();	
		JLabel OIDLabel = new JLabel("Order ID:");

		OIDPanel.add(OIDLabel);
		
        JTextField OID = new JTextField(20);

        OIDPanel.add(OID);
		box = Box.createHorizontalBox();

		box.add(OIDPanel);
		MDpanel.add(box);
		
		JPanel indatePanel = new JPanel();
		box = Box.createHorizontalBox();
		
		JLabel inLabel = new JLabel("Check-in date(YYYY-MM-DD):");

		indatePanel.add(inLabel);
		
        JTextField indateY = new JTextField(3);
        JTextField indateM = new JTextField(2);
        JTextField indateD = new JTextField(2);
		Box datebox = Box.createHorizontalBox();
		datebox.add(indateY);
		datebox.add(new JLabel("-"));
		datebox.add(indateM);
		datebox.add(new JLabel("-"));
		datebox.add(indateD);

		indatePanel.add(datebox);

		box.add(indatePanel);		
		MDpanel.add(box);

		JPanel outdatePanel = new JPanel();
		JLabel outLabel = new JLabel("Check-out date(YYYY-MM-DD):");

		outdatePanel.add(outLabel);
		
        JTextField outdateY = new JTextField(3);
        JTextField outdateM = new JTextField(2);
        JTextField outdateD = new JTextField(2);
		datebox = Box.createHorizontalBox();
		datebox.add(outdateY);
		datebox.add(new JLabel("-"));
		datebox.add(outdateM);
		datebox.add(new JLabel("-"));
		datebox.add(outdateD);

        outdatePanel.add(datebox);
		box = Box.createHorizontalBox();

		box.add(outdatePanel);
		MDpanel.add(box);

        JButton confirmButton = new JButton("Confirm");
        JButton backButton = new JButton("Return");
		JPanel btnPanel = new JPanel();
		
        backButton.setPreferredSize(new Dimension(115, 25));
        confirmButton.setPreferredSize(new Dimension(115, 25));
		btnPanel.add(confirmButton);
		btnPanel.add(backButton);

		confirmButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ModifyDate mdate = new ModifyDate(UID.getText(), Integer.parseInt(OID.getText()), indateY.getText()+"-"+indateM.getText()+"-"+indateD.getText(), outdateY.getText()+"-"+outdateM.getText()+"-"+outdateD.getText());
				try{
					ModifyDateResult r = mdate.setDate();
					MDframe.dispose();
					ConfirmMDPage mdConfirm = new ConfirmMDPage(r.in_date, r.out_date);
					mdConfirm.confirmMDGUI();
				}
				catch(Exception order_err){
					ErrorPage showError = new ErrorPage(order_err.getMessage());
					showError.error_msg();
				}
			}
		
		});
		
		backButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				MDframe.dispose();
				TitlePage newtitle = new TitlePage();
				newtitle.createTitleGUI();
			}
		});
		box = Box.createHorizontalBox();
		box.add(btnPanel);
		MDpanel.add(box);
		
		cp.add(MDpanel);
        MDframe.setVisible(true);
	}
}