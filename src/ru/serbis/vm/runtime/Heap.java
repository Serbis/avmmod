package ru.serbis.vm.runtime;

/**
 * Представлениие кучи виртуальной машины. Содержит пул объектов и механизмы
 * контроля размера структуры
 */
public class Heap {
    /** Экземпляр синглетона */
    private static Heap instance = null;

    /** Пул объектов */
    private Object[] objectPool = new Object[0];

    /** Возаращает экземпляр синглетона
     *
     * @return экземпляр синглетона
     */
    public static Heap getInstance() {
        if (instance == null)
            instance = new Heap();

        return instance;
    }

    /**
     * Возвращает пул объектов
     *
     * @return пул объетктов
     */
    public Object[] HEAP_GetObjectPool() {
        return objectPool;
    }

    /**
     * Устанавливает новую струткуту пула объектов
     *
     * @param op пул объектов
     */
    public void HEAP_SetObjectPool(Object[] op) {
        objectPool = op;
    }
}
