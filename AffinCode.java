package code;

import java.util.ArrayList;

/**
 * Реализация кодирования и раскодирования текста.
 * @author Admin
 */

public class AffinCode {
    //Создание и инициализация необходимыми символами завершенной переменной 
    //типа char SYMBOL
    private final static char[] SYMBOL = {'.', ',', '!', '?', ';', ':', '-', '+', '/', '*'};
    // Создание пустой коллекции типа ArrayList для последующего заполнения набором
    // символов для шифрования
    ArrayList<Character> symbolSet = new ArrayList<Character>();

        
    //Организация конструктора не принимающего параметры. Внутри конструктора
    //присходит инициализация коллекции symbolSet именно в таком порядке
    //как указано задано в конструкторе!
    public AffinCode() {
        //Обработка и инициализация symbolSet прописными буквами латинского алфавита
        for(char chr = 'A';chr <= 'Z'; chr++) symbolSet.add(chr);
        //...строчными буквами латинского алфавита
        for(char chr = 'a';chr <= 'z'; chr++) symbolSet.add(chr);
        //... буквами русского алфавита    
        for (char chr = 'А'; chr <= 'я'; chr++) {
            if(chr == 'Е'){
            symbolSet.add(chr);
            symbolSet.add('Ё');
            } else if(chr == 'е'){
            symbolSet.add(chr);
            symbolSet.add('ё');
            } else symbolSet.add(chr);    
        }
        //...инициализация цифрами
        for (char chr = '0'; chr <= '9'; chr++)  symbolSet.add(chr);
        //Обработка в стиле foreach и инициализация symbolSet символами 
        for (char chr : SYMBOL) symbolSet.add(chr);
    }
/**
 * Метод шифрования текста.
 * @param text Исходный текст для шифрования
 * @param a Первая часть ключа афинной системы подстановок
 * @param k Вторая часть ключа афинной системы подстановок
 * @return Возвращает зашифрованный текст
 */
    public String encoding(int a, int k, String text) {
        int size = symbolSet.size();
        System.out.println("size = " + symbolSet.size());
        a = a % size; //проверка, a не может быть больше size. 
        k = k % size; //проверка, k не может быть больше size.
        //проверка простоты size относительно a.
        //Если вернется не 1, то числа не взаимно простые и шифрование не возможно.
        if (greatestCommonDivisor(size, a) != 1) { 
            return null;
        }
        //Объект code необходим для динамического добавления зашифрованного симлова
        StringBuffer code = new StringBuffer();
        //Шифрование данных
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if(c==' ') code.append(' ');
            else {
            int idx = symbolSet.indexOf(c);
            idx = (idx * a + k) % size;
            code.append(symbolSet.get(idx));
            }
        }
        return code.toString();
    }
    /**
 * Метод дешифрования текста.
 * @param text Исходный текст для дешифрования
 * @param a Первая часть ключа афинной системы подстановок
 * @param k Вторая часть ключа афинной системы подстановок
 * @return Возвращает расшифрованный текст
 */
    public String decryption(int a, int k, String text){
        int size = symbolSet.size();
        a = a % size;
        k = k % size;
        //проверка простоты size относительно a.
        //Если вернется не 1, то числа не взаимно простые и шифрование не возможно.
        if (greatestCommonDivisor(size, a) != 1) {
            return null;
        }
        int reverse = -1;//обратное к a
        //ищем обратное к a
        for (int i = 0; i < size; i++) {
            if ((i * a) % size == 1) {
                reverse = i;
                break;
            }
        }
        //Объект decode необходим для динамического добавления рашифрованного симлова
        StringBuffer decode = new StringBuffer();
        //Дешифрование данных
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if(c==' ') decode.append(' ');
            else {
            int idx = symbolSet.indexOf(c);
            idx = (((idx - k) * reverse) % size + size) % size;
            decode.append(symbolSet.get(idx));
            }
        }
        return decode.toString();
    }
 /**
 * Метод нахождения наибольшего общего делителя.
 * @param a - переменные для поиска наибольшего общего делителя. В нашем случае
 *  @param b - переменные для поиска наибольшего общего делителя. В нашем случае
 * это размер алфавита (size) и одна из переменных ключа (a)
  * @return Возвращает наибольший общий делитель
 */
    private int greatestCommonDivisor(int a, int b) {
        while (a > 0 && b > 0) {
            if (a > b) {
                a %= b;
            } else {
                b %= a;
            }
        }
        return a + b;
    }
    
    public int getSymbolSet() {
        return symbolSet.size();
    }
}