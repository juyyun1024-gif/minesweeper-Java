// Written by Justin Yun yun00058, Ansh Kakkar kakka022
import java.util.Random;

public class Minefield {
    /**
    Global Section
    */
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE_BRIGHT = "\u001b[34;1m";
    public static final String ANSI_BLUE = "\u001b[34m";
    public static final String ANSI_RED_BRIGHT = "\u001b[31;1m";
    public static final String ANSI_RED = "\u001b[31m";
    public static final String ANSI_GREEN = "\u001b[32m";
    public static final String ANSI_GREY_BG = "\u001b[0m";
    private int flags = 0;
    private Cell[][] mineFieldArray;

    // isRunning is the boolean that checks whether the game is running. If it's true, the game is running
    private boolean isRunning = true;
    /**
     * Constructor
     * @param rows       Number of rows.
     * @param columns    Number of columns.
     * @param flags      Number of flags, should be equal to mines
     */
    public Minefield(int rows, int columns, int flags) {
        // Create a new Cell object for each cell in the minefield and initialize its state to false and "-".
        mineFieldArray = new Cell[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j <columns; j++) {
                mineFieldArray[i][j] = new Cell(false, "-");
            }
        }
        // Set the number of flags
        this.flags = flags;
    }
    /**
     * evaluateField
     *
     * @function When a mine is found in the field, calculate the surrounding 9x9 tiles values. If a mine is found, increase the count for the square.
     */
    public void evaluateField() {
        // Loop through each cell in the minefield
        for (int i = 0; i < mineFieldArray.length; i++) {
            for (int j = 0; j < mineFieldArray[0].length; j++) {
                // If the cell contains a mine
                if (mineFieldArray[i][j].getStatus().equals("M")) {
                    // Iterate over each neighboring cell
                    for (int k = i - 1; k <= i + 1; k++) {
                        for (int l = j - 1; l <= j + 1; l++) {
                            if (k >= 0 && k < mineFieldArray.length && l >= 0 && l < mineFieldArray[0].length) {
                                if (mineFieldArray[k][l].getStatus().equals("M")) { // continues
                                } else if (mineFieldArray[k][l].getStatus().equals("-")) {// If the neighboring cell is empty, set its status to 1

                                    mineFieldArray[k][l].setStatus("1");
                                } else { // If the neighboring cell contains a number, increment it by 1
                                    int currentStatus = Integer.parseInt(mineFieldArray[k][l].getStatus());
                                    mineFieldArray[k][l].setStatus(Integer.toString(currentStatus + 1));
                                }
                            }
                        }
                    }
                }// If the cell is empty, set its status to 0
                else if (mineFieldArray[i][j].getStatus().equals("-")) {
                    mineFieldArray[i][j].setStatus("0");
                }
            }
        }
    }
    /**
     * createMines
     *
     * @param x       Start x, avoid placing on this square.
     * @param y        Start y, avoid placing on this square.
     * @param mines      Number of mines to place.
     */
    public void createMines(int x, int y, int mines) {
        Random rand = new Random();
        while(mines != 0) { // Loop until all mines have been placed
            // Generate a random x and y-coordinate for the mine
            int XCoor = rand.nextInt(mineFieldArray.length);
            int YCoor = rand.nextInt(mineFieldArray[0].length);
            if ((XCoor != x && YCoor != y) && !mineFieldArray[XCoor][YCoor].getStatus().equals("M") && !mineFieldArray[XCoor][YCoor].getRevealed()) {
                // Set the status of the cell to "M" to indicate a mine and decrement
                mineFieldArray[XCoor][YCoor].setStatus("M");
                mines--;
                }
            }
        }

    /**
     * guess
     *
     * @param x       The x value the user entered.
     * @param y       The y value the user entered.
     * @param flag    A boolean value that allows the user to place a flag on the corresponding square.
     * @return boolean Return false if guess did not hit mine or if flag was placed, true if mine found.
     */
    public boolean guess(int x, int y, boolean flag) {
        // Check that the x and y coordinates are within the bounds of the minefield array
        if ((x >= mineFieldArray.length || x < 0 || y < 0 || y >= mineFieldArray[0].length)) {
            return false;
        }
        // Check if the cell has already been revealed
        if (mineFieldArray[x][y].getRevealed()) {
            return false;
        }
        // If flag is used and there are still flags left to place
        if (flag && flags > 0) {
            mineFieldArray[x][y].setStatus("F");
            mineFieldArray[x][y].setRevealed(true);
            flags--;
            return true;
        }
        // If flag is not used and the cell contains a mine
        if (!flag && mineFieldArray[x][y].getStatus().equals("M")) {
            isRunning = false;
            return true;
        }
        else if(!flag) {
            // reveals the zeroes
            if (mineFieldArray[x][y].getStatus().equals("0")) {
                revealZeroes(x, y);
            } else {
                mineFieldArray[x][y].setRevealed(true);
            }
        } else { // If flag is true and there are no more flags left to place
            isRunning = false;
        }
        return true;
    }
    /**
     * gameOver
     *
     * @return boolean Return false if game is not over and squares have yet to be revealed, otheriwse return true.
     */
    public boolean gameOver() {
        if (isRunning) { // if the game is running
            for (int i = 0; i < mineFieldArray.length; i++) {
                for (int j = 0; j < mineFieldArray[0].length; j++) {
                    // if it's not revealed and if it's not mine, the game still proceeds
                    if (!mineFieldArray[i][j].getRevealed() && !mineFieldArray[i][j].getStatus().equals("M"))
                        return false;
                }
            }
        }// otherwise, the game must be over
        return true;
    }

    /**
     * revealField
     *
     * This method should follow the psuedocode given.
     * Why might a stack be useful here rather than a queue?
     *
     * @param x      The x value the user entered.
     * @param y      The y value the user entered.
     */
    public void revealZeroes(int x, int y) {
        // A new Stack object is created to keep track of cells that need to be revealed
        Stack1Gen<int[]> stack = new Stack1Gen<>();
        stack.push(new int[]{x, y});
        while (!stack.isEmpty()) { // The while loop iterates through the elements in the stack until it is empty
            int[] elements = stack.pop();
            mineFieldArray[elements[0]][elements[1]].setRevealed(true);
            // The current element is popped off the stack and its corresponding MineField object is set to "revealed"

            // These conditional statements check the adjacent cells of the current element
            // for "0" values that have not yet been revealed, and add them to the stack if found.
            if (elements[0] - 1 >= 0 && !mineFieldArray[elements[0] - 1][elements[1]].getRevealed() && mineFieldArray[elements[0] - 1][elements[1]].getStatus().equals("0")) {
                int[] temp = {elements[0] - 1, elements[1]};
                stack.push(temp);
            }
            if (elements[1] - 1 >= 0 && !mineFieldArray[elements[0]][elements[1] - 1].getRevealed() && mineFieldArray[elements[0]][elements[1] - 1].getStatus().equals("0")) {
                int[] temp = {elements[0], elements[1] - 1};
                stack.push(temp);
            }
            if (elements[1] + 1 < mineFieldArray.length && !mineFieldArray[elements[0]][elements[1] + 1].getRevealed() && mineFieldArray[elements[0]][elements[1] + 1].getStatus().equals("0")) {
                int[] temp = {elements[0], elements[1] + 1};
                stack.push(temp);
            }
            if (elements[0] + 1 < mineFieldArray.length && !mineFieldArray[elements[0] + 1][elements[1]].getRevealed() && mineFieldArray[elements[0] + 1][elements[1]].getStatus().equals("0")) {
                int[] temp = {elements[0] + 1, elements[1]};
                stack.push(temp);
            }
            // These nested for loops check all adjacent cells of the current element
            // for non-mine values and sets them to "revealed".
            for (int i = elements[0] - 1; i <= elements[0] + 1; i ++) {
                for (int j = elements[1] - 1; j <= elements[1] + 1; j ++) {
                    if (i >= 0 && i < mineFieldArray.length && j >= 0 && j < mineFieldArray[0].length) {
                        if (!mineFieldArray[i][j].getStatus().equals("M") && !mineFieldArray[i][j].getStatus().equals("0")) {
                            mineFieldArray[i][j].setRevealed(true);
                        }
                    }
                }
            }
        }
    }

    /**
     * revealMines
     *
     * This method should follow the psuedocode given.
     * Why might a queue be useful for this function?
     *
     * @param x     The x value the user entered.
     * @param y     The y value the user entered.
     */
    public void revealMines(int x, int y) {
        // Create a queue to store cells that need to be revealed
        Q1Gen<int[]> queue = new Q1Gen<>();
        queue.add(new int[] {x, y});
        // While the queue is not empty, continue to reveal cells
        while (queue.length() != 0) {
            int[] elements = queue.remove();
            // If the current cell is a zero, reveal all adjacent zeros
            if (mineFieldArray[elements[0]][elements[1]].getStatus().equals("0")) {
                revealZeroes(elements[0], elements[1]);
            }
            // If the current cell is not a mine, reveal it
            if (!mineFieldArray[elements[0]][elements[1]].getStatus().equals("M")) {
                mineFieldArray[elements[0]][elements[1]].setRevealed(true);
            }
            // If the current cell is a mine, stop revealing cells
            if (mineFieldArray[elements[0]][elements[1]].getStatus().equals("M")) {
                break;
            }
            // Check all adjacent cells to the current cell and add them to the queue if they are valid and haven't been revealed
            if (elements[0] - 1 >= 0) {
                int[] temp = {elements[0] - 1, elements[1]};
                queue.add(temp);
            }
            if (elements[1] - 1 >= 0) {
                int[] temp = {elements[0], elements[1] - 1};
                queue.add(temp);
            }
            if (elements[0] + 1 < mineFieldArray.length) {
                int[] temp = {elements[0] + 1, elements[1]};
                queue.add(temp);
            }
            if (elements[1] + 1 < mineFieldArray.length) {
                int[] temp = {elements[0], elements[1] + 1};
                queue.add(temp);
            }
        }
    }

    /**
     * printMinefield
     *
     * @fuctnion This method should print the entire minefield, regardless if the user has guessed a square.
     * *This method should print out when debug mode has been selected. 
     */
    public void printMineField() {
        // line 256~263 allows numbers for rows and columns
        System.out.print("\n ");
        for (int z = 0; z < mineFieldArray.length; z++) {
            System.out.print(z + " ");
        }
        System.out.println();
        for (int i = 0; i < mineFieldArray.length; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < mineFieldArray[i].length; j++) {
                // Assigns certain colors to the certain numbers, flags, mines symbol
                if (mineFieldArray[i][j].getStatus().equals("0")) {
                   System.out.print(ANSI_BLUE_BRIGHT + mineFieldArray[i][j].getStatus() + ANSI_GREY_BG + " ");
                } else if (mineFieldArray[i][j].getStatus().equals("1")) {
                    System.out.print(ANSI_YELLOW + mineFieldArray[i][j].getStatus() + ANSI_GREY_BG + " ");
                } else if (mineFieldArray[i][j].getStatus().equals("2")) {
                    System.out.print(ANSI_RED_BRIGHT + mineFieldArray[i][j].getStatus() + ANSI_GREY_BG + " ");
                } else if (mineFieldArray[i][j].getStatus().equals("3")) {
                    System.out.print(ANSI_BLUE + mineFieldArray[i][j].getStatus() + ANSI_GREY_BG + " ");
                } else if (mineFieldArray[i][j].getStatus().equals("M")) {
                    System.out.print(ANSI_GREEN + mineFieldArray[i][j].getStatus() + ANSI_GREY_BG + " ");
                } else {
                    System.out.print(ANSI_RED + mineFieldArray[i][j].getStatus() + ANSI_GREY_BG + " ");
                }
            }
           System.out.println();
       }
    }

    /**
     * toString
     *
     * @return String The string that is returned only has the squares that has been revealed to the user or that the user has guessed.
     */
    public String toString() {
        // allows to print entire mineFieldArray in strings, with row numbers and columns assigned.
        String result = "\n ";
        for (int i = 0; i < mineFieldArray.length; i++) {
            result += " " + i;
        }
        result += "\n";
        for (int i = 0; i < mineFieldArray.length; i++) {
            result += i + " ";
            for (int j = 0; j < mineFieldArray[i].length; j++) {
                if (mineFieldArray[i][j].getRevealed()) {
                    if (mineFieldArray[i][j].getStatus().equals("0")) {
                        result += (ANSI_BLUE_BRIGHT + mineFieldArray[i][j].getStatus() + ANSI_GREY_BG + " ");
                    } else if (mineFieldArray[i][j].getStatus().equals("1")) {
                        result += (ANSI_YELLOW + mineFieldArray[i][j].getStatus() + ANSI_GREY_BG + " ");
                    } else if (mineFieldArray[i][j].getStatus().equals("M")) {
                        result += (ANSI_GREEN + mineFieldArray[i][j].getStatus() + ANSI_GREY_BG + " ");
                    } else {
                        result += (ANSI_RED + mineFieldArray[i][j].getStatus() + ANSI_GREY_BG + " ");
                    }
                } else {
                    result += (ANSI_BLUE + "-" + ANSI_GREY_BG + " ");
                }
            }
            result += "\n";
        }
        return result;
    }
}
