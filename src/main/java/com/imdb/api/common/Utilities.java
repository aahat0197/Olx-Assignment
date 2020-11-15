package com.imdb.api.common;

import okhttp3.*;

import java.io.IOException;
import java.util.Map;

public class Utilities {

    OkHttpClient client = new OkHttpClient();

public Response getAPIResponse(String URL, Map<String,String> headers, Map<String,String> queryParams ) throws IOException {

    HttpUrl.Builder httpURLBuilder= new HttpUrl.Builder();
    httpURLBuilder.scheme("https").host("movie-database-imdb-alternative.p.rapidapi.com");

    for(Map.Entry<String,String> e:queryParams.entrySet())
    {
        httpURLBuilder.addQueryParameter(e.getKey(),e.getValue());
    }

    Request.Builder requestBuilder = new Request.Builder()
            .url(httpURLBuilder.build())
            .get();

    if(headers!=null && !headers.isEmpty())
    {
        for(Map.Entry<String,String> e:headers.entrySet())
        {
            requestBuilder.addHeader(e.getKey(),e.getValue());
        }
    }

    Response response = client.newCall(requestBuilder.build()).execute();
    return response;
}

}
