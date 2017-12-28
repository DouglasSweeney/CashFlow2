package com.doug.cashflow.controller;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doug.cashflow.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileReadActivity extends AppCompatActivity {
    private static final String TAG = "FileReadActivity";

    // Dirs in the assets dir
    public static final String LICENSE_DIRECTORY = "licenses";

    private static final String EXTRA_DIRECTORY = "directory";
    private static final String EXTRA_FILE_NAME = "filename";

    private static Integer numberOfLines = 0;

    public static Intent newIntent(Context packageContext, String directory, String filename) {

        Intent intent = new Intent(packageContext, FileReadActivity.class);
        intent.putExtra(EXTRA_DIRECTORY, directory);
        intent.putExtra(EXTRA_FILE_NAME, filename);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String directory;
        String filename;
        String contents;

        Log.d(TAG, "onCreate() called");

        LoginLab.setCurrentActivity(this);

        setContentView(R.layout.activity_file_read);

        directory = (String) getIntent().getSerializableExtra(EXTRA_DIRECTORY);
        filename = (String) getIntent().getSerializableExtra(EXTRA_FILE_NAME);

        TextView tv = (TextView)findViewById(R.id.filename);

        contents = readFileFromAssets(directory, filename);

int screenWidth = getResources().getDisplayMetrics().widthPixels;
Log.d(TAG, "screenWidth: " + screenWidth);
Paint paint = tv.getPaint();
List<String> strings = splitWordsIntoStringsThatFit(contents, screenWidth, paint);
tv.setText(TextUtils.join("\n", strings));
//tv.setLines(numberOfLines*3);
tv.setLines(strings.size());
tv.setText(contents);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.file_read_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Utils utils = new Utils();
        Log.d(TAG, "onOptionsItemSelected(MenuItem item)");
//        Log.d(TAG, "    item: " + item.toString());
//        Log.d(TAG, "    item.getItemId(): " + item.getItemId());
//        Log.d(TAG, "    R.id.account_403b_menu_item: " + R.id.account_403b_menu_item);
//        Log.d(TAG, "    R.id.output_account_403b_menu_item: " + R.id.output_account_403b_menu_item);

        switch (item.getItemId()) {
            case R.id.inputs:
                utils.goToInputsActivity();
                return true;

            case R.id.outputs:
                utils.goToOutputsActivity();
                return true;

            case R.id.login:
                utils.goToLoginActivity();
                return true;
        }

        return false;
    }

    private String readFileFromAssets(String directory, String filename) {
        AssetManager assets = this.getApplicationContext().getAssets();
        String[] filenames = null;
        String   output;

        try {
            filenames = assets.list(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (filenames != null) {
            for (int i = 0; i < filenames.length; i++) {
                Log.i(TAG, "    filenames[" + i + "]: <" + filenames[i] + ">");
            }
        }

        try {
            output = readFile(directory + "/" + filename);
        } catch (IOException e) {
            e.printStackTrace();
            output = this.getResources().getString(R.string.error_file_not_found);
        }

        return output;
    }

    private String readFile(String pathname) throws IOException {
        String str;
        StringBuilder buf = new StringBuilder();
        InputStream file = getAssets().open(pathname);
        BufferedReader in = new BufferedReader(new InputStreamReader(file, "UTF-8"));

        while ((str=in.readLine()) != null) {
            str = removeLeadingSpaces(str);
            buf.append(str);
            numberOfLines++;
        }

        in.close();

        return buf.toString();
    }

    private String removeLeadingSpaces(String str) {

        while (str.length() > 0 && str.charAt(0) == ' ') {
            str = str.substring(1, str.length());
//            Log.d(TAG, "str: <" + str + ">");
        }

        return str;
    }

    public ArrayList<String> splitWordsIntoStringsThatFit(String source, int maxWidthPix, Paint paint) {
        ArrayList<String> result = new ArrayList<>();
        ArrayList<String> currentLine = new ArrayList<>();
        String[] sources = source.split("\\s");

        for (String chunk : sources) {
            if (paint.measureText(chunk) < maxWidthPix) {
                processFitChunk(maxWidthPix, paint, result, currentLine, chunk);
            } else {
                List<String> splitChunk = splitIntoStringsThatFit(chunk, maxWidthPix, paint);
                for (String chunkChunk : splitChunk) {
                    processFitChunk(maxWidthPix, paint, result, currentLine, chunkChunk);
                }
            }
        }

        if (!currentLine.isEmpty()) {
            result.add(TextUtils.join(" ", currentLine));
        }

        return result;
    }

    private List<String> splitIntoStringsThatFit(String source, float maxWidthPix, Paint paint) {
        if (TextUtils.isEmpty(source) || paint.measureText(source) < maxWidthPix) {
            return Arrays.asList(source);
        }

        ArrayList<String> result = new ArrayList<>();
        int start = 0;
        for (int i = 1; i<source.length(); i++) {
            String substr = source.substring(start, i);

            if (paint.measureText(substr) >= maxWidthPix) {
                String fits = source.substring(start, i - 1);
                result.add(fits);
                start = i - 1;
            }

            if (i == source.length()) {
                String fits = source.substring(start, i);
                result.add(fits);
            }
        }

        return result;
    }

    private void processFitChunk(float maxWidth, Paint paint, ArrayList<String> result,
                                 ArrayList<String> currentLine, String chunk) {
        currentLine.add(chunk);
        String currentLineStr = TextUtils.join(" ", currentLine);
        if (paint.measureText(currentLineStr) > maxWidth) {
            currentLine.remove(currentLine.size() - 1);
            result.add(TextUtils.join(" ", currentLine));
            currentLine.clear();
            currentLine.add(chunk);
        }
    }
}
