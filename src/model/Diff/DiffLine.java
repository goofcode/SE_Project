package model.Diff;

import java.util.ArrayList;
import java.util.List;

public class DiffLine {

    private List<DiffBlock> line;
    private Boolean isMatched;
    private Boolean isSelected;

    public DiffLine(List<DiffBlock> line, boolean isMatched) {
        this.line = new ArrayList<>(line);
        this.isMatched = isMatched;
        this.isSelected = false;
    }

    public void copyTo(DiffLine dst){

        StringBuilder sb = new StringBuilder();
        List<DiffBlock> newLine = new ArrayList<>();

        // merge to one block
        for (DiffBlock block : line)
            sb.append(block.getContent());

        newLine.add(new DiffBlock(sb.toString(), true));
        line = newLine;

        dst.setLine(newLine);

        isMatched = true;
        dst.setMatched(true);
    }

    public List<DiffBlock> getLine() {
        return line;
    }
    public Boolean getMatched() {
        return this.isMatched;
    }
    public Boolean getSelected() {
        return isSelected;
    }

    public void setLine(List<DiffBlock> line) {
        this.line = new ArrayList<>(line);
    }
    public void setMatched(boolean matched) {this.isMatched = matched;}
    public void setSelected(Boolean selected) {
        isSelected = selected;
    }
}

