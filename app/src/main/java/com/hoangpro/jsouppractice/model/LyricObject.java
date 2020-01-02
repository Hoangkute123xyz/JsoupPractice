package com.hoangpro.jsouppractice.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hoangpro.jsouppractice.morefunc.MyFunct;

public class LyricObject {

    @SerializedName("Err")
    @Expose
    private Object err;
    @SerializedName("Data")
    @Expose
    private List<Datum> data = null;

    public Object getErr() {
        return err;
    }

    public void setErr(Object err) {
        this.err = err;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public class Datum {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("song_id")
        @Expose
        private Integer songId;
        @SerializedName("start_time")
        @Expose
        private String startTime;
        @SerializedName("end_time")
        @Expose
        private String endTime;
        @SerializedName("period_order")
        @Expose
        private Integer periodOrder;
        @SerializedName("sentence_id")
        @Expose
        private Integer sentenceId;
        @SerializedName("language_code")
        @Expose
        private String languageCode;
        @SerializedName("sentence_value")
        @Expose
        private String sentenceValue;
        @SerializedName("sentence_hira")
        @Expose
        private String sentenceHira;
        @SerializedName("sentence_ro")
        @Expose
        private String sentenceRo;
        @SerializedName("sentence_translates")
        @Expose
        private String sentenceTranslates;

        private String furiganaText;
        public void setFuriganaText(){
            this.furiganaText= MyFunct.toFurigana(this.sentenceValue,this.sentenceHira);
        }
        public String getFuriganaText(){
            return this.furiganaText;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getSongId() {
            return songId;
        }

        public void setSongId(Integer songId) {
            this.songId = songId;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public Integer getPeriodOrder() {
            return periodOrder;
        }

        public void setPeriodOrder(Integer periodOrder) {
            this.periodOrder = periodOrder;
        }

        public Integer getSentenceId() {
            return sentenceId;
        }

        public void setSentenceId(Integer sentenceId) {
            this.sentenceId = sentenceId;
        }

        public String getLanguageCode() {
            return languageCode;
        }

        public void setLanguageCode(String languageCode) {
            this.languageCode = languageCode;
        }

        public String getSentenceValue() {
            return sentenceValue;
        }

        public void setSentenceValue(String sentenceValue) {
            this.sentenceValue = sentenceValue;
        }

        public String getSentenceHira() {
            return sentenceHira;
        }

        public void setSentenceHira(String sentenceHira) {
            this.sentenceHira = sentenceHira;
        }

        public String getSentenceRo() {
            return sentenceRo;
        }

        public void setSentenceRo(String sentenceRo) {
            this.sentenceRo = sentenceRo;
        }

        public String getSentenceTranslates() {
            return sentenceTranslates;
        }

        public void setSentenceTranslates(String sentenceTranslates) {
            this.sentenceTranslates = sentenceTranslates;
        }

    }

}