package com.cognition.app.kingstonuniversityvotingsystem;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Adhiambo Oyier on 4/10/2018.
 */

public class Post {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("comments")
    @Expose
    private List<Comment> comments = null;

    /**
     * No args constructor for use in serialization
     */
    public Post() {
    }

    /**
     * @param question
     * @param id
     * @param created
     * @param userId
     */
    public Post(Integer id, String userId, String question, String created, List<Comment> comments) {
        super();
        this.id = id;
        this.userId = userId;
        this.question = question;
        this.created = created;
        this.comments = comments;
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String topic) {
        this.question = question;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
