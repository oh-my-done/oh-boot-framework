package com.gizwits.pay.weixin.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import lombok.extern.slf4j.Slf4j;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.Writer;

/**
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
@Slf4j
public class XmlUtil {

    private static XStream xStream = new XStream(new XppDriver(new NoNameCoder()) {
        @Override
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {

                boolean cdata = true;  //对所有xml节点的转换都增加CDATA标记

                @Override
                @SuppressWarnings("rawtypes")
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                //当对象属性带下划线时，XStream会转换成双下划线，
                //重写这个方法，不再像XppDriver那样调用nameCoder来进行编译，而是直接返回节点名称，避免双下划线出现
                @Override
                public String encodeNode(String name) {
                    return name;
                }


                @Override
                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });

    /**
     * 对象转xml
     * 使用注解设置别名必须在使用之前加上注解类才有作用
     *
     * @param obj
     * @return
     */
    public static String toXMl(Object obj) {

        xStream.processAnnotations(obj.getClass());
        xStream.alias("xml", obj.getClass());// 设置根别名
        return xStream.toXML(obj);
    }

    public static <T> T fromXML(String xml, Class<T> objClass) {
        Serializer serializer = new Persister();
        try {
            return serializer.read(objClass, xml);
        } catch (Exception e) {
            log.error("{}", e);
        }
        return null;
    }

}
