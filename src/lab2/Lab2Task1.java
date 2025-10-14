package lab2;

import lombok.AllArgsConstructor;
import lombok.Data;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
class Movie
{
    private String title;
    private Integer year;
    private String duration;
    private String country;
    private String genre;
    private String director;
    private List<String> actors;
    private Float score;

    @Override
    public String toString()
    {
        return String.format(
            "| %-45s | %4d | %-8s | %-14s | %-10s | %-18s | %3s |",
            title,
            year,
            duration,
            country,
            genre,
            director,
            score == null ? '-' : String.format("%3.1f", score)
        );
    }
}

public class Lab2Task1
{
    private static final List<Movie> movies = new ArrayList<>();
    private static final String srcPath = "./src/lab2/movies.txt";

    private static void printMovies(List<Movie> printedMovies)
    {
        System.out.println("\n\n");
        System.out.printf("| %-45s | %4s | %-8s | %-14s | %-10s | %-18s | %-3s |", "Название", "Год", "Время", "Страна", "Жанр", "Режиссёр", "*");
        System.out.println("\n----------------------------------------------------------------------------------------------------------------------------");
        for (Movie movie : printedMovies)
        {
            System.out.println(movie);
        }
    }

    private static void printMoviesInFile(List<Movie> printedMovies, String filePath)
    {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(String.format("| %-45s | %4s | %-8s | %-14s | %-10s | %-18s | %-3s |", "Название", "Год", "Время", "Страна", "Жанр", "Режиссёр", "*"));
            writer.write("\n----------------------------------------------------------------------------------------------------------------------------\n");
            for (Movie movie : printedMovies)
            {
                writer.write(movie.toString() + '\n');
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Movie parseMovieLine(String line)
    {
        String[] parts = line.split("\t");

        String title = parts[0].trim();
        Integer year = Integer.parseInt(parts[1].trim());
        String duration = parts[2].trim();
        String country = parts[3].trim();
        String genre = parts[4].trim();
        String director = parts[5].trim().replace("Режиссёр: ", "");
        List<String> actors = List.of(parts[6].trim().replace("В ролях: ", "").split(","));
        Float score = (parts.length > 7) ? Float.parseFloat(parts[7].trim()) : null;

        return new Movie(title, year, duration, country, genre, director, actors, score);
    }

    private static void parseMovies()
    {
        List<String> lines;
        try
        {
            lines = Files.readAllLines(Path.of(srcPath));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        for (String line : lines)
        {
            Movie movie = parseMovieLine(line);
            movies.add(movie);
        }

        printMovies(movies);
    }

    private static void filterActionMovies()
    {
        List<Movie> actionMovies = new ArrayList<>();
        for (Movie movie : movies)
        {
            if (Objects.equals(movie.getGenre(), "боевик"))
            {
                actionMovies.add(movie);
            }
        }
        printMovies(actionMovies);
        printMoviesInFile(actionMovies, "./results/lab2/actionMovies.txt");
    }

    private static void getGenresWithAverageScore()
    {
        List<String> genres = new ArrayList<>();
        List<Integer> count = new ArrayList<>();
        List<Float> average = new ArrayList<>();
        for (Movie movie : movies)
        {
            if (movie.getScore() == null) continue;
            String currentGenre = movie.getGenre();
            if (genres.contains(currentGenre))
            {
                for (int i = 0; i < genres.size(); i++)
                {
                    if (Objects.equals(genres.get(i), currentGenre))
                    {
                        average.set(
                            i,
                            (average.get(i) * count.get(i) + movie.getScore())
                            / (count.get(i) + 1)
                        );
                        break;
                    }
                }
            }
            else
            {
                genres.add(movie.getGenre());
                count.add(1);
                average.add(movie.getScore());
            }
        }

        System.out.println('\n');
        System.out.printf("| %-15s | %-4s |\n", "Жанр", "*");
        System.out.print("--------------------------\n");
        for (int i = 0; i < genres.size(); i++)
        {
            System.out.printf("| %-15s | %4.2f |\n", genres.get(i), average.get(i));
        }

        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter("./results/lab2/genresAverageScore.txt"));
            writer.write(String.format("| %-15s | %-4s |\n", "Жанр", "*"));
            writer.write("--------------------------\n");
            for (int i = 0; i < genres.size(); i++)
            {
                writer.write(String.format("| %-15s | %4.2f |\n", genres.get(i), average.get(i)));
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void getUSAPercentage()
    {
        int count = 0;
        for (Movie movie : movies)
        {
            if (Objects.equals(movie.getCountry(), "США")) count++;
        }

        Float percentage = (float) count / (float) movies.size() * 100;
        System.out.print(String.format("\n\nФильмов из США: %.2f", percentage) + '%');
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter("./results/lab2/USAFilmsPercentage.txt"));
            writer.write(String.format("Фильмов из США: %.2f", percentage) + '%');
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int compareMoviesByDuration(Movie a, Movie b) {
        int duration1 = Integer.parseInt(a.getDuration().split(" ")[0]);
        int duration2 = Integer.parseInt(b.getDuration().split(" ")[0]);
        return Integer.compare(duration2, duration1);
    }

    private static void getRussianFilmsSortedByDuration()
    {
        List<Movie> russianMovies = new ArrayList<>();
        for (Movie movie : movies)
        {
            if (Objects.equals(movie.getCountry(), "Россия"))
            {
                russianMovies.add(movie);
            }
        }

        russianMovies.sort(Lab2Task1::compareMoviesByDuration);
        printMovies(russianMovies);
        printMoviesInFile(russianMovies, "./results/lab2/russianMovies.txt");
    }

    public static void run()
    {
        parseMovies();
        filterActionMovies();
        getGenresWithAverageScore();
        getUSAPercentage();
        getRussianFilmsSortedByDuration();
    }
}
