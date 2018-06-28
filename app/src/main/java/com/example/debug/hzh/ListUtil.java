package com.example.debug.hzh;
/*
 * @author xy
 * @emil 384813636@qq.com
 * create at 2018/6/27
 * description:
 */

import java.util.List;

public class ListUtil {
    public static boolean isEmpty(List list) {
        if (list == null)
            return true;

        return list.size() == 0;
    }

}//end class

