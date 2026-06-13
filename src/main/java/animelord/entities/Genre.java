package animelord.entities;

public class Genre {

    private int genreId;

    private String genreName;

    /*
        DEFAULT CONSTRUCTOR
    */
    public Genre() {
    }

    /*
        PARAMETERIZED CONSTRUCTOR
    */
    public Genre(
            int genreId,
            String genreName) {

        this.genreId = genreId;
        this.genreName = genreName;
    }

    /*
        GETTERS & SETTERS
    */

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(
            int genreId) {

        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(
            String genreName) {

        this.genreName = genreName;
    }

    @Override
    public String toString() {

        return "Genre{"
                + "genreId=" + genreId
                + ", genreName='" + genreName + '\''
                + '}';
    }

}