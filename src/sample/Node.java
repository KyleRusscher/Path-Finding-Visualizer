package sample;



public class Node {
    private boolean isWall;
    private boolean isStart;
    private boolean isEnd;
    private boolean isVisited;
    private boolean isPath;


    public Node() {
        isWall = false;
        isStart = false;
        isEnd = false;
        isVisited = false;
        isPath = false;

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

}
