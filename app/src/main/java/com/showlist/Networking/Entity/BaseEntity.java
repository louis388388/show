package com.showlist.Networking.Entity;

import java.util.List;

public class BaseEntity<T> {

    private List<Entity> data = null;

    public List<Entity> getData() {
        return data;
    }

}
