package json.moviepersistance;

import com.fasterxml.jackson.databind.module.SimpleModule;
import core.ILabel;
import core.LabelList;

public class LabelModule extends SimpleModule {
  public LabelModule() {
    addSerializer(ILabel.class, new LabelSerializer());
    addDeserializer(ILabel.class, new LabelDeserializer());
    addSerializer(LabelList.class, new LabelListSerializer());
    addDeserializer(LabelList.class, new LabelListDeserializer());
  }
}
