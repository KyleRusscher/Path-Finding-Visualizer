package sample;

/**********************************************************************
 * Node class for path finding algorithm project
 @author Joseph Turnbull, Kyle Russcher and Max Gagnon
 @version Fall 2019
 *********************************************************************/
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


    /**
     * Constructor that sets all the values to default
     */
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


    /**
     * Sets this current node's values to empty
     */
    public void empty() {
        isWall = false;
        isStart = false;
        isEnd = false;
        isVisited = false;
        isPath = false;
        prevNode = null;
        distance = 100000;
    }


    /**
     * Accessor to see if this current node is a wall
     * @return boolean value whether this node is a wall
     */
    public boolean isWall() {
        return isWall;
    }


    /**
     * Mutator to set this current node to be a wall
     * @param wall boolean value to set this node to a wall
     * or not
     */
    public void setWall(boolean wall) {
        isWall = wall;
    }


    /**
     * Accessor to see if this current node is the start node
     * @return boolean value whether this node is the start
     */
    public boolean isStart() {
        return isStart;
    }


    /**
     * Mutator to set this current node to be the start node
     * @param start boolean value to set this node to the start node
     * or not
     */
    public void setStart(boolean start) {
        isStart = start;
    }


    /**
     * Accessor to see if this current node is the end node
     * @return boolean value whether this node is the end node
     */
    public boolean isEnd() {
        return isEnd;
    }


    /**
     * Mutator to set this current node to be the end node
     * @param end boolean value to set this node to the end node
     * or not
     */
    public void setEnd(boolean end) {
        isEnd = end;
    }


    /**
     * Accessor to see if this current node has been visited by a path
     * finding algorithm
     * @return boolean value whether this node has been visited
     */
    public boolean isVisited() {
        return isVisited;
    }


    /**
     * Mutator to set this current node to be visited when looking for
     * the shortest path
     * @param visited boolean value to set this node to be visited
     * or not
     */
    public void setVisited(boolean visited) {
        isVisited = visited;
    }


    /**
     * Accessor to see if this current node is on the shortest path
     * @return boolean value whether this node is on the shortest path
     */
    public boolean isPath() {
        return isPath;
    }


    /**
     * Mutator to set this current node to on the shortest path
     * @param path boolean value to set this node to be on the
     * shortest path
     */
    public void setPath(boolean path) {
        isPath = path;
    }
    

    /**
     * Accessor to get the current distance from this node to the start
     * node
     * @return int value of distance to start node
     */
    public int getDistance() {
        return this.distance;
    }


    /**
     * Mutator to set the distance of this current node
     * @param dist int value to set as this node's distance
     */
    public void setDistance(int dist) {
        distance = dist;
    }


    /**
     * Mutator to set this current node row value for its coordinate
     * @param row int value to be the row value of this node
     */
    public void setRow(int row) {
        this.row = row;
    }


    /**
     * Accessor to get the current row value of this node
     * node
     * @return int value of row of this node
     */
    public int getRow() {
        return this.row;
    }


    /**
     * Mutator to set this current node column value for its coordinate
     * @param column int value to be the column value of this node
     */
    public void setColumn(int column) {
        this.column = column;
    }


    /**
     * Accessor to get the current column value of this node
     * node
     * @return int value of row of this column
     */
    public int getColumn() {
        return this.column;
    }


    /**
     * Mutator to set the previous node of this current node. This is
     * used in keeping track of prev node if it is on the shortest path
     * @param node value to be set to the this node's previous value
     */
    public void setPrevNode(Node node) {
        this.prevNode = node;
    }


    /**
     * Accessor to get the previous node of this current node
     * node. This prev node is used in keeping track of the prev node
     * if it is on the shortest path
     * @return int value of row of this column
     */
    public Node getPrevNode() {
        return this.prevNode;
    }


    /**
     * Checks to see if this node has all its values set to default
     * @return boolean value whether this node is empty or not
     */
    public boolean isEmpty() {
        return this.isWall == false && this.isEnd == false &&
                this.isStart == false && this.isPath == false &&
                this.isVisited == false && this.prevNode == null &&
                this.distance == 100000;
    }

}
