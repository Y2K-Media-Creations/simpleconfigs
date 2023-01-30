package sh.miles.simpleconfigs.type;

import com.google.gson.reflect.TypeToken;

public class LikeYamlConverter<I> implements YamlConverter<I, I> {

    private final TypeToken<I> type;

    public LikeYamlConverter(final Class<I> clazz) {
        this.type = TypeToken.get(clazz);
    }

    @Override
    public I convert(I input) {
        return input;
    }

    @Override
    public I revert(I output) {
        return output;
    }

    @Override
    public TypeToken<I> getInputToken() {
        return type;
    }

    @Override
    public TypeToken<I> getOutputToken() {
        return getInputToken();
    }

    @Override
    public String toString() {
        return "LikeYamlConverter{" +
                "type=" + type.getType().getTypeName() +
                '}';
    }

}
