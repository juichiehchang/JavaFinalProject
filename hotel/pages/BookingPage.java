package hotel.pages;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import hotel.user.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import hotel.exceptions.*;

public class BookingPage{

    public void createBookingGUI(){
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame bookframe = new JFrame("Booking");
		bookframe.setSize(410,350);
        bookframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container cp = bookframe.getContentPane();
		cp.setLayout(new GridLayout(0, 1));

        JPanel bookpanel = new JPanel();	
		
		JPanel indatePanel = new JPanel();
		Box box = Box.createHorizontalBox();
		
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
		bookpanel.add(box);

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
		bookpanel.add(box);

		JPanel singlePanel = new JPanel();		
		JLabel singleLabel = new JLabel("Single room:");

		singlePanel.add(singleLabel);
			
        JTextField singleroom = new JTextField(20);

        singlePanel.add(singleroom);
		box = Box.createHorizontalBox();

		box.add(singlePanel);
		bookpanel.add(box);

		JPanel doublePanel = new JPanel();		
		JLabel doubleLabel = new JLabel("Double room:");

		doublePanel.add(doubleLabel);
			
        JTextField doubleroom = new JTextField(20);

        doublePanel.add(doubleroom);
		box = Box.createHorizontalBox();

		box.add(doublePanel);
		bookpanel.add(box);

		JPanel quadPanel = new JPanel();	
		JLabel quadLabel = new JLabel("Quad room:");

		quadPanel.add(quadLabel);
		
        JTextField quadroom = new JTextField(20);

        quadPanel.add(quadroom);
		box = Box.createHorizontalBox();

		box.add(quadPanel);
		bookpanel.add(box);
		
		JPanel hotelIDPanel = new JPanel();	
		JLabel hotelIDLabel = new JLabel("Hotel ID:");

		hotelIDPanel.add(hotelIDLabel);
		
        JTextField hotelID = new JTextField(20);

        hotelIDPanel.add(hotelID);
		box = Box.createHorizontalBox();

		box.add(hotelIDPanel);
		bookpanel.add(box);		

		JPanel UIDPanel = new JPanel();	
		JLabel UIDLabel = new JLabel("User ID:");

		UIDPanel.add(UIDLabel);
		
        JTextField UID = new JTextField(20);

        UIDPanel.add(UID);
		box = Box.createHorizontalBox();

		box.add(UIDPanel);
		bookpanel.add(box);

        JButton confirmButton = new JButton("Confirm");
        JButton backButton = new JButton("Return");
		JPanel btnPanel = new JPanel();
		
        backButton.setPreferredSize(new Dimension(115, 25));
        confirmButton.setPreferredSize(new Dimension(115, 25));
		btnPanel.add(confirmButton);
		btnPanel.add(backButton);

		confirmButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Order order = new Order(Integer.parseInt(singleroom.getText()), Integer.parseInt(doubleroom.getText()), Integer.parseInt(quadroom.getText()), indateY.getText()+"-"+indateM.getText()+"-"+indateD.getText(), outdateY.getText()+"-"+outdateM.getText()+"-"+outdateD.getText(), Integer.parseInt(hotelID.getText()), UID.getText());
				try{
					OrderResult r = order.orderRoom();
					bookframe.dispose();
					ConfirmBookingPage bookingConfirm = new ConfirmBookingPage(r.in_date, r.out_date, Integer.toString(r.one_adult), Integer.toString(r.two_adults), Integer.toString(r.four_adults), Integer.toString(r.hotel_id), r.uid, Integer.toString(r.total_nights), Integer.toString(r.total_price), Integer.toString(r.id));
					bookingConfirm.confirmBooking();
				}
				catch(OrderException order_err){
					ErrorPage showError = new ErrorPage(order_err.getMessage());
					showError.error_msg();
				}
			}
		
		});
		
		backButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				bookframe.dispose();
				TitlePage newtitle = new TitlePage();
				newtitle.createTitleGUI();
			}
		});
		box = Box.createHorizontalBox();
		box.add(btnPanel);
		bookpanel.add(box);
		
		cp.add(bookpanel);
        bookframe.setVisible(true);		
	}

    public static void main(String[] args) {

		BookingPage bookpage = new BookingPage();
		bookpage.createBookingGUI();

    }
}