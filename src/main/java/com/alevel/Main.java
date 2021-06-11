package com.alevel;

import java.util.Collection;

public class Main {
    public static void main(String[] args) {
        LikeDao dao = new LikeDao();
        //type 1=user; type 2=photo; type 3=comment;
        dao.setLike(2,3,4);
        dao.setLike(2,3,4);
        dao.setLike(3,3,4);
        dao.delLike(3,3,4);
        dao.delLike(3,3,4);
        dao.setLike(3,3,4);
        System.out.println("count "+dao.countLikesByEntity(2,3));
        Collection<String> collection = dao.GetUsers();
        for (String user:collection) {
            System.out.println("User name:"+user);
        }
    }
}
