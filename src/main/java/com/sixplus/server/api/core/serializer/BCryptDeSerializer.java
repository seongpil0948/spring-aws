package com.sixplus.server.api.core.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.sixplus.server.api.utils.DataUtils;

import java.io.IOException;

public class BCryptDeSerializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return DataUtils.getPasswordEncode(parser.getText());
    }
}