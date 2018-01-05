package dump.b.a.builder;


import dump.b.a.ColorPickerView;
import dump.b.a.renderer.ColorWheelRenderer;
import dump.b.a.renderer.FlowerColorWheelRenderer;
import dump.b.a.renderer.SimpleColorWheelRenderer;

public class ColorWheelRendererBuilder {
	public static ColorWheelRenderer getRenderer(ColorPickerView.WHEEL_TYPE wheelType) {
		switch (wheelType) {
			case CIRCLE:
				return new SimpleColorWheelRenderer();
			case FLOWER:
				return new FlowerColorWheelRenderer();
		}
		throw new IllegalArgumentException("wrong WHEEL_TYPE");
	}
}