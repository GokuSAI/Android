package edu.uncc.cnnnews;

/*
a. Assignment #. In class assignment 05
b. File Name. InClass05_Mylavarapu.zip
c. Full name of all students in your group. Sai Yesaswy Mylavarapu
*/
/**
 * Created by Hello on 2/13/2017.
 */

public class CNNNews {

    private String title;
    private String description;
    private String urltoimage;
    private String pubdate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrltoimage() {
        return urltoimage;
    }

    public void setUrltoimage(String urltoimage) {
        this.urltoimage = urltoimage;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    @Override
    public String toString() {
        return "CNNNews{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", urltoimage='" + urltoimage + '\'' +
                ", pubdate='" + pubdate + '\'' +
                '}';
    }
}
