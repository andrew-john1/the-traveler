package com.example.andrew.myapplication;

public class Destination {
    private long id;
    private String destination;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination= destination;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return destination;
    }
}
