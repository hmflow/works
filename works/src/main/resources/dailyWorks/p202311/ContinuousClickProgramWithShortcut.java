package dailyWorks.p202311;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ContinuousClickProgramWithShortcut {
	private boolean flg = false;

	public ContinuousClickProgramWithShortcut() {
		JFrame frame = new JFrame("연속 클릭 프로그램");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new FlowLayout());

		frame.addKeyListener(new KeyListener() { 
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE) { // Space 키를 단축키로 사용
					Robot robot;
					try {

						robot = new Robot();
						while(true) {
							robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
							robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
						}
					} catch (AWTException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					// 클릭을 원하는 횟수만큼 반복

				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});

		frame.setFocusable(true);
		frame.requestFocus();

		frame.setSize(300, 100);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new ContinuousClickProgramWithShortcut());
	}
}