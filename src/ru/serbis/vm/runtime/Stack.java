package ru.serbis.vm.runtime;

/**
 * Представление стека виртуальной машины. В данной структуре находится пул
 * потоков, а так же функции управления разметорм стека
 */
public class Stack {
    /** Экземпляр синглетона */
    private static Stack instance = null;
    /** Пул поток */
    private Thread[] threadPool = new Thread[0];

    /**
     * Возаращает экземпляр синглетона
     *
     * @return экземпляр синглетона
     */
    public static Stack getInstance() {
        if (instance == null)
            instance = new Stack();

        return instance;
    }

    /**
     * Возвращает пул потоков
     *
     * @return пул потоков
     */
    public Thread[] STACK_GetThreadPool() {
        return threadPool;
    }

    /**
     * Устанавливает новую струткуту пула потоков
     *
     * @param tp пул потоков
     */
    public void STACK_SetThreadPool(Thread[] tp) {
        threadPool = tp;
    }
}
