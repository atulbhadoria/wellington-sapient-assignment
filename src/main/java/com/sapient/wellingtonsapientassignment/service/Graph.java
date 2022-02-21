package com.sapient.wellingtonsapientassignment.service;

import com.sapient.wellingtonsapientassignment.constant.WellingtonConstant;
import com.sapient.wellingtonsapientassignment.exceptionHandler.UniqueIdFailedException;
import com.sapient.wellingtonsapientassignment.model.Vertex;
import com.sapient.wellingtonsapientassignment.model.VertexFactory;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
@Getter
public class Graph {
    // We use Hashmap to store the edges in the Graph
    private Map<Vertex, List<Vertex>> vertexListMap = new HashMap<>();

    @PostConstruct
    public void postConstruct() {
        VertexFactory factory = new VertexFactory();

        // Initialize Investors
        Vertex i1 = factory.createVertex(WellingtonConstant.INVESTOR_STRING);
        Vertex i2 = factory.createVertex(WellingtonConstant.INVESTOR_STRING);
        i1.setId(WellingtonConstant.ONE);
        i1.setName(WellingtonConstant.INVESTOR_STRING+" "+WellingtonConstant.ONE);

        i2.setId(WellingtonConstant.TWO);
        i2.setName(WellingtonConstant.INVESTOR_STRING+" "+WellingtonConstant.TWO);

        // Initialize Funds
        Vertex f1 = factory.createVertex(WellingtonConstant.FUND_STRING);
        Vertex f2 = factory.createVertex(WellingtonConstant.FUND_STRING);
        Vertex f3 = factory.createVertex(WellingtonConstant.FUND_STRING);

        f1.setId(WellingtonConstant.ONE);
        f1.setName(WellingtonConstant.FUND_STRING+" "+WellingtonConstant.ONE);

        f2.setId(WellingtonConstant.TWO);
        f2.setName(WellingtonConstant.FUND_STRING+" "+WellingtonConstant.TWO);

        f3.setId(WellingtonConstant.THREE);
        f3.setName(WellingtonConstant.FUND_STRING+" "+WellingtonConstant.THREE);

        // Initialize Holdings
        Vertex h1 = factory.createVertex(WellingtonConstant.HOLDING_STRING);
        Vertex h2 = factory.createVertex(WellingtonConstant.HOLDING_STRING);
        Vertex h3 = factory.createVertex(WellingtonConstant.HOLDING_STRING);
        Vertex h4 = factory.createVertex(WellingtonConstant.HOLDING_STRING);

        h1.setId(WellingtonConstant.ONE);
        h1.setName(WellingtonConstant.HOLDING_STRING+" "+WellingtonConstant.ONE);
        h1.setAmount(WellingtonConstant.TEN);

        h2.setId(WellingtonConstant.TWO);
        h2.setName(WellingtonConstant.HOLDING_STRING+" "+WellingtonConstant.TWO);
        h2.setAmount(WellingtonConstant.TWENTY);

        h3.setId(WellingtonConstant.THREE);
        h3.setName(WellingtonConstant.HOLDING_STRING+" "+WellingtonConstant.THREE);
        h3.setAmount(WellingtonConstant.FIFTEEN);

        h4.setId(WellingtonConstant.FOUR);
        h4.setName(WellingtonConstant.HOLDING_STRING+" "+WellingtonConstant.FOUR);
        h4.setAmount(WellingtonConstant.TEN);

        this.addEdge(i1,f1,false);
        this.addEdge(i1,f2,false);

        this.addEdge(i2,f2,false);
        this.addEdge(i2,f3,false);

        this.addEdge(f1,h1,false);
        this.addEdge(f1,h2,false);
        this.addEdge(f1,h4,false);

        this.addEdge(f2,h1,false);
        this.addEdge(f2,h3,false);

        this.addEdge(f3,h1,false);
        this.addEdge(f3,h4,false);
        System.out.println(this.toString());
    }

    // This function adds a new vertex to the this
    public void addVertex(Vertex s)
    {
        if (checkIfIdExist(s))
            throw new UniqueIdFailedException(s);
        else
            vertexListMap.put(s, new LinkedList<Vertex>());
    }

    // This function adds the edge
    // between source to destination
    @Transactional
    public void addEdge(Vertex source,
                        Vertex destination,
                        boolean bidirectional)
    {

        if (!hasVertex(source))
            addVertex(source);

        if (!hasVertex(destination))
            addVertex(destination);

        if (!hasEdge(source, destination))
            vertexListMap.get(source).add(destination);

        if (bidirectional == true) {
            vertexListMap.get(destination).add(source);
        }
    }

    // This function gives whether
    // a vertex is present or not.
    public boolean hasVertex(Vertex s)
    {
        return vertexListMap.containsKey(s);
    }

    private boolean checkIfIdExist(Vertex s)
    {
        return vertexListMap.keySet().parallelStream().anyMatch(k -> (k.getClass().equals(s.getClass()) && k.getId().equals(s.getId())));
    }

    // This function gives whether an edge is present or not.
    public boolean hasEdge(Vertex s, Vertex d)
    {
        return vertexListMap.get(s).contains(d);
    }

    // Prints the adjancency list of each vertex.
    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        for (Vertex v : vertexListMap.keySet()) {
            builder.append(v.toString() + ": ");
            for (Vertex w : vertexListMap.get(v)) {
                builder.append(w.toString() + " ");
            }
            builder.append("\n");
        }

        return (builder.toString());
    }
}
