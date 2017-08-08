package ru.serbis.vm;

import ru.serbis.Logger;
import ru.serbis.vm.runtime.Frame;
import ru.serbis.vm.runtime.Object;
import ru.serbis.vm.runtime.Runtime;
import ru.serbis.vm.runtime.RuntimeLogger;

import java.math.BigDecimal;

/**
 * Created by serbis on 01.08.17.
 */
public class ClassLoader {
    /** Экземпляр синглетона */
    private static ClassLoader instance = null;

    /**
     * Возаращает экземпляр синглетона
     *
     * @return экземпляр синглетона
     */
    public static ClassLoader getInstance() {
        if (instance == null)
            instance = new ClassLoader();

        return instance;
    }

    /**
     * Функция заглушка для имитации загрузки класса. Она предназначена
     * для создания виртуального инстанса, пока в вм не будет реализована
     * полноценная система инстанциирования классов.
     */
    public void CLASSLOADER_ImitateClassLoad() {
        Object object = new Object();
        byte oid = Runtime.getInstance().RUNTIME_ObjectPool_GetFreeOid(); //Получить свободный идентификатор объекта
        if (oid == -1) //Если в пуле нет места, ошибка времени выполнения
            RuntimeLogger.getInstance().RUNTIMELOGGER_ThrowError("Ошибка создания имитатора объекта, нет свободных дескрипторов.");
        object.oid = oid; //Задать идентификатор для дефолтного потока
        if (!Runtime.getInstance().RUNTIME_ObjectPool_Put(object))
            return;

    }

    /**

     *
     * @param classFilePath путь к класс-файлу
     * @param tid идентификатор потока в котором будет размещен фрейм констрктора
     * @return результат выполнения операции
     */
    public boolean CLASSLOADER_Load(String classFilePath, byte tid, int aRefBuf) {
        if (!FsSvr.getInstance().FSSRV_Open(classFilePath)) //Создать файловый дескриптор для программного файла
            return false;

        Frame frame = new Frame(); //Создать ноывй фрейм
        frame.fdp = classFilePath; //Занестти указатель на файловый дескриптор

        int[] insOffset = new int[1];
        FsSvr.getInstance().FSSRV_ReadInt(insOffset,  classFilePath, 0);

        return true;
    }
}
