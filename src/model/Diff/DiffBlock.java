package model.Diff;

public class DiffBlock {

    private String content;
    private boolean isMatch;

    public DiffBlock(String content, boolean isMatch) {

        this.content = content;
        this.isMatch = isMatch;
    }

    public void setMatch(boolean match) {
        isMatch = match;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {

        return content;
    }

    public boolean getMatch() {
        return isMatch;
    }
}
