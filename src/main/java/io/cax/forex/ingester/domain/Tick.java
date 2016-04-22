package io.cax.forex.ingester.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by cq on 15/4/16.
 */
@JsonRootName(value = "tick")
@Entity
public class Tick {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    //{"tick":{"instrument":"EUR_USD","time":"2016-04-08T20:59:58.540499Z","bid":1.13929,"ask":1.14029}}
    private String instrument;

    @JsonProperty("time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.S", locale = "ENGLISH")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime dateTime;

    private double bid;

    private double ask;

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public double getBid() {
        return bid;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    public double getAsk() {
        return ask;
    }

    public void setAsk(double ask) {
        this.ask = ask;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Tick{");
        sb.append("instrument='").append(instrument).append('\'');
        sb.append(", dateTime=").append(dateTime);
        sb.append(", bid=").append(bid);
        sb.append(", ask=").append(ask);
        sb.append('}');
        return sb.toString();
    }
}