import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
public class First {
    public void first(){
            JFrame fr = new JFrame("Обертання чотирикутника навколо свого центра ваги");
            fr.setPreferredSize(new Dimension(300, 300));
            final JPanel pan = new JPanel();
            fr.add(pan);
            fr.setVisible(true);
            fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            fr.pack();

            Timer tm = new Timer(50, new ActionListener() {// затримка 50мс
                int i = 0;
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    Graphics2D gr = (Graphics2D) pan.getRootPane().getGraphics();
                    pan.update(gr);
                    GeneralPath path = new GeneralPath();
                    path.append(new Polygon(new int[]{60, -60, -60, 60}, new int[]{-60, -60, 60, 60}, 4), true);//малювання чотирикутника
                    int x = (60 - 60 - 60 + 60) / 4, y = (-60 - 60 + 60 + 60) / 4;      // середня точка фігури                                 //4 вершнини
                    gr.translate(150, 150);
                    AffineTransform tranforms = AffineTransform.getRotateInstance(Math.toRadians((i++) * 2), x, y);//обертання
                    gr.transform(tranforms);
                    gr.draw(path);
                }
            });
            tm.start();
    }
}
