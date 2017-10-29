package ru.otus.HW21;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.function.Supplier;

public class Main {

    public static void main(String... args) throws InterruptedException {
        System.out.println("pid: " + ManagementFactory.getRuntimeMXBean().getName());
        System.out.println("Starting the loop");
        MyClass calculator = new MyClass(200_000);

        System.out.println("--- Размер пустых объектов (bytes) ---");
        System.out.println(String.class + " pool: " + calculator.getObjectSize(String::new));
        System.out.println(String.class + " without String pool: " + calculator.getObjectSize(() -> new String(new char[0])));
        System.out.println(Object.class + ":" + calculator.getObjectSize(() -> new Object[0]));
        System.out.println(ArrayList.class + ":" + calculator.getObjectSize(() -> new ArrayList(10)));

        System.out.println("--- Рост размера контейнера с увеличением количества элементов ---");

        for (int i = 0; i < 11; i++) {
            int l = i;
            System.out.println(ArrayList.class + " of int: " + i + ", size(bytes): " + calculator.getObjectSize(() -> {
                ArrayList array = new ArrayList();
                for (int j = 0; j < l; j++) {
                    array.add(new Integer(j));
                }
                return array;
            }));
        }
    }
}



    class MyClass {
        private final Runtime runtime;
        private final int size;
        private final Object[] array;

        MyClass (int size) {
           runtime = Runtime.getRuntime();
           this.size = size;
           array = new Object[size];
        }
        private static long getMemorySize(Runtime runtime) {
            return runtime.totalMemory() - runtime.freeMemory();
        }
        long getObjectSize(Supplier<Object> supplier) {

            System.gc();
            Object[] array = new Object[size];

            long memBefore = getMemorySize(runtime);
            for (int j = 0; j < size; j++) {
                array[j] = supplier.get();
            }
            long memAfter  = getMemorySize(runtime);
            clearArray();
            System.gc();
            return (memAfter - memBefore) / size;
        }

        private void clearArray() {
            for (int i = 0; i < size; i++) {
                array[i] = null;
            }
        }
    }

