import java.awt.Color;
import java.awt.Font;
import java.util.TimerTask;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JLabel;

public class Clock extends TimerTask{
    public static final JLabel lblClock = new JLabel("0:00:00");
    private int i = 0;
    private View view;
    private int fastest = Integer.MAX_VALUE;

    public Clock(View view){
        this.view = view;
        lblClock.setForeground(Color.WHITE);
        lblClock.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblClock.setText("0:00:00");
        FileReader fr;
        try {
            fr = new FileReader("highscore.txt");
            BufferedReader reader = new BufferedReader(fr);
            String score = reader.readLine();
            fastest = Integer.valueOf(score);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public void run() {
        i++;
        int hrs = i/3600;
        int mins = i%3600/60;
        int secs = i%60;
        String minString = mins+"";
        if(mins<10) minString = "0"+mins;

        String secString = secs+"";
        if(secs<10) secString = "0"+secs;
        lblClock.setText(hrs+":"+minString+":"+secString);
        view.revalidate();
        view.repaint();
    }
    
    public void setFastest(int secs){
        fastest = secs;
        try {
            FileWriter fw = new FileWriter("highscore.txt");
            BufferedWriter writer = new BufferedWriter(fw);
            String fastestString = fastest+"";
            writer.write(fastestString, 0, fastestString.length());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public int getFastest(){
        return fastest;
    }

    public String getBestTime(){
        int hrs = fastest/3600;
        int mins = fastest%3600/60;
        int secs = fastest%60;
        String minString = mins+"";
        if(mins<10) minString = "0"+mins;

        String secString = secs+"";
        if(secs<10) secString = "0"+secs;
        return hrs+":"+minString+":"+secString;
    }

    public int getElapsedSeconds(){
        return i;
    } 

    public boolean isFaster(){
        return i<fastest;
    }
}
