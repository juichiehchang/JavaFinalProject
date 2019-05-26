package hotel.pages;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import hotel.user.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ModifyModePage{
	public void createModifyModeGUI(){
        JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame MMframe = new JFrame("ModifyMode");
		MMframe.setSize(300,300);
		Container cp = MMframe.getContentPane();
		cp.setLayout(new GridLayout(0, 1));
		JPanel MMpanel = new JPanel();

        MMframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MMpanel.setLayout(new BoxLayout(MMpanel, BoxLayout.Y_AXIS));		
		
        JButton MDButton = new JButton("Modify Date");
		MDButton.setPreferredSize(new Dimension(115, 25));
		MDButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				MMframe.dispose();
				ModifyDatePage mdPage = new ModifyDatePage();
				mdPage.createModifyDateGUI();
			}
		});
		Box box = Box.createHorizontalBox();
		JPanel but1 = new JPanel();
		but1.add(MDButton);
		but1.setPreferredSize(new Dimension(100, 300));
		box.add(but1);
        MMpanel.add(box);

        JButton MRButton = new JButton("Modify Room");
		MRButton.setPreferredSize(new Dimension(115, 25));
		MRButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				MMframe.dispose();
				ModifyRoomPage mrPage = new ModifyRoomPage();
				mrPage.createModifyRoomGUI();
			}
		});

		box = Box.createHorizontalBox();
		JPanel but2 = new JPanel();
		but2.add(MRButton);	
		but2.setPreferredSize(new Dimension(100, 300));		
		box.add(but2);
        MMpanel.add(box);

		cp.add(MMpanel);
        MMframe.setVisible(true);
	}

}