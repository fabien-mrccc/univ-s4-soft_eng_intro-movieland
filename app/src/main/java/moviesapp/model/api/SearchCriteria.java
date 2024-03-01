package moviesapp.model.api;

import java.util.ArrayList;
import java.util.List;

public class SearchCriteria {

    public String title = "";
    public String minYear = "";
    public String maxYear = "";
    public List<String> genres = new ArrayList<>();
    public String minVoteAverage = "";

    public SearchCriteria(){

    }

    public SearchCriteria(String title, String minYear, String maxYear,  List<String> genres, String minVoteAverage) {
        this.title = title;
        this.minYear = minYear;
        this.maxYear = maxYear;
        this.genres = genres;
        this.minVoteAverage = minVoteAverage;
    }

    public boolean informationSent() {
        if (gotDiscoverCriteria() && title.isEmpty()){
            System.out.println("\n| No information sent. \n| Please give me more details for your next search.");
            return false;
        }
        return true;
    }

    boolean gotDiscoverCriteria(){
        return !minYear.isEmpty() || !maxYear.isEmpty() || !minVoteAverage.isEmpty() || !genres.isEmpty();
    }
}
