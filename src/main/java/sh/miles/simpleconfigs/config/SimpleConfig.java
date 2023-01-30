package sh.miles.simpleconfigs.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import sh.miles.simpleconfigs.ConfigUtils;
import sh.miles.simpleconfigs.type.YamlConverter;
import sh.miles.simpleconfigs.type.YamlConverterManager;

@SuppressWarnings("unchecked")
public class SimpleConfig {

    private final String name;
    private FileConfiguration config;
    private final Plugin plugin;

    public SimpleConfig(final String name, FileConfiguration config, final Plugin plugin) {
        this.config = config;
        this.plugin = plugin;
        this.name = name;
    }

    public <I> void set(final String path, final I value) {
        final YamlConverter<I, ?> converter = YamlConverterManager.getInstance()
                .getConverter((Class<I>) value.getClass());
        if (converter == null) {
            throw new IllegalArgumentException("No converter found for type " + value.getClass());
        }

        final Object converted = converter.convert(value);
        config.set(path, converted);
    }

    public <I> void setList(final String path, final Collection<I> value, final Class<I> type) {
        final YamlConverter<I, ?> converter = YamlConverterManager.getInstance().getConverter(type);
        if (converter == null) {
            throw new IllegalArgumentException("No converter found for type " + type.getName());
        }

        final List<Object> converted = value.stream().map(converter::convert).collect(Collectors.toList());
        config.set(path, converted);
    }

    public <I, O> I get(final String path, final Class<I> inputType) {
        final YamlConverter<I, O> converter = YamlConverterManager.getInstance().getConverter(inputType);
        if (converter == null) {
            throw new IllegalArgumentException(
                    "No converter found for types " + inputType.getName());
        }

        final Object configValue = config.get(path);
        if (configValue == null) {
            return null;
        }

        final I converted = converter.revert((O) configValue);
        return converter.revert((O) converted);
    }

    public <I, O> List<I> getList(final String path, final Class<I> inputType) {
        final YamlConverter<I, O> converter = YamlConverterManager.getInstance().getConverter(inputType);
        if (converter == null) {
            throw new IllegalArgumentException(
                    "No converter found for types " + inputType.getName());
        }

        final List<?> configValue = config.getList(path);
        if (configValue == null) {
            return new ArrayList<>();
        }

        return configValue.stream().map(v -> converter.revert((O) v))
                .collect(Collectors.toList());
    }

    public void save() {
        ConfigUtils.saveConfig(plugin, config, name);
    }

    public void reload() {
        this.config = ConfigUtils.reloadConfig(plugin, name);
    }

    public FileConfiguration getFileConfiguration() {
        return config;
    }

}
