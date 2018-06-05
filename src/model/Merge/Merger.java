package model.Merge;

import javafx.scene.control.ListView;
import model.Diff.DiffLine;

import java.util.ArrayList;
import java.util.HashMap;

public class Merger {
    private  HashMap<String, ArrayList<DiffLine>> source;
    private int[] up_down = new int [2];
    private static int UP = 0;
    private static  int DOWN = 1;
    public Merger(HashMap<String, ArrayList<DiffLine>> source){this.source=source;}


    public int[] search_diff_lines(int clickedLine,int side){
        String sides;
        if(side == 0)
            sides = "left";
        else
            sides = "right";

        ArrayList<DiffLine> checklist = source.get(sides);

        up_down[UP]=up_down[DOWN]=clickedLine;
        if(checklist.get(clickedLine).getIsMatch())
            return null;
        else{
            if(clickedLine>0){
                for(int i = clickedLine; i>=0;i--){
                    if(checklist.get(i).getIsMatch())
                        break;
                    up_down[UP]=i;
                }
            }
            if(clickedLine<checklist.size()){
                for(int i = clickedLine; i < checklist.size();i++){
                    if(checklist.get(i).getIsMatch())
                        break;
                    up_down[DOWN]=i;

                }
            }

        }
        return up_down;
    }

    public HashMap<String,ArrayList<DiffLine>> mergefunction(int side,int left_up_idx, int left_down_idx,int right_up_idx,int right_down_idx){
        ArrayList<DiffLine> from;
        ArrayList<DiffLine> to;
        int[] from_up_down= new int[2];
        int[] to_up_down= new int[2];
        if(side == 0){
            from = source.get("left");
            to = source.get("right");
            from_up_down[UP] = left_up_idx;
            from_up_down[DOWN] = left_down_idx;
            to_up_down[UP] = right_up_idx;
            to_up_down[DOWN] = right_down_idx;
        }
        else {
            from = source.get("right");
            to = source.get("left");
            from_up_down[UP] = right_up_idx;
            from_up_down[DOWN] = right_down_idx;
            to_up_down[UP] = left_up_idx;
            to_up_down[DOWN] = left_down_idx;
        }
        for(int i=right_up_idx;i<=right_down_idx;i++){
            to.remove(to_up_down[UP]);
        }

        for(int i=from_up_down[DOWN];i>=from_up_down[UP];i--){
            to.add(to_up_down[UP],from.get(i));
        }
        return source;
    }

}
