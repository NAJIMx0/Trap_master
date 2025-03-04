// GamePanel.java
package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.text.html.HTML.Tag;

import piece.Bihsop;
import piece.King;
import piece.Knight;
import piece.Pawn;
import piece.Piece;
import piece.Queen;
import piece.Rook;


public class GamePanel extends JPanel implements Runnable {
    public static final int WIDTH = 1100;
    public static final int HEIGHT = 800;
    final int FPS = 60 ;
    Thread gameThread;
    Board board =new Board();
    Mouse mouse = new Mouse();
    
    // color
    
    public static final int WHITE = 0;
    public static final int BLACK = 1;
    int currentColor = WHITE;
    
    // array list piece
    public static ArrayList<Piece> pieces = new ArrayList<>();
    public static ArrayList<Piece> simPieces = new ArrayList<>();
    Piece activePiece;
    


    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        addMouseMotionListener(mouse);
        addMouseListener(mouse);
        
        setPieces();
        copyPieces(pieces, simPieces);
    }
    
    public void lanchGame() {
    	gameThread = new Thread(this);
    	gameThread.start();
		
	}
    
    public void  setPieces() {
    	
		// white team
    	 pieces.add(new Pawn(WHITE, 0, 6));
    	 pieces.add(new Pawn(WHITE, 1, 6));
    	 pieces.add(new Pawn(WHITE, 2, 6));
    	 pieces.add(new Pawn(WHITE, 3, 6));
    	 pieces.add(new Pawn(WHITE, 4, 6));
    	 pieces.add(new Pawn(WHITE, 5, 6));
    	 pieces.add(new Pawn(WHITE, 6, 6));
    	 pieces.add(new Pawn(WHITE, 7, 6));
    	 pieces.add(new Rook(WHITE, 0, 7));
    	 pieces.add(new Rook(WHITE, 7, 7));
    	 pieces.add(new Knight(WHITE,1, 7));
    	 pieces.add(new Knight(WHITE,6, 7));
    	 pieces.add(new Bihsop(WHITE, 2, 7));
    	 pieces.add(new Bihsop(WHITE, 5, 7));
    	 pieces.add(new Queen(WHITE, 3, 7));
    	 pieces.add(new King(WHITE, 4, 7));
    	 
 		// black team
    	 pieces.add(new Pawn(BLACK, 0, 1));
    	 pieces.add(new Pawn(BLACK, 1, 1));
    	 pieces.add(new Pawn(BLACK, 2, 1));
    	 pieces.add(new Pawn(BLACK, 3, 1));
    	 pieces.add(new Pawn(BLACK, 4, 1));
    	 pieces.add(new Pawn(BLACK, 5, 1));
    	 pieces.add(new Pawn(BLACK, 6, 1));
    	 pieces.add(new Pawn(BLACK, 7, 1));
    	 pieces.add(new Rook(BLACK, 0, 0));
    	 pieces.add(new Rook(BLACK, 7, 0));
    	 pieces.add(new Knight(BLACK,1, 0));
    	 pieces.add(new Knight(BLACK,6, 0));
    	 pieces.add(new Bihsop(BLACK, 2, 0));
    	 pieces.add(new Bihsop(BLACK, 5, 0));
    	 pieces.add(new Queen(BLACK, 3, 0));
    	 pieces.add(new King(BLACK, 4, 0));
    	
    	
	}
    
    public void copyPieces(ArrayList<Piece> source , ArrayList<Piece> target) {
		target.clear();
		for (int i = 0; i < source.size(); i++) {
			target.add(source.get(i));
		}
	}
   
    @Override
	public void run() {
		
    	double drawninterval = 1000000000 / FPS;
    	double delta = 0;
    	long lasttime = System.nanoTime();
    	long curenttime ;
    	
    	while (gameThread!= null) {
    		
    		curenttime = System.nanoTime();
    		delta +=(curenttime - lasttime)/drawninterval;
    		lasttime= curenttime;
    		
    		if (delta>=1) {
				update();
				repaint();
				delta--;
			}
    		
    		
    	}
    	
    
    	
    	
		
	}
    public 	void  update() {
    	//// MOUSE BUTTON PRESSED ///
    	if (mouse.pressed) {
			if(activePiece==null) {
				/// if activepiece is null, check if you can pick up a piece 
				for (Piece piece : simPieces) {
					/// if the mouse is on ally piece , pick it up as the active 
					if (piece.color==currentColor &&
								piece.col == mouse.x/Board.SQUARE_SIZE &&
								piece.row == mouse.y/Board.SQUARE_SIZE
								) {
						activePiece= piece ;
					}
				}
			}else {
				// if the player  is holding the piece simulate the move
				simulate();
			}
		}
    	if (mouse.pressed==false) {
			if (activePiece!=null) {
				activePiece.updatePosition();
				activePiece= null;
			}
		}
		
	}
    public void simulate() {
		activePiece.x = mouse.x - Board.HALF_SQUARE_SIZE;
		activePiece.y = mouse.y - Board.HALF_SQUARE_SIZE;
		activePiece.col = activePiece.getCol(activePiece.x);
		activePiece.row = activePiece.getRow(activePiece.y);
	}
    public void paintComponent(Graphics g) {
		super.paintComponent(g); 
		
		Graphics2D g2 = (Graphics2D)g;
		
		// board 
		board.draw(g2);
		
		// pieces
		
		for (Piece p : simPieces) {
			p.draw(g2);
		}
		
		//activepiece
		
		if (activePiece !=null) {
			g2.setColor( Color.white);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
			g2.fillRect(activePiece.col*Board.SQUARE_SIZE,
					activePiece.row*Board.SQUARE_SIZE,
					Board.SQUARE_SIZE,Board.SQUARE_SIZE);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER ,1f));
			
			activePiece.draw(g2);
					
		}
	}

}






















