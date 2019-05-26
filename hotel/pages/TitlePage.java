package hotel.pages;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import hotel.user.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TitlePage{
	public void createTitleGUI(){
        JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame titleframe = new JFrame("Title");
		titleframe.setSize(410,350);
		Container cp = titleframe.getContentPane();
		cp.setLayout(new GridLayout(0, 1));
		JPanel titlepanel = new JPanel();

        titleframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		createTitlecomponent(titlepanel,titleframe);
		cp.add(titlepanel);
        titleframe.setVisible(true);
	}

	private void createTitlecomponent(JPanel panel,JFrame frame)
	{

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		Box box = Box.createHorizontalBox();
		JLabel intro = new JLabel("Thank you for using Trivagogogo!");
		intro.setPreferredSize(new Dimension(70, 350));
		box.add(intro);
		panel.add(box);

        JButton searchButton = new JButton("Search hotel");

		searchButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				SearchingPage searchPage = new SearchingPage();
				searchPage.createsearchGUI();
			}
		});
		box = Box.createHorizontalBox();
		JPanel but1 = new JPanel();
		but1.add(searchButton);
		but1.setPreferredSize(new Dimension(50, 350));
		box.add(but1);
        panel.add(box);

        JButton bookButton = new JButton("Start booking");

		bookButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				BookingPage bookPage = new BookingPage();
				bookPage.createBookingGUI();
			}
		});

		box = Box.createHorizontalBox();
		JPanel but2 = new JPanel();
		but2.add(bookButton);	
		but2.setPreferredSize(new Dimension(50, 350));		
		box.add(but2);
        panel.add(box);

        JButton modifyButton = new JButton("Modify booking");

		modifyButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				ModifyModePage modifypage = new ModifyModePage();
				modifypage.createModifyModeGUI();
			}
		});

		box = Box.createHorizontalBox();
		JPanel but3 = new JPanel();
		but3.add(modifyButton);	
		but3.setPreferredSize(new Dimension(50, 350));		
		box.add(but3);
        panel.add(box);	

        JButton cancelButton = new JButton("Cancel booking");

		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				CancelPage cnpage = new CancelPage();
				cnpage.createCancelGUI();
			}
		});

		box = Box.createHorizontalBox();
		JPanel but4 = new JPanel();
		but4.add(cancelButton);	
		but4.setPreferredSize(new Dimension(50, 350));		
		box.add(but4);
        panel.add(box);	

        JButton checkBookingButton = new JButton("Check booking");

		checkBookingButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				CheckPage ckpage = new CheckPage();
				ckpage.createCheckGUI();
			}
		});

		box = Box.createHorizontalBox();
		JPanel but5 = new JPanel();
		but5.add(checkBookingButton);	
		but5.setPreferredSize(new Dimension(50, 350));		
		box.add(but5);
        panel.add(box);			
	}
    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
				TitlePage newtitle = new TitlePage();
				newtitle.createTitleGUI();
            }
        });
    }
}