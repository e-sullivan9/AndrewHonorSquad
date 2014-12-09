package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import javax.swing.SwingUtilities;

/**
 * Interior Server side logic for connect 5 Andrew's Honors Students 12/7/14
 *
 * @author Eric Sullivan
 */
public class GameLogic {

    private int x, y;
    private int[][] board;
    protected Socket socket;
    protected Socket socket2;
    BufferedReader reader2;
    BufferedReader reader;
    BufferedWriter writer;
    BufferedWriter writer2;

    public GameLogic() {
        x = 0;
        y = 0;
        board = new int[15][15];
        System.out.println(board[0][0]);
    }

    /**
     * Sets the the socket
     *
     * @param socket
     * @param socket2
     */
    public void setSockets(Socket socket, Socket socket2) {
        this.socket = socket;
        this.socket2 = socket2;
        open();
    }

    /**
     * This method creates player turns and then readers and writes to the
     * client side logic. also calls the processInput method.
     */
    public void execute() {
        try {
            writer.write("1");
            writer.newLine();
            writer.flush();
            writer2.write("2");
            writer2.newLine();
            writer2.flush();

            String line3 = "";
            String line4 = "";
            while (line3.split(",").length != 4 && line4.split(",").length != 4) {
                String line = reader.readLine();
                line3 = processInput(line);

                writer.write(line3);
                writer.newLine();
                writer.flush();

                writer2.write(line3);
                writer2.newLine();
                writer2.flush();
                // System.out.println(line);
                System.out.println(line3);

                if (line3.split(",").length != 4) {
                  //  break;

                    String line2 = reader2.readLine();
                    line4 = processInput(line2);

                    writer.write(line4);
                    writer.newLine();
                    writer.flush();

                    writer2.write(line4);
                    writer2.newLine();
                    writer2.flush();
                    //System.out.println(line2);
                    System.out.println(line4);
                }

                //if(line3.split(",").length==4)
                //  break;
            }
            close();

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public void close() {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                try {
                    writer.close();
                    writer2.close();
                    reader.close();
                    reader2.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }

    public void open() {
        try {
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer2 = new BufferedWriter(new OutputStreamWriter(socket2.getOutputStream()));
            reader2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method takes the String from the client and checks if that client
     * has won the game
     *
     * @param line
     * @return line processed for client
     */
    private String processInput(String line) {
        String[] split = line.split(",");
        int num = Integer.parseInt(split[0]);
        x = Integer.parseInt(split[1]);
        y = Integer.parseInt(split[2]);
        board[x][y] = num;
        if (1 + checkLeft(num, x, y) + checkRight(num, x, y) >= 5) {
            return ("" + num + "," + x + "," + y + "," + "win");
        } else if (1 + checkUp(num, x, y) + checkDown(num, x, y) >= 5) {
            return ("" + num + "," + x + "," + y + "," + "win");
        } else if (1 + checkUpLeft(num, x, y) + checkDownRight(num, x, y) >= 5) {
            return ("" + num + "," + x + "," + y + "," + "win");
        } else if (1 + checkUpRight(num, x, y) + checkDownLeft(num, x, y) >= 5) {
            return ("" + num + "," + x + "," + y + "," + "win");
        } else {
            return ("" + num + "," + x + "," + y);
        }
    }

    /**
     * recursive method that checks to the left of the current point in the d2
     * array.
     *
     * @param num
     * @param i
     * @param y
     * @return
     */

    private int checkLeft(int num, int i, int y) {
        System.out.print("left: " + board[i][y]);
        if (i - 1 >= 0 && board[i - 1][y] == num) {
            System.out.print("left: " + board[i][y]);
            return checkLeft(num, i - 1, y) + 1;

        } else {
            return 0;
        }
    }

    /**
     * recursive method that checks to the right of the current point in the d2
     * array.
     *
     * @param num
     * @param i
     * @param y
     * @return
     */
    private int checkRight(int num, int i, int y) {
        if (i + 1 <= 14 && board[i + 1][y] == num) {
            System.out.print("Right: " + board[i][y]);
            return checkRight(num, i + 1, y) + 1;
        } else {
            return 0;
        }
    }

    /**
     * recursive method that checks to the up of the current point in the d2
     * array.
     *
     * @param num
     * @param i
     * @param y
     * @return
     */
    private int checkUp(int num, int i, int y) {
        if (y + 1 <= 14 && board[i][y + 1] == num) {
            return checkUp(num, i, y + 1) + 1;
        } else {
            return 0;
        }
    }

    /**
     * recursive method that checks to the down of the current point in the d2
     * array.
     *
     * @param num
     * @param i
     * @param y
     * @return
     */
    private int checkDown(int num, int i, int y) {
        if (y - 1 >= 0 && board[i][y - 1] == num) {
            return checkDown(num, i, y - 1) + 1;
        } else {
            return 0;
        }
    }

    /**
     * recursive method that checks to the top left of the current point in the
     * d2 array.
     *
     * @param num
     * @param i
     * @param y
     * @return
     */
    private int checkUpLeft(int num, int i, int y) {
        if (y - 1 >= 0 && i - 1 >= 0 && board[i - 1][y - 1] == num) {
            return checkUpLeft(num, i - 1, y - 1) + 1;
        } else {
            return 0;
        }
    }

    /**
     * recursive method that checks to the bottom left of the current point in
     * the d2 array.
     *
     * @param num
     * @param i
     * @param y
     * @return
     */
    private int checkDownLeft(int num, int i, int y) {
        if (i + 1 <= 14 && y - 1 >= 0 && board[i + 1][y - 1] == num) {
            return checkDownLeft(num, i + 1, y - 1) + 1;
        } else {
            return 0;
        }
    }

    /**
     * recursive method that checks to the top right of the current point in the
     * d2 array.
     *
     * @param num
     * @param i
     * @param y
     * @return
     */
    private int checkUpRight(int num, int i, int y) {
        if (i - 1 >= 0 && y + 1 <= 14 && board[i - 1][y + 1] == num) {

            return checkUpRight(num, i - 1, y + 1) + 1;
        } else {
            return 0;
        }
    }

    /**
     * recursive method that checks to the bottom right of the current point in
     * the d2 array.
     *
     * @param num
     * @param i
     * @param y
     * @return
     */
    private int checkDownRight(int num, int i, int y) {
        if (i + 1 <= 14 && y + 1 <= 14 && board[i + 1][y + 1] == num) {
            return checkDownRight(num, i + 1, y + 1) + 1;
        } else {
            return 0;
        }
    }
}
