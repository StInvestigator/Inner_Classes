package com.project.models;

import com.project.enums.Genre;

public class Game {

    private enum Type{
        VIRTUAL,
        PHYSICAL;

        @Override
        public java.lang.String toString() {
            return switch (this.ordinal()) {
                case 0 -> "Virtual";
                case 1 -> "Physical";
                default -> "";
            };
        }
    }

    private final String name;
    private final Genre genre;
    private final Type type;

    private Game(String name, Genre genre, Type type) {
        this.name = name;
        this.genre = genre;
        this.type = type;
    }

    public static GameDisk getDisk(String name, Genre genre, String description) {
        return new GameDisk(name, genre, description);
    }

    public static VirtualGame getVirtualGame(String name, Genre genre) {
        return new VirtualGame(name, genre);
    }

    public static class GameDisk{
        private final String description;
        private final Game data;

        private GameDisk(String name, Genre genre, String description) {
            this.data = new Game(name, genre, Type.PHYSICAL);
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public Game getData() {
            return data;
        }

        public String getName() {
            return data.name;
        }

        public Genre getGenre() {
            return data.genre;
        }

        public String getType() {
            return data.type.toString();
        }
    }

    public static class VirtualGame{
        private short rating;
        private final Game data;

        private VirtualGame(String name, Genre genre) {
            this.data = new Game(name, genre, Type.VIRTUAL);
            this.rating = 0;
        }

        public short getRating() {
            return rating;
        }

        public void setRating(short rating) {
            this.rating = rating;
        }

        public Game getData() {
            return data;
        }

        public String getName() {
            return data.name;
        }

        public Genre getGenre() {
            return data.genre;
        }

        public String getType() {
            return data.type.toString();
        }
    }

    public String getName() {
        return name;
    }

    public Genre getGenre() {
        return genre;
    }

    public String getType() {
        return type.toString();
    }
}
