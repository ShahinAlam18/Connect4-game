import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectFourGame extends JFrame {

    private static final int ROWS = 6;
    private static final int COLS = 7;
    private JButton[][] buttons;
    private char[][] board;
    private char currentPlayer;

    public ConnectFourGame() {
        setTitle("Connect Four");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buttons = new JButton[ROWS][COLS];
        board = new char[ROWS][COLS];
        currentPlayer = 'X';

        initializeBoard();
        createGUI();
    }

    private void initializeBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                board[row][col] = ' ';
            }
        }
    }

    private void createGUI() {
        setLayout(new GridLayout(ROWS + 1, COLS));

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                buttons[row][col] = new JButton();
                buttons[row][col].setBackground(Color.WHITE);
                buttons[row][col].setPreferredSize(new Dimension(80, 80));
                buttons[row][col].addActionListener(new ButtonClickListener(row, col));
                add(buttons[row][col]);
            }
        }

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ResetButtonListener());
        add(resetButton);

        setVisible(true);
    }

    private void makeMove(int row, int col) {
        if (board[row][col] == ' ') {
            board[row][col] = currentPlayer;
            buttons[row][col].setText(String.valueOf(currentPlayer));
            buttons[row][col].setEnabled(false);

            if (checkWinner(row, col)) {
                String winner = (currentPlayer == 'X') ? "Player X" : "Player O";
                JOptionPane.showMessageDialog(this, winner + " wins!");
                resetGame();
            } else {
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            }
        }
    }

    private boolean checkWinner(int row, int col) {
        char symbol = board[row][col];

        // Check horizontally
        for (int i = 0; i <= COLS - 4; i++) {
            if (board[row][i] == symbol && board[row][i + 1] == symbol &&
                board[row][i + 2] == symbol && board[row][i + 3] == symbol) {
                return true;
            }
        }

        // Check vertically
        for (int i = 0; i <= ROWS - 4; i++) {
            if (board[i][col] == symbol && board[i + 1][col] == symbol &&
                board[i + 2][col] == symbol && board[i + 3][col] == symbol) {
                return true;
            }
        }

        // Check diagonally (from top-left to bottom-right)
        for (int i = 0; i <= ROWS - 4; i++) {
            for (int j = 0; j <= COLS - 4; j++) {
                if (board[i][j] == symbol && board[i + 1][j + 1] == symbol &&
                    board[i + 2][j + 2] == symbol && board[i + 3][j + 3] == symbol) {
                    return true;
                }
            }
        }

        // Check diagonally (from top-right to bottom-left)
        for (int i = 0; i <= ROWS - 4; i++) {
            for (int j = COLS - 1; j >= 3; j--) {
                if (board[i][j] == symbol && board[i + 1][j - 1] == symbol &&
                    board[i + 2][j - 2] == symbol && board[i + 3][j - 3] == symbol) {
                    return true;
                }
            }
        }

        return false;
    }

    private void resetGame() {
        initializeBoard();
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                buttons[row][col].setText("");
                buttons[row][col].setEnabled(true);
            }
        }
        currentPlayer = 'X';
    }

    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;
        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            makeMove(row, col);
        }
    }

    private class ResetButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            resetGame();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConnectFourGame());
    }
}
