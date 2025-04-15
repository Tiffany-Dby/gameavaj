package fr.sup_de_vinci.gameavaj.utils;

public enum Direction {
    UP, RIGHT, DOWN, LEFT;

    public Direction getOpposite() {
        switch (this) {
            case UP: return DOWN;
            case DOWN: return UP;
            case LEFT: return RIGHT;
            case RIGHT: return LEFT;
        }
        return this;
    }

    public Direction turnLeft() {
        switch (this) {
            case UP: return LEFT;
            case LEFT: return DOWN;
            case DOWN: return RIGHT;
            case RIGHT: return UP;
        }
        return this;
    }

    public Direction turnRight() {
        switch (this) {
            case UP: return RIGHT;
            case RIGHT: return DOWN;
            case DOWN: return LEFT;
            case LEFT: return UP;
        }
        return this;
    }
}

