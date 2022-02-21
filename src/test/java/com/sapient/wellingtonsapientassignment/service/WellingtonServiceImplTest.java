package com.sapient.wellingtonsapientassignment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sapient.wellingtonsapientassignment.exceptionHandler.FundNotFoundException;
import com.sapient.wellingtonsapientassignment.exceptionHandler.InvestorNotFoundException;
import com.sapient.wellingtonsapientassignment.model.Fund;
import com.sapient.wellingtonsapientassignment.model.Holding;
import com.sapient.wellingtonsapientassignment.model.Investor;
import com.sapient.wellingtonsapientassignment.model.Vertex;
import com.sapient.wellingtonsapientassignment.request.MarketValueRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class WellingtonServiceImplTest {

    @Spy
    private Graph graph = new Graph();

    @InjectMocks
    WellingtonServiceImpl service;

    @BeforeEach
    void setUp() {
        graph.postConstruct();
    }

    @Test
    void getMarketValueForInvestor() {
        Integer marketValue = service.getMarketValueForInvestor(new MarketValueRequest(1, null));
        assertEquals(6500,marketValue);
    }

    @Test
    void getMarketValueForInvestorExcludingHoldings() {
        Integer marketValue = service.getMarketValueForInvestor(new MarketValueRequest(1, Arrays.asList(1,3)));
        assertEquals(3000, marketValue);
    }

    @Test
    void getMarketValueForInvestorNotFound() {
        InvestorNotFoundException exception = assertThrows(InvestorNotFoundException.class,() -> service.getMarketValueForInvestor(new MarketValueRequest(10, Arrays.asList(1,3))));
        assertEquals(exception.getMessage(), "Investor not found");
    }

    @Test
    void getMarketValueForFund() {
        Integer marketValue = service.getMarketValueForFund(new MarketValueRequest(1, null));
        assertEquals(4000, marketValue);
    }

    @Test
    void getMarketValueForFundExcludingHoldings() {
        Integer marketValue = service.getMarketValueForFund(new MarketValueRequest(1, Arrays.asList(1,2)));
        assertEquals(1000, marketValue);
    }

    @Test
    void getMarketValueForFundNotFound() {
        FundNotFoundException exception = assertThrows(FundNotFoundException.class,() -> service.getMarketValueForFund(new MarketValueRequest(10, Arrays.asList(1,3))));
        assertEquals(exception.getMessage(), "Fund not found");
    }

    @Test
    void insertInGraphLevel1() throws JsonProcessingException {
        String requestBody = "{\"parent\":{\"id\":4,\"name\":\"I4\"},\"child\":{\"id\":10,\"name\":\"F10\"},\"level\":1}";
        service.insertInGraph(requestBody);
        Vertex parent = new Investor(4, "I4");
        Vertex child = new Fund(10, "F10");
        assertEquals(graph.getVertexListMap().containsKey(parent), true);
        assertEquals(graph.getVertexListMap().containsKey(child), true);
        assertEquals(graph.getVertexListMap().get(parent).contains(child), true);
    }

    @Test
    void insertInGraphLevel2() throws JsonProcessingException {
        String requestBody = "{\"parent\":{\"id\":5,\"name\":\"F5\"},\"child\":{\"id\":9,\"name\":\"H9\",\"amount\":30},\"level\":2}";
        service.insertInGraph(requestBody);
        Vertex parent = new Fund(5, "F5");
        Vertex child = new Holding(9, "H9", 30);
        assertEquals(graph.getVertexListMap().containsKey(parent), true);
        assertEquals(graph.getVertexListMap().containsKey(child), true);
        assertEquals(graph.getVertexListMap().get(parent).contains(child), true);
    }
}