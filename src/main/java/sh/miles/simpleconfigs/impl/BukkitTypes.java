package sh.miles.simpleconfigs.impl;

import org.bukkit.Material;
import org.bukkit.Sound;

import lombok.experimental.UtilityClass;
import sh.miles.simpleconfigs.type.SimpleYamlConverter;
import sh.miles.simpleconfigs.type.YamlConverter;
import sh.miles.simpleconfigs.type.YamlConverterManager;

@UtilityClass
public final class BukkitTypes {

    public static final YamlConverter<Sound, String> SOUND_CONVERTER = new SimpleYamlConverter<>(Sound::toString,
            (String fromConfig) -> {
                try {
                    return Sound.valueOf(fromConfig);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Invalid sound: " + fromConfig);
                }
            }, Sound.class, String.class);

    public static final YamlConverter<Material, String> MATERIAL_CONVERTER = new SimpleYamlConverter<>(
            Material::toString,
            (String fromConfig) -> {
                final Material material = Material.matchMaterial(fromConfig);
                if (material == null) {
                    throw new IllegalArgumentException("Invalid material: " + fromConfig);
                }
                return material;
            }, Material.class, String.class);

    public static void register() {
        YamlConverterManager.getInstance().registerConverter(SOUND_CONVERTER);
        YamlConverterManager.getInstance().registerConverter(MATERIAL_CONVERTER);
    }
}
