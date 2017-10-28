import processing.core.PApplet;

public class Main extends PApplet {
    Block[][] map = new Block[Constants.MAP_WIDTH][Constants.MAP_HEIGHT];
    Image img;
    BomberMan p1;
    Bomb[] bombs = new Bomb[6];
    public static void main(String[] args) {
        PApplet.main("Main");
        Main m = new Main();
    }

    @Override
    public void settings() {
        this.size(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

    }

    @Override
    public void setup() {
        this.background(51,102,0);
        img = new Image(this);
        makeMap();
        makeBomberMan();
    }

    @Override
    public void draw() {
        this.background(51,102,0);
        drawMap();
        if(keyPressed) {
            if (keyCode == RIGHT) p1.goRight(map, this);
            else if (keyCode == LEFT) p1.goLeft(map, this);
            else if (keyCode == DOWN) p1.goDown(map, this);
            else if (keyCode == UP) p1.goUP(map, this);
        } else p1.draw(this);
        drawBomb();
    }

    @Override
    public void keyPressed() {
        if(keyCode == 'm') {
            bombs[0] = new Bomb(p1.position, 1, img);
        }
    }

    @Override
    public void keyReleased() {

    }

    public void makeMap() {
        for (int y = 0; y < Constants.MAP_HEIGHT; y++) {
            for (int x = 0; x < Constants.MAP_WIDTH; x++) {
                Block.Types type;
                if (x == 0 || y == 0 || x == Constants.MAP_WIDTH - 1 || y == Constants.MAP_HEIGHT - 1) {
                    type = Block.Types.UNBREAKABLE;
                } else if (x % 2 == 0 && y % 2 == 0) {
                    type = Block.Types.UNBREAKABLE;
                } else if (Math.random() < 0.3) {
                    type = Block.Types.BREAKABLE;
                } else {
                    type = Block.Types.ABSENCE;
                }
                map[x][y] = new Block(x, y, type, img);

            }
        }
        map[1][1] = new Block(1, 1, Block.Types.ABSENCE, img);
        map[1][2] = new Block(1, 2, Block.Types.ABSENCE, img);
        map[2][1] = new Block(2, 1, Block.Types.ABSENCE, img);
        map[Constants.MAP_WIDTH - 2][Constants.MAP_HEIGHT - 2] = new Block(Constants.MAP_WIDTH - 2, Constants.MAP_HEIGHT - 2, Block.Types.ABSENCE, img);
        map[Constants.MAP_WIDTH - 2][Constants.MAP_HEIGHT - 3] = new Block(Constants.MAP_WIDTH - 2, Constants.MAP_HEIGHT - 3, Block.Types.ABSENCE, img);
        map[Constants.MAP_WIDTH - 3][Constants.MAP_HEIGHT - 2] = new Block(Constants.MAP_WIDTH - 3, Constants.MAP_HEIGHT - 1, Block.Types.ABSENCE, img);

        drawMap();
    }

    public void drawMap() {
        for (int y = 0; y < Constants.MAP_HEIGHT; y++) {
            for (int x = 0; x < Constants.MAP_WIDTH; x++) {
                map[x][y].draw(this, map[x][y].type);
            }
        }
    }

    public void makeBomberMan() {
        p1 = new BomberMan(1,1, img);
    }

    public void drawBomb() {
        if(bombs[0]==null){
            return;
        }
        bombs[0].draw(this);

    }
}
