package com.digw.it.entity;

import java.io.Serializable;

/**
 * digw创建于17-6-4.
 */

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private int followingCount;
    private String jobs;
    private int honorScore;
    private int followPaperTotalCount;
    private int type;
    private int codingRightCount;
    private int online;
    private int totalAnswerCount;
    private String token;
    private int doneRightCount;
    private int userId;
    private int totalDiscussPost;
    private int referenceCount;
    private int followPostTotalCount;
    private String nickname;
    private int status;
    private String rawNickname;
    private int likedCount;
    private String workInfo;
    private String headImg;
    private int clockCount;
    private String educationInfo;
    private boolean isFollowedByHost;
    private int totalTestsCount;
    private boolean active;
    private int honorLevel;
    private int followProblemTotalCount;
    private int followedCount;

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    public String getJobs() {
        return jobs;
    }

    public void setJobs(String jobs) {
        this.jobs = jobs;
    }

    public int getHonorScore() {
        return honorScore;
    }

    public void setHonorScore(int honorScore) {
        this.honorScore = honorScore;
    }

    public int getFollowPaperTotalCount() {
        return followPaperTotalCount;
    }

    public void setFollowPaperTotalCount(int followPaperTotalCount) {
        this.followPaperTotalCount = followPaperTotalCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCodingRightCount() {
        return codingRightCount;
    }

    public void setCodingRightCount(int codingRightCount) {
        this.codingRightCount = codingRightCount;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public int getTotalAnswerCount() {
        return totalAnswerCount;
    }

    public void setTotalAnswerCount(int totalAnswerCount) {
        this.totalAnswerCount = totalAnswerCount;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getDoneRightCount() {
        return doneRightCount;
    }

    public void setDoneRightCount(int doneRightCount) {
        this.doneRightCount = doneRightCount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTotalDiscussPost() {
        return totalDiscussPost;
    }

    public void setTotalDiscussPost(int totalDiscussPost) {
        this.totalDiscussPost = totalDiscussPost;
    }

    public int getReferenceCount() {
        return referenceCount;
    }

    public void setReferenceCount(int referenceCount) {
        this.referenceCount = referenceCount;
    }

    public int getFollowPostTotalCount() {
        return followPostTotalCount;
    }

    public void setFollowPostTotalCount(int followPostTotalCount) {
        this.followPostTotalCount = followPostTotalCount;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRawNickname() {
        return rawNickname;
    }

    public void setRawNickname(String rawNickname) {
        this.rawNickname = rawNickname;
    }

    public int getLikedCount() {
        return likedCount;
    }

    public void setLikedCount(int likedCount) {
        this.likedCount = likedCount;
    }

    public String getWorkInfo() {
        return workInfo;
    }

    public void setWorkInfo(String workInfo) {
        this.workInfo = workInfo;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public int getClockCount() {
        return clockCount;
    }

    public void setClockCount(int clockCount) {
        this.clockCount = clockCount;
    }

    public String getEducationInfo() {
        return educationInfo;
    }

    public void setEducationInfo(String educationInfo) {
        this.educationInfo = educationInfo;
    }

    public boolean isFollowedByHost() {
        return isFollowedByHost;
    }

    public void setFollowedByHost(boolean followedByHost) {
        isFollowedByHost = followedByHost;
    }

    public int getTotalTestsCount() {
        return totalTestsCount;
    }

    public void setTotalTestsCount(int totalTestsCount) {
        this.totalTestsCount = totalTestsCount;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getHonorLevel() {
        return honorLevel;
    }

    public void setHonorLevel(int honorLevel) {
        this.honorLevel = honorLevel;
    }

    public int getFollowProblemTotalCount() {
        return followProblemTotalCount;
    }

    public void setFollowProblemTotalCount(int followProblemTotalCount) {
        this.followProblemTotalCount = followProblemTotalCount;
    }

    public int getFollowedCount() {
        return followedCount;
    }

    public void setFollowedCount(int followedCount) {
        this.followedCount = followedCount;
    }
}
