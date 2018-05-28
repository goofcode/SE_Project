package model;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class FileManager {
    // 왼쪽이 0 오른쪽이 1
    private File file;

    ArrayList<String> text = new ArrayList<String>();

    public void loadFile(File file) {
        try {
            this.file = file;
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = reader.readLine()) != null)
                text.add(line);
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setText(ArrayList<String> k) {
      text = (ArrayList<String>)k.clone();
    }

    public void saveFile(File file) throws IOException {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file.getPath()));

            for (String str : text) {
                writer.write(str);
                writer.newLine();
            }
            writer.flush();
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void clearText(){
        this.file = null;
        text.clear();
    }

    public File getFile(){
        return this.file;
    }

    public ArrayList<String> getText(){
        return text;
    }

}
