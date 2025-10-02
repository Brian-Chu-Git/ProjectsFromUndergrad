package edu.ucalgary.oop;
/**

The GUIUnavoidableOverlapInstrc class provides a graphical user interface for selecting a treatment
to modify due to an unavoidable overlap in schedule. This class extends JFrame and implements the
ActionListener interface to handle user interactions with the interface.
@author Youssef Hamed
@since April 2023
*/
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUIUnavoidableOverlapInstrc extends JFrame implements ActionListener{

    private JLabel instruction;
    private JComboBox<String> cb;
    private JButton confirm; 
    private JPanel instrucPanel;
    private JPanel selectionPanel;
    private JPanel confirmPanel;
    private ArrayList<Treatment> possibleTreatments;
    private Hour errorHour;
/**
 * Constructs a GUIUnavoidableOverlapInstrc object with a given Hour object, which contains the treatments
 * that are overlapping.
 * 
 * @param givenHour the Hour object containing the treatments that are overlapping
 */
    public GUIUnavoidableOverlapInstrc(Hour givenHour){
        super("Please select one of the following tasks to modify");
        this.possibleTreatments = givenHour.getTreatments();
        this.errorHour = givenHour;
        setupUOIGUI(this.possibleTreatments);
        setSize(500, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
/**
 * Sets up the graphical user interface with a JComboBox containing the treatments that are overlapping
 * and a JButton to confirm the selection.
 * 
 * @param givenTreatments an ArrayList of Treatment objects containing the treatments that are overlapping
 */
    public void setupUOIGUI(ArrayList<Treatment> givenTreatments){

        instruction = new JLabel("There is an error at " + errorHour.gethourStr() + ". Please select one of the following activities to shift: ");
        
        // ArrayList<Task> taskArray = new ArrayList<Task>();
        // taskArray.add(new Task(1, "this is task1",2, 3));
        // taskArray.add(new Task(1, "this is task2",2, 3));
        // taskArray.add(new Task(1, "this is task3",2, 3));

        String[] choices = {givenTreatments.get(0).getAnimalTask().getDescription() + " - " + givenTreatments.get(0).getAnimal().getName(), givenTreatments.get(1).getAnimalTask().getDescription() + " - " + givenTreatments.get(1).getAnimal().getName(), givenTreatments.get(2).getAnimalTask().getDescription() + " - " + givenTreatments.get(2).getAnimal().getName()};
        
        cb = new JComboBox<String>(choices);
        cb.addActionListener(this);


        confirm = new JButton("Confirm");
        confirm.addActionListener(this);

        instrucPanel = new JPanel();
        instrucPanel.add(instruction);

        selectionPanel = new JPanel();
        selectionPanel.add(cb);

        confirmPanel = new JPanel();
        confirmPanel.add(confirm);

        this.add(instrucPanel, BorderLayout.NORTH);
        this.add(selectionPanel, BorderLayout.CENTER);
        this.add(confirmPanel, BorderLayout.PAGE_END);

    }
/**
 * Handles user interactions with the graphical user interface. If the user selects the "Confirm"
 * button, the index of the selected treatment is printed to the console and a new GUIUnavoidableOverlap
 * object is created with the selected Treatment object.
 * 
 * @param e the ActionEvent object representing the user interaction
 */
    public void actionPerformed(ActionEvent e){
        Object src = e.getSource();

        if(src == confirm){
            System.out.println(cb.getSelectedIndex());
            System.out.println(this.possibleTreatments.get(cb.getSelectedIndex()).getAnimalTask().getDescription());
            new GUIUnavoidableOverlap(this.possibleTreatments.get(cb.getSelectedIndex())).setVisible(true);       // need to get the right index somehow
            this.dispose();
        }



    }

    

}
