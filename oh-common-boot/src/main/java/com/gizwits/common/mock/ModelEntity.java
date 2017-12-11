package com.gizwits.common.mock;

import com.fasterxml.classmate.MemberResolver;
import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.ResolvedTypeWithMembers;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.classmate.members.ResolvedField;
import com.fasterxml.classmate.types.ResolvedInterfaceType;
import com.fasterxml.classmate.types.ResolvedObjectType;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;
import java.util.*;

/**
 * 根据泛型信息mock前端API数据
 * 使用说明：
 * example:
 * <p>
 * //TODD
 * return ApiResponse.success(new ModelEntity().resolveBean(new TypeToken<List<SysStatusCode>>() {
 * }.getType()));
 * </p>
 * 备注:
 * <code>
 * 以下代码在反射json对象的时候不好实现，故先放着
 * if (field.getRawMember().isAnnotationPresent(JsonProperty.class)) {
 * fieldName = field.getRawMember().getAnnotation(JsonProperty.class).value();
 * }
 * </code>
 *
 * @author Feel
 * @date 2017/11/17
 * @email fye@gizwits.com
 * @since 1.0
 */
public class ModelEntity {


    private TypeResolver typeResolver = new TypeResolver();

    private MemberResolver memberResolver = new MemberResolver(typeResolver);

    private String[] source = {"java", "golang", "python", "scala", "erlang", "swift", "kotlin", "c++", "ruby"};

    private Gson gson = new Gson();

    private Map resolvedType(Type type) {

        HashMap map = new HashMap();
        ResolvedType resolve = typeResolver.resolve(type);
        ResolvedTypeWithMembers members = memberResolver.resolve(resolve, null, null);
        ResolvedField[] fields = members.getMemberFields();

        for (ResolvedField field : fields) {

            Object defaultValue = defaultValue(field.getType().getTypeName());

            String fieldName = field.getName();

            if (defaultValue != null) {
                map.put(fieldName, defaultValue);
                continue;
            }
            if (field.getType() instanceof ResolvedInterfaceType) {
                if (field.getRawMember().getType().isAssignableFrom(List.class)) {
                    map.put(fieldName, Lists.newArrayList(resolvedType(field.getType().getTypeParameters().get(0))));
                }
                if (field.getRawMember().getType().isAssignableFrom(Map.class)) {
                    map.put(fieldName, resolvedType(field.getType().getTypeParameters().get(1)));
                }
                if (field.getRawMember().getType().isAssignableFrom(Set.class)) {
                    map.put(fieldName, Lists.newArrayList(resolvedType(field.getType().getTypeParameters().get(0))));
                }
            } else if (field.getType() instanceof ResolvedObjectType) {
                map.put(fieldName, resolvedType(field.getType()));
            } else {
                map.put(fieldName, resolvedType(field.getType()));
            }
        }

        return map;
    }

    public Object resolve(Type type) {

        ResolvedType resolve = typeResolver.resolve(type);

        if (resolve.getErasedType().isAssignableFrom(List.class)) {

            return Lists.newArrayList(resolvedType(resolve.getTypeParameters().get(0)));

        } else if (resolve.getErasedType().isAssignableFrom(Map.class)) {
            HashMap map = new HashMap();
            map.put(randomString(source), resolvedType(resolve.getTypeParameters().get(1)));
            return map;
        }

        return resolvedType(type);
    }

    public <T> T resolveBean(Type typeOfT) {
        Object resolve = resolve(typeOfT);
        return gson.fromJson(gson.toJson(resolve), typeOfT);
    }

    private Object defaultValue(String typeName) {

        DataType dataType = DataType.getDataType(typeName);
        if (dataType == null) return null;
        switch (dataType) {
            case INT:
                return randomInt(source);
            case BYTE:
                return randomByte(source);
            case CHAR:
                return randomChar(source);
            case BOOLEAN:
                return false;
            case LONG:
                return new Long(randomInt(source));
            case SHOT:
                return new Long(randomInt(source));
            case FLOAT:
                return new Float(new Long(randomInt(source)));
            case DOUBLE:
                return new Double(new Long(randomInt(source)));
            case STRING:
                return randomString(source);
            case DATE:
                return new Date();
            default:
                return null;
        }

    }


    /**
     * 生成指定source源随机字符串
     *
     * @param randomSource
     * @return
     */
    private String randomString(String[] randomSource) {
        int nextInt = new Random().nextInt(randomSource.length);
        return randomSource[nextInt];
    }

    /**
     * 生成指定source源随机char
     *
     * @param randomSource
     * @return
     */
    private char randomChar(String[] randomSource) {
        String join = StringUtils.join(randomSource);
        int nextInt = new Random().nextInt(join.length());
        return join.toCharArray()[nextInt];
    }

    /**
     * 生成指定source源随机byte
     *
     * @param randomSource
     * @return
     */
    private byte randomByte(String[] randomSource) {
        String join = StringUtils.join(randomSource);
        int nextInt = new Random().nextInt(join.length());
        return join.getBytes()[nextInt];
    }

    /**
     * 生成随机数
     *
     * @param randomSource
     * @return
     */
    private int randomInt(String[] randomSource) {
        return new Random().nextInt(randomSource.length + 100);
    }

}
