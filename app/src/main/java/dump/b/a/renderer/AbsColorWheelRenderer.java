package dump.b.a.renderer;


import java.util.ArrayList;
import java.util.List;

import dump.b.a.ColorCircle;

public abstract class AbsColorWheelRenderer implements ColorWheelRenderer {
	protected ColorWheelRenderOption colorWheelRenderOption;
	protected List<ColorCircle> colorCircleList = new ArrayList<>();

	public void initWith(ColorWheelRenderOption colorWheelRenderOption) {
		this.colorWheelRenderOption = colorWheelRenderOption;
		this.colorCircleList.clear();
	}

	@Override
	public ColorWheelRenderOption getRenderOption() {
		if (colorWheelRenderOption == null) colorWheelRenderOption = new ColorWheelRenderOption();
		return colorWheelRenderOption;
	}

	public List<ColorCircle> getColorCircleList() {
		return colorCircleList;
	}

	protected int getAlphaValueAsInt() {
		return Math.round(colorWheelRenderOption.alpha * 255);
	}

	protected int calcTotalCount(float radius, float size) {
		return Math.max(1, (int) ((1f - GAP_PERCENTAGE) * Math.PI / (Math.asin(size / radius)) + 0.5f));
	}
}