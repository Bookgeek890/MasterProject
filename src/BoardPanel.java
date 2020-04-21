import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {

    Board board;

    public BoardPanel(Board b){
        super();
        setPreferredSize(new Dimension(1000,1000));
        board = b;
    }

    @Override
    public void paintComponent(Graphics g){
        //System.out.println("Im in paint function");
        g.setColor(Color.BLACK);
        g.setFont(new Font("Serif", Font.BOLD, 10));

        //for all the islands
        for(Island i:board.getNodes()){
            if(!i.isBridge()) {

                //draw the island
                int x = (i.getX() * 25) + 5;
                int y = (i.getY() * 25) + 5;
                g.drawOval(x, y, 15, 15);
                FontMetrics metrics = g.getFontMetrics();
                String s = i.getConnections() + "";
                g.drawString(s, x + (7 - (metrics.stringWidth(s) / 2)), y + 5 + (metrics.getHeight() / 2));

                //draw the vertically connected bridges
                if (i.getSouthBridges() == 1) {
                    int h = (i.getSouthIsland().getY()*25) + 5 - y - 15;
                    g.fillRect(x + 7, y + 15, 2, h);
                }
                if (i.getSouthBridges() == 2) {
                    int h = (i.getSouthIsland().getY()*25) + 5 - y - 15;
                    g.fillRect(x + 5, y + 15, 2, h);
                    g.fillRect(x + 9, y + 15, 2, h);
                }

                //draw the horizontally connected bridges
                if(i.getEastBridges() == 1){
                    int w = (i.getEastIsland().getX()*25) + 5 - x - 15;
                    g.fillRect(x + 15, y + 7, w, 2);
                }
                if(i.getEastBridges() == 2){
                    int w = (i.getEastIsland().getX()*25)+5 - x - 15;
                    g.fillRect(x + 15, y + 5, w, 2);
                    g.fillRect(x + 15, y + 9, w, 2);
                }
            }
        }

    }
}
