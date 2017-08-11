package ru.serbis.vm.runtime;

import ru.serbis.vm.FsSvr;

import java.nio.ByteBuffer;

/**
 * Created by serbis on 08.08.17.
 */
public class Interpretator {
    /** Экземпляр синглетона */
    private static Interpretator instance = null;
    /** Указатель текущего адреса в файлае */
    public int currentAddress;
    /** Указатель на активный фрейм */
    public Frame frame;
    /** Буфер преобразования */
    private ByteBuffer bf;
    /** Пул выводв данный в вм */
    byte[] osOut = new byte[4];

    /** Возаращает экземпляр синглетона
     *
     * @return экземпляр синглетона
     */
    public static Interpretator getInstance() {
        if (instance == null)
            instance = new Interpretator();

        return instance;
    }

    public void INTERPRETATOR_Run() {
        boolean br = false;

        while (br) {
            byte[] instruction = new byte[1];
            FsSvr.getInstance().FSSRV_ReadBytes(instruction, frame.fdp, currentAddress, 1);

            switch (instruction[0]) {
                case (byte) 0xBB:
                    bf = ByteBuffer.allocate(4);
                    int[] its = new int[1];
                    if (FsSvr.getInstance().FSSRV_ReadInt(its, frame.fdp, currentAddress + 1)) {
                        //Тут нужно вызывать исключения уровня кода CodeReadException
                        return;
                    }
                    INTERPRETATOR_Exec_New();
                    break;
            }
        }
    }

    /**
     * Дествие: Создает новый объект
     * Результат: 1. Размещение на OS ссылки на объект типа aref
     *            2. Размешение на osOut ссылки на объект типа aref
     * Механика: В нормальном режиме получает в качестве аргумена адрес
     *           значения в CP с определение создаваемого объекта а с вершины
     *           стека TID, в котором необходимо создать эземлпяр.
     *           Работа разделяется на три режма:
     *
     *           1. Если оба аргумента являются значениями отличными от 0xF.
     *           Получив данные значения, задействует загрузчик в части функции
     *           Load которая в реузльтате свой работы вернет вернет aref,
     *           который помещается на вершину OS.
     *
     *           2. Если в качестве TID будет получено значение
     *           0xF. То интрукция сначала создаст новый поток а затем
     *           произведет действия описаные в п.1 относительно созданнного
     *           потока.
     *
     *           3. Если в качетсве указателя на CP и TIC указано 0xF,
     *           задействует режим первичной загрузки класса. В данным режиме,
     *           создаст новый поток, и передст загрузчику в качестве пути
     *           загрузки FDP. Получив aref, заносит его в массив вывода osOut,
     *           для  возможности деальнешего считывания переферией вм.
     *
     * @param cpPointer указатель определения класса в CP
     */
    public void INTERPRETATOR_Exec_New(int cpPointer) {
        /*
            1. Делаем эту инструкция (переделка Load загрузчика
            2. Провряем ее работу (корректируем иннициализатор)
            4. Релизовать метод bipush
            3. Делаем инутрукцию invokeseptial
            6. Делаем инструкцию invokevirtal


         */
    }

    /**
     * Помещает на вершину OS фрейма значения типа int из аргумента
     *
     * @param baInt значение для размещения
     */
    public void INTERPRETATOR_Exec_Bipush(byte[] baInt) {

    }
}
