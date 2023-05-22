package com.example.studentscoresmanagement;

import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class StudentManagement implements Initializable {

    @FXML
    private StackPane stackContainer;

    @FXML
    private AnchorPane container;

    @FXML
    private Pane container1;

    @FXML
    private Button addButton;
    @FXML
    private Button backButton;
    @FXML
    private Button buttonReset;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button buttonUpdate;

    @FXML
    private ComboBox<String> buttonHK;
    @FXML
    private ComboBox<String> combatK;
    @FXML
    private ComboBox<String> combatKH;
    @FXML
    private ComboBox<String> combatN;
    private ObservableList<String> listKH = FXCollections.observableArrayList("2021-2024");
    private ObservableList<String> listK = FXCollections.observableArrayList("IT");
    private ObservableList<String> listN = FXCollections.observableArrayList("Lập Trình Máy Tính");
    private ObservableList<String> listHK = FXCollections.observableArrayList("Hè 2022");


    @FXML
    private TextField inMSSV;
    @FXML
    private TextField inFN;
    @FXML
    private RadioButton maleRB;
    @FXML
    private RadioButton femaleRB;
    @FXML
    private ToggleGroup gender;
    @FXML
    private TextField inTHVP;
    @FXML
    private TextField inDTCB;
    @FXML
    private TextField inGDTC;
    @FXML
    private TextField inCNPM;
    @FXML
    private TextField inKTLT;
    @FXML
    private GridPane inForm;
    @FXML
    private GridPane inForm1;

    @FXML
    private TextField searchTF;
    @FXML
    private TableView<StudentScore> tableStudent;
    @FXML
    private TableColumn<StudentScore, String> tableMSSV;
    @FXML
    private TableColumn<StudentScore, String> tableFN;
    @FXML
    private TableColumn<StudentScore, String> tableGender;
    @FXML
    private TableColumn<StudentScore, Double> tableTHVP;
    @FXML
    private TableColumn<StudentScore, Double> tableDTCB;
    @FXML
    private TableColumn<StudentScore, Double> tableGDTC;
    @FXML
    private TableColumn<StudentScore, Double> tableCNPM;
    @FXML
    private TableColumn<StudentScore, Double> tableKTLT;
    @FXML
    private TableColumn<StudentScore, Double> tableDTB10;
    @FXML
    private TableColumn<StudentScore, Double> tableDTB4;
    @FXML
    private TableColumn<StudentScore, String> tableDC;
    @FXML
    private TableColumn<StudentScore, String> tableXL;

    @FXML
    private TableColumn<StudentScore, String> tableKQ;

    @FXML
    private Button sortMSSV;
    @FXML
    private Button sortFN;
    @FXML
    private Button sortGT;
    @FXML
    private Button sortTHVP;
    @FXML
    private Button sortDTCB;
    @FXML
    private Button sortGDTC;
    @FXML
    private Button sortCNPM;
    @FXML
    private Button sortKTLT;
    @FXML
    private Button sortDTB10;
    @FXML
    private Button sortDTB4;
    @FXML
    private Button sortDC;
    @FXML
    private Button sortXL;

    @FXML
    private Button sortKQ;
    private static int indexMSSV=1,indexFN=2,indexGT=1,indexTHVP=1,indexDTCB=1,indexGDTC=1,indexCNPM=1,indexKTLT=1,indexDTB10=1,indexDTB4=1,indexDC=1,indexXL=1,indexKQ=1;
    @FXML
    private Label labelTong;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableMSSV.setCellValueFactory(new PropertyValueFactory<StudentScore, String>("MSSV"));
        tableFN.setCellValueFactory(new PropertyValueFactory<StudentScore, String>("FullName"));
        tableGender.setCellValueFactory(new PropertyValueFactory<StudentScore, String>("Gender"));
        tableTHVP.setCellValueFactory(new PropertyValueFactory<StudentScore, Double>("THVP"));
        tableDTCB.setCellValueFactory(new PropertyValueFactory<StudentScore, Double>("DTCB"));
        tableGDTC.setCellValueFactory(new PropertyValueFactory<StudentScore, Double>("GDTC"));
        tableCNPM.setCellValueFactory(new PropertyValueFactory<StudentScore, Double>("CNPM"));
        tableKTLT.setCellValueFactory(new PropertyValueFactory<StudentScore, Double>("KTLT"));
        tableDTB10.setCellValueFactory(new PropertyValueFactory<StudentScore, Double>("DTB10"));
        tableDTB4.setCellValueFactory(new PropertyValueFactory<StudentScore, Double>("DTB4"));
        tableDC.setCellValueFactory(new PropertyValueFactory<StudentScore, String>("DC"));
        tableXL.setCellValueFactory(new PropertyValueFactory<StudentScore, String>("XL"));
        tableKQ.setCellValueFactory(new PropertyValueFactory<StudentScore, String>("KQ"));
        tableStudent.setEditable(true);

        container.setOpacity(0);
        makeFadeInTransition();

        setDisableButton(true);
        setStyleCBB();
        loadData();
        searchBar();
        sortAll();
        defaultSort("order by reverse(LEFT(trim(reverse(Họ_và_Tên)),CHARINDEX(' ',trim(reverse(Họ_và_Tên)) + ' ')-1)) ASC");
    }

    public void loadData(){
        try {
            Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=StudentScores;user=SA;password=123456;encrypt=true;trustServerCertificate=true;");
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("Select * from StudentScores");
            ObservableList<StudentScore> data = FXCollections.observableArrayList();
            while(rs.next()){
                data.add(new StudentScore(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        Double.valueOf(rs.getString(4)),
                        Double.valueOf(rs.getString(5)),
                        Double.valueOf(rs.getString(6)),
                        Double.valueOf(rs.getString(7)),
                        Double.valueOf(rs.getString(8))));
            }
            tableStudent.setItems(data);
            totalSV(labelTong);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void addStudent(ActionEvent event) throws SQLException {
        if(inputCheckAdd()==false){
            alertShowAdd();
        }else{
            StudentScore studentScore = new StudentScore(inMSSV.getText().trim(),
                    inFN.getText().trim(),
                    checkGender(maleRB),
                    Double.parseDouble(inTHVP.getText().trim()),
                    Double.parseDouble(inDTCB.getText().trim()),
                    Double.parseDouble(inGDTC.getText().trim()),
                    Double.parseDouble(inCNPM.getText().trim()),
                    Double.parseDouble(inKTLT.getText().trim())
            );
            ObservableList<StudentScore> studentScores = tableStudent.getItems();
            Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=StudentScores;user=SA;password=123456;encrypt=true;trustServerCertificate=true;");
            String query ="insert into StudentScores(MSSV,Họ_và_Tên,Giới_tính,THVP,DTCB,GDTC,CNPM,KTLT,ĐTB_hệ_10,ĐTB_hệ_4,Điểm_Chữ,Xếp_Loại,Kết_Quả)values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1,studentScore.getMSSV().trim());
            preparedStatement.setString(2,studentScore.getFullName().trim());
            preparedStatement.setString(3,studentScore.getGender().trim());
            preparedStatement.setDouble(4,studentScore.getTHVP());
            preparedStatement.setDouble(5,studentScore.getDTCB());
            preparedStatement.setDouble(6,studentScore.getGDTC());
            preparedStatement.setDouble(7,studentScore.getCNPM());
            preparedStatement.setDouble(8,studentScore.getKTLT());
            preparedStatement.setDouble(9,studentScore.getDTB10());
            preparedStatement.setDouble(10,studentScore.getDTB4());
            preparedStatement.setString(11,studentScore.getDC().trim());
            preparedStatement.setString(12,studentScore.getXL().trim());
            preparedStatement.setString(13,studentScore.getKQ().trim());
            preparedStatement.executeUpdate();

            studentScores.add(studentScore);
            tableStudent.setItems(studentScores);
            alert("Thành Công!","Thêm thành công");
            clearForm();
            if(searchTF.getText().trim().equals("")) {
                defaultSort("order by reverse(LEFT(trim(reverse(Họ_và_Tên)),CHARINDEX(' ',trim(reverse(Họ_và_Tên)) + ' ')-1)) ASC");
            }
            totalSV(labelTong);
            setDisableButton(true);

        }
    }

    @FXML
    void resetStudent(ActionEvent event) {
        setDisableButton(true);
        clearForm();
        tableStudent.sort();
        tableStudent.refresh();
    }

    public void clearForm(){
        this.inMSSV.setText("");
        this.inFN.setText("");
        this.maleRB.setSelected(false);
        this.femaleRB.setSelected(false);
        this.inTHVP.setText("");
        this.inDTCB.setText("");
        this.inGDTC.setText("");
        this.inCNPM.setText("");
        this.inKTLT.setText("");
    }

    @FXML
    void updateStudent(ActionEvent event) throws SQLException {
        if(inputCheckUpdate()==false){
            alertShowUpdate();
        }else {
            StudentScore studentScore = new StudentScore(inMSSV.getText().trim(),
                    inFN.getText().trim(),checkGender(maleRB),
                    Double.parseDouble(inTHVP.getText().trim()),
                    Double.parseDouble(inDTCB.getText().trim()),
                    Double.parseDouble(inGDTC.getText().trim()),
                    Double.parseDouble(inCNPM.getText().trim()),
                    Double.parseDouble(inKTLT.getText().trim()));
            Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=StudentScores;user=SA;password=123456;encrypt=true;trustServerCertificate=true;");
            String query ="Update StudentScores set MSSV=N'"+studentScore.getMSSV()+"'," +
                    "Họ_và_Tên=N'"+studentScore.getFullName()+"',Giới_tính=N'"+studentScore.getGender()+"'," +
                    "THVP="+studentScore.getTHVP()+","+
                    "DTCB="+studentScore.getDTCB()+","+
                    "GDTC="+studentScore.getGDTC()+","+
                    "CNPM="+studentScore.getCNPM()+","+
                    "KTLT="+studentScore.getKTLT()+","+
                    "ĐTB_hệ_10="+studentScore.getDTB10()+","+
                    "ĐTB_hệ_4="+studentScore.getDTB4()+","+
                    "Điểm_Chữ=N'"+studentScore.getDC()+"',"+
                    "Xếp_Loại=N'"+studentScore.getXL()+"'," +
                    "Kết_Quả=N'"+studentScore.getKQ()+"'" +
                    " Where MSSV='"+tableStudent.getSelectionModel().getSelectedItem().getMSSV()+"'";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.executeUpdate();

            StudentScore currentTableData = tableStudent.getSelectionModel().getSelectedItem();
            currentTableData.setMSSV(inMSSV.getText().trim());
            currentTableData.setFullName(inFN.getText().trim());
            currentTableData.setGender(checkGender(maleRB));
            currentTableData.setTHVP(Double.parseDouble(inTHVP.getText().trim()));
            currentTableData.setDTCB(Double.parseDouble(inDTCB.getText().trim()));
            currentTableData.setGDTC(Double.parseDouble(inGDTC.getText().trim()));
            currentTableData.setCNPM(Double.parseDouble(inCNPM.getText().trim()));
            currentTableData.setKTLT(Double.parseDouble(inKTLT.getText().trim()));
            currentTableData.update();
            tableStudent.getItems().add(tableStudent.getSelectionModel().getSelectedIndex(), currentTableData);
            tableStudent.getItems().remove(tableStudent.getSelectionModel().getSelectedIndex() - 1);
            tableStudent.refresh();

            alert("Thành Công!","Cập nhật thành công");

            if(searchTF.getText().trim().equals("")) {
                defaultSort("order by reverse(LEFT(trim(reverse(Họ_và_Tên)),CHARINDEX(' ',trim(reverse(Họ_và_Tên)) + ' ')-1)) ASC");
            }
            setDisableButton(true);
            clearForm();
            totalSV(labelTong);
        }
    }

    @FXML
    void deleteStudent(ActionEvent event) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=StudentScores;user=SA;password=123456;encrypt=true;trustServerCertificate=true;");
        String query ="DELETE from StudentScores where MSSV=?";
        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.setString(1,inMSSV.getText().trim());
        preparedStatement.executeUpdate();
        tableStudent.getItems().removeAll(tableStudent.getSelectionModel().getSelectedItem());

        alert("Thành Công!","Xóa thành công");
        if(searchTF.getText().trim().equals("")) {
            defaultSort("order by reverse(LEFT(trim(reverse(Họ_và_Tên)),CHARINDEX(' ',trim(reverse(Họ_và_Tên)) + ' ')-1)) ASC");
        }
        clearForm();
        setDisableButton(true);
        totalSV(labelTong);
    }

    public void alertShowAdd() throws SQLException {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Không thành công!");
        if (checkMSSV(inMSSV.getText().trim()) == false) {
            alert.setContentText("MSSV sai định dạng hoặc bỏ trống");
        } else if( checkMSSVAdd()==false){
            alert.setContentText("MSSV không được trùng");
        }else if(checkFN(inFN.getText().trim())==false){
            alert.setContentText("Họ và tên không được trống và chỉ sử dụng ký tự chữ");
        }else if(checkGender(maleRB,femaleRB)==false){
            alert.setContentText("Giới tính không được để trống");
        }else if(checkScore(inTHVP.getText().trim())==false
                || checkScore(inDTCB.getText().trim())==false
                || checkScore(inGDTC.getText().trim())==false
                || checkScore(inCNPM.getText().trim())==false
                || checkScore(inKTLT.getText().trim())==false) {
            alert.setContentText("Điểm không được để trống,dùng ký tự chữ,từ 0-10");
        }
        DialogPane dialog=alert.getDialogPane();
        dialog.setStyle("-fx-font-size:13px;\n"+
                "-fx-background-color:linear-gradient(from 0.0% 0.0% to 100.0% 100.0%, #868686 0.0%, #ffffff 100.0%);\n"+
                "-fx-font-weight: bold;\n");
        ButtonType buttonTypeYes = new ButtonType("Xác Nhận",ButtonBar.ButtonData.YES);
        alert.getButtonTypes().setAll(buttonTypeYes);
        alert.show();
    }

    public void alertShowUpdate() throws SQLException {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Không thành công!");
        if (checkMSSV(inMSSV.getText().trim()) == false) {
            alert.setContentText("MSSV Sai định dạng hoặc bỏ trống");
        } else if( checkMSSVUpdate()==false){
            alert.setContentText("MSSV không được trùng");
        }else if(checkFN(inFN.getText().trim())==false){
            alert.setContentText("Họ và tên không được để trống và chỉ sử dụng ký tự chữ");
        }else if(checkGender(maleRB,femaleRB)==false){
            alert.setContentText("Giới tính không được để trống");
        }else if(checkScore(inTHVP.getText().trim())==false
                || checkScore(inDTCB.getText().trim())==false
                || checkScore(inGDTC.getText().trim())==false
                || checkScore(inCNPM.getText().trim())==false
                || checkScore(inKTLT.getText().trim())==false) {
            alert.setContentText("Điểm không được để trống, sử dụng ký tự chữ, (0-10)");
        }
        DialogPane dialog=alert.getDialogPane();
        dialog.setStyle("-fx-font-size:13px;\n"+
                "-fx-background-color:linear-gradient(from 0.0% 0.0% to 100.0% 100.0%, #868686 0.0%, #ffffff 100.0%);\n"+
                "-fx-font-weight: bold;\n");
        ButtonType buttonTypeYes = new ButtonType("Xác Nhận",ButtonBar.ButtonData.YES);
        alert.getButtonTypes().setAll(buttonTypeYes);
        alert.show();
    }

    public boolean inputCheckAdd() throws SQLException {
        if(checkMSSV(inMSSV.getText().trim())==false
                || checkFN(inFN.getText().trim())==false
                || checkScore(inTHVP.getText().trim())==false
                || checkScore(inDTCB.getText().trim())==false
                || checkScore(inGDTC.getText().trim())==false
                || checkScore(inCNPM.getText().trim())==false
                || checkScore(inKTLT.getText().trim())==false
                || checkGender(maleRB,femaleRB)==false
                || checkMSSVAdd() ==false){
            return false;
        }
        return true;
    }

    public boolean inputCheckUpdate() throws SQLException {
        if(checkMSSV(inMSSV.getText().trim())==false
                || checkFN(inFN.getText().trim())==false
                || checkScore(inTHVP.getText().trim())==false
                || checkScore(inDTCB.getText().trim())==false
                || checkScore(inGDTC.getText().trim())==false
                || checkScore(inCNPM.getText().trim())==false
                || checkScore(inKTLT.getText().trim())==false
                || checkGender(maleRB,femaleRB)==false
                || checkMSSVUpdate()==false){
            return false;
        }
        return true;
    }

    public boolean checkMSSV(String mssv){
        String reMSSV="\\d{2}[-|/]\\d{1}[-|/]\\d{5}";
        if ( !mssv.matches(reMSSV)){
            return false;
        }
        return true;
    }

    public boolean checkFN(String name){
        if(name.isBlank() || name.isEmpty()){
            return false;
        }
        String tmpName[]=name.trim().split(" ");
        for(String a:tmpName){
            for(int i=0;i<a.length();i++){
                if(!Character.isAlphabetic(a.charAt(i))){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkGender(RadioButton ml,RadioButton fm){
        if(ml.isSelected()==false && fm.isSelected()==false){
            return false;
        }
        return true;
    }
    public static boolean checkNumeric(String score){
        try {
            Double.parseDouble(score);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public static boolean checkLimitedScore(String score){
        if(score.isEmpty()|| score.isBlank() || score==null);
        double bscore =Double.parseDouble(score);
        if(bscore<0 || bscore>10){
            return false;
        }
        return true;
    }

    public static boolean checkScore(String score){
        if(checkNumeric(score)==false || checkLimitedScore(score)==false ){
            return false;
        }
        return true;
    }

    public boolean checkMSSVAdd() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=StudentScores;user=SA;password=123456;encrypt=true;trustServerCertificate=true;");
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("Select * from StudentScores");
        ArrayList<String> MSSV= new ArrayList<>();
        while(rs.next()){
            MSSV.add(rs.getString(1));
        }
        for(int i=0;i<MSSV.size();i++){
            if(inMSSV.getText().trim().equals(MSSV.get(i).trim())){
                return false;
            }
        }
        return true;
    }

    public boolean checkMSSVUpdate() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=StudentScores;user=SA;password=123456;encrypt=true;trustServerCertificate=true;");
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("Select * from StudentScores");
        String tmp = tableStudent.getSelectionModel().getSelectedItem().getMSSV();
        ArrayList<String> MSSV= new ArrayList<>();
        while(rs.next()){
            if(!(tmp.trim().equals(rs.getString(1)))){
                MSSV.add(rs.getString(1));
            }
        }
        for(String a:MSSV){
            if(tmp.trim().equals(a.trim()) || inMSSV.getText().trim().equals(a.trim())) {
                return false;
            }
        }
        return true;
    }

    public void setDisableButton(boolean tmp){
        buttonDelete.setDisable(tmp);
        buttonUpdate.setDisable(tmp);
    }

    public static String checkGender(RadioButton tmp){
        if(tmp.isSelected()){
            return "Nam";
        }
        return "Nữ";
    }

    public void alert(String tt,String ct){
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle(tt);
        alert.setContentText(ct);
        DialogPane dialog=alert.getDialogPane();
        dialog.setStyle("-fx-font-size:13px;\n"+
                "-fx-background-color:linear-gradient(from 0.0% 0.0% to 100.0% 100.0%, #868686 0.0%, #ffffff 100.0%);\n"+
                "-fx-font-weight: bold;\n");
        ButtonType buttonTypeYes = new ButtonType("Xác Nhận",ButtonBar.ButtonData.YES);
        alert.getButtonTypes().setAll(buttonTypeYes);
        alert.show();
    }

    @FXML
    void selectedStudent(MouseEvent event) {
        StudentScore stOld = tableStudent.getSelectionModel().getSelectedItem();
        if( stOld!=null && tableStudent.getItems().size()>0 ) {
            setDisableButton(false);
            inMSSV.setText(stOld.getMSSV().trim());
            inFN.setText(stOld.getFullName().trim());
            if(stOld.getGender().trim().equals("Nam")){
                this.maleRB.setSelected(true);
            }else
                this.femaleRB.setSelected(true);
            inTHVP.setText(String.valueOf(stOld.getTHVP()));
            inDTCB.setText(String.valueOf(stOld.getDTCB()));
            inGDTC.setText(String.valueOf(stOld.getGDTC()));
            inCNPM.setText(String.valueOf(stOld.getCNPM()));
            inKTLT.setText(String.valueOf(stOld.getKTLT()));
        }
    }

    public void searchBar(){
        ObservableList<StudentScore> searchStudentScores = FXCollections.observableArrayList();
        searchTF.setOnKeyReleased(e->{
            if(searchTF.getText().trim().equals("")){
                loadData();
                defaultSort("order by reverse(LEFT(trim(reverse(Họ_và_Tên)),CHARINDEX(' ',trim(reverse(Họ_và_Tên)) + ' ')-1)) ASC");
            }
            else {
                searchStudentScores.clear();
                String searchQuery = "Select * from StudentScores where MSSV like N'%" + searchTF.getText().trim() + "%'"
                        +" Union Select * from StudentScores where Họ_và_Tên like N'%" + searchTF.getText().trim() + "%'"
                        +" Union Select * from StudentScores where THVP like N'%" + searchTF.getText().trim() + "%'"
                        +" Union Select * from StudentScores where DTCB like N'%" + searchTF.getText().trim() + "%'"
                        +" Union Select * from StudentScores where GDTC like N'%" + searchTF.getText().trim() + "%'"
                        +" Union Select * from StudentScores where CNPM like N'%" + searchTF.getText().trim() + "%'"
                        +" Union Select * from StudentScores where KTLT like N'%" + searchTF.getText().trim() + "%'"
                        +" Union Select * from StudentScores where Điểm_Chữ like N'%" + searchTF.getText().trim() + "%'"
                        +" Union Select * from StudentScores where Xếp_Loại like N'%" + searchTF.getText().trim() + "%'"
                        +" Union Select * from StudentScores where Kết_Quả like N'%" + searchTF.getText().trim() + "%'"
                        ;
                try {
                    Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=StudentScores;user=SA;password=123456;encrypt=true;trustServerCertificate=true;");
                    Statement statement = con.createStatement();
                    ResultSet rs = statement.executeQuery(searchQuery);
                    while (rs.next()) {
                        String queryMSSV = rs.getString("MSSV");
                        String queryFN = rs.getString("Họ_và_Tên");
                        String queryGender = rs.getString("Giới_tính");
                        double queryTHVP = Double.parseDouble(rs.getString("THVP"));
                        double queryDTCB = Double.parseDouble(rs.getString("DTCB"));
                        double queryGDTC = Double.parseDouble(rs.getString("GDTC"));
                        double queryCNPM = Double.parseDouble(rs.getString("CNPM"));
                        double queryKTLT = Double.parseDouble(rs.getString("KTLT"));
                        searchStudentScores.add(new StudentScore(queryMSSV, queryFN, queryGender, queryTHVP, queryDTCB, queryGDTC, queryCNPM, queryKTLT));

                    }
                    tableStudent.setItems(searchStudentScores);


                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void defaultSort(String querry){
        ObservableList<StudentScore> sortStudentScores = FXCollections.observableArrayList();
            sortStudentScores.clear();
            String searchQuery;
            searchQuery =querry;
            onSort(searchQuery,sortStudentScores);

    }

    public void sortAll(){
        onSortMSSV("order by MSSV ASC","order by MSSV DESC", sortMSSV);
        onSortFN("order by reverse(LEFT(trim(reverse(Họ_và_Tên)),CHARINDEX(' ',trim(reverse(Họ_và_Tên)) + ' ')-1)) ASC","order by reverse(LEFT(trim(reverse(Họ_và_Tên)),CHARINDEX(' ',trim(reverse(Họ_và_Tên)) + ' ')-1)) DESC", sortFN);
        onSortGT("order by Giới_tính DESC;","order by Giới_tính ASC", sortGT);
        onSortTHVP("order by THVP DESC","order by THVP ASC", sortTHVP);
        onSortDTCB("order by DTCB DESC","order by DTCB ASC", sortDTCB);
        onSortGDTC("order by GDTC DESC","order by GDTC ASC", sortGDTC);
        onSortCNPM("order by CNPM DESC","order by CNPM ASC", sortCNPM);
        onSortKTLT("order by KTLT DESC","order by KTLT ASC", sortKTLT);
        onSortDTB10("order by ĐTB_hệ_10 DESC","order by ĐTB_hệ_10 ASC", sortDTB10);
        onSortDTB4("order by ĐTB_hệ_10 DESC","order by ĐTB_hệ_10 ASC", sortDTB4);
        onSortDC("order by ĐTB_hệ_10 DESC","order by ĐTB_hệ_10 ASC", sortDC);
        onSortXL("order by ĐTB_hệ_10 DESC","order by ĐTB_hệ_10 ASC", sortXL);
        onSortKQ("order by ĐTB_hệ_10 DESC","order by ĐTB_hệ_10 ASC", sortKQ);
    }

    public String returnQuerry(){
        return "where MSSV like N'%"+searchTF.getText()+"%' "+
                " OR Họ_và_Tên like N'"+searchTF.getText()+"%'" +
                " OR Giới_tính like N'"+searchTF.getText()+"%'" +
                " OR THVP like N'"+searchTF.getText()+"%'" +
                " OR DTCB like N'"+searchTF.getText()+"%'" +
                " OR GDTC like N'"+searchTF.getText()+"%'" +
                " OR CNPM like N'"+searchTF.getText()+"%'" +
                " OR KTLT like N'"+searchTF.getText()+"%'" +
                " OR ĐTB_hệ_10 like N'"+searchTF.getText()+"%'" +
                " OR ĐTB_hệ_4 like N'"+searchTF.getText()+"%'" +
                " OR Điểm_Chữ like N'"+searchTF.getText()+"%'" +
                " OR Xếp_Loại like N'"+searchTF.getText()+"%'" +
                " OR Kết_Quả like N'"+searchTF.getText()+"%' " ;
    }

    public void onSortMSSV(String querry1,String querry2,Button sort) {
        ObservableList<StudentScore> sortStudentScores = FXCollections.observableArrayList();
        sort.setOnAction(e->{
            sortStudentScores.clear();
            String searchQuery="";
            if(!(searchTF.getText().trim().equals(""))){
                searchQuery=returnQuerry();
            }
            if(indexMSSV%2!=0){
                indexMSSV++;
                searchQuery +=querry1;
            }else{
                indexMSSV++;
                searchQuery += querry2;
            }
            onSort(searchQuery,sortStudentScores);

        });
    }

    public void onSortFN(String querry1,String querry2,Button sort) {
        ObservableList<StudentScore> sortStudentScores = FXCollections.observableArrayList();
        sort.setOnAction(e->{
            sortStudentScores.clear();
            String searchQuery="";
            if(!(searchTF.getText().trim().equals(""))){
                searchQuery=returnQuerry();
            }
            if(indexFN%2!=0){
                indexFN++;
                searchQuery +=querry1;
            }else{
                indexFN++;
                searchQuery += querry2;
            }
            onSort(searchQuery,sortStudentScores);

        });
    }

    public void onSortGT(String querry1,String querry2,Button sort) {
        ObservableList<StudentScore> sortStudentScores = FXCollections.observableArrayList();
        sort.setOnAction(e->{
            sortStudentScores.clear();
            String searchQuery="";
            if(!(searchTF.getText().trim().equals(""))){
                searchQuery=returnQuerry();
            }
            if(indexGT%2!=0){
                indexGT++;
                searchQuery +=querry1;
            }else{
                indexGT++;
                searchQuery += querry2;
            }
            onSort(searchQuery,sortStudentScores);

        });
    }

    public void onSortTHVP(String querry1,String querry2,Button sort) {
        ObservableList<StudentScore> sortStudentScores = FXCollections.observableArrayList();
        sort.setOnAction(e->{
            sortStudentScores.clear();
            String searchQuery="";
            if(!(searchTF.getText().trim().equals(""))){
                searchQuery=returnQuerry();
            }
            if(indexTHVP%2!=0){
                indexTHVP++;
                searchQuery +=querry1;
            }else{
                indexTHVP++;
                searchQuery += querry2;
            }
            onSort(searchQuery,sortStudentScores);

        });
    }

    public void onSortDTCB(String querry1,String querry2,Button sort) {
        ObservableList<StudentScore> sortStudentScores = FXCollections.observableArrayList();
        sort.setOnAction(e->{
            sortStudentScores.clear();
            String searchQuery="";
            if(!(searchTF.getText().trim().equals(""))){
                searchQuery=returnQuerry();
            }
            if(indexMSSV%2!=0){
                indexDTCB++;
                searchQuery +=querry1;
            }else{
                indexDTCB++;
                searchQuery += querry2;
            }
            onSort(searchQuery,sortStudentScores);

        });
    }

    public void onSortGDTC(String querry1,String querry2,Button sort) {
        ObservableList<StudentScore> sortStudentScores = FXCollections.observableArrayList();
        sort.setOnAction(e->{
            sortStudentScores.clear();
            String searchQuery="";
            if(!(searchTF.getText().trim().equals(""))){
                searchQuery=returnQuerry();
            }
            if(indexGDTC%2!=0){
                indexGDTC++;
                searchQuery +=querry1;
            }else{
                indexGDTC++;
                searchQuery += querry2;
            }
            onSort(searchQuery,sortStudentScores);

        });
    }

    public void onSortCNPM(String querry1,String querry2,Button sort) {
        ObservableList<StudentScore> sortStudentScores = FXCollections.observableArrayList();
        sort.setOnAction(e->{
            sortStudentScores.clear();
            String searchQuery="";
            if(!(searchTF.getText().trim().equals(""))){
                searchQuery=returnQuerry();
            }
            if(indexCNPM%2!=0){
                indexCNPM++;
                searchQuery +=querry1;
            }else{
                indexCNPM++;
                searchQuery += querry2;
            }
            onSort(searchQuery,sortStudentScores);

        });
    }

    public void onSortKTLT(String querry1,String querry2,Button sort) {
        ObservableList<StudentScore> sortStudentScores = FXCollections.observableArrayList();
        sort.setOnAction(e->{
            sortStudentScores.clear();
            String searchQuery="";
            if(!(searchTF.getText().trim().equals(""))){
                searchQuery=returnQuerry();
            }
            if(indexKTLT%2!=0){
                indexKTLT++;
                searchQuery +=querry1;
            }else{
                indexKTLT++;
                searchQuery += querry2;
            }
            onSort(searchQuery,sortStudentScores);

        });
    }

    public void onSortDTB10(String querry1,String querry2,Button sort) {
        ObservableList<StudentScore> sortStudentScores = FXCollections.observableArrayList();
        sort.setOnAction(e->{
            sortStudentScores.clear();
            String searchQuery="";
            if(!(searchTF.getText().trim().equals(""))){
                searchQuery=returnQuerry();
            }
            if(indexDTB10%2!=0){
                indexDTB10++;
                searchQuery +=querry1;
            }else{
                indexDTB10++;
                searchQuery += querry2;
            }
            onSort(searchQuery,sortStudentScores);

        });
    }

    public void onSortDTB4(String querry1,String querry2,Button sort) {
        ObservableList<StudentScore> sortStudentScores = FXCollections.observableArrayList();
        sort.setOnAction(e->{
            sortStudentScores.clear();
            String searchQuery="";
            if(!(searchTF.getText().trim().equals(""))){
                searchQuery=returnQuerry();
            }
            if(indexDTB4%2!=0){
                indexDTB4++;
                searchQuery +=querry1;
            }else{
                indexDTB4++;
                searchQuery += querry2;
            }
            onSort(searchQuery,sortStudentScores);

        });
    }

    public void onSortDC(String querry1,String querry2,Button sort) {
        ObservableList<StudentScore> sortStudentScores = FXCollections.observableArrayList();
        sort.setOnAction(e->{
            sortStudentScores.clear();
            String searchQuery="";
            if(!(searchTF.getText().trim().equals(""))){
                searchQuery=returnQuerry();
            }
            if(indexDC%2!=0){
                indexDC++;
                searchQuery +=querry1;
            }else{
                indexDC++;
                searchQuery += querry2;
            }
            onSort(searchQuery,sortStudentScores);

        });
    }

    public void onSortXL(String querry1,String querry2,Button sort) {
        ObservableList<StudentScore> sortStudentScores = FXCollections.observableArrayList();
        sort.setOnAction(e->{
            sortStudentScores.clear();
            String searchQuery="";
            if(!(searchTF.getText().trim().equals(""))){
                searchQuery=returnQuerry();
            }
            if(indexXL%2!=0){
                indexXL++;
                searchQuery +=querry1;
            }else{
                indexXL++;
                searchQuery += querry2;
            }
            onSort(searchQuery,sortStudentScores);

        });
    }

    public void onSortKQ(String querry1,String querry2,Button sort) {
        ObservableList<StudentScore> sortStudentScores = FXCollections.observableArrayList();
        sort.setOnAction(e->{
            sortStudentScores.clear();
            String searchQuery="";
            if(!(searchTF.getText().trim().equals(""))){
                searchQuery=returnQuerry();
            }
            if(indexKQ%2!=0){
                indexKQ++;
                searchQuery +=querry1;
            }else{
                indexKQ++;
                searchQuery += querry2;
            }
            onSort(searchQuery,sortStudentScores);

        });
    }

    public void onSort(String searchQuery, ObservableList<StudentScore> sortStudentScores){
        try {
            String querry="select * from StudentScores "+searchQuery;
            Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=StudentScores;user=SA;password=123456;encrypt=true;trustServerCertificate=true;");
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(querry);
            while (rs.next()) {
                String queryMSSV = rs.getString("MSSV");
                String queryFN = rs.getString("Họ_và_Tên");
                String queryGender = rs.getString("Giới_tính");
                double queryTHVP = Double.parseDouble(rs.getString("THVP"));
                double queryDTCB = Double.parseDouble(rs.getString("DTCB"));
                double queryGDTC = Double.parseDouble(rs.getString("GDTC"));
                double queryCNPM = Double.parseDouble(rs.getString("CNPM"));
                double queryKTLT = Double.parseDouble(rs.getString("KTLT"));
                sortStudentScores.add(new StudentScore(queryMSSV, queryFN, queryGender, queryTHVP, queryDTCB, queryGDTC, queryCNPM, queryKTLT));

            }
            tableStudent.setItems(sortStudentScores);


        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void totalSV(Label text) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=StudentScores;user=SA;password=123456;encrypt=true;trustServerCertificate=true;");
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM StudentScores");
        int result = 0;
        while(rs.next()){
            result=Integer.parseInt(rs.getString(1));
        }

        text.setText("Tổng số sinh viên: "+result);
    }
    public void setStyleCBB(){
        combatKH.setItems(listKH);
        combatKH.getSelectionModel().selectFirst();
        combatKH.setStyle("-fx-text-fill: black;\n" +
                "    -fx-font: 13px \"System\";\n");

        combatK.setItems(listK);
        combatK.getSelectionModel().selectFirst();
        combatK.setStyle("-fx-text-fill: black;\n" +
                "    -fx-font: 13px \"System\";\n");

        combatN.setItems(listN);
        combatN.getSelectionModel().selectFirst();
        combatN.setStyle("-fx-text-fill: black;\n" +
                "    -fx-font: 13px \"System\";\n");

        buttonHK.setItems(listHK);
        buttonHK.getSelectionModel().selectFirst();
        buttonHK.setStyle("-fx-text-fill: black;\n" +
                "    -fx-font: 13px \"System\";\n");
    }

    public void makeFadeInTransition(){
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(100));
        fadeTransition.setNode(container);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        fadeTransition.play();
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
        KeyFrame kf = new KeyFrame(Duration.seconds(1),kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(e->{
            parentContainer.getChildren().remove(container);
        });
        timeline.play();

    }

}
