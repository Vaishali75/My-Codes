/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OnlineTest;
 
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import java.awt.event.*;


public class TestInfo extends JFrame implements ActionListener{
    
    JPanel p;
    JLabel l1,l2;
    JButton b;
    
    public TestInfo()
    {
    p=new JPanel();
    p.setLayout(null);
    p.setBackground(Color.cyan);
    
    
    l1=new JLabel("INFORMATION  REGARDING  ONLINE  TEST");
    l1.setBounds(500,100,600,200);
    l1.setFont(new Font("Serif", Font.BOLD, 24));
    p.add(l1);
    
    l2=new JLabel("<html><ul>"+
            "<li>This Online Test is providing 3 subjects(C,C++,Core Java).</li>"+
           "<li>The candidate is allowed to give test on only 1 subject of his/her choice out of 3 subjects.</li>"+
            "<li>Each subject contains 10 questions.</li>"+
           "<li>No time limit.</li>"+
            "<li>No negative marking.</li>"+
            "<li>Result will be displayed at the end.</li>");
   l2.setBounds(250,120,800,500);
   l2.setFont(new Font("Serif", Font.PLAIN, 20));
    p.add(l2);
     
    b=new JButton("CONTINUE");
    b.setBounds(500,500,300,40);
    b.addActionListener(this);
    p.add(b);
    
    setVisible(true);
    setSize(400,400);
    setLocation(500,200);
    add(p);
    
    
  }
    public void actionPerformed(ActionEvent ae)
    {
        if(ae.getSource()==b)
        {
            Test tt=new Test();
            tt.fun1();
            dispose();
        }
    }
    public static void main(String args[])
    {
        new TestInfo();
    }
}
