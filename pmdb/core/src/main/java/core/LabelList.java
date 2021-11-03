package core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class LabelList implements Iterable<ILabel> {
  private Collection<ILabel> labels;

  public LabelList() {
    labels = new ArrayList<>();
  }

  public void addLabel(ILabel label) {
    if (getLabel(label.getTitle()) == null) {
      labels.add(label);
    } else {
      throw new IllegalStateException("This movie-title is already in use.");
    }
  }

  private ILabel getLabel(String title) {
    return labels.stream().filter(m -> m.getTitle().equals(title)).findFirst().orElse(null);
  }

  public void removeMovie(ILabel label) {
    labels.remove(label);
  }

  public Collection<ILabel> getMovies() {
    return new ArrayList<>(labels);
  }

  public void clearMovieList() {
    labels.clear();
  }

  @Override
  public Iterator<ILabel> iterator() {
    return labels.iterator();
  }
}
