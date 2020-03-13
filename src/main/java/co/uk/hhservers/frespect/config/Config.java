package co.uk.hhservers.frespect.config;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import java.io.File;
import java.io.IOException;

public class Config {

    private static Config instance;
    public static File config;
    public static ConfigurationLoader<CommentedConfigurationNode> loader;
    public static CommentedConfigurationNode confNode;
    public static String message = " has pressed F to pay respects to ";

    public static void setup(File myFile, ConfigurationLoader<CommentedConfigurationNode> load) {
        config = myFile;
        loader = load;
    }

    public static void load() throws IOException {

        if(!config.exists()){
            config.createNewFile();
            confNode = loader.load();
            addValues();
            loader.save(confNode);
        }
        confNode = loader.load();
    }

    public static void addValues(){
        confNode.getNode("message").setValue(message);
    }
}
