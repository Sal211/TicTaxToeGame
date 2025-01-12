import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final String EMPTY = " ";
    private static final String[][] board = new String[3][3];
    private static final String ERROR = "Invalid input,Please try again...";
    private static Scanner input = new Scanner(System.in);
    private static final Random random = new Random();

    public static void main(String[] args) {
        optionGame();
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
            opt = validateOption(999);
        }
        return opt;
    }

    private static int validateOption(int opt) {
        while (opt > 3 || opt <= 0) {
            try {
                input = new Scanner(System.in);
                System.out.println(ERROR);
                System.out.print("Enter your option: ");
                opt = input.nextInt();
            }catch (Exception _){}
        }
        return opt;
    }

    private static void display() {
        System.out.println("=============================================================");
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

    private static void playGame(Boolean isPlayWithCpt) {
        boolean isHaveWon = false,isInvalidInput;
        int numOfMove = 0,row = 0,col = 0;
        setDefaultBoard();
        String player = playerOption();
        final String currentPlayer = player;

        while (!isEndGame()) {
            display();
            if(isPlayWithCpt && numOfMove%2 != 0 && !Objects.equals(currentPlayer, player)){
                // PLAYER IS CPT
                do{
                    row = random.nextInt(3);
                    col = random.nextInt(3);
                }while (!Objects.equals(board[row][col], EMPTY));
            }else{
                // PLAYER IS USER
                do{
                    try {
                        input = new Scanner(System.in);
                        System.out.print("Player " + player + ", enter your move (row and column) : ");
                        row = input.nextInt();
                        col = input.nextInt();
                        if((row > 3 || row < 0) || (col > 3 || col < 0)) throw new Exception("error");
                        isInvalidInput = false;
                    }catch (Exception e){
                        isInvalidInput = true;
                        System.out.println(ERROR);
                    }
                }while (isInvalidInput);

            }

            // PLAYER MOVE
            player = playerMove(row, col, player);
            numOfMove++;

            // IF PLAYER WIN
            if (numOfMove >= 5 && isHaveWinner(setPlayer(player))) {
                display();
                System.out.println("Player " + setPlayer(player) + " wins!");
                isHaveWon = true;
                break;
            }
        }

        // IF PLAYER DRAW
        if(!isHaveWon) {
            display();
            System.out.println("It's a draw!");
        }
    }

    private static String playerOption() {
        String currentPlayer;
        boolean isInvalidInput = false;
        do{
            if(isInvalidInput) System.out.println("Please select only symbol (X or O) !!!");
            System.out.print("Player 1, please select your symbol (X or O): ");
            currentPlayer = input.next();
            isInvalidInput = true;
        }while (!currentPlayer.equals("X") && !currentPlayer.equals("O"));

        System.out.println("Player 1 has chosen: " + currentPlayer + ". Player 2 will be: " + setPlayer(currentPlayer) + ".");
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

    private static String playerMove(int row, int col, String player) {
        if (!Objects.equals(board[row][col], EMPTY)) {
            System.out.println("Invalid move! Try again.");
            return player;
        }
        board[row][col] = player;
        return setPlayer(player);
    }

    private static void optionGame() {
        int opt;
        do {
            opt = menu();
            setDefaultBoard();
            switch (opt) {
                case 1:
                    playGame(false);
                    break;
                case 2:
                    playGame(true);
                    break;
                case 3:
                    System.out.println("Good Bye!!!");
                    return;
            }
        } while (true);
    }

    private static String setPlayer(String currentPlayer) {
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