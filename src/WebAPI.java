import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class WebAPI {

    public static void main(String[] args)
    {
        String APIkey = "7c9d0ba92e44d16fc126a6e19165abdd";
        String urlNowPlaying = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + APIkey;
        String response = makeAPICall(urlNowPlaying);

        ArrayList<Movie> movies = new ArrayList<Movie>();
        if (response != null)
        {
            parseJSON(response, movies);
        }

        for (Movie movie : movies)
        {
            System.out.println(movie.getID() + " " + movie.getTitle() + " " + movie.getPosterPath() + " " + movie.getAdult() + " " + movie.getOriginalLanguage() + " " + movie.getVoteCount());
        }

        System.out.println();

        Scanner scan = new Scanner(System.in);
        System.out.print("Enter a movie ID: ");
        int movieID = scan.nextInt();

        String urlDetails = "https://api.themoviedb.org/3/movie/" + movieID + "?api_key=" + APIkey;
        response = makeAPICall(urlDetails);

        if (response != null)
        {
            parseJSONDetails(response, movies);
        }
    }

    public static String makeAPICall(String url)
    {
        try {
            URI myUri = URI.create(url); // creates a URI object from the url string
            HttpRequest request = HttpRequest.newBuilder().uri(myUri).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void parseJSON(String json, ArrayList<Movie> movies)
    {
        JSONObject jsonObj = new JSONObject(json);
        JSONArray movieList = jsonObj.getJSONArray("results");

        for (int i = 0; i < movieList.length(); i++)
        {
            JSONObject movieObj = movieList.getJSONObject(i);
            String movieTitle = movieObj.getString("title");
            int movieID = movieObj.getInt("id");
            String posterPath = movieObj.getString("poster_path");
            String fullPosterPath = "https://image.tmdb.org/t/p/w500" + posterPath;
            boolean adult = movieObj.getBoolean("adult");
            String originalLanguage = movieObj.getString("original_language");
            int voteCount = movieObj.getInt("vote_count");

            Movie movie = new Movie(movieTitle, movieID, fullPosterPath, adult, originalLanguage, voteCount);
            movies.add(movie);
        }
    }

    public static void parseJSONDetails(String json, ArrayList<Movie> movies)
    {
        JSONObject jsonObj = new JSONObject(json);
        JSONArray genreList = jsonObj.getJSONArray("genres");
        for (int i = 0; i < genreList.length(); i++)
        {
            JSONObject movieObj = genreList.getJSONObject(i);
            System.out.println(movieObj.getString("name"));
        }

        JSONArray productionList = jsonObj.getJSONArray("production_companies");
        for (int i = 0; i < productionList.length(); i++)
        {
            JSONObject movieObj = productionList.getJSONObject(i);
            System.out.println(movieObj.getString("name"));
            System.out.println("https://image.tmdb.org/t/p/w500" + movieObj.getString("logo_path"));
        }

        System.out.println(jsonObj.getString("tagline"));
    }
}