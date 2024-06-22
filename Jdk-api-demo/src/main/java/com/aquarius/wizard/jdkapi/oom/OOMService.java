package com.aquarius.wizard.jdkapi.oom;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author zhaoyijie
 * @since 2024/6/22 09:19
 */
public class OOMService {

    public void getUserList() {
        List<OOMUser> list = new ArrayList<OOMUser>();
        int i = 0;
        while (true) {
            list.add(new OOMUser(i++, UUID.randomUUID().toString()));
        }
    }
}
