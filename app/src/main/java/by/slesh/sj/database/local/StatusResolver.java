<<<<<<< HEAD
package by.slesh.sj.database.local;

import java.util.Random;

import by.slesh.sj.activity.R;

/**
 * Created by slesh on 05.09.2015.
 */
public final class StatusResolver {
    private static final Random GENERATOR = new Random();

    private static int randomFromInterval(int min, int max) {
        return min + GENERATOR.nextInt(max - min + 1);
    }

    public static final String[] STATUS_NAMES = {
            "Плохой друг",
            "Бывший друг",
            "Безнадега",
            "Потерянный",

            "Утратил доверие", //4
            "Полудруг", //5
            "Знакомый знакомого", //6

            "Словил звезду", //7
            "Смс-писатель", //8
            "Командировочный", //9
            "Экономный", //10

            "Деловой", //11
            "Занятой",
            "Начал исправляться",
            "Свет в конце тонелля",
            "Идет к успеху", //15

            "Родной человек", //16
            "Брат",
            "Хороший знакомый",
            "Повелитель зеленой кнопки",
            "Звонарь",
            "Заботливый друг",
            "Дружище",
            "Супер друг",
            "Король исходящих",
            "Лучший" //25
    };

    public static String getStatusName(int points) {
        if (points > 27) {
            return STATUS_NAMES[randomFromInterval(16, 25)];
        } else if (points > 17) {
            return STATUS_NAMES[randomFromInterval(11, 15)];
        } else if (points > 14) {
            return STATUS_NAMES[randomFromInterval(7, 10)];
        } else if (points > 10) {
            return STATUS_NAMES[randomFromInterval(7, 10)];
        } else if (points > 3) {
            return STATUS_NAMES[randomFromInterval(4, 6)];
        } else {
            return STATUS_NAMES[randomFromInterval(0, 3)];
        }
    }

    public static final int[] STATUS_GRAPHICS = new int[]{
            R.drawable.status0,
            R.drawable.status1,
            R.drawable.status2,
            R.drawable.status3,
            R.drawable.status4,
            R.drawable.status5
    };

    public static int getStatusGraphic(int count) {
        int index = (int)(count + 4) / 5;

        return STATUS_GRAPHICS[index % (STATUS_GRAPHICS.length + 1)];
    }
}
=======
package by.slesh.sj.database.local;

import java.util.Random;

import by.slesh.sj.activity.R;

/**
 * Created by slesh on 05.09.2015.
 */
public final class StatusResolver {
    private static final Random GENERATOR = new Random();

    private static final int randomFromInterval(int min, int max) {
        return min + GENERATOR.nextInt(max - min + 1);
    }

    public static final String[] STATUS_NAMES = {
            "Плохой друг",
            "Бывший друг",
            "Безнадега",
            "Потерянный",

            "Утратил доверие", //4
            "Полудруг", //5
            "Знакомый знакомого", //6

            "Словил звезду", //7
            "Смс-писатель", //8
            "Командировочный", //9
            "Экономный", //10

            "Деловой", //11
            "Занятой",
            "Начал исправляться",
            "Свет в конце тонелля",
            "Идет к успеху", //15

            "Родной человек", //16
            "Брат",
            "Хороший знакомый",
            "Повелитель зеленой кнопки",
            "Звонарь",
            "Заботливый друг",
            "Дружище",
            "Супер друг",
            "Король исходящих",
            "Лучший" //25
    };

    public static String getStatus(int points) {
        return getPair(points)[0].toString();
    }

    public static Integer getGraphic(int points) {
        return (Integer) getPair(points)[1];
    }

    private static Object[] getPair(int points) {
        if (points > 27) {
            return new Object[]{STATUS_NAMES[randomFromInterval(16, 25)], R.drawable.status5};
        } else if (points > 17) {
            return new Object[]{STATUS_NAMES[randomFromInterval(11, 15)], R.drawable.status4};
        } else if (points > 14) {
            return new Object[]{STATUS_NAMES[randomFromInterval(7, 10)], R.drawable.status3};
        } else if (points > 10) {
            return new Object[]{STATUS_NAMES[randomFromInterval(7, 10)], R.drawable.status2};
        } else if (points > 3) {
            return new Object[]{STATUS_NAMES[randomFromInterval(4, 6)], R.drawable.status1};
        } else {
            return new Object[]{STATUS_NAMES[randomFromInterval(0, 3)], R.drawable.status0};
        }
    }
}
>>>>>>> 49d28eb0877fb4d02ca30ad13e6abeecdf62e99e
