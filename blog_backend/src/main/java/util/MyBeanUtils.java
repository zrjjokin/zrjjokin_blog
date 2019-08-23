package util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MyBeanUtils {

    public static void transMap2Bean(Map<String,Object> map, Object obj ){
        //ConvertUtils.register(new DateLocaleConverter(), Date.class);
        if(map==null || obj==null){
            return ;
        }
        try{
            BeanUtils.populate(obj,map);
        }catch(Exception e){
            System.out.println("transMap2Bean Error"+e);
        }
    }

    public static Map<String,Object> transBean2Map(Object obj){
        //ConvertUtils.register(new DateLocaleConverter(), Date.class);
        if(obj==null){
            return null;
        }
        Map<String,Object> map = new HashMap<>();
        try{
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for(PropertyDescriptor property: propertyDescriptors){
                String key = property.getName();
                //过滤class属性
                if(!key.equals("class")){
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    map.put(key,value);
                }
            }
        }catch (Exception e){
            System.out.println("transBean2Map Error"+e);
        }
        return map;
    }
}
