package animelord.dao;

import animelord.entities.Anime;
import animelord.util.DBConnection;
import animelord.dao.GenreDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimeDAO {

    /*
        ADD ANIME
     */
    public boolean addAnime(Anime anime) {

        String sql =
                "INSERT INTO anime("
                + "title,"
                + "synopsis,"
                + "cover_image,"
                + "banner_image,"
                + "release_year,"
                + "status,"
                + "total_views"
                + ") "
                + "VALUES(?,?,?,?,?,?,?)";

        try(
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ){

            ps.setString(
                    1,
                    anime.getTitle()
            );

            ps.setString(
                    2,
                    anime.getSynopsis()
            );

            ps.setString(
                    3,
                    anime.getCoverImage()
            );

            ps.setString(
                    4,
                    anime.getBannerImage()
            );

            ps.setInt(
                    5,
                    anime.getReleaseYear()
            );

            ps.setString(
                    6,
                    anime.getStatus()
            );

            ps.setLong(
                    7,
                    anime.getTotalViews()
            );

            return ps.executeUpdate() > 0;

        }
        catch(Exception e){

            e.printStackTrace();

            return false;
        }
    }
    //Add Anime and get ID
        public int addAnimeAndGetId(
            Anime anime) {

        String sql =
                "INSERT INTO anime("
                + "title,"
                + "synopsis,"
                + "cover_image,"
                + "banner_image,"
                + "release_year,"
                + "status,"
                + "total_views"
                + ") "
                + "VALUES(?,?,?,?,?,?,?)";

        try(
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(
                                sql,
                                Statement.RETURN_GENERATED_KEYS
                        )
        ){

            ps.setString(
                    1,
                    anime.getTitle()
            );

            ps.setString(
                    2,
                    anime.getSynopsis()
            );

            ps.setString(
                    3,
                    anime.getCoverImage()
            );

            ps.setString(
                    4,
                    anime.getBannerImage()
            );

            ps.setInt(
                    5,
                    anime.getReleaseYear()
            );

            ps.setString(
                    6,
                    anime.getStatus()
            );

            ps.setLong(
                    7,
                    anime.getTotalViews()
            );

            int rows =
                    ps.executeUpdate();

            if(rows == 0){

                return 0;

            }

            ResultSet rs =
                    ps.getGeneratedKeys();

            if(rs.next()){

                return rs.getInt(1);

            }

        }
        catch(Exception e){

            e.printStackTrace();

        }

        return 0;
    }
    //Genere Search
        public List<Anime> getAnimeByGenre(
            int genreId) {

        List<Anime> animeList =
                new ArrayList<>();

        String sql =
                "SELECT a.* "
                + "FROM anime a "
                + "INNER JOIN anime_genres ag "
                + "ON a.anime_id = ag.anime_id "
                + "WHERE ag.genre_id=? "
                + "ORDER BY a.title";

        try(
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ){

            ps.setInt(
                    1,
                    genreId
            );

            ResultSet rs =
                    ps.executeQuery();

            while(rs.next()){

                animeList.add(
                        mapAnime(rs)
                );

            }

        }
        catch(Exception e){

            e.printStackTrace();

        }

        return animeList;
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
    
    public Anime getTrendingAnime() {

        String sql =
                "SELECT * "
                + "FROM anime "
                + "ORDER BY total_views DESC "
                + "LIMIT 1";

        try(
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ){

            if(rs.next()){

                return mapAnime(rs);

            }

        }
        catch(Exception e){

            e.printStackTrace();

        }

        return null;
    }
    
    public List<Anime> getTopAnime(int limit) {

        List<Anime> animeList =
                new ArrayList<>();

        String sql =
                "SELECT * "
                + "FROM anime "
                + "ORDER BY total_views DESC "
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

                animeList.add(
                        mapAnime(rs)
                );

            }

        }
        catch(Exception e){

            e.printStackTrace();

        }

        return animeList;
    }
    
    public List<Anime> getTopAnime(int limit,String status) {

        List<Anime> animeList =
                new ArrayList<>();

        String sql =
                "SELECT * "
                + "FROM anime "
                + "WHERE status=? "
                + "ORDER BY total_views DESC "
                + "LIMIT ?";

        try(
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ){

            ps.setString(
                    1,
                    status
            );

            ps.setInt(
                    2,
                    limit
            );

            ResultSet rs =
                    ps.executeQuery();

            while(rs.next()){

                animeList.add(
                        mapAnime(rs)
                );

            }

        }
        catch(Exception e){

            e.printStackTrace();

        }

        return animeList;
    }
    
    /*
    NEWLY RELEASED ANIME
    */
    public List<Anime> getNewlyReleasedAnime(
            int limit) {

        List<Anime> animeList =
                new ArrayList<>();

        String sql =
                "SELECT * "
                + "FROM anime "
                + "WHERE status='ONGOING' "
                + "ORDER BY created_at DESC "
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
    UPCOMING ANIME
    */
    public List<Anime> getUpcomingAnime(
            int limit) {

        List<Anime> animeList =
                new ArrayList<>();

        String sql =
                "SELECT * "
                + "FROM anime "
                + "WHERE status='UPCOMING' "
                + "ORDER BY release_year ASC, created_at DESC "
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
    RECENTLY COMPLETED ANIME
    */
    public List<Anime> getRecentlyCompletedAnime(
            int limit) {

        List<Anime> animeList = new ArrayList<>();

        String sql =
                "SELECT * "
                + "FROM anime "
                + "WHERE status='COMPLETED' "
                + "ORDER BY created_at DESC "
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
    public boolean incrementViews(int animeId) {

        String sql =
                "UPDATE anime "
                + "SET total_views = total_views + 1 "
                + "WHERE anime_id=?";

        try(
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ){

            ps.setInt(
                    1,
                    animeId
            );

            return ps.executeUpdate() > 0;

        }
        catch(Exception e){

            e.printStackTrace();

            return false;
        }
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

                return mapAnimeWithGenres(rs);

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
                + "WHERE title LIKE ? "
                + "OR synopsis LIKE ?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    "%" + keyword + "%"
            );

            ps.setString(
                    2,
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
    public boolean updateAnime(
            Anime anime) {

        String sql =
                "UPDATE anime "
                + "SET title=?, "
                + "synopsis=?, "
                + "cover_image=?, "
                + "banner_image=?, "
                + "release_year=?, "
                + "status=? "
                + "WHERE anime_id=?";

        try(
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ){

            ps.setString(
                    1,
                    anime.getTitle()
            );

            ps.setString(
                    2,
                    anime.getSynopsis()
            );

            ps.setString(
                    3,
                    anime.getCoverImage()
            );

            ps.setString(
                    4,
                    anime.getBannerImage()
            );

            ps.setInt(
                    5,
                    anime.getReleaseYear()
            );

            ps.setString(
                    6,
                    anime.getStatus()
            );

            ps.setInt(
                    7,
                    anime.getAnimeId()
            );

            return ps.executeUpdate() > 0;

        }
        catch(Exception e){

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
    private Anime mapAnime(
            ResultSet rs)
            throws SQLException {

        Anime anime =
                new Anime();

        anime.setAnimeId(
                rs.getInt(
                        "anime_id"
                )
        );

        anime.setTitle(
                rs.getString(
                        "title"
                )
        );

        anime.setSynopsis(
                rs.getString(
                        "synopsis"
                )
        );

        anime.setCoverImage(
                rs.getString(
                        "cover_image"
                )
        );

        anime.setBannerImage(
                rs.getString(
                        "banner_image"
                )
        );

        anime.setReleaseYear(
                rs.getInt(
                        "release_year"
                )
        );

        anime.setStatus(
                rs.getString(
                        "status"
                )
        );

        anime.setTotalViews(
                rs.getLong(
                        "total_views"
                )
        );

        anime.setCreatedAt(
                rs.getTimestamp(
                        "created_at"
                )
        );

        return anime;
    }
    /*
    RESULTSET -> ANIME WITH GENRES
    */
    private Anime mapAnimeWithGenres(
            ResultSet rs)
            throws SQLException {

        Anime anime =
                mapAnime(
                        rs
                );

        try{

            GenreDAO genreDAO =
                    new GenreDAO();

            anime.setGenres(
                    genreDAO.getGenresByAnime(
                            anime.getAnimeId()
                    )
            );

        }
        catch(Exception e){

            e.printStackTrace();

        }

        return anime;
    }
}