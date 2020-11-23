package demo.swing.ui;

import demo.swing.entity.Person;

import javax.swing.*;
import java.awt.event.*;

public class AddPersonDialogWithBuilder extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField firstNameInput;
    private JTextField lastNameInput;
    private PersonConsumer personConsumer;

    public AddPersonDialogWithBuilder(PersonConsumer personConsumer) {
        this.personConsumer = personConsumer;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        pack();
    }

    private void onOK() {
        String firstName = firstNameInput.getText().trim();
        if (firstName.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "First Name shouldn't be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String lastName = lastNameInput.getText().trim();

        if (lastName.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Last Name shouldn't be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Person person = new Person(firstName, lastName);
        personConsumer.addPerson(person);
        dispose();

    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
