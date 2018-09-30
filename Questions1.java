
package OnlineTest;

import com.sun.org.apache.xerces.internal.impl.dtd.models.CMBinOp;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import java.sql.*;
import OnlineTest.Test;
public class Questions1 extends JFrame

{
    
    static JPanel p;
   static JLabel l1,l2,l3,l4;
    static JButton b;
    //  static String sel;
   // private String s;
    
    public Questions1()
    {
        p=new JPanel();
        p.setLayout(null);
        p.setBackground(Color.CYAN);
           
        
       //  Test t1=new Test();
         //  sel=t1.selectedItem();
           
           l1=new JLabel("Test:"+getItem());
            l1.setBounds(500,100,300,70);
             l1.setFont(new Font("Serif", Font.BOLD, 24));
            p.add(l1);
        
         
       
        setVisible(true);
        setSize(400,400);
        setLocation(500,200);
        add(p);
    
       
    }
      public String getItem()
    {
        Test t=new Test();
        return(t.selectedItem());
        
    }
   
   

    
    public static void main(String args[])throws Exception
    {      
          Questions1 q=new Questions1();
          //q.getItem();
         // new Questions1();
          
          
           // sel=q.s;
           // l1.setText(sel);

            //l1=new JLabel("Your test is on:"+sel);
         //     if(sel.equalsIgnoreCase("C"))
       // {
           // l1.setText(sel);
       //   }
      //  else if(sel.equalsIgnoreCase("C++"))
        //{
         //   l1.setText(sel);
          //}
        //else if(sel.equalsIgnoreCase("CORE JAVA"))
        //{
          //  l1.setText(sel);
        //}
            
        Class.forName("com.mysql.jdbc.Driver");
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost/test","root", "root");
        Statement stat=con.createStatement();
       
        //ResultSet rs = s.executeQuery("select Language from Questions");
       // while(rs.next())
       // {
         //   String sel1=rs.getString("Language");
           // if(sel.equalsIgnoreCase(sel1))
            //{ 
           //     l1=new JLabel(sel);
                
            //}
        //}
    }
    
    
}
