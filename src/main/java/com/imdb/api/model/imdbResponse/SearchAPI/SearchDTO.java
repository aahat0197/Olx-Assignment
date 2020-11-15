
package com.imdb.api.model.imdbResponse.SearchAPI;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchDTO {

    @SerializedName(value="Title")
    public String title;
    @SerializedName(value="Year")
    public String year;
    @SerializedName(value="imdbID")
    public String imdbId;
    @SerializedName(value="Type")
    public String type;
    @SerializedName(value="Poster")
    public String poster;

    public float rating;

}
