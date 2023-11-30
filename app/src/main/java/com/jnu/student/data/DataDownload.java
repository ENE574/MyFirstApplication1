package com.jnu.student.data;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DataDownload{

    public String download(String url_) {
        try {
            // 创建URL对象
            URL url = new URL(url_);

            // 打开连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 设置请求方法（GET、POST等）
            connection.setRequestMethod("GET");

            // 设置连接和读取超时时间（可选）
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            // 发起连接
            connection.connect();

            // 获取响应码
            int responseCode = connection.getResponseCode();

            // 如果响应码为200表示请求成功
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 获取输入流
                InputStream inputStream = connection.getInputStream();

                // 读取输入流中的数据
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                String responseData = stringBuilder.toString();

                // 关闭输入流和连接
                reader.close();
                inputStream.close();
                connection.disconnect();

                // 返回下载的数据
                return responseData;
            } else {
                // 处理请求失败的情况
                // ...
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    public ArrayList<ShopLocation> parseJsonObjects(String content) {
        ArrayList<ShopLocation> locations=new ArrayList<>();
        try {
            //这里的text就是上边获取到的数据，一个String.
            JSONObject jsonObject = new JSONObject(content);
            JSONArray jsonDatas = jsonObject.getJSONArray("shops");
            int length = jsonDatas.length();
            String test;
            for (int i = 0; i < length; i++) {
                JSONObject shopJson = jsonDatas.getJSONObject(i);
                ShopLocation shop = new ShopLocation();
                shop.setName(shopJson.getString("name"));
                shop.setLatitude(shopJson.getDouble("latitude"));
                shop.setLongitude(shopJson.getDouble("longitude"));
                shop.setMemo(shopJson.getString("memo"));
                locations.add(shop);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  locations;
    }
}
