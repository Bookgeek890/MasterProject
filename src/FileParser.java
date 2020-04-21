//This parses a file into a hashi board

import java.io.BufferedReader;
import java.io.FileReader;

public class FileParser {

    public static Board parse(String filename){
        try {

            FileReader reader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line = bufferedReader.readLine();
            int x = Integer.parseInt(line.substring(0,line.indexOf('x')));
            int y = Integer.parseInt(line.substring(line.indexOf('x')+1));

            Board board = new Board(x,y);

            line = bufferedReader.readLine();

            while(line != null){
                int X = Integer.parseInt(line.substring(0,line.indexOf(' ')));
                int Y = Integer.parseInt(line.substring(line.indexOf(' ')+1, line.indexOf(' ', line.indexOf(' ')+1)));
                int num = Integer.parseInt(line.substring(line.indexOf(' ', line.indexOf(' ')+1)+1));
                Island island = new Island(X, Y, num);
                board.getBoard()[X][Y] = island;
                board.getNodes().add(island);
                line = bufferedReader.readLine();
            }

            board.setAdjacentIslands();

            return board;

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
