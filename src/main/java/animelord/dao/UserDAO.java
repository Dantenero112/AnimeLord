package animelord.dao;

import animelord.entities.User;
import animelord.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    /*
        REGISTER USER
    */
    public boolean addUser(User user) {

        String sql =
                "INSERT INTO users("
                + "username,"
                + "email,"
                + "password_hash,"
                + "role,"
                + "email_verified,"
                + "verification_token"
                + ") "
                + "VALUES(?,?,?,?,?,?)";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    user.getUsername()
            );

            ps.setString(
                    2,
                    user.getEmail()
            );

            ps.setString(
                    3,
                    user.getPasswordHash()
            );

            ps.setString(
                    4,
                    user.getRole()
            );

            ps.setBoolean(
                    5,
                    user.isEmailVerified()
            );

            ps.setString(
                    6,
                    user.getVerificationToken()
            );

            return ps.executeUpdate() > 0;

        }
        catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }
    /*
        LOGIN USING USERNAME
    */
    public User getUserByUsername(
            String username) {

        String sql =
                "SELECT * "
                + "FROM users "
                + "WHERE username=?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(1, username);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                return mapUser(rs);

            }

        }
        catch (Exception e) {

            e.printStackTrace();

        }

        return null;
    }

    /*
        FIND USER BY EMAIL
    */
    public User getUserByEmail(
            String email) {

        String sql =
                "SELECT * "
                + "FROM users "
                + "WHERE email=?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(1, email);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                return mapUser(rs);

            }

        }
        catch (Exception e) {

            e.printStackTrace();

        }

        return null;
    }

    /*
        GET USER BY ID
    */
    public User getUserById(
            int userId) {

        String sql =
                "SELECT * "
                + "FROM users "
                + "WHERE user_id=?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(1, userId);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                return mapUser(rs);

            }

        }
        catch (Exception e) {

            e.printStackTrace();

        }

        return null;
    }
    /*
    GET USER BY VERIFICATION TOKEN
    */
    public User getUserByVerificationToken(
            String token) {

        String sql =
                "SELECT * "
                + "FROM users "
                + "WHERE verification_token=?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(1, token);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                return mapUser(rs);

            }

        }
        catch (Exception e) {

            e.printStackTrace();

        }

        return null;
    }
    /*
    VERIFY USER ACCOUNT
    */
    public boolean verifyUser(
            String token) {

        String sql =
                "UPDATE users "
                + "SET email_verified=true, "
                + "verification_token=NULL "
                + "WHERE verification_token=?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    token
            );

            return ps.executeUpdate() > 0;

        }
        catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }
    /*
        GET ALL USERS
    */
    public List<User> getAllUsers() {

        List<User> users =
                new ArrayList<>();

        String sql =
                "SELECT * "
                + "FROM users "
                + "ORDER BY created_at DESC";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            while (rs.next()) {

                users.add(
                        mapUser(rs)
                );

            }

        }
        catch (Exception e) {

            e.printStackTrace();

        }

        return users;
    }

    /*
        UPDATE USER
    */
    public boolean updateUser(
            User user) {

        String sql =
                "UPDATE users "
                + "SET username=?, "
                + "email=?, "
                + "role=? "
                + "WHERE user_id=?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    user.getUsername()
            );

            ps.setString(
                    2,
                    user.getEmail()
            );

            ps.setString(
                    3,
                    user.getRole()
            );

            ps.setInt(
                    4,
                    user.getUserId()
            );

            return ps.executeUpdate() > 0;

        }
        catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    /*
        UPDATE PASSWORD
    */
    public boolean updatePassword(
            int userId,
            String passwordHash) {

        String sql =
                "UPDATE users "
                + "SET password_hash=? "
                + "WHERE user_id=?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    passwordHash
            );

            ps.setInt(
                    2,
                    userId
            );

            return ps.executeUpdate() > 0;

        }
        catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    /*
        DELETE USER
    */
    public boolean deleteUser(
            int userId) {

        String sql =
                "DELETE FROM users "
                + "WHERE user_id=?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(1, userId);

            return ps.executeUpdate() > 0;

        }
        catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    /*
        TOTAL USERS
    */
    public int getUserCount() {

        String sql =
                "SELECT COUNT(*) "
                + "FROM users";

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
        CHECK USERNAME EXISTS
    */
    public boolean usernameExists(
            String username) {

        String sql =
                "SELECT 1 "
                + "FROM users "
                + "WHERE username=?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(1, username);

            ResultSet rs =
                    ps.executeQuery();

            return rs.next();

        }
        catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    /*
        CHECK EMAIL EXISTS
    */
    public boolean emailExists(
            String email) {

        String sql =
                "SELECT 1 "
                + "FROM users "
                + "WHERE email=?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(1, email);

            ResultSet rs =
                    ps.executeQuery();

            return rs.next();

        }
        catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    /*
        RESULTSET -> USER
    */
    private User mapUser(
            ResultSet rs)
            throws SQLException {

        User user =
                new User();

        user.setUserId(
                rs.getInt(
                        "user_id"
                )
        );

        user.setUsername(
                rs.getString(
                        "username"
                )
        );

        user.setEmail(
                rs.getString(
                        "email"
                )
        );

        user.setPasswordHash(
                rs.getString(
                        "password_hash"
                )
        );

        user.setRole(
                rs.getString(
                        "role"
                )
        );

        user.setCreatedAt(
                rs.getTimestamp(
                        "created_at"
                )
        );
        
        user.setEmailVerified(
        rs.getBoolean(
                "email_verified"
        )
        );

        user.setVerificationToken(
                rs.getString(
                        "verification_token"
                )
        );

        return user;
    }

}