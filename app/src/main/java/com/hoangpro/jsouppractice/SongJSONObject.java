package com.hoangpro.jsouppractice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SongJSONObject {
    @SerializedName("Err")
    @Expose
    private Object err;
    @SerializedName("Song")
    @Expose
    private List<Song> song = null;

    public Object getErr() {
        return err;
    }

    public void setErr(Object err) {
        this.err = err;
    }

    public List<Song> getSong() {
        return song;
    }

    public void setSong(List<Song> song) {
        this.song = song;
    }

    public class Song {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("slug")
        @Expose
        private String slug;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("url_vimeo")
        @Expose
        private String urlVimeo;
        @SerializedName("check_download")
        @Expose
        private Integer checkDownload;
        @SerializedName("thumbnail")
        @Expose
        private String thumbnail;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("video_type")
        @Expose
        private String videoType;
        @SerializedName("video_length")
        @Expose
        private String videoLength;
        @SerializedName("level_id")
        @Expose
        private Integer levelId;
        @SerializedName("name_vn")
        @Expose
        private String nameVn;
        @SerializedName("name_ro")
        @Expose
        private String nameRo;
        @SerializedName("name_en")
        @Expose
        private String nameEn;
        @SerializedName("view")
        @Expose
        private Integer view;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrlVimeo() {
            return urlVimeo;
        }

        public void setUrlVimeo(String urlVimeo) {
            this.urlVimeo = urlVimeo;
        }

        public Integer getCheckDownload() {
            return checkDownload;
        }

        public void setCheckDownload(Integer checkDownload) {
            this.checkDownload = checkDownload;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getVideoType() {
            return videoType;
        }

        public void setVideoType(String videoType) {
            this.videoType = videoType;
        }

        public String getVideoLength() {
            return videoLength;
        }

        public void setVideoLength(String videoLength) {
            this.videoLength = videoLength;
        }

        public Integer getLevelId() {
            return levelId;
        }

        public void setLevelId(Integer levelId) {
            this.levelId = levelId;
        }

        public String getNameVn() {
            return nameVn;
        }

        public void setNameVn(String nameVn) {
            this.nameVn = nameVn;
        }

        public String getNameRo() {
            return nameRo;
        }

        public void setNameRo(String nameRo) {
            this.nameRo = nameRo;
        }

        public String getNameEn() {
            return nameEn;
        }

        public void setNameEn(String nameEn) {
            this.nameEn = nameEn;
        }

        public Integer getView() {
            return view;
        }

        public void setView(Integer view) {
            this.view = view;
        }

    }
}
