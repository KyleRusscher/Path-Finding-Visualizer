package sample;


import java.util.*;
import java.util.stream.Collectors;

public class Model {


    enum SelectMode {
        WALL, START, END, PATH
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


    public SelectMode getSelectMode() {
        return selectMode;
    }

    public void setSelectMode(SelectMode selectMode) {
        this.selectMode = selectMode;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    //clears board using empty which sets everything in node to default
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

    public void selectNode(int c, int r) {
        if (selectMode == SelectMode.WALL) {
            if (!maze.get(c).get(r).isStart() && !maze.get(c).get(r).isEnd()) {
                maze.get(c).get(r).setWall(!maze.get(c).get(r).isWall());
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


    private void dijkstraOrAStar() {
        if (startC == -1 || startR == -1 || endC == -1 || endR == -1) {
            return;
        } else {

            maze.get(startC).get(startR).setDistance(0);
            List<Node> nodesStillToCheck = getAllNodes();
            while (nodesStillToCheck.size() != 0) {
                nodesStillToCheck = sortNodes(nodesStillToCheck);
                Node currNode = nodesStillToCheck.get(0);
                nodesStillToCheck.remove(0);
                if (currNode.isWall() == false) {
                    // 100000 is our 'infinity' value
                    if (currNode.getDistance() == 100000) {
                        return;
                    }
                    visitOrder.add(currNode);
                    currNode.setVisited(true);
                    // we have found the end node
                    if (currNode.isEnd()) {
                        return;
                    }
                    if(this.algorithm == Algorithm.DIJKSTRA) {
                        updateNeighbours(currNode);
                    }
                    else{
                        updateNeighboursForAStar(currNode);
                    }
                }
            }
            return;
        }
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
        ;
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

    // calls path finding algorithm and back tracks from the end node to find the shortest path
    // updates all the nodes on the shortest path to having their path value equal to true
    public void shortestPath() {
        if (startC == -1 || startR == -1 || endC == -1 || endR == -1) {
            return;
        } else {
            if(this.algorithm == Algorithm.DIJKSTRA || this.algorithm == Algorithm.ASTAR) {
                dijkstraOrAStar();
            }
            else if(this.algorithm == Algorithm.BFS){
                breadthFirstSearch();
            }


            Node currNode = maze.get(endC).get(endR);
            while (currNode != null) {
                currNode.setPath(true);
                shortestPath.add(currNode);
                currNode = currNode.getPrevNode();
            }
            return;
        }
    }

    private void breadthFirstSearch() {

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
                return;
            }
            updateNeighbours(currentNode);
            List<Node> allNeighbors = getNeighbours(currentNode);

            if (allNeighbors == null)
                continue;

            for (Node neighbor : allNeighbors) {
                if (!neighbor.isVisited()) {
                    queue.add(neighbor);
                }
            }
        }
    }

    // common tactic to calculate heuristic for A* algorithm
    private int  manhattanDistance(Node currentNode) {
        int xChange = Math.abs(currentNode.getRow() - maze.get(endC).get(endR).getRow());
        int yChange = Math.abs(currentNode.getColumn() - maze.get(endC).get(endR).getColumn());

        return (xChange + yChange);
    }
}
