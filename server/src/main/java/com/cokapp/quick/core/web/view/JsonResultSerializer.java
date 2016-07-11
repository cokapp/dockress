/**
 *
 */
package com.cokapp.quick.core.web.view;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import com.cokapp.cokits.util.mapper.JsonMapper;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 *
 *
 * @author dev@cokapp.com
 * @date 2015年10月14日 下午5:59:55
 */
public class JsonResultSerializer extends JsonSerializer<JsonResult<?>> {
	private static final JsonMapper mapper = JsonMapper.getInstance();

	@Override
	public void serialize(JsonResult<?> value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		String jsonWithoutExtraData = value.getJsonObject().toJson();
		ObjectNode node = (ObjectNode) JsonResultSerializer.mapper.readTree(jsonWithoutExtraData);
		if (value.getExtraData() != null) {
			Iterator<Entry<String, Object>> it = value.getExtraData().entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, Object> entry = it.next();
				String k = entry.getKey();
				String v = JsonResultSerializer.mapper.toJson(entry.getValue());

				node.set(k, JsonResultSerializer.mapper.readTree(v));
			}
		}
		jgen.writeRaw(node.toString());

		// jgen.writeRaw(value.getJsonObject().toJson());
	}

}
