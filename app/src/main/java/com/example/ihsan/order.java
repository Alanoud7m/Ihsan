package com.example.ihsan;

public class order {
    String benef_addres ,benef_name ,benef_phone ,
            charity_name ,order_date,order_status,numOfBases,
            item_desc ,item_id ,item_size,item_image,
             volenteer_name;

int order_id;
    public order() {
    }


    public order(String benef_addres, String benef_name, String benef_phone, String charity_name, String order_date, String order_status, String numOfBases, String item_desc, String item_id, String item_size, String item_image, String volenteer_name , int order_id) {
        this.benef_addres = benef_addres;
        this.benef_name = benef_name;
        this.benef_phone = benef_phone;
        this.charity_name = charity_name;
        this.order_date = order_date;
        this.order_status = order_status;
        this.numOfBases = numOfBases;
        this.item_desc = item_desc;
        this.item_id = item_id;
        this.item_size = item_size;
        this.item_image = item_image;
        this.volenteer_name = volenteer_name;
        this.order_id = order_id;

    }

    public String getBenef_addres() {
        return benef_addres;
    }

    public void setBenef_addres(String benef_addres) {
        this.benef_addres = benef_addres;
    }

    public String getBenef_name() {
        return benef_name;
    }

    public void setBenef_name(String benef_name) {
        this.benef_name = benef_name;
    }

    public String getBenef_phone() {
        return benef_phone;
    }

    public void setBenef_phone(String benef_phone) {
        this.benef_phone = benef_phone;
    }

    public String getCharity_name() {
        return charity_name;
    }

    public void setCharity_name(String charity_name) {
        this.charity_name = charity_name;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getNumOfBases() {
        return numOfBases;
    }

    public void setNumOfBases(String numOfBases) {
        this.numOfBases = numOfBases;
    }

    public String getItem_desc() {
        return item_desc;
    }

    public void setItem_desc(String item_desc) {
        this.item_desc = item_desc;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_size() {
        return item_size;
    }

    public void setItem_size(String item_size) {
        this.item_size = item_size;
    }

    public String getItem_image() {
        return item_image;
    }

    public void setItem_image(String item_image) {
        this.item_image = item_image;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getVolenteer_name() {
        return volenteer_name;
    }

    public void setVolenteer_name(String volenteer_name) {
        this.volenteer_name = volenteer_name;
    }
}
