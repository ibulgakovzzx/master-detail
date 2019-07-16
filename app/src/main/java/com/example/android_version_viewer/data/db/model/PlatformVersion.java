package com.example.android_version_viewer.data.db.model;

import java.io.Serializable;

public class PlatformVersion implements Serializable {

    public static final String TABLE_NAME = "platform_version";

    public static final String VERSION = "version";
    public static final String NAME = "name";
    public static final String RELEASED = "released";
    public static final String API = "api";
    public static final String DISTRIBUTION = "distribution";
    public static final String FAVOURITE = "favourite";
    public static final String DESCRIPTION = "description";

    private String version;
    private String name;
    private String released;
    private Integer api;
    private Float distribution;
    private Boolean favourite;
    private String description;

    public PlatformVersion(String version, String name, String released, Integer api, Float distribution, Boolean favourite, String description) {
        this.version = version;
        this.name = name;
        this.released = released;
        this.api = api;
        this.distribution = distribution;
        this.favourite = favourite;
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public Integer getApi() {
        return api;
    }

    public void setApi(Integer api) {
        this.api = api;
    }

    public Float getDistribution() {
        return distribution;
    }

    public void setDistribution(Float distribution) {
        this.distribution = distribution;
    }

    public Boolean getFavourite() {
        return favourite;
    }

    public void setFavourite(Boolean favourite) {
        this.favourite = favourite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
