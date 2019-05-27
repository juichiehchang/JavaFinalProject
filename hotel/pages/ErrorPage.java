package hotel.pages;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import hotel.user.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ErrorPage{
	String error_msg;
	public ErrorPage(String error_msg){
		this.error_msg = error_msg;
	}

	public void error_msg(){
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame errorframe = new JFrame("Error");
		errorframe.setSize(500,500);
        errorframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel errorpanel = new JPanel();
		JLabel errorlabel = new JLabel();
		errorlabel.setText("<html>" + error_msg.replaceAll("<","&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>");
		errorpanel.add(errorlabel,BorderLayout.CENTER);
		errorframe.add(errorpanel,BorderLayout.CENTER);

        JButton backButton = new JButton("Return");
		JPanel btnPanel = new JPanel();
        backButton.setPreferredSize(new Dimension(115, 25));


		btnPanel.add(backButton);

		backButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				errorframe.dispose();
			}
		});

		errorframe.add(btnPanel,BorderLayout.SOUTH);
		errorframe.setVisible(true);
		
	}
}
	
	