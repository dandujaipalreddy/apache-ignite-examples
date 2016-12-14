package com.jaipal.model;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.io.Serializable;
import java.net.Inet4Address;

/**
 * Created by dreddy on 12/13/2016.
 */
public class Player implements Serializable {

    @QuerySqlField(index = true)
    private String username;
    @QuerySqlField(index = true)
    private String sessionId;
    private Inet4Address ipAddress;
    @QuerySqlField(index = true)
    private String country;

    public Player(String username, String sessionId, String country) {
        this.username = username;
        this.sessionId = sessionId;
        this.country = country;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Inet4Address getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(Inet4Address ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
