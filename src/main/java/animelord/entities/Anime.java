package animelord.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Anime {

    private int animeId;

    private String title;
    
    private String synopsis;

    private String coverImage;

    private String bannerImage;

    private int releaseYear;

    private String status;

    private long totalViews;

    private Timestamp createdAt;

    /*
        GENRES
    */
    private List<Genre> genres =
            new ArrayList<>();

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
            String synopsis,
            String coverImage,
            String bannerImage,
            int releaseYear,
            String status,
            long totalViews,
            Timestamp createdAt,
            List<Genre> genres) {

        this.animeId = animeId;
        this.title = title;
        this.synopsis = synopsis;
        this.coverImage = coverImage;
        this.bannerImage = bannerImage;
        this.releaseYear = releaseYear;
        this.status = status;
        this.totalViews = totalViews;
        this.createdAt = createdAt;
        this.genres = genres;
    }

    /*
        GETTERS & SETTERS
    */

    public int getAnimeId() {
        return animeId;
    }

    public void setAnimeId(
            int animeId) {

        this.animeId = animeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(
            String title) {

        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(
            String synopsis) {

        this.synopsis = synopsis;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(
            String coverImage) {

        this.coverImage = coverImage;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(
            String bannerImage) {

        this.bannerImage = bannerImage;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(
            int releaseYear) {

        this.releaseYear = releaseYear;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(
            String status) {

        this.status = status;
    }

    public long getTotalViews() {
        return totalViews;
    }

    public void setTotalViews(
            long totalViews) {

        this.totalViews = totalViews;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(
            Timestamp createdAt) {

        this.createdAt = createdAt;
    }

    /*
        GENRES
    */

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(
            List<Genre> genres) {

        this.genres = genres;
    }

    /*
        ADD SINGLE GENRE
    */
    public void addGenre(
            Genre genre) {

        if(this.genres == null){

            this.genres =
                    new ArrayList<>();

        }

        this.genres.add(
                genre
        );
    }

    @Override
    public String toString() {

        return "Anime{"
                + "animeId=" + animeId
                + ", title='" + title + '\''
                + ", synopsis='" + synopsis + '\''
                + ", coverImage='" + coverImage + '\''
                + ", bannerImage='" + bannerImage + '\''
                + ", releaseYear=" + releaseYear
                + ", status='" + status + '\''
                + ", totalViews=" + totalViews
                + ", createdAt=" + createdAt
                + ", genres=" + genres
                + '}';
    }

}
