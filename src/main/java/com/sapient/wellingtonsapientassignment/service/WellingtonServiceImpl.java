package com.sapient.wellingtonsapientassignment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sapient.wellingtonsapientassignment.constant.WellingtonConstant;
import com.sapient.wellingtonsapientassignment.exceptionHandler.FundNotFoundException;
import com.sapient.wellingtonsapientassignment.exceptionHandler.InvestorNotFoundException;
import com.sapient.wellingtonsapientassignment.model.Fund;
import com.sapient.wellingtonsapientassignment.model.Investor;
import com.sapient.wellingtonsapientassignment.model.Vertex;
import com.sapient.wellingtonsapientassignment.request.MarketValueRequest;
import com.sapient.wellingtonsapientassignment.util.WellingtonUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class WellingtonServiceImpl implements WellingtonService{
    @Autowired
    private Graph graph;

    @Override
    public Integer getMarketValueForInvestor(MarketValueRequest marketValueRequest) {
       Map<Vertex, List<Vertex>> vertexLinkedListMap = graph.getVertexListMap();
       Optional<Map.Entry<Vertex, List<Vertex>>> investor = WellingtonUtil.getInvestorOrFund(vertexLinkedListMap, marketValueRequest.getId(), Investor.class);
       if (investor.isPresent()){
           List<Vertex> funds = investor.get().getValue();
           Map<Vertex, List<Vertex>> fundsWithHoldings = WellingtonUtil.getFundsWithHoldings(funds, vertexLinkedListMap);

          return fundsWithHoldings
                   .entrySet()
                   .parallelStream()
                   .mapToInt(es -> WellingtonUtil.calMarketValueOfFund(es, marketValueRequest.getExcludedHoldingIds()))
                   .sum();

       }
       else {
           throw new InvestorNotFoundException(WellingtonConstant.INVESTOR_NOT_FOUND);
       }
    }

    @Override
    public Integer getMarketValueForFund(MarketValueRequest marketValueRequest) {
        Map<Vertex, List<Vertex>> vertexLinkedListMap = graph.getVertexListMap();
        Optional<Map.Entry<Vertex, List<Vertex>>> fund = WellingtonUtil.getInvestorOrFund(vertexLinkedListMap, marketValueRequest.getId(), Fund.class);

        if (fund.isPresent()){
            return WellingtonUtil.calMarketValueOfFund(fund.get(), marketValueRequest.getExcludedHoldingIds());
        }
        else {
            throw new FundNotFoundException(WellingtonConstant.FUND_NOT_FOUND);
        }
    }

    @Override
    public void insertInGraph(String requestBody) throws JsonProcessingException {
        JSONObject requestBodyJSON = new JSONObject(requestBody);
        Vertex parent = WellingtonUtil.getParentFromJSON(requestBodyJSON);
        Vertex child = WellingtonUtil.getChildFromJSON(requestBodyJSON);
        graph.addEdge(parent,child,false);
        System.out.println(graph.toString());
    }

    public String getGraphDetails() {
        return graph.toString();
    }
}
