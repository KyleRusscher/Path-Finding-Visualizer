package sample;



import java.util.ArrayList;
import java.util.List;

public class Model {
    enum SelectMode{
        WALL,START,END
    }
    List<List<Node>> maze;

    private SelectMode selectMode;
    private int startC = -1;
    private int startR = -1;
    private int endC = -1;
    private int endR = -1;


    public Model() {
        maze = new ArrayList<>();
        for(int i = 0; i < 40; i++){
            maze.add(new ArrayList<>());
            for(int j = 0;j < 20; j++){
                maze.get(i).add(new Node());
            }
        }
        selectMode = SelectMode.WALL;
    }



    public SelectMode getSelectMode() {
        return selectMode;
    }

    public void setSelectMode(SelectMode selectMode) {
        this.selectMode = selectMode;
    }

    public void selectNode(int c, int r){
        if(selectMode == SelectMode.WALL){
            if(!maze.get(c).get(r).isStart() && !maze.get(c).get(r).isEnd()) {
                maze.get(c).get(r).setWall(!maze.get(c).get(r).isWall());
            }
        }
        if(selectMode == SelectMode.START){
            if(!maze.get(c).get(r).isWall() && !maze.get(c).get(r).isEnd()) {
                if(startC == -1 && startR ==-1) {
                    maze.get(c).get(r).setStart(true);
                    startC = c;
                    startR = r;
                }else if(startC == c && startR == r){
                    maze.get(c).get(r).setStart(false);
                    startC = -1;
                    startR = -1;
                }

            }

        }
        if(selectMode == SelectMode.END){
            if(!maze.get(c).get(r).isWall() && !maze.get(c).get(r).isStart()) {
                if(endC == -1 && endR ==-1) {
                    maze.get(c).get(r).setEnd(true);
                    endC = c;
                    endR = r;
                }else if(endC == c && endR == r){
                    maze.get(c).get(r).setEnd(false);
                    endC = -1;
                    endR = -1;
                }
            }
        }
    }
}
