/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculatorgame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Максим
 */
public class CreateProject extends JPanel{
        JSlider[] slidersArr = new JSlider[3];
        JLabel[] slidersCountArr = new JLabel[3];
        CreateProject ref;
        GameFrame frame = null;
        String[] items = {
            "Школьный",
            "Программистический",
            "Инженерный",
            "Редкий",
            "Эпичный",
            "Легендарный"
        };
        JComboBox<String> comboBox = new JComboBox(items);
        
        
        public int NSliders = 3;
        CreateProject(GameFrame f){
            super();
            ref = this;
            this.frame = f;
            setLayout(new BorderLayout());
            JPanel sliders = new JPanel();
            sliders.setLayout(new GridLayout(3,NSliders));
            sliders.add(new JLabel("Дизайн"));
            sliders.add(new JLabel("Функционал"));
            sliders.add(new JLabel("Производительность"));
            for (int i = 0; i < NSliders; i++){
                slidersArr[i] = new JSlider(SwingConstants.VERTICAL,0,100,33);
                slidersArr[i].addChangeListener(new CreateProject.slidersListener());                
                sliders.add(slidersArr[i]);
                slidersCountArr[i] = new JLabel("33%");
            }
            for (int i = 0; i <NSliders; i++){
                sliders.add(slidersCountArr[i]);
            }            
            add(sliders,BorderLayout.CENTER);
            JPanel pn = new JPanel();
            pn.setLayout(new GridLayout(0,1));
            JPanel p = new JPanel();
            p.setLayout(new FlowLayout());
            JLabel l = new JLabel("Выберите тип калькулятора: ");
            p.add(l);
            p.add(comboBox);
            pn.add(p);
            JLabel help = new JLabel("Выберите, какую часть времени на что вы выделите:");
            pn.add(help);          
            add(pn,BorderLayout.NORTH);
            JButton button = new JButton("Отменить");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ref.setVisible(false);
                }
            });
            JPanel buttons = new JPanel();
            buttons.setLayout(new FlowLayout());
            buttons.add(button);
            button = new JButton("Начать проект");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (frame.projectQuery.isQueryFull()) return;
                    ref.setVisible(false);
                    frame.state = GameFrame.Status.ACTIVEPROJECT;
                    int sum = 0;
                    for (int i = 0; i < NSliders ; i++){
                        sum += slidersArr[i].getValue();
                    }
                    frame.projectQuery.add(100*slidersArr[0].getValue()/sum,
                            100*slidersArr[1].getValue()/sum,                            
                           100*slidersArr[2].getValue()/sum, (comboBox.getSelectedIndex() + 1)*10,comboBox.getSelectedIndex() + 2);  
                    frame.projectQuery.findActiveProject();
                    frame.workPanel.changePanel();
                }
            });
            buttons.add(button);
            add(buttons,BorderLayout.SOUTH);
        }
        
        class slidersListener implements ChangeListener{
            @Override
            public void  stateChanged(ChangeEvent e){                
                int sum = 0;
                for (int i = 0; i < NSliders ; i++){
                    sum += slidersArr[i].getValue();
                }
                if (sum == 0) {
                    ((JSlider)e.getSource()).setValue(1);
                    sum = 1;
                }
                for (int i = 0; i < NSliders; i++){                   
                    slidersCountArr[i].setText(Integer.toString(100*slidersArr[i].getValue()/sum) + "%");
                }                
            }
        }
}
