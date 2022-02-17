package spacearena;

// controls enemy spawns and increases difficulty as time goes on
public class Spawning {
    
    private static double SPAWNREGION = 1000000.0;
    private static int count, seconds;
    
    public static void init() {
        count = 0; 
        seconds = 0;
    }
    
    public static void update() {
        // spawn an enemy right at the start
        if(seconds == 0 && count == 0) {
            Point location = getSpawnLocation();
            System.out.println("spawned BasicEnemyShip");
            Entities.addShip(new BasicEnemyShip(location.x, location.y));
        }
        count++;
        // try spawn an enemy every second
        if(count % 60 == 0) trySpawn();
    }
    
    // spawns enemies based on difficulty
    public static void trySpawn() {
        seconds++;
        if(seconds <= 10) {
            if(Tools.irand(1, 5) == 1) {
                Point location = getSpawnLocation();
                System.out.println("spawned BasicEnemyShip");
                Entities.addShip(new BasicEnemyShip(location.x, location.y));
            }
        } else if(seconds <= 30) {
            if(Tools.irand(1, 5) == 1) {
                Point location = getSpawnLocation();
                if(Tools.irand(1, 3) == 1) {
                    System.out.println("spawned BasicEnemyShip");
                    Entities.addShip(new BasicEnemyShip(location.x, location.y));
                } else if(Tools.irand(1, 2) == 1) {
                    System.out.println("spawned ChargingEnemyShip");
                    Entities.addShip(new ChargingEnemyShip(location.x, location.y));
                } else {
                    System.out.println("spawned MinelayerEnemyShip");
                    Entities.addShip(new MinelayerEnemyShip(location.x, location.y));                    
                }
            }
        } else if(seconds <= 45) {
            if(Tools.irand(1, 5) ==1 ) {
                Point location = getSpawnLocation();
                int r=Tools.irand(1, 4);
                if(r==1) {
                    System.out.println("spawned BasicEnemyShip");
                    Entities.addShip(new BasicEnemyShip(location.x, location.y));
                } else if(r==2) {
                    System.out.println("spawned ChargingEnemyShip");
                    Entities.addShip(new ChargingEnemyShip(location.x, location.y));
                } else if(r==3) {
                    System.out.println("spawned AdvancedEnemyShip");
                    Entities.addShip(new AdvancedEnemyShip(location.x, location.y));
                } else {
                    System.out.println("spawned MinelayerEnemyShip");
                    Entities.addShip(new MinelayerEnemyShip(location.x, location.y));
                }
            }
        } else {
            if(Tools.irand(1, 3) == 1) {
                Point location = getSpawnLocation();
                int r=Tools.irand(1, 4);
                if(r==1) {
                    System.out.println("spawned BasicEnemyShip");
                    Entities.addShip(new BasicEnemyShip(location.x, location.y));
                } else if(r==2) {
                    System.out.println("spawned ChargingEnemyShip");
                    Entities.addShip(new ChargingEnemyShip(location.x, location.y));
                } else if(r==3) {
                    System.out.println("spawned AdvancedEnemyShip");
                    Entities.addShip(new AdvancedEnemyShip(location.x, location.y));
                } else {
                    System.out.println("spawned MinelayerEnemyShip");
                    Entities.addShip(new MinelayerEnemyShip(location.x, location.y));
                }
            }
        }
    }
    
    // returns a location off screen to spawn enemy
    public static Point getSpawnLocation() {
        Point ret = new Point(Tools.drand(-2500, 2500), Tools.drand(-2500, 2500));
        while(Tools.sqDist(ret.x, ret.y, Main.player.xPos, Main.player.yPos)<SPAWNREGION) {
            ret.x=Tools.drand(-2500, 2500);
            ret.y=Tools.drand(-2500, 2500);
        }
        
        return ret;
    }
}
