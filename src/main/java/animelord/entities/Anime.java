package animelord.entities;

import java.sql.Timestamp;

public class Anime {

    private int animeId;

    private String title;

    private String description;

    private String posterPath;

    private String status;

    private int releaseYear;

    private Timestamp createdAt;

    /*
        DEFAULT CONSTRUCTOR
    */
    public Anime() {
    }

    /*
        PARAMETERIZED CONSTRUCTOR
    */
    public Anime(
            int animeId,
            String title,
            String description,
            String posterPath,
            String status,
            int releaseYear,
            Timestamp createdAt) {

        this.animeId = animeId;
        this.title = title;
        this.description = description;
        this.posterPath = posterPath;
        this.status = status;
        this.releaseYear = releaseYear;
        this.createdAt = createdAt;
    }

    /*
        GETTERS & SETTERS
    */

    public int getAnimeId() {
        return animeId;
    }

    public void setAnimeId(int animeId) {
        this.animeId = animeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {

        return "Anime{"
                + "animeId=" + animeId
                + ", title=" + title
                + ", description=" + description
                + ", posterPath=" + posterPath
                + ", status=" + status
                + ", releaseYear=" + releaseYear
                + ", createdAt=" + createdAt
                + '}';
    }
}