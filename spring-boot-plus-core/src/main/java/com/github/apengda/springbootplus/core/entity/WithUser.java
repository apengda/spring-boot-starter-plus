package com.github.apengda.springbootplus.core.entity;

public interface WithUser {
    String getCreatorId();

    void setCreatorId(String creatorId);

    String getCreatorNick();

    void setCreatorNick(String creatorNick);

    String getUpdatorId();

    void setUpdatorId(String updatorId);

    String getUpdatorNick();

    void setUpdatorNick(String updatorNick);
}
