package sh.miles.simpleconfigs.type;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gson.reflect.TypeToken;

public final class YamlConverterManager {

    private static YamlConverterManager instance;

    private final Map<TypeToken<?>, YamlConverter<?, ?>> converters = new HashMap<>();

    private YamlConverterManager(){
        registerConverter(new LikeYamlConverter<>(String.class));
        registerConverter(new LikeYamlConverter<>(Integer.class));
        registerConverter(new LikeYamlConverter<>(Double.class));
        registerConverter(new LikeYamlConverter<>(Boolean.class));
        registerConverter(new LikeYamlConverter<>(Long.class));
        registerConverter(new LikeYamlConverter<>(Float.class));
        registerConverter(new LikeYamlConverter<>(Byte.class));
        registerConverter(new LikeYamlConverter<>(Short.class));
        registerConverter(new LikeYamlConverter<>(Character.class));
    }

    public <I, O> boolean registerConverter(YamlConverter<I, O> converter) {
        return converters.put(converter.getInputToken(), converter) != null;
    }

    @SuppressWarnings("unchecked")
    public <I, O> YamlConverter<I, O> getConverter(TypeToken<I> inputToken) {
        return (YamlConverter<I, O>) converters.get(inputToken);
    }

    public <I, O> YamlConverter<I, O> getConverter(Class<I> inputClass) {
        return getConverter(TypeToken.get(inputClass));
    }

    @SuppressWarnings("all")
    public Set<YamlConverter<?, ?>> getConverters() {
        return new HashSet<>(converters.values());
    }

    public static YamlConverterManager getInstance() {
        if (instance == null) {
            instance = new YamlConverterManager();
        }
        return instance;
    }

}
