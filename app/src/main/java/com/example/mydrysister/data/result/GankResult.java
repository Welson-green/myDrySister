package com.example.mydrysister.data.result;

import com.example.mydrysister.data.dto.GankMeizi;

import java.util.ArrayList;

public class GankResult {
    private Boolean error;
    private ArrayList<GankMeizi> results;

    public GankResult() { }

    public GankResult(Boolean error,ArrayList<GankMeizi> results){
        this.error = error;
        this.results = results;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public ArrayList<GankMeizi> getResults(){
        return results;
    }


    public void setResults(ArrayList<GankMeizi> results){
        this.results = results;
    }


    @Override
    public String toString() {
        return "GankResult{" + "error=" + error + ",results=" + results.toString() + '}';
    }

}
