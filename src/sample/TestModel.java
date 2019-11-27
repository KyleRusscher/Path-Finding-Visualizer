package sample;

import org.junit.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TestModel {

    // test constructor
    @Test
    public void testConstructor1() {
        Model model;
        model = new Model();
        Assert.assertEquals(40, model.maze.size());
    }

    @Test
    public void testConstructor2() {
        Model model;
        model = new Model();
        for (int i = 0; i < 40; i++) {
            Assert.assertEquals(20, model.maze.get(i).size());
        }

    }

    // test getters and setters

    @Test
    public void testGetSelectedNodeNull() {
        Model model;
        model = new Model();
        Assert.assertEquals(null, model.getSelectMode());
    }

    @Test
    public void testSetSelectedNodeNull() {
        Model model;
        model = new Model();
        model.setSelectMode(null);
        Assert.assertEquals(null, model.getSelectMode());
    }

    @Test
    public void testGetAndSetSelectedNodes() {
        Model model;
        model = new Model();
        for (Model.SelectMode mode : Model.SelectMode.values()) {
            model.setSelectMode(mode);
            Assert.assertEquals(mode, model.getSelectMode());
        }
    }

    @Test
    public void testGetAlgorithmNodeNull() {
        Model model;
        model = new Model();
        Assert.assertEquals(null, model.getAlgorithm());
    }

    @Test
    public void testSetAlgorithmNodeNull() {
        Model model;
        model = new Model();
        model.setAlgorithm(null);
        Assert.assertEquals(null, model.getAlgorithm());
    }

    @Test
    public void testGetAndSetAlgorithms() {
        Model model;
        model = new Model();
        for (Model.Algorithm alg : Model.Algorithm.values()) {
            model.setAlgorithm(alg);
            Assert.assertEquals(alg, model.getAlgorithm());
        }
    }

    // has start and end

    @Test
    public void testHasStartAndEnd1() {
        Model model;
        model = new Model();
        Assert.assertFalse(model.hasStartAndEnd());
    }

    @Test
    public void testHasStartAndEnd2() {
        Model model;
        model = new Model();
        model.maze.get(0).get(10).setStart(true);
        model.maze.get(0).get(10).setEnd(true);
        Assert.assertTrue(model.hasStartAndEnd());
    }

    // test clear board
    @Test
    public void testClearBoard() {
        Model model;
        model = new Model();
        model.clearBoard();
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 20; j++) {
                Assert.assertTrue(model.maze.get(i).get(j).isEmpty());
            }
        }
    }

    @Test
    public void testClearBoard2() {
        Model model;
        model = new Model();
        model.maze.get(0).get(0).setVisited(true);
        model.clearBoard();
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 20; j++) {
                Assert.assertTrue(model.maze.get(i).get(j).isEmpty());
            }
        }
    }

    // test clear path
    @Test
    public void testClearPath1() {
        Model model;
        model = new Model();
        model.load("Path-Finding-Visualizer/ForTestClass.txt");
        model.maze.get(0).get(0).setPath(true);
        model.maze.get(0).get(1).setPath(true);
        model.clearPath();
        Assert.assertFalse(model.maze.get(0).get(1).isPath() || model.maze.get(0).get(0).isPath());
    }

    @Test
    public void testClearPath2() {
        Model model;
        model = new Model();
        model.load("Path-Finding-Visualizer/ForTestClass.txt");
        model.maze.get(0).get(0).setPath(true);
        model.maze.get(0).get(1).setPath(true);
        model.clearPath();
        Assert.assertTrue(model.maze.get(17).get(0).isWall() && model.maze.get(17).get(1).isWall());
    }

    @Test
    public void testClearPath3() {
        Model model;
        model = new Model();
        model.load("Path-Finding-Visualizer/ForTestClass.txt");
        model.maze.get(0).get(0).setPath(true);
        model.maze.get(0).get(1).setPath(true);
        model.clearPath();
        Assert.assertTrue(model.maze.get(3).get(6).isStart() && model.maze.get(22).get(8).isEnd());
    }

    @Test
    public void testClearPath4() {
        Model model;
        model = new Model();
        model.maze.get(0).get(0).setPath(true);
        model.maze.get(0).get(1).setPath(true);
        model.clearPath();
        Assert.assertFalse(model.maze.get(0).get(0).isWall() || model.maze.get(0).get(1).isWall());
    }

    // selectNode

    @Test(expected = IndexOutOfBoundsException.class)
    public void selectNodeFailRow() {
        Model model;
        model = new Model();
        model.setSelectMode(Model.SelectMode.WALL);
        model.selectNode(5, 20);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void selectNodeFailCol() {
        Model model;
        model = new Model();
        model.setSelectMode(Model.SelectMode.WALL);
        model.selectNode(40, 19);
    }

    @Test
    public void selectNodeEmpty() {
        Model model;
        model = new Model();
        model.setSelectMode(Model.SelectMode.REMOVE_WALL);
        model.selectNode(15, 19);
        Assert.assertTrue(model.maze.get(15).get(19).isEmpty());
    }

    @Test
    public void selectNodeWall() {
        Model model;
        model = new Model();
        model.setSelectMode(Model.SelectMode.WALL);
        model.selectNode(15, 19);
        Assert.assertTrue(model.maze.get(15).get(19).isWall());
    }

    @Test
    public void selectNodeStart() {
        Model model;
        model = new Model();
        model.setSelectMode(Model.SelectMode.START);
        model.selectNode(15, 19);
        Assert.assertTrue(model.maze.get(15).get(19).isStart());
    }

    @Test
    public void selectNodeResetStart() {
        Model model;
        model = new Model();
        model.setSelectMode(Model.SelectMode.START);
        model.selectNode(15, 19);
        model.selectNode(15, 19);
        Assert.assertFalse(model.maze.get(15).get(19).isStart());
    }

    @Test
    public void selectNodeEnd() {
        Model model;
        model = new Model();
        model.setSelectMode(Model.SelectMode.END);
        model.selectNode(15, 19);
        Assert.assertTrue(model.maze.get(15).get(19).isEnd());
    }

    @Test
    public void selectNodeResetEnd() {
        Model model;
        model = new Model();
        model.setSelectMode(Model.SelectMode.END);
        model.selectNode(15, 19);
        model.selectNode(15, 19);
        Assert.assertFalse(model.maze.get(15).get(19).isEnd());
    }

    @Test
    public void ShortestPathNoStartEnd() {
        Model model;
        model = new Model();
        model.shortestPath();
        Assert.assertFalse(model.hasStartAndEnd());
    }

    @Test
    public void DijkstraNoPath() {
        Model model;
        model = new Model();
        model.load("Path-Finding-Visualizer/NoPath.txt");
        model.setAlgorithm(Model.Algorithm.DIJKSTRA);
        Assert.assertTrue(model.shortestPath() == false);
    }

    @Test
    public void DijkstraShortestPath() {
        Model model;
        model = new Model();
        model.load("Path-Finding-Visualizer/BasicPath.txt");
        model.setAlgorithm(Model.Algorithm.DIJKSTRA);
        model.shortestPath();
        Assert.assertTrue(
                model.shortestPath.get(6).getRow() == 0 &&
                        model.shortestPath.get(6).getColumn() == 0 &&
                        model.shortestPath.get(5).getRow() == 1 &&
                        model.shortestPath.get(5).getColumn() == 0 &&
                        model.shortestPath.get(4).getRow() == 1 &&
                        model.shortestPath.get(4).getColumn() == 1 &&
                        model.shortestPath.get(3).getRow() == 1 &&
                        model.shortestPath.get(3).getColumn() == 2 &&
                        model.shortestPath.get(2).getRow() == 1 &&
                        model.shortestPath.get(2).getColumn() == 3 &&
                        model.shortestPath.get(1).getRow() == 1 &&
                        model.shortestPath.get(1).getColumn() == 4 &&
                        model.shortestPath.get(0).getRow() == 0 &&
                        model.shortestPath.get(0).getColumn() == 4
        );
    }

    @Test
    public void DijkstraAndAStarShortestPath1() {
        Model model;
        model = new Model();
        model.load("Path-Finding-Visualizer/ForTestClass.txt");
        model.setAlgorithm(Model.Algorithm.ASTAR);
        model.shortestPath();
        List<Node> tempDijk = model.shortestPath;
        model.visitOrder = new ArrayList<>();
        model.shortestPath = new ArrayList<>();
        model.clearPath();
        model.setAlgorithm(Model.Algorithm.DIJKSTRA);
        model.shortestPath();
        Assert.assertEquals(tempDijk, model.shortestPath);
    }

    @Test
    public void DijkstraAndAStarShortestPath2() {
        Model model;
        model = new Model();
        model.load("Path-Finding-Visualizer/BasicPath.txt");
        model.setAlgorithm(Model.Algorithm.ASTAR);
        model.shortestPath();
        List<Node> tempDijk = model.shortestPath;
        model.visitOrder = new ArrayList<>();
        model.shortestPath = new ArrayList<>();
        model.clearPath();
        model.setAlgorithm(Model.Algorithm.DIJKSTRA);
        model.shortestPath();
        Assert.assertEquals(tempDijk, model.shortestPath);
    }

    @Test
    public void AStarNoPath() {
        Model model;
        model = new Model();
        model.load("Path-Finding-Visualizer/NoPath.txt");
        model.setAlgorithm(Model.Algorithm.ASTAR);
        Assert.assertTrue(model.shortestPath() == false);
    }

    @Test
    public void BFSNoPath() {
        Model model;
        model = new Model();
        model.load("Path-Finding-Visualizer/NoPath.txt");
        model.setAlgorithm(Model.Algorithm.BFS);
        Assert.assertTrue(model.shortestPath() == false);
    }

    @Test
    public void BFSShortestPath() {
        Model model;
        model = new Model();
        model.load("Path-Finding-Visualizer/ForTestClass.txt");
        model.setAlgorithm(Model.Algorithm.DIJKSTRA);
        model.shortestPath();
        List<Node> tempDijk = model.shortestPath;
        model.visitOrder = new ArrayList<>();
        model.shortestPath = new ArrayList<>();
        model.clearPath();
        model.setAlgorithm(Model.Algorithm.BFS);
        model.shortestPath();
        Assert.assertEquals(tempDijk.size(), model.shortestPath.size());
    }

    @Test(expected = NullPointerException.class)
    public void TestBadSave() {
        Model model;
        model = new Model();
        model.save("4<&*");
    }

    @Test
    public void TestGoodSave() {
        Model model;
        model = new Model();
        model.load("Path-Finding-Visualizer/BasicPath.txt");
        model.maze.get(39).get(0).setWall(true);
        model.save("Path-Finding-Visualizer/SaveTest.txt");
        model.clearBoard();
        model.load("Path-Finding-Visualizer/SaveTest.txt");
        Assert.assertTrue(model.maze.get(0).get(0).isStart() &&
                model.maze.get(1).get(0).isWall() &&
                model.maze.get(2).get(0).isWall() &&
                model.maze.get(3).get(0).isWall() &&
                model.maze.get(4).get(0).isEnd() &&
                model.maze.get(39).get(0).isWall()
        );


    }

    @Test
    public void TestGoodLoad() {
        Model model;
        model = new Model();
        model.load("Path-Finding-Visualizer/BasicPath.txt");
        Assert.assertTrue(model.maze.get(0).get(0).isStart() &&
                model.maze.get(1).get(0).isWall() &&
                model.maze.get(2).get(0).isWall() &&
                model.maze.get(3).get(0).isWall() &&
                model.maze.get(4).get(0).isEnd()
        );

    }

    @Test
    public void TestBadLoad() {
        Model model;
        model = new Model();
        model.load("NoSuchFile.txt");
    }
}