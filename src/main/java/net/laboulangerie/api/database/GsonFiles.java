package net.laboulangerie.api.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import net.laboulangerie.api.models.TypedNameUuidModel;

public class GsonFiles {
    public static TypedNameUuidModel[] readArray(File file) throws JsonSyntaxException, FileNotFoundException {
        return new Gson().fromJson(new FileReader(file), TypedNameUuidModel[].class);
    }

    public static void writeArray(File file, List<TypedNameUuidModel> array) throws IOException {
        FileWriter writer = new FileWriter(file, false);
        writer.write(new Gson().toJson(array));
        writer.close();
    }
}
