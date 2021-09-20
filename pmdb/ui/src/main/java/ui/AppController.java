package ui;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;

import core.Label;
import core.Movie;
import javafx.fxml.FXML;
import json.Storage;

public class AppController {
    private Storage storage;
    private ArrayList<Label> labels = new ArrayList<>();
    private ArrayList<Label> movies = new ArrayList<>();
    public AppController() {
        storage = new Storage();
    }

    @FXML
    public void saveMovie(){
        storage.save(new Movie("Tenet", "description", LocalTime.of(10,10,10)));
    }
    
}
