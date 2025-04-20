package net.laboulangerie.api.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import net.laboulangerie.api.models.TypedNameIdModel;

public class GsonFiles {
    public static TypedNameIdModel[] readArray(File file)
            throws JsonSyntaxException, FileNotFoundException, IOException {
        if (!file.isFile()) {
            if (!file.createNewFile())
                throw new IOException("Error creating new file: " + file.getAbsolutePath());
            else {
                List<TypedNameIdModel> emptyArray = new ArrayList<TypedNameIdModel>();
                writeArray(file, emptyArray);
                return (TypedNameIdModel[]) emptyArray.toArray();
            }
        }

        return new Gson().fromJson(new FileReader(file),
                new com.google.gson.reflect.TypeToken<TypedNameIdModel>() {
                }.getType());
    }

    public static void writeArray(File file, List<TypedNameIdModel> array) throws IOException {
        FileWriter writer = new FileWriter(file, false);
        writer.write(new Gson().toJson(array));
        writer.close();
    }
}
