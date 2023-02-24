package sh.miles.simpleconfigs;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import sh.miles.simpleconfigs.config.SimpleConfig;

public class BukkitTypeTests extends BasicTest {
    
    @Test
    public void testBukkitType() {
        SimpleConfigs.setPlugin(getPlugin());
        SimpleConfigs.registerBukkitConverters();
        SimpleConfig config = SimpleConfigs.loadConfig("test.yml");

        config.set("sound", org.bukkit.Sound.AMBIENT_CAVE);
        config.save();

        org.bukkit.Sound sound = config.get("sound", org.bukkit.Sound.class);
        assertNotNull(sound, "Sound should not be null");
    }

    @Test
    public void testBukkitTypeList() {
        SimpleConfigs.setPlugin(getPlugin());
        SimpleConfigs.registerBukkitConverters();
        SimpleConfig config = SimpleConfigs.loadConfig("test.yml");

        config.setList("sounds", List.of(org.bukkit.Sound.AMBIENT_CAVE, org.bukkit.Sound.AMBIENT_UNDERWATER_ENTER), org.bukkit.Sound.class);
        config.save();

        List<org.bukkit.Sound> sounds = config.getList("sounds", org.bukkit.Sound.class);
        assertNotNull(sounds, "Sounds should not be null");
    }

}
