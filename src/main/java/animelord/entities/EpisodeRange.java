package animelord.entities;

public class EpisodeRange {

    private int start;

    private int end;

    /*
        DEFAULT CONSTRUCTOR
    */
    public EpisodeRange() {
    }

    /*
        PARAMETERIZED CONSTRUCTOR
    */
    public EpisodeRange(
            int start,
            int end) {

        this.start = start;
        this.end = end;
    }

    /*
        GETTERS & SETTERS
    */

    public int getStart() {
        return start;
    }

    public void setStart(
            int start) {

        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(
            int end) {

        this.end = end;
    }

    /*
        RANGE LABEL

        Examples:

        1-100
        101-200
        201-253
    */
    public String getRangeLabel() {

        return start
                + "-"
                + end;
    }

    @Override
    public String toString() {

        return "EpisodeRange{"
                + "start=" + start
                + ", end=" + end
                + ", rangeLabel='" + getRangeLabel() + '\''
                + '}';
    }

}