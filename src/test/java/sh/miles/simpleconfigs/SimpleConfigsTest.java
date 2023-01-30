package sh.miles.simpleconfigs;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.google.gson.reflect.TypeToken;

import sh.miles.simpleconfigs.config.SimpleConfig;
import sh.miles.simpleconfigs.type.LikeYamlConverter;
import sh.miles.simpleconfigs.type.YamlConverter;
import sh.miles.simpleconfigs.type.YamlConverterManager;

public class SimpleConfigsTest extends BasicTest {

    @Test
    public void testConfigExists() {
        assertNotNull(SimpleConfigs.loadConfig("test.yml"), "Config should exist");

        assertNotNull(SimpleConfigs.getConfig("test.yml"), "Config should exist");
    }

    @Test
    public void testConfigSave() {
        SimpleConfigs.setPlugin(getPlugin());
        SimpleConfig config = SimpleConfigs.loadConfig("test.yml");
        assertDoesNotThrow(() -> config.save(), "Config should save");
    }

    @Test
    @SuppressWarnings("all")
    public void testGetYamlType() {

        final YamlConverter<String, String> converter = YamlConverterManager.getInstance().getConverter(TypeToken.get(String.class));
        assertNotNull(converter, "Converter should exist");
    }

    @Test
    public void testSpecialityGetYamlType() {
        final YamlConverter<String, String> converter = YamlConverterManager.getInstance().getConverter(String.class);
        assertNotNull(converter, "Converter should exist");
    }

    @Test
    public void testRegisterYamlType() {
        final YamlConverter<String, String> converter = YamlConverterManager.getInstance().getConverter(String.class);
        assertNotNull(converter, "Converter should exist");

        final YamlConverter<String, String> newConverter = new YamlConverter<String, String>() {

            @Override
            public String convert(String input) {
                return input;
            }

            @Override
            public String revert(String output) {
                return output;
            }

            @Override
            public TypeToken<String> getInputToken() {
                return TypeToken.get(String.class);
            }

            @Override
            public TypeToken<String> getOutputToken() {
                return TypeToken.get(String.class);
            }

        };

        YamlConverterManager.getInstance().registerConverter(newConverter);

        final YamlConverter<String, String> newConverter2 = YamlConverterManager.getInstance().getConverter(String.class);
        assertNotNull(newConverter2, "Converter should exist");
    }

    @Test
    public void testGetFromConfig() {
        SimpleConfigs.setPlugin(getPlugin());
        SimpleConfig config = SimpleConfigs.loadConfig("test.yml");
        assertNotNull(config, "Config should exist");
        config.set("key", "value");
        config.save();

        final String test = config.get("key", String.class);
        assertNotNull(test, "Test should exist");
    }

    @Test
    public void testGetListFromConfig() {
        SimpleConfigs.setPlugin(getPlugin());
        SimpleConfig config = SimpleConfigs.loadConfig("test.yml");
        assertNotNull(config, "Config should exist");
        config.setList("key", List.of("value"), String.class);
        config.save();

        final List<String> test = config.getList("key", String.class);
        assertNotNull(test, "Test should exist");
    }

    @Test
    public void testCreateAndRetrieveCustomType() {

        YamlConverterManager.getInstance().registerConverter(new LikeYamlConverter<>(UUID.class));

        SimpleConfigs.setPlugin(getPlugin());
        SimpleConfig config = SimpleConfigs.loadConfig("test.yml");

        final UUID uuid = UUID.randomUUID();
        config.set("key", uuid);
        config.save();

        final UUID test = config.get("key", UUID.class);

        assertNotNull(test, "Test should exist");

    }

}
