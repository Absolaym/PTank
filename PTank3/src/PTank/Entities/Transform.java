package PTank.Entities;

import org.newdawn.slick.geom.Shape;

public class Transform {
	public static Shape translate(Shape shp, float x, float y) {
		return shp.transform(org.newdawn.slick.geom.Transform.createTranslateTransform(x, y));
	}
	
	public static Shape rotate(Shape shp, Float angle, float x, float y) {
		return shp.transform(org.newdawn.slick.geom.Transform.createRotateTransform(angle, x, y));
	}
}
