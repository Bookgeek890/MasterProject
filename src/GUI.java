//used to display the hashi board

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame{

    JScrollPane pane;
    public GUI(Board board){
        super("Solving Hashiwokakero - Reagan Prater - Graduate Project");
        setSize(new Dimension(500,500));
        //setPreferredSize(new Dimension(500,500));

        JPanel panel = new BoardPanel(board);
        pane = new JScrollPane(panel);
        //pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(pane);

        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
