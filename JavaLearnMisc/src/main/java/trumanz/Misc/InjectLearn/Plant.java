package trumanz.Misc.InjectLearn;

import com.google.inject.ProvidedBy;

@ProvidedBy(PlantDefaultProvider.class)
public interface Plant {
    void method();
}
