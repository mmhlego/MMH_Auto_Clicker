import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.InputEvent;

public class GUI extends JFrame implements Runnable {
    private static final long serialVersionUID = -7847038228214946416L;

    private boolean start = false;
    JButton toggleBTN;
    JLabel ModeLBL, TimerLBL;
    JComboBox<String> Modes;
    JTextField TimeTXF;

    String[] Options = { "Left-Click", "Right-Click" };

    int timeMS = 500, mode = 0;

    public GUI() {
        create();
        design();
    }

    private void create() {

        ModeLBL = new JLabel("Mode :", 0);
        ModeLBL.setBounds(25, 25, 100, 25);
        ModeLBL.setBorder(null);

        Modes = new JComboBox<>(Options);
        Modes.setBounds(125, 25, 100, 25);

        TimerLBL = new JLabel("Timer :", 0);
        TimerLBL.setBounds(25, 75, 100, 25);
        TimerLBL.setBorder(null);

        TimeTXF = new JTextField("500");
        TimeTXF.setHorizontalAlignment(0);
        TimeTXF.setBackground(new Color(235, 235, 235));
        TimeTXF.setBounds(125, 75, 100, 25);
        TimeTXF.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));

        toggleBTN = new JButton("Start");
        toggleBTN.setBounds(25, 125, 200, 25);
        toggleBTN.setBackground(new Color(113, 220, 123));
        toggleBTN.setBorder(null);
        toggleBTN.addActionListener(e -> toggle());
    }

    private void design() {
        setTitle("MMH Auto Clicker");
        setLayout(null);
        setBounds(600, 300, 250 + 10, 175 + 35);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(ModeLBL);
        add(Modes);
        add(TimerLBL);
        add(TimeTXF);
        add(toggleBTN);
        setVisible(true);
    }

    private void toggle() {
        if (start) {
            toggleBTN.setBackground(new Color(113, 220, 123));
            toggleBTN.setText("Start");
            start = false;
            TimeTXF.setEditable(true);
        } else {
            select();
            toggleBTN.setBackground(new Color(218, 65, 65));
            toggleBTN.setText("Stop");
            start = true;
            TimeTXF.setEditable(false);
            runProgram();
        }
    }

    private void runProgram() {
        Thread t = new Thread(this);
        t.start();
    }

    private void select() {
        mode = Modes.getSelectedIndex();

        try {
            timeMS = Integer.parseInt(TimeTXF.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (start) {
                Thread.sleep(timeMS);

                if (!start) {
                    break;
                }

                Robot robot = new Robot();
                if (mode == 0) {
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    System.out.println("Clicked");
                } else if (mode == 1) {
                    robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                    System.out.println("right Clicked");
                }
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}
