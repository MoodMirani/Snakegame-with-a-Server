package Snake;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Client extends JFrame {
    static JFrame frame = new JFrame("SNAKE"); 
    private Socket sock;
    BufferedReader in;
    PrintWriter out;
    String name;
    static ArrayList<String> scores = new ArrayList<String>();
    private GameBoard gameboard;

    Client(String host, int port, String name) {
        this.sock = getSocket(host, port);
        this.in = getInStream(this.sock);
        this.out = getOutputStream(this.sock);
        this.name = name;
    }

    private static GameBoard startGame(Client client) {
        
        GameBoard gameboard = new GameBoard(client);
        frame.add(gameboard);
        frame.pack();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
        return gameboard;
    }

    private Socket getSocket(String host, int port) {
        try {
            Socket sock = new Socket(host, port);
            return sock;
        } catch (Exception e) {
            System.out.println("4" + e);
        }
        return null;
    }

    private BufferedReader getInStream(Socket sock) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            return in;
        } catch (IOException e) {
            System.err.print("3" + e);
        }

        return null;
    }

    private PrintWriter getOutputStream(Socket sock) {
        try {
            PrintWriter ut = new PrintWriter(sock.getOutputStream());
            return ut;
        } catch (IOException e) {
            System.err.println("2" + e);
        }
        return null;
    }
    private static String getUserName(){
        String name = (String)JOptionPane.showInputDialog(frame,"Welcome to Snake!\nWrite your username: \n"
        ,"USERNAME",JOptionPane.PLAIN_MESSAGE, null, null, "Username");

        if (name==null){
            System.exit(0);
        }
        return name;
    }
    private static void showLeaderboard(ArrayList scores){
        
    
        // JPanel LBoard = new JPanel();
        // LBoard.setLayout(new BoxLayout(LBoard, BoxLayout.PAGE_AXIS));

        JFrame leader_board = new JFrame("Leaderboard"); 
        String highScores = "";
        int i = 0;
        

        for (String score : Client.scores ) {
            if (i >= 10){
                break;
            }
            highScores = highScores + "\n" + score;
            i++;
        }
        JOptionPane.showMessageDialog(leader_board,
        highScores,
        "SNAKE LEADERBOARD",
        JOptionPane.PLAIN_MESSAGE);
    }

    
    public static void main(String[] args) {
        String name = getUserName();
        Client client = new Client("localhost", 4713, name);
        // Client client = new Client("localhost", 4713, "Lotta");
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.sock.getInputStream()));
            PrintWriter ut = new PrintWriter(client.sock.getOutputStream());

            ut.println(name);
            ut.flush();

            GameBoard gameboard = startGame(client);
            System.out.println(in.readLine());
            ut.println(gameboard.score);
            ut.flush();

            int rows = Integer.parseInt(in.readLine());

            for (int i = 0; i < rows; i++) {
                scores.add(in.readLine());
            } 
            showLeaderboard(scores);

    }
        catch (IOException e){
            System.err.println(e);
        }


    }
}
