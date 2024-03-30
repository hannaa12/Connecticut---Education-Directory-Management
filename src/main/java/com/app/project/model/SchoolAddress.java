package com.app.project.model;

public class SchoolAddress {
    private int id;
    private int schoolId;
    private String districtName;
    private String address;
    private String town;
    private String zipcode;
    private Coordinate coordinate;

    public SchoolAddress() {
    }

    public SchoolAddress(int id, int schoolId, String districtName, String address, String town, String zipcode, Coordinate coordinate) {
        this.id = id;
        this.schoolId = schoolId;
        this.districtName = districtName;
        this.address = address;
        this.town = town;
        this.zipcode = zipcode;
        this.coordinate = coordinate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public static class Coordinate {
        private String latitude;
        private String longitude;

        public Coordinate() {
        }

        public Coordinate(String latitude, String longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }
    }
}
