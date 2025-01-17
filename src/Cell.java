public class Cell {
    private boolean hidden;
    private boolean mine;
    private int surroundingMines;
    private boolean flagged;

    public Cell() {
        hidden = true;
        mine = false;
        surroundingMines = 0;
        flagged = false;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public boolean isMine() {
        return mine;
    }

    public void setSurroundingMines(int surroundingMines) {
        this.surroundingMines = surroundingMines;
    }

    public int getSurroundingMines() {
        return surroundingMines;
    }
}
