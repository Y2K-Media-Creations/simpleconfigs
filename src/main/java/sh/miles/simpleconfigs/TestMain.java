package sh.miles.simpleconfigs;

import java.io.File;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

/**
 * Class for testing the plugin in a development environment with MockBukkit.
 */
public class TestMain extends JavaPlugin {

    public TestMain() {
        super();
    }

    protected TestMain(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onEnable() {
        getLogger().info("Test plugin enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("Test plugin disabled");
    }

}
