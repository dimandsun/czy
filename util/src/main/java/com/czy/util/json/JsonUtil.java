package com.czy.util.json;

import com.czy.util.StringUtil;
import com.czy.util.enums.IEnum;
import com.czy.util.model.ResultVO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Jackson对json数据的操作
 *
 * @author chenzy
 * @date 2019.12.19
 */
public class JsonUtil {
    private final static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        SimpleModule simpleModule = new SimpleModule();
        /**
         * 序列化：对象=>jsonString
         */
        simpleModule.addSerializer(IEnum.class, new EnumSerializer());
        simpleModule.addSerializer(Date.class, new DateSerializer());
        simpleModule.addSerializer(Boolean.class, new BooleanSerializer());
        //忽略null字段
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        /**
         * 反序列化：jsonString=>对象
         */
        //允许json属性名不使用双引号
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        //忽略不存在字段
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        simpleModule.addDeserializer(String.class, new StringDeserializer());
        simpleModule.addDeserializer(Date.class, new DateDeserializer());
        simpleModule.addDeserializer(Enum.class,new EnumDeserializer());//反序列化枚举，
        simpleModule.addDeserializer(Boolean.class, new BooleanDeserializer());
        objectMapper.registerModule(simpleModule);
    }

    /**
     * 对象=>json字符串=>map
     *
     * @param model
     * @return
     */
    public static Map<String, Object> model2Map(Object model) {
        String json = model2Str(model);
        return str2Map(json);
    }
    public static <Model> Model map2Model(Map<String, Object> beanMap, Class<Model> modelClass) {
        String json = model2Str(beanMap);
        return str2ModelSimple(json, modelClass);
    }
    /**
     * 对象=>json字符串=>另一个对象
     *
     * @param model
     * @return
     */
    public static <Target> Target model2Model(Object model, Class<Target> targetClass) {
        if (model==null){
            return null;
        }
        //对象是Target类型或子类，
        if (ClassUtils.isAssignable(model.getClass(),targetClass)){
            return (Target) model;
        }
        String json = model2Str(model);
        return str2ModelSimple(json,targetClass);
    }

    /**
     * 对象的字段是泛型时也能转换
     * @param modelClass1
     * @param modelClass2
     * @param <T1>        对象类型
     * @param <T2>        字段的泛型
     * @return
     */
    public static <T1, T2> T1 model2Model(Object model, Class<T1> modelClass1, Class<T2> modelClass2) {
        String json = model2Str(model);
        return str2Model(json,modelClass1,modelClass2);
    }

    /**
     * 对象=>json字符串
     *
     * @param model
     * @return
     */
    public static String model2Str(Object model) {
        try {
            return objectMapper.writeValueAsString(model);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
        return null;
    }
    /**
     * json->model
     * 有些json格式不正确时，str2ModelSimple会出错，只能先转Map，map再转json，json再转model
     * str2ModelByBean()效率更低，不推荐使用
     * 例，正确格式：result:{data:{}}
     * 但当data为空时，接收到的是result:{data:""}。用
     *
     * @param jsonStr
     * @param modelClass
     * @param <T>
     * @return
     */
    public static <T> T str2Model(String jsonStr, Class<T> modelClass) {
//        String jsonStr2 = StringEscapeUtils.unescapeJava(jsonStr);//多层json可能存在转义字符\
        if (StringUtil.isBlank(jsonStr)){
            return null;
        }
        Integer index = jsonStr.indexOf("{");
        if (index>0){
            jsonStr =jsonStr.substring(index);
        }
        index = jsonStr.lastIndexOf("}");
        if (index!=-1&&index<(jsonStr.length()-1)){
            jsonStr =jsonStr.substring(0,index);
        }

        Map<String, Object> result = str2Map(jsonStr);
        String json = model2Str(result);
        return str2ModelSimple(json, modelClass);
    }

    /**
     * 对象的字段是泛型时也能转换
     *
     * @param jsonStr
     * @param modelClass1
     * @param modelClass2
     * @param <T1>        对象类型
     * @param <T2>        字段的泛型
     * @return
     */
    public static <T1, T2> T1 str2Model(String jsonStr, Class<T1> modelClass1, Class<T2> modelClass2) {
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(modelClass1, modelClass2);
            T1 model = objectMapper.readValue(jsonStr, javaType);
            return model;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
//{"ble":[{"mac":"00:0E:0B:0E:C1:D2","uptime":0}],"id":1576122585098,"investor":140000000000000001,"mdata":[{"index":"","machinedata":""}],"mtype":0,"nb":[{"cmd":"","imei":"868221049310231","nb_random":"","nbdeviceid":"b02c5e40-de12-4d5a-8368-d69851d55844","nbflag":0,"status":1,"uptime":0}],"pminfo":null,"schoolid":1,"serverid":1,"serviceinfo":null,"softver":"V1.02","stateinfo":[{"info":"00","uptime":"1576808348938"}],"uptime":1576808348938,"use":1,"ver":"","zfbiot":[]}

    /**
     * json字符转model的简单实现，适合简单的标准json字符
     * 使用此方法时要注意 json格式要统一。若打算转result:{data:{}}，却传入result:{data:""}，会出问题。
     * {@link this.str2Model()}已解决此问题
     *
     * @param jsonStr
     * @param modelClass
     * @param <T>
     * @return
     */
    public static <T> T str2ModelSimple(String jsonStr, Class<T> modelClass) {
        try {
            return objectMapper.readValue(jsonStr, modelClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return null;
        }
    }

    public static Map str2Map(String jsonStr) {
        if (StringUtil.isBlank(jsonStr)){
            return new HashMap();
        }
        Integer index = jsonStr.indexOf("{");
        if (index>0){
            jsonStr =jsonStr.substring(index);
        }
        return str2ModelSimple(jsonStr, Map.class);
    }

    /**
     * 创建一个json对象
     * @return
     */
    public static ObjectNode createJsonNode() {
        return objectMapper.createObjectNode();
    }
    public static void main(String[] ars) throws JsonProcessingException {
        {
            String json = "{\"ble\":[{\"mac\":\"00:0E:0B:0E:C1:D2\",\"uptime\":0}],\"id\":1576122585098,\"investor\":140000000000000001,\"mdata\":[{\"index\":\"\",\"machinedata\":\"\"}],\"mtype\":0,\"nb\":[{\"cmd\":\"\",\"imei\":\"868221049310231\",\"nb_random\":\"\",\"nbdeviceid\":\"b02c5e40-de12-4d5a-8368-d69851d55844\",\"nbflag\":0,\"status\":1,\"uptime\":0}],\"pminfo\":null,\"schoolid\":1,\"serverid\":1,\"serviceinfo\":null,\"softver\":\"V1.02\",\"stateinfo\":[{\"info\":\"00\",\"uptime\":\"1576634734985\"}],\"uptime\":1576634734985,\"use\":1,\"ver\":\"\",\"zfbiot\":[]}";
            json = "{\"Code\":-301,\"Serial\":\"1576662296966\",\"Data\":\"\",\"Message\":\"加密中心校验数据不通过:URLDataPassBlue\"}";
            json = "{\"Code\":\"0\"}";
//        Mapping map = objectMapper.readValue(json, HashMap.class);
//        MachineCashBO machineInfo = objectMapper.readValue(json, MachineCashBO.class);
//        ResultVO result = objectMapper.readValue(json, ResultVO.class);
         /*   json = "{\"Code\":\"-301\"}";
            ClientSourceEnum reqTypeEnum = str2Model(json, ClientSourceEnum.class);
            ResCodeEnum resCodeEnum = str2Model(json, ResCodeEnum.class);
            String s = model2Str(reqTypeEnum);
            s = model2Str(resCodeEnum);*/
        }
        {
            /** 获取当前系统时间*/
            long startTime = System.currentTimeMillis();
            String json = "{\"Code\":-301,\"Serial\":\"1576662296966\",\"Data\":\"\",\"Message\":\"加密中心校验数据不通过:URLDataPassBlue\"}";
            System.out.println(json);
            ResultVO result222 = null;//str2ModelByBean(json, ResultVO.class);
            System.out.println(result222);
            /** 获取当前的系统时间，与初始时间相减就是程序运行的毫秒数，除以1000就是秒数*/
            long endTime = System.currentTimeMillis();
            long usedTime = (endTime - startTime);
            System.out.println("用时：:" + usedTime);
        }
        {
            /** 获取当前系统时间*/
            long startTime = System.currentTimeMillis();
            String json = "{\"Code\":-301,\"Serial\":\"1576662296966\",\"Data\":\"\",\"Message\":\"加密中心校验数据不通过:URLDataPassBlue\"}";
            System.out.println(json);
            ResultVO result222 = str2Model(json, ResultVO.class);
            System.out.println(result222);
            /** 获取当前的系统时间，与初始时间相减就是程序运行的毫秒数，除以1000就是秒数*/
            long endTime = System.currentTimeMillis();
            long usedTime = (endTime - startTime);
            System.out.println("用时：:" + usedTime);
        }
        {
//            String json="{\"Code\":0,\"Serial\":\"1576806443048\",\"Data\":\"{\\\"typeid\\\":\\\"6\\\",\\\"pos_studentid\\\":\\\"0005F185\\\",\\\"pos_systemtime\\\":\\\"571B5A2F665E\\\",\\\"pos_money\\\":3,\\\"pos_serial\\\":11,\\\"pos_time\\\":\\\"208727904710294\\\",\\\"machineid\\\":\\\"9912120600000339\\\",\\\"app_key\\\":\\\"1BB71CEF\\\",\\\"next_open_pos_money\\\":\\\"0003\\\",\\\"next_open_pos_serial\\\":\\\"000B\\\",\\\"machineid_random\\\":\\\"557EA8BD\\\"}\",\"Message\":\"\"}";
        }


/*        {
            String json ="{\"ble\":[{\"mac\":\"00:0E:0B:0E:C1:D2\",\"uptime\":0}],\"id\":1576122585098,\"investor\":140000000000000001,\"mdata\":[{\"index\":\"\",\"machinedata\":\"\"}],\"mtype\":0,\"nb\":[{\"cmd\":\"\",\"imei\":\"868221049310231\",\"nb_random\":\"\",\"nbdeviceid\":\"b02c5e40-de12-4d5a-8368-d69851d55844\",\"nbflag\":0,\"status\":1,\"uptime\":0}],\"pminfo\":null,\"schoolid\":1,\"serverid\":1,\"serviceinfo\":null,\"softver\":\"V1.02\",\"stateinfo\":[{\"info\":\"00\",\"uptime\":\"1576661605052\"}],\"uptime\":1576661605052,\"use\":1,\"ver\":\"\",\"zfbiot\":[]}";
            System.out.println(json);
//            MachineCashBO machineInfo = str2Model(json, MachineCashBO.class);
            Mapping result = str2Map(json);
            System.out.println(result);
        }*/

    }


}
