package hotel.pages;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import hotel.user.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ConfirmMDPage{
	String in_date;
	String out_date;

	public ConfirmMDPage(String in_date, String out_date){
		this.in_date = in_date;
		this.out_date = out_date;
	}

	public void confirmMDGUI(){
        JFrame.setDefaultLookAndFeelDecorated(true);


        JFrame confirmMDframe = new JFrame("ModifyDateResult");
		confirmMDframe.setSize(300,300);
		
		confirmMDframe.add(new JLabel("Your booking has been changed from: " + in_date +" to " + out_date),BorderLayout.CENTER);
		
        JButton backButton = new JButton("Return");
		JPanel btnPanel = new JPanel();
        backButton.setPreferredSize(new Dimension(115, 25));


		btnPanel.add(backButton);

		backButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				confirmMDframe.dispose();
				TitlePage newtitle = new TitlePage();
				newtitle.createTitleGUI();
			}
		});
		confirmMDframe.add(btnPanel, BorderLayout.SOUTH);
        confirmMDframe.setVisible(true);	
	}
}