import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class Main {

//6
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                Виберіть завдання:
                1. Обертовий чотирикутник
                2. Матриця
                """);
        System.out.print("Ваш вибір: ");
    int choise = scanner.nextInt();
        First first = new First();
        second second = new second();

    switch (choise){
        case 1 -> first.first();
        case 2 -> second.second();
    }
    }
}
