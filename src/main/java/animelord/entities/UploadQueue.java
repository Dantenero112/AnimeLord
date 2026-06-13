package animelord.entities;

import java.sql.Timestamp;

public class UploadQueue {

    private int queueId;

    private int episodeId;

    private String status;

    private boolean encode1080p;

    private boolean encode720p;

    private boolean encode480p;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    /*
        DEFAULT CONSTRUCTOR
    */
    public UploadQueue() {
    }

    /*
        PARAMETERIZED CONSTRUCTOR
    */
    public UploadQueue(
            int queueId,
            int episodeId,
            String status,
            boolean encode1080p,
            boolean encode720p,
            boolean encode480p,
            Timestamp createdAt,
            Timestamp updatedAt) {

        this.queueId = queueId;
        this.episodeId = episodeId;
        this.status = status;
        this.encode1080p = encode1080p;
        this.encode720p = encode720p;
        this.encode480p = encode480p;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /*
        GETTERS & SETTERS
    */

    public int getQueueId() {
        return queueId;
    }

    public void setQueueId(
            int queueId) {

        this.queueId = queueId;
    }

    public int getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(
            int episodeId) {

        this.episodeId = episodeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(
            String status) {

        this.status = status;
    }

    public boolean isEncode1080p() {
        return encode1080p;
    }

    public void setEncode1080p(
            boolean encode1080p) {

        this.encode1080p = encode1080p;
    }

    public boolean isEncode720p() {
        return encode720p;
    }

    public void setEncode720p(
            boolean encode720p) {

        this.encode720p = encode720p;
    }

    public boolean isEncode480p() {
        return encode480p;
    }

    public void setEncode480p(
            boolean encode480p) {

        this.encode480p = encode480p;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(
            Timestamp createdAt) {

        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(
            Timestamp updatedAt) {

        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {

        return "UploadQueue{"
                + "queueId=" + queueId
                + ", episodeId=" + episodeId
                + ", status='" + status + '\''
                + ", encode1080p=" + encode1080p
                + ", encode720p=" + encode720p
                + ", encode480p=" + encode480p
                + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt
                + '}';
    }

}