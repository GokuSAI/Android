package com.example.saikrishna.recipepuppy;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by saikrishna on 10/2/17.
 */

public class ItemsUtil {
    static public class RecipesPullParser {
        static ArrayList<Items> ParseRecipes (InputStream in) throws XmlPullParserException, IOException {
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(in,"UTF-8");
            Items items = null;
            ArrayList<Items> itemslist = new ArrayList<Items>();
            int event = parser.getEventType();

            while(event!= XmlPullParser.END_DOCUMENT){
                switch (event){
                    case XmlPullParser.START_TAG:
                        if(parser.getName().equals("recipe")){
                             items = new Items();
                        }

                            if(parser.getName().equals("title")){
                                if(items!= null) {
                                    Log.d("Entered", "LOOP");
                                    items.setTitle(parser.nextText().trim());
                                    //Log.d("ITEMS", parser.nextText().trim());
                                }
                        }
                        if(parser.getName().equals("href")){
                            if(items!= null){
                                items.setHref(parser.nextText());
                                //Log.d("HREF",parser.nextText().trim());
                            }
                        }
                        if(parser.getName().equals("ingredients")){
                            if(items!= null){
                                items.setIngredients(parser.nextText().trim());
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("recipe")){
                            itemslist.add(items);
                            items= null;
                        }
                        break;
                    default:break;
                }
                event=parser.next();
            }

            return  itemslist;
        }

        }
    }

