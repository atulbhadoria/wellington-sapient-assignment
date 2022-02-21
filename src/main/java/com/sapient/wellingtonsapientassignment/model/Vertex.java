package com.sapient.wellingtonsapientassignment.model;

public interface Vertex {
    public void setId(Integer id);
    public Integer getId();

    public void setName(String name);
    public String getName();

    public default void setAmount(Integer name) {

    }
    public default Integer getAmount() {
        return null;
    }
}
