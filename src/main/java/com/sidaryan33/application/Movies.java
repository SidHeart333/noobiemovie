package com.sidaryan33.application;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import com.sidaryan33.application.Logger;

public class Movies implements Parcelable {
    public static final Parcelable.Creator<Movies> CREATOR
            = new Parcelable.Creator<Movies>() {
        public Movies createFromParcel(Parcel in) {
            Logger.m("create from parcel :Movie");
            return new Movies(in);
        }

        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };
    private long id;
    private String title;
    private Date releaseDate;
    private double ratings;
    private String synopsis;
    private String urlThumbnail;
    private double popularity;
    private String urlPoster;

    public Movies() {

    }

    public Movies(Parcel input) {
        id = input.readLong();
        long dateMillis=input.readLong();
        releaseDate = (dateMillis == -1 ? null : new Date(dateMillis));
        title = input.readString();
        ratings = input.readDouble();
        popularity = input.readDouble();
        synopsis = input.readString();
        urlThumbnail = input.readString();
        urlPoster = input.readString();
    }

    public Movies(long id,
                  String title,
                  Date releaseDate,
                  double ratings,
                  double popularity,
                  String synopsis,
                  String urlThumbnail,
                  String urlPoster) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.ratings = ratings;
        this.synopsis = synopsis;
        this.urlThumbnail = urlThumbnail;
        this.urlPoster = urlPoster;
        this.popularity = popularity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double releaseDate) {
        this.popularity = popularity;
    }

    public double getRatings() {
        return ratings;
    }

    public void setRatings(double ratings) {
        this.ratings = ratings;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getUrlThumbnail() {
        return urlThumbnail;
    }

    public void setUrlThumbnail(String urlThumbnail) {
        this.urlThumbnail = urlThumbnail;
    }

    public String getUrlPoster() {
        return urlPoster;
    }

    public void setUrlPoster(String urlPoster) {
        this.urlPoster = urlPoster;
    }

    @Override
    public String toString() {
        return "\nID: " + id +
                "\nTitle " + title +
                "\nDate " + releaseDate +
                "\nSynopsis " + synopsis +
                "\nScore " + ratings +
                "\nPopularity " + popularity +
                "\nurlPoster " + urlPoster +
                "\nurlThumbnail " + urlThumbnail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeLong(releaseDate == null ? -1 : releaseDate.getTime());
        dest.writeDouble(ratings);
        dest.writeDouble(popularity);
        dest.writeString(synopsis);
        dest.writeString(urlThumbnail);
        dest.writeString(urlPoster);
    }
}
