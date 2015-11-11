package kr.iolo.springboard.thrift;

import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftStruct;

@ThriftStruct("Forum")
public final class TForum {

    private int id;
    private String title;

    public TForum() {
    }

    @ThriftField(1)
    public int getId() {
        return id;
    }

    @ThriftField(2)
    public String getTitle() {
        return title;
    }

    @ThriftField
    public void setId(int id) {
        this.id = id;
    }

    @ThriftField
    public void setTitle(String title) {
        this.title = title;
    }

}
