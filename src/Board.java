//This holds a hashi board

import java.util.ArrayList;
import java.util.LinkedList;

public class Board {

    private Island[][] board;
    private int x,y;
    private LinkedList<Island> nodes;
    private double fitness;

    public Board(int x, int y){
        setBoard(new Island[x][y]);
        setNodes(new LinkedList<Island>());
        setFitness(0);
        this.x = x;
        this.y = y;
    }

    public Island[][] getBoard() { return board; }

    public void setBoard(Island[][] board) { this.board = board; }

    public LinkedList<Island> getNodes() { return nodes; }

    public void setNodes(LinkedList<Island> nodes) { this.nodes = nodes; }

    public double getFitness() {
        calculateFitness();
        return fitness;
    }

    public void setFitness(double fitness) { this.fitness = fitness; }

    public void setAdjacentIslands(){
        //have nodes point to each other if they are near each other or not
        for(Island i:nodes){

            //if this island isn't already pointing to a node in the west
            if(i.getWestIsland() == null) {
                for (int x1 = i.getX() - 1; x1 >= 0; x1--) {
                    if (board[x1][i.getY()] != null) {
                        i.setWestIsland(board[x1][i.getY()]);
                        board[x1][i.getY()].setEastIsland(i);
                        break;
                    }
                }
            }

            //if this island isn't already pointing to a node in the east
            if(i.getEastIsland() == null) {
                for (int x2 = i.getX() + 1; x2 < board.length; x2++) {
                    if (board[x2][i.getY()] != null) {
                        i.setEastIsland(board[x2][i.getY()]);
                        board[x2][i.getY()].setWestIsland(i);
                        break;
                    }
                }
            }

            //if this island isn't already pointing to a node in the north
            if(i.getNorthIsland() == null){
                for(int y1 = i.getY()-1; y1 >= 0; y1--){
                    if(board[i.getX()][y1] != null){
                        i.setNorthIsland(board[i.getX()][y1]);
                        board[i.getX()][y1].setSouthIsland(i);
                        break;
                    }
                }
            }

            //if this island isn't already pointing to a node in the south
            if(i.getSouthIsland() == null){
                for(int y2 = i.getY()+1; y2 < board[0].length; y2++){
                    if(board[i.getX()][y2] != null){
                        i.setSouthIsland(board[i.getX()][y2]);
                        board[i.getX()][y2].setNorthIsland(i);
                        break;
                    }
                }
            }
        }//end for loop
    }//end function setAdjacentIslands

    //makes a complete copy of the board in its current state
    public Board copyBoard(){
        Board b = new Board(board.length, board[0].length);
        for(Island i:nodes){
            Island newIsland = i.copyIsland();
            b.getBoard()[i.getX()][i.getY()] = newIsland;
            b.getNodes().add(newIsland);
        }
        b.setAdjacentIslands();
        b.fixBridges();
        return b;
    }

    //will add bridges and will fix any that cross each other
    public void fixBridges(){
        board = new Island[x][y];
        for(int i = 0; i < nodes.size(); i++){
            Island island = nodes.get(i);
            board[island.getX()][island.getY()] = island;
            if(island.getSouthBridges() != 0){
                boolean isLocked = false, clear = false;

                while(!isLocked && !clear) {
                    //where the bridge is found, if there is one
                    int bridgeX = -1, bridgeY = -1;
                    //check if there is a bridge in the way
                    for (int y = island.getY() + 1; y < island.getSouthIsland().getY(); y++) {
                        if (board[island.getX()][y] != null && board[island.getX()][y].isBridge()) {
                            bridgeX = island.getX();
                            bridgeY = y;
                            break;
                        }
                    }
                    //a bridge was found
                    if (bridgeX >= 0 && bridgeY >= 0) {
                        //find the west island for this bridge
                        Island west = null;
                        for (int x = bridgeX - 1; x >= 0; x--) {
                            if (board[x][bridgeY] != null && !board[x][bridgeY].isBridge()) {
                                west = board[x][bridgeY];
                            }
                        }
                        //can we remove the bridge?
                        if (west != null && west.isEastLocked()) {
                            isLocked = true;
                            break;
                        } else if (west != null) {
                            //if so remove the bridge
                            west.setEastBridges(0);
                            west.getEastIsland().setWestBridges(0);
                            for (int x = west.getX() + 1; x < west.getEastIsland().getX(); x++) {
                                board[x][bridgeY] = null;
                            }
                        }
                    } else{
                        clear = true;
                        break;
                    }
                }
                if(!isLocked && clear) {
                    for (int y = island.getY() + 1; y < island.getSouthIsland().getY(); y++) {
                        board[island.getX()][y] = new Island(true);
                    }
                } else{//if it is locked and there is a crossing, remove the bridge
                    island.setSouthBridges(0);
                    island.getSouthIsland().setNorthBridges(0);
                }
            }
            if(island.getEastBridges() != 0){
                boolean isLocked = false, clear = false;
                while(!isLocked && !clear) {
                    //where the bridge is found, if there is one
                    int bridgeX = -1, bridgeY = -1;
                    //check if there is a bridge in the way
                    for (int x = island.getX() + 1; x < island.getEastIsland().getX(); x++) {
                        if (board[x][island.getY()] != null && board[x][island.getY()].isBridge()) {
                            bridgeX = x;
                            bridgeY = island.getY();
                            break;
                        }
                    }
                    //a bridge was found
                    if (bridgeX >= 0 && bridgeY >= 0) {
                        Island north = null;
                        //find the island
                        for (int y = bridgeY - 1; y >= 0; y--) {
                            if (board[bridgeX][y] != null && !board[bridgeX][y].isBridge()) {
                                north = board[bridgeX][y];
                                break;
                            }
                        }

                        //if this bridge is locked
                        if (north != null && north.isSouthLocked()) {
                            isLocked = true;
                            break;
                        } else if (north != null) {//else, it can be removed
                            north.setSouthBridges(0);
                            north.getSouthIsland().setNorthBridges(0);
                            for (int y = north.getY() + 1; y < north.getSouthIsland().getY(); y++) {
                                board[bridgeX][y] = null;
                            }
                        }
                    } else{
                        clear = true;
                        break;
                    }
                }
                if(!isLocked && clear) {
                    for (int x = island.getX() + 1; x < island.getEastIsland().getX(); x++) {
                        board[x][island.getY()] = new Island(true);
                    }
                } else{
                    island.setEastBridges(0);
                    island.getEastIsland().setWestBridges(0);
                }
            }
        }
    }

    //calculates the fitness score
    private void calculateFitness(){
        double correctConnections = 0;
        double count = 0;
        double connect = 0;
        for(Island i:nodes){
            if(i.getConnections() == i.numberOfBridges()){
                correctConnections++;
            }
            count++;
            if(i.getSouthBridges() != 0){
                connect++;
            }
            if(i.getEastBridges() != 0){
                connect++;
            }
        }
        double loop = 0;
        //check for loops and penalize them
        /*if(loopExists(nodes.get(0), null, null)){
            loop = -5;
        }*/

        //this takes care of there being more connections than needed
        double cc = 1-Math.abs(1-(connect/(count-1)));
        fitness = (50 * (correctConnections/(double)(nodes.size()))) + (50 * cc)+loop;

    }

    //recursive function to find loops
    private boolean loopExists(Island vertex, ArrayList<Island> visited, Island parent){
        //first time being called
        if(parent == null){
            visited = new ArrayList<>();
        }
        //check if node is in visited
        for(int i = 0; i < visited.size(); i++){
            //if it is, there is a loop
            if(vertex == visited.get(i)){
                return true;
            }
        }

        //add vertex to visited
        visited.add(vertex);

        //need to call function for each direction

        //check the north. If it has bridges and is not the parent
        if(vertex.getNorthIsland() != null && vertex.getNorthBridges() != 0 && vertex.getNorthIsland() != parent){
            //see if a loop exists in that direction, if so, return true
            if(loopExists(vertex.getNorthIsland(), visited, vertex)){
                return true;
            }
        }

        //check the south. If it has bridges and is not the parent
        if(vertex.getSouthIsland() != null && vertex.getSouthBridges() != 0 && vertex.getSouthIsland() != parent){
            //see if loop exists in that direction, if so, return true
            if(loopExists(vertex.getSouthIsland(), visited, vertex)){
                return true;
            }
        }

        //check the east. If it has bridges and is not the parent
        if(vertex.getEastIsland() != null && vertex.getEastBridges() != 0 && vertex.getEastIsland() != parent){
            //see if loop exists in that direction. If so, return true
            if(loopExists(vertex.getEastIsland(), visited, vertex)){
                return true;
            }
        }

        //check the west. If it has bridges and is not the parent
        if(vertex.getWestIsland() != null && vertex.getWestBridges() != 0 && vertex.getWestIsland() != parent){
            //see if loop exists in that direction. If so, return true
            if(loopExists(vertex.getWestIsland(), visited, vertex)){
                return true;
            }
        }

        return false;
    }
}
