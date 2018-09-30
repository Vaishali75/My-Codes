
package OnlineTest;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.lang.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import jdk.nashorn.internal.ir.BreakNode;


public class Test extends JFrame implements ActionListener  {
    
    JPanel p;
    JLabel l1,l2,que,n;
    
  public JComboBox cmb;
  public JRadioButton o1,o2,o3,o4;
    JButton b1,b2;
  public  String s="";
   public Test()
    {
       // p=new JPanel();
       // p.setLayout(null);
        //p.setBackground(Color.CYAN);
        
        
        //l1=new JLabel("Select Your Choice:");
        //l1.setBounds(400,100,200,300);
        //l1.setFont(new Font("Serif", Font.BOLD, 22));
        //p.add(l1);
        
        //l2=new JLabel();
        //l2.setHorizontalAlignment(JLabel.CENTER);  
        //l2.setBounds(400,300,500,150);  
        //l2.setFont(new Font("Serif", Font.PLAIN, 18));
         //p.add(l2);
    
         
        // cmb = new JComboBox();
		//cmb.addItem("C");
		//cmb.addItem("C++");
		//cmb.addItem("COREJAVA");
                //cmb.setFont(new Font("Serif", Font.PLAIN, 18));
       
                //cmb.setBounds(600,232,200,30);
               //p.add(cmb);
       
        //b1=new JButton("CONTINUE");
        //b1.setBounds(500,400,300,40);
       // b2=new JButton("SHOW"); 
        //b2.setBounds(900,232,150,40);
        // b1.addActionListener(this);
         //b2.addActionListener(this);
        //p.add(b1);
        //p.add(b2);
        //String x = JComboBox.getSelectedItem().toString();
         //  String s=cmb.getItemAt(cmb.getSelectedIndex()).toString();
    
        //setVisible(true);
        //setSize(400,400);
        //setLocation(500,200);
        //add(p);
        
        }
    void  fun1()
         {
        p=new JPanel();
        p.setLayout(null);
        p.setBackground(Color.CYAN);
        
        l1=new JLabel("Select Your Choice:");
        l1.setBounds(300,0,200,300);
        l1.setFont(new Font("Serif", Font.BOLD, 22));
        p.add(l1);
        
        l2=new JLabel();
        l2.setHorizontalAlignment(JLabel.CENTER);  
        l2.setBounds(400,150,500,150);  
        l2.setFont(new Font("Serif", Font.PLAIN, 18));
        p.add(l2);
         
        que=new JLabel();
        que.setHorizontalAlignment(JLabel.CENTER);
        que.setBounds(10, 190, 800, 300);
        que.setFont(new Font("Serif", Font.PLAIN, 18));
        p.add(que);
         
        n=new JLabel();
        n.setHorizontalAlignment(JLabel.LEFT);
        n.setBounds(240, 190, 800, 300);
        n.setFont(new Font("Serif", Font.PLAIN, 18));
        p.add(n);
         
       cmb = new JComboBox();
	 cmb.addItem("C");
	 cmb.addItem("C++");
	 cmb.addItem("CORE JAVA");
         cmb.setFont(new Font("Serif", Font.PLAIN, 18));
       
         //cmb.setBounds(600,232,200,30);
         cmb.setBounds(500,130,120,30);
         p.add(cmb);
       
        b1=new JButton("CONTINUE");
        b1.setBounds(500,250,300,40);
        b2=new JButton("SHOW"); 
        b2.setBounds(700,130,150,40);
        b1.addActionListener(this);
        b2.addActionListener(this);
        p.add(b1);
        p.add(b2);
      
        
        setVisible(true);
        setSize(900,700);
        setLocation(500,200);
        add(p);
       
   }
    public String selectedItem()
    {
        s=cmb.getItemAt(cmb.getSelectedIndex()).toString();
        return(s);
    }
    public void actionPerformed(ActionEvent ae)
    {
        System.out.println("vaishali");
        
            try 
           {
               
             Class.forName("com.mysql.jdbc.Driver");
             Connection con=DriverManager.getConnection("jdbc:mysql://localhost/test","root","root");
             Statement stat=con.createStatement();
              System.out.println("vaishali");
             ResultSet rs= stat.executeQuery("Select  *from questions");
             System.out.println("vaishali");
            while (rs.next())
            {
            if(ae.getSource()==b1)
        {            
            if(selectedItem().equals("C"))
           {
            
              System.out.println("vaishali");
             
              String i = rs.getString("S.No.");
              System.out.println("vaishali");
              String q=rs.getString("Questions");
              String op1=rs.getString("Option1");
              String op2=rs.getString("Option2");  
              String op3=rs.getString("Option3");
              String op4=rs.getString("Option4");
              String ans=rs.getString("Answer");
              
               System.out.println("vaishali");
               
               n.setText(i);
              que.setText(q);
               o1=new JRadioButton(op1);
       
        o1.setBounds(220, 360, 800, 50);
       
        p.add(o1);
         
        o2=new JRadioButton(op2);
       
        o2.setBounds(220, 390, 800, 50);
       
        p.add(o2);
        
        o3=new JRadioButton(op3);
        
        o3.setBounds(220, 420, 800, 50);
        
        p.add(o3);
         
        o4=new JRadioButton(op4);
        
        o4.setBounds(220, 450, 800, 50);
        p.add(o4);
         
        ButtonGroup bg=new ButtonGroup();    
       bg.add(o1);bg.add(o2);
        bg.add(o3);bg.add(o4);
       //  no++;
         System.out.println("vaishali");
               
               /* if(no>10)
                   
                 {
                  break;
                 }*/
         
               }
           }
            
        
           
/*else if(selectedItem().equals("C++"))
               {
                    ResultSet rs= stat.executeQuery("select *from questions order by S.No. limit 10,10");
             
            while(rs.next())
            {
               int i = rs.getInt("S.No.");
              String q=rs.getString("Questions");
              String op1=rs.getString("Option1");
              String op2=rs.getString("Option2");  
              String op3=rs.getString("Option3");
              String op4=rs.getString("Option4");
              String ans=rs.getString("Answer");
               int t= i;
               String ts=Integer.toString(i);
               n.setText(ts);
               
               que.setText(q);
               o1.setText(op1);
               o2.setText(op2);
               o3.setText(op3);
               o4.setText(op4);
               
               
                 if(i>20)
                 {
                     break;
                 }
               }
          }
               
               
               else if(selectedItem().equals("CORE JAVA"))
               {
                    ResultSet rs= stat.executeQuery("select *from questions order by S.No. limit 20,10");
             
            while(rs.next())
            {
               int i = rs.getInt("S.No.");
              String q=rs.getString("Questions");
              String op1=rs.getString("Option1");
              String op2=rs.getString("Option2");  
              String op3=rs.getString("Option3");
              String op4=rs.getString("Option4");
              String ans=rs.getString("Answer");
               int t= i;
               String ts=Integer.toString(i);
               n.setText(ts);
               
               que.setText(q);
               o1.setText(op1);
               o2.setText(op2);
               o3.setText(op3);
               o4.setText(op4);
               
               
                 if(i>30)
                 {
                     break;
                 }
               }
          }
           
        
           
           
           }*/
                
              
           
       
    
        
        else if(ae.getSource()==b2)
        
           {  
            String data = "Programming language Selected: " +selectedItem();  
    l2.setText(data);  
        }
    }
            
           }
   
    catch(Exception e)
           {
               System.out.println("Exception found" +e);
               
             
           } 
           
}
   
    
    
    public static void main(String args[])
    {Test t=new Test();
        t.fun1();
    }
}
