package dailyWorks.p202311;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ContinuousClickWithShortcut {
    private int clickCount = 0;

    public ContinuousClickWithShortcut() {
        JFrame frame = new JFrame("연속 클릭 프로그램");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JButton clickButton = new JButton("클릭");
        JLabel clickCountLabel = new JLabel("클릭 횟수: " + clickCount);

        clickButton.setBounds(10, 10, 80, 30);
        clickCountLabel.setBounds(100, 10, 150, 30);

        frame.add(clickButton);
        frame.add(clickCountLabel);

        clickButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickCount++;
                clickCountLabel.setText("클릭 횟수: " + clickCount);
            }
        });

        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == 'c') {
                    // 'c' 키를 누르면 클릭 버튼을 자동으로 클릭
                    clickButton.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        frame.setSize(300, 100);
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.requestFocus();

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ContinuousClickWithShortcut();
            }
        });
    }
}