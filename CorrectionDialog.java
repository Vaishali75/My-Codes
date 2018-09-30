
package OnlineTest;

import javax.swing.*;  
import java.awt.*;  
import java.awt.event.*;  
import sun.java2d.d3d.D3DRenderQueue;
public class CorrectionDialog implements ActionListener{  
    JDialog d;
    JLabel l;
    JButton b;
    JFrame f;
   public CorrectionDialog() {  
         f= new JFrame();  
         f.setLayout(null);
        d = new JDialog(f , "Error Detected", true);  
        d.setLayout( new FlowLayout() );  
         b = new JButton ("OK");  
        b.addActionListener(this);
       b.setBounds(50,40,70,30);
        l=new JLabel("Enter correct Email and Password!");
           
          
        d.add(l);  
        d.add(b);
        d.setLocation(500,200);
        d.setSize(300,200);    
        d.setVisible(true);
        
    }  
     public void actionPerformed( ActionEvent e )  
            {  
               if(e.getSource()==b)
               {
               
                new loginPage();
                 d.setVisible(false);
               }
            }  
    public static void main(String args[])  
    {  
        new CorrectionDialog();  
    }  

  
}  