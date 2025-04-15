package fr.sup_de_vinci.gameavaj.enums;

public enum Direction {
    UP, RIGHT, DOWN, LEFT, NONE;

    public Direction getOpposite() {
        switch (this) {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            case NONE:
            default:
                return NONE;
        }
    }

    public Direction turnLeft() {
        switch (this) {
            case UP:
                return LEFT;
            case LEFT:
                return DOWN;
            case DOWN:
                return RIGHT;
            case RIGHT:
                return UP;
            case NONE:
            default:
                return NONE;
        }
    }

    public Direction turnRight() {
        switch (this) {
            case UP:
                return RIGHT;
            case RIGHT:
                return DOWN;
            case DOWN:
                return LEFT;
            case LEFT:
                return UP;
            case NONE:
            default:
                return NONE;
        }
    }
}
