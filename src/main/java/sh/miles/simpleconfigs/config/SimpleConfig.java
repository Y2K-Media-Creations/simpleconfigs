package sh.miles.simpleconfigs.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import lombok.NonNull;
import sh.miles.simpleconfigs.ConfigUtils;

public class SimpleConfig extends SimpleConfigSection {

    private final String name;
    private final Plugin plugin;

    public SimpleConfig(@NonNull final String name, @NonNull final FileConfiguration config,
            @NonNull final Plugin plugin) {
        super(config);
        this.plugin = plugin;
        this.name = name;
    }

    public String name() {
        return this.name;
    }

    public void save() {
        ConfigUtils.saveConfig(plugin, getBukkitRoot(), name);
    }

}
