/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tampilan;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

/**
 *
 * @author FUCK_HIM
 */
public class panelRuncing extends JPanel{
    private Color col;

    public panelRuncing() {
        setOpaque(false);
        col=new Color(getBackground().getRed(),getBackground().getGreen(),getBackground().getRed(),80);
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        col=new Color(getBackground().getRed(),getBackground().getGreen(),getBackground().getBlue(),80);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gr=(Graphics2D)g.create();
        gr.setPaint(col);
        gr.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        gr.dispose();
    }



}
