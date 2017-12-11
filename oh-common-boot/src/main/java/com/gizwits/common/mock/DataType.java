package com.gizwits.common.mock;

/**
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
public enum DataType {

    SHOT("shot"),
    CHAR("char"),
    INT("int", "java.lang.Integer"),
    LONG("long", "java.lang.Long"),
    FLOAT("float", "java.lang.Float"),
    DOUBLE("double", "java.lang.Double"),
    BYTE("byte", "java.lang.Byte"),
    BOOLEAN("boolean", "java.lang.Boolean"),
    STRING("string", "java.lang.String"),
    DATE("date", "java.util.Date");

    private String name;
    private String erasedType = "";


    DataType(String name) {
        this.name = name;
    }

    DataType(String name, String erasedType) {
        this.name = name;
        this.erasedType = erasedType;
    }

    public java.lang.String getName() {
        return name;
    }


    public java.lang.String getErasedType() {
        return erasedType;
    }

    public static DataType getDataType(String name) {
        for (DataType c : DataType.values()) {
            if (c.getName().equalsIgnoreCase(name) || c.getErasedType().equalsIgnoreCase(name)) {
                return c;
            }
        }
        return null;
    }

}
