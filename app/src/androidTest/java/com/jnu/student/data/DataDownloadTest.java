package com.jnu.student.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class DataDownloadTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void download() {
        DataDownload httpDataLoader=new DataDownload();
        String fileContent=httpDataLoader.download("http://file.nidama.net/class/mobile_develop/data/bookstore.json");
        assertTrue(fileContent.contains("\"name\": \"暨大珠海\","));
        assertTrue(fileContent.contains("\"memo\": \"珠海二城广场\""));
    }
    @Test
    public void parseJsonObjects() {
        DataDownload httpDataLoader=new DataDownload();
        String fileContent="{\n" +
                "  \"shops\": [{\n" +
                "    \"name\": \"暨大珠海\",\n" +
                "    \"latitude\": \"22.255925\",\n" +
                "    \"longitude\": \"113.541112\",\n" +
                "    \"memo\": \"暨南大学珠海校区\"\n" +
                "  },\n" +
                "    {\n" +
                "      \"name\": \"沃尔玛(前山店)\",\n" +
                "      \"latitude\": \"22.261365\",\n" +
                "      \"longitude\": \"113.532989\",\n" +
                "      \"memo\": \"沃尔玛(前山店)\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"明珠商业广场\",\n" +
                "      \"latitude\": \"22.251953\",\n" +
                "      \"longitude\": \"113.526421\",\n" +
                "      \"memo\": \"珠海二城广场\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        ArrayList<ShopLocation> locations= httpDataLoader.parseJsonObjects(fileContent);

        assertEquals(3,locations.size());
        assertEquals("暨大珠海",locations.get(0).getName());
        assertEquals(22.251953,locations.get(2).getLatitude(),1e-6);
        assertEquals(113.526421,locations.get(2).getLongitude(),1e-6);
        assertEquals("珠海二城广场",locations.get(2).getMemo());
    }
}