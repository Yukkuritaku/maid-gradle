package io.github.yukkuritaku.lmjsondata;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class Main {

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public static void main(String[] args) {
        try (JsonWriter writer = new JsonWriter(new FileWriter("littlemaid-rebirth-url.json"))) {
            GSON.toJson(LmrbJarUrlMap.getLmrbJarUrlMap(), Map.class, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (JsonWriter writer = new JsonWriter(new FileWriter("littlemaid-modelloader-url.json"))) {
            GSON.toJson(LmmlJarUrlMap.getLmmlJarUrlMap(), Map.class, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
