package edu.uncc.cnnnews;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/*
a. Assignment #. In class assignment 05
b. File Name. InClass05_Mylavarapu.zip
c. Full name of all students in your group. Sai Yesaswy Mylavarapu
*/

public class NewsUtil {
    static public class NewsPullParser {

        static ArrayList<CNNNews> newsParser(InputStream in) throws IOException, XmlPullParserException {

            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(in, "UTF-8");
            CNNNews news = null;
            ArrayList<CNNNews> newsList = new ArrayList<CNNNews>();
            int event = parser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_TAG:
                        //if (parser.getName().equals("channel")) {
                            if (parser.getName().equals("item")) {
                                news = new CNNNews();
                            }
                                if(news!=null){
                                    if (parser.getName().equals("title")) {
                                        news.setTitle(parser.nextText().trim());}
                                }
                                if(news!=null){
                                    if (parser.getName().equals("pubDate")) {
                                        news.setPubdate(parser.nextText().trim());}
                                }
                                if(news!=null){
                                    if (parser.getName().equals("description")) {
                                        news.setDescription(parser.nextText().trim());}
                                }
                                if(news!=null){
                                    if(parser.getName().equals("media:content")){
                                        String height = parser.getAttributeValue(null,"height");
                                        String width = parser.getAttributeValue(null,"width");
                                        if(height==width){
                                            news.setUrltoimage(parser.getAttributeValue(null,"url"));}}
                                }
                                break;

                        case XmlPullParser.END_TAG:
                            if(parser.getName().equals("item")){
                            newsList.add(news);
                        }
                        default:
                            break;
                }
                event = parser.next();
            }
            return newsList;
        }
    }
}
