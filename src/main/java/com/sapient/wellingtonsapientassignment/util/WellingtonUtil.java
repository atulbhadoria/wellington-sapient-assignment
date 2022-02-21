package com.sapient.wellingtonsapientassignment.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import com.sapient.wellingtonsapientassignment.constant.WellingtonConstant;
import com.sapient.wellingtonsapientassignment.exceptionHandler.ValidationFailedException;
import com.sapient.wellingtonsapientassignment.model.Fund;
import com.sapient.wellingtonsapientassignment.model.Holding;
import com.sapient.wellingtonsapientassignment.model.Investor;
import com.sapient.wellingtonsapientassignment.model.Vertex;
import org.json.JSONObject;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;
import java.util.stream.Collectors;

public class WellingtonUtil {

    public static Vertex getParentFromJSON(JSONObject requestBody) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        switch (requestBody.get(WellingtonConstant.LEVEL_STRING).toString()){
            case "1":
                return validateVertex(mapper.readValue(requestBody.getJSONObject(WellingtonConstant.PARENT_STRING).toString(), Investor.class));
            case "2":
                return validateVertex(mapper.readValue(requestBody.getJSONObject(WellingtonConstant.PARENT_STRING).toString(), Fund.class));
            default:
                throw new ValidationFailedException(WellingtonConstant.INVALID_LEVEL_MSG);
        }
    }

    public static Vertex getChildFromJSON(JSONObject requestBody) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        switch (requestBody.get(WellingtonConstant.LEVEL_STRING).toString()){
            case "1":
                return validateVertex(mapper.readValue(requestBody.getJSONObject(WellingtonConstant.CHILD_STRING).toString(), Fund.class));
            case "2":
                return validateVertex(mapper.readValue(requestBody.getJSONObject(WellingtonConstant.CHILD_STRING).toString(), Holding.class));
            default:
                throw new ValidationFailedException(WellingtonConstant.INVALID_LEVEL_MSG);
        }
    }

    public static Vertex validateVertex(Vertex vertex) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Vertex>> violations = validator.validate(vertex);
        if (violations.size() > 0){
            throw new ValidationFailedException(
                    violations.stream()
                            .map(m -> m.getMessage())
                            .reduce((x, y) -> x+", "+y)
                            .orElse(""));
        }
        else
            return vertex;
    }

    public static Map<Vertex, List<Vertex>> getFundsWithHoldings(List<Vertex> funds, Map<Vertex, List<Vertex>> vertexLinkedListMap) {
        return vertexLinkedListMap.entrySet()
                .stream()
                .filter(vertexListEntry -> funds.contains(vertexListEntry.getKey()))
                .collect(Collectors.toMap(Map.Entry<Vertex, List<Vertex>>::getKey,Map.Entry<Vertex, List<Vertex>>::getValue));
    }

    public static Integer calMarketValueOfFund(Map.Entry<Vertex, List<Vertex>> es, List<Integer> excludedHoldingIds) {
        System.out.println(es.getValue());
        List<Integer> excludedHoldings = Optional.ofNullable(excludedHoldingIds).orElse(new LinkedList<>());
        return es.getValue().stream().filter(h -> !excludedHoldings.contains(h.getId())).mapToInt(h -> h.getAmount()*100).sum();
    }

    public static Optional<Map.Entry<Vertex, List<Vertex>>> getInvestorOrFund(Map<Vertex, List<Vertex>> vertexLinkedListMap, Integer id, Class vertexClass){
        return vertexLinkedListMap.entrySet()
                .parallelStream()
                .filter(e -> (e.getKey().getClass().equals(vertexClass) && e.getKey().getId().equals(id)))
                .findFirst();
    }
}
