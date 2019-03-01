import javafx.scene.paint.Color;

public enum Colors {
	CREATE(Color.BLUE), PREVIOUS(Color.RED), NEXT(Color.GREEN), START(Color.BLACK);
	Color color;

	Colors(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
}
