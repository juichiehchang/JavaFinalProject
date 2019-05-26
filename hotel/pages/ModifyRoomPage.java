package hotel.pages;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import hotel.user.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class ModifyRoomPage{
	public void createModifyRoomGUI(){
        JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame MRframe = new JFrame("Modify Room");
		MRframe.setSize(350,400);
		Container cp = MRframe.getContentPane();
		cp.setLayout(new GridLayout(0, 1));
		JPanel MRpanel = new JPanel();

        MRframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MRpanel.setLayout(new BoxLayout(MRpanel, BoxLayout.Y_AXIS));

		JPanel UIDPanel = new JPanel();	
		JLabel UIDLabel = new JLabel("User ID:");

		UIDPanel.add(UIDLabel);
		
        JTextField UID = new JTextField(20);

        UIDPanel.add(UID);
		Box box = Box.createHorizontalBox();

		box.add(UIDPanel);
		MRpanel.add(box);
		
		JPanel OIDPanel = new JPanel();	
		JLabel OIDLabel = new JLabel("Order ID:");

		OIDPanel.add(OIDLabel);
		
        JTextField OID = new JTextField(20);

        OIDPanel.add(OID);
		box = Box.createHorizontalBox();

		box.add(OIDPanel);
		MRpanel.add(box);
		
		JPanel singlePanel = new JPanel();		
		JLabel singleLabel = new JLabel("Single room:");

		singlePanel.add(singleLabel);
			
        JTextField singleroom = new JTextField(20);

        singlePanel.add(singleroom);
		box = Box.createHorizontalBox();

		box.add(singlePanel);
		MRpanel.add(box);

		JPanel doublePanel = new JPanel();		
		JLabel doubleLabel = new JLabel("Double room:");

		doublePanel.add(doubleLabel);
			
        JTextField doubleroom = new JTextField(20);

        doublePanel.add(doubleroom);
		box = Box.createHorizontalBox();

		box.add(doublePanel);
		MRpanel.add(box);

		JPanel quadPanel = new JPanel();	
		JLabel quadLabel = new JLabel("Quad room:");

		quadPanel.add(quadLabel);
		
        JTextField quadroom = new JTextField(20);

        quadPanel.add(quadroom);
		box = Box.createHorizontalBox();

		box.add(quadPanel);
		MRpanel.add(box);

        JButton confirmButton = new JButton("Confirm");
        JButton backButton = new JButton("Return");
		JPanel btnPanel = new JPanel();
		
        backButton.setPreferredSize(new Dimension(115, 25));
        confirmButton.setPreferredSize(new Dimension(115, 25));
		btnPanel.add(confirmButton);
		btnPanel.add(backButton);

		confirmButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ModifyRoom mdate = new ModifyRoom(UID.getText(), Integer.parseInt(OID.getText()), Integer.parseInt(singleroom.getText()), Integer.parseInt(doubleroom.getText()), Integer.parseInt(quadroom.getText()));
				try{
					ModifyRoomResult r = mdate.setRoom();
					MRframe.dispose();
					ConfirmMRPage mrConfirm = new ConfirmMRPage(r.one_adult, r.two_adults, r.four_adults);
					mrConfirm.confirmMRGUI();
				}
				catch(Exception order_err){
					ErrorPage showError = new ErrorPage(order_err.getMessage());
					showError.error_msg();
				}
			}
		
		});
		
		backButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				MRframe.dispose();
				TitlePage newtitle = new TitlePage();
				newtitle.createTitleGUI();
			}
		});
		box = Box.createHorizontalBox();
		box.add(btnPanel);
		MRpanel.add(box);
		
		cp.add(MRpanel);
        MRframe.setVisible(true);
	}
}