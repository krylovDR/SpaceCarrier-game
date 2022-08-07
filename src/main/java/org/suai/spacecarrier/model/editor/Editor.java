package org.suai.spacecarrier.model.editor;

import org.suai.spacecarrier.model.io.EditorInput;
import org.suai.spacecarrier.model.level.Level;
import org.suai.spacecarrier.model.utils.Time;
import org.suai.spacecarrier.view.Display;
import org.suai.spacecarrier.view.graphics.Background;
import org.suai.spacecarrier.view.graphics.TexturePath;
import org.suai.spacecarrier.view.panels.GameState;

import java.awt.*;

public class Editor implements Runnable {

    public static final int WIDTH = 800; // ширина окна
    public static final int HEIGHT = 600; // высота окна
    public static final String TITLE = "Level Editor"; // заголовок окна
    public static final int CLEAR_COLOR = 0xff000000; // заполнение окна черным цветом в начале
    public static final int NUM_BUFFERS = 3; // количество буферов для BufferStrategy

    public static final float UPDATE_RATE = 60.0f; // подсчёт физики в секунду
    public static final float UPDATE_INTERVAL = Time.SECOND / UPDATE_RATE; // время между каждым обновлением
    public static final long IDLE_TIME = 1; // время отдыха Thread

    private static boolean running; // запущен ли редактор
    private Thread editorThread; // дополнительный процесс для редактора
    private Graphics2D graphics; // объект графики для изменений в окне
    private EditorInput input; // для ввода
    private Background bg; // фон


    // конструктор
    public Editor() {
        running = false; // редактор не запущен
        Display.create(WIDTH, HEIGHT, TITLE, CLEAR_COLOR, NUM_BUFFERS); // создание окна редактора
        graphics = Display.getGraphics(); // для рисований изменений в окне

        input = new EditorInput(Level.LEVEL_NAME); // содание объекта ввода
        Display.addEditorInputListener(input); // создание связи ввода с дисплеем

        bg = new Background(TexturePath.BACKGROUND); // создание фона

    }

    // запуск редактора
    // synchronized - чтобы функция не могла вызываться из разных процессов (Thread) одновременно
    public synchronized void start() {
        if (running) {
            return; // если запущен - ничего не делать
        }

        running = true; // редактор запустился
        editorThread = new Thread(this, "LevelEditor"); // конструктор с параметром класса,
        // имплементриуещего с Runnable, и имя потока
        editorThread.start(); // запустит метод run()
    }

    // остановка игры
    public synchronized void stop() {
        if (!running) {
            return; // если не запущен - ничего не делать
        }

        running = false; // редактор остановлен

        //try {
        //    gameThread.join(); // ждёт когда Thread закончит работу
        //} catch (InterruptedException e) {
        //    e.printStackTrace(); // распечатать, где произошло исключение
        //}

        cleanUp(); // завершение работы
    }

    // расчёты
    private void update() {

        if (input.getGS() == GameState.QUIT) {
            stop();
        }

    }

    // "рисует" всё посчитанное в update для вывода на экран
    private void render() {
        Display.clear(); // очищает буфер на цвет в конструкторе

        bg.render(graphics); // задний фон
        input.renderLevel(graphics); // уровень
        input.renderUser(graphics); // активная ячейка


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
        long elapsedTime; // время итерации цикла
        long now; // время в цикле на данный момент

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

}