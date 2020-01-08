/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculatorgame;

import java.util.Random;

/**
 *
 * @author Максим
 */
public class ProjectArray{
        
        ProjectProgress[] array;
        
        public int N = 5;
        public int index;
        private int count;
        private GameFrame frame = null;
        
        public Random randomNumbers = new Random();
        
        
        ProjectArray(GameFrame f){
            this.frame = f;
           this.index = -1;
           this.count = 0;
           this.array = new ProjectProgress[N];
        }
        
        public boolean isQueryFull(){
            if (count >= N) return true;
            return false;
        }              
        
        public int add(int d, int f, int p, int t, int n){
            if (isQueryFull()) return -1;            
            for (int i = 0; i < N; ++i){
                if ( (array[i] == null ) || !(array[i].state) ) {
                    array[i] = new ProjectProgress(d, f, p, t, n);
                    array[i].state = true;
                    
                    break;
                }
            }            
            this.index = findActiveProject();
            
            return index;
        }
        
        public int findActiveProject(){
            
            int min = -1;
            for(int i = 0; i < N; i++)
                if ( (array[i] != null) && (array[i].state) ){
                    if (min == -1)
                        min = i;                
                    else if ( array[min].completeTime - array[min].currentTime >
                            array[i].completeTime - array[i].currentTime)
                            min = i; 
                    }
            if (min == -1) return min;
            for (int i = 0; i < array[min].NW; i++){
                array[min].workerList[i] = frame.workers.workers[i];
            }
            return min;
        }
        
        
        public void perform(){
                if (frame.state == GameFrame.Status.DEBUGING){
                    this.array[index].debuging();
                    if (this.array[index].bugs <= 0){
                        if ((this.index =  completeProject()) == -1 ){
                            this.frame.state = GameFrame.Status.NOPROJECTS;                        
                        }
                        else this.frame.state = GameFrame.Status.ACTIVEPROJECT;
                    }
                    this.frame.workPanel.changePanel();
                    
                    return;
                }
                this.array[index].currentTime++;
                this.array[index].randomPoints();
                this.frame.workPanel.progress.setValue(this.array[index].currentTime);
                if (this.array[index].currentTime >= this.array[index].completeTime){
                    
                    this.frame.state = GameFrame.Status.DEBUGING;
                }
                this.frame.workPanel.changePanel();
            }
        
        public int completeProject(){
            for(int i = 0; i< this.array[index].NW; i++){
                if (this.array[index].workerList[i] != null)
                    this.array[index].workerList[i].addEXP(1,1,1);
            }
            this.array[index] = null;
            frame.gameInfoPanel.moneyLabel.setText(Integer.toString(++frame.gameInfoPanel.money));
            frame.messageSellingCalculator.setVisible(true);
            return findActiveProject();
           
           
        }
        
        public class ProjectProgress {
            private int NW=0;
            public Worker[] workerList;
            public int tech = 0, design = 0, bugs = 0,
                    desighnPercent, functionalPercent, powerPercent, 
                    completeTime, currentTime = 0;
            public boolean state = false;
            ProjectProgress(int d, int f, int p, int t, int n){
                this.desighnPercent = d;
                this.functionalPercent = f;
                this.powerPercent = p;
                this.completeTime = t;
                this.NW = n;
                workerList = new Worker[n];
                
            }                    
            
            public void debuging(){
                double bug = randomNumbers.nextDouble()%1;
                if (bug >0.90){
                    this.bugs++;
                }
                else if (bugs >=0) this.bugs--;
                    
            }
        
            
            public int randomTypePoints(){
                int r = randomNumbers.nextInt() % 100;
                if (r < 0) r = -r;
                r++;
                if (r <= this.desighnPercent) return 1;
                if (r <= this.functionalPercent) return 2;
                else return 3;
            }
            
            public int randomPoints(){
                int levelsum = 0, dlevel = 0, tlevel = 0;
                for (int i = 0; i < NW; i++){
                    if (workerList[i] != null) {
                        levelsum += workerList[i].level;
                        dlevel += workerList[i].designSkill;
                        tlevel += workerList[i].codingSkill;
                    }
                }
                int n = randomNumbers.nextInt();
                if (n < 0) n = -n;
                switch (randomTypePoints()){
                    
                    case 1:        
                        this.design += n%4*(dlevel);
                        break;
                    case 2:
                        this.design += n%4*(dlevel+tlevel)/2;
                        this.tech += n%4*(dlevel+tlevel)/2;
                        break;
                    case 3:
                        this.tech += n%4*(tlevel);
                        break;
                }
                
                if (randomNumbers.nextDouble()%1 > 0.65)
                    this.bugs++;
                return 0;
            }
        }
    }
