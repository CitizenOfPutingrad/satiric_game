/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculatorgame;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 *
 * @author Максим
 */
public class WorkStage extends JPanel{
        JLabel design = new JLabel("1");
        Rectangle dRectangle = new Rectangle(0,0,50,50);
        JLabel techno = new JLabel("1");
        Rectangle tRectangle = new Rectangle(250,0,50, 50);
        JProgressBar progress = new JProgressBar(0,100);
        Rectangle pRectangle = new Rectangle(51,0,200,50);
        JLabel progressText = new JLabel("Уровень: 1");
        
        GameFrame frame = null;
        
        public void changePanel(){
            if (this.frame.state == GameFrame.Status.NOPROJECTS){
                design.setText(Integer.toString(this.frame.workers.workers[0].designSkill) );
                techno.setText(Integer.toString(this.frame.workers.workers[0].codingSkill));
                progressText.setText("Уровень: " + Integer.toString(this.frame.workers.workers[0].level));
                progress.setMaximum(100);
                progress.setValue(0);
            }
            else if (this.frame.state == GameFrame.Status.ACTIVEPROJECT){
                progressText.setText("Выполняется проект. Ошибок: " 
                        + this.frame.projectQuery.array[this.frame.projectQuery.index].bugs);     
                
                progress.setMaximum(this.frame.projectQuery.array[this.frame.projectQuery.index].completeTime);
                progress.setValue(this.frame.projectQuery.array[this.frame.projectQuery.index].currentTime);
                design.setText(Integer.toString(this.frame.projectQuery.array[this.frame.projectQuery.index].design));
                techno.setText(Integer.toString(this.frame.projectQuery.array[this.frame.projectQuery.index].tech));
            }
            else {
                progressText.setText("Исправляем баги. Ошибок: " 
                        + this.frame.projectQuery.array[this.frame.projectQuery.index].bugs);                    
            }
        }
        
        WorkStage(GameFrame f){
            super();
            this.frame = f;
            setLayout(null);
            JPanel p = new JPanel(){
                 @Override
                public void paintComponent(Graphics g){                 
                    super.paintComponent(g);      
                    g.setColor(Color.ORANGE);
                    g.fillRect(0, 0, 100, 100 );               
                }                
            };     
            p.setLayout(new FlowLayout());
            p.setBounds(dRectangle);  
            design.setForeground(Color.white);
            p.add(design);
            JLabel l = new JLabel("Дизайн");
            l.setForeground(Color.white);
            p.add(l);
            add(p);
            p = new JPanel(){
                 @Override
                public void paintComponent(Graphics g){                 
                    super.paintComponent(g);      
                    g.setColor(Color.RED);
                    g.fillRect(0, 0, 200, 50 );               
                }                
            };
            p.setBounds(pRectangle); 
            progress.setStringPainted(true);
            progress.setMinimum(0);
            progress.setMaximum(100);
            progress.setValue(0);                       
            p.add(progress);
            
            progressText.setForeground(Color.white);
            p.add(progressText);
            
            add(p);
            p = new JPanel(){
                @Override
                public void paintComponent(Graphics g){
                    super.paintComponent(g);                      
                    g.setColor(Color.BLUE);
                    g.fillRect(0, 0, 100, 100 );             
                }
            };
            techno.setForeground(Color.white);
            p.setBounds(tRectangle);        
            p.add(techno);            
            add(p);
            l = new JLabel("Техно");
            l.setForeground(Color.white);
            p.add(l);
        }
    }