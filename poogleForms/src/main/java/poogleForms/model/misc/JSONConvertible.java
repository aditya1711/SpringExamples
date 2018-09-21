package poogleForms.model.misc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public interface JSONConvertible {
	public static ObjectWriter ow = new ObjectMapper().writer();
	public String toJSONString() throws JsonProcessingException;
}
