package com.smilearts.smilenotes.model;

public class FCMMOdel {
    String mob_model , fcm_id;

    public FCMMOdel() {
    }

    public FCMMOdel(String mob_model, String fcm_id) {
        this.mob_model = mob_model;
        this.fcm_id = fcm_id;
    }

    public String getMob_model() {
        return mob_model;
    }

    public void setMob_model(String mob_model) {
        this.mob_model = mob_model;
    }

    public String getFcm_id() {
        return fcm_id;
    }

    public void setFcm_id(String fcm_id) {
        this.fcm_id = fcm_id;
    }
}
