package kr.iolo.springboard.thrift;

import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftStruct;

@ThriftStruct("Comment")
public final class TComment {

    private int id;
    private int userId;
    private int postId;
    private String content;
    private String createdAt;

    public TComment() {
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
    public int getPostId() {
        return postId;
    }

    @ThriftField(4)
    public String getContent() {
        return content;
    }

    @ThriftField(5)
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
    public void setPostId(int postId) {
        this.postId = postId;
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
