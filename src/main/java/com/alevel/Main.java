package com.alevel;

import java.util.Collection;

public class Main {
    public static void main(String[] args) {
        LikeDao dao = new LikeDao();
        dao.createSchema();
        //type 0=user; type 1=photo; type 2=comment;
        dao.setLike(Type.COMMENT,"как дела?","вася","фото это мой друг","вася","коля");
        dao.setLike(Type.USER,"вася",null,null,null,"коля");
        dao.setLike(Type.PHOTO,"фото это мой друг","вася",null,null,"коля");
        dao.setLike(Type.PHOTO,"фото шкафа","вова",null,null,"петя");
        //dao.setLike(Type.PHOTO,"фото это мой друг","вася",null,null,"вова"); //этот юзер лайки не ставил, он скучный
        dao.setLike(Type.PHOTO,"фото это мой друг","вася",null,null,"вася");
        dao.setLike(Type.PHOTO,"фото это мой друг","вася",null,null,"вася");//повторно не лайкается
        dao.setLike(Type.PHOTO,"фото это мой враг","вася",null,null,"вася");//такого фото нет не лайкается
        dao.setLike(Type.PHOTO,"фото это мой друг","вася",null,null,"рома");//такого юзера нет не лайкается
        dao.deleteLike(Type.PHOTO,"фото это мой друг","вася",null,null,"вася");
        dao.deleteLike(Type.PHOTO,"фото это мой друг","вася",null,null,"вася");//повторно не удалится
        dao.setLike(Type.PHOTO,"фото это мой друг","вася",null,null,"вася");
        System.out.println("count "+dao.countLikesByEntity(Type.PHOTO, "фото это мой друг","вася",null,null));
        Collection<String> collection = dao.GetUsers();
        for (String user:collection) {
            System.out.println("User name:"+user);
        }
    }
}
