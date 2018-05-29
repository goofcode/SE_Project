package model.Diff;

public class Seq {

    private int begin, end;

    Seq(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    public int getBegin() {
        return this.begin;
    }

    public int getEnd() {
        return this.end;
    }
}
