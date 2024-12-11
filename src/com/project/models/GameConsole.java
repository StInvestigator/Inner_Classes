package com.project.models;

import com.project.enums.Brand;
import com.project.enums.Color;
import com.project.exceptions.ConsoleInactiveException;
import com.project.exceptions.ConsoleIsOffException;
import com.project.interfaces.Powered;

public class GameConsole implements Powered {
    private final Brand brand;
    private final String serial;
    private boolean isOn = false;
    private Gamepad firstGamepad;
    private Gamepad secondGamepad;
    private Game activeGame;
    private short waitingCounter = 0;

    public GameConsole(Brand brand, String serial) {
        this.brand = brand;
        this.serial = serial;

        firstGamepad = new Gamepad(brand, 1, Color.BLACK);
        secondGamepad = new Gamepad(brand, 2, Color.GREEN);
    }

    void checkStatus() throws ConsoleInactiveException, ConsoleIsOffException {
        if (isOn) {
            if (!firstGamepad.isOn && !secondGamepad.isOn) {
                waitingCounter++;
                if (waitingCounter == 6) {
                    PowerOff();
                    throw new ConsoleInactiveException("The console is shutting down due to lack of activity");
                }
            } else {
                waitingCounter = 0;
            }
        } else throw new ConsoleIsOffException("Cant do anything with console being off");
    }

    public void loadGame(Game game) {
        checkStatus();
        if (firstGamepad.isOn || secondGamepad.isOn) {
            activeGame = game;
            System.out.println("Game \"" + activeGame.getName() + "\" is loading");
        } else System.out.println("Cant load the game without any gamepad connected");
    }

    public void playGame() {
        checkStatus();
        if (firstGamepad.isOn || secondGamepad.isOn) {
            if (activeGame == null) {
                System.out.println("You need to load the game first");
                return;
            }
            System.out.println("Playing in \"" + activeGame.getName() + "\"");
            if (secondGamepad.isOn) {
                secondGamepad.setChargeLevel(secondGamepad.chargeLevel - 10);
            }
            if (firstGamepad.isOn) {
                firstGamepad.setChargeLevel(firstGamepad.chargeLevel - 10);
                System.out.println("First gamepad current charge level: " + firstGamepad.chargeLevel);
            }
            if (secondGamepad.isOn) {
                System.out.println("Second gamepad current charge level: " + secondGamepad.chargeLevel);
            }
        } else System.out.println("Cant play without any gamepad connected");
    }

    @Override
    public void PowerOn() {
        isOn = true;
    }

    @Override
    public void PowerOff() {
        isOn = false;
    }

    public class Gamepad implements Powered {
        private final Brand brand;
        private final String consoleSerial;
        private int connectedNumber;
        private final Color color;
        private float chargeLevel = 100.0F;
        private boolean isOn = false;

        public Gamepad(Brand brand, int connectedNumber, Color color) {
            this.brand = brand;
            this.connectedNumber = connectedNumber;
            consoleSerial = serial;
            this.color = color;
        }

        @Override
        public void PowerOn() {
            isOn = true;
            if (!GameConsole.this.isOn) {
                GameConsole.this.PowerOn();
            }
            if (connectedNumber == 2 && !firstGamepad.isOn) {
                firstGamepad.setConnectedNumber(2);
                secondGamepad = firstGamepad;
                firstGamepad = this;
                connectedNumber = 1;
            }
        }

        @Override
        public void PowerOff() {
            isOn = false;
            if (connectedNumber == 1 && secondGamepad.isOn) {
                secondGamepad.setConnectedNumber(1);
                firstGamepad = secondGamepad;
                secondGamepad = this;
                connectedNumber = 2;
            }
        }

        public Brand getBrand() {
            return brand;
        }

        public String getConsoleSerial() {
            return consoleSerial;
        }

        public int getConnectedNumber() {
            return connectedNumber;
        }

        public void setConnectedNumber(int connectedNumber) {
            this.connectedNumber = connectedNumber;
        }

        public Color getColor() {
            return color;
        }

        public float getChargeLevel() {
            return chargeLevel;
        }

        public void setChargeLevel(float chargeLevel) {
            if (chargeLevel <= 0.0F) {
                this.chargeLevel = 0.0F;
                PowerOff();
                return;
            }
            if (chargeLevel > 100.0F) {
                this.chargeLevel = 100.0F;
                return;
            }
            this.chargeLevel = chargeLevel;
        }

        public boolean isOn() {
            return isOn;
        }
    }

    public Brand getBrand() {
        return brand;
    }

    public String getSerial() {
        return serial;
    }

    public boolean isOn() {
        return isOn;
    }

    public Gamepad getFirstGamepad() {
        return firstGamepad;
    }

    public void setFirstGamepad(Gamepad firstGamepad) {
        this.firstGamepad = firstGamepad;
    }

    public Gamepad getSecondGamepad() {
        return secondGamepad;
    }

    public void setSecondGamepad(Gamepad secondGamepad) {
        this.secondGamepad = secondGamepad;
    }
}
