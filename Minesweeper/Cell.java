import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

public class Cell extends JLabel {
    public static final int SIZE = 20;
    private boolean isBomb = false;
    private boolean isRevealed = false;
    private boolean flag = false;
    private int localBombs = 0;
    private int row;
    private int col;

    public Cell(int row, int col){
        super();
        this.row = row;
        this.col = col;
        setVerticalAlignment(JLabel.CENTER);
        setHorizontalAlignment(JLabel.CENTER);
        setBackground(Color.GRAY);
        setFont(new Font("Tahoma", Font.BOLD, 14));
        setPreferredSize(new Dimension(SIZE-1, SIZE-1));
        setSize(getPreferredSize());
        setOpaque(true);
    }

    public int reveal(){        
        if(isBomb && !flag){
            setBackground(Color.RED);
            return -1;
        }else if(!flag){
            isRevealed = true;
            setBackground(Color.WHITE);
            if(localBombs==2) setForeground(new Color(0,0, 120));
            if(localBombs==3) setForeground(new Color(0,120, 0));
            if(localBombs==4) setForeground(new Color(120,0, 0));
            if(localBombs==5) setForeground(new Color(80,0, 0));
            if(localBombs>=6) setForeground(new Color(220,0, 0));
            if(localBombs>0) setText(localBombs+"");
            return localBombs;
        }
        return -2;
    }

    public void setLocalBombCount(int num){        
        if(!isBomb) localBombs = num;
    }

    public int getLocalBombCount(){
        return localBombs;
    }

    public void toggleFlag(){
        if(!isRevealed){
            flag = !flag;
            if(flag){
                setBackground(Color.MAGENTA);
            }else{
                setBackground(Color.GRAY);
            }
        }        
    }

    public void setBomb(){
        isBomb = true;
        localBombs = -1;
        //setBackground(Color.DARK_GRAY);
    }

    public boolean isBomb(){
        return isBomb;
    }

    public boolean isRevealed(){
        return isRevealed;
    }
    
    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    public void reset(){
        setBackground(Color.GRAY);
        setForeground(Color.BLACK);
        setText("");
        isBomb = false;
        isRevealed = false;
        flag = false;
        localBombs = 0;
    }
}
