package com.imdb.api.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.imdb.api.common.Utilities;
import com.imdb.api.model.imdbResponse.IdTitleAPI.TitleResponseDTO;
import com.imdb.api.model.imdbResponse.SearchAPI.SearchResponseDTO;
import okhttp3.Response;
import com.imdb.api.constants.IMDBConstants;

import java.io.IOException;
import java.util.HashMap;

public class IMDBManager implements IMDBConstants {
    Utilities utilities= new Utilities();
    ObjectMapper objectMapper= new ObjectMapper();
    HashMap<String,String> headers;
    HashMap<String,String> queryParams;
public SearchResponseDTO searchAPI(HashMap<String,Object> data) throws IOException {


    if(data.containsKey("headers"))
    {
        headers=objectMapper.convertValue(data.get("headers"), HashMap.class);
    }
    if(data.containsKey("queryParams"))
    {
        queryParams=objectMapper.convertValue(data.get("queryParams"), HashMap.class);
    }

    SearchResponseDTO searchResponseDTO;
    Response response= utilities.getAPIResponse(SEARCH_API_URL,headers,queryParams);
    searchResponseDTO= new Gson().fromJson(new String(response.body().bytes()), SearchResponseDTO.class);

    return searchResponseDTO;
}

    public TitleResponseDTO titleAPI(HashMap<String,Object> data) throws IOException {

        if(data.containsKey("headers"))
        {
            headers=objectMapper.convertValue(data.get("headers"), HashMap.class);
        }
        if(data.containsKey("queryParams"))
        {
            queryParams=objectMapper.convertValue(data.get("queryParams"), HashMap.class);
        }

        TitleResponseDTO titleResponseDTO;
        Response response= utilities.getAPIResponse(TITLE_API_URL,headers,queryParams);
        titleResponseDTO= new Gson().fromJson(new String(response.body().bytes()), TitleResponseDTO.class);

        return titleResponseDTO;
    }

}
