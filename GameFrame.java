/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculatorgame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.Random;


/**
 *
 * @author nazarov_m
 */
public class GameFrame extends JFrame{
    
    private int frameWidth = 1280, frameHeight = 900,
            money = 0, everyMonthDebt = 0;      
    
    public enum Status{
        ACTIVEPROJECT,
        NOPROJECTS,
        DEBUGING
    }    
    public Status state;
    public ProjectArray projectQuery = new ProjectArray(this);
    private CreateProject createProjectMenu = new CreateProject(this);
    public WorkStage workPanel = new WorkStage(this);
    public GameInfo gameInfoPanel = new GameInfo(this); 
    public ActionMenu pam = new ActionMenu(ActionMenu.Types.PLAYER, createProjectMenu, this);
    
    class WorkersArr{
        public int capacity = 8;
        public Worker[] workers = new Worker[capacity];
        public int size = 0;       
        
        
        public void add(Worker w){
            if (size == capacity-1) {
                return;
            }
            workers[size++] = w; 
            gameInfoPanel.payments();
            gameInfoPanel.money -= workers[size - 1].cost;
            gameInfoPanel.moneyLabel.setText(gameInfoPanel.money + "р");
            gameInfoPanel.workcountLabel.setText(++gameInfoPanel.workcount + "ч");
            
        }
        public void delete(){
            if (size >1){
                workers[--size] = null;   
            gameInfoPanel.payments();
            gameInfoPanel.workcountLabel.setText(--gameInfoPanel.workcount + "ч");
            }
        }
        
    }
    
    public WorkersArr workers = new WorkersArr();
    
    
    
    private JPanel gamePanel = new JPanel(){                     
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);       
            Image img = null;
            try {
                URL imgURL = GameInfo.class.getResource(AllImages.FRAMEBACKGROUND.source);
                img = ImageIO.read(imgURL);
            } catch(IOException e){ }
            if (img == null) return;
            g.drawImage(img, 0, 0, this );               
        }
    };
    
    public JPanel messageSellingCalculator = new JPanel(){
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);       
            Image img = null;
            try {
                URL imgURL = GameInfo.class.getResource(AllImages.MESSAGEBACKGROUND.source);
                img = ImageIO.read(imgURL);
            } catch(IOException e){ }
            if (img == null) return;
            g.drawImage(img, 0, 0, this );               
        }
    };
    
    
    
    
    private Rectangle gameInfoRectangle = new Rectangle(
            frameWidth - gameInfoPanel.image.width - 20,
            0, gameInfoPanel.image.width, gameInfoPanel.image.height),
            
            playerRectangle,
    
            playerActionMenuRectangle,
            createProjectMenuRectangle = new Rectangle (
                    frameWidth / 2 - 200,frameHeight / 2 - 200,
                    400,
                    400
            ),
            workPanelRectangle = new Rectangle(
                    frameWidth /2 - 150, 0,
                    300, 50
            ), messageRectangle = new Rectangle(
                    frameWidth / 2 - 200,frameHeight / 2 - 150,
                    AllImages.MESSAGEBACKGROUND.width,
                    AllImages.MESSAGEBACKGROUND.height
            );
    
    
     private Timer GameTime = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if ( (state == Status.ACTIVEPROJECT) || (state == Status.DEBUGING) ){
                projectQuery.perform();
            }
            gameInfoPanel.changeData();
        }
    });
    
    public GameFrame(){        
        super("Calculator game");
        
        
        workers.add(new Worker(AllImages.PLAYER, pam, 1, 1, 1,200000, -500000));
        playerRectangle = new Rectangle (
                    frameWidth - workers.workers[0].image.width - 50,
                    frameHeight - workers.workers[0].image.height - 50,
                    workers.workers[0].image.width,
                    workers.workers[0].image.height
            );
        playerActionMenuRectangle = new Rectangle (
                    playerRectangle.x - pam.width,
                    playerRectangle.y + playerRectangle.height - pam.height,
                    pam.width, pam.height
            );
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(frameWidth,frameHeight);
        setLayout(new BorderLayout());
        setResizable(false);
        gamePanel.setLayout(null);
        
        JButton b = new JButton("ОК");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageSellingCalculator.setVisible(false);
            }
        });
        messageSellingCalculator.setLayout(new BorderLayout());
        messageSellingCalculator.add(b,BorderLayout.SOUTH);
        messageSellingCalculator.setBounds(messageRectangle);
        messageSellingCalculator.setVisible(false);
        gamePanel.add(messageSellingCalculator);
        workers.workers[0].setBounds(playerRectangle);
        gamePanel.add(workers.workers[0]);
        pam.setBounds(playerActionMenuRectangle);
        pam.setVisible(false);
        gamePanel.add(pam);        
        gameInfoPanel.setBounds(gameInfoRectangle);       
        gamePanel.add(gameInfoPanel);
        createProjectMenu.setBounds(createProjectMenuRectangle);
        createProjectMenu.setVisible(false);
        gamePanel.add(createProjectMenu);
        workPanel.setBounds(workPanelRectangle);
        gamePanel.add(workPanel);
        add(gamePanel);
        GameTime.start();
        
    }  
    
                        
        
    }
