package animelord.dao;

import animelord.entities.UploadQueue;
import animelord.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UploadQueueDAO {

    /*
        ADD TO QUEUE
    */
    public boolean addToQueue(
            UploadQueue queue) {

        String sql =
                "INSERT INTO upload_queue("
                + "episode_id,"
                + "status,"
                + "encode_1080p,"
                + "encode_720p,"
                + "encode_480p"
                + ") "
                + "VALUES(?,?,?,?,?)";

        try(
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ){

            ps.setInt(
                    1,
                    queue.getEpisodeId()
            );

            ps.setString(
                    2,
                    queue.getStatus()
            );

            ps.setBoolean(
                    3,
                    queue.isEncode1080p()
            );

            ps.setBoolean(
                    4,
                    queue.isEncode720p()
            );

            ps.setBoolean(
                    5,
                    queue.isEncode480p()
            );

            return ps.executeUpdate() > 0;

        }
        catch(Exception e){

            e.printStackTrace();

            return false;
        }
    }

    /*
        GET BY QUEUE ID
    */
    public UploadQueue getQueueItem(
            int queueId) {

        String sql =
                "SELECT * "
                + "FROM upload_queue "
                + "WHERE queue_id=?";

        try(
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ){

            ps.setInt(
                    1,
                    queueId
            );

            ResultSet rs =
                    ps.executeQuery();

            if(rs.next()){

                return mapQueue(
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
        GET BY EPISODE ID
    */
    public UploadQueue getQueueItemByEpisodeId(
            int episodeId) {

        String sql =
                "SELECT * "
                + "FROM upload_queue "
                + "WHERE episode_id=?";

        try(
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ){

            ps.setInt(
                    1,
                    episodeId
            );

            ResultSet rs =
                    ps.executeQuery();

            if(rs.next()){

                return mapQueue(
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
        GET ALL QUEUE ITEMS
    */
    public List<UploadQueue> getAllQueueItems() {

        List<UploadQueue> queueList =
                new ArrayList<>();

        String sql =
                "SELECT * "
                + "FROM upload_queue "
                + "ORDER BY created_at DESC";

        try(
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ){

            while(rs.next()){

                queueList.add(
                        mapQueue(rs)
                );

            }

        }
        catch(Exception e){

            e.printStackTrace();

        }

        return queueList;
    }

    /*
        UPDATE STATUS
    */
    public boolean updateStatus(
            int episodeId,
            String status) {

        String sql =
                "UPDATE upload_queue "
                + "SET status=? "
                + "WHERE episode_id=?";

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
                    episodeId
            );

            return ps.executeUpdate() > 0;

        }
        catch(Exception e){

            e.printStackTrace();

            return false;
        }
    }

    /*
        DELETE QUEUE ITEM
    */
    public boolean deleteQueueItem(
            int episodeId) {

        String sql =
                "DELETE FROM upload_queue "
                + "WHERE episode_id=?";

        try(
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ){

            ps.setInt(
                    1,
                    episodeId
            );

            return ps.executeUpdate() > 0;

        }
        catch(Exception e){

            e.printStackTrace();

            return false;
        }
    }

    /*
        TOTAL ITEMS
    */
    public int getQueueCount() {

        String sql =
                "SELECT COUNT(*) "
                + "FROM upload_queue";

        try(
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ){

            if(rs.next()){

                return rs.getInt(1);

            }

        }
        catch(Exception e){

            e.printStackTrace();

        }

        return 0;
    }

    /*
        GET ITEMS BY STATUS
    */
    public List<UploadQueue> getQueueByStatus(
            String status) {

        List<UploadQueue> queueList =
                new ArrayList<>();

        String sql =
                "SELECT * "
                + "FROM upload_queue "
                + "WHERE status=? "
                + "ORDER BY created_at ASC";

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

            ResultSet rs =
                    ps.executeQuery();

            while(rs.next()){

                queueList.add(
                        mapQueue(rs)
                );

            }

        }
        catch(Exception e){

            e.printStackTrace();

        }

        return queueList;
    }

    /*
        RESULTSET -> UPLOADQUEUE
    */
    private UploadQueue mapQueue(
            ResultSet rs)
            throws SQLException {

        UploadQueue queue =
                new UploadQueue();

        queue.setQueueId(
                rs.getInt(
                        "queue_id"
                )
        );

        queue.setEpisodeId(
                rs.getInt(
                        "episode_id"
                )
        );

        queue.setStatus(
                rs.getString(
                        "status"
                )
        );

        queue.setEncode1080p(
                rs.getBoolean(
                        "encode_1080p"
                )
        );

        queue.setEncode720p(
                rs.getBoolean(
                        "encode_720p"
                )
        );

        queue.setEncode480p(
                rs.getBoolean(
                        "encode_480p"
                )
        );

        queue.setCreatedAt(
                rs.getTimestamp(
                        "created_at"
                )
        );

        queue.setUpdatedAt(
                rs.getTimestamp(
                        "updated_at"
                )
        );

        return queue;
    }

}