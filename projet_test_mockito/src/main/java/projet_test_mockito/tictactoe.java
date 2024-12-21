package projet_test_mockito;


public class tictactoe {

    private char last_player = '\0';
    private Character[][] board = {{'\0', '\0', '\0'}, {'\0', '\0', '\0'}, {'\0', '\0', '\0'}};
    private String gameStatus = "Game still on ";
    private TicTacToeSave saveManager;
    private int turn = 0;

    public tictactoe(TicTacToeSave saveManager) throws Exception {
        this.saveManager = saveManager;
        initializeGame();
    }

    private void initializeGame() throws Exception {
        saveManager.clearDatabase(); // Clean old data
        saveManager.initializeDatabase("tic-tac-toe"); // Initialize the database
    }

    public String play(int x, int y) {
        checkAxis(x, "X");
        checkAxis(y, "Y");
        last_player = nextPlayer();
        setBox(x, y);

        turn++; // Increment the turn number
        Data move = new Data(turn, x, y, last_player);

        // Save the move
        boolean saved = saveManager.saveMove(move);
        if (!saved) {
        	throw new RuntimeException("Data saving failed");

        }

        Verify_Victory();
        if (Verify_Tie() && gameStatus.equals("Game still on ")) {
            gameStatus = "There is a Tie ";
        }
        return gameStatus;
    }

    private boolean Verify_Tie() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '\0') {
                    return false; // Found an empty cell, so it's not a tie
                }
            }
        }
        return true; // No empty cells, all are filled
    }

    private void Verify_Victory() {
        // Check horizontal rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != '\0' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                gameStatus = board[i][0] + " is the Winner";
                return; // Exit as soon as a winner is found
            }
        }

        // Check vertical columns
        for (int i = 0; i < 3; i++) {
            if (board[0][i] != '\0' && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                gameStatus = board[0][i] + " is the Winner";
                return; // Exit as soon as a winner is found
            }
        }

        // Check first diagonal (top-left to bottom-right)
        if (board[0][0] != '\0' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            gameStatus = board[0][0] + " is the Winner";
            return; // Exit as soon as a winner is found
        }

        // Check second diagonal (top-right to bottom-left)
        if (board[0][2] != '\0' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            gameStatus = board[0][2] + " is the Winner";
            return; // Exit as soon as a winner is found
        }
    }

    private void checkAxis(int axis, String axisname) {
        if (axis < 1 || axis > 3) {
            throw new RuntimeException(axisname + " is outside board");
        }
    }

    private void setBox(int x, int y) {
        if (board[x - 1][y - 1] != '\0') {
            throw new RuntimeException("Occupied Case");
        } else {
            board[x - 1][y - 1] = last_player;
        }
    }

    public char nextPlayer() {
        return (last_player == 'X') ? 'O' : 'X';
    }
}
