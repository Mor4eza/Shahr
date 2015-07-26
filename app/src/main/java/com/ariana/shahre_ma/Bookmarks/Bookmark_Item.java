package com.ariana.shahre_ma.Bookmarks;

/**
 * Created by ariana2 on 7/26/2015.
 */
public class Bookmark_Item  {

private String title;
private Integer Id;

        public Bookmark_Item(String title,Integer Id) {
            super();
            this.title = title;
            this.Id=Id;

        }


        public String getTitle() {
            return title;
        }
        public int GetId() {
            return Id;
        }
}
