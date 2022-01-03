package filmparser;

public class FilmParserTest {
    public static void main(String[] args) {
        String path = "testfilm.m2rec";
        FilmParser fp = new FilmParser();
        Film film = fp.parseFilm(path);
        System.out.println(film.getName());
        System.out.println(film.getMeshTag());
        System.out.println(film.getBuild());
    }
}
