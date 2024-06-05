import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class Model {
    private Cell cells[][];
    public static final int CELLS_PER_ROW = 20;
    private boolean gameOver = false;
    private static final int BOMB_COUNT = 80;

    public Model(){
        cells = new Cell[CELLS_PER_ROW][CELLS_PER_ROW];
        for(int r=0; r<CELLS_PER_ROW; r++){
            for(int c=0; c<CELLS_PER_ROW; c++){
                cells[r][c] = new Cell(r, c);                
            }
        }  
    }

    public void updateBombCounts(){
        for(int r=0; r<CELLS_PER_ROW; r++){
            for(int c=0; c<CELLS_PER_ROW; c++){
                cells[r][c].setLocalBombCount(countLocalBombs(r, c));
            }
        }
    }
    public Cell[][] getCells(){
        return cells;
    }

    public void setBombs(){
        for(int i=0; i<BOMB_COUNT; i++){
            int r = (int) (Math.random()*CELLS_PER_ROW);
            int c = (int) (Math.random()*CELLS_PER_ROW);
            while(cells[r][c].isBomb()){
                r = (int) (Math.random()*CELLS_PER_ROW);
                c = (int) (Math.random()*CELLS_PER_ROW);
            }
            cells[r][c].setBomb();
        }
    }
    public int countLocalBombs(int row, int col){
        int count = 0;
        for(int r=row-1; r<=row+1; r++){
            for(int c = col-1; c<=col+1; c++){
                if(r>=0 && c>=0 && r<CELLS_PER_ROW && c<CELLS_PER_ROW){
                    if(r!=row || c!=col){
                        if(cells[r][c].isBomb()) count++;
                    }
                }
            }
        }
        return count;
    }

    public boolean reveal(Cell cell){
        if(!gameOver && !cell.isRevealed()){
            int localBombs = cell.reveal();
            if(localBombs==0){
                revealLocal(cell);
                return false;
            }else if(localBombs==-1){
                explode();
                return true;
            }
        }
        return false;
    }

    private void revealLocal(Cell cell){
        int row = cell.getRow();
        int col = cell.getCol();
        for(int r=row-1; r<=row+1; r++){
            for(int c = col-1; c<=col+1; c++){
                if(r>=0 && c>=0 && r<Model.CELLS_PER_ROW && c<Model.CELLS_PER_ROW){
                    if(r!=row || c!=col){
                        if(!cells[r][c].isBomb() && !cells[r][c].isRevealed()){
                            int num = cells[r][c].reveal();
                            if(num==0) revealLocal(cells[r][c]);
                        }
                    }
                }
            }
        }
    }

    public void explode(){
        Border greenline = BorderFactory.createLineBorder(Color.GREEN);
        for(int r=0; r<CELLS_PER_ROW; r++){
            for(int c=0; c<CELLS_PER_ROW; c++){
                if(cells[r][c].isBomb()) {
                    cells[r][c].reveal();
                    gameOver = true;
                }else{
                    if(cells[r][c].isFlagged()) {
                        cells[r][c].setForeground(Color.GREEN);
                        cells[r][c].setBorder(greenline);
                        cells[r][c].setText("X");
                    }
                }
            }
        }
    }

    public boolean evaluateWin(){
        int count = 0;
        for(int r=0; r<CELLS_PER_ROW; r++){
            for(int c=0; c<CELLS_PER_ROW; c++){
                if(!cells[r][c].isBomb() && cells[r][c].isRevealed()) {
                    count++;
                }
            }
        }
        if(count == CELLS_PER_ROW*CELLS_PER_ROW-BOMB_COUNT) gameOver = true;
        return gameOver;
    }

    public void resetGame() {
        for(int r=0; r<CELLS_PER_ROW; r++){
            for(int c=0; c<CELLS_PER_ROW; c++){                   
                cells[r][c].reset();            
            }
        } 
        gameOver = false;
        setBombs();
        updateBombCounts();
    }
}
