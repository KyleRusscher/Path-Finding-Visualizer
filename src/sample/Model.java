package sample;


import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**********************************************************************
 * Model class for path finding algorithm project
 @author Joseph Turnbull, Kyle Russcher and Max Gagnon
 @version Fall 2019
 *********************************************************************/
public class Model {


    enum SelectMode {
        WALL, REMOVE_WALL, START, END
    }

    enum Algorithm {
        DIJKSTRA, BFS, ASTAR
    }

    List<List<Node>> maze;
    List<Node> visitOrder = new ArrayList<>();
    List<Node> shortestPath = new ArrayList<>();

    private SelectMode selectMode;
    private Algorithm algorithm;
    private int startC = -1;
    private int startR = -1;
    private int endC = -1;
    private int endR = -1;

    /**
     * Fast speed setting for visualize
     */
    public final static int FAST_SPEED = 2;

    /**
     * Normal speed setting for visualize
     */
    public final static int NORMAL_SPEED = 20;

    /**
     * Slow speed setting for visualize
     */
    public final static int SLOW_SPEED = 70;

    /**
     * Class speed value
     */
    public int speed = NORMAL_SPEED;

    /**
     * Constructor for Model class
     * Creates the board with all the nodes of size 40x20
     */
    public Model() {
        maze = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            maze.add(new ArrayList<>());
            for (int j = 0; j < 20; j++) {
                maze.get(i).add(new Node());
                maze.get(i).get(j).setRow(j);
                maze.get(i).get(j).setColumn(i);
            }
        }
    }


    /**
     * Accessor for the current mode of current node choice
     * @return current mode of node selection
     */
    public SelectMode getSelectMode() {
        return selectMode;
    }


    /**
     * Mutator for the current mode of the current node
     * @param selectMode Mode for node selection
     */
    public void setSelectMode(SelectMode selectMode) {
        this.selectMode = selectMode;
    }


    /**
     * Accessor for the current algorithm selected
     * @return current algorithm selected
     */
    public Algorithm getAlgorithm() {
        return algorithm;
    }


    /**
     * mutator for the current algorithm
     * @param algorithm current algorithm to be selected
     */
    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }


    /**
     * Checks the current maze to see if there is a start and end node
     * @return whether there is a start and end node or not
     */
    public boolean hasStartAndEnd() {
        boolean start = false;
        boolean end = false;
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 20; j++) {
                if (maze.get(i).get(j).isStart()) {
                    start = true;
                }
                if (maze.get(i).get(j).isEnd()) {
                    end = true;
                }
            }
        }
        return start && end;
    }


    /**
     * Clears board using the empty function and sets al the nodes to
     * default
     */
    public void clearBoard() {
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 20; j++) {
                maze.get(i).get(j).empty();
            }
        }
        startC = -1;
        startR = -1;
        endC = -1;
        endR = -1;
    }


    /**
     * Clears everything from the board except walls, the start node
     * and the end node. Effectively clears all the traces of running
     * an algorithm
     */
    public void clearPath() {
        if (!hasStartAndEnd()) {
            return;
        }
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 20; j++) {
                if (!maze.get(i).get(j).isWall()) {
                    maze.get(i).get(j).empty();
                }
            }
        }
        maze.get(startC).get(startR).setStart(true);
        maze.get(endC).get(endR).setEnd(true);
    }


    /**
     * Takes in a coordinate of the maze and updates the node at that
     * coordinate to the type of the current node selected
     * @param c Column value of node to be updated
     * @param r Row value of node to be updated
     */
    public void selectNode(int c, int r) {
        if (selectMode == SelectMode.WALL) {
            if (!maze.get(c).get(r).isStart() && !maze.get(c).get(r).isEnd()) {
                maze.get(c).get(r).setWall(true);
            }
        }
        if (selectMode == SelectMode.REMOVE_WALL) {
            if (!maze.get(c).get(r).isStart() && !maze.get(c).get(r).isEnd()) {
                maze.get(c).get(r).setWall(false);
            }
        }
        if (selectMode == SelectMode.START) {
            if (!maze.get(c).get(r).isWall() && !maze.get(c).get(r).isEnd()) {
                if (startC == -1 && startR == -1) {
                    maze.get(c).get(r).setStart(true);
                    startC = c;
                    startR = r;
                } else if (startC == c && startR == r) {
                    maze.get(c).get(r).setStart(false);
                    startC = -1;
                    startR = -1;
                }

            }
        }
        if (selectMode == SelectMode.END) {
            if (!maze.get(c).get(r).isWall() && !maze.get(c).get(r).isStart()) {
                if (endC == -1 && endR == -1) {
                    maze.get(c).get(r).setEnd(true);
                    endC = c;
                    endR = r;
                } else if (endC == c && endR == r) {
                    maze.get(c).get(r).setEnd(false);
                    endC = -1;
                    endR = -1;
                }
            }
        }
    }


    private boolean dijkstraOrAStar() {
        maze.get(startC).get(startR).setDistance(0);
        List<Node> nodesStillToCheck = getAllNodes();
        while (nodesStillToCheck.size() != 0) {
            nodesStillToCheck = sortNodes(nodesStillToCheck);
            Node currNode = nodesStillToCheck.get(0);
            nodesStillToCheck.remove(0);
            if (currNode.isWall() == false) {
                // 100000 is our 'infinity' value
                if (currNode.getDistance() == 100000) {
                    return false;
                }
                visitOrder.add(currNode);
                currNode.setVisited(true);
                // we have found the end node
                if (currNode.isEnd()) {
                    return true;
                }
                if (this.algorithm == Algorithm.DIJKSTRA) {
                    updateNeighbours(currNode);
                } else {
                    updateNeighboursForAStar(currNode);
                }
            }
        }
        //will never hit this false
        return false;
    }

    //update the distances of the current node's neighbours
    private void updateNeighbours(Node currNode) {
        List<Node> neighbours = getNeighbours(currNode);
        for (Node neighbour : neighbours) {
            neighbour.setDistance(currNode.getDistance() + 1);
            neighbour.setPrevNode(currNode);
        }
    }

    private void updateNeighboursForAStar(Node currNode) {
        List<Node> neighbours = getNeighbours(currNode);
        for (Node neighbour : neighbours) {
            neighbour.setDistance(currNode.getDistance() + 1 + manhattanDistance(currNode));
            neighbour.setPrevNode(currNode);
        }
    }


    // gets the neighbours of the given node that have been visited
    private List<Node> getNeighbours(Node currNode) {
        List<Node> neighbours = new ArrayList<Node>();
        int col = currNode.getColumn();
        int row = currNode.getRow();
        if (col > 0) {
            neighbours.add(maze.get(col - 1).get(row));
        }
        if (col < maze.size() - 1) {
            neighbours.add(maze.get(col + 1).get(row));
        }
        if (row > 0) {
            neighbours.add(maze.get(col).get(row - 1));
        }
        if (row < maze.get(0).size() - 1) {
            neighbours.add(maze.get(col).get(row + 1));
        }
        return neighbours.stream().filter(neighbour -> !neighbour.isVisited()).collect(Collectors.toList());
    }

    // sort nodes so we can keep accessing the nearest node
    private List<Node> sortNodes(List<Node> workingNodes) {
        workingNodes.sort(Comparator.comparingInt(Node::getDistance));
        return workingNodes;
    }

    // puts all nodes into one list
    private List<Node> getAllNodes() {
        List<Node> nodes = new ArrayList<Node>();
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 20; j++) {
                nodes.add(maze.get(i).get(j));
            }
        }
        return nodes;
    }


    /**
     * Calls path finding algorithm and back tracks from the end node
     * to find the shortest path. Then it updates all the nodes on the
     * shortest path to having their path value equal to true
     * @return boolean value whether there is shortest path found
     */
    public boolean shortestPath() {
        boolean rtnVal = true;
        if (startC == -1 || startR == -1 || endC == -1 || endR == -1) {
            return false;
        } else {
            if (this.algorithm == Algorithm.DIJKSTRA || this.algorithm == Algorithm.ASTAR) {
                rtnVal = dijkstraOrAStar();
            } else if (this.algorithm == Algorithm.BFS) {
                rtnVal = breadthFirstSearch();
            }


            Node currNode = maze.get(endC).get(endR);
            while (currNode != null) {
                currNode.setPath(true);
                shortestPath.add(currNode);
                currNode = currNode.getPrevNode();
            }
            return rtnVal;
        }
    }

    private boolean breadthFirstSearch() {

        LinkedList<Node> queue = new LinkedList<>();
        queue.add(maze.get(startC).get(startR));

        while (!queue.isEmpty()) {
            Node currentNode = queue.removeFirst();
            if (currentNode.isVisited() || currentNode.isWall())
                continue;

            // Mark the node as visited
            currentNode.setVisited(true);
            visitOrder.add(currentNode);
            if (currentNode.isEnd()) {
                return true;
            }
            updateNeighbours(currentNode);
            List<Node> allNeighbors = getNeighbours(currentNode);

            for (Node neighbor : allNeighbors) {
                if (!neighbor.isVisited()) {
                    queue.add(neighbor);
                }
            }
        }
        return false;
    }

    // common tactic to calculate heuristic for A* algorithm
    private int manhattanDistance(Node currentNode) {
        int xChange = Math.abs(currentNode.getRow() - maze.get(endC).get(endR).getRow());
        int yChange = Math.abs(currentNode.getColumn() - maze.get(endC).get(endR).getColumn());

        return (xChange + yChange);
    }


    /**
     * Saves the current state of the map in text file in our current
     * path name equal to our parameter
     * @param filename name that our file will be saved as
     */
    public void save(String filename) {
        if (filename.isEmpty()) {
            return;
        }
        PrintWriter out = null;
        try {
            out = new PrintWriter(new BufferedWriter
                    (new FileWriter(filename)));

        } catch (Exception e) {
            //e.printStackTrace();
        }
        for (int c = 0; c < 40; c++) {
            for (int r = 0; r < 20; r++) {
                if (maze.get(c).get(r).isWall()) {
                    //walls are a 1
                    out.print("1:");
                } else if (c == startC && r == startR) {
                    //start is 2
                    out.print("2:");
                } else if (c == endC && r == endR) {
                    //end is 3
                    out.print("3:");
                } else {
                    out.print("0:");
                }
            }
            //out.println("");
        }
        out.close();
    }


    /**
     * Loads a map from a text file that is in our current path name
     * equal to our parameter given
     * @param filename name of the file that we are loading
     */
    public void load(String filename) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            return;
        }

        scanner.useDelimiter(":");
        for (int c = 0; c < 40; c++) {
            for (int r = 0; r < 20; r++) {
                if (scanner.hasNext()) {
                    String key = scanner.next();
                    if (key.equals("0")) {
                        maze.get(c).get(r).empty();
                    } else if (key.equals("1")) {
                        maze.get(c).get(r).setWall(true);
                    } else if (key.equals("2")) {
                        maze.get(c).get(r).setStart(true);
                        startC = c;
                        startR = r;
                    } else if (key.equals("3")) {
                        maze.get(c).get(r).setEnd(true);
                        endC = c;
                        endR = r;
                    }
                }
            }
        }
    }
}
