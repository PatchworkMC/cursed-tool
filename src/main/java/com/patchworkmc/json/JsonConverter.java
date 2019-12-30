package com.patchworkmc.json;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {
	public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	public static <T> T streamToObject(InputStream stream, Class<T> targetClass) throws JsonConverterException {
		try {
			return OBJECT_MAPPER.readValue(stream, targetClass);
		} catch (JsonParseException e) {
			throw new JsonConverterException("Got malformed json", e);
		} catch (JsonMappingException e) {
			throw new JsonConverterException("JsonMappingException while converting object", e);
		} catch (IOException e) {
			throw new JsonConverterException("IOException while reading from stream", e);
		}
	}
}
