package com;

import java.io.Serializable;
@SuppressWarnings("serial")
public class Social implements Serializable {
    public Social (){}

    public Social(String facebook, String twitter, String linkedin, String youtube, String instagram, String quora, String overflow) {
        this.facebook = facebook;
        this.twitter = twitter;
        this.linkedin = linkedin;
        this.youtube = youtube;
        this.instagram = instagram;
        this.quora = quora;
        this.overflow = overflow;
    }

    public Social(String facebook, String twitter, String linkedin, String youtube) {
        this.facebook = facebook;
        this.twitter = twitter;
        this.linkedin = linkedin;
        this.youtube = youtube;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getQuora() {
        return quora;
    }

    public void setQuora(String quora) {
        this.quora = quora;
    }

    public String getOverflow() {
        return overflow;
    }

    public void setOverflow(String overflow) {
        this.overflow = overflow;
    }

    private String facebook;
    private String twitter;
    private String linkedin;
    private String youtube;
    private String instagram;
    private String quora;
    private String overflow;

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }
}
