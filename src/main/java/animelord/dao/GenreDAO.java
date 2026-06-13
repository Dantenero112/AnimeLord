package animelord.dao;

import animelord.entities.Genre;
import animelord.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenreDAO {

    /*
        ADD GENRE
    */
    public boolean addGenre(
            Genre genre) {

        String sql =
                "INSERT INTO genres("
                + "genre_name"
                + ") "
                + "VALUES(?)";

        try(
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ){

            ps.setString(
                    1,
                    genre.getGenreName()
            );

            return ps.executeUpdate() > 0;

        }
        catch(Exception e){

            e.printStackTrace();

            return false;
        }
    }

    /*
        GET GENRE BY ID
    */
    public Genre getGenreById(
            int genreId) {

        String sql =
                "SELECT * "
                + "FROM genres "
                + "WHERE genre_id=?";

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

            if(rs.next()){

                return mapGenre(
                        rs
                );

            }

        }
        catch(Exception e){

            e.printStackTrace();

        }

        return null;
    }

    /*
        GET GENRE BY NAME
    */
    public Genre getGenreByName(
            String genreName) {

        String sql =
                "SELECT * "
                + "FROM genres "
                + "WHERE genre_name=?";

        try(
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ){

            ps.setString(
                    1,
                    genreName
            );

            ResultSet rs =
                    ps.executeQuery();

            if(rs.next()){

                return mapGenre(
                        rs
                );

            }

        }
        catch(Exception e){

            e.printStackTrace();

        }

        return null;
    }

    /*
        GET ALL GENRES
    */
    public List<Genre> getAllGenres() {

        List<Genre> genres =
                new ArrayList<>();

        String sql =
                "SELECT * "
                + "FROM genres "
                + "ORDER BY genre_name ASC";

        try(
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ){

            while(rs.next()){

                genres.add(
                        mapGenre(rs)
                );

            }

        }
        catch(Exception e){

            e.printStackTrace();

        }

        return genres;
    }

    /*
        UPDATE GENRE
    */
    public boolean updateGenre(
            Genre genre) {

        String sql =
                "UPDATE genres "
                + "SET genre_name=? "
                + "WHERE genre_id=?";

        try(
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ){

            ps.setString(
                    1,
                    genre.getGenreName()
            );

            ps.setInt(
                    2,
                    genre.getGenreId()
            );

            return ps.executeUpdate() > 0;

        }
        catch(Exception e){

            e.printStackTrace();

            return false;
        }
    }

    /*
        DELETE GENRE
    */
    public boolean deleteGenre(
            int genreId) {

        String sql =
                "DELETE FROM genres "
                + "WHERE genre_id=?";

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

            return ps.executeUpdate() > 0;

        }
        catch(Exception e){

            e.printStackTrace();

            return false;
        }
    }

    /*
        GET GENRES OF AN ANIME
    */
    public List<Genre> getGenresByAnime(
            int animeId) {

        List<Genre> genres =
                new ArrayList<>();

        String sql =
                "SELECT g.* "
                + "FROM genres g "
                + "INNER JOIN anime_genres ag "
                + "ON g.genre_id = ag.genre_id "
                + "WHERE ag.anime_id=? "
                + "ORDER BY g.genre_name";

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

            ResultSet rs =
                    ps.executeQuery();

            while(rs.next()){

                genres.add(
                        mapGenre(rs)
                );

            }

        }
        catch(Exception e){

            e.printStackTrace();

        }

        return genres;
    }

    /*
        ADD ANIME GENRES
    */
    public boolean addAnimeGenres(
            int animeId,
            int[] genreIds) {

        String sql =
                "INSERT INTO anime_genres("
                + "anime_id,"
                + "genre_id"
                + ") "
                + "VALUES(?,?)";

        try(
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ){

            for(int genreId
                    : genreIds){

                ps.setInt(
                        1,
                        animeId
                );

                ps.setInt(
                        2,
                        genreId
                );

                ps.addBatch();

            }

            ps.executeBatch();

            return true;

        }
        catch(Exception e){

            e.printStackTrace();

            return false;
        }
    }

    /*
        DELETE ALL GENRES
        OF AN ANIME
    */
    public boolean deleteAnimeGenres(
            int animeId) {

        String sql =
                "DELETE FROM anime_genres "
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

            return ps.executeUpdate() >= 0;

        }
        catch(Exception e){

            e.printStackTrace();

            return false;
        }
    }

    /*
        RESULTSET -> GENRE
    */
    private Genre mapGenre(
            ResultSet rs)
            throws SQLException {

        Genre genre =
                new Genre();

        genre.setGenreId(
                rs.getInt(
                        "genre_id"
                )
        );

        genre.setGenreName(
                rs.getString(
                        "genre_name"
                )
        );

        return genre;
    }

}