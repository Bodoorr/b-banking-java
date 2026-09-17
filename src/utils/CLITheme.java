package utils;

import static org.fusesource.jansi.Ansi.ansi;

public class CLITheme {

    public static void showLogo() {

        String[] logo = {
                "╔══════════════════════════════════════╗",
                "║                B-BANK                ║",
                "║          YOUR BANKING SYSTEM         ║",
                "╚══════════════════════════════════════╝"
        };

        int terminalWidth = 80;

        for (String line : logo) {
            int spaces = (terminalWidth - line.length()) / 2;

            System.out.println(
                    " ".repeat(spaces) +
                            ansi().fgMagenta().bold().a(line).reset()
            );
        }
    }

    public static void showTitle(String title) {
        int terminalWidth = 80;

        String text = "========== " + title + " ==========";
        int spaces = (terminalWidth - text.length()) / 2;

        System.out.println(
                " ".repeat(spaces) +
                        ansi().fgCyan().bold()
                                .a(text)
                                .reset()
        );
    }

    public static void success(String message) {
        System.out.println(
                ansi().fgGreen().a(message).reset()
        );
    }

    public static void error(String message) {
        System.out.println(
                ansi().fgRed().a(message).reset()
        );
    }

    public static void option(String number, String text) {
        System.out.println(
                ansi().fgYellow()
                        .a("[" + number + "] ")
                        .reset()
                        .a(text)
        );
    }
    public static void info(String message) {
        System.out.println(
                ansi().fgCyan()
                        .a("ℹ " + message)
                        .reset()
        );
    }

    public static void goodbye(String name) {
        System.out.println(
                ansi().fgMagenta().bold()
                        .a("\nThank you for using B-Bank, " + name + "!")
                        .reset()
        );
        System.out.println("Have a great day.");
    }
}