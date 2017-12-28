package com.doug.cashflow.model.system;

public class ToastFlags {
    private static final int NUMBER_OF_ENTRIES=10;
    private static int     size;
    private static boolean flags[][];

    public static void init(int size) {
        if (flags == null){
            ToastFlags.size = size;
            flags = new boolean[size][NUMBER_OF_ENTRIES];
            ToastFlags.reset();
        }
    }

    public static void reset() {
        if (flags != null) {
            for (int i = 0; i < size; i++)
                for (int j = 0; j < NUMBER_OF_ENTRIES; j++)
                    flags[i][j] = false;
        }
    }

    public void set(int page, int entry) {
        if (flags != null && page >= 0 && page < size && entry >= 0 && entry < NUMBER_OF_ENTRIES) {
            flags[page][entry] = true;
        }
    }

    public boolean isSet(int page, int entry) {
        if (page >= 0 && page < size && entry >= 0 && entry < NUMBER_OF_ENTRIES) {
            return flags[page][entry];
        }
        else {
            return false;
        }
    }
}
