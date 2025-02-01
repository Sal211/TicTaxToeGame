import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final String EMPTY = " ";
    private static String[][] board = new String[3][3];
    private static final String ERROR = "Invalid input,Please try again...";
    private static final Scanner input = new Scanner(System.in);
    private static final Random random = new Random();
    private static final String[] totalCell = {"0,0", "0,1", "0,2", "1,0", "1,1", "1,2", "2,0", "2,1", "2,2"};

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
        while (opt > 3 || opt < 1) {
            try {
                System.out.println(ERROR);
                System.out.print("Enter your option: ");
                opt = input.nextInt();
            } catch (Exception _) {
                input.nextLine();
            }
        }
        return opt;
    }

    private static void display(boolean isCell, int numOfMove) {
        System.out.println("===============================");
        if (isCell && numOfMove == 0) {
            board = new String[][]{
                    {"1", "2", "3"},
                    {"4", "5", "6"},
                    {"7", "8", "9"}
            };
        } else if (!isCell) {
            System.out.println("   0   1   2");
        }

        for (int i = 0; i < 3; i++) {
            if (!isCell) System.out.print(i + " ");
            for (int j = 0; j < 3; j++) {
                System.out.print(" " + board[i][j] + " ");
                if (j < 2) System.out.print("|");
            }
            System.out.println();
            if (i < 2) System.out.println(isCell ? "---+---+---" : "  ---+---+---");
        }
    }

    private static void setDefaultBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    private static String rowOrColMove(boolean isPlayWithCpt, int numOfMove, String currentPlayer, String player) {
        int row, col;
        if (isPlayWithCpt && numOfMove % 2 != 0 && !Objects.equals(currentPlayer, player)) {
            // PLAYER IS CPT
            do {
                row = random.nextInt(3);
                col = random.nextInt(3);
            } while (!board[row][col].equals(EMPTY));
        } else {
            // PLAYER IS USER
            row = col = -1;
            while ((row > 2 || row < 0) || (col > 2 || col < 0)) {
                try {
                    System.out.print("Player " + player + ", enter your move (row and column) : ");
                    row = input.nextInt();
                    col = input.nextInt();
                    if((row > 2 || row < 0) || (col > 2 || col < 0)) throw new Exception("999");
                } catch (Exception e) {
                    System.out.println(ERROR);
                    input.nextLine();
                }
            }
        }

        // PLAYER MOVE
        return playerMove(row, col, player);
    }

    private static String cellMove(boolean isPlayWithCpt, int numOfMove, String currentPlayer, String player) {
        int row, col, cell;
        if (isPlayWithCpt && numOfMove % 2 != 0 && !Objects.equals(currentPlayer, player)) {
            // PLAYER IS CPT
            do {
                cell = random.nextInt(1,10);
                row = Integer.parseInt(totalCell[cell].split(",")[0]);
                col = Integer.parseInt(totalCell[cell].split(",")[1]);
            } while (!board[row][col].matches("[1-9]"));
        } else {
            // PLAYER IS USER
            cell = -1;
            while (cell < 1 || cell > 9) {
                try {
                    System.out.print("Player " + player + ", enter your move (cell) : ");
                    cell = input.nextInt();
                    if(cell < 1 || cell > 9) throw new Exception("999");
                } catch (Exception e) {
                    System.out.println(ERROR);
                    input.nextLine();
                }
            }
        }

        // PLAYER MOVE
        return playerMove(cell - 1, player);
    }

    private static boolean playGame(boolean isPlayWithCpt) {
        boolean isHaveWon = false;
        int numOfMove = 0;

        // GET SYMBOL OF PLAYER
        String player = playerOption(isPlayWithCpt);

        // GET MOVE OPTION
        int moveOpt = moveOption();
        if (moveOpt == 3) return false;
        boolean isCell = moveOpt == 2;

        if (!isCell) setDefaultBoard();
        final String currentPlayer = player;

        do {
            display(isCell,numOfMove);
            player = isCell ? cellMove(isPlayWithCpt, numOfMove, currentPlayer, player) : rowOrColMove(isPlayWithCpt, numOfMove, currentPlayer, player);
            numOfMove++;

            // IF PLAYER WIN
            if (numOfMove >= 5 && isHaveWinner(setPlayer(player))) {
                display(isCell,numOfMove);
                System.out.println("Player " + setPlayer(player) + " wins!");
                isHaveWon = true;
                break;
            }
        } while (!isEndGame());

        // IF PLAYER DRAW
        if (!isHaveWon) {
            display(isCell,numOfMove);
            System.out.println("It's a draw!");
        }

        return true;
    }

    private static int moveOption() {
        int opt;
        System.out.println("========> Move option <=======");
        System.out.println("1. Row & Column");
        System.out.println("2. Cell Number");
        System.out.println("3. Exit");
        do {
            try {
                System.out.print("Please select move option : ");
                opt = input.nextInt();
            } catch (Exception e) {
                input.nextLine();
                opt = 999;
            }
        } while (opt < 1 || opt > 3);
        return opt;
    }

    private static String playerOption(boolean isPlayWithCpt) {
        String currentPlayer;
        boolean isInvalidInput = false;

        // PLAY WITH CPT
        if (isPlayWithCpt) {
            currentPlayer = random.nextInt(2) == 1 ? "O" : "X";
            System.out.println("Your symbol is : " + currentPlayer + ", My symbol is :  " + setPlayer(currentPlayer));
            System.out.println("The game begins now. Good luck!");
            return currentPlayer;
        }

        // PLAY WITH USER
        do {
            if (isInvalidInput) System.out.println("Please select only symbol (X or O) !!!");
            System.out.print("Player 1, please select your symbol (X or O): ");
            currentPlayer = input.next();
            isInvalidInput = true;
        } while (!currentPlayer.equalsIgnoreCase("X") && !currentPlayer.equalsIgnoreCase("O"));

        System.out.println("Player 1 has chosen: " + currentPlayer + ". Player 2 will be: " + setPlayer(currentPlayer) + ".");
        System.out.println("The game begins now. Good luck!");
        return currentPlayer;
    }

    private static boolean isEndGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].equals(EMPTY) || board[i][j].matches("[1-9]")) return false;
            }
        }
        return true;
    }

    private static String playerMove(int row, int col, String player) {
        if (!Objects.equals(board[row][col], EMPTY)) {
            System.out.println("Invalid move! Try again.");
            return player;
        }
        board[row][col] = player.toUpperCase();
        return setPlayer(player);
    }

    private static String playerMove(int cell, String player) {
        int row = Integer.parseInt(totalCell[cell].split(",")[0]);
        int col = Integer.parseInt(totalCell[cell].split(",")[1]);

        if (!board[row][col].matches("[1-9]")) {
            System.out.println("Invalid move! Try again.");
            return player;
        }

        board[row][col] = player.toUpperCase();
        return setPlayer(player);
    }

    private static void optionGame() {
        int opt;
        boolean isExit;

        do {
            opt = menu();
            // EXIT
            if (opt == 3) return;

            isExit = switch (opt) {
                case 1 -> playGame(false);
                case 2 -> playGame(true);
                default -> playGame(true);
            };
        } while (isExit);
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