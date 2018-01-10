package com.libo.libokdemos.MVP.Model;

/**
 * Created by libok on 2018-01-10.
 */

public class Product {

    /**
     * company : 买买买
     * pro_log : http://rongyisz.com/static/admin/uploade/201711/22/2a3a6e187ead177694da2eb21338d184.png
     * id : 24
     * name : 花花白卡
     * loan : 1000-5000
     * rate : 0.12%（日/月）
     * give : 199
     * reason : 五分钟到账，通过率高，放款快
     */

    private String company;
    private String pro_log;
    private int id;
    private String name;
    private String loan;
    private String rate;
    private int give;
    private String reason;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPro_log() {
        return pro_log;
    }

    public void setPro_log(String pro_log) {
        this.pro_log = pro_log;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoan() {
        return loan;
    }

    public void setLoan(String loan) {
        this.loan = loan;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public int getGive() {
        return give;
    }

    public void setGive(int give) {
        this.give = give;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
