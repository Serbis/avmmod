package ru.serbis.vm;

import ru.serbis.vm.runtime.Runtime;
import ru.serbis.vm.runtime.RuntimeLogger;
import ru.serbis.vm.runtime.Thread;

/**
 * Инициализатор виртуальной машины. Задача иницилизатора, заключается в том,
 * что бы создать все управляющие конструкции виртуальной машины, создать
 * первичный поток. После чего передать передать загрузчику указание выполнить
 * загрузку головного класса.
 */
public class Initializer extends java.lang.Thread {
    /** Экземпляр синглетона */
    private static Initializer instance = null;

    /**
     * Возаращает экземпляр синглетона
     *
     * @return экземпляр синглетона
     */
    public static Initializer getInstance() {
        if (instance == null)
            instance = new Initializer();

        return instance;
    }

    public void INITIZALIZER_Start(String classFilePath) {
        Thread defThread = new Thread(); //Создать дефолтный поток
        byte tid = Runtime.getInstance().RUNTIME_ThreadPool_GetFreeTid(); //Получить свободный идентификатор потока
        if (tid == -1) //Если в пуле нет места, ошибка времени выполнения
            RuntimeLogger.getInstance().RUNTIMELOGGER_ThrowError("Ошибка создания головного потока, нет свободных дескрипторов.");
        defThread.tid = tid; //Задать идентификатор для дефолтного потока
        if (!Runtime.getInstance().RUNTIME_ThreadPool_Put(defThread))
            return;

        ClassLoader.getInstance().CLASSLOADER_ImitateClassLoad(); //Имитация загрузки класса, читай определение функции
        if (!ClassLoader.getInstance().CLASSLOADER_Load(classFilePath, tid)) //Загрузить программный файл
            return;

    }
}
