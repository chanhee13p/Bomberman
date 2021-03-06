import processing.core.PApplet;

public class BomberMan {
    private Position position;
    private Image img;
    private int tick = 0;
    private int state = 0;
    private int headMove = 0;
    private int headStay = 0;
    private float speed = 0.05f;
    private int power = 1;
    private int numberOfBomb = 1;
    private boolean dead = false;

    private boolean isLeftGo = false;
    private boolean isRightGo = false;
    private boolean isUpGo = false;
    private boolean isDownGo = false;


    BomberMan(int x, int y, Image img) {
        this.position = new Position(x, y);
        this.img = img;
    }

    public Position getPosition() {
        return position;
    }


    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        if (speed >= 0.2f) return;
        this.speed = speed;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        if (power >= 3) return;
        this.power = power;
    }

    public int getNumberOfBomb() {
        return numberOfBomb;
    }

    public void setNumberOfBomb(int numberOfBomb) {
        if (numberOfBomb >= 3) return;
        this.numberOfBomb = numberOfBomb;
    }

    public boolean isLeftGo() {
        return isLeftGo;
    }

    public void setLeftGo(boolean leftGo) {
        isLeftGo = leftGo;
    }

    public boolean isRightGo() {
        return isRightGo;
    }

    public void setRightGo(boolean rightGo) {
        setAllgoFalse();
        isRightGo = rightGo;
    }

    public void setAllgoFalse() {
        isRightGo = false;
        isLeftGo = false;
        isDownGo = false;
        isUpGo = false;
    }

    public boolean isUpGo() {
        return isUpGo;
    }

    public void setUpGo(boolean upGo) {
        isUpGo = upGo;
    }

    public boolean isDownGo() {
        return isDownGo;
    }

    public void setDownGo(boolean downGo) {
        isDownGo = downGo;
    }

    public void goLeft(Block[][] map) {
        position.setX(position.getX() - speed);
        if (detectCollisionWithBlock(map)) position.setX(position.getX() + speed);
        headMove = 15;
        headStay = 9;
    }

    public void goRight(Block[][] map) {
        position.setX(position.getX() + speed);
        if (detectCollisionWithBlock(map)) position.setX(position.getX() - speed);
        headMove = 5;
        headStay = 3;
    }

    public void goUP(Block[][] map) {
        position.setY(position.getY() - speed);
        if (detectCollisionWithBlock(map)) position.setY(position.getY() + speed);
        headMove = 10;
        headStay = 6;
    }

    public void goDown(Block[][] map) {
        position.setY(position.getY() + speed);
        if (detectCollisionWithBlock(map)) position.setY(position.getY() - speed);
        headMove = 0;
        headStay = 0;
    }

    public boolean isDead() {
        return dead;
    }

    public void isDead(boolean dead) {
        this.dead = dead;
    }

    public boolean detectCollisionWithBlock(Block[][] map) {
        int x1 = (int) (position.getX() + Constants.PLAYER_EXTRA_X);
        int x2 = (int) (position.getX() + 0.6); // 사이즈 맨 밑에
        int y1 = (int) (position.getY() + Constants.PLAYER_EXTRA_Y);
        int y2 = (int) (position.getY() + 0.8);

        Block.Types brk = Block.Types.BREAKABLE;
        Block.Types unbrk = Block.Types.UNBREAKABLE;
        if (map[x1][y1].getType() == brk || map[x1][y1].getType() == unbrk) return true;
        if (map[x1][y2].getType() == brk || map[x1][y2].getType() == unbrk) return true;
        if (map[x2][y1].getType() == brk || map[x2][y1].getType() == unbrk) return true;
        if (map[x2][y2].getType() == brk || map[x2][y2].getType() == unbrk) return true;

        return false;
    }

    public void speedUp() {
        if (speed > 0.2) return;
        speed += 0.05f;
    }

    public void powerUp() {
        if (power > 3) return;
        power += 1;
    }

    public void numberOfBombUp() {
        if (numberOfBomb > 3) return;
        numberOfBomb += 1;
    }

    public void setState() {
        state++;
        if (state > 4) {
            state = 0;
        }
    }


    public void draw(PApplet applet, Block[][] map) {
        if (isLeftGo) goLeft(map);
        if (isRightGo) goRight(map);
        if (isUpGo) goUP(map);
        if (isDownGo) goDown(map);

        tick++;
        if (applet.keyPressed) {
            setState();
            applet.image(img.characterMovements[headMove + (state / 5)], position.getX() * Constants.BLOCK_WIDTH, position.getY() * Constants.BLOCK_HEIGHT);
        } else {
            applet.image(img.characterStays[((tick / 10) % 3) + headStay], position.getX() * Constants.BLOCK_WIDTH, position.getY() * Constants.BLOCK_HEIGHT);
        }
    }

    public void getItem(Item item) {
        Item.Types itemType = item.getType();

        if (itemType == Item.Types.SPEED) speedUp();
        if (itemType == Item.Types.POWER) powerUp();
        if (itemType == Item.Types.NUMBER) numberOfBombUp();
    }
}
