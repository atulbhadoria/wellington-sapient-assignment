package com.sapient.wellingtonsapientassignment.model;

public class VertexFactory {
    public Vertex createVertex(String type) {
        if (type == null || type.isEmpty())
            return null;
        if ("Fund".equals(type)) {
            return new Fund();
        }
        else if ("Investor".equals(type)) {
            return new Investor();
        }
        else if ("Holding".equals(type)) {
            return new Holding();
        }
        return null;
    }
}
