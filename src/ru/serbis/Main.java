package ru.serbis;

import ru.serbis.vm.Initializer;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        int argPointer = 0;
        String path = null;
        while (argPointer < args.length) {
            switch (args[argPointer]) {
                case "-f":
                    path = args[argPointer + 1];
                    argPointer = argPointer + 2;
                    break;
                default:
                    Logger.getInstance().log(Logger.LogType.ERROR, "Незивестный входящий аргумент " + args[argPointer]);
                    break;
            }
        }

        Initializer.getInstance().INITIZALIZER_Start(path);
    }
}
