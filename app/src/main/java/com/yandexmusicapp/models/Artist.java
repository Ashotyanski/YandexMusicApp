package com.yandexmusicapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Artist implements Serializable{
    //модель артиста для десериализации Gson
    //доступ к полям через геттеры

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("genres")
    @Expose
    private List<String> genres = new ArrayList<String>();
    @SerializedName("tracks")
    @Expose
    private Integer tracks;
    @SerializedName("albums")
    @Expose
    private Integer albums;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("cover")
    @Expose
    private Cover cover;

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     * The genres
     */
    public List<String> getGenres() {
        return genres;
    }

    //специальный геттер для вывода жанров артиста через запятую
    public String getImplodedGenres(){
        if(genres.size()==1){
            return genres.get(0);
        }
        else {
            String res = "";
            for (String genre : genres
                    ) {
                res += genre + ", ";
            }
            return res;
        }
    }

    /**
     *
     * @return
     * The tracks
     */
    public Integer getTracks() {
        return tracks;
    }

    /**
     *
     * @return
     * The albums
     */
    public Integer getAlbums() {
        return albums;
    }

    //еще один специальный геттер для вывода "репертуара" артиста одной строкой
    public String getRepertoire(){
        return albums+" альбомов, "+tracks+" песен";
    }
    /**
     *
     * @return
     * The link
     */
    public String getLink() {
        return link;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return
     * The cover
     */
    public Cover getCover() {
        return cover;
    }
}