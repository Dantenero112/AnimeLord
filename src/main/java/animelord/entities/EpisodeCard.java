package animelord.entities;

import java.sql.Timestamp;

public class EpisodeCard {

    private int episodeId;

    private int animeId;

    private String animeTitle;

    private String thumbnailPath;

    private int episodeNumber;

    private String episodeTitle;

    private Timestamp uploadDate;

    public EpisodeCard() {
    }

    public EpisodeCard(
            int episodeId,
            int animeId,
            String animeTitle,
            String thumbnailPath,
            int episodeNumber,
            String episodeTitle,
            Timestamp uploadDate) {

        this.episodeId = episodeId;
        this.animeId = animeId;
        this.animeTitle = animeTitle;
        this.thumbnailPath = thumbnailPath;
        this.episodeNumber = episodeNumber;
        this.episodeTitle = episodeTitle;
        this.uploadDate = uploadDate;
    }

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

    public String getAnimeTitle() {
        return animeTitle;
    }

    public void setAnimeTitle(String animeTitle) {
        this.animeTitle = animeTitle;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
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

    public Timestamp getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Timestamp uploadDate) {
        this.uploadDate = uploadDate;
    }
}