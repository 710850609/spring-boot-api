package me.linbo.web.core.api;

import me.linbo.web.core.entity.UserInfo;

import java.beans.PropertyEditorSupport;

/**
 * @author LinBo
 * @date 2019-12-17 15:11
 */
public class UserInfoBinder extends PropertyEditorSupport {

    @Override
    public void setValue(Object value) {
        if (value instanceof UserInfo) {
            super.setValue(value);
        } else {

            super.setValue(value);
        }
    }
}
