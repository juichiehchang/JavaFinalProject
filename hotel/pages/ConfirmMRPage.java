package hotel.pages;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import hotel.user.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ConfirmMRPage{
	int single_room;
	int double_room;
	int quad_room;	

	public ConfirmMRPage(int one, int two, int four){
		this.single_room = one;
		this.double_room = two;
		this.quad_room = four;		
	}

	public void confirmMRGUI(){
        JFrame.setDefaultLookAndFeelDecorated(true);


        JFrame confirmMRframe = new JFrame("ModifyRoomResult");
		confirmMRframe.setSize(300,300);
		
		confirmMRframe.add(new JLabel("<html>Your booking has been changed to:<br/>single: " + single_room +"<br/>double: " + double_room + "<br/>quad: " + quad_room + "</html>"),BorderLayout.CENTER);
		
        JButton backButton = new JButton("Return");
		JPanel btnPanel = new JPanel();
        backButton.setPreferredSize(new Dimension(115, 25));


		btnPanel.add(backButton);

		backButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				confirmMRframe.dispose();
				TitlePage newtitle = new TitlePage();
				newtitle.createTitleGUI();
			}
		});
		confirmMRframe.add(btnPanel, BorderLayout.SOUTH);
        confirmMRframe.setVisible(true);	
	}
}