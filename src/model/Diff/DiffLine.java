package model.Diff;

import java.util.ArrayList;

public class DiffLine {

    private ArrayList<DiffBlock> line;
    private Boolean isMatch;
    //불리언 하나 추가해서 하나라도 다르면 다른 라인임을 표시한다 포커스 되었을시 위아래로 서치, 전체 색바꿈

    public DiffLine(ArrayList<DiffBlock> line) {
        this.line = line;
        isMatch = true;
        for(DiffBlock b : this.line){
            if(!b.getMatch())
                isMatch = false;
        }
    }
    public DiffLine(ArrayList<DiffBlock> line,boolean isMatch) {
        this.line = line;
        this.isMatch=isMatch;
    }

    public ArrayList<DiffBlock> getLine() {
        return line;
    }

    public void setLine(ArrayList<DiffBlock> line) {
        this.line = line;
    }

    public Boolean getIsMatch(){return this.isMatch;}

}

