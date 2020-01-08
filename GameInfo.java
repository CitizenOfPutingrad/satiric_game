/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculatorgame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.GridLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.*;
import java.net.URL;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author nazarov_m
 */
public class GameInfo extends JPanel {
    
    public GameFrame frame;
    
    public int money = 500000, debt= 0, day = 1, month = 1, year = 2007, workcount = 0;
    
    public JLabel moneyLabel = new JLabel(money + "p"),
            debtLabel = new JLabel(debt +"p/м"),
            workcountLabel = new JLabel(workcount + "ч"),
            dataLabel = new JLabel( day + "." + month + "." + year);
    
    
    
    private Font font = new Font("TimesRoman", Font.BOLD , 18);
    
    public AllImages image = AllImages.INFORMATIONBACKGROUND;
    
    public Rectangle infoRectangle = new Rectangle(
            10,10, image.width - 20, image.height - 20
    );
    
    public void changeData(){
        int old_month = this.month;
        countData();
        if (this.month != old_month){
            this.money -= this.debt;
            moneyLabel.setText(this.money + "p");
        }
        this.dataLabel.setText(day + "." + month + "." + year);
    }
    
    public void countData(){
        this.day++;
        if (this.day < 29) return;
        if (this.day < 31) {
                if (this.month != 2) return;
                else if ((this.day == 29) && (this.year % 400 == 0) 
                   || ( this.day == 29 && (this.year % 100 != 0) 
                    && (this.year % 4 == 0) ) ) return;    
        }
        if (this.day == 31 )
            if (this.month < 8){ 
                if (this.month % 2 == 1)
                    return;    
            }
            else if ( this.month % 2 == 0)  
               return;              
        this.day = 1;
        this.month++;
        if (this.month == 13){
                this.month = 1;
                this.year++;
            }
    }
    
    public void payments(){
        int sum = 0;
        for (int i = 0; i <= frame.workers.size; i++){
            if (frame.workers.workers[i] != null)
            sum += frame.workers.workers[i].everydayCost;
        }
        debt = sum;
        debtLabel.setText(debt + "р/м");
    }
    
    GameInfo(GameFrame f){
        setLayout(null); 
        this.frame = f; 
        JPanel info = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                g.setColor(Color.white);
                g.fillRect(infoRectangle.x,infoRectangle.y, infoRectangle.width,
                        infoRectangle.height);
            }
        };
        info.setBounds(infoRectangle);
        info.setLayout(new GridLayout(0,2));
        JLabel l = new JLabel("Бюджет:");
        l.setFont(font );
        moneyLabel.setFont(font);
        info.add(l);
        info.add(moneyLabel);
        l = new JLabel("Плата/м: ");
        l.setFont(font);
        debtLabel.setFont(font);
        info.add(l);
        info.add(debtLabel);
        l = new JLabel("Раб.: ");
        l.setFont(font);
        workcountLabel.setFont(font);
        info.add(l);
        info.add(workcountLabel);
        l = new JLabel("День:");
        l.setFont(font);
        dataLabel.setFont(font);   
        info.add(l);
        info.add(dataLabel);
        add(info);
        
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
