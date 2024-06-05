
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import java.util.Timer;

public class Controller{
    private View view;
    private Model model;
    private Clock clock;
    private Timer timer;

    public Controller(){
        model = new Model();
        view = new View(this, model.getCells());  
        
        model.setBombs();
        model.updateBombCounts();
        view.revalidate();
        view.repaint();

        clock = new Clock(view);
        view.setMessage("Fastest time: "+clock.getBestTime());
        timer = new Timer();
        timer.schedule(clock, 1000, 1000);
    }

    public void setListener(Cell cell){
        cell.addMouseListener(new CellListener());
    }

    public void setListener(JButton btn){
        btn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {
                model.resetGame(); 
                btn.setEnabled(false);  
                view.setMessage("Fastest time: "+clock.getBestTime());             
                view.revalidate();
                view.repaint();  
                timer.cancel();
                clock = new Clock(view);
                timer = new Timer();
                timer.schedule(clock, 1000, 1000);
            }

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}             
        });
    }

    private class CellListener implements MouseListener{
        @Override
            public void mouseClicked(MouseEvent e) {
                Cell cell = (Cell) e.getSource();
                if(SwingUtilities.isRightMouseButton(e)){
                    cell.toggleFlag();
                }else{
                    if(model.reveal(cell)){
                        view.enablePlayButton();
                        view.setMessage("GAME OVER - Click the button to play again.");
                        timer.cancel();
                    }else if(model.evaluateWin()) {
                        view.enablePlayButton();
                        view.setMessage("You Win!  Click the button to play again.");
                        timer.cancel();
                        if(clock.isFaster()){
                            view.setMessage("You Win!  New record time!");
                            clock.setFastest(clock.getElapsedSeconds());
                        }
                    } 
                } 
                            

            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
    }
}