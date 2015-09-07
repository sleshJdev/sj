package by.slesh.sj.database.local;

import java.util.Random;

import by.slesh.sj.activity.R;
import by.slesh.sj.database.local.model.Status;

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

    public static Status getStatus(int points) {
        if (points > 27) {
            return new Status(STATUS_NAMES[randomFromInterval(16, 25)], R.drawable.status5);
        } else if (points > 17) {
            return new Status(STATUS_NAMES[randomFromInterval(11, 15)], R.drawable.status4);
        } else if (points > 14) {
            return new Status(STATUS_NAMES[randomFromInterval(7, 10)], R.drawable.status3);
        } else if (points > 10) {
            return new Status(STATUS_NAMES[randomFromInterval(7, 10)], R.drawable.status2);
        } else if (points > 3) {
            return new Status(STATUS_NAMES[randomFromInterval(4, 6)], R.drawable.status1);
        } else {
            return new Status(STATUS_NAMES[randomFromInterval(0, 3)], R.drawable.status0);
        }
    }
}
