package model;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Model {
    // 왼쪽이 0 오른쪽이 1
    File[] files = new File[2];

    ArrayList<String> leftText = new ArrayList<String>();
    ArrayList<String> rightText = new ArrayList<String>();

    public void loadFile(File file, int side) {
        files[side] = file;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = reader.readLine()) != null) {
                switch (side) {
                    case 0:
                        leftText.add(line);
                        break;
                    case 1:
                        rightText.add(line);
                        break;
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveFile(File file, int side) throws IOException {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file.getPath() + ".txt"));

            switch (side) {
                case 0: {
                    for (String str : leftText) {
                        writer.write(str);
                        writer.newLine();
                    }
                    break;
                }
                case 1: {
                    for (String str : rightText) {
                        writer.write(str);
                        writer.newLine();
                    }
                    break;
                }
            }
            writer.flush();
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public File getFile(int side){
        return files[side];
    }

    public ArrayList<String> getLeftText(){
        return leftText;
    }

    public ArrayList<String> getRightText(){
        return rightText;
    }

}
