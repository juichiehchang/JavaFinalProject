package hotel.pages;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import hotel.user.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CheckPage{
	public void createCheckGUI(){
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame checkframe = new JFrame("Checklation");
		checkframe.setSize(400,200);
        checkframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel checkpanel = new JPanel();
		checkpanel.setLayout(new BoxLayout(checkpanel, BoxLayout.Y_AXIS));

		JPanel UIDPanel = new JPanel();	
		JLabel UIDLabel = new JLabel("User ID:");

		UIDPanel.add(UIDLabel);
		
        JTextField UID = new JTextField(20);

        UIDPanel.add(UID);
		Box box = Box.createHorizontalBox();

		box.add(UIDPanel);
		checkpanel.add(box);
		
		JPanel OIDPanel = new JPanel();	
		JLabel OIDLabel = new JLabel("Order ID:");

		OIDPanel.add(OIDLabel);
		
        JTextField OID = new JTextField(20);

        OIDPanel.add(OID);
		box = Box.createHorizontalBox();

		box.add(OIDPanel);
		checkpanel.add(box);

		checkframe.add(checkpanel,BorderLayout.CENTER);

        JButton confirmButton = new JButton("Confirm");
        JButton backButton = new JButton("Return");
		JPanel btnPanel = new JPanel();
		
        backButton.setPreferredSize(new Dimension(115, 25));
        confirmButton.setPreferredSize(new Dimension(115, 25));
		btnPanel.add(confirmButton);
		btnPanel.add(backButton);

		confirmButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Check checkbook = new Check(UID.getText(), Integer.parseInt(OID.getText()));
				try{
					CheckResult r = checkbook.getOrder();
					checkframe.dispose();
					JOptionPane.showMessageDialog(null, "<html>HOTEL_ID: " + r.hotel_id +"<br/>ONE_ADULT: " + r.one_adult + 
													"<br/>TWO_ADULTS: "+ r.two_adults+"<br/>FOUR_ADULTS: " + r.four_adults +
													"<br/>IN_DATE: " + r.in_date+"<br/>OUT_DATE: " + r.out_date +
													"<br/>TOTAL_NIGHTS: " + r.total_nights + "<br/>TOTAL_PRICE: " + r.total_price + "</html>");
					TitlePage newtitle = new TitlePage();
					newtitle.createTitleGUI();
				}
				catch(Exception order_err){
					ErrorPage showError = new ErrorPage(order_err.getMessage());
					showError.error_msg();
				}
			}
		
		});
		
		backButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				checkframe.dispose();
				TitlePage newtitle = new TitlePage();
				newtitle.createTitleGUI();
			}
		});

		checkframe.add(btnPanel,BorderLayout.SOUTH);
		checkframe.setVisible(true);
		
	}
}