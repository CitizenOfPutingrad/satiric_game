/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculatorgame;

public enum AllImages {
    
    PLAYER("images\\player.jpg", 153, 165),
    WORKER("images\\worker.jpg", 100, 100),
    ACTIONMENUBUTTON("images\\Create Calculator.jpg", 200, 50),
    INFORMATIONBACKGROUND("images\\Infromation background.jpg",200,300),
    FRAMEBACKGROUND("images\\frameBackGround.jpg",1260,860),
    MESSAGEBACKGROUND("images\\calculatorCompleted.jpg",400,300);
    
    public int width, height;
    public String source;
    
    AllImages(String src, int w, int h){
        this.source = src;
        this.width = w;
        this.height = h;
    }
    
}
