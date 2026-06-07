package animelord.dao;

import animelord.entities.EpisodeFile;
import animelord.util.DBConnection;

import java.sql.*;

public class EpisodeFileDAO {

    /*
        ADD EPISODE FILE
    */
    public boolean addEpisodeFile(
            EpisodeFile episodeFile) {

        String sql =
                "INSERT INTO episode_files("
                + "episode_id,"
                + "master_playlist,"
                + "thumbnail_path"
                + ") "
                + "VALUES(?,?,?)";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    episodeFile.getEpisodeId()
            );

            ps.setString(
                    2,
                    episodeFile.getMasterPlaylist()
            );

            ps.setString(
                    3,
                    episodeFile.getThumbnailPath()
            );

            return ps.executeUpdate() > 0;

        }
        catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    /*
        GET BY FILE ID
    */
    public EpisodeFile getEpisodeFileById(
            int fileId) {

        String sql =
                "SELECT * "
                + "FROM episode_files "
                + "WHERE file_id=?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(1, fileId);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                return mapEpisodeFile(rs);

            }

        }
        catch (Exception e) {

            e.printStackTrace();

        }

        return null;
    }

    /*
        GET BY EPISODE ID
    */
    public EpisodeFile getEpisodeFileByEpisodeId(
            int episodeId) {

        String sql =
                "SELECT * "
                + "FROM episode_files "
                + "WHERE episode_id=?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(1, episodeId);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                return mapEpisodeFile(rs);

            }

        }
        catch (Exception e) {

            e.printStackTrace();

        }

        return null;
    }

    /*
        UPDATE MASTER PLAYLIST
    */
    public boolean updateMasterPlaylist(
            int episodeId,
            String masterPlaylist) {

        String sql =
                "UPDATE episode_files "
                + "SET master_playlist=? "
                + "WHERE episode_id=?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    masterPlaylist
            );

            ps.setInt(
                    2,
                    episodeId
            );

            return ps.executeUpdate() > 0;

        }
        catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    /*
        UPDATE THUMBNAIL
    */
    public boolean updateThumbnailPath(
            int episodeId,
            String thumbnailPath) {

        String sql =
                "UPDATE episode_files "
                + "SET thumbnail_path=? "
                + "WHERE episode_id=?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    thumbnailPath
            );

            ps.setInt(
                    2,
                    episodeId
            );

            return ps.executeUpdate() > 0;

        }
        catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    /*
        UPDATE BOTH FILE PATHS
    */
    public boolean updateEpisodeFile(
            EpisodeFile episodeFile) {

        String sql =
                "UPDATE episode_files "
                + "SET master_playlist=?, "
                + "thumbnail_path=? "
                + "WHERE episode_id=?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    episodeFile.getMasterPlaylist()
            );

            ps.setString(
                    2,
                    episodeFile.getThumbnailPath()
            );

            ps.setInt(
                    3,
                    episodeFile.getEpisodeId()
            );

            return ps.executeUpdate() > 0;

        }
        catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    /*
        DELETE BY EPISODE ID
    */
    public boolean deleteEpisodeFile(
            int episodeId) {

        String sql =
                "DELETE FROM episode_files "
                + "WHERE episode_id=?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(1, episodeId);

            return ps.executeUpdate() > 0;

        }
        catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    /*
        TOTAL FILE RECORDS
    */
    public int getEpisodeFileCount() {

        String sql =
                "SELECT COUNT(*) "
                + "FROM episode_files";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            if (rs.next()) {

                return rs.getInt(1);

            }

        }
        catch (Exception e) {

            e.printStackTrace();

        }

        return 0;
    }

    /*
        RESULTSET -> EPISODEFILE
    */
    private EpisodeFile mapEpisodeFile(
            ResultSet rs)
            throws SQLException {

        EpisodeFile episodeFile =
                new EpisodeFile();

        episodeFile.setFileId(
                rs.getInt(
                        "file_id"
                )
        );

        episodeFile.setEpisodeId(
                rs.getInt(
                        "episode_id"
                )
        );

        episodeFile.setMasterPlaylist(
                rs.getString(
                        "master_playlist"
                )
        );

        episodeFile.setThumbnailPath(
                rs.getString(
                        "thumbnail_path"
                )
        );

        return episodeFile;
    }

}