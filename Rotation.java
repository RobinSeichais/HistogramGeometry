import java.awt.Color;
import java.awt.image.BufferedImage;


public class Rotation {

	private double theta;
	private double x, y, z;
	private BufferedImage img;
	private int height, width;
	private double[][] mat;
	
	public Rotation(double x, double y, double z, double t){
		
		this.theta = t;
		this.img = null;
		this.height = this.width = 0;
		
		double c = Math.cos(this.theta);
		double s = Math.sin(this.theta);
		
		mat = new double[][] {
				{x*x + (1 - x*x)*c, x*y*(1 - c) - z*s, x*z*(1 - c) + y*s},
				{x*y*(1 - c) + z*s, y*y + (1 - y*y)*c, y*z*(1 - c) - x*s},
				{ x*z*(1 - c) - y*s, y*z*(1 - c) + x*s, z*z + (1 - z*z)*c}
		};
	}
	
	public BufferedImage get(BufferedImage img){
		
		this.img = img;

		this.width = this.img.getWidth();
		this.height = this.img.getHeight();
		
		BufferedImage res = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
		
		int r, g, b;
		Color c, d;
		
		for(int i = 0; i < this.width; i++){
			
			for(int j = 0; j < this.height; j++){
				
				c = new Color(this.img.getRGB(i, j));
				r = c.getRed();
				g = c.getGreen();
				b = c.getBlue();
				

				if(i == 128 && j == 128){
					
					System.out.println(r + " - " + g + " - " + b);
				}
				
				Point p = new Point(r, g, b);
				Point np = rotate(p);
				
				r = (int) Math.max(0, Math.min(255, Math.round(np.x)));
				g = (int) Math.max(0, Math.min(255, Math.round(np.y)));
				b = (int) Math.max(0, Math.min(255, Math.round(np.z)));
				

				if(i == 128 && j == 128){
					
					System.out.println(r + " - " + g + " - " + b + " / " + this.theta);
				}
				
				d = new Color(r, g, b, 255);
				res.setRGB(i, j, d.getRGB());
			}	
		}
		
		return res;
	}
	
	private Point rotate(Point p){
		
		p.x -= 127;
		p.y -= 127;
		p.z -= 127;
		
		Point res = new Point();

		res.x = p.x * this.mat[0][0] + p.y * this.mat[0][1] + p.z * this.mat[0][2];
		res.y = p.x * this.mat[1][0] + p.y * this.mat[1][1] + p.z * this.mat[1][2];
		res.z = p.x * this.mat[2][0] + p.y * this.mat[2][1] + p.z * this.mat[2][2];
		
		res.x += 127;
		res.y += 127;
		res.z += 127;
		
		return res;
	}
	
	private class Point{
		
		public double x, y, z;
		
		public Point(){
			this.x = this.y = this.z = 0;
		}
		
		public Point(double x, double y, double z){
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
		public Point(int x, int y, int z){
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}
}
