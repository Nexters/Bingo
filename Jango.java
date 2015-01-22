import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.lang.Math;
import javax.swing.*;
import java.awt.Color;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;



 

 

@SuppressWarnings("serial")
public class Jango extends JFrame
{
   final int BLUE=0, RED=1, YELLOW=2, MAGENTA = 3;
   int i,j;
   
 
   /* add more variable */
   
   final int blockSize = 600;                                          //Cell Size
   
   private int clickCnt = 0;                                          //Counter variable
   private JLabel msg = new JLabel("냉장고 칸 개수   : " + clickCnt);                  //Counter Message

   
   
   
   //..
   
   public Jango()  throws FileNotFoundException
   {   
      /* window layout setting */
      this.setLocation(300, 100);                                       
      this.setTitle("BINGO");                           
      
      this.setLayout(new GridBagLayout());                              //Set layout Configuration
      GridBagConstraints cst = new GridBagConstraints();

      
      
      
      /* "Counter Label" */
      cst.gridx = 0;      cst.gridy =0;                                
      this.add(msg, cst); 
      /* "Counter Label" end */
      
      
      /* Subtract Button setting */

      JButton AddBtn = new JButton("삭제  ");                              //make "Subtract" button object
      AddBtn.addMouseListener(new MouseAdapter()                           
      {
         public void mouseClicked(MouseEvent e)                          
         {
            btnClicked2(BLUE);
         }
      });
      
      AddBtn.setBackground(Color.BLUE);                                //set "Subtract" button layout 

      cst.gridx = 2;   cst.gridy = 1;                           
      this.add(AddBtn,cst);         
      /* Subtract Button end */
      
      
      
      /* Add Button */
      JButton SubBtn = new JButton("추가 ");                           //make "Add" button object
      SubBtn.addMouseListener(new MouseAdapter()                     
      {
         public void mouseClicked(MouseEvent e)                           
         {
            btnClicked(RED);
         }
      });
      
      SubBtn.setBackground(Color.RED);                              //set "Add" button layout
      cst.gridx = 0;
      cst.gridy = 1;
      this.add(SubBtn,cst);
      /* Add Button end */
      
      
      
      /* Confirm Button */
      JButton ConBtn = new JButton("확인  ");                           //make "Confirm" button object
      ConBtn.addMouseListener(new MouseAdapter()                     
      {
         public void mouseClicked(MouseEvent e)                           
         {
            btnClicked3(YELLOW);
         }
      });
      
      ConBtn.setBackground(Color.YELLOW);                              //set "Confirm" button layout
      cst.gridx = 1;
      cst.gridy = -4;
      this.add(ConBtn,cst);
      /* Confirm Button end */
      
      this.pack();
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setVisible(true);
      
      
   
   }
   



private void btnClicked(int flag)                                 
   {
      
      
      this.msg.setText("냉장고 칸 개수   :" + (++clickCnt));       

      if ( clickCnt > 10 ) {
    	  Component frame = null;
		JOptionPane.showMessageDialog(frame, "너무 많잖아");
		clickCnt = 1;
    	 
      }
      
  
      int temp;
     
      
   }
   
private void btnClicked2(int flag)
   {
	   this.msg.setText("냉장고 칸 개수   :" + (--clickCnt));       

	      if ( clickCnt < 1 ) {
	    	  Component frame = null;
	  		JOptionPane.showMessageDialog(frame, "너무 적잖아!");
	  		clickCnt=1;
	      }
	      
	      int temp;
   }
   

   private void btnClicked3(int flag)
   {
	   Component frame=null;
	   JOptionPane.showMessageDialog(frame, clickCnt + "칸으로 하시겠습니까? ");
	   
	   /* Confirm Button */
	      JButton Con2Btn = new JButton("확인  ");                           //make "Confirm" button object
	      Con2Btn.addMouseListener(new MouseAdapter()                     
	      {
	         public void mouseClicked(MouseEvent e)                           
	         {
	            btnClicked4(MAGENTA);
	         }
	        
	      });
	      
	      Con2Btn.setBackground(Color.MAGENTA);                              //set "Confirm" button layout
	      
   }
	      
   
   private void btnClicked4(int flag){


	   	   Component frame = null;
	   	  
	   	   
	   	   JOptionPane.showMessageDialog(frame, "자~~~");
	   	   
	      }
	      
	      /* Confirm Button end */
   int temp;
	      
	      
   
   
 
   
   
   
   
   
   
   public static void main(String[] args) throws FileNotFoundException                           //main function
   {
      @SuppressWarnings("unused")
      Jango app = new Jango();

   }
}