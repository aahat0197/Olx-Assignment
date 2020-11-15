package com.imdb.api.common;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.json.GsonJsonProvider;
import com.jayway.jsonpath.spi.mapper.GsonMappingProvider;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * Utility of serializing/de-serializing JSON
 * 
 * @author aahat.sachdeva
 *
 */
public class JsonUtils
{
	/**
	 * Converts Java Object to JSON format
	 * 
	 * @param object
	 * @param cType
	 * @return JSON format representation for Java Object
	 */
	public String toJson(Object object, Class<?> cType)
	{
		return new Gson().toJson(object, cType);
	}

	/**
	 * Converts Java Object to JSON format
	 * 
	 * @param object
	 * @return JSON format representation for Java Object
	 */
	public String toJson(Object object)
	{
		return new Gson().toJson(object);
	}

	/**
	 * Converts JSON string to Java Object
	 * @param strJson
	 * @param cType
	 * @return Java representation of JSON
	 */
	public <T> T fromJson(String strJson, Class<T> cType)
	{
		return new Gson().fromJson(strJson, cType);
	}

	/**
	 * Convert Json String to Map<String, Object>
	 * 
	 * @param strJsonString
	 * @return
	 */
	public Map<String, Object> jsonToMap(String strJsonString)
	{
		Type type = new TypeToken<HashMap<String, Object>>()
		{
		}.getType();
		return new Gson().fromJson(strJsonString, type);
	}

	/**
	 * Convert Json File to Map<String, Object>
	 * 
	 * @param strFilePath
	 * @return
	 * @throws FileNotFoundException
	 */
	public Map<String, Object> jsonFileToMap(String strFilePath) throws FileNotFoundException
	{
		BufferedReader br = new BufferedReader(new FileReader(strFilePath));
		return jsonToMap(new Gson().toJson(br, Object.class));
	}

	/**
	 * Find any key in JSON of any format
	 *
	 * @param content
	 * @param jsonPath
	 * @return
	 */
	public static List<?> findKeyInJSON(String content, String jsonPath) {
		if (content.equals("") || content==null) {
			System.out.println("Content is either blank or null");
			return null;
		}

		//System.out.println("jsonpath - "+jsonPath);
		List<?> values = null;
		Configuration conf = Configuration.builder()
				.jsonProvider(new GsonJsonProvider())
				.mappingProvider(new GsonMappingProvider())
				.build();

		try{
			DocumentContext context = JsonPath.using(conf).parse(content);
			values = context.read(jsonPath, List.class);//List
		}
		catch(JsonSyntaxException e){
			System.out.println("Not a valid JSON. \n \nContent: "+content );
		}

		return values;
	}

}
