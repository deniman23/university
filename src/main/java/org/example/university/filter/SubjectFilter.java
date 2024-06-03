package org.example.university.filter;

import java.util.List;

public class SubjectFilter {
    private String search;
    private List<String> includes;

    public SubjectFilter() {
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public List<String> getIncludes() {
        return includes;
    }

    public void setIncludes(List<String> includes) {
        this.includes = includes;
    }
}
