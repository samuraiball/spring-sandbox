package dev.hirooka.r2dbcsample;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

public class Customer implements Persistable {

    public Customer() {
    }

    public Customer(String id, String userName, boolean isNew) {
        this.id = id;
        this.userName = userName;
        this.isNew = isNew;
    }

    @Id
    private String id;

    private String userName;

    @Transient
    private boolean isNew;

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}
