/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OnlineTest;
import java.awt.Color;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.*;

public class loginPage extends JFrame implements ActionListener
{
    JPanel p;
    JLabel l1,l2,l3;
    JTextField t1;
    JPasswordField t2;
    JButton b1,b2;
    
   public loginPage()
   {
       p= new JPanel();
       p.setLayout(null);
       p.setBackground(Color.CYAN);
       
       l1=new JLabel("E-Mail");
       l1.setBounds(150,100,100,50);
       p.add(l1);
       
       l2=new JLabel("Password");
       l2.setBounds(150,150,100,50);
       p.add(l2);
       
       l3=new JLabel("If not Registered,Please Register!");
       l3.setBounds(150,250,500,50);
       p.add(l3);
       
       t1=new JTextField(20);
       t1.setBounds(230, 100,150 , 30);
       p.add(t1);
       
       
      t2=new JPasswordField();
       t2.setBounds(230, 150,150 , 30);
       p.add(t2);
       
       b1=new JButton("LOGIN");
       b1.setBounds(200, 200,80 , 30);
       b1.addActionListener(this);
       p.add(b1);
        
       b2=new JButton("REGISTER");
       b2.setBounds(200, 300,100, 30);
       b2.addActionListener(this);
       p.add(b2);
       
       setVisible(true);
       setSize(400,400);
       setLocation(500,200);
       add(p);
       
   }
   public void actionPerformed(ActionEvent ae)
   {
       if(ae.getSource()==b1)
       {  String s1=t1.getText();
              String s2=new String(t2.getPassword());
              // System.out.println(");
              
               String sel1;
               String sel2;
                System.out.println(s1);
             System.out.println(s2);
               
          
             try 
           {//System.out.println("hello");
            Class.forName("com.mysql.jdbc.Driver");
             Connection con=DriverManager.getConnection("jdbc:mysql://localhost/test","root","root");
             Statement stat=con.createStatement();
                       //    System.out.println("hello n1");

           ResultSet rs= stat.executeQuery("select *from registerinfo");
              //System.out.println("hello n");
            
              int flag=0;
           while(rs.next())
               
           {
              // BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//String password = encoder.encode("Test"));
//DB.save(password);
                 sel1=rs.getString("Emaill");
               sel2=rs.getString("Password");
                System.out.println(sel1);
               System.out.println(sel2);
                
              if(sel1.equals(s1) && sel2.equals(s2))
               {
                // System.out.println("hello");
                   flag=1;
                  TestInfo tf= new TestInfo();
                   setVisible(false);
                   
               }
           }
           if(flag==0)
           {
               
                new CorrectionDialog();
                   setVisible(false);
               
           
           
           }
           }
      
                  
              catch(Exception e)
           {
               System.out.println("Exception found" +e);
               
             
           } 
           
       } 
          
         
       else if(ae.getSource()==b2)
       {
           new Register();
           dispose();
       }
   }
   public static void main(String args[])throws Exception
   {
       loginPage lp=new loginPage();
       
   }
           
    
}
