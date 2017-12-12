package com.example.saikrishna.jtrivia;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Created by saikrishna on 10/3/17.
 */

public class Questions implements  Parcelable {
    String id="",text="",answer="";
    String image="";
    JSONObject choices;
    JSONArray listOfChoices;



    ArrayList<String> options = new ArrayList();
    private int mData;

    public static Questions createQuestion(JSONObject js) throws JSONException {
        Questions questions = new Questions();
        questions.setId(js.getString("id"));
        questions.setText(js.getString("text"));
        try {
            questions.setImage(js.getString("image"));
        }catch (JSONException e){
            questions.setImage("");
            e.printStackTrace();
        }
        questions.setChoices(js.getJSONObject("choices"));
        questions.setAnswer();
        questions.setListOfChoices();
        return questions;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getImage() {
        return image;
    }

    public String getAnswer() {
        return answer;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> ch) {
        options = ch;
    }

    public JSONObject getChoices() {
        return choices;
    }

    public JSONArray getListOfChoices() {
        return listOfChoices;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setAnswer() throws JSONException {
        JSONObject ch = getChoices();
        this.answer = ch.getString("answer");
    }

    public void setChoices(JSONObject choices) {
        this.choices = choices;
    }


    public void setListOfChoices() throws JSONException {
        JSONObject ch = getChoices();
        this.listOfChoices = ch.getJSONArray("choice");
        for(int i =0;i<listOfChoices.length();i++){
            options.add(getListOfChoices().getString(i));
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(text);
        dest.writeString(answer);
        dest.writeString(image);
        dest.writeStringList(options);
    }
    public static final Parcelable.Creator<Questions> CREATOR
            = new Parcelable.Creator<Questions>() {
        public Questions createFromParcel(Parcel in) {
            return new Questions(in);
        }

        public Questions[] newArray(int size) {
            return new Questions[size];
        }
    };
    private Questions(Parcel in) {
        this.id = in.readString();
        this.text = in.readString();
        this.answer = in.readString();
        this.image = in.readString();
        in.readStringList(this.options);
    }
    public Questions(){

    }
}
