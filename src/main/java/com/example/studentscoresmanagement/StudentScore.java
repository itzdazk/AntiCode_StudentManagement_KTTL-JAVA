package com.example.studentscoresmanagement;

import javafx.scene.control.Button;

public class StudentScore {
    private String MSSV,FullName,Gender,DC,XL,KQ;
    private double  THVP,DTCB,GDTC,CNPM,KTLT,DTB10,DTB4;

    public StudentScore(){

    }

    public StudentScore(String MSSV,String FullName,String Gender,double THVP,double DTCB,double GDTC,double CNPM,double KTLT){
        this.MSSV=MSSV;
        this.FullName=FullName;
        this.Gender=Gender;
        this.THVP=THVP;
        this.DTCB=DTCB;
        this.GDTC=GDTC;
        this.CNPM=CNPM;
        this.KTLT=KTLT;
        this.DTB10=Math.floor(((this.THVP+this.DTCB+this.GDTC+this.CNPM+this.KTLT)/5) * 10) / 10;
        if(this.DTB10 <= 10 && this.DTB10 >=9 ){
           this.DTB4=4;
            this.DC="A+";
            this.XL="Xuất Sắc";
            this.KQ="Đạt";
       }else if(this.DTB10 >=8.5 && this.DTB10 <9){
            this.DTB4=3.7;
            this.DC="A";
            this.XL="Giỏi";
            this.KQ="Đạt";
       }
        else if(this.DTB10 >=8 && this.DTB10 <8.5){
            this.DTB4=3.5;
            this.DC="B+";
            this.XL="Khá Giỏi";
            this.KQ="Đạt";
       }else if(this.DTB10 >=7 && this.DTB10 <8){
            this.DTB4=3;
            this.DC="B";
            this.XL="Khá";
            this.KQ="Đạt";
        }else if(this.DTB10 >=6.5 && this.DTB10 <7){
            this.DTB4=2.5;
            this.DC="C+";
            this.XL="TB Khá";
            this.KQ="Đạt";
       }
        else if(this.DTB10 >=5.5 && this.DTB10 <6.5){
            this.DTB4=2;
            this.DC="C";
            this.XL="TB";
            this.KQ="Đạt";
        }else if(this.DTB10 >=5 && this.DTB10 <5.5){
           this.DTB4=1.5;
            this.DC="D+";
            this.XL="TB Yếu";
            this.KQ="Đạt";
        }
        else if(this.DTB10 >=4 && this.DTB10 <5){
            this.DTB4=1;
            this.DC="D";
            this.XL="Yếu";
            this.KQ="Không Đạt";
        }else{
            this.DTB4=0;
            this.DC="F";
            this.XL="Kém";
            this.KQ="Không Đạt";
        }

    }

    public void update(){
        this.setDTB10(Math.floor(((this.THVP+this.DTCB+this.GDTC+this.CNPM+this.KTLT)/5) * 10) / 10);
        if(this.getDTB10() <= 10 && this.getDTB10() >=9 ){
            this.setDTB4(4);
            this.setDC("A+");
            this.setXL("Xuất Sắc");
            this.setKQ("Đạt");
        }else if(this.getDTB10() >=8.5 && this.getDTB10() <9){
            this.setDTB4(3.7);
            this.setDC("A");
            this.setXL("Giỏi");
            this.setKQ("Đạt");
        }
        else if(this.getDTB10() >=8 && this.getDTB10() <8.5){
            this.setDTB4(3.5);
            this.setDC("B+");
            this.setXL("Khá Giỏi");
            this.setKQ("Đạt");
        }else if(this.getDTB10() >=7 && this.getDTB10() <8){
            this.setDTB4(3);
            this.setDC("B");
            this.setXL("Khá");
            this.setKQ("Đạt");
        }else if(this.getDTB10() >=6.5 && this.getDTB10() <7){
            this.setDTB4(2.5);
            this.setDC("C+");
            this.setXL("TB Khá");
            this.setKQ("Đạt");
        }
        else if(this.getDTB10() >=5.5 && this.getDTB10() <6.5){
            this.setDTB4(2);
            this.setDC("C");
            this.setXL("TB");
            this.setKQ("Đạt");
        }else if(this.getDTB10() >=5 && this.getDTB10() <5.5){
            this.setDTB4(1.5);
            this.setDC("D+");
            this.setXL("TB Yếu");
            this.setKQ("Đạt");
        }
        else if(this.DTB10 >=4 && this.DTB10 <5){
            this.setDTB4(1);
            this.setDC("D");
            this.setXL("Yếu");
            this.setKQ("Không Đạt");
        }else{
            this.setDTB4(0);
            this.setDC("F");
            this.setXL("Kém");
            this.setKQ("Không Đạt");
        }

    }

    public String getXL() {
        return XL;
    }

    public void setXL(String XL) {
        this.XL = XL;
    }

    public double getDTB4() {
        return DTB4;
    }

    public double getDTB10() {
        return DTB10;
    }

    public void setDC(String DC) {
        this.DC = DC;
   }

   public String getDC() {
       return DC;
   }

    public void setDTB4(double DTB4) {
        this.DTB4 = DTB4;
    }

    public void setDTB10(double DTB10) {
        this.DTB10 = DTB10;
    }

    public double getCNPM() {
        return CNPM;
    }

    public double getDTCB() {
        return DTCB;
    }

    public double getGDTC() {
        return GDTC;
    }

    public double getKTLT() {
        return KTLT;
    }

    public double getTHVP() {
        return THVP;
    }

    public String getFullName() {
        return FullName;
    }

    public String getGender() {
        return Gender;
    }

    public String getMSSV() {
        return MSSV;
    }

    public void setCNPM(double CNPM) {
        this.CNPM = CNPM;
    }

    public void setDTCB(double DTCB) {
        this.DTCB = DTCB;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public void setGDTC(double GDTC) {
        this.GDTC = GDTC;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public void setKTLT(double KTLT) {
        this.KTLT = KTLT;
    }

    public void setMSSV(String MSSV) {
        this.MSSV = MSSV;
    }

    public void setTHVP(double THVP) {
        this.THVP = THVP;
    }

    public String getKQ() {
        return KQ;
    }

    public void setKQ(String KQ) {
        this.KQ = KQ;
    }
}
