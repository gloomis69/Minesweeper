
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

public class Controller{
    private View view;
    private Model model;

    public Controller(){
        model = new Model();
        view = new View(this, model.getCells());  
        
        model.setBombs();
        model.updateBombCounts();
        view.revalidate();
        view.repaint();
    }

    public void setListener(Cell cell){
        cell.addMouseListener(new CellListener());
    }

    public void setListener(JButton btn){
        btn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                model.resetGame(); 
                btn.setEnabled(false);  
                view.setMessage("");             
                view.revalidate();
                view.repaint();                
            }

            @Override
            public void mousePressed(MouseEvent e) {}

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
                    }else if(model.evaluateWin()) {
                        view.enablePlayButton();
                        view.setMessage("You Win!  Click the button to play again.");
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