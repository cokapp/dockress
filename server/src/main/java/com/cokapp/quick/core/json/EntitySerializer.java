package com.cokapp.quick.core.json;

import java.io.IOException;
import java.io.Serializable;

import com.cokapp.quick.core.entity.BaseEntity;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class EntitySerializer extends JsonSerializer<BaseEntity<? extends Serializable>> {

	@Override
	public void serialize(BaseEntity<? extends Serializable> value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		if (value != null) {
			jgen.writeStartObject();
			jgen.writeFieldName("id");
			jgen.writeString(value.getId().toString());
			jgen.writeFieldName("display");
			jgen.writeString(value.toString());
			jgen.writeEndObject();
		}
	}
}
