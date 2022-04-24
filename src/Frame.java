import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;

public class Frame extends JFrame {
    private static final Color MAIN_COLOR = new Color(33, 33, 33);
    private static final Color FONT_COLOR = new Color(232, 162, 54);
    private static final Font FONT = new Font("Dialog", Font.PLAIN, 24);

    private JPanel bottomPanel;
    private JTextField textField;
    private JButton button;
    private JTextArea area;
    private JScrollPane scrollPane;

    private final Calculator calculator;

    public Frame(String name) {
        super(name);
        calculator = new Calculator();
        initCenterPanel();
        initBottomPanel();
        initFrame();
    }

    private void initBottomPanel() {
        bottomPanel = new JPanel();

        textField = new JTextField();
        textField.setPreferredSize(new Dimension(270, 50));
        textField.setFont(FONT);
        textField.setForeground(FONT_COLOR);
        textField.setCaretColor(FONT_COLOR);
        textField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        textField.setBackground(MAIN_COLOR);

        bottomPanel.add(textField);

        button = new JButton("=");
        button.setPreferredSize(new Dimension(50, 50));
        button.setFont(FONT);
        button.setBackground(MAIN_COLOR);
        button.setForeground(FONT_COLOR);
        button.setFocusable(false);
        bottomPanel.add(button);

        addTextFieldActionListener();
        addButtonActionListener();

        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        textField.requestFocus();
    }

    private void initCenterPanel() {
        area = new JTextArea();
        area.setBackground(MAIN_COLOR);
        area.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        area.setFont(FONT);
        area.setForeground(FONT_COLOR);
        area.setCaretColor(FONT_COLOR);
        area.setEditable(false);

        scrollPane = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        addMouseListener();

        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }


    private void initFrame() {
        setSize(350, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        textField.requestFocus();
    }

    private void addTextFieldActionListener() {
        textField.addActionListener(e -> {
            addActionHelper();
        });
    }

    private void addButtonActionListener() {
        button.addActionListener(e -> {
            addActionHelper();
        });
    }

    private void addActionHelper() {
        if (!textField.getText().isBlank() && !textField.getText().matches(".*[A-Za-zА-Яа-я]+.*")) {
            String textWithMulSigns = calculator.insertMulSigns(textField.getText());
            String textRPN = calculator.transformToRPN(textWithMulSigns);
            double value = calculator.calculateFromRPN(textRPN);
            area.setText(area.getText() + textField.getText() + " = " + value + "\n");
            textField.setText("");
        }
    }

    private void addMouseListener() {
        scrollPane.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                JScrollBar bar = scrollPane.getVerticalScrollBar();
                if (e.getWheelRotation() < 0) {
                    System.out.println("mouse wheel Up");
                    bar.setValue(bar.getValue() - bar.getMaximum() / 20);
                } else {
                    System.out.println("mouse wheel Down");
                    bar.setValue(bar.getValue() + bar.getMaximum() / 20);
                }
                super.mouseWheelMoved(e);
            }
        });
    }

}
