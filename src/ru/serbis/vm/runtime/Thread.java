package ru.serbis.vm.runtime;

/**
 * Струтура определяющая поток выполнения
 */
public class Thread {
    /** Идентификатор потока */
    public byte tid;
    /** Стек вызовов */
    public Frame[] frameStack = new Frame[0];
}
