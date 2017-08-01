package com.example.qthjen.newspaper;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ListView              lv;
    ArrayList<Attributes> mainView;
    CustomList            customList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.lv);

        mainView = new ArrayList<Attributes>();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, ActivitySecond.class);
                intent.putExtra("link", mainView.get(i).mLink);
                startActivity(intent);
            }
        });

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new readNewspaper().execute("http://vnexpress.net/rss/so-hoa.rss");
            }
        });

        /** sử dụng thư viện volley **/
//        String url = "http://vnexpress.net/rss/so-hoa.rss";
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                XMLDOMParser parser = new XMLDOMParser();
//                Document document = parser.getDocument(response);
//                NodeList nodeList = document.getElementsByTagName("item");
//                String title = "";
//                for ( int i = 0; i < nodeList.getLength(); i++) {
//                    Element element = (Element) nodeList.item(i);
//                    title = parser.getValue(element, "title");
//                    arrayTitle.add(title);
//                    arrayLink.add(parser.getValue(element,"link"));
//                }
//                adapter.notifyDataSetChanged();
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(MainActivity.this, "Error: " + error, Toast.LENGTH_LONG).show();
//            }
//        });
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);

    }

    /** sử dụng AsyncTask **/

    private class readNewspaper extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            StringBuilder stringBuilder = new StringBuilder();

            try {

                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while ( (line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }

                bufferedReader.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            XMLDOMParser parse = new XMLDOMParser();

            /** phân tích xml **/
            Document document = parse.getDocument(s);

            /** nodeList để chứa các item **/
            NodeList nodeList = document.getElementsByTagName("item");
            NodeList nodeListDescription = document.getElementsByTagName("description");
            /** đọc dữ liệu trong từng item **/
            String title   = "";
            String iconsrc = "";
            String link    = "";
            for ( int i = 0; i < nodeList.getLength(); i++) {

                Element element = (Element) nodeList.item(i);

                String cData = nodeListDescription.item(i + 1).getTextContent();
                Pattern pattern = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                Matcher matcher = pattern.matcher(cData);
                if ( matcher.find()) {
                    iconsrc = matcher.group(1);
                }

                title = parse.getValue(element, "title");
                link  = parse.getValue(element, "link");

                mainView.add(new Attributes(iconsrc, title, link));
                customList = new CustomList(MainActivity.this, mainView , R.layout.list_view);
                lv.setAdapter(customList);

            }
            customList.notifyDataSetChanged();
        }
    }

}
