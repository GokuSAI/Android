package com.example.saikrishna.inclass08;

/**
 * Created by saikrishna on 10/31/17.
 */

public class flag {
    String tag;
    String edittextname;



    flag(){
        tag = "add";
        edittextname = "";

    }

    @Override
    public String toString() {
        return "flag{" +
                "tag='" + tag + '\'' +
                ", edittextname='" + edittextname + '\'' +
                '}';
    }
}
