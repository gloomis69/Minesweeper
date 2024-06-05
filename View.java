import javax.swing.*;
import java.awt.*;

public class View extends JFrame{
    //private Controller controller;
    private JPanel panel;
    JButton btnPlay;
    JLabel lblMessage;

    public View(Controller controller, Cell[][] cells){
        super(); 
        //this.controller = controller;
        setPreferredSize(new Dimension(Cell.SIZE*Model.CELLS_PER_ROW+10, Cell.SIZE*Model.CELLS_PER_ROW+150));
        setSize(new Dimension(Cell.SIZE*Model.CELLS_PER_ROW+10, Cell.SIZE*Model.CELLS_PER_ROW+150));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER, 1, 1));
        JPanel pnlInfo = new JPanel();
        pnlInfo.setLayout(new FlowLayout(FlowLayout.CENTER, 1, 1));
        pnlInfo.setPreferredSize(new Dimension(getWidth(), 100));
        pnlInfo.setSize(getPreferredSize());
        pnlInfo.setBackground(Color.BLUE);
        
        pnlInfo.add(Clock.lblClock);

        lblMessage = new JLabel("", JLabel.CENTER);
        lblMessage.setForeground(Color.WHITE);
        lblMessage.setPreferredSize(new Dimension(getWidth(), 30));
        lblMessage.setSize(getPreferredSize());
        pnlInfo.add(lblMessage);
        btnPlay = new JButton("Play");
        controller.setListener(btnPlay);
        pnlInfo.add(btnPlay);

        panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 1, 1));
        panel.setPreferredSize(new Dimension(Cell.SIZE*Model.CELLS_PER_ROW, Cell.SIZE*Model.CELLS_PER_ROW));
        panel.setSize(getPreferredSize());
        panel.setBackground(Color.WHITE);

        for(int r=0; r<cells.length; r++){
            for(int c=0; c<cells[0].length; c++){                
                panel.add(cells[r][c]);
                controller.setListener(cells[r][c]);
            }
        }

        add(pnlInfo);
        add(panel);
        setVisible(true);
        pack();     
        
    }

    public void resetGame(Cell cells[][]){
        panel.removeAll();
        for(int r=0; r<cells.length; r++){
            for(int c=0; c<cells[0].length; c++){                
                panel.add(cells[r][c]);
            }
        }

    }

    public void enablePlayButton(){
        btnPlay.setEnabled(true);
    }
    
    public void setMessage(String msg){
        lblMessage.setText(msg);
    }
}
