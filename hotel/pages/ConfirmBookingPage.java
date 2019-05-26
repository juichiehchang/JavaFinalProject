package hotel.pages;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import hotel.user.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ConfirmBookingPage{
	private String indate;
	private String outdate;
	private String singleroom;
	private String doubleroom;
	private String quadroom;
	private String hotelID;
	private String UID;
	private String total_nights;
	private String total_price;
	private String orderID;
	public ConfirmBookingPage(String indate, String outdate, String singleroom, String doubleroom, String quadroom, String hotelID, String UID, String total_nights, String total_price, String orderID){
		this.indate = indate;
		this.outdate = outdate;
		this.singleroom = singleroom;
		this.doubleroom = doubleroom;
		this.quadroom = quadroom;
		this.hotelID = hotelID;
		this.UID = UID;
		this.total_nights = total_nights;
		this.total_price = total_price;
		this.orderID = orderID;
		
	}
	public void confirmBooking(){
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame confirmBookingframe = new JFrame("Confirm");
		confirmBookingframe.setSize(400,350);

		Container cp = confirmBookingframe.getContentPane();
		cp.setLayout(new GridLayout(0, 1));

        JPanel bookpanel = new JPanel();
		bookpanel.setLayout(new BoxLayout(bookpanel, BoxLayout.Y_AXIS));

		Box box = Box.createHorizontalBox();		
		JLabel uidLabel = new JLabel("USER: " + UID);
		uidLabel.setPreferredSize(new Dimension(70, 350));
		box.add(uidLabel);
		bookpanel.add(box);

		box = Box.createHorizontalBox();
		JLabel orderIDLabel = new JLabel("Order ID: " + orderID );
		box.add(orderIDLabel);
		bookpanel.add(box);
		orderIDLabel.setPreferredSize(new Dimension(70, 350));

		box = Box.createHorizontalBox();
		JLabel hotelIDLabel = new JLabel("Hotel ID: " + hotelID );
		box.add(hotelIDLabel);
		bookpanel.add(box);
		hotelIDLabel.setPreferredSize(new Dimension(70, 350));
		
		box = Box.createHorizontalBox();
		JLabel indateLabel = new JLabel("Check-in Date: " + indate );
		box.add(indateLabel);
		bookpanel.add(box);
		indateLabel.setPreferredSize(new Dimension(70, 350));
		
		box = Box.createHorizontalBox();
		JLabel outdateLabel = new JLabel("Check-out Date: " + outdate );
		box.add(outdateLabel);
		bookpanel.add(box);
		outdateLabel.setPreferredSize(new Dimension(70, 350));
		
		box = Box.createHorizontalBox();
		JLabel singleLabel = new JLabel("Single room: " + singleroom );
		box.add(singleLabel);
		bookpanel.add(box);
		singleLabel.setPreferredSize(new Dimension(70, 350));
			
		box = Box.createHorizontalBox();
		JLabel doubleLabel = new JLabel("Double room: " + doubleroom );
		box.add(doubleLabel);
		bookpanel.add(box);
		doubleLabel.setPreferredSize(new Dimension(70, 350));
		
		box = Box.createHorizontalBox();
		JLabel quadLabel = new JLabel("Quad room: " + quadroom );
		box.add(quadLabel);
		bookpanel.add(box);
		quadLabel.setPreferredSize(new Dimension(70, 350));
		
		box = Box.createHorizontalBox();
		JLabel totalPriceLabel = new JLabel("Total price: " + total_price );
		box.add(totalPriceLabel);
		bookpanel.add(box);
		totalPriceLabel.setPreferredSize(new Dimension(70, 350));

		box = Box.createHorizontalBox();
		JLabel totalNightLabel = new JLabel("Total nights: " + total_nights );
		box.add(totalNightLabel);
		bookpanel.add(box);
		totalNightLabel.setPreferredSize(new Dimension(70, 350));

		
        JButton backButton = new JButton("Return");
		JPanel btnPanel = new JPanel();
        backButton.setPreferredSize(new Dimension(115, 25));


		btnPanel.add(backButton);

		backButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				confirmBookingframe.dispose();
				TitlePage newtitle = new TitlePage();
				newtitle.createTitleGUI();
			}
		});

		box = Box.createHorizontalBox();
		box.add(btnPanel);
		bookpanel.add(box);
		
		cp.add(bookpanel);
        confirmBookingframe.setVisible(true);		
	}
}