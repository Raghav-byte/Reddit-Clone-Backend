package com.example.RedditClone.Reddit.Response;

import com.example.RedditClone.Reddit.Model.Comment;
import com.example.RedditClone.Reddit.Model.Vote;

import java.util.ArrayList;
import java.util.List;

public class CommentResponse {

    private long totalComments;
    private List<Comment> commentList;

    public CommentResponse() {
        this.commentList = new ArrayList<>();
    }

    public long getTotalComments() {
        return totalComments;
    }

    public void setTotalComments(long totalComments) {
        this.totalComments = totalComments;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
