package com.sapient.wellingtonsapientassignment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sapient.wellingtonsapientassignment.model.Vertex;
import com.sapient.wellingtonsapientassignment.request.MarketValueRequest;
import org.springframework.stereotype.Service;

@Service
public interface WellingtonService {
    public void insertInGraph(String requestBody) throws JsonProcessingException;
    public Integer getMarketValueForInvestor(MarketValueRequest marketValueRequest);
    public Integer getMarketValueForFund(MarketValueRequest marketValueRequest);
    public String getGraphDetails();
}
