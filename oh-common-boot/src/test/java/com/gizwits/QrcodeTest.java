package com.gizwits;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gizwits.common.entity.SysStatusCode;
import com.gizwits.common.mock.ModelEntity;
import com.gizwits.common.qrcode.QRCodeFactory;
import com.gizwits.generic_test.Address;
import com.gizwits.generic_test.ApiResponse;
import com.gizwits.generic_test.Page;
import com.gizwits.generic_test.Person;
import com.gizwits.testbean.User;
import com.google.common.reflect.TypeToken;
import org.junit.Test;

import java.util.List;

/**
 * Created by feel on 2017/10/24.
 */
public class QrcodeTest {

    
    @Test
    public void genQrcode() throws Exception {

        String path = this.getClass().getClassLoader().getResource("./").getPath();
        String logo = this.getClass().getClassLoader().getResource("logo.png").getPath();
        System.out.println(logo);
        String content = "http://www.baidu.com";
        String logUri = logo;
        String outFileUri = path + "/logo.jpg";
        int[] size = new int[]{430, 430};
        String format = "jpg";


        QRCodeFactory qrCodeFactory = new QRCodeFactory();

        qrCodeFactory.CreatQrImage(content, format, outFileUri, logUri, null, null, size);
    }

    @Test
    public void testSimpleBean(){
        Object bean = new ModelEntity().resolveBean(new TypeToken<List<SysStatusCode>>() {
        }.getType());
        print(bean);
    }

    @Test
    public void testGenericBean(){
        Object resolveBean = new ModelEntity().resolveBean(new TypeToken<ApiResponse<Page<Person<Address>>>>() {
        }.getType());
        print(resolveBean);
    }
    @Test
    public void testJavaBean(){
        Object resolveBean = new ModelEntity().resolveBean(new TypeToken<User>(){}.getType());
        print(resolveBean);
    }




    /**
     * 打印json结构
     *
     * @param data
     */
    private void print(Object data) {

        System.out.println(JSON.toJSONStringWithDateFormat(data, "YYYY-MM-dd HH:mm:ss", SerializerFeature.PrettyFormat));
        //   System.out.println(JSON.toJSONString(data, true));
    }
}
