package com.project;

import com.project.enums.Brand;
import com.project.enums.Genre;
import com.project.exceptions.ConsoleInactiveException;
import com.project.exceptions.ConsoleIsOffException;
import com.project.models.Game;
import com.project.models.GameConsole;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class PlayRoom {
    public static void play(GameConsole console, Game game) {
        try {
            console.playGame();
            console.getSecondGamepad().PowerOn(); // Второй геймпад становится первым
            System.out.println("Gamepad connected");
            console.playGame();
            console.loadGame(game);
            console.playGame();
            console.playGame();
            console.playGame();

            console.getSecondGamepad().PowerOn(); // Геймпад что раньше был первым включается
            console.getFirstGamepad().PowerOff(); // Второй становится первым
            console.getSecondGamepad().PowerOn(); // Снова включаем второй
            System.out.println("Second gamepad connected (and they are switched places)");

            for (int i = 0; i < 100; i++) {
                console.playGame();
            }
        } catch (ConsoleInactiveException e) {
            System.out.println(e.getMessage());
        } catch (ConsoleIsOffException e) {
            System.out.println(e.getMessage());
            System.out.println("Power on the console? (Y/N)");
            Scanner scanner = new Scanner(System.in);
            if (scanner.nextLine().equalsIgnoreCase("y")) {
                console.PowerOn();
                System.out.println("Console is on");
                play(console, game);
            }
        }
    }

    public static void main(String[] args) {
        Game.GameDisk[] disks = new Game.GameDisk[]{
                Game.getDisk("God of War: Ragnarok", Genre.ACTION, "God of War Ragnarök " +
                        "is an action-adventure game where players take the role of Kratos and his son " +
                        "as they embark on a quest to prevent the coming of Ragnarök. Players explore " +
                        "the Nine Realms, battling enemies in close-up, hand-to-hand combat against " +
                        "human-like raiders and fantastical creatures."),
                Game.getDisk("Minecraft", Genre.SANDBOX, "Steve mines blocs"),
                Game.getDisk("Need for Speed", Genre.RACE, "Game about races and cars in general"),
                Game.getDisk("Baldur Gates 3", Genre.RPG, "Game based on popular board game D&D")
        };

        Game.VirtualGame[] virtualGames = new Game.VirtualGame[]{
                Game.getVirtualGame("Spider-man", Genre.ACTION),
                Game.getVirtualGame("Dead by Daylight", Genre.HORROR),
                Game.getVirtualGame("Counter-Strike 2", Genre.SHOOTER),
                Game.getVirtualGame("Portal", Genre.STORY)
        };

        GameConsole console = new GameConsole(Brand.Sony, "XC123QeWR");

        Arrays.sort(disks, Comparator.comparing(Game.GameDisk::getGenre));
        Arrays.sort(virtualGames, Comparator.comparingInt(Game.VirtualGame::getRating));

        play(console,disks[0].getData()); // метод для проверки работы методов консоли и исключений
    }
}