package kr.iolo.springboard.thrift;

import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftStruct;

@ThriftStruct("User")
public final class TUser {
    private int id;
    private String username;
    private String password;

    public TUser() {
    }

    @ThriftField(1)
    public int getId() {
        return id;
    }

    @ThriftField(2)
    public String getUsername() {
        return username;
    }

    @ThriftField(3)
    public String getPassword() {
        return password;
    }

    @ThriftField
    public void setId(int id) {
        this.id = id;
    }

    @ThriftField
    public void setUsername(String username) {
        this.username = username;
    }

    @ThriftField
    public void setPassword(String password) {
        this.password = password;
    }
}
