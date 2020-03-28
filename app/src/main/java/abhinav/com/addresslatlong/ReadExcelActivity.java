package abhinav.com.addresslatlong;

import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

import abhinav.com.addresslatlong.Adapters.AdapterFriendsList;
import abhinav.com.addresslatlong.Bean.Friend;

public class ReadExcelActivity extends AppCompatActivity implements View.OnClickListener
{
    /*static {
        System.setProperty(
                "org.apache.poi.javax.xml.stream.XMLInputFactory",
                "com.fasterxml.aalto.stax.InputFactoryImpl"
        );
        System.setProperty(
                "org.apache.poi.javax.xml.stream.XMLOutputFactory",
                "com.fasterxml.aalto.stax.OutputFactoryImpl"
        );
        System.setProperty(
                "org.apache.poi.javax.xml.stream.XMLEventFactory",
                "com.fasterxml.aalto.stax.EventFactoryImpl"
        );
    }*/

    String TAG = "main";
    Button btn_browse;
    TextView textpath;
    ListView listv_details;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_excel);

        btn_browse = (Button) findViewById(R.id.btn_browse);
        btn_browse.setOnClickListener(this);

        textpath = (TextView) findViewById(R.id.textpath);

        listv_details = (ListView) findViewById(R.id.listv_details);
    }

    public void readExcelFileFromAssests(String file_path)
    {
        try
        {
            InputStream myInput;

            File f = new File(file_path);
            myInput = new FileInputStream(f);

            // Create a POI File System object
            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);

            // Create a workbook using the File System
            //HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);

            // Get the first sheet from workbook
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);

            // We now need something to iterate through the cells.

            Iterator<Row> rowIter = mySheet.rowIterator();
            int rowno =0;

            ArrayList<Friend> al_friends = new ArrayList<>();
            al_friends.clear();

            while (rowIter.hasNext())
            {
                Log.e(TAG, " row no "+ rowno );
                HSSFRow myRow = (HSSFRow) rowIter.next();
                if(rowno!=0)
                {
                    Iterator<Cell> cellIter = myRow.cellIterator();
                    int colno =0;
                    String sno="", name="", dob="", age="";

                    while (cellIter.hasNext())
                    {
                        HSSFCell myCell = (HSSFCell) cellIter.next();
                        if (colno==0)
                        {
                            sno = myCell.toString();
                        }
                        else if (colno==1)
                        {
                            name = myCell.toString();
                        }
                        else if (colno==2)
                        {
                            dob = myCell.toString();
                        }
                        else if (colno==3)
                        {
                            age = myCell.toString();
                        }

                        colno++;
                    }

                    Friend friend = new Friend(sno,name,dob,age);
                    al_friends.add(friend);
                }

                rowno++;
            }

            AdapterFriendsList adapterFriendsList = new AdapterFriendsList(ReadExcelActivity.this, al_friends);
            listv_details.setAdapter(adapterFriendsList);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.btn_browse)
        {
//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.setType("*/*");
//            startActivityForResult(intent, 7);

            new MaterialFilePicker()
                    .withActivity(this)
                    .withRequestCode(1)
                    .withFilter(Pattern.compile(".*\\.xls")) // Filtering files and directories by file name using regexp
                    .withHiddenFiles(true) // Show hidden files and folders
                    .start();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case 1:
                if(resultCode==RESULT_OK)
                {
                    String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
                    // Do anything with file
                    textpath.setText(filePath);
                    readExcelFileFromAssests(filePath);
                }
                break;
        }
    }


}