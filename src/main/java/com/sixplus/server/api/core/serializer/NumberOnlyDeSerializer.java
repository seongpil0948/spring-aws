package com.sixplus.server.api.core.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.sixplus.server.api.utils.CmmUtils;

import java.io.IOException;

public class NumberOnlyDeSerializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return CmmUtils.toNumberOnly(parser.getText());
    }
}
