package PTank.Entities;

import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

public class GridShape {
	
	public static Shape get(float x, float y, int nbSeg, float catWidth) {
		float offset;
		float pts[];
	
		
		offset = 0;		
		pts = new float[nbSeg * 8];
		
		for(int i = 0; i < nbSeg*2; i++) {			
			pts[i*4] = x + offset;
			pts[i*4+2] = x + offset;
			
			offset = (offset == 0) ? catWidth : 0;	
		}
		
		pts[1] = y;
		
		for(int i = 1; i <= nbSeg; i++) {
			pts[i*4-1] = y + catWidth*i;
			pts[i*4+1] = y + catWidth*i;
		}
		
		for(int i = nbSeg-1; i > 0; i--) {
			pts[(i+nbSeg)*4-1] = y + catWidth*(nbSeg-i);
			pts[(i+nbSeg)*4+1] = y + catWidth*(nbSeg-i);
		}
		
		pts[nbSeg*8-1] = y;
		
		return new Polygon(pts);
	}
}
