package Snake;

import java.net.*;
import java.io.*;

public class Server4713 {

    public static void main(String[] args) {
        Highscore.loadScores();
        try {
            ServerSocket sock = new ServerSocket(4713, 100);
            while (true)
                new ClientHandler(sock.accept()).start();
        } catch (IOException e) {
            System.err.println("9" + e);
        }
    }
}

class ClientHandler extends Thread {
    BufferedReader in;
    PrintWriter out;
    Highscore highscore;

    ClientHandler(Socket socket) {

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            System.err.println("8" + e);
        }
    }

    public void run() {
        /*
        JFrame serverInfo = new JFrame("SERVER FEED");
        serverInfo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // serverInfo.setLayout(Border);
        serverInfo.setSize(400, 800);
        serverInfo.add(new JLabel("Hello World"));
        serverInfo.setVisible(true);
        */
        try {
            String name = in.readLine();
            // serverInfo.add(new JLabel(name + " started playing Snake"));
            System.out.println(name + " started playing Snake");
            while(true) {
                String input = in.readLine();//
                if(input==null || input.equals("Gameover")) break;
            }
            
            out.println("Bye " + name); out.flush();
            int score = Integer.parseInt(in.readLine());
            //serverInfo.add(new JLabel(name + " stopped playing and scored " + score));
            System.out.println(name + " stopped playing and scored " + score);
            Highscore player = new Highscore(name, score);
            Highscore.saveScore(player);
            int rows = Highscore.highscores.size();
            
            out.println(rows);
            out.flush();
        
            for (Highscore highscore : Highscore.highscores) {
                out.println(highscore.name + " " + highscore.score);
                out.flush();
            }

        }

        catch(Exception e) {
            System.err.println("10"+e);
        }
    }
}

/*
System.out.println("Här 1");
System.out.println("Här 2");
System.out.println("Här 3");
System.out.println("Här 4");
System.out.println("Här 5");
System.out.println("Här 6");
System.out.println("Här 7");
System.out.println("Här 8");
System.out.println("Här 9");
System.out.println("Här 10");
*/