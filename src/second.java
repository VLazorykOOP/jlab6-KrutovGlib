import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class second {
    private JFrame frame;
    private JPanel panel;
    private JButton calculateButton;
    private JLabel matrixSizeLabel;
    private JTextField matrixSizeField;
    private JTable matrixTable;
    private JButton initMatrixButton;
    private JTextArea resultArea;
    private JButton loadMatrixButton;
    private DefaultTableModel tableModel;

    public static void second() {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new second().createAndShowGUI();
        });
    }

    private void createAndShowGUI() {
        frame = new JFrame("Матриця");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);

        panel = new JPanel();
        panel.setLayout(new GridLayout(6, 3));

        matrixSizeLabel = new JLabel("Введіть розмір матриці:");
        matrixSizeField = new JTextField();

        initMatrixButton = new JButton("Ініціалізувати матрицю");
        initMatrixButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int size = Integer.parseInt(matrixSizeField.getText());
                    String[] columncount = new String[size];
                    tableModel = new DefaultTableModel(new String[size][size], columncount);
                    matrixTable.setModel(tableModel);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Введено некоректний розмір матриці.", "Помилка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        loadMatrixButton = new JButton("Зчитати матрицю з файлу");
        loadMatrixButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("Текстові файли", "txt"));
                int result = fileChooser.showOpenDialog(frame);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
                        String line;
                        int row = 0;
                        while ((line = reader.readLine()) != null && row < tableModel.getRowCount()) {
                            String[] values = line.trim().split("\\s+");
                            for (int col = 0; col < Math.min(values.length, tableModel.getColumnCount()); col++) {
                                tableModel.setValueAt(values[col], row, col);
                            }
                            row++;
                        }
                        reader.close();
                    } catch (Exception ex) {
                        ex.printStackTrace(System.out);
                        JOptionPane.showMessageDialog(frame, "Помилка при зчитуванні матриці з файлу.", "Помилка", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        calculateButton = new JButton("Обчислити");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (tableModel != null) {
                        int size = Integer.parseInt(matrixSizeField.getText());
                        double[][] A = new double[size][size];
                        double[] B = new double[size];

                        // Зчитування матриці з DefaultTableModel
                        for (int i = 0; i < size; i++) {
                            for (int j = 0; j < size; j++) {
                                Object value = tableModel.getValueAt(i, j);
                                if (value != null) {
                                    A[i][j] = Double.parseDouble(value.toString());
                                } else {
                                    A[i][j] = 0.0;
                                }
                            }
                        }

                        // Обчислення вектора B
                        for (int i = 0; i < size; ++i) {
                            double rowSum = 0.0;
                            for (int j = 0; j < size; j++) {
                                rowSum += A[i][j];
                            }
                            B[i] = rowSum / (double) size;
                        }

                        // Виведення результату
                        resultArea.setText("Вектор B:\n");
                        for (int i = 0; i < size; i++) {
                            resultArea.append(Double.toString(B[i]) + "\n");
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Ініціалізуйте матрицю перед обчисленням.", "Помилка", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (CustomException ex) {
                    ex.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane resultScrollPane = new JScrollPane(resultArea);

        // Виправлення проблеми NullPointerException
        matrixTable = new JTable();
        panel.add(matrixSizeLabel);
        panel.add(matrixSizeField);
        panel.add(initMatrixButton);// ініціалізація
        panel.add(loadMatrixButton); //файл

        panel.add(new JLabel());

        panel.add(new JLabel());
        panel.add(matrixTable);
        panel.add(calculateButton);
        panel.add(matrixTable);

        panel.add(resultScrollPane);

        frame.add(panel);
        frame.setVisible(true);
    }
}
