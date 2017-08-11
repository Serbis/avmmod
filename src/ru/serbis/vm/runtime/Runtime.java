package ru.serbis.vm.runtime;

import javafx.util.Pair;
import ru.serbis.Logger;
import ru.serbis.vm.ClassLoader;

/**
 * Created by serbis on 01.08.17.
 */
public class Runtime {
    /** Экземпляр синглетона */
    private static Runtime instance = null;

    /**
     * Возаращает экземпляр синглетона
     *
     * @return экземпляр синглетона
     */
    public static Runtime getInstance() {
        if (instance == null)
            instance = new Runtime();

        return instance;
    }

    /**
     * Возвращает первый свободный идентификатор потока
     *
     * @return свободный идентификатор потока
     */
    public byte RUNTIME_ThreadPool_GetFreeTid() {
        boolean tidExist = false;

        for (int i = 0; i < 127; i++) {
            tidExist = false;
            for (int j = 0; j < Stack.getInstance().STACK_GetThreadPool().length; j++) {
                if (Stack.getInstance().STACK_GetThreadPool()[j].tid == i) {
                    tidExist = true;
                    break;
                }
            }
            if (!tidExist)
                return (byte) i;
        }

        return -1;
    }

    /**
     * Возвращает первый свободный идентификатор объекта
     *
     * @return свободный идентификатор объекта
     */
    public byte RUNTIME_ObjectPool_GetFreeOid() {
        boolean oidExist = false;

        for (int i = 0; i < 127; i++) {
            oidExist = false;
            for (int j = 0; j < Heap.getInstance().HEAP_GetObjectPool().length; j++) {
                if (Heap.getInstance().HEAP_GetObjectPool()[j].oid == i) {
                    oidExist = true;
                    break;
                }
            }
            if (!oidExist)
                return (byte) i;
        }

        return -1;
    }

    /**
     * Добавляет новый поток в пул потоков
     *
     * @param thread поток для добавления
     * @return результат выполнения операции
     */
    public boolean RUNTIME_ThreadPool_Put(Thread thread) {
        if (thread.tid == -1)
            RuntimeLogger.getInstance().RUNTIMELOGGER_ThrowError("Добавляемый поток имеет недопустимый идентификатор -1");
        for (int i = 0; i < Stack.getInstance().STACK_GetThreadPool().length; i++) {
            if (Stack.getInstance().STACK_GetThreadPool()[i].tid == thread.tid) {
                RuntimeLogger.getInstance().RUNTIMELOGGER_ThrowError("Добавляемый поток имеет существующий идентификатор " + thread.tid);
                return false;
            }
        }

        Thread ntp[] = new Thread[Stack.getInstance().STACK_GetThreadPool().length + 1];
        ntp[Stack.getInstance().STACK_GetThreadPool().length] = thread;
        for (int i = 0; i < Stack.getInstance().STACK_GetThreadPool().length; i++) {
            ntp[i] = Stack.getInstance().STACK_GetThreadPool()[i];
        }

        Stack.getInstance().STACK_SetThreadPool(ntp);


        return true;
    }

    /**
     * Добавляет новый инстанс в пул объектов
     *
     * @param object объект для добавления
     * @return результат выполнения операции
     */
    public boolean RUNTIME_ObjectPool_Put(Object object) {
        if (object.oid == -1)
            RuntimeLogger.getInstance().RUNTIMELOGGER_ThrowError("Добавляемый объект имеет недопустимый идентификатор -1");
        for (int i = 0; i < Heap.getInstance().HEAP_GetObjectPool().length; i++) {
            if (Heap.getInstance().HEAP_GetObjectPool()[i].oid == object.oid) {
                RuntimeLogger.getInstance().RUNTIMELOGGER_ThrowError("Добавляемый объект имеет существующий идентификатор " + object.oid);
                return false;
            }
        }

        Object nop[] = new Object[Heap.getInstance().HEAP_GetObjectPool().length + 1];
        nop[Heap.getInstance().HEAP_GetObjectPool().length] = object;
        for (int i = 0; i < Heap.getInstance().HEAP_GetObjectPool().length; i++) {
            nop[i] = Heap.getInstance().HEAP_GetObjectPool()[i];
        }

        Heap.getInstance().HEAP_SetObjectPool(nop);


        return true;
    }

    public boolean RUNTIME_OS_Push(Frame frame, byte[] value) {
        Object nop[] = new Object[Heap.getInstance().HEAP_GetObjectPool().length + 1];
        nop[Heap.getInstance().HEAP_GetObjectPool().length] = object;
        for (int i = 0; i < Heap.getInstance().HEAP_GetObjectPool().length; i++) {
            nop[i] = Heap.getInstance().HEAP_GetObjectPool()[i];
        }

        Heap.getInstance().HEAP_SetObjectPool(nop);
        frame.
    }

}
