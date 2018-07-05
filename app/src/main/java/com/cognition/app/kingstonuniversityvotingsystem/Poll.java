package com.cognition.app.kingstonuniversityvotingsystem;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Poll {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("topic")
    @Expose
    private String topic;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("expiry")
    @Expose
    private String expiry;
    @SerializedName("approved")
    @Expose
    private Integer approved;
    @SerializedName("choices")
    @Expose
    private List<Choice> choices = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public Poll() {
    }

    /**
     *
     * @param topic
     * @param id
     * @param approved
     * @param created
     * @param choices
     * @param userId
     * @param expiry
     */
    public Poll(Integer id, String userId, String topic, String created, String expiry, Integer approved, List<Choice> choices) {
        super();
        this.id = id;
        this.userId = userId;
        this.topic = topic;
        this.created = created;
        this.expiry = expiry;
        this.approved = approved;
        this.choices = choices;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public Integer getApproved() {
        return approved;
    }

    public void setApproved(Integer approved) {
        this.approved = approved;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

}