package hotel.pages;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import hotel.user.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SearchingPage{
    public void createsearchGUI(){

        JFrame.setDefaultLookAndFeelDecorated(true);


        JFrame searchframe = new JFrame("SearchRoom");
		searchframe.setSize(410,350);
        searchframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container cp = searchframe.getContentPane();
		cp.setLayout(new GridLayout(0, 1));
		
        JPanel searchpanel = new JPanel();	

        placeSearchComponents(searchpanel,searchframe);
		cp.add(searchpanel);
        searchframe.setVisible(true);
    }

	private void placeSearchComponents(JPanel panel, JFrame frame){
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
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
		panel.add(box);

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
		panel.add(box);

		JPanel singlePanel = new JPanel();		
		JLabel singleLabel = new JLabel("Single room:");

		singlePanel.add(singleLabel);
			
        JTextField singleroom = new JTextField(20);

        singlePanel.add(singleroom);
		box = Box.createHorizontalBox();

		box.add(singlePanel);
		panel.add(box);

		JPanel doublePanel = new JPanel();		
		JLabel doubleLabel = new JLabel("Double room:");

		doublePanel.add(doubleLabel);
			
        JTextField doubleroom = new JTextField(20);

        doublePanel.add(doubleroom);
		box = Box.createHorizontalBox();

		box.add(doublePanel);
		panel.add(box);

		JPanel quadPanel = new JPanel();	
		JLabel quadLabel = new JLabel("Quad room:");

		quadPanel.add(quadLabel);
		
        JTextField quadroom = new JTextField(20);

        quadPanel.add(quadroom);
		box = Box.createHorizontalBox();

		box.add(quadPanel);
		panel.add(box);		

		JPanel starPanel = new JPanel();	
		JLabel starLabel = new JLabel("Star:");

		starPanel.add(starLabel);
		
        JTextField star = new JTextField(20);

        starPanel.add(star);
		box = Box.createHorizontalBox();

		box.add(starPanel);
		panel.add(box);		
		
        JButton searchButton = new JButton("Search");
        JButton backButton = new JButton("Return");
		JPanel btnPanel = new JPanel();
		
        backButton.setPreferredSize(new Dimension(115, 25));
        searchButton.setPreferredSize(new Dimension(115, 25));
		btnPanel.add(searchButton);
		btnPanel.add(backButton);

		searchButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				SearchingResultPage searchingResult = new SearchingResultPage(indateY.getText()+"-"+indateM.getText()+"-"+indateD.getText(),outdateY.getText()+"-"+outdateM.getText()+"-"+outdateD.getText(),Integer.parseInt(singleroom.getText()),Integer.parseInt(doubleroom.getText()),Integer.parseInt(quadroom.getText()),Integer.parseInt(star.getText()));
				searchingResult.createresultGUI();
			}
		});
		backButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				TitlePage newtitle = new TitlePage();
				newtitle.createTitleGUI();
			}
		});
		box = Box.createHorizontalBox();
		box.add(btnPanel);
		panel.add(box);
		
	}
}