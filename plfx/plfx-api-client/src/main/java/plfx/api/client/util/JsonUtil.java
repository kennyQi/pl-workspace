package plfx.api.client.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonUtil {
    private static final SerializerFeature[] CONFIG = new SerializerFeature[]{  
        SerializerFeature.WriteNullBooleanAsFalse,//boolean为null时输出false  
        SerializerFeature.WriteMapNullValue, //输出空置的字段  
        SerializerFeature.WriteNullListAsEmpty,//list为null时输出[]  
        SerializerFeature.WriteNullNumberAsZero,//number为null时输出0  
        SerializerFeature.WriteNullStringAsEmpty//String为null时输出""  
};  

public static JSONObject toJSON(Object javaObject) {  

    SerializeWriter out = new SerializeWriter();  
    String jsonStr;  
    try {  
        JSONSerializer serializer = new JSONSerializer(out);  

        for (com.alibaba.fastjson.serializer.SerializerFeature feature : CONFIG) {  
            serializer.config(feature, true);  
        }  
        serializer.config(SerializerFeature.WriteEnumUsingToString, false);  
        serializer.write(javaObject);  

        jsonStr =  out.toString();  
    } finally {  
        out.close();  
    }  
    JSONObject jsonObject = JSON.parseObject(jsonStr);  
    return jsonObject;  
}  
}
