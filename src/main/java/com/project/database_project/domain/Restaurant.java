package com.project.database_project.domain;

public class Restaurant {

    private String restaurname;
    private String city;
    private int capacity;
    private String rating;
    private String reportsresults2;

    public Restaurant(String restaurname, String city, int capacity, String rating, String reportsresults2) {
        this.restaurname = restaurname;
        this.city = city;
        this.capacity = capacity;
        this.rating = rating;
        this.reportsresults2 = reportsresults2;
    }

    public String getRestaurname() {
        return restaurname;
    }

    public void setRestaurname(String restaurname) {
        this.restaurname = restaurname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReportsresults2() {
        return reportsresults2;
    }

    public void setReportsresults2(String reportsresults2) {
        this.reportsresults2 = reportsresults2;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "restaurname='" + restaurname + '\'' +
                ", city='" + city + '\'' +
                ", capacity=" + capacity +
                ", rating='" + rating + '\'' +
                ", reportsresults2='" + reportsresults2 + '\'' +
                '}';
    }
}
