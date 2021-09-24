package core;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Random;

public class Label {
    private String label;
    private String color;
    private String description;
    private Collection<IMovie> movies;

    public Label(String label){
        setLabel(label);
        setColor(getRandomColor());
        setDescription("");
        setMovies(new ArrayList<>());
    }

    public Label(String label, String color, String description, Collection<IMovie> movies){
        setLabel(label);
        setColor(color);
        setDescription(description);
        setMovies(movies);
    }



    /**
     * 
     * @return A random hexcolor #FFFFFF
     */
    public String getRandomColor(){
        String hexValues = "0123456789ABCEDF";
        StringBuilder colorValue = new StringBuilder("#");
        Random rnd = new Random();

        for (int i = 0; i < 6; i++){
            int randomValue = rnd.nextInt(16);
            char randomHexValue = hexValues.charAt(randomValue);
            colorValue.append(randomHexValue);
        }

        return colorValue.toString();
    }

    /**
     * 
     * @param label
     * @return wheter label is valid. Can only contain letters.
     */
    private boolean validLabel(String label){
        return label.matches("[A-Za-z ]+");
    }

    public void setLabel(String label){
        if (!validLabel(label)){
            throw new IllegalArgumentException("Invalid label name, can only contain letters");
        }
        this.label = label;
    }
    
    public String getLabel(){
        return label;
    }

    public void setColor(String color){
        this.color = color;
    }

    public String getColor(){
        return color;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setMovies(Collection<IMovie> movies){
        this.movies = new ArrayList<>(movies);
    }

    public Collection<IMovie> getMovies(){
        return new ArrayList<>(movies);
    }

    public void removeMovie(IMovie movie){
        movies.remove(movie);
    }

    public void addMovie(IMovie movie){
        movies.add(movie);
    }

    @Override
    public String toString(){
        String s = "Label: " + getLabel() + "\n" +
                    "Color: " + getColor() +
                    (getDescription().equals("") ? "" : "\nDescription: " + getDescription());
        
        if (movies.size() == 0){
            return s;
        }
        
        else{
            s += "\nMovies: ";
            for (IMovie movie : getMovies()){
                s += movie.getTitle() + ", ";
                
            }
            return s.substring(0,s.length()-2); 
        }
    }
}
