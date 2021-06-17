package com.alevel;

public class Comments {
    int id;
    String text;
    int authorId;
    int photoId;

    public Comments(int id, String text, int authorId, int photoId) {
        this.id = id;
        this.text = text;
        this.authorId = authorId;
        this.photoId = photoId;
    }
}
