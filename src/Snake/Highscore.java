package Snake;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Highscore implements Comparable<Highscore>{
    String name;
    int score;
    static ArrayList<Highscore> highscores = new ArrayList<Highscore>();
    Highscore newScore;

    Highscore(String name, int score) {
        this.name = name;
        this.score = score;
    }
/*
    public static void main(String[] args) {
        Highscore person;
   
        person = new Highscore("Mogambo", 8);
        Highscore.loadScores();
        Highscore.saveScore(person);

        // System.out.print(System.getProperty("user.dir") + System.getProperty(file.separator) );
        Path path = FileSystems.getDefault().getPath(".").toAbsolutePath();
        System.out.print(path);

    }

*/
    @Override
    public int compareTo(Highscore o) {
        if (o.score > this.score){
            return 1;
        }
        return -1;
    }
    
    static void saveScore(Highscore newScore) {
        File file = new File("C:\\Users\\Mahmo\\Documents\\KODA\\Projekt\\src\\Snake\\Highscores.txt");
        FileWriter fr;
        try {
            fr = new FileWriter(file);
            highscores.add(newScore);
            highscores.sort(null);
            for (Highscore highscore : highscores) {
                fr.write(highscore.name + " " + highscore.score + "\n");
            }
            fr.close();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    static void loadScores() {
        Scanner sc = null;
        try {
            File file = new File("C:\\Users\\Mahmo\\Documents\\KODA\\Projekt\\src\\Snake\\Highscores.txt");
            sc = new Scanner(file); 
            sc.useDelimiter("\\Z");   
        } catch (FileNotFoundException e) {
            
        }
        while (sc != null && sc.hasNextLine()){
            String[] data = sc.nextLine().split("\\s+");   
            highscores.add(new Highscore(data[0], Integer.parseInt(data[1])));
        }
        
    }    

}