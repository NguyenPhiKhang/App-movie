package com.khangse.movieapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailerResponse {
    @SerializedName("id")
    private String id_trailer;
    @SerializedName("results")
    private List<Trailer> results;

    public TrailerResponse(String id_trailer, List<Trailer> results) {
        this.id_trailer = id_trailer;
        this.results = results;
    }

    public String getId_trailer() {
        return id_trailer;
    }

    public void setId_trailer(String id_trailer) {
        this.id_trailer = id_trailer;
    }

    public List<Trailer> getResults() {
        return results;
    }

    public void setResults(List<Trailer> results) {
        this.results = results;
    }
}
