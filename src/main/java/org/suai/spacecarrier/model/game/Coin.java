package org.suai.spacecarrier.model.game;

import org.suai.spacecarrier.view.graphics.Texture;
import org.suai.spacecarrier.view.graphics.TexturePath;

import java.awt.*;

public class Coin {

    private static int coins = 0; // количество монет

    // массив с путям текустур для цифр
    private static final String[] textures = {TexturePath.ZERO, TexturePath.ONE, TexturePath.TWO,
                                              TexturePath.THREE, TexturePath.FOUR, TexturePath.FIVE,
                                              TexturePath.SIX, TexturePath.SEVEN, TexturePath.EIGHT,
                                              TexturePath.NINE};


    public static void render (Graphics2D g) {
        Texture hud = new Texture(TexturePath.HUD); // HUD
        Texture number1 = new Texture(textures[coins / 10]); // первая цифра
        Texture number2 = new Texture(textures[coins % 10]); // вторая цифра

        // вывести на экран
        hud.render(g, 0, 0); // вывод панельки для монет
        number1.render(g, 44, 12); // вывод первой цифры
        number2.render(g, 58, 12); // вывод второй цифры
    }

    // установить количество монет (для пакета)
    static void setCoinsZero () {
        coins = 0;
    }

    // добавить одну монетку (для пакета)
    static void addCoin () {
        coins++;
    }

    // геттер для coins
    public static int getCoins () {
        return coins;
    }

}
