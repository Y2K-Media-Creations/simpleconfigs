package sh.miles.simpleconfigs;

import org.bukkit.plugin.Plugin;

import com.google.gson.reflect.TypeToken;

import lombok.Setter;
import sh.miles.simpleconfigs.config.SimpleConfig;
import sh.miles.simpleconfigs.type.YamlConverter;
import sh.miles.simpleconfigs.type.YamlConverterManager;

@SuppressWarnings("all")
public final class SimpleConfigs {

        @Setter
        private static Plugin plugin;

        private SimpleConfigs() {
                throw new UnsupportedOperationException("Utility class");
        }

        public static SimpleConfig loadConfig(String name) {
                return new SimpleConfig(name, ConfigUtils.createConfig(plugin, name), plugin);
        }

        public static SimpleConfig getConfig(String name) {
                return new SimpleConfig(name, ConfigUtils.getConfigFile(plugin, name), plugin);
        }

        public static <I, O> void registerConverter(YamlConverter<I, O> converter) {
                YamlConverterManager.getInstance().registerConverter(converter);
        }

        public static <I, O> YamlConverter<I, O> getConverter(Class<I> inputType) {
                return YamlConverterManager.getInstance().getConverter(inputType);
        }

        public static <I, O> YamlConverter<I, O> getConverter(TypeToken<I> inputType) {
                return YamlConverterManager.getInstance().getConverter(inputType);
        }
}
