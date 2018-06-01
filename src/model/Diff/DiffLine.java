package model.Diff;

import java.util.ArrayList;

public class DiffLine {

    private ArrayList<DiffBlock> line;

    public DiffLine(ArrayList<DiffBlock> line) {
        this.line = line;
    }

    public ArrayList<DiffBlock> getLine() {

        return line;
    }

    public void setLine(ArrayList<DiffBlock> line) {
        this.line = line;
    }
}
