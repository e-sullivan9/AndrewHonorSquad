package Client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class GameBoard extends JFrame implements ActionListener, Runnable {

    private ImageIcon terran = new ImageIcon(getClass().getResource("terran.jpg"));
    private ImageIcon zerg = new ImageIcon(getClass().getResource("zerg.jpg"));
    private ImageIcon zergBanner = new ImageIcon(getClass().getResource("zergBanner.png"));
    private ImageIcon terranBanner = new ImageIcon(getClass().getResource("terranBanner.png"));
    private ImageIcon zergTurn = new ImageIcon(getClass().getResource("zergTurn.jpg"));
    private ImageIcon terranTurn = new ImageIcon(getClass().getResource("terranTurn.jpg"));
    private ImageIcon backgroundImage = new ImageIcon(getClass().getResource("wallpaper.jpg"));
    private ImageIcon blue = new ImageIcon(getClass().getResource("blue_overlay.jpg"));
    private ImageIcon currentTurnImage = terranTurn;
    private ImageIcon PlayerMarker;
    private boolean isPlaying = true;
    private String input = "";
    private int player;
    private int current = 1;
    Client client;

    JFrame frame;
    JPanel topPanel;
    JMenuBar menuBar;
    JMenu menu, help, rush;
    JMenuItem NewGame;
    JPanel mainPanel;
    JPanel teamBanner;
    JPanel gameBoard;
    JButton[][] boardSquares;
    JPanel turnPanel;
    JLabel currentPlayer;
    JLabel currentTurnLabel;
    JLabel turn;
    JButton turnImage;
    JPanel chatPanel;
    JTextField enterField;
    JTextArea displayArea;
    JOptionPane joption;
    JOptionPane jowinner;
    JFrame waitingScreen = new JFrame();
    JLabel background = new JLabel(backgroundImage);

    public GameBoard() {
        try {
            waitingScreen.add(background);
            waitingScreen.setVisible(true);
            waitingScreen.setSize(1280, 720);
            waitingScreen.setLocationRelativeTo(null);
            String customip
                    = joption.showInputDialog("Leave Empty for localHost\n" + "or Enter a custom IP and go on an adventure");
            client = new Client(customip);
            player = Integer.parseInt(client.receive());
            waitingScreen.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void drawGUI() {

        JFrame frame = new JFrame("Connect 5 Game Window");
        frame.setVisible(true);
        frame.setSize(900, 950);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        //MENU BAR 
        //Worthless menu that can have added features 
        //such as starting a game, Learning that there is no help availible,
        //or Zerg Rush for win
        topPanel = new JPanel(new BorderLayout());

        menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        menuBar.add(menu);
        help = new JMenu("Help");
        menuBar.add(help);
        rush = new JMenu("Zerg Rush");
        menuBar.add(rush);

        NewGame = new JMenuItem("Start a new Game");
        menu.add(NewGame);
        NewGame = new JMenuItem("There is no help...");
        help.add(NewGame);
        NewGame = new JMenuItem("Win");
        rush.add(NewGame);

        //Menu Panel
        mainPanel = new JPanel(new BorderLayout());

        //TeamBannerpanel
        teamBanner = new JPanel(new BorderLayout());
        teamBanner.setSize(900, 50);
        JLabel banner = new JLabel();
        if (player == 2) {
            banner.setIcon(zergBanner);
        }
        if (player == 1) {
            banner.setIcon(terranBanner);
        }
        teamBanner.add(banner);

        topPanel.add(menuBar, BorderLayout.NORTH);
        topPanel.add(teamBanner, BorderLayout.SOUTH);
        //Gameboard
        gameBoard = new JPanel();
        gameBoard.setLayout(new GridLayout(15, 15, 1, 1));
        boardSquares = new JButton[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                boardSquares[i][j] = new JButton();
                boardSquares[i][j].setPreferredSize(new Dimension(45, 45));
                boardSquares[i][j].setIcon(blue);
                boardSquares[i][j].putClientProperty("row", i);
                boardSquares[i][j].putClientProperty("col", j);
                boardSquares[i][j].addActionListener(this);
                gameBoard.add(boardSquares[i][j]);
            }
        }

        //TurnPanel
        turnPanel = new JPanel(new BorderLayout());
        turnPanel.setSize(50, 675);
        turnPanel.setBackground(Color.white);
        currentPlayer = new JLabel("Current Turn");
        currentPlayer.setHorizontalAlignment(SwingConstants.CENTER);
        turn = new JLabel(currentTurnImage);
        turn.setSize(224, 358);
        currentTurnLabel = new JLabel(new ImageIcon(getClass().getResource("pylon1.jpg")));

        turnPanel.add(currentPlayer, BorderLayout.NORTH);
        turnPanel.add(turn, BorderLayout.CENTER);
        turnPanel.add(currentTurnLabel, BorderLayout.SOUTH);

        //ChatPanel
        chatPanel = new JPanel(new BorderLayout());
        enterField = new JTextField();
        chatPanel.add(enterField, BorderLayout.NORTH);
        displayArea = new JTextArea(5, 5);
        displayArea.append("The Protoss have disable all communication...\n How sad...");
        chatPanel.add(new JScrollPane(displayArea), BorderLayout.SOUTH);
        chatPanel.setBackground(Color.blue);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(gameBoard, BorderLayout.WEST);
        mainPanel.add(turnPanel, BorderLayout.EAST);
        mainPanel.add(chatPanel, BorderLayout.SOUTH);
        frame.add(mainPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
    }

//search for button
    public void run() {

        while (isPlaying) {
            try {
                input = client.receive();
                System.out.println(input);
                processInput();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public synchronized void processInput() {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {

                String[] s = input.split(",");
                System.out.println("s length " + s.length);
                System.out.println(Arrays.toString(s));
                if (boardSquares[Integer.parseInt(s[1])][Integer.parseInt(s[2])].getIcon().equals(blue)) {

                    //find button
                    if (Integer.parseInt(s[0]) == 1) {
                        boardSquares[Integer.parseInt(s[1])][Integer.parseInt(s[2])].setIcon(terran);
                        boardSquares[Integer.parseInt(s[1])][Integer.parseInt(s[2])].setDisabledIcon(terran);
                        boardSquares[Integer.parseInt(s[1])][Integer.parseInt(s[2])].setEnabled(false);
                        turn.setIcon(zergTurn);

                    }
                    if (Integer.parseInt(s[0]) == 2) {
                        boardSquares[Integer.parseInt(s[1])][Integer.parseInt(s[2])].setIcon(zerg);
                        boardSquares[Integer.parseInt(s[1])][Integer.parseInt(s[2])].setDisabledIcon(zerg);
                        boardSquares[Integer.parseInt(s[1])][Integer.parseInt(s[2])].setEnabled(false);
                        turn.setIcon(terranTurn);

                    }

                    //change button by player
                    if (current == 1) {
                        current = 2;
                    } else {
                        current = 1;
                    }
                    if (s.length == 4) {

                        if (s[0].equals("1")) {
                            isPlaying = false;
                            jowinner.showMessageDialog(frame, "Terran Wins!");

                            try {
                                client.close();
                                System.exit(0);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }

                        }
                        if (s[0].equals("2")) {
                            isPlaying = false;
                            jowinner.showMessageDialog(frame, "Zerg Wins!");
                            try {
                                client.close();
                                System.exit(0);

                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }

                        }

                    }

                }

            }

        });
    }

    public void actionPerformed(ActionEvent e) {
        //All the information will come from 
        JButton button = (JButton) e.getSource();
        String info
                = "" + player
                + "," + button.getClientProperty("row")
                + "," + button.getClientProperty("col");
        try {
            if (player == current) {
                client.send(info);
            }
            System.out.println(info);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String args[]) {

        GameBoard gameboard = new GameBoard();
        gameboard.drawGUI();
        gameboard.run();

    }

}
