package algorithm_term_project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.ImageIcon;

public class CardGame extends JFrame {
	final int ROWS = 4;
	final int COLS = 4;
	Card[][] cards;
	int cursorRow, cursorCol;
	int moveCount = 0, try_num = 0;
	JLabel try_numLabel;
	int first_num, second_num;
	boolean first = true, second = false;
	int firstclickedrow, firstclickedcol, secondclickedrow, secondclickedcol;
	int resetnum, initialboard[][];
	int r, c;
	boolean end_flag = false;
	boolean entered = false;
	JLabel entered_num, Hint, clicked_number;
	Font font1 = new Font("����", Font.BOLD, 30);
	Font font2 = new Font("����", Font.PLAIN, 20);
	public CardGame() {
	    cards = new Card[ROWS][COLS];
	    initializeCards();

	    JPanel gridPanel = new JPanel(new GridLayout(ROWS, COLS));
	    for (int i = 0; i < ROWS; i++) {
	        for (int j = 0; j < COLS; j++) {
	            JButton button = cards[i][j].getButton();
	            button.setFont(font1);  // �� ��ư�� ��Ʈ ����
	            gridPanel.add(button);
	        }
	    }
	    
	    // Set the title for the JFrame
	    setTitle("ī�� ¦ ���߱� ����");
	    
	    //���� ���� font
	    Font middleSize=new Font("����", Font.PLAIN,15);
	    
	    // ���� label�� JFrame ��� �гο� �߰�
	    JLabel descriptionLabel = new JLabel("���� ����: �̵� Ƚ�� ���� ��� ī���� ¦�� ���纸����(����� : ��ĭ �̵�, ctrl + ����Ű : ī�尡 �ִ� ������ ����Ű �������� �̵� ������ ������.");
	    descriptionLabel.setFont(middleSize);
	    JPanel descriptionPanel = new JPanel();
	    descriptionPanel.add(descriptionLabel);
	    add(descriptionPanel, BorderLayout.NORTH);

	    gridPanel.setFocusable(true);
	    gridPanel.addKeyListener(new ArrowKeyListener());
	    add(gridPanel, BorderLayout.CENTER);  // �߾ӿ� �׸��� �г� �߰�

	    try_numLabel = new JLabel("Try:" + try_num);
	    entered_num = new JLabel("Input number:" + moveCount);
	    Hint = new JLabel("HINT: Soon open");
	    clicked_number = new JLabel("Choosed:");
	    clicked_number.setFont(font2);
	    try_numLabel.setFont(font2);
	    Hint.setFont(font2);
	    entered_num.setFont(font2);
	    // SOUTH�� �� ���̺��� �߰�
	    JPanel southPanel = new JPanel(new GridLayout(1, 3));
	    southPanel.add(try_numLabel);
	    southPanel.add(entered_num);
	    southPanel.add(clicked_number);
//	    southPanel.add(Hint);
	    add(southPanel, BorderLayout.SOUTH);

	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    pack();
	    setLocationRelativeTo(null);
	    setVisible(true);
	}



	void startlocation(int r, int c) {
		this.r = r;
		this.c = c;
		setInitialCursorPosition(r, c);
	}

	private void resetGame() {
		// �������� ī��� �ٽ� ������
		// Restore the initial state of the board
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				cards[i][j].setValue(initialboard[i][j]);
				cards[i][j].getButton().setBackground(UIManager.getColor("Button.background"));
			}
		}

		// Ŀ�� ��ġ �ʱ� ��ġ�� �̵�
		setInitialCursorPosition(r, c);
		System.out.println("Game reset!");
		try_num++;
		try_numLabel.setText("Try:" + try_num);
		moveCount = 0; // ������ Ƚ�� �ʱ�ȭ
		first_num = -1;
		second_num = -1;
		firstclickedrow = -1;
		firstclickedcol = -1;
		secondclickedcol = -1;
		secondclickedrow = -1;
		first = true;
		entered_num.setText("Input number:0");
		if(try_num > 2) {
			Hint.setText("Minimum number is " + resetnum);
			// ����Ǿ��� ���� ��� �ʱ�ȭ
		}
	}

	void saveinitialboardgame(int values[][]) {
		initialboard = values;
	}

	///
	void setBoardValues(int[][] values) {
		if (values.length != ROWS || values[0].length != COLS) {
			throw new IllegalArgumentException("Invalid dimensions for the values array");
		}

		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				cards[i][j].setValue(values[i][j]);
			}
		}
		saveinitialboardgame(values);
	}
	///

	private void highlightCursorPosition() {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				cards[i][j].getButton().setBackground(UIManager.getColor("Button.background"));
			}
		}
		cards[cursorRow][cursorCol].getButton().setBackground(Color.RED);
	}
	///

	void setInitialCursorPosition(int r, int c) {
		cursorRow = Math.max(0, Math.min(r, ROWS - 1));
		cursorCol = Math.max(0, Math.min(c, COLS - 1));
		cards[r][c].getButton().setBackground(Color.red);
		// cards[cursorRow][cursorCol].getButton().setBackground(Color.RED);
	}

	private void initializeCards() {
		int cardValue = 1;

		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				cards[i][j] = new Card(cardValue);
				cardValue++;
			}
		}
	}
    private void initializeCardsWithImages() {
        int cardValue = 1;

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                cards[i][j] = new Card(cardValue);
                cardValue++;
            }
        }
    }
    
	private class Card {
		private int value;
		private boolean faceUp;
		private JButton button;

		public Card(int value) {
			this.value = value;
			this.faceUp = false;

			button = new JButton();
			button.setPreferredSize(new Dimension(200, 200));

			////
			updateButton();
			////
		}

		/////
		// Inside the Card class
		public void setValue(int value) {
			this.value = value;
			updateButton();
		}

		///

		public JButton getButton() {
			return button;
		}

		public int getValue() {
			return value;
		}

		public boolean isFaceUp() {
			return faceUp;
		}

		public void flip() {
			faceUp = !faceUp;
			updateButton();
		}

        private void updateButton() {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    if (faceUp) {
                        // Load image for face-up state
                        String imagePath = "\\C:\\Users\\josun\\OneDrive\\����\\photo\\" + value + ".jpg"; // Replace with your image path
                        ImageIcon icon = new ImageIcon(imagePath);
                        button.setIcon(icon);
                    } else {
                        // Load image for face-down state
                        String imagePath = "\\C:\\Users\\josun\\OneDrive\\����\\photo\\" + value + ".JPG"; // Replace with your back image path
                        ImageIcon icon = new ImageIcon(imagePath);
                        button.setIcon(icon);
                    }
                }
            });
        }
		public int getnumber() {
			return value;
		}
	}

	private class ArrowKeyListener implements KeyListener {
		private boolean ctrlPressed = false;

		@Override
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			switch (keyCode) {
			case KeyEvent.VK_UP:
				moveCount++;
				moveCursor(-1, 0);
				break;
			case KeyEvent.VK_DOWN:
				moveCount++;
				moveCursor(1, 0);
				break;
			case KeyEvent.VK_LEFT:
				moveCount++;
				moveCursor(0, -1);
				break;
			case KeyEvent.VK_RIGHT:
				moveCount++;
				moveCursor(0, 1);
				break;
			case KeyEvent.VK_ENTER:
				moveCount++;
				onCardClick();
				break;
			case KeyEvent.VK_CONTROL:
				ctrlPressed = true;
				break;
			}
			if (moveCount >= resetnum) {
				if (!end_flag) {
					JOptionPane.showMessageDialog(null, "���� ���ФФ�");
				}
				resetGame(); // �ʱ�ȭ �޼��� ȣ��
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
				ctrlPressed = false;
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}

		private void moveCursor(int rowChange, int colChange) {

			if (ctrlPressed) {
				// Ctrl Ű�� ���� ���¿��� ȭ��ǥ Ű�� ������ ������ �̵�
				cursorRow = Math.max(0, Math.min(cursorRow + rowChange, ROWS - 1));
				cursorCol = Math.max(0, Math.min(cursorCol + colChange, COLS - 1));
				cards[cursorRow - rowChange][cursorCol - colChange].getButton()
						.setBackground(UIManager.getColor("Button.background"));
				//while != 0�̶�� ������ ������ ���� ��ġ�� 0�� �ƴ� ��쿡�� �ƿ� �������� �ʴ� ��찡 �߻��Ͽ���, �׷��⿡
				//�̵��� ��ġ�� ���� ������, �� ���� ��ġ�� Ż���� ������ �̵��� ������. �̴�
				//CardSolve�� nr,nc����� ����.
				while (cursorRow + rowChange >= 0 && cursorRow + rowChange < ROWS && cursorCol + colChange >= 0
						&& cursorCol + colChange < COLS) {
					if (cards[cursorRow][cursorCol].getnumber() != 0) {
						break;
					}
					cards[cursorRow][cursorCol].getButton().setBackground(UIManager.getColor("Button.background"));
					// Reset the color of the previous cursor position
					cursorRow += rowChange;
					cursorCol += colChange;
				}
				cards[cursorRow][cursorCol].getButton().setBackground(Color.RED); // Set the new cursor position to red
				entered_num.setText("Input number:" + moveCount);
			}

			else {
				// Ctrl Ű�� ������ ���� ���¿����� �Ϲ����� �̵� ����
				int newRow = Math.max(0, Math.min(cursorRow + rowChange, ROWS - 1));
				int newCol = Math.max(0, Math.min(cursorCol + colChange, COLS - 1));
				cards[cursorRow][cursorCol].getButton().setBackground(UIManager.getColor("Button.background")); // Reset the color of the previous cursor position
				cursorRow = newRow;
				cursorCol = newCol;
				cards[cursorRow][cursorCol].getButton().setBackground(Color.RED); // Set the new cursor position to red
				entered_num.setText("Input number:" + moveCount);
			}
			System.out.println(moveCount);
		}
	}

	private void onCardClick() {
		Card clickedCard = cards[cursorRow][cursorCol];
		System.out.println(moveCount);
		if (first) {
			first_num = clickedCard.getnumber();
			clickedCard.getButton().setBackground(Color.yellow);
			first = false;
			System.out.println("First number is " + first_num);
			firstclickedrow = cursorRow;
			firstclickedcol = cursorCol;
			entered = true;
			clicked_number.setText("Choosed: " + Integer.toString(first_num));
			entered_num.setText("Input number:" + moveCount);
		} else {
			second_num = clickedCard.getnumber();
			System.out.println("Second number is " + second_num);
			secondclickedrow = cursorRow;
			secondclickedcol = cursorCol;
			clicked_number.setText("Choosed: " + Integer.toString(second_num));
			entered_num.setText("Input number:" + moveCount);
			if (first_num == second_num) {
				if ((firstclickedrow == secondclickedrow) && (firstclickedcol == secondclickedcol)) {
					System.out.println("Same Position!");
					first_num = -1;
					second_num = -1;
					firstclickedrow = -1;
					firstclickedcol = -1;
					secondclickedcol = -1;
					secondclickedrow = -1;
					first = true;
					cards[firstclickedrow][firstclickedcol].getButton().setBackground(UIManager.getColor("Button.background"));
					entered_num.setText("Input number:" + moveCount);
					//���� ���� ��ġ�� ī�带 ���ٸ� �� ����..
				} else {
					clickedCard.getButton().setBackground(Color.yellow);
					System.out.println("Match! Numbers are the same.");
					cards[firstclickedrow][firstclickedcol].setValue(0);
					cards[secondclickedrow][secondclickedcol].setValue(0);
					first = true;
					game_end(cards);
					entered_num.setText("Input number:" + moveCount);
					//���� ��ġ�� �ƴ� �� ���� ���ڶ�� OK
				}
			} else {
				System.out.println("No match. Numbers are different.");
				first_num = -1;
				second_num = -1;
				firstclickedrow = -1;
				firstclickedcol = -1;
				secondclickedcol = -1;
				secondclickedrow = -1;
				first = true;
				cards[firstclickedrow][firstclickedcol].getButton().setBackground(UIManager.getColor("Button.background"));
				//�ٸ� ī�带 ����� ��
			}
		}
	}

	void resetVal(int reset) {
		this.resetnum = reset;
	}

	void game_end(Card cards[][]) {
		end_flag = true; // �ʱⰪ�� true�� ����

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (cards[i][j].getValue() != 0) {
					end_flag = false; // �ϳ��� 0�� �ƴ� ī�尡 ������ false�� �����ϰ� �ݺ��� ����
					break;
				}
			}
			if (!end_flag) {
				break;
			}
		}

		if (end_flag) {
			JOptionPane.showMessageDialog(null, "���� ����!");
		}
	}
}
