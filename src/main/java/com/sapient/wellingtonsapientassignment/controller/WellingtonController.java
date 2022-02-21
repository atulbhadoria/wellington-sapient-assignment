package com.sapient.wellingtonsapientassignment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sapient.wellingtonsapientassignment.constant.WellingtonConstant;
import com.sapient.wellingtonsapientassignment.request.MarketValueRequest;
import com.sapient.wellingtonsapientassignment.response.SuccessfulResponse;
import com.sapient.wellingtonsapientassignment.service.WellingtonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/v1")
public class WellingtonController {
    @Autowired
    private WellingtonService service;

    @PostMapping("/marketValueForInvestor")
    public ResponseEntity<?> getMarketValueForInvestor(@Valid @RequestBody MarketValueRequest marketValueRequest) {
       Integer marketValue = service.getMarketValueForInvestor(marketValueRequest);
       return ResponseEntity.ok(new SuccessfulResponse(marketValue, LocalDateTime.now()));
    }

    @PostMapping("/marketValueForFund")
    public ResponseEntity<?> getMarketValueForFund(@Valid @RequestBody MarketValueRequest marketValueRequest) {
        Integer marketValue = service.getMarketValueForFund(marketValueRequest);
        return ResponseEntity.ok(new SuccessfulResponse(marketValue, LocalDateTime.now()));
    }

    @PostMapping("/insertData")
    public ResponseEntity<?> insertDataInGraph(@RequestBody String request) throws JsonProcessingException {
        service.insertInGraph(request);
        return ResponseEntity.ok(WellingtonConstant.DATA_SAVED);
    }

    @GetMapping("/getGraphDetails")
    public ResponseEntity<?> getGraphDetails() {
        return ResponseEntity.ok(service.getGraphDetails());
    }
}
