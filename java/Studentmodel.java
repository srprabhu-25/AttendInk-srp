package com.example.attendink;

public class Studentmodel {
    String accounttype, address, age, email, gender, name, online, password, phone, profileimage, section, sid, timestamp, uid;

    public Studentmodel() {
    }

    public Studentmodel(String accounttype, String address, String age, String email, String gender, String name, String online, String password, String phone, String profileimage, String section, String sid, String timestamp, String uid) {
        this.accounttype = accounttype;
        this.address = address;
        this.age = age;
        this.email = email;
        this.gender = gender;
        this.name = name;
        this.online = online;
        this.password = password;
        this.phone = phone;
        this.profileimage = profileimage;
        this.section = section;
        this.sid = sid;
        this.timestamp = timestamp;
        this.uid = uid;
    }

    public String getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
