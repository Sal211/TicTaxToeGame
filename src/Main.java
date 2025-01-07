import java.util.Objects;
import java.util.Scanner;

public class Main {

    private static final String EMPTY = " ";
    private static final String[][] board = new String[3][3];
    private static final String ERROR = "Invalid option,Please try again...";
    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        int opt = menu();
        optionGame(opt);
    }

    private static int menu() {
        int opt;
        try {
            System.out.println("<><><><><><>><> Welcome to Tic Tac Toe! <><><><><><><><><><>");
            System.out.println("Choose Game Mode:");
            System.out.println("1. User vs User");
            System.out.println("2. User vs Computer");
            System.out.println("3. Exit");
            System.out.print("Enter your option: ");
            opt = input.nextInt();
            opt = validateOption(opt);
        } catch (Exception e) {
            System.out.println(ERROR);
            opt = validateOption(999);
        }
        return opt;
    }

    private static int validateOption(int opt) {
        while (opt > 3 || opt <= 0) {
            System.out.println(ERROR);
            System.out.print("Enter your option: ");
            opt = input.nextInt();
        }
        return opt;
    }

    private static void display() {
        for (int col = 0; col < 3; col++) {
            System.out.print("   " + col);
        }
        System.out.println();
        for (int i = 0; i < 3; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < 3; j++) {
                System.out.print(" " + board[i][j] + " ");
                if (j < 2) System.out.print("|");
            }
            System.out.println();
            if (i < 2) System.out.println("  ---+---+---");
        }
    }

    private static void setDefaultBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    private static void playUserVsCpt() {
    }
    private static void playUserVsUser() {}
    private static void play() {
        String currentPlayer = playerOption();
        int numOfMove = 0;
        setDefaultBoard();

        while (!isEndGame()) {
            display();
            System.out.print("Player " + currentPlayer + ", enter your move (row and column) : ");
            int row = input.nextInt();
            int col = input.nextInt();

            currentPlayer = playerMove(row, col, currentPlayer);
            numOfMove++;

            if (numOfMove >= 5 && isHaveWinner(setCurrentPlayer(currentPlayer))) {
                display();
                System.out.println("Player " + setCurrentPlayer(currentPlayer) + " wins!");
                return;
            }
        }
        display();
        System.out.println("It's a draw!");
    }

    private static String playerOption() {
        System.out.print("Player 1, please select your symbol (X or O): ");
        String currentPlayer = input.next();
        System.out.println("Player 1 has chosen: " + currentPlayer + ". Player 2 will be: " + setCurrentPlayer(currentPlayer) + ".");
        System.out.println("The game begins now. Good luck!");
        return currentPlayer;
    }

    private static boolean isEndGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].equals(EMPTY)) return false;
            }
        }
        return true;
    }

    private static String playerMove(int row, int col, String currentPlayer) {
        if (!Objects.equals(board[row][col], EMPTY)) {
            System.out.println("Invalid move! Try again.");
            return currentPlayer;
        }
        board[row][col] = currentPlayer;
        return setCurrentPlayer(currentPlayer);
    }

    private static void optionGame(int opt) {
        do {
            setDefaultBoard();
            switch (opt) {
                case 1:
                    playUserVsUser();
                    break;
                case 2:
                    playUserVsCpt();
                    break;
                case 3:
                    return;
                default:
                    opt = validateOption(999);
            }
        } while (opt != 3);
    }

    private static String setCurrentPlayer(String currentPlayer) {
        return currentPlayer.equals("X") ? "O" : "X";
    }

    private static boolean isHaveWinner(String currentPlayer) {
        if (board[0][0].equals(currentPlayer) && board[1][1].equals(currentPlayer)
                && board[2][2].equals(currentPlayer)) return true;

        if (board[0][2].equals(currentPlayer) && board[1][1].equals(currentPlayer)
                && board[2][0].equals(currentPlayer)) return true;

        for (int i = 0; i < 3; i++) {
            if (board[i][0].equals(currentPlayer) && board[i][1].equals(currentPlayer) && board[i][2].equals(currentPlayer))
                return true;
            if (board[0][i].equals(currentPlayer) && board[1][i].equals(currentPlayer) && board[2][i].equals(currentPlayer))
                return true;
        }
        return false;
    }
}