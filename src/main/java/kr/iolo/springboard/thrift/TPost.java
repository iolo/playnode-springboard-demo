package kr.iolo.springboard.thrift;

import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftStruct;

@ThriftStruct("Post")
public final class TPost {

    private int id;
    private int userId;
    private int forumId;
    private String title;
    private String content;
    private String createdAt;

    public TPost() {
    }

    @ThriftField(1)
    public int getId() {
        return id;
    }

    @ThriftField(2)
    public int getUserId() {
        return userId;
    }

    @ThriftField(3)
    public int getForumId() {
        return forumId;
    }

    @ThriftField(4)
    public String getTitle() {
        return title;
    }

    @ThriftField(5)
    public String getContent() {
        return content;
    }

    @ThriftField(6)
    public String getCreatedAt() {
        return createdAt;
    }

    @ThriftField
    public void setId(int id) {
        this.id = id;
    }

    @ThriftField
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @ThriftField
    public void setForumId(int forumId) {
        this.forumId = forumId;
    }

    @ThriftField
    public void setTitle(String title) {
        this.title = title;
    }

    @ThriftField
    public void setContent(String content) {
        this.content = content;
    }

    @ThriftField
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
