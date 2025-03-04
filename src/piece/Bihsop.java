package piece;

import main.GamePanel;

public class Bihsop extends Piece{

	public Bihsop(int color, int col, int row) {
		super(color, col, row);
		if (color == GamePanel.WHITE) {
			image =getImage("/piece/bishop");
		}else {
			image = getImage("/piece/bishop1");
		}
	}

}
