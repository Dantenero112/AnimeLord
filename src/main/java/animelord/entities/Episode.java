package animelord.entities;

import java.sql.Timestamp;

public class Episode {

    private int episodeId;

    private int animeId;

    private int episodeNumber;

    private String episodeTitle;

    private String description;

    private int durationSeconds;
    private String uploadedVideoPath;
    private Timestamp uploadDate;

    /*
        DEFAULT CONSTRUCTOR
    */
    public Episode() {
    }

    /*
        PARAMETERIZED CONSTRUCTOR
    */
    public Episode(
            int episodeId,
            int animeId,
            int episodeNumber,
            String episodeTitle,
            String description,
            int durationSeconds,
            Timestamp uploadDate) {

        this.episodeId = episodeId;
        this.animeId = animeId;
        this.episodeNumber = episodeNumber;
        this.episodeTitle = episodeTitle;
        this.description = description;
        this.durationSeconds = durationSeconds;
        this.uploadDate = uploadDate;
    }

    /*
        GETTERS & SETTERS
    */

    public int getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(int episodeId) {
        this.episodeId = episodeId;
    }

    public int getAnimeId() {
        return animeId;
    }

    public void setAnimeId(int animeId) {
        this.animeId = animeId;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getEpisodeTitle() {
        return episodeTitle;
    }

    public void setEpisodeTitle(String episodeTitle) {
        this.episodeTitle = episodeTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(int durationSeconds) {
        this.durationSeconds = durationSeconds;
    }
    
    public String getUploadedVideoPath() {
    return uploadedVideoPath;
    }
    
    public void setUploadedVideoPath(
        String uploadedVideoPath) {

    this.uploadedVideoPath =
            uploadedVideoPath;
    }
    
    public Timestamp getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Timestamp uploadDate) {
        this.uploadDate = uploadDate;
    }

    @Override
    public String toString() {

        return "Episode{"
                + "episodeId=" + episodeId
                + ", animeId=" + animeId
                + ", episodeNumber=" + episodeNumber
                + ", episodeTitle='" + episodeTitle + '\''
                + ", description='" + description + '\''
                + ", durationSeconds=" + durationSeconds
                + ", uploadedVideoPath='" + uploadedVideoPath + '\''
                + ", uploadDate=" + uploadDate
                + '}';
    }

}