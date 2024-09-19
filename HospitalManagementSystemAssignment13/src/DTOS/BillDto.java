
package DTOS;
import DAO.BillDao;
import java.util.Scanner;
import java.sql.Connection;
import Database.Database;
import java.sql.CallableStatement;
import java.sql.ResultSet;
public class BillDto implements BillDao{
    private Scanner scan;
    private Connection con;
    public BillDto(){
        scan = new Scanner(System.in);
        try{
           con = Database.getConnection();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    @Override
    public void insertBill() {
        try{
            System.out.println("Enter payable amount");
            int amount = scan.nextInt();
            System.out.println("Enter payer name");
            String payerName = scan.next();
            System.out.println("Enter appointment id");
            int appointId = scan.nextInt();
            CallableStatement cs = con.prepareCall("CALL insertBill(?,?,?)");
            cs.setInt(1, amount);
            cs.setString(2, payerName);
            cs.setInt(3, appointId);
            int row = cs.executeUpdate();
            if (row > 0){
                System.out.println("Bill added successfully..."); 
            }else{
                System.out.println("Something went wrong");
            }
            cs.close();
            con.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void displayAllBills() {
        try{
           CallableStatement cs = con.prepareCall("CALL displayAllBills();");
           ResultSet rs = cs.executeQuery();
           while(rs.next()){
               System.out.println("Doctor name : " + rs.getString("docName"));
               System.out.println("Patient name : " + rs.getString("patName"));
               System.out.println("Bill payable amount: " + rs.getString("billPayableAmount"));
               System.out.println("Payer name : " + rs.getString("payerName"));
               System.out.println("Bill Date: " + rs.getString("billDate"));
               System.out.println("--------------------------------------");
           }
           rs.close();
           cs.close();
        con.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void displayBillsDateWise() {
        try{
            System.out.println("Enter date");
            String date = scan.next();
            CallableStatement cs = con.prepareCall("CALL dateWiseAllBills(?);");
            cs.setString(1, date);
            int row = cs.executeUpdate();
            if(row > 0){
                ResultSet rs = cs.executeQuery();
           while(rs.next()){
               System.out.println("Doctor name : " + rs.getString("docName"));
               System.out.println("Patient name : " + rs.getString("patName"));
               System.out.println("Bill payable amount: " + rs.getString("billPayableAmount"));
               System.out.println("Payer name : " + rs.getString("payerName"));
               System.out.println("Bill Date: " + rs.getString("billDate"));
               System.out.println("--------------------------------------");
           }
           rs.close();
           cs.close();
        con.close();
        scan.close();
                
            }else{
                System.out.println("Something went wrong!");
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
}
