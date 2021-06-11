package com.alevel;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class LikeDao {
    private static final String USER = "root";
    private static final String PASSWORD = "rjv,fqyths";
    private static final String URL = "jdbc:mysql://localhost:3306/facebook";
    private static final String CHECK_BY_ID = "Select * from likes " +
            "where type=? and entity=? and user=?";
    private static final String ADD_BY_ID = "INSERT INTO `facebook`.`likes` (`type`, `entity`, `user`) " +
            "VALUES (?, ?, ?)";
    private static final String DEL_BY_ID = "DELETE FROM `facebook`.`likes` " +
            "WHERE (`type` = ? and `entity` = ? and `user` = ?)";
    private static final String COUNT_LIKES_BY_ENTITY = "Select COUNT(*) as count from likes " +
            "where type=? and entity=?";
    private static final String GET_USERS = "SELECT distinct facebook.likes.user,facebook.users.name FROM facebook.likes left join facebook.users on facebook.likes.user=facebook.users.id";

    public boolean checkLike(int type, int entity, int user){
        try(Connection connection = DriverManager.getConnection(URL,USER,PASSWORD)){
            PreparedStatement statement = connection.prepareStatement(CHECK_BY_ID);
            statement.setString(1,String.valueOf(type));
            statement.setString(2,String.valueOf(entity));
            statement.setString(3,String.valueOf(user));
            ResultSet result = statement.executeQuery();
            if (result.next()){
                /*System.out.println(result.getInt("id")
                        +" "+result.getInt("type")
                        +" "+result.getInt("entity")
                        +" "+result.getInt("user"));*/
                return true;
            } else
                //System.out.println("тест");
                return false;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public int countLikesByEntity(int type, int entity){
        try(Connection connection = DriverManager.getConnection(URL,USER,PASSWORD)){
            PreparedStatement statement = connection.prepareStatement(COUNT_LIKES_BY_ENTITY);
            statement.setString(1,String.valueOf(type));
            statement.setString(2,String.valueOf(entity));
            ResultSet result = statement.executeQuery();
            if (result.next())
                return result.getInt("count");
             else
            return 0;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public Collection<String> GetUsers(){
        try(Connection connection = DriverManager.getConnection(URL,USER,PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(GET_USERS);
            ResultSet result = statement.executeQuery();
            Collection<String> collection = new ArrayList<>();
            while (result.next()) {
                //System.out.println(result.getString("name"));
                collection.add(result.getString("name"));
            }
                return collection;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public void setLike(int type,int entity, int user){
        LikeDao dao = new LikeDao();
        if (!dao.checkLike(entity,type,user))
        try(Connection connection = DriverManager.getConnection(URL,USER,PASSWORD)){
            PreparedStatement statement = connection.prepareStatement(ADD_BY_ID);
            statement.setString(1,String.valueOf(type));
            statement.setString(2,String.valueOf(entity));
            statement.setString(3,String.valueOf(user));
            statement.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


    public void delLike(int type,int entity, int user){
        LikeDao dao = new LikeDao();
        if (dao.checkLike(entity,type,user))
            try(Connection connection = DriverManager.getConnection(URL,USER,PASSWORD)){
            PreparedStatement statement = connection.prepareStatement(DEL_BY_ID);
            statement.setString(1,String.valueOf(type));
            statement.setString(2,String.valueOf(entity));
            statement.setString(3,String.valueOf(user));
            statement.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


}
