package sh.miles.simpleconfigs.type;

import java.util.function.Function;

import com.google.gson.reflect.TypeToken;

public class SimpleYamlConverter<I, O> implements YamlConverter<I, O> {

    private final TypeToken<I> inputToken;
    private final TypeToken<O> outputToken;
    private final Function<I, O> converter;
    private final Function<O, I> reverter;

    public SimpleYamlConverter(Function<I, O> converter, Function<O, I> reverter, Class<I> inputClass,
            Class<O> outputClass) {
        this.inputToken = TypeToken.get(inputClass);
        this.outputToken = TypeToken.get(outputClass);
        this.converter = converter;
        this.reverter = reverter;
    }

    @Override
    public O convert(I input) {
        return this.converter.apply(input);
    }

    @Override
    public I revert(O output) {
        return this.reverter.apply(output);
    }

    @Override
    public TypeToken<I> getInputToken() {
        return this.inputToken;
    }

    @Override
    public TypeToken<O> getOutputToken() {
        return this.outputToken;
    }

    @Override
    public String toString() {
        return "SimpleYamlConverter{" +
                "inputToken=" + inputToken +
                ", outputToken=" + outputToken +
                ", converter=" + converter +
                ", reverter=" + reverter +
                '}';
    }

}
