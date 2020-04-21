public class Main {

    public static void main(String[] args){
        if(args.length < 1){
            System.out.println("Please type in the location of a file holding a Hashiboard");
        }
        Board board = FileParser.parse(args[0]);
        determineInitialHeldFacts(board);
        Board best = null;
        long averageTime = 0;
        double averageFitness = 0;
        for(int n = 0; n < 30; n++) {
            //GUI gui = new GUI(board);
            long startTime = System.nanoTime();
            GA ga = new GA(board, 100);
            Board highest = null;
            for (int i = 0; i < 1000; i++) {
                ga.nextGen();
                highest = ga.highestFitness();
                if (highest.getFitness() == 100) {
                    break;
                }
            }
            if (highest != null) {
                //new GUI(highest);
                averageFitness += highest.getFitness();
            }
            if(best == null){
                best = highest;
            } else{
                if(highest.getFitness() > best.getFitness()){
                    best = highest;
                }
            }
            long endTime = System.nanoTime();
            averageTime += (endTime - startTime);
            System.out.println("Fitness = " + highest.getFitness());
            System.out.println("Time taken = " + ((endTime - startTime) / (1000000000.0)));
        }
        if(best != null){
            new GUI(best);
        }
        System.out.println("Average Time = "+((averageTime/1000000000.0)/30));
        System.out.println("Average Fitness = "+(averageFitness/30.0));
    }

    //determine things that are for sure true about the board
    public static void determineInitialHeldFacts(Board b){
        for(Island i:b.getNodes()){

            //if an island has an 8 on it, we know for a fact all nodes surrounding it are connected with 2 bridges
            if(i.getConnections() == 8){

                i.setNorthBridges(2);
                i.setNorthLocked(true);
                i.getNorthIsland().setSouthBridges(2);
                i.getNorthIsland().setSouthLocked(true);
                //add bridges into the board
                for(int y1 = i.getY()-1; y1 == i.getNorthIsland().getY()+1; y1--){
                    b.getBoard()[i.getX()][y1] = new Island(true);
                }

                i.setSouthBridges(2);
                i.setSouthLocked(true);
                i.getSouthIsland().setNorthBridges(2);
                i.getSouthIsland().setNorthLocked(true);
                //add bridges into the board
                for(int y2 = i.getY()+1; y2 == i.getSouthIsland().getY()-1; y2++){
                    b.getBoard()[i.getX()][y2] = new Island(true);
                }

                i.setWestBridges(2);
                i.setWestLocked(true);
                i.getWestIsland().setEastBridges(2);
                i.getWestIsland().setEastLocked(true);
                //add bridges into the board
                for(int x1 = i.getX()-1; x1 == i.getWestIsland().getX()+1; x1--){
                    b.getBoard()[x1][i.getY()] = new Island(true);
                }

                i.setEastBridges(2);
                i.setEastLocked(true);
                i.getEastIsland().setWestBridges(2);
                i.getEastIsland().setWestLocked(true);
                //add bridges into the board
                for(int x2 = i.getX()+1; x2 == i.getEastIsland().getX()-1; x2++){
                    b.getBoard()[x2][i.getY()] = new Island(true);
                }
            }//end if island is 8

            if(i.getConnections() == 6 && i.numberOfAdjacentIslands() == 3){

                if(i.getNorthIsland() != null) {
                    i.setNorthBridges(2);
                    i.setNorthLocked(true);
                    i.getNorthIsland().setSouthBridges(2);
                    i.getNorthIsland().setSouthLocked(true);
                    //add bridges into the board
                    for (int y1 = i.getY() - 1; y1 == i.getNorthIsland().getY() + 1; y1--) {
                        b.getBoard()[i.getX()][y1] = new Island(true);
                    }
                }

                if(i.getSouthIsland() != null) {
                    i.setSouthBridges(2);
                    i.setSouthLocked(true);
                    i.getSouthIsland().setNorthBridges(2);
                    i.getSouthIsland().setNorthLocked(true);
                    //add bridges into the board
                    for (int y2 = i.getY() + 1; y2 == i.getSouthIsland().getY() - 1; y2++) {
                        b.getBoard()[i.getX()][y2] = new Island(true);
                    }
                }

                if(i.getWestIsland() != null) {
                    i.setWestBridges(2);
                    i.setWestLocked(true);
                    i.getWestIsland().setEastBridges(2);
                    i.getWestIsland().setEastLocked(true);
                    //add bridges into the board
                    for (int x1 = i.getX() - 1; x1 == i.getWestIsland().getX() + 1; x1--) {
                        b.getBoard()[x1][i.getY()] = new Island(true);
                    }
                }

                if(i.getEastIsland() != null) {
                    i.setEastBridges(2);
                    i.setEastLocked(true);
                    i.getEastIsland().setWestBridges(2);
                    i.getEastIsland().setWestLocked(true);
                    //add bridges into the board
                    for (int x2 = i.getX() + 1; x2 == i.getEastIsland().getX() - 1; x2++) {
                        b.getBoard()[x2][i.getY()] = new Island(true);
                    }
                }
            }//end if island has 6 connections and only 3 adjacent islands

            if(i.getConnections() == 4 && i.numberOfAdjacentIslands() == 2){
                if(i.getNorthIsland() != null) {
                    i.setNorthBridges(2);
                    i.setNorthLocked(true);
                    i.getNorthIsland().setSouthBridges(2);
                    i.getNorthIsland().setSouthLocked(true);
                    //add bridges into the board
                    for (int y1 = i.getY() - 1; y1 == i.getNorthIsland().getY() + 1; y1--) {
                        b.getBoard()[i.getX()][y1] = new Island(true);
                    }
                }

                if(i.getSouthIsland() != null) {
                    i.setSouthBridges(2);
                    i.setSouthLocked(true);
                    i.getSouthIsland().setNorthBridges(2);
                    i.getSouthIsland().setNorthLocked(true);
                    //add bridges into the board
                    for (int y2 = i.getY() + 1; y2 == i.getSouthIsland().getY() - 1; y2++) {
                        b.getBoard()[i.getX()][y2] = new Island(true);
                    }
                }

                if(i.getWestIsland() != null) {
                    i.setWestBridges(2);
                    i.setWestLocked(true);
                    i.getWestIsland().setEastBridges(2);
                    i.getWestIsland().setEastLocked(true);
                    //add bridges into the board
                    for (int x1 = i.getX() - 1; x1 == i.getWestIsland().getX() + 1; x1--) {
                        b.getBoard()[x1][i.getY()] = new Island(true);
                    }
                }

                if(i.getEastIsland() != null) {
                    i.setEastBridges(2);
                    i.setEastLocked(true);
                    i.getEastIsland().setWestBridges(2);
                    i.getEastIsland().setWestLocked(true);
                    //add bridges into the board
                    for (int x2 = i.getX() + 1; x2 == i.getEastIsland().getX() - 1; x2++) {
                        b.getBoard()[x2][i.getY()] = new Island(true);
                    }
                }
            }//end if island equals 4 and only has 2 adjacent islands

            if(i.getConnections() == 2 && i.numberOfAdjacentIslands() == 1){
                if(i.getNorthIsland() != null) {
                    i.setNorthBridges(2);
                    i.setNorthLocked(true);
                    i.getNorthIsland().setSouthBridges(2);
                    i.getNorthIsland().setSouthLocked(true);
                    //add bridges into the board
                    for (int y1 = i.getY() - 1; y1 == i.getNorthIsland().getY() + 1; y1--) {
                        b.getBoard()[i.getX()][y1] = new Island(true);
                    }
                }

                if(i.getSouthIsland() != null) {
                    i.setSouthBridges(2);
                    i.setSouthLocked(true);
                    i.getSouthIsland().setNorthBridges(2);
                    i.getSouthIsland().setNorthLocked(true);
                    //add bridges into the board
                    for (int y2 = i.getY() + 1; y2 == i.getSouthIsland().getY() - 1; y2++) {
                        b.getBoard()[i.getX()][y2] = new Island(true);
                    }
                }

                if(i.getWestIsland() != null) {
                    i.setWestBridges(2);
                    i.setWestLocked(true);
                    i.getWestIsland().setEastBridges(2);
                    i.getWestIsland().setEastLocked(true);
                    //add bridges into the board
                    for (int x1 = i.getX() - 1; x1 == i.getWestIsland().getX() + 1; x1--) {
                        b.getBoard()[x1][i.getY()] = new Island(true);
                    }
                }

                if(i.getEastIsland() != null) {
                    i.setEastBridges(2);
                    i.setEastLocked(true);
                    i.getEastIsland().setWestBridges(2);
                    i.getEastIsland().setWestLocked(true);
                    //add bridges into the board
                    for (int x2 = i.getX() + 1; x2 == i.getEastIsland().getX() - 1; x2++) {
                        b.getBoard()[x2][i.getY()] = new Island(true);
                    }
                }
            }//end if island is 2 and only has 1 adjacent island

            if(i.getConnections() == 1 && i.numberOfAdjacentIslands() == 1){
                if(i.getNorthIsland() != null) {
                    i.setNorthBridges(1);
                    i.setNorthLocked(true);
                    i.getNorthIsland().setSouthBridges(1);
                    i.getNorthIsland().setSouthLocked(true);
                    //add bridges into the board
                    for (int y1 = i.getY() - 1; y1 == i.getNorthIsland().getY() + 1; y1--) {
                        b.getBoard()[i.getX()][y1] = new Island(true);
                    }
                }

                if(i.getSouthIsland() != null) {
                    i.setSouthBridges(1);
                    i.setSouthLocked(true);
                    i.getSouthIsland().setNorthBridges(1);
                    i.getSouthIsland().setNorthLocked(true);
                    //add bridges into the board
                    for (int y2 = i.getY() + 1; y2 == i.getSouthIsland().getY() - 1; y2++) {
                        b.getBoard()[i.getX()][y2] = new Island(true);
                    }
                }

                if(i.getWestIsland() != null) {
                    i.setWestBridges(1);
                    i.setWestLocked(true);
                    i.getWestIsland().setEastBridges(1);
                    i.getWestIsland().setEastLocked(true);
                    //add bridges into the board
                    for (int x1 = i.getX() - 1; x1 == i.getWestIsland().getX() + 1; x1--) {
                        b.getBoard()[x1][i.getY()] = new Island(true);
                    }
                }

                if(i.getEastIsland() != null) {
                    i.setEastBridges(1);
                    i.setEastLocked(true);
                    i.getEastIsland().setWestBridges(1);
                    i.getEastIsland().setWestLocked(true);
                    //add bridges into the board
                    for (int x2 = i.getX() + 1; x2 == i.getEastIsland().getX() - 1; x2++) {
                        b.getBoard()[x2][i.getY()] = new Island(true);
                    }
                }
            }//end if island is 1 and has 1 adjacent island

            //if the island needs 7 bridges, we know that there is at least 1 bridge in each direction
            if(i.getConnections() == 7){

                i.setNorthBridges(1);
                i.getNorthIsland().setSouthBridges(1);
                //add bridges into the board
                for (int y1 = i.getY() - 1; y1 == i.getNorthIsland().getY() + 1; y1--) {
                    b.getBoard()[i.getX()][y1] = new Island(true);
                }

                i.setSouthBridges(1);
                i.getSouthIsland().setNorthBridges(1);
                //add bridges into the board
                for (int y2 = i.getY() + 1; y2 == i.getSouthIsland().getY() - 1; y2++) {
                    b.getBoard()[i.getX()][y2] = new Island(true);
                }

                i.setWestBridges(1);
                i.getWestIsland().setEastBridges(1);
                //add bridges into the board
                for (int x1 = i.getX() - 1; x1 == i.getWestIsland().getX() + 1; x1--) {
                    b.getBoard()[x1][i.getY()] = new Island(true);
                }

                i.setEastBridges(1);
                i.getEastIsland().setWestBridges(1);
                //add bridges into the board
                for (int x2 = i.getX() + 1; x2 == i.getEastIsland().getX() - 1; x2++) {
                    b.getBoard()[x2][i.getY()] = new Island(true);
                }

            }//end if island needs 7 connections

            if(i.getConnections() == 5 && i.numberOfAdjacentIslands() == 3){
                if(i.getNorthIsland() != null) {
                    i.setNorthBridges(1);
                    i.getNorthIsland().setSouthBridges(1);
                    //add bridges into the board
                    for (int y1 = i.getY() - 1; y1 == i.getNorthIsland().getY() + 1; y1--) {
                        b.getBoard()[i.getX()][y1] = new Island(true);
                    }
                }

                if(i.getSouthIsland() != null) {
                    i.setSouthBridges(1);
                    i.getSouthIsland().setNorthBridges(1);
                    //add bridges into the board
                    for (int y2 = i.getY() + 1; y2 == i.getSouthIsland().getY() - 1; y2++) {
                        b.getBoard()[i.getX()][y2] = new Island(true);
                    }
                }

                if(i.getWestIsland() != null) {
                    i.setWestBridges(1);
                    i.getWestIsland().setEastBridges(1);
                    //add bridges into the board
                    for (int x1 = i.getX() - 1; x1 == i.getWestIsland().getX() + 1; x1--) {
                        b.getBoard()[x1][i.getY()] = new Island(true);
                    }
                }

                if(i.getEastIsland() != null) {
                    i.setEastBridges(1);
                    i.getEastIsland().setWestBridges(1);
                    //add bridges into the board
                    for (int x2 = i.getX() + 1; x2 == i.getEastIsland().getX() - 1; x2++) {
                        b.getBoard()[x2][i.getY()] = new Island(true);
                    }
                }
            }//end if island needs 5 connections and has 3 adjacent islands

            if(i.getConnections() == 3 && i.numberOfAdjacentIslands() == 2){
                if(i.getNorthIsland() != null) {
                    i.setNorthBridges(1);
                    i.getNorthIsland().setSouthBridges(1);
                    //add bridges into the board
                    for (int y1 = i.getY() - 1; y1 == i.getNorthIsland().getY() + 1; y1--) {
                        b.getBoard()[i.getX()][y1] = new Island(true);
                    }
                }

                if(i.getSouthIsland() != null) {
                    i.setSouthBridges(1);
                    i.getSouthIsland().setNorthBridges(1);
                    //add bridges into the board
                    for (int y2 = i.getY() + 1; y2 == i.getSouthIsland().getY() - 1; y2++) {
                        b.getBoard()[i.getX()][y2] = new Island(true);
                    }
                }

                if(i.getWestIsland() != null) {
                    i.setWestBridges(1);
                    i.getWestIsland().setEastBridges(1);
                    //add bridges into the board
                    for (int x1 = i.getX() - 1; x1 == i.getWestIsland().getX() + 1; x1--) {
                        b.getBoard()[x1][i.getY()] = new Island(true);
                    }
                }

                if(i.getEastIsland() != null) {
                    i.setEastBridges(1);
                    i.getEastIsland().setWestBridges(1);
                    //add bridges into the board
                    for (int x2 = i.getX() + 1; x2 == i.getEastIsland().getX() - 1; x2++) {
                        b.getBoard()[x2][i.getY()] = new Island(true);
                    }
                }
            }//end if islands has 3 connections and 2 adjacent islands

            //if an island who only needs 1 connection total and it has an adjacent island that only needs 1 to complete it, they should never connect
            if(i.getConnections() == 1){
                if(i.getNorthIsland() != null && i.getNorthIsland().getConnections() == 1){
                    i.setNorthBridges(0);
                    i.setNorthLocked(true);
                    i.getNorthIsland().setSouthBridges(0);
                    i.getNorthIsland().setSouthLocked(true);
                }
                if(i.getSouthIsland() != null && i.getSouthIsland().getConnections() == 1){
                    i.setSouthBridges(0);
                    i.setSouthLocked(true);
                    i.getSouthIsland().setNorthBridges(0);
                    i.getSouthIsland().setNorthLocked(true);
                }
                if(i.getWestIsland() != null && i.getWestIsland().getConnections() == 1){
                    i.setWestBridges(0);
                    i.setWestLocked(true);
                    i.getWestIsland().setEastBridges(0);
                    i.getWestIsland().setEastLocked(true);
                }
                if(i.getEastIsland() != null && i.getEastIsland().getConnections() == 1){
                    i.setEastBridges(0);
                    i.setEastLocked(true);
                    i.getEastIsland().setWestBridges(0);
                    i.getEastIsland().setWestLocked(true);
                }
            }//end if there are islands with 1 connection adjacent to each other
        }//end for loop

        //go through this again but check if any of the 7, 5, 3 nodes can be finished and locked
        for (Island i:b.getNodes()) {

            if(i.getConnections() == 7){
                if(i.getNorthIsland().getConnections() == i.getNorthIsland().numberOfBridges() && i.getNorthBridges() == 1 && i.isNorthLocked()){
                    i.setNorthLocked(true);
                    i.getNorthIsland().setSouthLocked(true);

                    i.setSouthBridges(2);
                    i.setSouthLocked(true);
                    i.getSouthIsland().setNorthBridges(2);
                    i.getSouthIsland().setNorthLocked(true);

                    i.setWestBridges(2);
                    i.setWestLocked(true);
                    i.getWestIsland().setEastBridges(2);
                    i.getWestIsland().setEastLocked(true);

                    i.setEastBridges(2);
                    i.setEastLocked(true);
                    i.getEastIsland().setWestBridges(2);
                    i.getEastIsland().setWestLocked(true);
                }

                if(i.getSouthIsland().getConnections() == i.getSouthIsland().numberOfBridges() && i.getSouthBridges() == 1 && i.isSouthLocked()){
                    i.setSouthLocked(true);
                    i.getSouthIsland().setNorthLocked(true);

                    i.setNorthBridges(2);
                    i.setNorthLocked(true);
                    i.getNorthIsland().setSouthBridges(2);
                    i.getNorthIsland().setSouthLocked(true);

                    i.setWestBridges(2);
                    i.setWestLocked(true);
                    i.getWestIsland().setEastBridges(2);
                    i.getWestIsland().setEastLocked(true);

                    i.setEastBridges(2);
                    i.setEastLocked(true);
                    i.getEastIsland().setWestBridges(2);
                    i.getEastIsland().setWestLocked(true);
                }

                if(i.getWestIsland().getConnections() == i.getWestIsland().numberOfBridges() && i.getWestBridges() == 1 && i.isWestLocked()){
                    i.setWestLocked(true);
                    i.getWestIsland().setEastLocked(true);

                    i.setNorthBridges(2);
                    i.setNorthLocked(true);
                    i.getNorthIsland().setSouthBridges(2);
                    i.getNorthIsland().setSouthLocked(true);

                    i.setSouthBridges(2);
                    i.setSouthLocked(true);
                    i.getSouthIsland().setNorthBridges(2);
                    i.getSouthIsland().setNorthLocked(true);

                    i.setEastBridges(2);
                    i.setEastLocked(true);
                    i.getEastIsland().setWestBridges(2);
                    i.getEastIsland().setWestLocked(true);
                }

                if(i.getEastIsland().getConnections() == i.getEastIsland().numberOfBridges() && i.getEastBridges() == 1 && i.isEastLocked()){
                    i.setEastLocked(true);
                    i.getEastIsland().setWestLocked(true);

                    i.setNorthBridges(2);
                    i.setNorthLocked(true);
                    i.getNorthIsland().setSouthBridges(2);
                    i.getNorthIsland().setSouthLocked(true);

                    i.setSouthBridges(2);
                    i.setSouthLocked(true);
                    i.getSouthIsland().setNorthBridges(2);
                    i.getSouthIsland().setNorthLocked(true);

                    i.setWestBridges(2);
                    i.setWestLocked(true);
                    i.getWestIsland().setEastBridges(2);
                    i.getWestIsland().setEastLocked(true);
                }
            }//end if island has 7 connections

            if(i.getConnections() == 5 && i.numberOfAdjacentIslands() == 3){
                if(i.getNorthIsland() != null && i.getNorthIsland().getConnections() == i.getNorthIsland().numberOfBridges() && i.getNorthBridges() == 1 && i.isNorthLocked()){
                    i.setNorthLocked(true);
                    i.getNorthIsland().setSouthLocked(true);

                    if(i.getSouthIsland() != null) {
                        i.setSouthBridges(2);
                        i.setSouthLocked(true);
                        i.getSouthIsland().setNorthBridges(2);
                        i.getSouthIsland().setNorthLocked(true);
                    }

                    if(i.getWestIsland() != null) {
                        i.setWestBridges(2);
                        i.setWestLocked(true);
                        i.getWestIsland().setEastBridges(2);
                        i.getWestIsland().setEastLocked(true);
                    }

                    if(i.getEastIsland() != null) {
                        i.setEastBridges(2);
                        i.setEastLocked(true);
                        i.getEastIsland().setWestBridges(2);
                        i.getEastIsland().setWestLocked(true);
                    }
                }

                if(i.getSouthIsland() != null && i.getSouthIsland().getConnections() == i.getSouthIsland().numberOfBridges() && i.getSouthBridges() == 1 && i.isSouthLocked()){
                    i.setSouthLocked(true);
                    i.getSouthIsland().setNorthLocked(true);

                    if(i.getNorthIsland() != null) {
                        i.setNorthBridges(2);
                        i.setNorthLocked(true);
                        i.getNorthIsland().setSouthBridges(2);
                        i.getNorthIsland().setSouthLocked(true);
                    }

                    if(i.getWestIsland() != null) {
                        i.setWestBridges(2);
                        i.setWestLocked(true);
                        i.getWestIsland().setEastBridges(2);
                        i.getWestIsland().setEastLocked(true);
                    }

                    if(i.getEastIsland() != null) {
                        i.setEastBridges(2);
                        i.setEastLocked(true);
                        i.getEastIsland().setWestBridges(2);
                        i.getEastIsland().setWestLocked(true);
                    }
                }

                if(i.getWestIsland() != null && i.getWestIsland().getConnections() == i.getWestIsland().numberOfBridges() && i.getWestBridges() == 1 && i.isWestLocked()){
                    i.setWestLocked(true);
                    i.getWestIsland().setEastLocked(true);

                    if(i.getNorthIsland() != null) {
                        i.setNorthBridges(2);
                        i.setNorthLocked(true);
                        i.getNorthIsland().setSouthBridges(2);
                        i.getNorthIsland().setSouthLocked(true);
                    }

                    if(i.getSouthIsland() != null) {
                        i.setSouthBridges(2);
                        i.setSouthLocked(true);
                        i.getSouthIsland().setNorthBridges(2);
                        i.getSouthIsland().setNorthLocked(true);
                    }

                    if(i.getEastIsland() != null) {
                        i.setEastBridges(2);
                        i.setEastLocked(true);
                        i.getEastIsland().setWestBridges(2);
                        i.getEastIsland().setWestLocked(true);
                    }
                }

                if(i.getEastIsland() != null && i.getEastIsland().getConnections() == i.getEastIsland().numberOfBridges() && i.getEastBridges() == 1 && i.isEastLocked()){
                    i.setEastLocked(true);
                    i.getEastIsland().setWestLocked(true);

                    if(i.getNorthIsland() != null) {
                        i.setNorthBridges(2);
                        i.setNorthLocked(true);
                        i.getNorthIsland().setSouthBridges(2);
                        i.getNorthIsland().setSouthLocked(true);
                    }

                    if(i.getSouthIsland() != null) {
                        i.setSouthBridges(2);
                        i.setSouthLocked(true);
                        i.getSouthIsland().setNorthBridges(2);
                        i.getSouthIsland().setNorthLocked(true);
                    }

                    if(i.getWestIsland() != null) {
                        i.setWestBridges(2);
                        i.setWestLocked(true);
                        i.getWestIsland().setEastBridges(2);
                        i.getWestIsland().setEastLocked(true);
                    }
                }
            }//end if island has 5 connections and 3 adjacent islands

            if(i.getConnections() == 3 && i.numberOfAdjacentIslands() == 2){
                if(i.getNorthIsland() != null && i.getNorthIsland().getConnections() == i.getNorthIsland().numberOfBridges() && i.getNorthBridges() == 1 && i.isNorthLocked()){
                    i.setNorthLocked(true);
                    i.getNorthIsland().setSouthLocked(true);

                    if(i.getSouthIsland() != null) {
                        i.setSouthBridges(2);
                        i.setSouthLocked(true);
                        i.getSouthIsland().setNorthBridges(2);
                        i.getSouthIsland().setNorthLocked(true);
                    }

                    if(i.getWestIsland() != null) {
                        i.setWestBridges(2);
                        i.setWestLocked(true);
                        i.getWestIsland().setEastBridges(2);
                        i.getWestIsland().setEastLocked(true);
                    }

                    if(i.getEastIsland() != null) {
                        i.setEastBridges(2);
                        i.setEastLocked(true);
                        i.getEastIsland().setWestBridges(2);
                        i.getEastIsland().setWestLocked(true);
                    }
                }

                if(i.getSouthIsland() != null && i.getSouthIsland().getConnections() == i.getSouthIsland().numberOfBridges() && i.getSouthBridges() == 1 && i.isSouthLocked()){
                    i.setSouthLocked(true);
                    i.getSouthIsland().setNorthLocked(true);

                    if(i.getNorthIsland() != null) {
                        i.setNorthBridges(2);
                        i.setNorthLocked(true);
                        i.getNorthIsland().setSouthBridges(2);
                        i.getNorthIsland().setSouthLocked(true);
                    }

                    if(i.getWestIsland() != null) {
                        i.setWestBridges(2);
                        i.setWestLocked(true);
                        i.getWestIsland().setEastBridges(2);
                        i.getWestIsland().setEastLocked(true);
                    }

                    if(i.getEastIsland() != null) {
                        i.setEastBridges(2);
                        i.setEastLocked(true);
                        i.getEastIsland().setWestBridges(2);
                        i.getEastIsland().setWestLocked(true);
                    }
                }

                if(i.getWestIsland() != null && i.getWestIsland().getConnections() == i.getWestIsland().numberOfBridges() && i.getWestBridges() == 1 && i.isWestLocked()){
                    i.setWestLocked(true);
                    i.getWestIsland().setEastLocked(true);

                    if(i.getNorthIsland() != null) {
                        i.setNorthBridges(2);
                        i.setNorthLocked(true);
                        i.getNorthIsland().setSouthBridges(2);
                        i.getNorthIsland().setSouthLocked(true);
                    }

                    if(i.getSouthIsland() != null) {
                        i.setSouthBridges(2);
                        i.setSouthLocked(true);
                        i.getSouthIsland().setNorthBridges(2);
                        i.getSouthIsland().setNorthLocked(true);
                    }

                    if(i.getEastIsland() != null) {
                        i.setEastBridges(2);
                        i.setEastLocked(true);
                        i.getEastIsland().setWestBridges(2);
                        i.getEastIsland().setWestLocked(true);
                    }
                }

                if(i.getEastIsland() != null && i.getEastIsland().getConnections() == i.getEastIsland().numberOfBridges() && i.getEastBridges() == 1 && i.isEastLocked()){
                    i.setEastLocked(true);
                    i.getEastIsland().setWestLocked(true);

                    if(i.getNorthIsland() != null) {
                        i.setNorthBridges(2);
                        i.setNorthLocked(true);
                        i.getNorthIsland().setSouthBridges(2);
                        i.getNorthIsland().setSouthLocked(true);
                    }

                    if(i.getSouthIsland() != null) {
                        i.setSouthBridges(2);
                        i.setSouthLocked(true);
                        i.getSouthIsland().setNorthBridges(2);
                        i.getSouthIsland().setNorthLocked(true);
                    }

                    if(i.getWestIsland() != null) {
                        i.setWestBridges(2);
                        i.setWestLocked(true);
                        i.getWestIsland().setEastBridges(2);
                        i.getWestIsland().setEastLocked(true);
                    }
                }
            }//end if island has 3 connections and 2 adjacent islands
        }//end for loop

        //now go through and lock everything that has the correct number of connections period
        for(Island i:b.getNodes()){
            if(i.getConnections() == i.numberOfBridges()){
                if(i.getNorthIsland() != null){
                    i.setNorthLocked(true);
                    i.getNorthIsland().setSouthLocked(true);
                }
                if(i.getSouthIsland() != null){
                    i.setSouthLocked(true);
                    i.getSouthIsland().setNorthLocked(true);
                }
                if(i.getWestIsland() != null){
                    i.setWestLocked(true);
                    i.getWestIsland().setEastLocked(true);
                }
                if(i.getEastIsland() != null){
                    i.setEastLocked(true);
                    i.getEastIsland().setWestLocked(true);
                }
            }//end if island connections needed == island bridges

            //check if there are bridges between nodes and if so, lock them as never connecting in that direction
            if(i.getNorthIsland() != null && !i.isNorthLocked() && i.getNorthBridges() == 0){
                int y = i.getY()-1;
                while(y != i.getNorthIsland().getY()){
                    if(b.getBoard()[i.getX()][y] != null && b.getBoard()[i.getX()][y].isBridge()){
                        i.setNorthLocked(true);
                        i.getNorthIsland().setSouthLocked(true);
                        break;
                    }
                    y--;
                }
            }
            if(i.getSouthIsland() != null && !i.isSouthLocked() && i.getSouthBridges() == 0){
                int y = i.getY() + 1;
                while(y != i.getSouthIsland().getY()){
                    if(b.getBoard()[i.getX()][y] != null && b.getBoard()[i.getX()][y].isBridge()){
                        i.setSouthLocked(true);
                        i.getSouthIsland().setNorthLocked(true);
                        break;
                    }
                    y++;
                }
            }
            if(i.getWestIsland() != null && !i.isWestLocked() && i.getWestBridges() == 0){
                int x = i.getX()-1;
                while(x != i.getWestIsland().getX()){
                    if(b.getBoard()[x][i.getY()] != null && b.getBoard()[x][i.getY()].isBridge()){
                        i.setWestLocked(true);
                        i.getWestIsland().setEastLocked(true);
                        break;
                    }
                    x--;
                }
            }
            if(i.getEastIsland() != null && !i.isEastLocked() && i.getEastBridges() == 0){
                int x = i.getX()+1;
                while(x != i.getEastIsland().getX()){
                    if(b.getBoard()[x][i.getY()] != null && b.getBoard()[x][i.getY()].isBridge()){
                        i.setEastLocked(true);
                        i.getEastIsland().setWestLocked(true);
                        break;
                    }
                    x++;
                }
            }
        }//end for loop
    }
}