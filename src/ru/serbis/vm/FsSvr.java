package ru.serbis.vm;

import ru.serbis.vm.runtime.Object;
import ru.serbis.vm.runtime.RuntimeLogger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

/**
 * Разделительный модуль для инкапсуляции логики работы с файловой систмой.
 * Задачей данного модуля, является предельное разделение логики работы
 * с файлами, для обеспечения возможности максимального легкого переноса
 * виртуальной машины с одной архитектуры на другу по средствам
 * сведения механики взаимодействия с файлами к тривильным операциям.
 */
public class FsSvr {
    /** Экземпляр синглетона */
    private static FsSvr instance = null;
    /** Пул файловых дескрипторов */
    private FileDescriptor[] fdPool = new FileDescriptor[0];
    /** Буфер пробразования данных */
    private ByteBuffer bf;

    /**
     * Возаращает экземпляр синглетона
     *
     * @return экземпляр синглетона
     */
    public static FsSvr getInstance() {
        if (instance == null)
            instance = new FsSvr();

        return instance;
    }

    /**
     * Создает файловый дескриптор по указанному физическому пути внутри
     * файловой системы.
     *
     * @param pathToFile путь к файлу в файловой системе
     * @return результат выполнения операции
     */
    public boolean FSSRV_Open(String pathToFile) {
        for (int i = 0; i < fdPool.length; i++) {
            if (fdPool[i].path.equals(pathToFile)) {
                RuntimeLogger.getInstance().RUNTIMELOGGER_ThrowError("Ошибка при создании файлого дескриптора программного файла. Дескриптор уже существует - " + pathToFile);
                return false;
            }
        }

        RandomAccessFile file;
        try {
            file = new RandomAccessFile(pathToFile, "r");
        } catch (FileNotFoundException ignored) {
            RuntimeLogger.getInstance().RUNTIMELOGGER_ThrowError("Ошибка при создании файлого дескриптора программного файла. Файл не найден - " + pathToFile);
            return false;
        }

        FileDescriptor fd = new FileDescriptor();
        fd.path = pathToFile;
        fd.raf = file;

        FileDescriptor fdn[] = new FileDescriptor[fdPool.length + 1];
        fdn[fdPool.length] = fd;
        for (int i = 0; i < fdPool.length; i++) {
            fdn[i] = fdPool[i];
        }

        fdPool = fdn;

        return true;
    }

    /**
     * Производит чтения блока данных из файла с указанной меткой дескриптора
     *
     * @param buffer буфер в который будет проивзодится запись данных
     * @param fdp метка файлового дескриптора
     * @param off смещения начала чтения в байтах
     * @param len количество считываемых байт
     * @return результат выполнения операции
     */
    public boolean FSSRV_ReadBytes(byte[] buffer, String fdp, int off, int len) {
        int fdid = -1;
        for (int i = 0; i < fdPool.length; i++) {
            if (fdPool[i].path.equals(fdp)) {
                fdid = i;
                break;
            }
        }

        if (fdid == -1) {
            RuntimeLogger.getInstance().RUNTIMELOGGER_ThrowError("Ошибка чтения файла. Не найден файловый дескриптор с указателем " + fdp);
            return false;
        }

        try {
            fdPool[fdid].raf.read(buffer, off, len);
        } catch (IOException ignored) {
            RuntimeLogger.getInstance().RUNTIMELOGGER_ThrowError("Ошибка чтения файла. Ошибка ввода-вывода файла в дескрипторе с указателем " + fdp);
            return false;
        }

        return true;
    }

    /**
     * Преобразует блок байт с заданным смещением внутри файла в чило типа
     * Int.
     *
     * @param target буферный объект для преобразования. Тут задан массив ввиду
     *               ограничения языка Java на передачу по ссылке примитивных
     *               типов. Поэтому пришлось сдлеать такую вот некрасивость.
     * @param fdp метка дескриптора файла
     * @param off смещение от начала файла, откуда будет читаться число
     * @return результат выполнения операции
     */
    public boolean FSSRV_ReadInt(int[] target, String fdp, int off) {
        byte[] buffer = new byte[4];
        if (!FSSRV_ReadBytes(buffer, fdp, off, 4))
            return false;
        bf = ByteBuffer.wrap(buffer);

        target[0] = bf.getInt();

        return true;
    }

    /**
     * Описывает дескрипто файла данного модуля
     */
    private class FileDescriptor {
        public String path;
        public RandomAccessFile raf;
    }
}

