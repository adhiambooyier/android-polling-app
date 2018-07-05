package com.cognition.app.kingstonuniversityvotingsystem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Choice {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("poll_id")
    @Expose
    private Integer pollId;
    @SerializedName("choices")
    @Expose
    private String choices;
    @SerializedName("votes")
    @Expose
    private Integer votes;
    @SerializedName("percentage")
    @Expose
    private Integer percentage;
    /**
     * No args constructor for use in serialization
     *
     */
    public Choice() {
    }

    /**
     * @param id
     * @param pollId
     * @param choices
     * @param votes
     * @param percentage
     */
    public Choice(Integer id, Integer pollId, String choices, Integer votes, Integer percentage) {
        super();
        this.id = id;
        this.pollId = pollId;
        this.choices = choices;
        this.votes = votes;
        this.percentage = percentage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPollId() {
        return pollId;
    }

    public void setPollId(Integer pollId) {
        this.pollId = pollId;
    }

    public String getChoices() {
        return choices;
    }

    public void setChoices(String choices) {
        this.choices = choices;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }
}