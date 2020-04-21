//is an island with anumber and connections in the hashi

public class Island {

    //coordinates on the board
    private int x;
    private int y;

    //number of connections this island needs to be complete
    private int connections;

    //points to islands it is connected too
    private Island northIsland;
    private Island southIsland;
    private Island eastIsland;
    private Island westIsland;

    //number of bridges between this island and its connnecting islands
    private int northBridges;
    private int southBridges;
    private int eastBridges;
    private int westBridges;

    //is this bridge locked?
    private boolean northLocked;
    private boolean southLocked;
    private boolean eastLocked;
    private boolean westLocked;

    //acting as a bridge
    private boolean bridge;

    public Island(){
        setNorthBridges(0);
        setSouthBridges(0);
        setEastBridges(0);
        setWestBridges(0);
        setX(0);
        setY(0);
        setConnections(0);
        setNorthLocked(false);
        setSouthLocked(false);
        setEastLocked(false);
        setWestLocked(false);
    }

    //takes in true and sets this island up to act like a bridge
    public Island(boolean bridge){
        this.setBridge(bridge);
    }

    //takes in the x coordinate, y coordinate, and number of connections
    public Island(int x, int y, int connections){
        setX(x);
        setY(y);
        setConnections(connections);
        setNorthBridges(0);
        setSouthBridges(0);
        setEastBridges(0);
        setWestBridges(0);
        setNorthLocked(false);
        setSouthLocked(false);
        setEastLocked(false);
        setWestLocked(false);
    }

    //returns the total number of bridges currently connected to this island
    public int numberOfBridges(){
        return northBridges+southBridges+eastBridges+westBridges;
    }

    //returns the number of adjacent islands
    public int numberOfAdjacentIslands(){
        int count = 0;
        if(northIsland != null){
            count++;
        }
        if(southIsland != null){
            count++;
        }
        if(westIsland != null){
            count++;
        }
        if(eastIsland != null){
            count++;
        }
        return count;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getConnections() {
        return connections;
    }

    public void setConnections(int connections) {
        this.connections = connections;
    }

    public Island getNorthIsland() {
        return northIsland;
    }

    public void setNorthIsland(Island northIsland) {
        this.northIsland = northIsland;
    }

    public Island getSouthIsland() {
        return southIsland;
    }

    public void setSouthIsland(Island southIsland) {
        this.southIsland = southIsland;
    }

    public Island getEastIsland() {
        return eastIsland;
    }

    public void setEastIsland(Island eastIsland) {
        this.eastIsland = eastIsland;
    }

    public Island getWestIsland() {
        return westIsland;
    }

    public void setWestIsland(Island westIsland) {
        this.westIsland = westIsland;
    }

    public int getNorthBridges() {
        return northBridges;
    }

    public void setNorthBridges(int northBridges) {
        if(!northLocked)
            this.northBridges = northBridges;
    }

    public int getSouthBridges() {
        return southBridges;
    }

    public void setSouthBridges(int southBridges) {
        if(!southLocked)
            this.southBridges = southBridges;
    }

    public int getEastBridges() {
        return eastBridges;
    }

    public void setEastBridges(int eastBridges) {
        if(!eastLocked)
            this.eastBridges = eastBridges;
    }

    public int getWestBridges() {
        return westBridges;
    }

    public void setWestBridges(int westBridges) {
        if(!westLocked)
            this.westBridges = westBridges;
    }

    public boolean isNorthLocked() { return northLocked; }

    public void setNorthLocked(boolean northLocked) { this.northLocked = northLocked; }

    public boolean isSouthLocked() { return southLocked; }

    public void setSouthLocked(boolean southLocked) { this.southLocked = southLocked; }

    public boolean isEastLocked() { return eastLocked; }

    public void setEastLocked(boolean eastLocked) { this.eastLocked = eastLocked; }

    public boolean isWestLocked() { return westLocked; }

    public void setWestLocked(boolean westLocked) { this.westLocked = westLocked; }

    public boolean isBridge() { return bridge; }

    public void setBridge(boolean bridge) { this.bridge = bridge; }

    public int viableAdjacentIslands(){
        int answer = 0;
        if(northIsland != null && !northLocked){
            answer++;
        }
        if(southIsland != null && !southLocked){
            answer++;
        }
        if(westIsland != null && !westLocked){
            answer++;
        }
        if(eastIsland != null && !eastLocked){
            answer++;
        }
        return answer;
    }

    public Island copyIsland(){
        Island i = new Island();
        i.setNorthBridges(northBridges);
        i.setSouthBridges(southBridges);
        i.setWestBridges(westBridges);
        i.setEastBridges(eastBridges);
        i.setConnections(connections);
        i.setX(x);
        i.setY(y);
        i.setNorthLocked(northLocked);
        i.setSouthLocked(southLocked);
        i.setEastLocked(eastLocked);
        i.setWestLocked(westLocked);
        return i;
    }
}
