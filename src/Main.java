import java.util.Scanner;

public class Main {
    private static boolean isRunning;
    private static Cryptography cryptography = new Cryptography();

    public static void main(String[] args) {
        isRunning = true;

        while (isRunning) {
            displayMenu();
            switch (getValueShowMessage()) {
                case 1:
                    System.out.println("Введите ключ шифрования");
                    cryptography.encrypt(getValueShowMessage());
                    break;
                case 2:
                    System.out.println("Введите ключ шифрования");
                    cryptography.decryptToFile(getValueShowMessage());
                    break;
                case 3:
                    System.out.println("Ключ шифрования :" + cryptography.bruteForce());
                    break;
                case 4:
                    isRunning = false;
                    break;
                default:
                    System.out.println("Введите цифру от 1 до 4");
            }
        }
    }

    private static int getValueShowMessage() {
        Scanner scanner = new Scanner(System.in);
        int value;
        try {
            value = scanner.nextInt();
        } catch (Exception ex) {
            System.out.println("Введите цифру от 1 до 4");
            value = getValueShowMessage();
        }
        return value;
    }

    private static void displayMenu() {
        System.out.println("Введите номер: ");
        System.out.println("1. Зашифровать текст?");
        System.out.println("2. Расшифровать текст?");
        System.out.println("3. Подобрать пароль");
        System.out.println("4. ВЫХОД");
    }
}