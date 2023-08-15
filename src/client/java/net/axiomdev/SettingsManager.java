package net.axiomdev;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SettingsManager {
    private String fileName;
    private String path;
    public SettingsModel settings;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public SettingsManager(String name) {
        fileName = name;
        path = "";
        load();
    }

    public SettingsManager(String name, String path) {
        fileName = name;
        this.path = path;
        load();
    }

    public void load() {
        try {
            var reader = new FileReader(path + fileName);
            settings = gson.fromJson(reader, SettingsModel.class);
        } catch(FileNotFoundException e) {
            settings = new SettingsModel();
        }
    }

    public void save() {
        if (settings == null) return;
        try {
            Files.createDirectories(Paths.get(path));
            var writer = new FileWriter(path + fileName);
            gson.toJson(settings, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
