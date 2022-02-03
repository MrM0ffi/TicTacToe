package Spaghetti;

import java.util.Scanner;

// ---------------------------- Table of contents ---------------------------- \\
//      1.0 Start of Code                                                      \\
//      2.0 METHODS                                                            \\
//      2.1 Enemy "AI"                                                         \\
// ----------------------------- Code by Mr.Moffi ---------------------------- \\

public class Main {
    public static void main(String[] args) throws InterruptedException {

//---------------------------- 1.0 Start of Code ---------------------------- \\
        String[][] playground = new String[5][5];
        boolean winnerFound;
        String andTheWinnerIs;
        boolean playNextRound = true;

        clearPlayground(playground);
        drawPlayground(playground);

        int[] statistik = {0, 0, 0};

        do {

            int playerChosenRow = getPlayerRowOrColumn("row");
            int playerChosenColumn = getPlayerRowOrColumn("column");
            playerInputCheck(playerChosenRow, playerChosenColumn, playground);
            andTheWinnerIs = checkForWinner(playground);

            if (andTheWinnerIs.equalsIgnoreCase("Player")) {
                System.out.println("Du hast gewonnen!");
                winnerFound = true;
                playNextRound = noNextRound();
                statistik[0] += 1;
            } else if (andTheWinnerIs.equalsIgnoreCase("Computer")) {
                System.out.println("Der Computer hat gewonnen!");
                winnerFound = true;
                playNextRound = noNextRound();
                statistik[1] += 1;
            } else if (andTheWinnerIs.equalsIgnoreCase("Draw")) {
                System.out.println("Unentschieden!");
                winnerFound = true;
                playNextRound = noNextRound();
                statistik[2] += 1;
            } else {
                winnerFound = false;
            }

            if (playNextRound && winnerFound) {
                clearPlayground(playground);
                drawPlayground(playground);
                winnerFound = false;
            }

        } while (playNextRound && !winnerFound);

        System.out.printf("%n%nDanke fürs spielen! Hier noch deine Statistik:%n");
        System.out.printf(ConsoleColors.GREEN + "Spieler" + ConsoleColors.RESET + ": %d" +
                        " | %d " + ConsoleColors.BLUE + "Computer" + ConsoleColors.RESET + "" +
                        " | " + ConsoleColors.YELLOW + "Unentschieden" + ConsoleColors.RESET + ": %d"
                , statistik[0], statistik[1], statistik[2]);
    }

    // ---------------------------- 2.0 METHODS ---------------------------- \\
    public static void drawPlayground(String[][] x) {
        for (String[] strings : x) {
            for (String string : strings) {

                System.out.print(string + " ");

            }
            System.out.println();
        }
    }

    public static int getPlayerRowOrColumn(String RowOrColumn) {
        Scanner scanner = new Scanner(System.in);
        int playerChosenRowOrColumn;
        boolean rowInvalid = true;

        do {
            System.out.printf("%nIn welche %s möchtest du gerne dein " + ConsoleColors.GREEN + "Kreuz" + ConsoleColors.RESET + " setzen?%n",
                    RowOrColumn.equalsIgnoreCase("Row") ? ConsoleColors.YELLOW + "Zeile" + ConsoleColors.RESET
                            : ConsoleColors.YELLOW + "Spalte" + ConsoleColors.RESET);

            playerChosenRowOrColumn = scanner.nextInt();

            if (playerChosenRowOrColumn < 4 && playerChosenRowOrColumn > 0) {
                rowInvalid = false;
            } else {
                System.out.println(ConsoleColors.RED + "Bitte gib '1', '2' oder '3' ein." + ConsoleColors.RESET);
            }
        } while (rowInvalid);
        return playerChosenRowOrColumn;
    }

    public static void playerInputCheck(int row, int column, String[][] playground) throws InterruptedException {
        if (playground[row + 1][column + 1].equals(" ")) {
            playground[row + 1][column + 1] = ConsoleColors.GREEN + "x" + ConsoleColors.RESET;
            drawPlayground(playground);
            System.out.printf(ConsoleColors.GREEN + "Kreuz" + ConsoleColors.RESET + " gesetzt auf Feld %d | %d .%n", row, column);
            Thread.sleep(3000);
            if ((!checkForWinner(playground).equals("Player") && !checkForWinner(playground).equals("Computer") && !checkForWinner(playground).equals("Draw"))) {
                System.out.printf("%nDer " + ConsoleColors.BLUE + "Gegner" + ConsoleColors.RESET + " ist am Zug!%n%n");
                enemyTurn(playground);
            }

        } else {
            System.out.println();
            System.out.println(ConsoleColors.RED + "Dieses Feld ist bereits besetzt, bitte wähle ein anderes aus." + ConsoleColors.RESET);
        }
    }

    public static String checkForWinner(String[][] playground) {

        String winner = "no";
        String[] xOrO = {ConsoleColors.GREEN + "x" + ConsoleColors.RESET, ConsoleColors.BLUE + "o" + ConsoleColors.RESET};

        for (int i = 0; i < 2; i++) {
            if ((playground[2][2].equals(xOrO[i]) && playground[2][3].equals(xOrO[i]) && playground[2][4].equals(xOrO[i]))
                    || (playground[3][2].equals(xOrO[i]) && playground[3][3].equals(xOrO[i]) && playground[3][4].equals(xOrO[i]))
                    || (playground[4][2].equals(xOrO[i]) && playground[4][3].equals(xOrO[i]) && playground[4][4].equals(xOrO[i]))

                    || (playground[2][2].equals(xOrO[i]) && playground[3][2].equals(xOrO[i]) && playground[4][2].equals(xOrO[i]))
                    || (playground[2][3].equals(xOrO[i]) && playground[3][3].equals(xOrO[i]) && playground[4][3].equals(xOrO[i]))
                    || (playground[2][4].equals(xOrO[i]) && playground[3][4].equals(xOrO[i]) && playground[4][4].equals(xOrO[i]))

                    || (playground[4][2].equals(xOrO[i]) && playground[3][3].equals(xOrO[i]) && playground[2][4].equals(xOrO[i]))
                    || (playground[2][2].equals(xOrO[i]) && playground[3][3].equals(xOrO[i]) && playground[4][4].equals(xOrO[i]))
            )
                winner = i == 0 ? "Player" : "Computer";
        }

        if (winner.equals("no") && (!playground[2][2].equalsIgnoreCase(" ") &&
                !playground[2][3].equalsIgnoreCase(" ") &&
                !playground[2][4].equalsIgnoreCase(" ") &&

                !playground[3][2].equalsIgnoreCase(" ") &&
                !playground[3][3].equalsIgnoreCase(" ") &&
                !playground[3][4].equalsIgnoreCase(" ") &&

                !playground[4][2].equalsIgnoreCase(" ") &&
                !playground[4][3].equalsIgnoreCase(" ") &&
                !playground[4][4].equalsIgnoreCase(" "))
        )
            winner = "Draw";

        return winner;
    }

    public static void clearPlayground(String[][] playground) {

        playground[0][0] = " ";
        playground[0][1] = "|";
        playground[0][2] = "1";
        playground[0][3] = "2";
        playground[0][4] = "3";

        playground[1][0] = "─";
        playground[1][1] = "┼";
        playground[1][2] = "─";
        playground[1][3] = "─";
        playground[1][4] = "─";

        playground[2][0] = "1";
        playground[2][1] = "|";

        playground[3][0] = "2";
        playground[3][1] = "|";

        playground[4][0] = "3";
        playground[4][1] = "|";

        playground[2][2] = " ";
        playground[2][3] = " ";
        playground[2][4] = " ";

        playground[3][2] = " ";
        playground[3][3] = " ";
        playground[3][4] = " ";

        playground[4][2] = " ";
        playground[4][3] = " ";
        playground[4][4] = " ";

    }

    // ---------------------------- 2.1 Enemy "AI" ---------------------------- \\
    public static void enemyTurn(String[][] playground) throws InterruptedException {
        String[] xOrO = {ConsoleColors.BLUE + "o" + ConsoleColors.RESET, ConsoleColors.GREEN + "x" + ConsoleColors.RESET};
        String[] oOrX = {ConsoleColors.GREEN + "x" + ConsoleColors.RESET, ConsoleColors.BLUE + "o" + ConsoleColors.RESET};

        boolean validEntry = false;
        boolean winTrigger = false;

        int rowTrigger = 0;
        int columnTrigger = 0;

        for (int i = 0; i < 2; i++) {
            // Row 1 Check \\
            if ((playground[2][2].equals(xOrO[i]) && playground[2][3].equals(xOrO[i]) && playground[2][4].equals(" ")) && !winTrigger) {
                playground[2][4] = oOrX[1];
                winTrigger = true;
                rowTrigger = 1;
                columnTrigger = 3;
            } else if ((playground[2][2].equals(xOrO[i]) && playground[2][4].equals(xOrO[i]) && playground[2][3].equals(" ")) && !winTrigger) {
                playground[2][3] = oOrX[1];
                winTrigger = true;
                rowTrigger = 1;
                columnTrigger = 2;
            } else if ((playground[2][3].equals(xOrO[i]) && playground[2][4].equals(xOrO[i]) && playground[2][2].equals(" ")) && !winTrigger) {
                playground[2][2] = oOrX[1];
                winTrigger = true;
                rowTrigger = 1;
                columnTrigger = 1;
            }
            // Row 2 Check \\
            else if ((playground[3][2].equals(xOrO[i]) && playground[3][3].equals(xOrO[i]) && playground[3][4].equals(" ")) && !winTrigger) {
                playground[3][4] = oOrX[1];
                winTrigger = true;
                rowTrigger = 2;
                columnTrigger = 3;
            } else if ((playground[3][2].equals(xOrO[i]) && playground[3][4].equals(xOrO[i]) && playground[3][3].equals(" ")) && !winTrigger) {
                playground[3][3] = oOrX[1];
                winTrigger = true;
                rowTrigger = 2;
                columnTrigger = 2;
            } else if ((playground[3][3].equals(xOrO[i]) && playground[3][4].equals(xOrO[i]) && playground[3][2].equals(" ")) && !winTrigger) {
                playground[3][2] = oOrX[1];
                winTrigger = true;
                rowTrigger = 2;
                columnTrigger = 1;
            }
            // Row 3 Check \\
            else if ((playground[4][2].equals(xOrO[i]) && playground[4][3].equals(xOrO[i]) && playground[4][4].equals(" ")) && !winTrigger) {
                playground[4][4] = oOrX[1];
                winTrigger = true;
                rowTrigger = 3;
                columnTrigger = 3;
            } else if ((playground[4][2].equals(xOrO[i]) && playground[4][4].equals(xOrO[i]) && playground[4][3].equals(" ")) && !winTrigger) {
                playground[4][3] = oOrX[1];
                winTrigger = true;
                rowTrigger = 3;
                columnTrigger = 2;
            } else if ((playground[4][3].equals(xOrO[i]) && playground[4][4].equals(xOrO[i]) && playground[4][2].equals(" ")) && !winTrigger) {
                playground[4][2] = oOrX[1];
                winTrigger = true;
                rowTrigger = 3;
                columnTrigger = 1;
            }
            // Column 1 Check \\
            else if ((playground[2][2].equals(xOrO[i]) && playground[3][2].equals(xOrO[i]) && playground[4][2].equals(" ")) && !winTrigger) {
                playground[4][2] = oOrX[1];
                winTrigger = true;
                rowTrigger = 3;
                columnTrigger = 1;
            } else if ((playground[2][2].equals(xOrO[i]) && playground[4][2].equals(xOrO[i]) && playground[3][2].equals(" ")) && !winTrigger) {
                playground[3][2] = oOrX[1];
                winTrigger = true;
                rowTrigger = 2;
                columnTrigger = 1;
            } else if ((playground[3][2].equals(xOrO[i]) && playground[4][2].equals(xOrO[i]) && playground[2][2].equals(" ")) && !winTrigger) {
                playground[2][2] = oOrX[1];
                winTrigger = true;
                rowTrigger = 1;
                columnTrigger = 1;
            }
            // Column 2 Check \\
            else if ((playground[2][3].equals(xOrO[i]) && playground[3][3].equals(xOrO[i]) && playground[4][3].equals(" ")) && !winTrigger) {
                playground[4][3] = oOrX[1];
                winTrigger = true;
                rowTrigger = 3;
                columnTrigger = 2;
            } else if ((playground[2][3].equals(xOrO[i]) && playground[4][3].equals(xOrO[i]) && playground[3][3].equals(" ")) && !winTrigger) {
                playground[3][3] = oOrX[1];
                winTrigger = true;
                rowTrigger = 2;
                columnTrigger = 2;
            } else if ((playground[3][3].equals(xOrO[i]) && playground[4][3].equals(xOrO[i]) && playground[2][3].equals(" ")) && !winTrigger) {
                playground[2][3] = oOrX[1];
                winTrigger = true;
                rowTrigger = 1;
                columnTrigger = 2;
            }
            // Column 3 Check \\
            else if ((playground[2][4].equals(xOrO[i]) && playground[3][4].equals(xOrO[i]) && playground[4][4].equals(" ")) && !winTrigger) {
                playground[4][4] = oOrX[1];
                winTrigger = true;
                rowTrigger = 3;
                columnTrigger = 3;
            } else if ((playground[2][4].equals(xOrO[i]) && playground[4][4].equals(xOrO[i]) && playground[3][4].equals(" ")) && !winTrigger) {
                playground[3][4] = oOrX[1];
                winTrigger = true;
                rowTrigger = 2;
                columnTrigger = 3;
            } else if ((playground[3][4].equals(xOrO[i]) && playground[4][4].equals(xOrO[i]) && playground[2][4].equals(" ")) && !winTrigger) {
                playground[2][4] = oOrX[1];
                winTrigger = true;
                rowTrigger = 1;
                columnTrigger = 3;
            }
            // Diagonal 1 Check \\
            else if ((playground[4][2].equals(xOrO[i]) && playground[3][3].equals(xOrO[i]) && playground[2][4].equals(" ")) && !winTrigger) {
                playground[2][4] = oOrX[1];
                winTrigger = true;
                rowTrigger = 1;
                columnTrigger = 3;
            } else if ((playground[4][2].equals(xOrO[i]) && playground[2][4].equals(xOrO[i]) && playground[3][3].equals(" ")) && !winTrigger) {
                playground[3][3] = oOrX[1];
                winTrigger = true;
                rowTrigger = 2;
                columnTrigger = 2;
            } else if ((playground[3][3].equals(xOrO[i]) && playground[2][4].equals(xOrO[i]) && playground[4][2].equals(" ")) && !winTrigger) {
                playground[4][2] = oOrX[1];
                winTrigger = true;
                rowTrigger = 3;
                columnTrigger = 1;
            }
            // Diagonal 2 Check \\
            else if ((playground[2][2].equals(xOrO[i]) && playground[3][3].equals(xOrO[i]) && playground[4][4].equals(" ")) && !winTrigger) {
                playground[4][4] = oOrX[1];
                winTrigger = true;
                rowTrigger = 3;
                columnTrigger = 3;
            } else if ((playground[2][2].equals(xOrO[i]) && playground[4][4].equals(xOrO[i]) && playground[3][3].equals(" ")) && !winTrigger) {
                playground[3][3] = oOrX[1];
                winTrigger = true;
                rowTrigger = 2;
                columnTrigger = 2;
            } else if ((playground[3][3].equals(xOrO[i]) && playground[4][4].equals(xOrO[i]) && playground[2][2].equals(" ")) && !winTrigger) {
                playground[2][2] = oOrX[1];
                winTrigger = true;
                rowTrigger = 1;
                columnTrigger = 1;
            }

        }
        if (!winTrigger) {
            do {
                int row = (int) (Math.random() * 3) + 1;
                int column = (int) (Math.random() * 3) + 1;

                if (playground[row + 1][column + 1].equals(" ")) {
                    playground[row + 1][column + 1] = ConsoleColors.BLUE + "o" + ConsoleColors.RESET;
                    Thread.sleep(2000);
                    drawPlayground(playground);
                    System.out.printf("Er setzt seinen " + ConsoleColors.BLUE + "Kreis" + ConsoleColors.RESET + " auf %d | %d !%n", row, column);
                    Thread.sleep(2000);
                    validEntry = true;
                }
            } while (!validEntry);

        } else {
            Thread.sleep(2000);
            drawPlayground(playground);
            System.out.printf("Er setzt seinen " + ConsoleColors.BLUE + "Kreis" + ConsoleColors.RESET + " auf %d | %d !%n", rowTrigger, columnTrigger);
            Thread.sleep(2000);
        }
    }

    public static boolean noNextRound() {
        Scanner scanner = new Scanner(System.in);
        String wantNextRound;
        boolean playNextRound = true;
        boolean validEntry = false;

        do {
            System.out.printf("%nMöchtest du eine weitere Runde spielen? Schreibe 'Ja' oder 'Nein'.%n");
            wantNextRound = scanner.nextLine();
            if (wantNextRound.equalsIgnoreCase("ja")) {
                validEntry = true;
            } else if (wantNextRound.equalsIgnoreCase("nein")) {
                playNextRound = false;
                validEntry = true;
            }
        } while (!validEntry);
        return playNextRound;
    }
}