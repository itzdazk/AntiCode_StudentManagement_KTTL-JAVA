package com.example.studentscoresmanagement;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class DashBoard implements Initializable {

    @FXML
    private Rectangle CNPMd;

    @FXML
    private Rectangle CNPMt;

    @FXML
    private Rectangle DTBd;

    @FXML
    private Rectangle DTBt;

    @FXML
    private Rectangle DTCBd;

    @FXML
    private Rectangle DTCBt;

    @FXML
    private Rectangle GDTCd;

    @FXML
    private Rectangle GDTCt;

    @FXML
    private Rectangle KTLTd;

    @FXML
    private Rectangle KTLTt;

    @FXML
    private Rectangle THVPd;

    @FXML
    private Rectangle THVPt;

    @FXML
    private Button backButton;

    @FXML
    private BarChart<?, ?> barChart;

    @FXML
    private AnchorPane container;

    @FXML
    private Text percentCNPM;

    @FXML
    private Text percentD;

    @FXML
    private Text percentDTB;

    @FXML
    private Text percentDTCB;

    @FXML
    private Text percentGDTC;

    @FXML
    private Text percentKD;

    @FXML
    private Text percentKTLT;

    @FXML
    private Text percentTHVP;

    @FXML
    private PieChart pieChart;

    @FXML
    private AnchorPane scene;

    @FXML
    private ImageView test;

    @FXML
    private Text textCNPMd;

    @FXML
    private Text textCNPMt;

    @FXML
    private Text textDTBd;

    @FXML
    private Text textDTBt;

    @FXML
    private Text textDTCBd;

    @FXML
    private Text textDTCBt;

    @FXML
    private Text textGDTCd;

    @FXML
    private Text textGDTCt;

    @FXML
    private Text textKTLTd;

    @FXML
    private Text textKTLTt;

    @FXML
    private Text textTHVPd;

    @FXML
    private Text textTHVPt;

    @FXML
    private Text totalClass;

    @FXML
    private Text totalD;

    @FXML
    private Text totalKD;

    @FXML
    private Text totalStudent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            createPie();
            createBar();
            totalStudent.setText(String.valueOf(selectScoreData("SELECT COUNT(*) FROM StudentScores ")) );

            setPercent("SELECT COUNT(*) FROM StudentScores where ĐTB_hệ_10>=5 ","SELECT COUNT(*) FROM StudentScores ",DTBt,DTBd,percentDTB,textDTBt,textDTBd);
            setPercent("SELECT COUNT(*) FROM StudentScores where THVP>=5 ","SELECT COUNT(*) FROM StudentScores ",THVPt,THVPd,percentTHVP,textTHVPt,textTHVPd);
            setPercent("SELECT COUNT(*) FROM StudentScores where DTCB>=5 ","SELECT COUNT(*) FROM StudentScores ",DTCBt,DTCBd,percentDTCB,textDTCBt,textDTCBd);
            setPercent("SELECT COUNT(*) FROM StudentScores where GDTC>=5 ","SELECT COUNT(*) FROM StudentScores ",GDTCt,GDTCd,percentGDTC,textGDTCt,textGDTCd);
            setPercent("SELECT COUNT(*) FROM StudentScores where CNPM>=5 ","SELECT COUNT(*) FROM StudentScores ",CNPMt,CNPMd,percentCNPM,textCNPMt,textCNPMd);
            setPercent("SELECT COUNT(*) FROM StudentScores where KTLT>=5 ","SELECT COUNT(*) FROM StudentScores ",KTLTt,KTLTd,percentKTLT,textKTLTt,textKTLTd);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void setPercent(String querry1,String querry2,Rectangle t,Rectangle d,Text percent,Text slt,Text sld) throws SQLException, ClassNotFoundException {
        int percentDTB= (int) (toDouble(selectScoreData(querry1))/toDouble(selectScoreData(querry2))*100);
        int resultDTBt= (int) (toDouble(350)/toDouble(100)*percentDTB);
        int resultDTBd=350-resultDTBt;
        t.setWidth(resultDTBt);
        d.setWidth(resultDTBd);
        slt.setText(String.valueOf(selectScoreData(querry1)));
        sld.setText(String.valueOf(selectScoreData(querry2)-selectScoreData(querry1)));
        percent.setText(percentDTB+"%");
        if(percentDTB>50) {
            d.setX(d.getX() + (resultDTBt - 175));
        }else{
            d.setX(d.getX() - (175-resultDTBt));
        }
    }

    public void createPie() throws SQLException, ClassNotFoundException {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Không Đạt", selectScoreData("SELECT COUNT(*) FROM StudentScores where ĐTB_hệ_10<5")),
                new PieChart.Data("Đạt", selectScoreData("SELECT COUNT(*) FROM StudentScores where ĐTB_hệ_10>=5")));
        pieChart.setData(pieChartData);
        totalD.setText( String.valueOf(selectScoreData("SELECT COUNT(*) FROM StudentScores where ĐTB_hệ_10>=5")));
        totalKD.setText(String.valueOf(selectScoreData("SELECT COUNT(*) FROM StudentScores where ĐTB_hệ_10<5")));

        int d = (int) toDouble((int) (selectScoreData("SELECT COUNT(*) FROM StudentScores where ĐTB_hệ_10>=5")/toDouble(selectScoreData("SELECT COUNT(*) FROM StudentScores"))*100));
        percentD.setText(d+"%");
        percentKD.setText(100-d+"%");
    }

    public void createBar() throws SQLException, ClassNotFoundException {
        XYChart.Series series = new XYChart.Series();
        series.setName("Thống Kê Học Lực");

        series.getData().add(new XYChart.Data("Xuất Sắc",selectScoreData("SELECT COUNT(*) FROM StudentScores where ĐTB_hệ_10 <=10 AND ĐTB_hệ_10 >=9")));
        series.getData().add(new XYChart.Data("Giỏi",selectScoreData("SELECT COUNT(*) FROM StudentScores where ĐTB_hệ_10 >=8.5 AND ĐTB_hệ_10 <9")));
        series.getData().add(new XYChart.Data("Khá Giỏi",selectScoreData("SELECT COUNT(*) FROM StudentScores where ĐTB_hệ_10 >=8 AND ĐTB_hệ_10 <8.5")));
        series.getData().add(new XYChart.Data("Khá",selectScoreData("SELECT COUNT(*) FROM StudentScores where ĐTB_hệ_10 >=7 AND ĐTB_hệ_10 <8")));
        series.getData().add(new XYChart.Data("TB Khá",selectScoreData("SELECT COUNT(*) FROM StudentScores where ĐTB_hệ_10 >=6.5 AND ĐTB_hệ_10 <7")));
        series.getData().add(new XYChart.Data("TB",selectScoreData("SELECT COUNT(*) FROM StudentScores where ĐTB_hệ_10 >=5.5 AND ĐTB_hệ_10 <6.5")));
        series.getData().add(new XYChart.Data("TB Yếu",selectScoreData("SELECT COUNT(*) FROM StudentScores where ĐTB_hệ_10 >=5 AND ĐTB_hệ_10 <5.5")));
        series.getData().add(new XYChart.Data("Yếu",selectScoreData("SELECT COUNT(*) FROM StudentScores where ĐTB_hệ_10 >=4 AND ĐTB_hệ_10 <5")));
        series.getData().add(new XYChart.Data("Kém",selectScoreData("SELECT COUNT(*) FROM StudentScores where ĐTB_hệ_10 <4")));

        barChart.getData().addAll(series);
    }

    public int selectScoreData(String condition) throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=StudentScores;user=SA;password=123456;encrypt=true;trustServerCertificate=true;");
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(condition);
        int result = 0;
        while(rs.next()){
            result=Integer.parseInt(rs.getString(1));
        }

        return result;
    }

    public double toDouble(int a){
        double b=a;
        return a;
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
