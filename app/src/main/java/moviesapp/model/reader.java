package moviesapp.model;
import com.fasterxml.jackson.databind.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class reader {
    public void Print_information(){
        try{
            String filePath = "s_team_pigl_moviesapp/app/src/main/java/moviesapp/model/données.json";
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(new File(filePath));

            int page = jsonNode.get("page").asInt();
            JsonNode resultsNode = jsonNode.get("results");

            for(JsonNode result : resultsNode){
                boolean adult = result.get("adult").asBoolean() ;
                String backdrop_path = result.get("backdrop_path").asText() ;
                int id = result.get("id").asInt() ;
                String language = result.get("original_language").asText() ;
                String original_title = result.get("original_title").asText() ;
                String overview = result.get("overview").asText() ;
                double popularity = result.get("popularity").asDouble() ;
                String poster_path = result.get("poster_path").asText() ;
                String release = result.get("release_date").asText() ;
                String title = result.get("title").asText() ;
                boolean video = result.get("video").asBoolean() ;
                double vote_average = result.get("vote_average").asDouble() ;
                double vote_count  =result.get("vote_count").asDouble() ;
                System.out.println("--------------------") ;
                System.out.println("adulte : " + adult);
                System.out.println("backdrop_path : " + backdrop_path);
                System.out.println("id : " + id);
                System.out.println("language : " + language);
                System.out.println("original_title : " + original_title);
                System.out.println("overview : " + overview);
                System.out.println("popularity : " + popularity);
                System.out.println("poster_path : " + poster_path);
                System.out.println("release : " + release);
                System.out.println("title : " + title);
                System.out.println("video : " + video);
                System.out.println("vote_average : " + vote_average);
                System.out.println("vote_count : " + vote_count);
                System.out.println("--------------------") ;
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void movie_informations(int id) {
        try {
            String filePath = "s_team_pigl_moviesapp/app/src/main/java/moviesapp/model/données.json";
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(new File(filePath));
            int page = jsonNode.get("page").asInt();
            JsonNode resultsNode = jsonNode.get("results");
            List<JsonNode> elt = new ArrayList<>();

            for(JsonNode result : resultsNode ){
                if(result.get("id").asInt() == id){
                    elt.add(result) ;
                }
            }
            for (JsonNode result : elt) {
                boolean adult = result.get("adult").asBoolean() ;
                String backdrop_path = result.get("backdrop_path").asText() ;
                int Id = result.get("id").asInt() ;
                String language = result.get("original_language").asText() ;
                String original_title = result.get("original_title").asText() ;
                String overview = result.get("overview").asText() ;
                double popularity = result.get("popularity").asDouble() ;
                String poster_path = result.get("poster_path").asText() ;
                String release = result.get("release_date").asText() ;
                String title = result.get("title").asText() ;
                boolean video = result.get("video").asBoolean() ;
                double vote_average = result.get("vote_average").asDouble() ;
                double vote_count  =result.get("vote_count").asDouble() ;
                System.out.println("--------------------") ;
                System.out.println("adulte : " + adult);
                System.out.println("backdrop_path : " + backdrop_path);
                System.out.println("id : " + id);
                System.out.println("language : " + language);
                System.out.println("original_title : " + original_title);
                System.out.println("overview : " + overview);
                System.out.println("popularity : " + popularity);
                System.out.println("poster_path : " + poster_path);
                System.out.println("release : " + release);
                System.out.println("title : " + title);
                System.out.println("video : " + video);
                System.out.println("vote_average : " + vote_average);
                System.out.println("vote_count : " + vote_count);
                System.out.println("--------------------") ;

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}