package sh.miles.simpleconfigs.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import lombok.NonNull;
import sh.miles.simpleconfigs.type.YamlConverter;
import sh.miles.simpleconfigs.type.YamlConverterManager;

@SuppressWarnings("unchecked")
public class SimpleConfigSection implements ConfigSection {

    private final FileConfiguration root;
    private final ConfigurationSection currentSection;

    protected SimpleConfigSection(FileConfiguration root) {
        this.root = root;
        this.currentSection = root;
    }

    protected SimpleConfigSection(@NonNull ConfigSection parent, @NonNull final String path) {
        this.root = parent.getBukkitRoot();
        this.currentSection = root.getConfigurationSection(path);
    }

    @Override
    public <I> void set(String path, I value) {
        final YamlConverter<I, ?> converter = YamlConverterManager.getInstance()
                .getConverter((Class<I>) value.getClass());
        if (converter == null) {
            throw new IllegalArgumentException("No converter found for type " + value.getClass());
        }

        final Object converted = converter.convert(value);
        this.currentSection.set(path, converted);
    }

    @Override
    public <I> void setList(String path, Collection<I> value, Class<I> type) {
        final YamlConverter<I, ?> converter = YamlConverterManager.getInstance().getConverter(type);
        if (converter == null) {
            throw new IllegalArgumentException("No converter found for type " + type.getName());
        }

        final List<Object> converted = value.stream().map(converter::convert).collect(Collectors.toList());
        this.currentSection.set(path, converted);
    }

    @Override
    public <I, O> I get(String path, Class<I> type) {
        final YamlConverter<I, O> converter = YamlConverterManager.getInstance().getConverter(type);
        if (converter == null) {
            throw new IllegalArgumentException(
                    "No converter found for types " + type.getName());
        }

        final Object configValue = this.currentSection.get(path);
        if (configValue == null) {
            return null;
        }

        return converter.revert((O) configValue);
    }

    @Override
    public <I, O> List<I> getList(String path, Class<I> type) {
        final YamlConverter<I, O> converter = YamlConverterManager.getInstance().getConverter(type);
        if (converter == null) {
            throw new IllegalArgumentException(
                    "No converter found for types " + type.getName());
        }

        final List<?> configValue = this.currentSection.getList(path);
        if (configValue == null) {
            return new ArrayList<>();
        }

        return configValue.stream().map(v -> converter.revert((O) v))
                .collect(Collectors.toList());
    }

    @Override
    public ConfigSection getSection(String path) {
        return new SimpleConfigSection(this, path);
    }

    @Override
    public FileConfiguration getBukkitRoot() {
        return this.root;
    }

    @Override
    public ConfigurationSection getCurrentBukkitSection() {
        return this.currentSection;
    }

}
