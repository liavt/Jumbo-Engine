package com.jumbo.tools.calculations;

import java.awt.Dimension;
import java.awt.Rectangle;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import com.jumbo.components.FloatRectangle;
import com.jumbo.components.JumboColor;
import com.jumbo.components.Position;
import com.jumbo.core.Jumbo;
import com.jumbo.core.JumboEntity;
import com.jumbo.tools.JumboErrorHandler;
import com.jumbo.tools.JumboSettings;

public final class JumboMathHandler {
	public static float xmod = ((Jumbo.getFrameWidth() * 1.0f) / (1080 * 1.0f)), // for
			// rendering
			ymod = ((Jumbo.getFrameHeight() * 1.0f) / (720 * 1.0f));// for
	// rendering
	public static Dimension currentdim;

	// mapping
	// input

	public static boolean isPrime(int n) {
		if (n % 2 == 0) {
			return false;
		}
		for (int i = 3; i * i <= n; i += 2) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}

	public static int rgbToSRGB(JumboColor c) {
		return rgbToSRGB(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
	}

	public static int rgbToSRGB(int r, int g, int b) {
		return rgbToSRGB(r, g, b, 255);
	}

	public static int rgbToSRGB(int r, int g, int b, int a) {
		return ((a)) << 24 | (g) << 16 | ((b)) << 8 | ((r));
	}

	public static int floatToX(int number) {
		int num = number;
		num = JumboSettings.launchConfig.width() * (num / 2);
		return num;
	}

	public static int floatToY(int number) {
		int num = number;
		num = JumboSettings.launchConfig.height() * (num / 2);
		return num;
	}

	/**
	 * Returns the closest multiple of 2 there is to n
	 * 
	 * @param n
	 * @return closest multiple of 2
	 */
	public static int log2(int n) {
		int v = n;
		v--;
		v |= v >> 1;
		v |= v >> 2;
		v |= v >> 4;
		v |= v >> 8;
		v |= v >> 16;
		v++;
		return v;
	}

	public static Vector2f log2(Vector2f vec) {
		int result1 = JumboMathHandler.log2((int) vec.x);
		int result2 = JumboMathHandler.log2((int) vec.y);
		return new Vector2f(result1, result2);
	}

	public static Rectangle multiplyRectangle(Rectangle rect, float num) {
		return new Rectangle((int) (rect.x * num), (int) (rect.y * num), (int) (rect.width * num),
				(int) (rect.height * num));
	}

	public static Rectangle subtractRectangle(Rectangle rect, int num) {
		return new Rectangle(rect.x - num, rect.y - num, rect.width - num, rect.height - num);
	}

	public static Rectangle subtractRectangle(Rectangle rect, Rectangle rect2) {
		return new Rectangle(rect.x - rect2.x, rect.y - rect2.y, rect.width - rect2.width, rect.height - rect2.height);
	}

	public static JumboColor floatToColor(FloatRectangle v) {
		return new JumboColor((int) v.x, (int) v.y, (int) v.width, (int) v.height);
	}

	public static Rectangle calculateEntityPosition(JumboEntity e) {
		Dimension entitydim = e.getOptimizedbounds(), currentdim = JumboMathHandler.currentdim;
		Rectangle bounds = e.getOutbounds();
		if (entitydim != null && (entitydim != currentdim || e.isUpdaterequired())) {
			int x = 0, y = 0, w = 0, h = 0;
			try {
				bounds = e.getBounds();
				if (bounds != null) {
					e.setOptimizedbounds(currentdim);
					w = bounds.width;
					h = bounds.height;
					x = bounds.x;
					y = bounds.y;
					if (!e.isMaintainingX()) {
						x *= xmod;
					}
					if (!e.isMaintainingY()) {
						y *= ymod;
					}
					if (!e.isMaintainingWidth()) {
						w *= xmod;
					}
					if (!e.isMaintainingHeight()) {
						h *= ymod;
					}
					bounds = e.additionalCalculations(new Rectangle(x, y, w, h));
					e.setUpdaterequired(false);
				}
			} catch (NullPointerException i) {
				System.err.println("ENTITY " + e + " IS NULL!");
				JumboErrorHandler.handle(i);
			}
		}
		return bounds;

	}

	public static Object[] reverseArray(Object[] o) {
		final Object[] out = o;
		for (int i = 0; i < out.length / 2; i++) {
			final Object temp = out[i];
			out[i] = out[out.length - i - 1];
			out[out.length - i - 1] = temp;
		}
		return out;
	}

	public static int rgbToByte(int pix) {
		return ((pix & 0xff000000) >> 24) << 24 | (pix & 0xff) << 16 | ((pix & 0xff00) >> 8) << 8
				| ((pix & 0xff0000) >> 16);
	}

	public static boolean collides(Rectangle r1, Rectangle r2) {
		return r1.x + r1.width >= r2.x && r1.y + r1.height >= r2.y && r1.y < r2.y + r2.height && r1.x < r2.x + r2.width;
	}

	public static boolean collides(Position r1, Rectangle r2) {
		return r1.x >= r2.x && r1.x <= r2.width + r2.x && r1.y >= r2.y && r1.y <= r2.height + r2.y;
	}

	public static boolean collides(int x, int y, Rectangle r2) {
		return collides(new Position(x, y), r2);
	}

	public static FloatRectangle multiplyRectangle(FloatRectangle r, float f) {
		return new FloatRectangle(r.x * f, r.y * f, r.width * f, r.height * f);
	}

	public static FloatRectangle divideRectangle(FloatRectangle r, float f) {
		return new FloatRectangle(r.x / f, r.y / f, r.width / f, r.height / f);
	}

	public static FloatRectangle divideRectangle(Rectangle r, int f) {
		return new FloatRectangle(r.x / f, r.y / f, r.width / f, r.height / f);
	}

	public static Matrix4f createProjectionMatrix(float FOV, float NEAR_PLANE, float FAR_PLANE) {
		final float aspectRatio = (float) Jumbo.getFrameWidth() / (float) Jumbo.getFrameHeight();
		final float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
		final float x_scale = y_scale / aspectRatio;
		final float frustum_length = FAR_PLANE - NEAR_PLANE;

		Matrix4f projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		projectionMatrix.m33 = 0;

		return projectionMatrix;
	}
}
