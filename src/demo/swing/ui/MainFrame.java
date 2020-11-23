package demo.swing.ui;

import demo.swing.entity.Person;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Demo Persons App");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        DefaultListModel<Person> listModel = new DefaultListModel<>();
        JList<Person> list = new JList<>(listModel);
        JButton addPersonBtn = new JButton("Add Person");
        JButton removePersonBtn = new JButton("Remove Person");

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.add(addPersonBtn);
        buttonsPanel.add(Box.createVerticalStrut(10));
        buttonsPanel.add(removePersonBtn);

        removePersonBtn.addActionListener(e -> {
            int selectedIndex = list.getSelectedIndex();
            if (selectedIndex != -1) {
                listModel.remove(selectedIndex);
            }
        });

        addPersonBtn.addActionListener(e -> {
            JDialog addPersonDialog = new AddPersonDialogWithBuilder(newPerson -> listModel.addElement(newPerson));
            addPersonDialog.setModal(true);
            addPersonDialog.setLocationRelativeTo(this);
            addPersonDialog.setVisible(true);
        });

        list.addListSelectionListener(e -> {
            removePersonBtn.setEnabled(list.getSelectedIndex() != -1);
        });
        removePersonBtn.setEnabled(false);

        mainPanel.add(list, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.EAST);

        listModel.addElement(new Person("Ivan", "Ivanov"));
        listModel.addElement(new Person("Petrov", "Petrov"));

        setContentPane(mainPanel);
    }
}
