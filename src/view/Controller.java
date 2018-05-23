package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.SplittableRandom;

public class Controller {
    private Alert alert;


    @FXML
    protected void load_file(ActionEvent event){
        ArrayList<String> text = new ArrayList<String>();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());

        if(file != null){
            text = getTextFromFile(file);
        }
        System.out.println(text.get(0));
    }

    public ArrayList<String> getTextFromFile(File txtfile){
        ArrayList<String> text = new ArrayList<String >();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(txtfile)));
            String line;
            while ((line = br.readLine())!=null){
                text.add(line);
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        return text;
    }

}
