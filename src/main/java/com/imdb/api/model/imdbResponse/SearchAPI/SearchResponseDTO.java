
package com.imdb.api.model.imdbResponse.SearchAPI;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchResponseDTO {


    @SerializedName(value="Search")
    public List<SearchDTO> search;
    public String totalResults;

    @SerializedName(value="Response")
    public String response;

}
