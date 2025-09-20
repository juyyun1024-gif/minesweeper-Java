// Written by Justin Yun yun00058, Ansh Kakkar kakka022
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
// Initializing Variables
        int dimensions = 0;
        int flags = 0;
        boolean boolFlag = false;
        // Defining Scanner
        Scanner scanner = new Scanner(System.in);
        String input;
        // Difficulty selection input
        // Difficulty check conditions
        while (true) {
            System.out.println("Choose the difficulty of your minesweeper game: (easy, medium, hard)");
            input = scanner.nextLine();
//        if (input.equals("easy")) {
//            // setting values of the array
//            dimensions = 5;
//            flags = 5;
//        }else if (input.equals("medium")) {
//            dimensions = 9;
//            flags = 12;
//        }else if (input.equals("hard")) {
//            dimensions = 20;
//            flags = 40;
//        }else {
//            // incorrect input exception
//            System.out.println("Please enter a valid value");
//        }
            if (input.equals("easy") || input.equals("medium") || input.equals("hard")) {
                break;
            } else {
                System.out.println("try again!");
            }
        }
        switch (input) {
            case "hard":
                dimensions = 20;
                flags = 40;
                break;
            case "easy":
                dimensions = 5;
                flags = 5;
                break;
            case "medium":
                dimensions = 9;
                flags = 9;
                break;
        }
        scanner = new Scanner(System.in);
        System.out.println("Debug mode (yes/no)");
        String debug = scanner.nextLine();


        // Initializing minefield with the predefined values from the conditional
        Minefield gameField = new Minefield(dimensions, dimensions, flags);
        // Second input for Starting coordinates
        System.out.println("Please give me a set of starting coordinates (X Y): ");
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        // Creating mines based of the previously set values
        gameField.createMines(x, y, flags);
        // Evaluate Field sets the numbers
        gameField.evaluateField();
        //Guess input
        if (debug.equals("yes")) {
            gameField.printMineField();
        } else {
            gameField.revealMines(x, y);
            System.out.println(gameField);


        }
        while (!gameField.gameOver()) {
            System.out.println("Please pick a coordinate & whether you would like to place a flag (X Y yes/no): ");
            x = scanner.nextInt();
            y = scanner.nextInt();
            String yNFlag = scanner.nextLine();
            yNFlag = yNFlag.trim(); // trim function was used because there was white space
            if (yNFlag.equals("yes")) {
                boolFlag = true;
            } else {
                boolFlag = false;
            }
            gameField.guess(x, y, boolFlag);

            if (debug.equals("yes")) {
                gameField.printMineField();
            } else {
                System.out.println(gameField);
            }
        }
        // when the game over, the character prints out with printed out board with everything shown on the board
        if (gameField.gameOver()) {
            System.out.println("YOu JuST acTIvaTeD my TRaP CaRD");
            System.out.println("⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
            System.out.println("⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
            System.out.println("⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠿⠛⠋⠉⠉⠉⠉⠉⠉⠛⠻⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
            System.out.println("⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠋⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
            System.out.println("⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠃⠀⠀⣀⣀⣀⠀⠀⠀⠀⠀⣀⣀⣀⡀⠀⠈⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
            System.out.println("⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⣼⣾⣿⠿⣿⣷⠀⠀⠀⣾⣿⠿⣿⣿⣦⡀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
            System.out.println("⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣆⠛⠿⣿⣶⣿⡟⠀⣴⡄⠙⣿⣷⣿⡿⠟⢳⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
            System.out.println("⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡟⠂⠀⣄⠚⠋⠀⠸⠿⢿⠀⠈⠓⢢⣄⠀⢻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
            System.out.println("⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣇⠈⠛⢯⡟⠷⣶⠦⡤⠤⣤⠤⡞⣿⠟⠃⣸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
            System.out.println("⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠏⠙⢿⣶⣀⠀⠉⠲⢾⣤⣧⣤⣿⡴⠟⠁⢀⣴⠿⠁⣠⣽⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
            System.out.println("⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣶⣦⣤⣉⠻⣷⣶⣦⣤⣶⣶⣶⣦⣴⣶⣾⠿⠃⣤⣾⣿⣟⠻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
            System.out.println("⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣫⣿⣿⡟⣿⣿⣷⣦⣝⡻⣷⣌⠉⢁⡾⠟⢻⣣⣤⣾⣿⡿⣿⣿⣷⡞⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
            System.out.println("⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⣽⣿⣿⣿⡇⣿⣿⣿⡟⣿⢻⣿⠉⠉⢻⡿⠚⣻⣿⣿⣿⠟⠀⣿⣿⣿⣿⡆⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
            System.out.println("YoU LOse!!!");
            gameField.printMineField();
        }
    }
}
