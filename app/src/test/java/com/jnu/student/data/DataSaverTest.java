package com.jnu.student.data;
import static org.junit.Assert.*;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import com.jnu.student.R;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
public class DataSaverTest {
    DataSaver dataSaverBackup;
    ArrayList<ShopItem> shopItemsBackup;
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void save() {
        DataSaver dataSaver=new DataSaver();
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ArrayList<ShopItem> shopItems=new ArrayList<>();
        ShopItem shopItem=new ShopItem("测试",R.drawable.book_2);
        shopItems.add(shopItem);
        shopItem=new ShopItem("正常", R.drawable.book_no_name);
        shopItems.add(shopItem);
        dataSaver.Save(targetContext,shopItems);
        DataSaver dataLoader=new DataSaver();
        ArrayList<ShopItem> shopItemsRead=dataLoader.Load(targetContext);
        assertNotSame(shopItems,shopItemsRead);
        assertEquals(shopItems.size(),shopItemsRead.size());
        for(int index=0;index<shopItems.size();++index)
        {
            assertNotSame(shopItems.get(index),shopItemsRead.get(index));
            assertEquals(shopItems.get(index).getTitle(),shopItemsRead.get(index).getTitle());
            assertEquals(shopItems.get(index).getResourceId(),shopItemsRead.get(index).getResourceId());
        }
    }

    @Test
    public void load() {
    }
}