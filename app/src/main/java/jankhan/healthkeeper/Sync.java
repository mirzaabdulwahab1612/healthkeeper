package jankhan.healthkeeper;

import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by arifu on 6/24/2017.
 */

public class Sync {

    File file;
    String filename = "savedData";
    Context context;

    public Sync(Context context) {
        this.context = context;
        file = new File(context.getFilesDir(), "temp");
    }

    void saveData(UserData userData) {
        try {

            //serialization doesn't work for the files in src folder somehow....use full path...
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file,false));
            out.writeObject(userData);
            out.close();
            Toast.makeText(context, "Stored", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    UserData getData(){
        try {
            UserData temp;
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            temp = (UserData) in.readObject();
            in.close();
            Toast.makeText(context, "Error Getting Data", Toast.LENGTH_LONG).show();
            fileIn.close();
            return temp;
        } catch (IOException i) {
            Toast.makeText(context, "IO Exeception", Toast.LENGTH_LONG).show();
            //i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            Toast.makeText(context, "Class not found", Toast.LENGTH_LONG).show();
            return null;
        }
    }


}
