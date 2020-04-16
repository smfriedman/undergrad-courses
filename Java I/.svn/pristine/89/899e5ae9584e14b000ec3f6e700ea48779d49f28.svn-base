package julia;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.border.EtchedBorder;

import sedgewick.StdDraw;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class JuliaController extends JFrame {

	private JPanel contentPane;
	private Point start = new Point(0,0);
	private final Julia julia;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JuliaController frame = new JuliaController();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JuliaController() {
		setTitle("Julia");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 150, 308);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		julia = new Julia(512, 512);

		final JPanel btnPanel = new JPanel();
		btnPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btnPanel.setBounds(6, 6, 142, 277);
		contentPane.add(btnPanel);
		btnPanel.setLayout(null);

		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				julia.reset();
			}
		});
		btnReset.setBounds(26, 13, 89, 23);
		btnPanel.add(btnReset);

		JButton btnZoomIn = new JButton("Zoom In");
		btnZoomIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				julia.zoomIn();
			}
		});
		btnZoomIn.setBounds(26, 49, 89, 23);
		btnPanel.add(btnZoomIn);

		JButton btnZoomOut = new JButton("Zoom Out");
		btnZoomOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				julia.zoomOut();
			}
		});
		btnZoomOut.setBounds(26, 85, 89, 23);
		btnPanel.add(btnZoomOut);

		JButton btnincreaseresolution = new JButton("<html>Increase<br>Resolution</html>");
		btnincreaseresolution.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				julia.bump();
			}
		});
		btnincreaseresolution.setBounds(26, 121, 89, 36);
		btnPanel.add(btnincreaseresolution);

		JButton btndecreaseresolution = new JButton("<html>Decrease<br>Resolution</html>");
		btndecreaseresolution.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				julia.unbump();
			}
		});
		btndecreaseresolution.setBounds(26, 170, 89, 33);
		btnPanel.add(btndecreaseresolution);

		JButton btnMouse = new JButton("Mouse");
		btnMouse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//
				// Need to move this out of the way of the button handler, or we get
				//   deadlock with Sedgewick's mouse-polling approach.
				//
				new Thread(
						new Runnable() {
							public void run() {
								Color was = btnPanel.getBackground();
								btnPanel.setBackground(Color.RED);
								System.out.println("Click and then drag to select the zoom box");
								while (!StdDraw.mousePressed())
									;
								System.out.println("Got upper left-hand corner, now drag and release to lower right-hand corner");
								btnPanel.setBackground(Color.GREEN);
								Complex ul = new Complex(StdDraw.mouseX(), StdDraw.mouseY());
								while (StdDraw.mousePressed())
									;
								System.out.println("Got lower right-hand corner");
								btnPanel.setBackground(was);
								Complex lr = new Complex(StdDraw.mouseX(), StdDraw.mouseY());
								//
								// correct so area zoomed is square
								//
								Complex diff = lr.minus(ul);
								double sqlen = Math.max(diff.getReal(), -diff.getImaginary());
								diff = new Complex(sqlen, -sqlen);
								lr = ul.plus(diff);
								
								julia.setCoordinates(new Complex(ul.getReal(),lr.getImaginary()), new Complex(lr.getReal(),ul.getImaginary()));
								julia.draw();
							}
						}
						).start();
			}
		});
		btnMouse.setBounds(26, 225, 89, 24);
		btnPanel.add(btnMouse);
	}
}
