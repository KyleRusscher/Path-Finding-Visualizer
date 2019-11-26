package sample;



public class Node {
    private boolean isWall;
    private boolean isStart;
    private boolean isEnd;
    private boolean isVisited;
    private boolean isPath;
    private int distance;
    private int row;
    private int column;
    private Node prevNode;


    public Node() {
        row = -1;
        column = -1;
        distance = 100000;
        isWall = false;
        isStart = false;
        isEnd = false;
        isVisited = false;
        isPath = false;
        prevNode = null;

    }
    public void empty(){
        isWall = false;
        isStart = false;
        isEnd = false;
        isVisited = false;
        isPath = false;
        prevNode = null;
        distance = 100000;
    }

    public boolean isWall() {
        return isWall;
    }

    public void setWall(boolean wall) {
        isWall = wall;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public boolean isPath() {
        return isPath;
    }

    public void setPath(boolean path) {
        isPath = path;
    }

    public void setDistance(int dist){distance = dist;}

    public int getDistance(){return this.distance;}

    public void setRow(int row){ this.row = row;}

    public int getRow(){return this.row;}

    public void setColumn(int column){this.column = column;}

    public int getColumn(){return this.column;}

    public void setPrevNode(Node node){this.prevNode = node;}

    public Node getPrevNode(){return this.prevNode;}

    public boolean isEmpty(){
        return this.isWall == false && this.isEnd == false && this.isStart == false
                && this.isPath == false && this.isVisited == false && this.prevNode == null
                && this.distance == 100000;
    }

}
