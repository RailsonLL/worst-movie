package com.railson.worstmovie.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageResult<T> {
    private int page;

    @JsonProperty("size")
    private int pageSize;

    private List<T> results;

    public PageResult(int page, int pageSize, List<T> results) {
        this.page = page;
        this.pageSize = pageSize;
        this.results = results;
    }

}
