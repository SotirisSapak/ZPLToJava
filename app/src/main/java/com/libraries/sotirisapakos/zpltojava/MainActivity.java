package com.libraries.sotirisapakos.zpltojava;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.libraries.sotirisapakos.zpljava.config.LabelSize;
import com.libraries.sotirisapakos.zpljava.templates.ZPLJavaTemplates;
import com.libraries.sotirisapakos.zpljava.components.ZPLJavaLabel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create a new label
        // label configuration: {width: 3in, height: 2in, size: 8dpmm} -> (in = inches)
        ZPLJavaLabel label = new ZPLJavaLabel(3, 2, LabelSize._8DPMM);

        /*
            set up one of the ready templates...Print result to log
            Template configuration:
            {
                label:      label above,
                title:      "Give some title",
                subtitle:   "Πατάτες τηγανητές (English -> Fried potatoes) -- check utf8 encoding",
                info:       "1200 kg",
                barcode:    "1234546789"
            }
         */
        Log.w("LABEL#1: ", "\n" +
                ZPLJavaTemplates.getTemplate2(label,
                        "Give some title",
                        "Πατάτες τηγανητές",
                        "1200 kg",
                        "1234546789"));
        Log.w("----------------------",
                "----------------------------------------------\n");

    }
}