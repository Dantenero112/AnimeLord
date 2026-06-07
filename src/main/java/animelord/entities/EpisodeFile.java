package animelord.entities;

public class EpisodeFile {

    private int fileId;

    private int episodeId;

    private String masterPlaylist;

    private String thumbnailPath;

    /*
        DEFAULT CONSTRUCTOR
    */
    public EpisodeFile() {
    }

    /*
        PARAMETERIZED CONSTRUCTOR
    */
    public EpisodeFile(
            int fileId,
            int episodeId,
            String masterPlaylist,
            String thumbnailPath) {

        this.fileId = fileId;
        this.episodeId = episodeId;
        this.masterPlaylist = masterPlaylist;
        this.thumbnailPath = thumbnailPath;
    }

    /*
        GETTERS & SETTERS
    */

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public int getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(int episodeId) {
        this.episodeId = episodeId;
    }

    public String getMasterPlaylist() {
        return masterPlaylist;
    }

    public void setMasterPlaylist(String masterPlaylist) {
        this.masterPlaylist = masterPlaylist;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    @Override
    public String toString() {

        return "EpisodeFile{"
                + "fileId=" + fileId
                + ", episodeId=" + episodeId
                + ", masterPlaylist='" + masterPlaylist + '\''
                + ", thumbnailPath='" + thumbnailPath + '\''
                + '}';
    }

}