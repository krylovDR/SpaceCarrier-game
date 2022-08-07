package org.suai.spacecarrier.view;

import org.suai.spacecarrier.model.io.EditorInput;
import org.suai.spacecarrier.model.io.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

public abstract class Display {

    private static boolean created = false; // создано ли окно
    private static JFrame window; // окно

    private static BufferedImage buffer; // пустое изображение
    private static int[] bufferData; // для хранения RGB
    private static Graphics bufferGraphics; // для операций с графикой
    private static int clearColor; // цвет, для очистки буфера

    private static BufferStrategy bufferStrategy; // для мульти-буферизации


    public static void create (int width, int height, String title, int _clearColor, int numBuffers) {
        if (created) {
            return; //если окно создано - ничего не делать
        }

        Canvas content; // содержимое окна, лист

        window = new JFrame(title); // создаём окно
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //закрыть программу на "крестик"
        content = new Canvas(); // создаём лист

        Dimension size = new Dimension(width, height); // объект с размером листа
        content.setPreferredSize(size); // передаём размер листа в объект

        window.setResizable(false); //запрещаем пользователю менять размер окна
        window.getContentPane().add(content); // добавление во внутреннюю часть окна листа
        window.pack(); // изменит размер окна под размер листа
        window.setLocationRelativeTo(null); //разместить окно по середине экрана

        window.setVisible(true); // делаем окно видимым

        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB); // передаём в параметре int ARGB
        bufferData = ((DataBufferInt)buffer.getRaster().getDataBuffer()).getData(); // возвращает массив int, хранящий
                                                                                    // информацию об изображении
        bufferGraphics = buffer.getGraphics(); // позволяет выполнять операции с графикой, рисовать и тд

        // сглаживание
        ((Graphics2D)bufferGraphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        clearColor = _clearColor; // сохранили цвет

        content.createBufferStrategy(numBuffers); // создаём стратегию буферизации с заданным количеством буферов
        bufferStrategy = content.getBufferStrategy(); // для имплементации numBuffers буферов
                                                      // иначе NullPointerException, т.к. bufferStrategy не существует

        created = true; // окно создано
    }

    public static void clear () {
        Arrays.fill(bufferData, clearColor); // заполняет bufferData значением clearColor
    }

    public static void swapBuffers () {
        Graphics g = bufferStrategy.getDrawGraphics(); // функция вернёт графический объект следующего буфера
        g.drawImage(buffer, 0, 0, null); // перерисовываем изображение из буфера
        bufferStrategy.show(); // показывает буфер после выполнения "рисования"
    }

    // возвращает графический объект картинки, на которой всё рисуется
    public static Graphics2D getGraphics () {
        return (Graphics2D)bufferGraphics;
    }

    // закрытие игры
    public static void destroy () {
        if (!created) {
            return; // ничего не делать, если окно не создано
        }

        window.dispose(); // закрыть окно
        System.exit(0);
    }

    // функция смены имени окна
    public static void setTitle (String title) {
        window.setTitle(title);
    }

    // для ввода внутри игры (для boolean массива)
    public static void addInputListener (Input inputListener) {
        window.add(inputListener);
    }

    // для ввода в редакторе (для KeyListener)
    public static void addEditorInputListener (EditorInput input) {
        window.addKeyListener(input);
    }

}
