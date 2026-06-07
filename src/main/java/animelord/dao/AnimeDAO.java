package animelord.dao;

import animelord.entities.Anime;
import animelord.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimeDAO {

    /*
        ADD ANIME
     */
    public boolean addAnime(Anime anime) {

        String sql =
                "INSERT INTO anime "
                + "(title, description, poster_path, status, release_year) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(1, anime.getTitle());
            ps.setString(2, anime.getDescription());
            ps.setString(3, anime.getPosterPath());
            ps.setString(4, anime.getStatus());
            ps.setInt(5, anime.getReleaseYear());

            return ps.executeUpdate() > 0;

        }
        catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    /*
        GET ALL ANIME
     */
    public List<Anime> getAllAnime() {

        List<Anime> animeList =
                new ArrayList<>();

        String sql =
                "SELECT * FROM anime "
                + "ORDER BY anime_id DESC";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql);
                ResultSet rs =
                        ps.executeQuery()
        ) {

            while (rs.next()) {

                animeList.add(
                        mapAnime(rs)
                );

            }

        }
        catch (Exception e) {

            e.printStackTrace();

        }

        return animeList;
    }

    /*
        GET RECENT ANIME
     */
    public List<Anime> getRecentAnime(int limit) {

        List<Anime> animeList =
                new ArrayList<>();

        String sql =
                "SELECT * FROM anime "
                + "ORDER BY created_at DESC "
                + "LIMIT ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(1, limit);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                animeList.add(
                        mapAnime(rs)
                );

            }

        }
        catch (Exception e) {

            e.printStackTrace();

        }

        return animeList;
    }

    /*
        GET ANIME BY ID
     */
    public Anime getAnimeById(int animeId) {

        String sql =
                "SELECT * FROM anime "
                + "WHERE anime_id = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(1, animeId);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                return mapAnime(rs);

            }

        }
        catch (Exception e) {

            e.printStackTrace();

        }

        return null;
    }

    /*
        SEARCH ANIME
     */
    public List<Anime> searchAnime(String keyword) {

        List<Anime> animeList =
                new ArrayList<>();

        String sql =
                "SELECT * FROM anime "
                + "WHERE title LIKE ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    "%" + keyword + "%"
            );

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                animeList.add(
                        mapAnime(rs)
                );

            }

        }
        catch (Exception e) {

            e.printStackTrace();

        }

        return animeList;
    }

    /*
        UPDATE ANIME
     */
    public boolean updateAnime(Anime anime) {

        String sql =
                "UPDATE anime "
                + "SET title=?, "
                + "description=?, "
                + "poster_path=?, "
                + "status=?, "
                + "release_year=? "
                + "WHERE anime_id=?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(1, anime.getTitle());
            ps.setString(2, anime.getDescription());
            ps.setString(3, anime.getPosterPath());
            ps.setString(4, anime.getStatus());
            ps.setInt(5, anime.getReleaseYear());
            ps.setInt(6, anime.getAnimeId());

            return ps.executeUpdate() > 0;

        }
        catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    /*
        DELETE ANIME
     */
    public boolean deleteAnime(int animeId) {

        String sql =
                "DELETE FROM anime "
                + "WHERE anime_id=?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(1, animeId);

            return ps.executeUpdate() > 0;

        }
        catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    /*
        TOTAL ANIME COUNT
     */
    public int getAnimeCount() {

        String sql =
                "SELECT COUNT(*) "
                + "FROM anime";

        try (
                Connection con = DBConnection.getConnection();
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
        RESULTSET -> ANIME
     */
    private Anime mapAnime(ResultSet rs)
            throws SQLException {

        Anime anime =
                new Anime();

        anime.setAnimeId(
                rs.getInt("anime_id")
        );

        anime.setTitle(
                rs.getString("title")
        );

        anime.setDescription(
                rs.getString("description")
        );

        anime.setPosterPath(
                rs.getString("poster_path")
        );

        anime.setStatus(
                rs.getString("status")
        );

        anime.setReleaseYear(
                rs.getInt("release_year")
        );

        return anime;
    }

}