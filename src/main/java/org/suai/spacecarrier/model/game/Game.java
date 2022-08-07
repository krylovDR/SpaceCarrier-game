package org.suai.spacecarrier.model.game;

import org.suai.spacecarrier.model.io.Input;
import org.suai.spacecarrier.model.level.Level;
import org.suai.spacecarrier.model.utils.Time;
import org.suai.spacecarrier.view.Display;
import org.suai.spacecarrier.view.graphics.Background;
import org.suai.spacecarrier.view.graphics.TexturePath;
import org.suai.spacecarrier.view.panels.EndMenu;
import org.suai.spacecarrier.view.panels.GameState;
import org.suai.spacecarrier.view.panels.MiniMenu;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Game implements Runnable {

    public static final int WIDTH = 800; // ширина окна
    public static final int HEIGHT = 600; // высота окна
    public static final String TITLE = "SpaceCarrier"; // заголовок окна
    public static final int CLEAR_COLOR = 0xff000000; // заполнение окна черным цветом в начале
    public static final int NUM_BUFFERS = 3; // количество буферов для BufferStrategy

    public static final float UPDATE_RATE = 60.0f; // подсчёт физики в секунду
    public static final float UPDATE_INTERVAL = Time.SECOND / UPDATE_RATE; // время между каждым обновлением
    public static final long IDLE_TIME = 1; // время отдыха Thread

    private static boolean running; // запущена ли игра
    private Thread gameThread; // дополнительный процесс для игры
    private Graphics2D graphics; // объект графики для изменений в окне
    private Input input; // для ввода
    private Player player; // игрок
    private Level lvl; // уровень
    private Background bg; // фон
    private Laser laser; // лазеры врагов


    // конструктор
    public Game() {
        running = false; // игра не запущена
        Display.create(WIDTH, HEIGHT, TITLE, CLEAR_COLOR, NUM_BUFFERS); // создание окна игры
        graphics = Display.getGraphics(); // для рисований изменений в окне

        input = new Input(); // содание объекта ввода
        Display.addInputListener(input); // создание связи ввода с дисплеем

        player = new Player(Player.POSITION_X, Player.POSITION_Y); // создание объекта игрока
        lvl = new Level(Level.LEVEL_NAME); // создание уровня
        laser = new Laser(lvl); // определение лазеров врагов
        bg = new Background(TexturePath.BACKGROUND); // создание фона

    }

    // запуск игры
    // synchronized - чтобы функция не могла вызываться из разных процессов (Thread) одновременно
    public synchronized void start() {
        if (running) {
            return; // если запущена - ничего не делать
        }

        running = true; // игра запустилась
        gameThread = new Thread(this, "SpaceCarrier"); // конструктор с параметром класса,
                                                                   // имплементриуещего с Runnable, и имя потока
        gameThread.start(); // запустит метод run()
    }

    // остановка игры
    public synchronized void stop() {
        if (!running) {
            return; // если не запущена - ничего не делать
        }

        running = false; // игра остановлена

        //try {
        //    gameThread.join(); // ждёт когда Thread закончит работу
        //} catch (InterruptedException e) {
        //    e.printStackTrace(); // распечатать, где произошло исключение
        //}

        cleanUp(); // завершение работы
    }

    //вся физика, математические расчёты, движения, позиции и тд
    private void update() {

        // если нажата кнопка Esc - открыть мини-меню
        if (input.getKey(KeyEvent.VK_ESCAPE)) {

            player.setMoveable(false); // запрет игроку двигаться
            MiniMenu.miniMenuLauncher(); // открыть мини-меню

            if (MiniMenu.getGameState() == GameState.CONTINUE) {

                player.setMoveable(true); // разрешение игроку двигаться
                input.releaseKeys(); // отжать Esc (избежание залипания клавиши)
                MiniMenu.setCreated(false); // пометить окно мини-меню закрытым

            } else if (MiniMenu.getGameState() == GameState.RESTART) {

                player.setMoveable(true);
                input.releaseKeys(); // отжать Esc (избежание залипания клавиши)
                MiniMenu.setCreated(false);
                restartLevel(); // перезапуск уровня

            } else if (MiniMenu.getGameState() == GameState.QUIT) {

                player.setMoveable(true);
                input.releaseKeys(); // отжать Esc (избежание залипания клавиши)
                MiniMenu.setCreated(false);
                stop(); // закрыть игру

            }
        }

        player.update(input); // обновление игрока
        laser.update(lvl); // обновление местоположения лазеров
        lvl.update(); // обновление уровня

        // определить наличие коллизии
        if (CollisionDetector.detectCollision(player, lvl, laser)) {

            player.setMoveable(false); // запрет игроку двигаться
            input.releaseKeys(); // отжать все клавиши (избежание залипания)
            EndMenu.endMenuLauncher(CollisionDetector.getGameState());

            if (EndMenu.getGameState() == GameState.RESTART) {

                player.setMoveable(true);
                input.releaseKeys(); // отжать Esc (избежание залипания клавиши)
                EndMenu.setCreated(false);
                restartLevel(); // перезапуск уровня

            } else if (EndMenu.getGameState() == GameState.QUIT) {

                player.setMoveable(true);
                input.releaseKeys(); // отжать Esc (избежание залипания клавиши)
                EndMenu.setCreated(false);
                stop(); // закрыть игру

            }

        }

    }

    // "рисует" всё посчитанное в update для вывода на экран
    private void render() {
        Display.clear(); // очищает буфер на цвет в конструкторе

        bg.render(graphics); // задний фон
        lvl.render(graphics); // уровень
        player.render(graphics); // игрок
        laser.render(graphics); // лазеры
        Coin.render(graphics); // монетки

        Display.swapBuffers(); // обновить картинку на экране

    }

    // главная функция с циклом
    public void run() {
        int fps = 0; // количество кадров в секунду
        int upd = 0; // количество апдейтов в секунду
        int updl = 0; // количество апдейтов в цикле подряд

        long count = 0; // для подсчёта общего времени игры

        float delta = 0; // для подсчёта количества апдейтов
        long lastTime = Time.get(); // время запуска цикла
        boolean render = false; // нужно ли обновлять экран
        long elapsedTime = 0; // время итерации цикла
        long now = 0; // время в цикле на данный момент

        while (running) {
            now = Time.get(); // настоящее время
            elapsedTime = now - lastTime; // время итерации цикла
            lastTime = now; // время начала итерации цикла

            count += elapsedTime; // общее время игры

            render = false;
            delta += (elapsedTime / UPDATE_INTERVAL); // количество апдейтов на каждое целое число

            // 60 апдейтов в секунду
            while (delta > 1) {
                update(); // обновляем
                upd++; // подсчёт количества апдейтов
                delta--;

                if (render) {
                    updl++; // подсчёт количества апдейтов в цикле подряд
                } else {
                    render = true; // на экране что-то должно изменится после апдейта
                }
            }

            if (render) {
                render(); // перерисовываем экран
                fps++; // подсчёт fps
            } else {
                try {
                    Thread.sleep(IDLE_TIME); // даём отдохнуть процессу
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // вывод данных в заголовок
            if (count >= Time.SECOND) {
                Display.setTitle(TITLE + " || Fps: " + fps + " | Upd: " + upd + " | Updl: " + updl);
                upd = 0;
                fps = 0;
                updl = 0;
                count = 0;
            }

        }
    }

    // для закрытия ресурсов
    private void cleanUp() {
        Display.destroy(); // закрыть окно
    }

    // перезапуск уровня
    private void restartLevel () {
        lvl.fillRand(Level.LEVEL_NAME); // перезаполнение уровня случайными составляющими
        player.toStart(); // исходня точка игрока
        Coin.setCoinsZero(); // обнуление количества монеток у игрока
        laser.findEnemy(lvl); // перерасчитать нахождения лазеров относительно врагов
        CollisionDetector.restartGameState(); // обычное состояние игры (игрок жив)
    }
}
