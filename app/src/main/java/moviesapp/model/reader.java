package moviesapp.model;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

public class reader {
    public void Print_name(){
        try{
            String filePath = "s_team_pigl_moviesapp/app/src/main/java/moviesapp/model/données.json";
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(new File(filePath));

            int page = jsonNode.get("page").asInt();
            JsonNode resultsNode = jsonNode.get("results");

            for(JsonNode result : resultsNode){
                String Name = result.get("original_title").asText() ;
                System.out.println("Title: " + Name);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}