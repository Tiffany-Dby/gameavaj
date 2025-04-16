package fr.sup_de_vinci.gameavaj.enums;

public enum Direction {
    UP(0, 1), RIGHT(1, 0), DOWN(0, -1), LEFT(-1, 0), NONE(0, 0);

    public final static Direction[] ALL = { Direction.DOWN, Direction.UP, Direction.LEFT, Direction.RIGHT };
    private int dx, dy;

    private Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

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

    public Coord getNext(Coord pos) {
        return pos.clone().move(this.dx, this.dy);
    }
}
