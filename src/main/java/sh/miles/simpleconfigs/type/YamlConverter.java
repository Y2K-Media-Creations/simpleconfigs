package sh.miles.simpleconfigs.type;

import com.google.gson.reflect.TypeToken;

public interface YamlConverter<I, O> {

    O convert(I input);

    I revert(O configValue);

    TypeToken<I> getInputToken();

    TypeToken<O> getOutputToken();

}
