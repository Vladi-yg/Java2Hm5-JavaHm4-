public class Main {
//1. Необходимо написать два метода, которые делают следующее:
//1) Создают одномерный длинный массив, например:
//
//static final int size = 10000000;
//static final int h = size / 2;
//float[] arr = new float[size];
//
//2) Заполняют этот массив единицами;
//3) Засекают время выполнения: long a = System.currentTimeMillis();
//4) Проходят по всему массиву и для каждой ячейки считают новое значение по формуле:
//arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
//5) Проверяется время окончания метода System.currentTimeMillis();
//6) В консоль выводится время работы: System.out.println(System.currentTimeMillis() - a);
//
//Отличие первого метода от второго:
//Первый просто бежит по массиву и вычисляет значения.
//Второй разбивает массив на два массива, в двух потоках высчитывает новые значения и потом склеивает эти массивы обратно в один.
//
//Пример деления одного массива на два:
//
//System.arraycopy(arr, 0, a1, 0, h);
//System.arraycopy(arr, h, a2, 0, h);
//
//Пример обратной склейки:
//
//System.arraycopy(a1, 0, arr, 0, h);
//System.arraycopy(a2, 0, arr, h, h);
//
//Примечание:
//System.arraycopy() – копирует данные из одного массива в другой:
//System.arraycopy(массив-источник, откуда начинаем брать данные из массива-источника, массив-назначение, откуда начинаем записывать данные в массив-назначение, сколько ячеек копируем)
//По замерам времени:
//Для первого метода надо считать время только на цикл расчета:
//
//for (int i = 0; i < size; i++) {
//arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
//}
//
//Для второго метода замеряете время разбивки массива на 2, просчета каждого из двух массивов и склейки.
    public static void main(String[] args) {
        Thread t1 = new Thread( new MyRunnableClass(100,100) {
            @Override
            public void run() {
               init();
            }
        });
        t1.start();
    }
}
class MyRunnableClass implements Runnable {
    int size;
    int value;


    public MyRunnableClass (int size, int value){
        this.size = size;
        this.value = value;
    }


    @Override
    public  void run () {
      init();
    }

    public void init() {
        if ((size % 2 | value % 2) == 0 ) {
            calculateValue();
            splitArraysCalculation();
        }
        else System.out.println("Задайте четные размеры массива");
    }

    public synchronized void calculateValue() {
        float[] firstArray = new float[size];


        System.out.println();
        System.out.println("Запоненный массив(первого метода)");
        for (int i = 0; i < firstArray.length; i++) {
            firstArray[i] = 1.0f;
            System.out.print(firstArray[i] + "  ");
        }


        System.out.println();
        System.out.println();


        long a = System.currentTimeMillis();
        for (int i = 0; i < firstArray.length; i++) {
            System.currentTimeMillis();
            firstArray[i] = (float)(firstArray[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            System.out.print(firstArray[i] + " ");
        }
        System.out.println();
        System.out.print("Расчет элементов массива  выполнился за ");
        System.out.print(System.currentTimeMillis() - a);
        System.out.print(" милисекунду");
    }


    public synchronized void splitArraysCalculation() {
        System.out.println();
        System.out.println();
        int split = value/2;
        float[] secondArray = new float[value];
        System.out.println("Начальный массив");

        for (int i = 0; i < secondArray.length; i++) {
            secondArray[i] = 1f;
            System.out.print(secondArray[i] + "  ");
        }

        float[] a1 = new float[split];
        float[] a2 = new float[split];
        long arraySplitTime =  System.currentTimeMillis();


        System.out.println();
        System.out.println();


        System.out.print("Время расщепления массива на равные части(в милисекундах) - ");
        System.currentTimeMillis();
        System.arraycopy(secondArray, 0 , a1, 0, split);
        System.arraycopy(secondArray, split , a2, 0, split);
        System.out.println(System.currentTimeMillis() - arraySplitTime);
        System.out.println();
        System.out.println("Массив а1");

        System.out.println();
        System.out.println();


        for (int i = 0; i < a1.length; i++) {
            System.currentTimeMillis();
            a1[i] = (float)(a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            System.out.print(a1[i] + "  ");
        }
        System.out.println();
        System.out.print("Время расчета элементов для массива a1 (в милисекундах) - ");
        System.out.println(System.currentTimeMillis() - arraySplitTime);


        System.out.println();
        System.out.println();

        System.out.println("Массив а2");

        System.out.println();
        System.out.println();

        for (int i = 0; i < a2.length; i++) {
            System.currentTimeMillis();
            a2[i] = (float)(a2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            System.out.print(a2[i] + "  ");
        }
        System.out.println();
        System.out.print("Время расчета элементов для массива a2 (в милисекундах) - ");
        System.out.println(System.currentTimeMillis() - arraySplitTime);

        System.out.println();
        System.out.println();


        System.out.print("Время склейки массива (в милисекундах) - ");
        System.currentTimeMillis();
        System.arraycopy(a1, 0, secondArray, 0, split);
        System.arraycopy(a2, 0, secondArray, split, split);
        System.out.println(System.currentTimeMillis() - arraySplitTime);


        System.out.println();
        System.out.println();

        System.out.println("Склеенный массив");
        for (int i = 0; i < secondArray.length; i++) {
            System.out.print(secondArray[i] + "  ");
        }
        System.out.println();
        System.out.println();
    }
}

