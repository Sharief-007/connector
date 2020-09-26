package com;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
@SuppressWarnings("serial")
@XmlRootElement
public class Data implements Serializable {
    private Address adrs;
    private Social social;
    private Edu edu;

    private boolean videoAsCover = false;

    public Data() {}
    public Data(Address adrs, Social social, Edu edu) {
        this.adrs = adrs;
        this.social = social;
        this.edu = edu;
    }

    @XmlElement
    public Address getAdrs() {
        return adrs;
    }

    public void setAdrs(Address adrs) {
        this.adrs = adrs;
    }
    @XmlElement
    public Social getSocial() {
        return social;
    }

    public void setSocial(Social social) {
        this.social = social;
    }

    @XmlElement
    public Edu getEdu() {
        return edu;
    }

    public void setEdu(Edu edu) {
        this.edu = edu;
    }

    public boolean isVideoAsCover() {
        return videoAsCover;
    }

    public void setVideoAsCover(boolean videoAsCover) {
        this.videoAsCover = videoAsCover;
    }
}
