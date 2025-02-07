package com.saveslave.commons.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class JsonLongSerializer extends JsonSerializer<Long> {


    @Override
    public void serialize(Long aLong, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if(aLong ==null){
            jsonGenerator.writeNull();
        }else{
            jsonGenerator.writeString(Long.toString(aLong));
        }

    }
}
