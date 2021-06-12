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
    private static final String CREATE_SCHEMA ="CREATE SCHEMA IF NOT EXISTS `facebook` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;";
    private static final String CREATE_TABLE1 =
            "CREATE TABLE IF NOT EXISTS `facebook`.`comments` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `text` VARCHAR(45) NOT NULL,\n" +
            "  `author` INT NOT NULL,\n" +
            "  `photo` INT NOT NULL,\n" +
            "  PRIMARY KEY (`id`))\n" +
            "ENGINE = InnoDB\n" +
            "DEFAULT CHARACTER SET = utf8mb4\n" +
            "COLLATE = utf8mb4_0900_ai_ci;";
    private static final String CREATE_TABLE2 =
            "CREATE TABLE IF NOT EXISTS `facebook`.`likes` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `type` INT NOT NULL,\n" +
            "  `entity` INT NOT NULL,\n" +
            "  `user` INT NOT NULL,\n" +
            "  PRIMARY KEY (`id`))\n" +
            "ENGINE = InnoDB\n" +
            "DEFAULT CHARACTER SET = utf8mb4\n" +
            "COLLATE = utf8mb4_0900_ai_ci;";
    private static final String CREATE_TABLE3 =
            "CREATE TABLE IF NOT EXISTS `facebook`.`photos` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(45) NOT NULL,\n" +
            "  `author` INT NOT NULL,\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)\n" +
            "ENGINE = InnoDB\n" +
            "DEFAULT CHARACTER SET = utf8mb4\n" +
            "COLLATE = utf8mb4_0900_ai_ci;";
    private static final String CREATE_TABLE4 =
            "CREATE TABLE IF NOT EXISTS `facebook`.`users` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)\n" +
            "ENGINE = InnoDB\n" +
            "DEFAULT CHARACTER SET = utf8mb4\n" +
            "COLLATE = utf8mb4_0900_ai_ci;";
    private static final String CREATE_DATA1 ="INSERT INTO `facebook`.`users` (`name`) VALUES ('коля')";
    private static final String CREATE_DATA2 ="INSERT INTO `facebook`.`users` (`name`) VALUES ('вова')";
    private static final String CREATE_DATA3 ="INSERT INTO `facebook`.`users` (`name`) VALUES ('вася')";
    private static final String CREATE_DATA4 ="INSERT INTO `facebook`.`users` (`name`) VALUES ('петя')";
    private static final String CREATE_DATA5 ="INSERT INTO `facebook`.`photos` (`name`, `author`) VALUES ('фото это я', '1')";
    private static final String CREATE_DATA6 ="INSERT INTO `facebook`.`photos` (`name`, `author`) VALUES ('фото это ты', '2')";
    private static final String CREATE_DATA7 ="INSERT INTO `facebook`.`photos` (`name`, `author`) VALUES ('фото это мой друг', '3')";
    private static final String CREATE_DATA8 ="INSERT INTO `facebook`.`photos` (`name`, `author`) VALUES ('общее фото', '1')";
    private static final String CREATE_DATA9 ="INSERT INTO `facebook`.`photos` (`name`, `author`) VALUES ('фото шкафа', '2')";
    private static final String CREATE_DATA10 ="INSERT INTO `facebook`.`photos` (`name`, `author`) VALUES ('фото полки', '3')";
    private static final String CREATE_DATA11 ="INSERT INTO `facebook`.`comments` (`text`, `author`, `photo`) VALUES ('привет', '1', '1')";
    private static final String CREATE_DATA12 ="INSERT INTO `facebook`.`comments` (`text`, `author`, `photo`) VALUES ('чувак!', '2', '2')";
    private static final String CREATE_DATA13 ="INSERT INTO `facebook`.`comments` (`text`, `author`, `photo`) VALUES ('как дела?', '3', '3')";
    private static final String CREATE_DATA14 ="INSERT INTO `facebook`.`comments` (`text`, `author`, `photo`) VALUES ('давай', '1', '2')";
    private static final String CREATE_DATA15 ="INSERT INTO `facebook`.`comments` (`text`, `author`, `photo`) VALUES ('дружить', '2', '3')";
    private static final String CREATE_DATA16 ="INSERT INTO `facebook`.`comments` (`text`, `author`, `photo`) VALUES ('с тобой', '3', '1')";
    private static final String CREATE_TMP_TABLE =
            "CREATE TEMPORARY TABLE `tmp_table`\n" +
                    "SELECT likes.type,\n" +
                    "entities.name as entity_name,\n" +
                    "users.name as user_name\n" +
                    "FROM facebook.likes as likes\n" +
                    "inner join facebook.users as users \n" +
                    "on likes.user=users.id\n" +
                    "inner join facebook.users as entities\n" +
                    "on likes.entity=entities.id\n" +
                    "where likes.type=1\n" +
                    "union\n" +
                    "SELECT likes.type,\n" +
                    "entities.name,\n" +
                    "users.name \n" +
                    "FROM facebook.likes as likes\n" +
                    "inner join facebook.users as users \n" +
                    "on likes.user=users.id\n" +
                    "inner join facebook.photos as entities\n" +
                    "on likes.entity=entities.id\n" +
                    "where likes.type=2\n" +
                    "union\n" +
                    "SELECT likes.type,\n" +
                    "entities.text,\n" +
                    "users.name \n" +
                    "FROM facebook.likes as likes\n" +
                    "inner join facebook.users as users \n" +
                    "on likes.user=users.id\n" +
                    "inner join facebook.comments as entities\n" +
                    "on likes.entity=entities.id\n" +
                    "where likes.type=3;";
    private static final String CREATE_TMP_ENTITIES =
            "CREATE TEMPORARY TABLE `tmp_table`\n" +
                    "SELECT 1 as type,\n" +
                    "entities.id as entity_id,\n" +
                    "entities.name as entity_name\n" +
                    "FROM facebook.users as entities\n" +
                    "union\n" +
                    "SELECT 2,\n" +
                    "entities.id,\n" +
                    "entities.name \n" +
                    "FROM facebook.photos as entities\n" +
                    "union\n" +
                    "SELECT 3,\n" +
                    "entities.id,\n" +
                    "entities.text \n" +
                    "FROM facebook.comments as entities";

    private static final String CHECK_BY_ID = "Select * from likes " +
            "where type=? and entity=? and user=?";
    private static final String GET_ENTITY_ID = "Select * from tmp_table " +
            "where type=? and entity_name=?";
    private static final String ADD_BY_ID = "INSERT INTO `facebook`.`likes` (`type`, `entity`, `user`) " +
            "VALUES (?, ?, ?)";
    private static final String DEL_BY_ID = "DELETE FROM `facebook`.`likes` " +
            "WHERE (`type` = ? and `entity` = ? and `user` = ?)";
    private static final String COUNT_LIKES_BY_ENTITY = "Select COUNT(*) as count from tmp_table " +
            "where type=? and entity_name=?";
    private static final String GET_USERS = "SELECT distinct facebook.likes.user,facebook.users.name FROM facebook.likes left join facebook.users on facebook.likes.user=facebook.users.id";
    private static final String GET_USER_ID = "Select * from users where name=?";

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
    public boolean checkLike(Type type, int entity_id, int user_id) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(CHECK_BY_ID);
            statement.setString(1, String.valueOf(type.ordinal() + 1));
            statement.setString(2, String.valueOf(entity_id));
            statement.setString(3, String.valueOf(user_id));
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                /*System.out.println(result.getInt("id")
                        +" "+result.getInt("type")
                        +" "+result.getInt("entity")
                        +" "+result.getInt("user"));*/
                return true;
            } else
                //System.out.println("тест");
                return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int getUserId(String user) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(GET_USER_ID);
            statement.setString(1, String.valueOf(user));
            ResultSet result = statement.executeQuery();
            if (result.next())
                return result.getInt("id");
            else
                return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int getEntityId(Type type, String entity) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(CREATE_TMP_ENTITIES);
            statement.executeUpdate();
            statement = connection.prepareStatement(GET_ENTITY_ID);
            statement.setString(1, String.valueOf(type.ordinal() + 1));
            statement.setString(2, String.valueOf(entity));
            ResultSet result = statement.executeQuery();
            if (result.next())
                return result.getInt("entity_id");
            else
                return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int countLikesByEntity(Type type, String entity) {
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
            statement.setString(1, String.valueOf(type.ordinal() + 1));
            statement.setString(2, String.valueOf(entity));
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
    public void setLike(Type type, String entity, String user) {
        LikeDao dao = new LikeDao();
        int entity_id = dao.getEntityId(type, entity);
        int user_id = dao.getUserId(user);
        if (entity_id == 0 || user_id == 0)
            return;
        if (!dao.checkLike(type,entity_id, user_id))
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                PreparedStatement statement = connection.prepareStatement(ADD_BY_ID);
                statement.setString(1, String.valueOf(type.ordinal() + 1));
                statement.setString(2, String.valueOf(entity_id));
                statement.setString(3, String.valueOf(user_id));
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }
    public void deleteLike(Type type, String entity, String user) {
        LikeDao dao = new LikeDao();
        if (dao.checkLike(type, dao.getEntityId(type, entity), dao.getUserId(user)))
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                PreparedStatement statement = connection.prepareStatement(DEL_BY_ID);
                statement.setString(1, String.valueOf(type.ordinal() + 1));
                statement.setString(2, String.valueOf(dao.getEntityId(type, entity)));
                statement.setString(3, String.valueOf(dao.getUserId(user)));
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }
}
