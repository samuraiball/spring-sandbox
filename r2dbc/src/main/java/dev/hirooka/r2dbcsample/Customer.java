package dev.hirooka.r2dbcsample;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;

public class Customer implements Persistable {

    public Customer() {
    }

    public Customer(String id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    @Id
    private String id;

    private String userName;


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
        return true;
    }
}
