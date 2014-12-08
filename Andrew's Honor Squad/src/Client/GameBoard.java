package Client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;


public class GameBoard extends JFrame implements ActionListener, Cloneable{

	private ImageIcon terran = new ImageIcon(getClass().getResource("terran.jpg"));
	private ImageIcon zerg = new ImageIcon(getClass().getResource("zerg.jpg"));
	private ImageIcon zergTurn = new ImageIcon(getClass().getResource("zergTurn.jpg"));
	private ImageIcon terranTurn = new ImageIcon(getClass().getResource("terranTurn.jpg"));
	private ImageIcon backgroundImage= new ImageIcon(getClass().getResource("wallpaper.jpeg"));
        private ImageIcon blue = new ImageIcon(getClass().getResource("blue_overlay.jpg"));
	private ImageIcon currentTurnImage =zergTurn;
	private ImageIcon PlayerMarker;
	int[] information= new int[4];
	private int player;
        private int current = 1;
	Client client;

	JFrame frame;
	JMenuBar menuBar;
	JMenu menu,help,rush;
	JMenuItem NewGame;
	JPanel mainPanel;
	JPanel gameBoard;
	JButton[][] boardSquares;
	JPanel turnPanel;
	JLabel currentPlayer;
	JLabel currentTurnLabel;
	JButton turnImage ;
	JPanel chatPanel;
	JTextField enterField;
	JTextArea displayArea;

	public GameBoard(){
		try
		{
			client = new Client();
			player = Integer.parseInt( client.receive()); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}	
	public void drawGUI(){


		JFrame frame = new JFrame("Connect 5 Game Window");
		frame.setVisible(true);
		frame.setSize(900, 900);
		frame.setResizable(false);


		//MENU BAR 
		//Worthless menu that can have added features 
		//such as starting a game, Learning that there is no help availible,
		//or Zerg Rush for win


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

		//Gameboard
		gameBoard = new JPanel();
		gameBoard.setLayout(new GridLayout(15,15,1,1));
		boardSquares = new JButton[15][15];
		for (int i = 0; i < 15; i++) 
		{
			for (int j = 0; j < 15; j++)
			{
				boardSquares[i][j] = new JButton();
				boardSquares[i][j].setPreferredSize(new Dimension(45,45));
				boardSquares[i][j].setIcon(new ImageIcon(getClass().getResource("blue_overlay.jpg")));				
				boardSquares[i][j].putClientProperty("row", i);
				boardSquares[i][j].putClientProperty("col", j);
				boardSquares[i][j].putClientProperty("marker", 0);
				boardSquares[i][j].addActionListener(this);
				gameBoard.add(boardSquares[i][j]);
			}
		}




		//TurnPanel
		turnPanel = new JPanel(new BorderLayout());
		turnPanel.setSize(50,675);
		turnPanel.setBackground(Color.pink);
		currentPlayer = new JLabel("Player:"+player);
		JButton turn = new JButton(currentTurnImage);
		currentTurnLabel = new JLabel(new ImageIcon(getClass().getResource("pylon1.jpg")));


		turnPanel.add(currentPlayer,BorderLayout.NORTH);
		turnPanel.add(turn,BorderLayout.CENTER);
		turnPanel.add(currentTurnLabel,BorderLayout.SOUTH);


		//ChatPanel
		chatPanel = new JPanel(new BorderLayout());
		enterField = new JTextField();
		chatPanel.add(enterField, BorderLayout.NORTH);
		displayArea = new JTextArea(5, 5);
		chatPanel.add(new JScrollPane(displayArea), BorderLayout.SOUTH);
		chatPanel.setBackground(Color.blue);



		mainPanel.add(menuBar, BorderLayout.NORTH);
		mainPanel.add(gameBoard, BorderLayout.WEST);
		mainPanel.add(turnPanel, BorderLayout.EAST);
		mainPanel.add(chatPanel, BorderLayout.SOUTH);
		frame.add(mainPanel);
		frame.pack();
	}

//search for button
	public void searchBoard(int x, int y){
		for (int i = 0; i < 15; i++) 
		{
			for (int j = 0; j < 15; j++)
			{

			}
		}
	}

	public void placePiece(){
		
	}
	
	public void searchPiece(){
		
	}
        public void execute(){
            SwingUtilities.invokeLater(new Runnable(){
            
            public void run(){
                
                try {
                    String line = client.receive();
                    String[] s = line.split(",");
                    if(boardSquares[Integer.parseInt(s[1])][Integer.parseInt(s[2])].getIcon().equals(blue))
                        {
                            if(Integer.parseInt(s[0])==current){
                                //find button
                                if(Integer.parseInt(s[0])==1)
                                    boardSquares[Integer.parseInt(s[1])][Integer.parseInt(s[2])].setIcon(terran);
                                if(Integer.parseInt(s[0])==2)
                                    boardSquares[Integer.parseInt(s[1])][Integer.parseInt(s[2])].setIcon(zerg);
                        //change button by player
                        
                                if(current==1){
                                    current=2;
                                }
                                else
                                    current=1;
                             }
                        }
                    
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            
            
        });
        }

	//search method to find buttons goes here



	public void actionPerformed(ActionEvent e) {
		//All the information will come from 
		JButton button = (JButton) e.getSource();
                String info = 
				""+ player
				+","+ button.getClientProperty("row")
                                +","+ button.getClientProperty("col");
            try {
                client.send(info);System.out.println(info);
                //Button locations
                /*
                
                +","+ button.getClientProperty("marker");
                System.out.println(info);
                
                information[0]= (player);
                information[1]=(int) button.getClientProperty("row");
                information[2]=(int) button.getClientProperty("col");
                information[3]=(int) button.getClientProperty("marker");
                
                if(GameLogic.getTurn()==player){
                try {
                ;
                System.out.println("sent info");
                String line = client.receive();
                
                
                
                } catch (IOException e1) {
                e1.printStackTrace();
                System.out.println("its all gone wrong!");
                }
                
                }
                */
            } catch (IOException ex) {
                ex.printStackTrace();
            }
	}


	public static void main(String args[]){

		GameBoard gameboard = new GameBoard();
		gameboard.drawGUI();
                gameboard.execute();


	}



}

