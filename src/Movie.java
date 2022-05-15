public class Movie {

    private String title;
    private int id;
    private String posterPath;
    private boolean adult;
    private String originalLanguage;
    private int voteCount;

    public Movie(String title, int id, String posterPath, boolean adult, String originalLanguage, int voteCount)
    {
        this.title = title;
        this.id = id;
        this.posterPath = posterPath;
        this.adult = adult;
        this.originalLanguage = originalLanguage;
        this.voteCount = voteCount;
    }

    public String getTitle()
    {
        return title;
    }

    public int getID()
    {
        return id;
    }

    public String getPosterPath()
    {
        return posterPath;
    }

    public boolean getAdult() { return adult; }

    public String getOriginalLanguage() { return originalLanguage; }

    public int getVoteCount() { return voteCount; }
}
