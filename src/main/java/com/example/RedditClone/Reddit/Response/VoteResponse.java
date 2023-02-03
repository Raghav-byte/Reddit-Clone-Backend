package com.example.RedditClone.Reddit.Response;

import com.example.RedditClone.Reddit.Model.Vote;

import java.util.ArrayList;
import java.util.List;

public class VoteResponse {

    private long totalVotes;
    private List<Vote> voteList;

    public VoteResponse(){
        this.voteList = new ArrayList<>();
    }

    public long getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(long totalVotes) {
        this.totalVotes = totalVotes;
    }

    public List<Vote> getVoteList() {
        return voteList;
    }

    public void setVoteList(List<Vote> voteList) {
        this.voteList = voteList;
    }
}
