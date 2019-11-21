package com.fdzang.microservice.common.util;

import java.util.Date;

/**
 * @author tanghu
 * @Date: 2019/11/21 15:14
 */
public class DateUtil {

    /**
     * 未超期返回ture
     * @param date
     * @return
     */
    public static boolean isExpire(Date date){
        long end = date.getTime();
        long now = new Date().getTime();

        return end > now;
    }
}
