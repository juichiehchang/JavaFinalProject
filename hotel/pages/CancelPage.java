package hotel.pages;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import hotel.user.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CancelPage{
	public void createCancelGUI(){
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame cancelframe = new JFrame("Cancellation");
		cancelframe.setSize(400,200);
        cancelframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel cancelpanel = new JPanel();
		cancelpanel.setLayout(new BoxLayout(cancelpanel, BoxLayout.Y_AXIS));

		JPanel UIDPanel = new JPanel();	
		JLabel UIDLabel = new JLabel("User ID:");

		UIDPanel.add(UIDLabel);
		
        JTextField UID = new JTextField(20);

        UIDPanel.add(UID);
		Box box = Box.createHorizontalBox();

		box.add(UIDPanel);
		cancelpanel.add(box);
		
		JPanel OIDPanel = new JPanel();	
		JLabel OIDLabel = new JLabel("Order ID:");

		OIDPanel.add(OIDLabel);
		
        JTextField OID = new JTextField(20);

        OIDPanel.add(OID);
		box = Box.createHorizontalBox();

		box.add(OIDPanel);
		cancelpanel.add(box);

		cancelframe.add(cancelpanel,BorderLayout.CENTER);

        JButton confirmButton = new JButton("Confirm");
        JButton backButton = new JButton("Return");
		JPanel btnPanel = new JPanel();
		
        backButton.setPreferredSize(new Dimension(115, 25));
        confirmButton.setPreferredSize(new Dimension(115, 25));
		btnPanel.add(confirmButton);
		btnPanel.add(backButton);

		confirmButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Cancel cancelbook = new Cancel(UID.getText(), Integer.parseInt(OID.getText()));
				try{
					cancelbook.delOrder();
					cancelframe.dispose();
					JOptionPane.showMessageDialog(null, "Cancellation succeed.");
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
				cancelframe.dispose();
				TitlePage newtitle = new TitlePage();
				newtitle.createTitleGUI();
			}
		});

		cancelframe.add(btnPanel,BorderLayout.SOUTH);
		cancelframe.setVisible(true);
		
	}
}