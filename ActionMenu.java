/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculatorgame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 *
 * @author nazarov_m
 */

public class ActionMenu extends JPanel {
    
    enum Types {
        PLAYER, WORKER
    }
    private GameFrame frame;
    private MenuButton b1, b2, b3;
    public int width = 0, height = 0 ;
    private CreateProject createProjectMenu = null;
    private ActionMenu thisMenu;
    
    public ActionMenu(Types t, CreateProject menu, GameFrame f){
        setLayout(new GridLayout(0,1));
        this.frame = f;
        this.createProjectMenu = menu;
        thisMenu = this;
        //switch(t){
        //    case PLAYER: 
                PlayerMenu();
        //        break;
        //    case WORKER:                
        //        break;
        //}
        
    }

    private void PlayerMenu(){
        
        b1 = new MenuButton("Создать проект");
        b1.addMouseListener(new CreateCalcListner());
        add(b1);
        this.width += b1.image.width;
        this.height += b1.image.height;
        b2 = new MenuButton("Абитуриент++");
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.workers.add(new Worker(AllImages.WORKER,frame.pam,1,1,1,12000,-75000));
            }
        });
        add(b2);
        this.height += b2.image.height;
        b3 = new MenuButton("Абитуриент--");
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.workers.delete();
            }
        });
        add(b3);
        this.height += b3.image.height;
    }
    
    class MenuButton extends JButton{
        private AllImages image = AllImages.ACTIONMENUBUTTON;
        MenuButton(String name){
            super();           
            setLayout(new BorderLayout());
            JLabel text = new JLabel(name);
            text.setForeground(Color.white);
            text.setFont((new Font("Times new Romania", Font.BOLD,20)));
            add(text,BorderLayout.CENTER);           
        }
        
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
             URL imgURL = GameInfo.class.getResource(image.source);
            Image img = null;
            try {
                img = ImageIO.read(imgURL);
            }
            catch(IOException e){
                System.out.println(e);
            }
            if (img == null) return;
            g.drawImage(img, 0, 0, this);            
        }
    }

    class CreateCalcListner implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e){
           createProjectMenu.setVisible(true);
           thisMenu.setVisible(false);
        }
        @Override
        public void mousePressed(MouseEvent e){

        }
        @Override
        public void mouseReleased(MouseEvent e){

        }
        @Override
        public void mouseEntered(MouseEvent e){

        }
        @Override
        public void mouseExited(MouseEvent e){

        }
    }
}
