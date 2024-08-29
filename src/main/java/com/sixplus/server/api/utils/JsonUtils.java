package com.sixplus.server.api.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;


public class JsonUtils {
    public static final ObjectMapper objectMapper = new ObjectMapper();

    public static JsonNode search(Configuration jacksonConfig, String source, String searchKey) {
        return JsonPath.using(jacksonConfig).parse(source.toString()).read(searchKey, JsonNode.class);
    }

    public static String searchKey(String strJson, String key) {
        try {
            Configuration jacksonConfig = Configuration.builder().mappingProvider(new JacksonMappingProvider())
                    .jsonProvider(new JacksonJsonProvider()).build();
            JsonNode node = search(jacksonConfig, strJson, key);
            if (!node.isMissingNode()) {
                if (node.size() > 0) {
                    node = node.get(0);
                }
                if (node.isTextual()) {
                    return node.asText();
                } else {
                    return node.toString();
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

}
