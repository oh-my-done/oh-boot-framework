package com.gizwits.security.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.access.ConfigAttribute;

/**
 * @author Feel
 * @date 2017/11/29
 * @email fye@gizwits.com
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityAttribute implements ConfigAttribute {

    private String name;

    @Override
    public String getAttribute() {
        return name;
    }
}
