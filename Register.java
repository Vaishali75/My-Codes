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


public class Register extends JFrame implements ActionListener {
    JPanel p;
    JLabel l1,l2,l3,l4,l5;
    JTextField t1,t2,t3,t4,t5;
   
    JButton b1,b2;
    
    public Register()
   {
       p= new JPanel();
       p.setLayout(null);
       p.setBackground(Color.CYAN);
       
       l1=new JLabel("Name");
       l1.setBounds(150,100,100,50);
       p.add(l1);
       
       l2=new JLabel("DOB");
       l2.setBounds(150,150,100,50);
       p.add(l2);
       
       l3=new JLabel("Address");
       l3.setBounds(150,200,500,50);
       p.add(l3);
       
       l4=new JLabel("E-Mail");
       l4.setBounds(150,250,100,50);
       p.add(l4);
       
       l5=new JLabel("Password");
       l5.setBounds(150,300,100,50);
       p.add(l5);
       
       t1=new JTextField(20);
       t1.setBounds(230, 100,150 , 30);
       p.add(t1);
       
       
      t2=new JTextField(20);
       t2.setBounds(230, 150,150 , 30);
       p.add(t2);
        
       t3=new JTextField(20);
       t3.setBounds(230, 200,150 , 30);
       p.add(t3);
       
       t4=new JTextField(20);
       t4.setBounds(230, 250,150 , 30);
       p.add(t4); 
       
       t5=new JTextField(20);
       t5.setBounds(230, 300,150 , 30);
       p.add(t5);
       
       
       
       b1=new JButton("BACK");
       b1.setBounds(150,380,80 , 30);
       b1.addActionListener(this);
       p.add(b1);
        
       b2=new JButton("SAVE");
       b2.setBounds(250,380,80, 30);
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
        {
            new loginPage();
            dispose();
        }
        else if(ae.getSource()==b2)
        {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con=DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "root");
                Statement stat=con.createStatement();
                String st1=t1.getText();
                String st2=t2.getText();
                String st3=t3.getText();
                String st4=t4.getText();
                String st5=t5.getText();
              
                stat.execute("insert into registerinfo values('"+st1+"','"+st2+"','"+st3+"','"+st4+"','"+st5+"')");
                
            } 
            catch (Exception ex) {
                System.out.println("Exception");
            }
            
        }
    }
   public static void main(String args[])
   {
       new Register();
   }
           
    
}
    
    
    

