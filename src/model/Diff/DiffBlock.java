package model.Diff;

public class DiffBlock {

    private String content;
    private boolean isMatch;

    public DiffBlock(String content, boolean isMatch) {

        this.content = content;
        this.isMatch = isMatch;
    }

    public String getContent() {
        return content;
    }

    public boolean getMatch() {
        return isMatch;
    }
}
