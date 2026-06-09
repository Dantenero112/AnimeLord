package animelord.dao;

import animelord.entities.Episode;
import animelord.util.DBConnection;
import animelord.entities.EpisodeCard;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EpisodeDAO {

    /*
        ADD EPISODE
    */
    public boolean addEpisode(Episode episode) {

        String sql =
                "INSERT INTO episodes("
                + "anime_id,"
                + "episode_number,"
                + "episode_title,"
                + "description,"
                + "duration_seconds"
                + ") "
                + "VALUES(?,?,?,?,?)";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    episode.getAnimeId()
            );

            ps.setInt(
                    2,
                    episode.getEpisodeNumber()
            );

            ps.setString(
                    3,
                    episode.getEpisodeTitle()
            );

            ps.setString(
                    4,
                    episode.getDescription()
            );

            ps.setInt(
                    5,
                    episode.getDurationSeconds()
            );

            return ps.executeUpdate() > 0;

        }
        catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }
    /*
    RECENTLY ADDED EPISODE CARDS
    */
    public List<EpisodeCard> getRecentEpisodeCards(
            int limit) {

        List<EpisodeCard> cards =
                new ArrayList<>();

        String sql =
                "SELECT "
                + "e.episode_id, "
                + "e.anime_id, "
                + "a.title, "
                + "ef.thumbnail_path, "
                + "e.episode_number, "
                + "e.episode_title, "
                + "e.upload_date "
                + "FROM episodes e "
                + "INNER JOIN anime a "
                + "ON e.anime_id = a.anime_id "
                + "INNER JOIN episode_files ef "
                + "ON e.episode_id = ef.episode_id "
                + "ORDER BY e.upload_date DESC "
                + "LIMIT ?";

        try(
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ){

            ps.setInt(
                    1,
                    limit
            );

            ResultSet rs =
                    ps.executeQuery();

            while(rs.next()){

                EpisodeCard card =
                        new EpisodeCard();

                card.setEpisodeId(
                        rs.getInt(
                                "episode_id"
                        )
                );

                card.setAnimeId(
                        rs.getInt(
                                "anime_id"
                        )
                );

                card.setAnimeTitle(
                        rs.getString(
                                "title"
                        )
                );

                card.setThumbnailPath(
                        rs.getString(
                                "thumbnail_path"
                        )
                );

                card.setEpisodeNumber(
                        rs.getInt(
                                "episode_number"
                        )
                );

                card.setEpisodeTitle(
                        rs.getString(
                                "episode_title"
                        )
                );

                card.setUploadDate(
                        rs.getTimestamp(
                                "upload_date"
                        )
                );

                cards.add(card);

            }

        }
        catch(Exception e){

            e.printStackTrace();

        }

        return cards;
    }

    /*
        GET EPISODE BY ID
    */
    public Episode getEpisodeById(int episodeId) {

        String sql =
                "SELECT * "
                + "FROM episodes "
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

                return mapEpisode(rs);

            }

        }
        catch (Exception e) {

            e.printStackTrace();

        }

        return null;
    }

    /*
        GET EPISODE
        BY ANIME + EPISODE NUMBER
    */
    public Episode getEpisode(
            int animeId,
            int episodeNumber) {

        String sql =
                "SELECT * "
                + "FROM episodes "
                + "WHERE anime_id=? "
                + "AND episode_number=?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(1, animeId);
            ps.setInt(2, episodeNumber);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                return mapEpisode(rs);

            }

        }
        catch (Exception e) {

            e.printStackTrace();

        }

        return null;
    }

    /*
        GET ALL EPISODES OF AN ANIME
    */
    public List<Episode> getEpisodesByAnime(
            int animeId) {

        List<Episode> episodes =
                new ArrayList<>();

        String sql =
                "SELECT * "
                + "FROM episodes "
                + "WHERE anime_id=? "
                + "ORDER BY episode_number";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(1, animeId);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                episodes.add(
                        mapEpisode(rs)
                );

            }

        }
        catch (Exception e) {

            e.printStackTrace();

        }

        return episodes;
    }

    /*
    RECENTLY ADDED EPISODES
    */
    public List<Episode> getRecentlyAddedEpisodes(
            int limit) {

        List<Episode> episodes =
                new ArrayList<>();

        String sql =
                "SELECT * "
                + "FROM episodes "
                + "ORDER BY upload_date DESC "
                + "LIMIT ?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    limit
            );

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                episodes.add(
                        mapEpisode(rs)
                );

            }

        }
        catch (Exception e) {

            e.printStackTrace();

        }

        return episodes;
    }
    /*
        UPDATE EPISODE
    */
    public boolean updateEpisode(
            Episode episode) {

        String sql =
                "UPDATE episodes "
                + "SET episode_title=?, "
                + "description=?, "
                + "duration_seconds=? "
                + "WHERE episode_id=?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    episode.getEpisodeTitle()
            );

            ps.setString(
                    2,
                    episode.getDescription()
            );

            ps.setInt(
                    3,
                    episode.getDurationSeconds()
            );

            ps.setInt(
                    4,
                    episode.getEpisodeId()
            );

            return ps.executeUpdate() > 0;

        }
        catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }
    /*
        NEXT EPISODE
    */
    public Episode getNextEpisode(
            int animeId,
            int currentEpisodeNumber) {

        String sql =
                "SELECT * "
                + "FROM episodes "
                + "WHERE anime_id=? "
                + "AND episode_number>? "
                + "ORDER BY episode_number ASC "
                + "LIMIT 1";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    animeId
            );

            ps.setInt(
                    2,
                    currentEpisodeNumber
            );

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                return mapEpisode(rs);

            }

        }
        catch (Exception e) {

            e.printStackTrace();

        }

        return null;
    }
    /*
    PREVIOUS EPISODE
    */
    public Episode getPreviousEpisode(
            int animeId,
            int currentEpisodeNumber) {

        String sql =
                "SELECT * "
                + "FROM episodes "
                + "WHERE anime_id=? "
                + "AND episode_number<? "
                + "ORDER BY episode_number DESC "
                + "LIMIT 1";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    animeId
            );

            ps.setInt(
                    2,
                    currentEpisodeNumber
            );

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                return mapEpisode(rs);

            }

        }
        catch (Exception e) {

            e.printStackTrace();

        }

        return null;
    }
    
    /*
    LATEST EPISODE OF AN ANIME
    */
    public Episode getLatestEpisode(
            int animeId) {

        String sql =
                "SELECT * "
                + "FROM episodes "
                + "WHERE anime_id=? "
                + "ORDER BY episode_number DESC "
                + "LIMIT 1";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    animeId
            );

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                return mapEpisode(rs);

            }

        }
        catch (Exception e) {

            e.printStackTrace();

        }

        return null;
    }
    /*
        DELETE EPISODE
    */
    public boolean deleteEpisode(
            int episodeId) {

        String sql =
                "DELETE FROM episodes "
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
        GET TOTAL EPISODES
    */
    public int getEpisodeCount() {

        String sql =
                "SELECT COUNT(*) "
                + "FROM episodes";

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
        GET TOTAL EPISODES OF AN ANIME
    */
    public int getEpisodeCountByAnime(
            int animeId) {

        String sql =
                "SELECT COUNT(*) "
                + "FROM episodes "
                + "WHERE anime_id=?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(1, animeId);

            ResultSet rs =
                    ps.executeQuery();

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
        RESULTSET -> EPISODE
    */
    private Episode mapEpisode(
            ResultSet rs)
            throws SQLException {

        Episode episode =
                new Episode();

        episode.setEpisodeId(
                rs.getInt(
                        "episode_id"
                )
        );

        episode.setAnimeId(
                rs.getInt(
                        "anime_id"
                )
        );

        episode.setEpisodeNumber(
                rs.getInt(
                        "episode_number"
                )
        );

        episode.setEpisodeTitle(
                rs.getString(
                        "episode_title"
                )
        );

        episode.setDescription(
                rs.getString(
                        "description"
                )
        );

        episode.setDurationSeconds(
                rs.getInt(
                        "duration_seconds"
                )
        );

        episode.setUploadDate(
                rs.getTimestamp(
                        "upload_date"
                )
        );

        return episode;
    }

}