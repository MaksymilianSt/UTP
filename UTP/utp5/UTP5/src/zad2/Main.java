/**
 *
 *  @author Stachnik Maksymilian S25304
 *
 */

package zad2;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main extends JFrame {

  private DefaultListModel<Future<Integer>> tasks;
  private JList<Future<Integer>> taskDisplay;
  private JButton addButton;
  private JButton cancelButton;
  private JButton getResultButton;

  private ExecutorService executorService;

  public Main() {

    executorService = Executors.newFixedThreadPool(3);
    setTitle("Task List ");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    tasks = new DefaultListModel<>();
    taskDisplay = new JList<>(tasks);


    JScrollPane scrollPane = new JScrollPane(taskDisplay);

    addButton = new JButton("New Task");
    addButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        addTask();
      }
    });

    cancelButton = new JButton("Cancel ");
    cancelButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        cancelTask();
      }
    });

    getResultButton = new JButton("Get Result");
    getResultButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        getResult();
      }
    });

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(addButton);
    buttonPanel.add(cancelButton);
    buttonPanel.add(getResultButton);

    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(scrollPane, BorderLayout.CENTER);
    getContentPane().add(buttonPanel, BorderLayout.SOUTH);

    pack();
    setLocationRelativeTo(null);
  }

  private void addTask() {
    Future<Integer> task = newTask();
    tasks.addElement(task);
  }

  private Future<Integer> newTask() {
    return executorService.submit(() -> {
      int i = 0;
      while (i++ < 3) {
        try {
          Thread.sleep(500);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
      return ((int) (Math.random() * 10));
    });
  }

  private void cancelTask() {
    int selectedIndex = taskDisplay.getSelectedIndex();
    if (selectedIndex != -1) {
      Future<?> selectedTask = tasks.getElementAt(selectedIndex);
      selectedTask.cancel(true);
      tasks.remove(selectedIndex);
    }
  }

  private void getResult() {
    int selectedIndex = taskDisplay.getSelectedIndex();
    if (selectedIndex != -1) {
      Future<?> selectedTask = tasks.getElementAt(selectedIndex);
      if (selectedTask.isDone()) {
        try {
          Object result = selectedTask.get();
          JOptionPane.showMessageDialog(this, "Task result: " + result.toString(), "Task Result", JOptionPane.INFORMATION_MESSAGE);
        } catch (InterruptedException | ExecutionException e) {
          JOptionPane.showMessageDialog(this, "Error retrieving task result: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
      } else {
        JOptionPane.showMessageDialog(this, "Task is not yet completed.", "Information", JOptionPane.INFORMATION_MESSAGE);
      }
    }
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(()->{
      Main taskListExample = new Main();
      taskListExample.setVisible(true);
    });
  }
}
