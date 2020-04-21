
public class GA {

    Board initial;
    Board[] current;

    //takes in an initial board and population number
    public GA(Board b, int num){
        initial = b;
        current = new Board[num];
        for(int i = 0; i < num; i++){
            current[i] = b.copyBoard();
        }
        mutate();
    }

    //this will generate the next generation
    public void nextGen(){
        Board[] parents = new Board[current.length];
        int[] top20 = new int[(int)(.2*current.length)];
        int highestFitnessIndex = 0;
        double highestFitness = 0;
        for(int c = 0; c < top20.length; c++) {
            for (int i = 0; i < current.length; i++) {
                if(c == 0){
                    if(highestFitness < current[i].getFitness()){
                        highestFitnessIndex = i;
                        highestFitness = current[i].getFitness();
                    }
                } else{
                    if(current[i].getFitness() < current[top20[c-1]].getFitness() && current[i].getFitness() > highestFitness){
                        highestFitnessIndex = i;
                        highestFitness = current[i].getFitness();
                    }
                }
            }//end for loop going through all the current boards

            //put the index in the list of top 20% highest fitness levels
            top20[c] = highestFitnessIndex;
        }//end for loop to obtain the boards in the top 20% of fitness scores
        //determine who is gonna be parents
        for(int i = 0; i < parents.length; i++){
            double rand1 = Math.random();
            if(rand1 < .8){
                int index = (int)(Math.random()*top20.length);
                parents[i] = current[top20[index]];
            } else{
                int index = (int)(Math.random()*current.length);
                parents[i] = current[index];
            }
        }

        //make the kids
        for(int i = 0; i < current.length; i=i+2){
            current[i] = combine(parents[i], parents[i+1]);
            current[i+1] = combine(parents[i+1], parents[i]);
        }

        //mutate all the kids
        mutate();
    }

    //this will mutate the current boards
    private void mutate(){
        for(int i = 0; i < current.length; i++){

            //obtain a random node that isn't completed or if all are completed, then just get a random island
            int rand = (int)(Math.random()*current[i].getNodes().size());
            int count = 0;
            while(current[i].getNodes().get(rand).getConnections() == current[i].getNodes().get(rand).numberOfBridges() && count != current[i].getNodes().size()){
                rand++;
                count++;
                if(rand == current[i].getNodes().size()){
                    rand = 0;
                }
            }
            Board b = current[i];
            Island island = b.getNodes().get(rand);
            double direction = Math.random();

            //go north if direction is less than .25
            if(direction < .25){
                //if unable to go north
                if(!north(b,island)){
                    //try going south. if unable to go south
                    if(!south(b, island)){
                        //go east. if unable to go east
                        if(!east(b, island)){
                            //go west
                            west(b, island);
                        }
                    }
                }
            } else if(direction < .5){//go south if between .25 and .5
                //if unable to go south
                if(!south(b, island)){
                    //go east. if unable to go east
                    if(!east(b, island)){
                        //go west. if unable to go west
                        if(!west(b, island)){
                            //go north
                            north(b, island);
                        }
                    }
                }
            } else if(direction < .75){//go east if between .5 and .75
                //if unable to go east
                if(!east(b, island)){
                    //go west. if unable to go west
                    if(!west(b, island)){
                        //go north. if unable to go north
                        if(!north(b, island)){
                            south(b, island);
                        }
                    }
                }
            } else{//go west if between .75 and 1
                //if unable to go west
                if(!west(b, island)){
                    //go north. if unable to go north
                    if(!north(b, island)){
                        //go south. if unable to go south
                        if(!south(b, island)){
                            //go east
                            east(b, island);
                        }
                    }
                }
            }//end of if else statements about direction

        }//end for loop
    }//end mutate function

    //take a board and an island and will randomize the bridges north, if able
    //will return true if able and will return false if unable
    private boolean north(Board b, Island island){
        //if there is no north island or the island is locked, unable to randomize the bridge connections
        if(island.getNorthIsland() == null || island.isNorthLocked()){
            return false;
        }
        //randomize the bridges
        int bridges = (int)(Math.random()*3);

        //there are currently no bridges in this direction
        if(island.getNorthBridges() == 0){
            boolean isLocked = false, clear = false;

            //until either a locked bridge is found or all bridges are cleared
            while(!isLocked && !clear) {
                int bridgeX = -1, bridgeY = -1;
                //check if there is a bridge in this direction
                for (int y = island.getY() - 1; y > island.getNorthIsland().getY(); y--) {
                    if (b.getBoard()[island.getX()][y] != null && b.getBoard()[island.getX()][y].isBridge()) {
                        bridgeX = island.getX();
                        bridgeY = y;
                        break;
                    }
                }
                if(bridgeX < 0 && bridgeY < 0){
                    clear = true;
                    break;
                } else {
                    Island west = null;
                    //if there is a bridge, find the west island for it
                    for(int x = bridgeX-1; x >= 0; x--){
                        if(b.getBoard()[x][bridgeY] != null && !b.getBoard()[x][bridgeY].isBridge()){
                            west = b.getBoard()[x][bridgeY];
                            break;
                        }
                    }
                    //check if it is locked
                    if(west != null && west.isEastLocked()){
                        isLocked = true;
                        break;
                    } else if(west != null){
                        //if it is not, remove this bridge
                        west.setEastBridges(0);
                        west.getEastIsland().setWestBridges(0);
                        for(int x = west.getX()+1; x < west.getEastIsland().getX(); x++){
                            b.getBoard()[x][west.getY()] = null;
                        }
                    }
                }//end if else there is a bridge or not
            }//end while there are bridges in the way and they aren't locked
            //if there is either no bridge in the way or they could all be removed, change the bridge
            if(!isLocked && clear){
                island.setNorthBridges(bridges);
                island.getNorthIsland().setSouthBridges(bridges);
                for(int y = island.getY()-1; y > island.getNorthIsland().getY(); y--){
                    b.getBoard()[island.getX()][y] = new Island(true);
                }
            }
        } else{//there existed a bridge in this direction
            //if these bridges are to be removed
            if(bridges == 0){
                island.setNorthBridges(bridges);
                island.getNorthIsland().setSouthBridges(bridges);
                //get rid of bridges
                for(int y = island.getY()-1; y > island.getNorthIsland().getY(); y--){
                    b.getBoard()[island.getX()][y] = null;
                }
            } else{//otherwise, just adjust the numbers
                island.setNorthBridges(bridges);
                island.getNorthIsland().setSouthBridges(bridges);
            }
        }

        return true;
    }

    //take a board and an island and will randomize the bridges south, if able
    //will return true if able and will return false if unable
    private boolean south(Board b, Island island){
        //if there is no south island or the island is locked, unable to randomize the bridge connections
        if(island.getSouthIsland() == null || island.isSouthLocked()){
            return false;
        }
        //randomize the bridges
        int bridges = (int)(Math.random()*3);

        //there are currently no bridges in this direction
        if(island.getSouthBridges() == 0){
            boolean isLocked = false, clear = false;
            //while there are bridges in the way and one of them isn't locked yet
            while(!isLocked && !clear){
                //keep track of bridge numbers
                int bridgeX = -1, bridgeY = -1;
                //see if there is a bridge
                for(int y = island.getY()+1; y < island.getSouthIsland().getY(); y++){
                    if(b.getBoard()[island.getX()][y] != null && b.getBoard()[island.getX()][y].isBridge()){
                        bridgeX = island.getX();
                        bridgeY = y;
                        break;
                    }
                }
                //if there is no bridge found
                if(bridgeX < 0 && bridgeY < 0){
                    clear = true;
                    break;
                } else{
                    Island west = null;
                    //if there is a bridge, find the west island for it
                    for(int x = bridgeX-1; x >= 0; x--){
                        if(b.getBoard()[x][bridgeY] != null && !b.getBoard()[x][bridgeY].isBridge()){
                            west = b.getBoard()[x][bridgeY];
                            break;
                        }
                    }
                    //check if it is locked
                    if(west != null && west.isEastLocked()){
                        isLocked = true;
                        break;
                    } else if(west != null){
                        //if it is not, remove this bridge
                        west.setEastBridges(0);
                        west.getEastIsland().setWestBridges(0);
                        for(int x = west.getX()+1; x < west.getEastIsland().getX(); x++){
                            b.getBoard()[x][west.getY()] = null;
                        }
                    }
                }
            }
            //if the way is clear and not locked
            if(!isLocked && clear){
                //set the bridges
                island.setSouthBridges(bridges);
                island.getSouthIsland().setNorthBridges(bridges);
                for(int y = island.getY()+1; y < island.getSouthIsland().getY(); y++){
                    b.getBoard()[island.getX()][y] = new Island(true);
                }
            }
        } else{//there existed a bridge in this direction
            //if these bridges are to be removed
            if(bridges == 0){
                island.setSouthBridges(bridges);
                island.getSouthIsland().setNorthBridges(bridges);
                //get rid of bridges
                for(int y = island.getY()+1; y < island.getSouthIsland().getY(); y++){
                    b.getBoard()[island.getX()][y] = null;
                }
            } else{//otherwise, just adjust the numbers
                island.setSouthBridges(bridges);
                island.getSouthIsland().setNorthBridges(bridges);
            }
        }

        return true;
    }

    //take a board and an island and will randomize the bridges east, if able
    //will return true if able and will return false if unable
    private boolean east(Board b, Island island){
        //if there is no east island or the island is locked, unable to randomize the bridge connections
        if(island.getEastIsland() == null || island.isEastLocked()){
            return false;
        }
        //randomize the bridges
        int bridges = (int)(Math.random()*3);

        //there are currently no bridges in this direction
        if(island.getEastBridges() == 0){
            boolean isLocked = false, clear = false;
            //while there are bridges in the way and one of them isn't locked
            while(!isLocked && !clear){
                int bridgeX = -1, bridgeY = -1;
                //check for bridges
                for(int x = island.getX()-1; x > island.getEastIsland().getX(); x--){
                    if(b.getBoard()[x][island.getY()] != null && b.getBoard()[x][island.getY()].isBridge()){
                        bridgeX = x;
                        bridgeY = island.getY();
                        break;
                    }
                }
                //if no bridge was found
                if(bridgeX < 0 && bridgeY < 0){
                    clear = true;
                    break;
                } else{
                    Island north = null;
                    //find the north island of the bridge
                    for(int y = bridgeY-1; y >= 0; y--){
                        if(b.getBoard()[bridgeX][y] != null && !b.getBoard()[bridgeX][y].isBridge()){
                            north = b.getBoard()[bridgeX][y];
                            break;
                        }
                    }
                    //check if it is locked
                    if(north != null && north.isSouthLocked()){
                        isLocked = true;
                        break;
                    } else if(north != null){
                        //get rid of the bridge
                        north.setSouthBridges(0);
                        north.getSouthIsland().setNorthBridges(0);
                        for(int y = north.getY()+1; y < north.getSouthIsland().getY(); y++){
                            b.getBoard()[north.getX()][y] = null;
                        }
                    }
                }
            }
            //if the way is not locked and it is clear of bridges
            if(!isLocked && clear){
                //add bridges
                island.setEastBridges(bridges);
                island.getEastIsland().setWestBridges(bridges);
                for(int x = island.getX()-1; x > island.getEastIsland().getX(); x--){
                    b.getBoard()[x][island.getY()] = new Island(true);
                }
            }
        } else{
            //if these bridges are to be removed
            if(bridges == 0){
                island.setEastBridges(bridges);
                island.getEastIsland().setWestBridges(bridges);
                //get rid of bridges
                for(int x = island.getX()+1; x < island.getEastIsland().getX(); x++){
                    b.getBoard()[x][island.getY()] = null;
                }
            } else{//otherwise, just adjust the numbers
                island.setEastBridges(bridges);
                island.getEastIsland().setWestBridges(bridges);
            }
        }

        return true;
    }

    //take a board and an island and will randomize the bridges west, if able
    //will return true if able and will return false if unable
    private boolean west(Board b, Island island){
        //if there is no west island or the island is locked, unable to randomize the bridge connections
        if(island.getWestIsland() == null || island.isWestLocked()){
            return false;
        }
        //randomize the bridges
        int bridges = (int)(Math.random()*3);

        //there are currently no bridges in this direction
        if(island.getWestBridges() == 0){
            boolean isLocked = false, clear = false;
            //while there are bridges in the way and one of them isn't locked
            while(!isLocked && !clear){
                int bridgeX = -1, bridgeY = -1;
                //check for bridges
                for(int x = island.getX()+1; x < island.getWestIsland().getX(); x++){
                    if(b.getBoard()[x][island.getY()] != null && b.getBoard()[x][island.getY()].isBridge()){
                        bridgeX = x;
                        bridgeY = island.getY();
                        break;
                    }
                }
                //if there are no bridges
                if(bridgeX < 0 && bridgeY < 0){
                    clear = true;
                    break;
                } else{
                    Island north = null;
                    //find the north island of the bridge
                    for(int y = bridgeY-1; y >= 0; y--){
                        if(b.getBoard()[bridgeX][y] != null && !b.getBoard()[bridgeX][y].isBridge()){
                            north = b.getBoard()[bridgeX][y];
                            break;
                        }
                    }
                    //check if it is locked
                    if(north != null && north.isSouthLocked()){
                        isLocked = true;
                        break;
                    } else if(north != null){
                        //get rid of the bridge
                        north.setSouthBridges(0);
                        north.getSouthIsland().setNorthBridges(0);
                        for(int y = north.getY()+1; y < north.getSouthIsland().getY(); y++){
                            b.getBoard()[north.getX()][y] = null;
                        }
                    }
                }
            }
            //if the way is clear with no locked bridges
            if(!isLocked && clear){
                //add the bridges
                island.setWestBridges(bridges);
                island.getWestIsland().setEastBridges(bridges);
                for(int x = island.getX()+1; x < island.getWestIsland().getX(); x++){
                    b.getBoard()[x][island.getY()] = new Island(true);
                }
            }
        } else{
            //if these bridges are to be removed
            if(bridges == 0){
                island.setWestBridges(bridges);
                island.getWestIsland().setEastBridges(bridges);
                //get rid of bridges
                for(int x = island.getX()-1; x > island.getWestIsland().getX(); x--){
                    b.getBoard()[x][island.getY()] = null;
                }
            } else{//otherwise, just adjust the numbers
                island.setWestBridges(bridges);
                island.getWestIsland().setEastBridges(bridges);
            }
        }

        return true;
    }

    //combine two parents
    private Board combine(Board one, Board two){
        Board b = new Board(one.getBoard().length, one.getBoard()[0].length);

        //copy everything in from first parent
        for(int i = 0; i < one.getNodes().size(); i++){
            Island island = one.getNodes().get(i).copyIsland();
            b.getBoard()[island.getX()][island.getY()] = island;
            b.getNodes().add(island);
        }
        b.setAdjacentIslands();

        //change half of it to parent 2
        for(int i = 1; i < two.getNodes().size(); i = i+2){
            Island island = b.getNodes().get(i);
            Island copy = two.getNodes().get(i);
            if(!island.isNorthLocked() && island.getNorthIsland() != null){
                island.setNorthBridges(copy.getNorthBridges());
                island.getNorthIsland().setSouthBridges(copy.getNorthBridges());
            }
            if(!island.isSouthLocked() && island.getSouthIsland() != null){
                island.setSouthBridges(copy.getSouthBridges());
                island.getSouthIsland().setNorthBridges(copy.getSouthBridges());
            }
            if(!island.isEastLocked() && island.getEastIsland() != null){
                island.setEastBridges(copy.getEastBridges());
                island.getEastIsland().setWestBridges(copy.getEastBridges());
            }
            if(!island.isWestLocked() && island.getWestIsland() != null){
                island.setWestBridges(copy.getWestBridges());
                island.getWestIsland().setEastBridges(copy.getWestBridges());
            }
        }

        b.fixBridges();

        return b;
    }

    //return board with highest fitness
    public Board highestFitness(){
        int index = 0;
        double fitness = 0;
        for(int i = 0; i < current.length; i++){
            if(current[i].getFitness() > fitness){
                index = i;
                fitness = current[i].getFitness();
            }
        }
        return current[index];
    }
}
