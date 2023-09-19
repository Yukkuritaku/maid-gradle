package io.github.yukkuritaku.lmjsondata;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public static void main(String[] args) {
        System.out.println("Generating json!");
        System.out.println(GSON.toJson(LmrbJarUrlMap.getLmrbJarUrlMap()));
        try (JsonWriter writer = new JsonWriter(new FileWriter("littlemaid-rebirth-url.json"))) {
            GSON.toJson(LmrbJarUrlMap.getLmrbJarUrlMap(), Map.class, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(GSON.toJson(LmmlJarUrlMap.getLmmlJarUrlMap()));
        try (JsonWriter writer = new JsonWriter(new FileWriter("littlemaid-modelloader-url.json"))) {
            GSON.toJson(LmmlJarUrlMap.getLmmlJarUrlMap(), Map.class, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
