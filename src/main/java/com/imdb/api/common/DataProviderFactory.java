package com.imdb.api.common;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class DataProviderFactory
{
	private DataProviderFactory()
	{
		// do nothing
	}

	@DataProvider(name = "json",parallel = true)
	public static Iterator<Object[]> jsonDataProvider(ITestContext context, Method method) throws Exception
	{
		ArrayList<HashMap<String, Object>> testdataList;
		JsonFilePath parameters = method.getAnnotation(JsonFilePath.class);
		Path resourceDirectory = Paths.get("src", "test", "resources", "test_data/imdb", parameters.path());
		String strInputFile = System.getProperty("user.dir") + "/" + resourceDirectory.toString();
		System.out.println("strInputFile"+strInputFile);
		JsonParser.getJsonParser(strInputFile);
		Collection<Object[]> dp = new ArrayList<Object[]>();
		testdataList = JsonParser.getTestData(strInputFile + "/" + method.getName());

		for (HashMap<String, Object> testdata : testdataList)
		{
			dp.add(new Object[] {testdata});
		}
		return dp.iterator();
	}
}