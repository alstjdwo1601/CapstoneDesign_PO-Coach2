package com.example.netwod;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class MainActivity extends AppCompatActivity {

    // FrameLayout에 각 메뉴의 Fragment를 바꿔 줌
    //UserInfo user;

    ExcelScrapper excelscrapper;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    // 4개의 메뉴에 들어갈 Fragment들
    public Menu1Fragment menu1Fragment = new Menu1Fragment();
    private Menu2Fragment menu2Fragment = new Menu2Fragment();
    private Menu3Fragment menu3Fragment = new Menu3Fragment();
    private Menu4Fragment menu4Fragment = new Menu4Fragment();
    private WodselectFragment wodselectFragment = new WodselectFragment(); //이건 필요 없는거임지금은
    private WodlistFragment wodlistFragment = new WodlistFragment();
    private WodgenerateFragment wodgenerateFragment = new WodgenerateFragment();
    private HelpFragment helpFragment = new HelpFragment();
    public ChangeinfoFragment changeinfoFragment = new ChangeinfoFragment();
    public ArrayList<String> list = new ArrayList<String>();

    LinearLayout selectwodlayout;

    /*
    1~4 하단 네비게이션 메뉴 화면
    1:홈화면
    2:와드 시작 화면
    3:기록 화면
    4:커뮤니티 화면
    5:2-와드 목록 화면
    6:2-와드 생성 화면
    7:2-정보 화면
    */
    public void onFragmentChange(int index) {

        switch (index) {
            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, menu1Fragment).commit();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, menu2Fragment).commit();
                break;
            case 3:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, menu3Fragment).commit();
                break;
            case 4:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, menu4Fragment).commit();
                break;
            case 5:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, wodlistFragment).commit();
                break;
            case 6:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, wodgenerateFragment).commit();
                break;
            case 7:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, helpFragment).commit();
                break;
            case 8:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, changeinfoFragment).commit();
                break;
            case 9:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, wodselectFragment).commit();
                break;
            case 10:

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, wodselectFragment).commit();
                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        //user = new UserInfo();




        String sdCardPath = null;
        sdCardPath = Environment.getExternalStorageDirectory().getAbsolutePath();




        File file = new File(sdCardPath+"/Download/netwodtemplate.xls" );

       // File tmpfile = new File(sdCardPath+"/Download/temporary.xls" );


        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //excelscrapper=new ExcelScrapper(sdCardPath,fileInputStream);
        excelscrapper=new ExcelScrapper(sdCardPath);
        excelscrapper.readExcel();

            //excelscrapper.readExcel(fileInputStream);


        //testexcel();









        //user.setPullUpBar(false);
        //user.setBarbell(true);
        //user.setKettlebell(false);
        //user.setBody(true);
        //user.setDumbbell(false);
        //user.setPullUpBar(false);
        //user.setBox(true);
        //user.setJumprope(false);
        //user.setWallBall(false);
        //System.out.println("메인엑티비티에서"+excelscrapper.userinfo.wodrecord.wodlist.get(0).getWODname());

        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        // 첫 화면 지정
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, menu1Fragment).commitAllowingStateLoss();
        //fragment 요소마다 하나씩
        //fragment 1 홈
        //fragment 2 와드


        // bottomNavigationView의 아이템이 선택될 때 호출될 리스너 등록
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.navigation_menu1: {
                        //transaction.replace(R.id.frame_layout, menu1Fragment).commitAllowingStateLoss();
                        onFragmentChange(1);

                        break;
                    }
                    case R.id.navigation_menu2: {
                        onFragmentChange(2);
                        break;
                    }
                    case R.id.navigation_menu3: {
                        onFragmentChange(3);
                        break;
                    }
                    case R.id.navigation_menu4: {
                        onFragmentChange(4);
                        break;
                    }
                }

                return true;
            }
        });
    }







    class ExcelScrapper {
        //변수들 특히 어레이리스트 1.동적할당 2.private썼으면 게터세터
        //어플 키면 excelscrapper가 엑셀 읽고 Userinfo를 하나 줌. 난 그 userinfo.리스트 이걸로만 접근할거임
        UserInfo userinfo=new UserInfo();
        String sdCardPath;
        FileInputStream is=null;
        File file;
        public ExcelScrapper(String s){
            this.sdCardPath=s;


        }

        class UserInfo {

            private WODrecord wodrecord; //템플릿에 있는 개인이 실제로 한 기록
            private ArrayList<WOD> userwodlist; //와드 선택할때 리스트(뭐 할지 고를지)

            private String UserName;
            private String UserAge;
            private String UserWeight;
            private String UserHeight;
            private String User_NumOfTraining;

            public UserInfo(){
                this.wodrecord=new WODrecord(); //템플릿에 있는 개인이 실제로 한 기록
                this.userwodlist=new ArrayList<WOD>(); //와드 선택할때 리스트(뭐 할지 고를지)

            }


            //운동 기구가 있는지 없는지
            private boolean Dumbbell;
            private boolean Body;
            private boolean Kettlebell;
            private boolean Barbell;
            private boolean WallBall;
            private boolean Box;
            private boolean Jumprope;
            private boolean PullUpBar;

            public WODrecord getWodrecord() {
                return wodrecord;
            }

            public void setWodrecord(WODrecord wodrecord) {
                this.wodrecord = wodrecord;
            }

            public ArrayList<WOD> getUserwodlist() {
                return userwodlist;
            }

            public void setUserwodlist(ArrayList<WOD> userwodlist) {
                this.userwodlist = userwodlist;
            }


            public String getUserName() {
                return UserName;
            }

            public void setUserName(String userName) {
                UserName = userName;
            }

            public String getUserAge() {
                return UserAge;
            }

            public void setUserAge(String userAge) {
                UserAge = userAge;
            }

            public String getUserWeight() {
                return UserWeight;
            }

            public void setUserWeight(String userWeight) {
                UserWeight = userWeight;
            }

            public String getUserHeight() {
                return UserHeight;
            }

            public void setUserHeight(String userHeight) {
                UserHeight = userHeight;
            }

            public String getUser_NumOfTraining() {
                return User_NumOfTraining;
            }

            public void setUser_NumOfTraining(String user_NumOfTraining) {
                User_NumOfTraining = user_NumOfTraining;
            }

            public boolean isDumbbell() {
                return Dumbbell;
            }

            public void setDumbbell(boolean dumbbell) {
                Dumbbell = dumbbell;
            }

            public boolean isBody() {
                return Body;
            }

            public void setBody(boolean body) {
                Body = body;
            }

            public boolean isKettlebell() {
                return Kettlebell;
            }

            public void setKettlebell(boolean kettlebell) {
                Kettlebell = kettlebell;
            }

            public boolean isBarbell() {
                return Barbell;
            }

            public void setBarbell(boolean barbell) {
                Barbell = barbell;
            }

            public boolean isWallBall() {
                return WallBall;
            }

            public void setWallBall(boolean wallBall) {
                WallBall = wallBall;
            }

            public boolean isBox() {
                return Box;
            }

            public void setBox(boolean box) {
                Box = box;
            }


            public boolean isJumprope() {
                return Jumprope;
            }

            public void setJumprope(boolean jumprope) {
                Jumprope = jumprope;
            }

            public boolean isPullUpBar() {
                return PullUpBar;
            }

            public void setPullUpBar(boolean pullUpBar) {
                PullUpBar = pullUpBar;
            }

        }


        class WOD{
            private String WODname;//ex) FRAN,고성주의 와드
            private String WODlevel;
            private String WODtype; //포타임, 암랩 등등

            //데드21,월볼21 + 데드15,월볼15 와드 예시
            private ArrayList<String> movement;//{데드,월볼,데드,월볼,데드,월볼}
            private ArrayList<String> movementnum;//={21,21,21,15,15,15};
            private ArrayList<String> weightlist;//각각 무게
            private ArrayList<String> equipment;// 사용한 기구

            public WOD(){
                this.movement=new ArrayList<String>();
                this.movementnum=new ArrayList<String>();
                this.weightlist=new ArrayList<String>();
                this.equipment=new ArrayList<String>();
            }



            public String getWODlevel() {
                return WODlevel;
            }

            public void setWODlevel(String WODlevel) {
                this.WODlevel = WODlevel;
            }

            public String getWODname() {
                return WODname;
            }

            public void setWODname(String WODname) {
                this.WODname = WODname;
            }

            public String getWODtype() {
                return WODtype;
            }

            public void setWODtype(String WODtype) {
                this.WODtype = WODtype;
            }

            public ArrayList<String> getMovement() {
                return movement;
            }

            public void setMovement(ArrayList<String> movement) {
                this.movement = movement;
            }

            public ArrayList<String> getMovementnum() {
                return movementnum;
            }

            public void setMovementnum(ArrayList<String> movementnum) {
                this.movementnum = movementnum;
            }



            public ArrayList<String> getWeightlist() {
                return weightlist;
            }

            public void setWeightlist(ArrayList<String> weightlist) {
                this.weightlist = weightlist;
            }



            public ArrayList<String> getEquipment() {
                return equipment;
            }

            public void setEquipment(ArrayList<String> equipment) {
                this.equipment = equipment;
            }




        }
        class WODrecord{
            private ArrayList<WOD> wodlist; //{프란,신디,민성재,김정훈}
            private ArrayList<String> scorelist; //{"70","80","75","60"}
            private ArrayList<String> recordlist; //{"4:32","3:53","2:10","6:34"}


            public WODrecord(){
                wodlist=new ArrayList<WOD>();
                scorelist=new ArrayList<String>();
                recordlist=new ArrayList<String>();

            }

            public ArrayList<WOD> getWodlist() {
                return wodlist;
            }

            public void setWodlist(ArrayList<WOD> wodlist) {
                this.wodlist = wodlist;
            }

            public ArrayList<String> getScorelist() {
                return scorelist;
            }

            public void setScorelist(ArrayList<String> scorelist) {
                this.scorelist = scorelist;
            }

            public ArrayList<String> getRecordlist() {
                return recordlist;
            }

            public void setRecordlist(ArrayList<String> recordlist) {
                this.recordlist = recordlist;
            }


        }





        //dfsd

        public ArrayList<String> equipment = new ArrayList<String>();
        public ArrayList<ArrayList<String>> schedule = new ArrayList<ArrayList<String>>();
        public ArrayList<ArrayList<String>> record = new ArrayList<ArrayList<String>>();


        public void writeExcel() throws IOException, BiffException, WriteException {
            file=new File(sdCardPath+"/Download/netwodtemplate.xls" );
            //File file = new File(sdCardPath+"/Download/netwodtemplate.xls" );

            try {
                is = new FileInputStream(file);
                System.out.println("write에서 인풋스트림 생성 성공");
            } catch (FileNotFoundException e) {
                System.out.println("write에서 인풋스트림 생성 불가");
                e.printStackTrace();
            }

            Workbook originworkbook = null;
            //WritableWorkbook f=Workbook.createWorkbook(os,workbook);

            //String p = System.getProperty("user.dir");

            originworkbook = Workbook.getWorkbook(is);



            WritableWorkbook wworkbook = null;
            try {
                wworkbook = Workbook.createWorkbook(new File(sdCardPath+"/Download/netwodtemplate.xls" ),originworkbook);

            } catch (IOException e) {

                e.printStackTrace();
            }
            //WritableSheet sheet = wworkbook.createSheet("Sheet1", 0);
            WritableSheet sheet = wworkbook.getSheet(1);

            jxl.write.WritableCellFormat  format= new WritableCellFormat();
            jxl.write.WritableCellFormat  format0= new WritableCellFormat();
            jxl.write.Label label = null;
            jxl.write.Blank blank = null;
            WritableCell fcell= sheet.getWritableCell(0,0);
            System.out.println("00cell타입:" + fcell.getType());
            //label = new jxl.write.Label(0,0,"넷와드 템플릿",format);
            WritableCell heightcell= sheet.getWritableCell(13,8);
            System.out.println("cell.label:"+CellType.LABEL);
            System.out.println("heightcell타입:" + heightcell.getType());

            if (heightcell.getType() == CellType.NUMBER) {
                jxl.write.Number n=(jxl.write.Number) heightcell;
               // Label heightlabel = (Label) heightcell;
                //l.setString("")
                n.setValue(Integer.parseInt(userinfo.getUserHeight()));
                //heightlabel.setString(this.userinfo.getUserHeight());
                System.out.println("수정키:" + this.userinfo.getUserHeight());

                //sheet.addCell(l);

            }
            WritableCell namecell= sheet.getWritableCell(13,5);
            System.out.println("namecell타입:" + namecell.getType());
            if (namecell.getType() == CellType.LABEL) {

                Label namelabel = (Label) namecell;
                //l.setString("")
                namelabel.setString(this.userinfo.getUserName());
                System.out.println("수정유저이름:" + this.userinfo.getUserName());
                //sheet.addCell(l);
            }




            try {
                wworkbook.write();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                wworkbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }
            is.close();
            originworkbook.close();



        }
        public void readExcel(){
            file=new File(sdCardPath+"/Download/netwodtemplate.xls" );
            //File file = new File(sdCardPath+"/Download/netwodtemplate.xls" );

            try {
                is = new FileInputStream(file);
                System.out.println("rea에서 인풋스트림 생성 성공");
            } catch (FileNotFoundException e) {
                System.out.println("rea에서 인풋스트림 생성 불가");
                e.printStackTrace();
            }


            try {


                 // TODO : use is(InputStream).
                Workbook workbook = null;
                //WritableWorkbook f=Workbook.createWorkbook(os,workbook);
                Sheet sheet;
                //String p = System.getProperty("user.dir");

                workbook = Workbook.getWorkbook(is);
                if(workbook==null){
                System.out.println("워크북이 NULL");

                }
                //InputStream df=new FileInputStream("")
                // String p=System.getProperty("user.dir");




                // TODO : use is(InputStream).









                //netwodtemplate.xls 읽기
                if(workbook != null){
                    System.out.println("워크북이 있다");

                    sheet = workbook.getSheet(1);

                    if(sheet != null) {


                        // 유저 정보 엑셀에서 읽어오는 부분
                        String name = sheet.getCell(13, 5).getContents();
                        String age = sheet.getCell(13, 6).getContents();
                        String weight = sheet.getCell(13, 7).getContents();
                        String height = sheet.getCell(13, 8).getContents();
                        String NumofTraining = sheet.getCell(13, 9).getContents();

                        this.userinfo.setUserName(name);
                        this.userinfo.setUserWeight(weight);
                        this.userinfo.setUserHeight(height);
                        //this.user_info.add(age);
                        //this.user_info.add(NumofTraining);




                        //운동 스케줄 엑셀에서 읽어오는 부분
                        //int nMaxColumn = 9;
                        int nRowStartIndex = 9;
                        int nRowEndIndex = 150;
                        int nColumnStartIndex = 2;
                        int wodrow;
                        int wodcol;
                        //int nColumnEndIndex = 10;
                        //무브먼트(4,X) ,
                        while(sheet.getCell(nColumnStartIndex, nRowStartIndex).getContents()!=""){ //와드 두당한번씩돈다

                        this.userinfo.wodrecord.recordlist.add(sheet.getCell(9, nRowStartIndex).getContents());
                        this.userinfo.wodrecord.scorelist.add(sheet.getCell(10, nRowStartIndex).getContents());
                            wodrow=nRowStartIndex;
                            wodcol=4; //movement
                            WOD wod=new WOD();

                            wod.setWODname(sheet.getCell(2, nRowStartIndex).getContents()            );
                            wod.setWODtype(sheet.getCell(3, nRowStartIndex).getContents()    );
                            wod.setWODlevel(sheet.getCell(8, nRowStartIndex).getContents()    );

                            while(sheet.getCell(4, wodrow).getContents()!=""){
                                //System.out.println("NULL인가"+sheet.getCell(2, nRowStartIndex).getContents() );
                                wod.getMovement().add(sheet.getCell(4, wodrow).getContents());

                                wod.getEquipment().add(sheet.getCell(5, wodrow).getContents());
                                wod.getMovementnum().add(sheet.getCell(6, wodrow).getContents());
                                wod.getWeightlist().add(sheet.getCell(7, wodrow).getContents());


                                wodrow+=1;
                            }
                            //System.out.println("와드네임"+sheet.getCell(2, nRowStartIndex).getContents() );
                            this.userinfo.wodrecord.wodlist.add(wod);

                        nRowStartIndex+=50;
                        }

                    }
                    else{
                        System.out.println("Sheet is null");
                    }
                }
                else{
                    System.out.println("Workbook is null");
                }



                //movement.xls 읽기


                //equipment.xls 엑셀 읽기



                //wodlist.xls 엑셀 읽기



                //userwodlist.xls 읽기


            workbook.close();
                is.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
                }
    }
}





