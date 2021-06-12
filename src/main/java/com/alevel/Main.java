package com.alevel;

import java.util.Collection;

public class Main {
    public static void main(String[] args) {
        LikeDao dao = new LikeDao();
        dao.createSchema();
        //type 1=user; type 2=photo; type 3=comment;
        dao.setLike(Type.COMMENT,"как дела?","коля");
        dao.setLike(Type.USER,"вася","коля");
        dao.setLike(Type.PHOTO,"фото это мой друг","коля");
        dao.setLike(Type.PHOTO,"фото шкафа","петя");
        //dao.setLike(Type.PHOTO,"фото это мой друг","вова"); //этот юзер лайки не ставил, он скучный
        dao.setLike(Type.PHOTO,"фото это мой друг","вася");
        dao.setLike(Type.PHOTO,"фото это мой друг","вася");//повторно не лайкается
        dao.setLike(Type.PHOTO,"фото это мой враг","вася");//такого фото нет не лайкается
        dao.setLike(Type.PHOTO,"фото это мой друг","рома");//такого юзера нет не лайкается
        dao.deleteLike(Type.PHOTO,"фото это мой друг","вася");
        dao.deleteLike(Type.PHOTO,"фото это мой друг","вася");//повторно не удалится
        dao.setLike(Type.PHOTO,"фото это мой друг","вася");
        System.out.println("count "+dao.countLikesByEntity(Type.PHOTO, "фото это мой друг"));
        Collection<String> collection = dao.GetUsers();
        for (String user:collection) {
            System.out.println("User name:"+user);
        }
    }
}
