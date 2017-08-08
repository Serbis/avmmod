package ru.serbis.vm.runtime;

import ru.serbis.Logger;
import ru.serbis.vm.ClassLoader;

/**
 * Логгер среды выполнения. Специльно вынесен из основго модуля среды
 * выполнения для снятия сопряжения моделей со средой выполнения из-за
 * системы логгирвоания.
 */
public class RuntimeLogger {
    /** Экземпляр синглетона */
    private static RuntimeLogger instance = null;

    /**
     * Возаращает экземпляр синглетона
     *
     * @return экземпляр синглетона
     */
    public static RuntimeLogger getInstance() {
        if (instance == null)
            instance = new RuntimeLogger();

        return instance;
    }
    /**
     * Ошибка среды выполнения. Задача данной функции состоит в инициализации
     * и выводе ошибок среды выолнения. В данном макете она использует обвязку
     * логгера для вывода сообщения в стандартный вывод. В реальной вм там
     * будет что-то другое, например запись лога в файл на sd карте.
     */
    public void RUNTIMELOGGER_ThrowError(String message) {
        Logger.getInstance().log(Logger.LogType.RUNTIME_ERROR, message);
    }
}
