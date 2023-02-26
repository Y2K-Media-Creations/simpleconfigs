package sh.miles.simpleconfigs.config;

import java.util.Collection;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public interface ConfigSection {

    <I> void set(final String path, final I value);

    <I> void setList(final String path, final Collection<I> value, final Class<I> type);

    @SuppressWarnings("unused")
    <I, O> I get(final String path, final Class<I> type);

    @SuppressWarnings("unused")
    <I, O> List<I> getList(final String path, final Class<I> type);

    FileConfiguration getBukkitRoot();

    ConfigurationSection getCurrentBukkitSection();

    ConfigSection getSection(final String path);
}
