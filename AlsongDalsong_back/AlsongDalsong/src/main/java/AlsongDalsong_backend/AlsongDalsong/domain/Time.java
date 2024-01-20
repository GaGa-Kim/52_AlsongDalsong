package AlsongDalsong_backend.AlsongDalsong.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 생성 시간 변환
 */
public class Time {
    private static class TIME_MAXIMUM {
        public static final int SEC = 60;
        public static final int MIN = 60;
        public static final int HOUR = 24;
        public static final int DAY = 30;
        public static final int MONTH = 12;
    }

    public static String calculateTime(Date date) {
        long curTime = System.currentTimeMillis();
        long regTime = date.getTime();
        long diffTime = (curTime - regTime) / 1000;

        Date curDate = new Date(curTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate1 = dateFormat.format(curDate);
        System.out.println("Current Time (formatted): " + formattedDate1);

        Date regDate = new Date(regTime);
        String formattedDate2 = dateFormat.format(regDate);
        System.out.println("reg Time (formatted): " + formattedDate2);

        String msg = null;
        if (diffTime < TIME_MAXIMUM.SEC) {
            msg = diffTime + "초 전";
        } else if ((diffTime /= TIME_MAXIMUM.SEC) < TIME_MAXIMUM.MIN) {
            msg = diffTime + "분 전";
        } else if ((diffTime /= TIME_MAXIMUM.MIN) < TIME_MAXIMUM.HOUR) {
            msg = (diffTime) + "시간 전";
        } else if ((diffTime /= TIME_MAXIMUM.HOUR) < TIME_MAXIMUM.DAY) {
            msg = (diffTime) + "일 전";
        } else if ((diffTime /= TIME_MAXIMUM.DAY) < TIME_MAXIMUM.MONTH) {
            msg = (diffTime) + "달 전";
        } else {
            msg = (diffTime /= TIME_MAXIMUM.MONTH) + "년 전";
        }
        return msg;
    }
}