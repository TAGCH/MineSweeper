import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Game extends JFrame {
    private int boardSize = 10;
    private int mineCount = 20;
    private Board board = new Board(boardSize, mineCount);
    private Gui gui = new Gui();
    private boolean gameOver = false;
    private int flagCounter;

    public Game() {
        flagCounter = mineCount;
        add(gui);
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void revealCellsRecursively(int x, int y) {
        Cell cell = board.getCell(x, y);
        if(!cell.isHidden() || cell.isFlagged()) {
            return;
        }
        cell.setHidden(false);
        if (cell.isMine()) {
            gameOver = true;
            JOptionPane.showMessageDialog(Game.this, "Game Over");
            return;
        }
        if (cell.getSurroundingMines() == 0) {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    int nx = x + dx;
                    int ny = y + dy;
                    if (dx == 0 && dy == 0) {
                        continue;
                    }

                    if (nx < 0 || ny < 0 || nx >= boardSize || ny >= boardSize) {
                        continue;
                    }
                    revealCellsRecursively(nx, ny);
                }
            }
        }
    }

    private void toggleFlagOnCell(int x, int y) {
        Cell cell = board.getCell(x, y);
        if(!cell.isHidden()) {
            return;
        }
        if (cell.isFlagged()) {
            cell.setFlagged(false);
            flagCounter++;
        } else if (flagCounter > 0) {
            cell.setFlagged(true);
            flagCounter--;
        }
    }

    private void checkWinning() {
        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {
                Cell cell = board.getCell(x, y);
                if (cell.isHidden() && !cell.isMine()) {
                    return;
                }
            }
        }
    }

    class Gui extends JPanel {
        private int cellSize = 30;
        private int numPaddingX = 10, numPaddingY = 20;
        private Image imageCell = new ImageIcon("imgs/Cell.png").getImage();
        private Image imageFlag = new ImageIcon("imgs/Flag.png").getImage();
        private Image imageMine = new ImageIcon("imgs/Mine.png").getImage();
        private Color [] numberColors = new Color[] {
                Color.blue,
                Color.green,
                Color.orange,
                Color.black,
                Color.red,
                Color.pink,
                Color.yellow,
                Color.white
        };

        public Gui() {
            setPreferredSize(new Dimension(boardSize * cellSize, boardSize * cellSize));
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    if(gameOver) {
                        return;
                    }
                    int x = e.getX() / cellSize;
                    int y = e.getY() / cellSize;
                    if(e.getButton() == MouseEvent.BUTTON1) {
                        revealCellsRecursively(x, y);
                    } else if(e.getButton() == MouseEvent.BUTTON3) {
                        toggleFlagOnCell(x, y);
                    }
                    checkWinning();
                    repaint();
                }
            });
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            for(int x = 0; x < boardSize; x++) {
                for(int y = 0; y < boardSize; y++) {
                    Cell cell = board.getCell(x, y);
                    int pX = x * cellSize;
                    int pY = y * cellSize;
                    if(cell.isHidden()) {
                        g.drawImage(imageCell, pX, pY, cellSize, cellSize, this);

                    } else {
                        g.setColor(Color.lightGray);
                        g.fillRect(pX, pY, cellSize, cellSize);
                        if (cell.isMine()) {
                            g.drawImage(imageMine, pX, pY, cellSize, cellSize, this);
                        } else if (cell.getSurroundingMines() > 0){
                            g.setColor(numberColors[cell.getSurroundingMines() - 1]);
                            g.drawString(cell.getSurroundingMines() + "", pX + numPaddingX, pY + numPaddingY);
                        }
                    }

                    if(cell.isFlagged()) {
                        g.drawImage(imageFlag, pX, pY, cellSize, cellSize, this);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
    }
}