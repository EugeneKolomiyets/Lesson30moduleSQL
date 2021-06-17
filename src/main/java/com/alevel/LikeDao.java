package com.alevel;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class LikeDao {
    private static final String USER = "root";
    private static final String PASSWORD = "rjv,fqyths";
    private static final String URL = "jdbc:mysql://localhost:3306/facebook";
    private static final String URL0 = "jdbc:mysql://localhost:3306";
    private static final String CHECK_SCHEMA = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = 'facebook'";
    private static final String CREATE_SCHEMA ="CREATE SCHEMA IF NOT EXISTS `facebook`;";
    private static final String CREATE_TABLE1 =
            "CREATE TABLE IF NOT EXISTS `facebook`.`comments` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `text` VARCHAR(45) NOT NULL,\n" +
            "  `authorId` INT NOT NULL,\n" +
            "  `photoId` INT NOT NULL,\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);";
    private static final String CREATE_TABLE2 =
            "CREATE TABLE IF NOT EXISTS `facebook`.`likes` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `type` INT NOT NULL,\n" +
            "  `entityId` INT NOT NULL,\n" +
            "  `userId` INT NOT NULL,\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);";
    private static final String CREATE_TABLE3 =
            "CREATE TABLE IF NOT EXISTS `facebook`.`photos` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(45) NOT NULL,\n" +
            "  `authorId` INT NOT NULL,\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);";
    private static final String CREATE_TABLE4 =
            "CREATE TABLE IF NOT EXISTS `facebook`.`users` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);";
    private static final String CREATE_DATA1 ="INSERT INTO `facebook`.`users` (`name`) VALUES ('коля')";
    private static final String CREATE_DATA2 ="INSERT INTO `facebook`.`users` (`name`) VALUES ('вова')";
    private static final String CREATE_DATA3 ="INSERT INTO `facebook`.`users` (`name`) VALUES ('вася')";
    private static final String CREATE_DATA4 ="INSERT INTO `facebook`.`users` (`name`) VALUES ('петя')";
    private static final String CREATE_DATA5 ="INSERT INTO `facebook`.`photos` (`name`, `authorId`) VALUES ('фото это я', '1')";
    private static final String CREATE_DATA6 ="INSERT INTO `facebook`.`photos` (`name`, `authorId`) VALUES ('фото это ты', '2')";
    private static final String CREATE_DATA7 ="INSERT INTO `facebook`.`photos` (`name`, `authorId`) VALUES ('фото это мой друг', '3')";
    private static final String CREATE_DATA8 ="INSERT INTO `facebook`.`photos` (`name`, `authorId`) VALUES ('общее фото', '1')";
    private static final String CREATE_DATA9 ="INSERT INTO `facebook`.`photos` (`name`, `authorId`) VALUES ('фото шкафа', '2')";
    private static final String CREATE_DATA10 ="INSERT INTO `facebook`.`photos` (`name`, `authorId`) VALUES ('фото полки', '3')";
    private static final String CREATE_DATA11 ="INSERT INTO `facebook`.`comments` (`text`, `authorId`, `photoId`) VALUES ('привет', '1', '1')";
    private static final String CREATE_DATA12 ="INSERT INTO `facebook`.`comments` (`text`, `authorId`, `photoId`) VALUES ('чувак!', '2', '2')";
    private static final String CREATE_DATA13 ="INSERT INTO `facebook`.`comments` (`text`, `authorId`, `photoId`) VALUES ('как дела?', '3', '3')";
    private static final String CREATE_DATA14 ="INSERT INTO `facebook`.`comments` (`text`, `authorId`, `photoId`) VALUES ('давай', '1', '2')";
    private static final String CREATE_DATA15 ="INSERT INTO `facebook`.`comments` (`text`, `authorId`, `photoId`) VALUES ('дружить', '2', '3')";
    private static final String CREATE_DATA16 ="INSERT INTO `facebook`.`comments` (`text`, `authorId`, `photoId`) VALUES ('с тобой', '3', '1')";
    private static final String CREATE_TMP_TABLE =
            "CREATE TEMPORARY TABLE `tmp_table`\n" +
                    "SELECT likes.type,\n" +
                    "entities.id as entityId,\n" +
                    "entities.name as entityName,\n" +
                    "users.name as userName\n" +
                    "FROM facebook.likes as likes\n" +
                    "inner join facebook.users as users \n" +
                    "on likes.userId=users.id\n" +
                    "inner join facebook.users as entities\n" +
                    "on likes.entityId=entities.id\n" +
                    "where likes.type=0\n" +
                    "union\n" +
                    "SELECT likes.type,\n" +
                    "entities.id,\n" +
                    "entities.name,\n" +
                    "users.name \n" +
                    "FROM facebook.likes as likes\n" +
                    "inner join facebook.users as users \n" +
                    "on likes.userId=users.id\n" +
                    "inner join facebook.photos as entities\n" +
                    "on likes.entityId=entities.id\n" +
                    "where likes.type=1\n" +
                    "union\n" +
                    "SELECT likes.type,\n" +
                    "entities.id,\n" +
                    "entities.text,\n" +
                    "users.name \n" +
                    "FROM facebook.likes as likes\n" +
                    "inner join facebook.users as users \n" +
                    "on likes.userId=users.id\n" +
                    "inner join facebook.comments as entities\n" +
                    "on likes.entityId=entities.id\n" +
                    "where likes.type=2;";
    private static final String CREATE_TMP_ENTITIES =
            "CREATE TEMPORARY TABLE `tmp_table`\n" +
                    "SELECT 0 as type,\n" +
                    "entities.id as entityId,\n" +
                    "entities.name as entityName,\n" +
                    "0 as entityAuthorId,\n" +
                    "0 as entityPhotoId\n" +
                    "FROM facebook.users as entities\n" +
                    "union\n" +
                    "SELECT 1,\n" +
                    "entities.id,\n" +
                    "entities.name,\n" +
                    "entities.authorId,\n" +
                    "0\n" +
                    "FROM facebook.photos as entities\n" +
                    "union\n" +
                    "SELECT 2,\n" +
                    "entities.id,\n" +
                    "entities.text,\n" +
                    "entities.authorId,\n" +
                    "entities.photoId\n" +
                    "FROM facebook.comments as entities";

    private static final String CHECK_BY_ID = "Select * from likes " +
            "where type=? and entityId=? and userId=?";
    private static final String GET_ENTITY_ID = "Select * from tmp_table " +
            "where type=? and entityName=? and entityAuthorId=? and entityPhotoId=?";
    private static final String ADD_BY_ID = "INSERT INTO `facebook`.`likes` (`type`, `entityId`, `userId`) " +
            "VALUES (?, ?, ?)";
    private static final String DEL_BY_ID = "DELETE FROM `facebook`.`likes` " +
            "WHERE (`type` = ? and `entityId` = ? and `userId` = ?)";
    private static final String COUNT_LIKES_BY_ENTITY = "Select COUNT(*) as count from tmp_table " +
            "where type=? and entityId=?";
    private static final String GET_USERS = "SELECT distinct facebook.likes.userId,facebook.users.name FROM facebook.likes left join facebook.users on facebook.likes.userId=facebook.users.id";
    private static final String GET_USER = "Select * from users where name=?";
    private static final String GET_PHOTO = "Select * from photos where name=? and authorId=?";

    public boolean checkSchema() {
        try (Connection connection = DriverManager.getConnection(URL0, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(CHECK_SCHEMA);
            ResultSet result = statement.executeQuery();
            if (result.next())
                if (result.getString("SCHEMA_NAME").equals("facebook"))
                    return true;
                else
                    return false;
            else
                return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void createSchema() {
        LikeDao dao = new LikeDao();
        if (!dao.checkSchema()) {
            try (Connection connection = DriverManager.getConnection(URL0, USER, PASSWORD)) {
                connection.setAutoCommit(false);
                try (Statement stmt = connection.createStatement()) {
                    stmt.executeUpdate(CREATE_SCHEMA);
                    stmt.executeUpdate(CREATE_TABLE1);
                    stmt.executeUpdate(CREATE_TABLE2);
                    stmt.executeUpdate(CREATE_TABLE3);
                    stmt.executeUpdate(CREATE_TABLE4);
                    stmt.executeUpdate(CREATE_DATA1);
                    stmt.executeUpdate(CREATE_DATA2);
                    stmt.executeUpdate(CREATE_DATA3);
                    stmt.executeUpdate(CREATE_DATA4);
                    stmt.executeUpdate(CREATE_DATA5);
                    stmt.executeUpdate(CREATE_DATA6);
                    stmt.executeUpdate(CREATE_DATA7);
                    stmt.executeUpdate(CREATE_DATA8);
                    stmt.executeUpdate(CREATE_DATA9);
                    stmt.executeUpdate(CREATE_DATA10);
                    stmt.executeUpdate(CREATE_DATA11);
                    stmt.executeUpdate(CREATE_DATA12);
                    stmt.executeUpdate(CREATE_DATA13);
                    stmt.executeUpdate(CREATE_DATA14);
                    stmt.executeUpdate(CREATE_DATA15);
                    stmt.executeUpdate(CREATE_DATA16);
                    connection.commit();
                } catch (SQLException e) {
                    connection.rollback();
                    throw e;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
    public boolean checkLike(Type type, int entityId, int userId) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(CHECK_BY_ID);
            statement.setString(1, String.valueOf(type.ordinal()));
            statement.setString(2, String.valueOf(entityId));
            statement.setString(3, String.valueOf(userId));
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                /*System.out.println(result.getInt("id")
                        +" "+result.getInt("type")
                        +" "+result.getInt("entity")
                        +" "+result.getInt("userId"));*/
                return true;
            } else
                //System.out.println("тест");
                return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Photo getPhoto(String photoName,String photoAuthorName) {
        User author = getUserByName(photoAuthorName);
        if (author == null)
            return null;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(GET_PHOTO);
            statement.setString(1, String.valueOf(photoName));
            statement.setString(2, String.valueOf(author.id));
            ResultSet result = statement.executeQuery();
            if (result.next())
                return new Photo(result.getInt("id"), result.getString("name"), result.getInt("authorId"));
            else
                return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public User getUserByName(String userName) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(GET_USER);
            statement.setString(1, String.valueOf(userName));
            ResultSet result = statement.executeQuery();
            if (result.next())
                return new User(result.getInt("id"), result.getString("name"));
            else
                return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int getEntityId(Type type, String entityName, String entityAuthorName, String commentPhotoName,String commentPhotoAuthorName) {
        Photo photo = getPhoto(commentPhotoName,commentPhotoAuthorName);
        User author = getUserByName(entityAuthorName);
        int photoId = 0;
        if (photo != null)
            photoId = photo.id;
        int authorId = 0;
        if (author != null)
            authorId = author.id;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(CREATE_TMP_ENTITIES);
            statement.executeUpdate();
            statement = connection.prepareStatement(GET_ENTITY_ID);
            statement.setString(1, String.valueOf(type.ordinal()));
            statement.setString(2, String.valueOf(entityName));
            statement.setString(3, String.valueOf(authorId));
            statement.setString(4, String.valueOf(photoId));
            ResultSet result = statement.executeQuery();
            if (result.next())
                return result.getInt("entityId");
            else
                return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int countLikesByEntity(Type type, String entityName, String entityAuthorName, String commentPhotoName,String commentPhotoAuthorName) {
        int entityId = getEntityId(type, entityName, entityAuthorName, commentPhotoName,commentPhotoAuthorName);
        if (entityId == 0)
            return 0;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(CREATE_TMP_TABLE);
            statement.executeUpdate();
            /*statement = connection.prepareStatement("select * from tmp_table where type=2");
            ResultSet result0 = statement.executeQuery();
            while (result0.next()) {
                System.out.println(result0.getString("type")
                +" "+result0.getString("entity_id")
                +" "+result0.getString("entity_name")
                +" "+result0.getString("user_name"));
            }*/
            statement = connection.prepareStatement(COUNT_LIKES_BY_ENTITY);
            statement.setString(1, String.valueOf(type.ordinal()));
            statement.setString(2, String.valueOf(entityId));
            ResultSet result = statement.executeQuery();
            if (result.next())
                return result.getInt("count");
            else
                return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Collection<String> GetUsers() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(GET_USERS);
            ResultSet result = statement.executeQuery();
            Collection<String> collection = new ArrayList<>();
            while (result.next()) {
                //System.out.println(result.getString("name"));
                collection.add(result.getString("name"));
            }
            return collection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void setLike(Type type, String entityName, String entityAuthorName, String commentPhotoName,String commentPhotoAuthorName, String userName) {
        int entityId = getEntityId(type, entityName, entityAuthorName, commentPhotoName,commentPhotoAuthorName);
        User user= getUserByName(userName);
        if (entityId == 0 || user == null)
            return;
        if (!checkLike(type,entityId, user.id))
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                PreparedStatement statement = connection.prepareStatement(ADD_BY_ID);
                statement.setString(1, String.valueOf(type.ordinal()));
                statement.setString(2, String.valueOf(entityId));
                statement.setString(3, String.valueOf(user.id));
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }
    public void deleteLike(Type type, String entityName, String entityAuthorName, String commentPhotoName,String commentPhotoAuthorName, String userName) {
        int entityId = getEntityId(type, entityName, entityAuthorName, commentPhotoName,commentPhotoAuthorName);
        User user= getUserByName(userName);
        if (entityId == 0 || user == null)
            return;
        if (checkLike(type, entityId, user.id))
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                PreparedStatement statement = connection.prepareStatement(DEL_BY_ID);
                statement.setString(1, String.valueOf(type.ordinal()));
                statement.setString(2, String.valueOf(entityId));
                statement.setString(3, String.valueOf(user.id));
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }
}
