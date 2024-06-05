package ua.comparus.database.entity;

import java.io.Serializable;

public interface Userable<T extends Serializable> {
    T getId();
    void setId(T id);
}
