package com.example.studentscoresmanagement;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Update implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private Button buttonUpdate;

    @FXML
    private AnchorPane container;

    @FXML
    private StackPane stackParent;

    @FXML
    private ImageView test;

    @FXML
    private Text updateA;

    @FXML
    private Text updatePercent;

    @FXML
    private Text updateRC;

    @FXML
    private ProgressBar updateBar;

    private static DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static LocalDateTime now = LocalDateTime.now();

    @FXML
    private AnchorPane scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateBar.setStyle("fx-accent: green;");
        try {
            updateRC.setText("Cập nhật gần nhất: "+getDate());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onUpdate(ActionEvent event) {
        new Thread(() -> {
            for(int i = 0; i <=100; i++){
                final int position = i;
                Platform.runLater(() -> {
                    updateBar.setProgress(position/100.0);
                    updatePercent.setText(position+"%");
                    if(updatePercent.getText().equals("100%")){
                        updateA.setText("Hoàn Tất!!");
                        buttonUpdate.setDisable(true);
                        try {
                            updateRC.setText("Cập nhật gần nhất: "+saveDate());
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                try{
                    Thread.sleep(100);
                }catch(Exception e){ System.err.println(e); }
            }
        }).start();

    }

    public String getDate() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("C:\\Users\\ADMIN\\IdeaProjects\\StudentScoresManagement\\src\\main\\resources\\com\\example\\studentscoresmanagement\\UpdateData.txt"));
        String date="";
        while(sc.hasNextLine()){
            date=sc.nextLine();
        }
        sc.close();
        return date;
    }

    public String saveDate() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new File("C:\\Users\\ADMIN\\IdeaProjects\\StudentScoresManagement\\src\\main\\resources\\com\\example\\studentscoresmanagement\\UpdateData.txt"));
        writer.print("");
        String dateNow=now.format(format);
        writer.print(dateNow);
        writer.close();
        return dateNow;
    }

    @FXML
    void onBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HomePage.fxml")));
        Scene scene = backButton.getScene();

        root.translateXProperty().set(scene.getHeight());
        AnchorPane parentContainer = (AnchorPane) scene.getRoot();
        parentContainer.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(),0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(javafx.util.Duration.seconds(1),kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(e->{
            parentContainer.getChildren().remove(container);
        });
        timeline.play();
    }

}

