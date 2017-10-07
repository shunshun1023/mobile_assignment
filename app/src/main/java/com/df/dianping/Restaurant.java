package com.df.dianping;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Lenovo on 2017/10/6.
 */

public class Restaurant implements Serializable {

    private String name;
    private String price;
    private int busyness;

    private int stars;
    private double taste_score;
    private double environment_score;
    private double service_score;
    private String address;
    private String phone;
    private String recommended;
    private String self_description;

    public Restaurant(Map<String,Object>map){
        this.stars = ((Integer) map.get("star")).intValue();
        this.name = map.get("name").toString();
        this.address = map.get("addr").toString();
        this.price = map.get("price").toString();
        this.busyness = ((Integer) map.get("busy")).intValue();
        this.taste_score = ((Double) map.get("taste_score")).doubleValue();
        this.environment_score = ((Double) map.get("environment_score")).doubleValue();
        this.service_score = ((Double) map.get("service_score")).doubleValue();
        this.phone = map.get("phone").toString();
        this.recommended = map.get("recommended").toString();
        this.self_description = map.get("self-description").toString();
    }
    public int getStars() {
        return stars;
    }
    public void setStars(int stars) {
        this.stars = stars;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPrice() {
        return price;
    }
    public String getName(){
        return this.name.toString();
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public int getBusyness() {
        return busyness;
    }

    public void setBusyness(int busyness) {
        this.busyness = busyness;
    }



    public double getTaste_score() {
        return taste_score;
    }

    public void setTaste_score(double taste_score) {
        this.taste_score = taste_score;
    }

    public double getEnvironment_score() {
        return environment_score;
    }

    public void setEnvironment_score(double environment_score) {
        this.environment_score = environment_score;
    }

    public double getService_score() {
        return service_score;
    }

    public void setService_score(double service_score) {
        this.service_score = service_score;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRecommended() {
        return recommended;
    }

    public void setRecommended(String recommended) {
        this.recommended = recommended;
    }

    public String getSelf_description() {
        return self_description;
    }

    public void setSelf_description(String self_description) {
        this.self_description = self_description;
    }


}
