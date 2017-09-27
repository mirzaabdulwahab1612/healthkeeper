package jankhan.healthkeeper;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by arifu on 6/22/2017.
 */

public class TestReport implements Serializable {
    Date date;
    boolean isdue;//make it private
    String result;
    String nameoftest;
    FileAttached fileAttached;


    public TestReport(String nameoftest) {
        this.isdue = true;
        this.nameoftest = nameoftest;
    }

    public void setResult(String result){
        this.isdue = false;
        this.date = new Date();
        this.result = result;

    }


    public void attachFile(Uri uri, UserData userData, Context applicationContext) throws Exception {
        if(this.fileAttached!=null){
            //delete the existing file....
        }


        String name=this.nameoftest;
        boolean attached=true;
        boolean uploaded=false;



        InputStream inputStream = applicationContext.getContentResolver().openInputStream(uri);
        File myFile = new File(applicationContext.getFilesDir(), name);

        try {
            Utitlity.copyFile(inputStream,myFile);
            this.fileAttached= new FileAttached(name,uploaded,attached);
        } catch (IOException e) {
            throw new Exception("Failed to store file.... io Exeption");
        }



    }
    public boolean isDue(){
        return isdue;
    }

    public class FileAttached {


        public FileAttached(String name, boolean uploaded, boolean evenAttached) {
            this.name = name;
            this.uploaded = uploaded;
            this.evenAttached = evenAttached;
        }

        String name;
        boolean uploaded;
        boolean evenAttached;

    }


}
