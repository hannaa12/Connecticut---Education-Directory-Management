package com.app.project.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SchoolAPIData {
    @JsonProperty("district_name")
    private String districtName;
    private String name;
    @JsonProperty("organization_type")
    private String organizationType;
    @JsonProperty("organization_code")
    private String organizationCode;
    private String address;
    private String town;
    private String zipcode;
    private String phone;
    @JsonProperty("prekindergarten")
    private boolean hasPreKindergarten;
    private boolean hasKindergarten;
    @JsonProperty("grade_1")
    private boolean hasGrade1;
    @JsonProperty("grade_2")
    private boolean hasGrade2;
    @JsonProperty("grade_3")
    private boolean hasGrade3;
    @JsonProperty("grade_4")
    private boolean hasGrade4;
    @JsonProperty("grade_5")
    private boolean hasGrade5;
    @JsonProperty("grade_6")
    private boolean hasGrade6;
    @JsonProperty("grade_7")
    private boolean hasGrade7;
    @JsonProperty("grade_8")
    private boolean hasGrade8;
    @JsonProperty("grade_9")
    private boolean hasGrade9;
    @JsonProperty("grade_10")
    private boolean hasGrade10;
    @JsonProperty("grade_11")
    private boolean hasGrade11;
    @JsonProperty("grade_12")
    private boolean hasGrade12;
    @JsonProperty("student_open_date")
    private String studentOpenDate;
    @JsonProperty("geocoded_column")
    private GeoCodedData geoCodedData;

    public SchoolAPIData() {
    }

    public SchoolAPIData(String districtName, String name, String organizationType, String organizationCode, String address, String town, String zipcode, String phone, String preKindergarten, String kindergarten, String grade1, String grade2, String grade3, String grade4, String grade5, String grade6, String grade7, String grade8, String grade9, String grade10, String grade11, String grade12, String studentOpenDate) {
        this.districtName = districtName;
        this.name = name;
        this.organizationType = organizationType;
        this.organizationCode = organizationCode;
        this.address = address;
        this.town = town;
        this.zipcode = zipcode;
        this.phone = phone;
        this.hasPreKindergarten = preKindergarten.equals("1");
        this.hasKindergarten = kindergarten.equals("1");
        this.hasGrade1 = grade1.equals("1");
        this.hasGrade2 = grade2.equals("1");
        this.hasGrade3 = grade3.equals("1");
        this.hasGrade4 = grade4.equals("1");
        this.hasGrade5 = grade5.equals("1");
        this.hasGrade6 = grade6.equals("1");
        this.hasGrade7 = grade7.equals("1");
        this.hasGrade8 = grade8.equals("1");
        this.hasGrade9 = grade9.equals("1");
        this.hasGrade10 = grade10.equals("1");
        this.hasGrade11 = grade11.equals("1");
        this.hasGrade12 = grade12.equals("1");
        this.studentOpenDate = studentOpenDate;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(String organizationType) {
        this.organizationType = organizationType;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean hasPreKindergarten() {
        return hasPreKindergarten;
    }

    public void setHasPreKindergarten(String kindergarten) {
        this.hasPreKindergarten = kindergarten.equals("1");
    }

    public boolean hasKindergarten() {
        return hasKindergarten;
    }

    public void setHasKindergarten(String kindergarten) {
        this.hasKindergarten = kindergarten.equals("1");
    }

    public boolean hasGrade1() {
        return hasGrade1;
    }

    public void setHasGrade1(String grade1) {
        this.hasGrade1 = grade1.equals("1");
    }

    public boolean hasGrade2() {
        return hasGrade2;
    }

    public void setHasGrade2(String grade2) {
        this.hasGrade2 = grade2.equals("1");
    }

    public boolean hasGrade3() {
        return hasGrade3;
    }

    public void setHasGrade3(String grade3) {
        this.hasGrade3 = grade3.equals("1");
    }

    public boolean hasGrade4() {
        return hasGrade4;
    }

    public void setHasGrade4(String grade4) {
        this.hasGrade4 = grade4.equals("1");
    }

    public boolean hasGrade5() {
        return hasGrade5;
    }

    public void setHasGrade5(String grade5) {
        this.hasGrade5 = grade5.equals("1");
    }

    public boolean hasGrade6() {
        return hasGrade6;
    }

    public void setHasGrade6(String grade6) {
        this.hasGrade6 = grade6.equals("1");
    }

    public boolean hasGrade7() {
        return hasGrade7;
    }

    public void setHasGrade7(String grade7) {
        this.hasGrade7 = grade7.equals("1");
    }

    public boolean hasGrade8() {
        return hasGrade8;
    }

    public void setHasGrade8(String grade8) {
        this.hasGrade8 = grade8.equals("1");
    }

    public boolean hasGrade9() {
        return hasGrade9;
    }

    public void setHasGrade9(String grade9) {
        this.hasGrade9 = grade9.equals("1");
    }

    public boolean hasGrade10() {
        return hasGrade10;
    }

    public void setHasGrade10(String grade10) {
        this.hasGrade10 = grade10.equals("1");
    }

    public boolean hasGrade11() {
        return hasGrade11;
    }

    public void setHasGrade11(String grade11) {
        this.hasGrade11 = grade11.equals("1");
    }

    public boolean hasGrade12() {
        return hasGrade12;
    }

    public void setHasGrade12(String grade12) {
        this.hasGrade12 = grade12.equals("1");
    }

    public String getStudentOpenDate() {
        return studentOpenDate;
    }

    public void setStudentOpenDate(String studentOpenDate) {
        this.studentOpenDate = studentOpenDate;
    }

    public GeoCodedData getGeoCodedData() {
        return geoCodedData;
    }

    public void setGeoCodedData(GeoCodedData geoCodedData) {
        this.geoCodedData = geoCodedData;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GeoCodedData {
        private String latitude;
        private String longitude;

        public GeoCodedData() {
        }

        public GeoCodedData(String latitude, String longitude) {
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