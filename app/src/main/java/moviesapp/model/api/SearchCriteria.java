package moviesapp.model.api;

import java.util.ArrayList;
import java.util.List;

public class SearchCriteria {

    public String title = "";
    public String minYear = "";
    public String maxYear = "";
    public List<String> genres = new ArrayList<>();
    public String minVoteAverage = "";
    public String page = "1";

    public SearchCriteria(){

    }

    public SearchCriteria(String title, String minYear, String maxYear,  List<String> genres, String minVoteAverage, String page) {
        this.title = title;
        this.minYear = minYear;
        this.maxYear = maxYear;
        this.genres = genres;
        this.minVoteAverage = minVoteAverage;
        this.page = page;
    }

    @Override
    public String toString() {
        return "SearchCriteria{" +
                "title='" + title + '\'' +
                ", minYear='" + minYear + '\'' +
                ", maxYear='" + maxYear + '\'' +
                ", genres=" + genres +
                ", minVoteAverage='" + minVoteAverage + '\'' +
                ", page='" + page + '\'' +
                '}';
    }

    public boolean noInformationSent() {
        return noDiscoverCriteria() && title.isEmpty();
    }

    boolean noDiscoverCriteria(){
        return minYear.isEmpty() && maxYear.isEmpty() && genres.isEmpty() && minVoteAverage.isEmpty();
    }
}
