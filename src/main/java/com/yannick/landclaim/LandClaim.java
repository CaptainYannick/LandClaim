package com.yannick.landclaim;

import com.yannick.core.Core;
import com.yannick.core.config.Config;
import com.yannick.core.config.ConfigUpdater;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.util.Objects;

public final class LandClaim extends Core {

    private static LandClaim instance;
    private Config dataFile, mainConfig, messageConfig;

    @Override
    public void onPluginLoad() {
        instance = this;
    }

    @Override
    public void onPluginEnable() {
        mainConfig = new Config(this, "config.yml");
        dataFile = new Config(this, "data.yml");
        messageConfig = new Config(this, "messages.yml");

        updateConfigs();
    }

    @Override
    public void onPluginDisable() {
        instance = null;
    }

    public void updateConfigs() {
        String CONFIG_VERSION = "1";
        String MESSAGE_CONFIG_VERSION = "1";
        if (getMainConfig().getString("ConfigVersion") == null || !Objects.equals(getMainConfig().getString("ConfigVersion"), CONFIG_VERSION)) {
            try {
                ConfigUpdater.update(this, "config.yml", mainConfig.getFile());
                mainConfig = new Config(this, "config.yml");
                mainConfig.getConfig().set("ConfigVersion", CONFIG_VERSION);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (getMessageConfig().getString("ConfigVersion") == null || !Objects.equals(getMainConfig().getString("ConfigVersion"), MESSAGE_CONFIG_VERSION)) {
            try {
                ConfigUpdater.update(this, "messages.yml", messageConfig.getFile());
                messageConfig = new Config(this, "messages.yml");
                messageConfig.getConfig().set("ConfigVersion", MESSAGE_CONFIG_VERSION);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static LandClaim getInstance() {
        return instance;
    }
    public YamlConfiguration getMainConfig() {
        return mainConfig.getConfig();
    }
    public YamlConfiguration getMessageConfig() {
        return messageConfig.getConfig();
    }
    public Config getDataFileRaw() {
        return dataFile;
    }
    public YamlConfiguration getDataFile() {
        return dataFile.getConfig();
    }
}
