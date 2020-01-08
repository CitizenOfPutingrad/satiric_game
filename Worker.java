/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculatorgame;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import java.net.URL;
import java.math.*;



/**
 *
 * @author nazarov_m
 */
public class Worker extends JLabel {
    public AllImages image;
    public int designSkill;
    public int codingSkill;
    public int level;
    public int lxp=0, dxp=0, cxp=0;
    public int lxpmax = 1, dxpmax = 1, cxpmax = 1;
    public int status;
    public int everydayCost, cost;
    private Worker person;
    private ActionMenu actionMenu;
    Worker(AllImages type, ActionMenu p, int design, int coding, int lvl, int c, int buy ){
        super();
        this.everydayCost = c;
        this.actionMenu = p;
        this.image = type;
        this.codingSkill = coding;
        this.designSkill = design;
        this.level = lvl;
        this.status = 0;
        this.cost = buy;
        URL imgURL =  Worker.class.getResource(type.source);
        setIcon( new ImageIcon( imgURL ));
        addMouseListener(new WorkerMouseListener());
        person = this;        
    }
    
    public void addEXP(int l, int d, int c){
        this.lxp += l;
        this.dxp += d;
        this.cxp += c;
        if (this.lxp > this.lxpmax){
            this.lxp = 0;
            this.level++;
        }
        if (this.dxp > this.dxpmax){
            this.dxp = 0;
            this.designSkill++;
        }
        if (this.cxp > this.cxpmax){
            this.cxp = 0;
            this.codingSkill++;
        }
    }
    
    class WorkerMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            person.actionMenu.setVisible(true); 
        }
 
        @Override
        public void mousePressed(MouseEvent e) {
            
        }
 
        @Override
        public void mouseReleased(MouseEvent e) {
 
        }
 
        @Override
        public void mouseEntered(MouseEvent e) {            
 
        }
 
        @Override
        public void mouseExited(MouseEvent e) {
 
        }
 
    }

    
    
}