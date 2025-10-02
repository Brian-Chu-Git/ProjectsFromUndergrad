package edu.ucalgary.oop;
/**

The GUIBackupVolunteerConfirmation class is a JFrame that displays a confirmation message to the backup volunteer
regarding their availability. It has a warning message and a confirm button. When the confirm button is clicked,
the JFrame is disposed. This class implements the ActionListener interface.
@author Youssef Hamed
@since April 2023
*/
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class GUIBackupVolunteerConfirmation extends JFrame implements ActionListener{

    private JLabel warning;
    private JButton confirm;
    private JPanel instrucPanel;
    private JPanel confirmPanel;

/**
 * Creates a GUIBackupVolunteerConfirmation object with the givenHour parameter.
 * @param givenHour the Hour object that represents the hour for which the backup volunteer is needed
 */
    public GUIBackupVolunteerConfirmation(Hour givenHour){
        super("Please confirm the backup volunteer availability");
        System.out.println(givenHour.gethourStr());
        setupGUI(givenHour);
        setSize(600, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
/**
 * Sets up the GUI for the confirmation message.
 * @param givenHour the Hour object that represents the hour for which the backup volunteer is needed
 */
    public void setupGUI(Hour givenHour){
        
        warning = new JLabel("At " + givenHour.gethourStr() + " a backup volunteer is needed, please confirm their availability");
        confirm = new JButton("Confirm");
        confirm.addActionListener(this);

        instrucPanel = new JPanel();
        instrucPanel.add(warning);

        confirmPanel = new JPanel();
        confirmPanel.add(confirm);

        this.add(instrucPanel, BorderLayout.NORTH);
        this.add(confirmPanel, BorderLayout.CENTER);

    }
/**
 * Disposes the JFrame when the confirm button is clicked.
 */
    public void actionPerformed(ActionEvent e){
        this.dispose();
    }

}
