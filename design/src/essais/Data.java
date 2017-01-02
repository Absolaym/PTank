package essais;

public class Data {
	// classe qui sert à la communication entre States, faute de mieux
	
	private static Tank tank;
	
	public static Tank getTank(float x, float y) {
		
		if(tank == null) {
			tank = new Tank(x, y);
		}
		tank.moveTo(x, y);
		
		return tank;
	}
	
	public static void setTank(Tank tk) {
		tank = tk;
	}
	
	private Data() {
		
	}
}
