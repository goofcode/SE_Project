package model;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileManager {

    private File file;
    private List<String> lines;
    private int size;

    public FileManager(File file) {

        this.file = file;
        this.lines = new ArrayList<>();

        try {

            this.file = file;
            Charset charset = StandardCharsets.UTF_8;
            lines.addAll(Files.readAllLines(this.file.toPath(), charset));
            size = lines.size();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void synchronizeSize(FileManager fileManager){

        for (int i=size; i<lines.size(); i++)
            lines.remove(i);
        for (int i=fileManager.getSize(); i<fileManager.getLines().size(); i++)
            fileManager.getLines().remove(i);

        if(this.size < fileManager.getSize())
            this.padLines(fileManager.getSize() - this.size);
        else if (this.size > fileManager.getSize())
            fileManager.padLines(this.size - fileManager.getSize());
    }

    private void padLines(int padding) {
        for (int i=0; i<padding; i++) lines.add("");
    }

    public File getFile(){
        return this.file;
    }
    public int getSize() { return this.size; }
    public List<String> getLines(){
        return lines;
    }
    public String getLine(int index){
        return lines.get(index);
    }
    public String getLinesAsOneString() {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i< lines.size(); i++){
            sb.append(lines.get(i));
            if(i != lines.size() - 1) sb.append("\n");
        }
        return sb.toString();
    }

    public void setLines(List<String> lines) {
        this.lines = new ArrayList<>(lines);
    }
    public void setLine(int index, String line) {
        lines.set(index, line);
        if(index > size-1) size = index+1;
    }
    public void setLinesFromOneString(String linesString){
        setLines(Arrays.asList(linesString.split("\n", -1)));
        size = lines.size();
    }

    public void save() {

        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(file.getPath()));

            for(int i=0; i<size; i++){
                writer.write(lines.get(i));
                writer.newLine();
            }

            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
