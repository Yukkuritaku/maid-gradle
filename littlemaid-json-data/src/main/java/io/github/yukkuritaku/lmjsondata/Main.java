package io.github.yukkuritaku.lmjsondata;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class Main {
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public static void main(String[] args) {
        System.out.println("Generating json!");
        System.out.println(GSON.toJson(LmrbJarUrlMap.getLmrbJarUrlMap()));
        String outputLocation = System.getProperty("output.location");
        try (JsonWriter writer = new JsonWriter(new FileWriter(outputLocation + "/littlemaid-rebirth-url.json"))) {
            GSON.toJson(LmrbJarUrlMap.getLmrbJarUrlMap(), new TypeToken<>(){}.getRawType(), writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(GSON.toJson(LmmlJarUrlMap.getLmmlJarUrlMap()));
        try (JsonWriter writer = new JsonWriter(new FileWriter(outputLocation + "/littlemaid-modelloader-url.json"))) {
            GSON.toJson(LmmlJarUrlMap.getLmmlJarUrlMap(), new TypeToken<>(){}.getRawType(), writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}