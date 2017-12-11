package com.gizwits.security.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.access.ConfigAttribute;

import java.util.Objects;

/**
 * @author Feel
 * @date 2017/11/29
 * @email fye@gizwits.com
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlConfigAttribute implements ConfigAttribute {
    private String url;
    private String method;


    @Override
    public String getAttribute() {
        return this.url + ";" + this.method;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj instanceof UrlConfigAttribute) {
            UrlConfigAttribute url = (UrlConfigAttribute) obj;
            if (url.url.equals(this.url) && url.method.equals(this.method))
                return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(url) + Objects.hashCode(method);

    }
}
