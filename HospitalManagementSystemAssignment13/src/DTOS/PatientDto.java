
package DTOS;
import DAO.PatientDao;
import Database.Database;
import java.sql.CallableStatement;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.ResultSet;
public class PatientDto implements PatientDao{
    private Connection con;
    private Scanner scan;
    public PatientDto(){
        scan = new Scanner(System.in);
        try{
            con = Database.getConnection();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
    }
    @Override
    public void loadAllPatients() {
       try{
           CallableStatement cs = con.prepareCall("CALL displayAllPatients()");
           ResultSet rs = cs.executeQuery();
           while(rs.next()){
               System.out.println("Patient ID: " + rs.getString("patId"));
               System.out.println("Patient Name: " + rs.getString("patName"));
               System.out.println("Patient Age: " + rs.getString("patAge"));
               System.out.println("Patient Disease: " + rs.getString("patDisease"));
               System.out.println("Patient Phone Number: " + rs.getString("patPhoneNumber"));
               System.out.println("---------------------------------------------");
           }
           rs.close();
           cs.close();
       } catch(Exception ex){
           ex.printStackTrace();
       }
    }

    @Override
    public void insertPatient() {
        try{
            System.out.println("Enter patient name");
            String name = scan.nextLine();
            System.out.println("Enter patient age");
            int age = scan.nextInt();
            System.out.println("Enter patient disease");
            String disease = scan.next();
            System.out.println("Enter patient phone number");
            String phoneNumber = scan.nextLine();
            CallableStatement cs = con.prepareCall("CALL insertPatient(?,?,?,?)");
            cs.setString(1, name);
            cs.setInt(2, age);
            cs.setString(3, disease);
            cs.setString(4, phoneNumber);
            int row = cs.executeUpdate();
            if(row > 0){
                System.out.println("Patient is added successfully ...");
            }else {
                System.out.println("Something went wrong ");
            }
        }catch(Exception ex)
        {
           ex.printStackTrace();
        }
    }

    @Override
    public void deletePatient() {
        System.out.println("Enter id");
        int id = scan.nextInt();
        if(id > 0){
            try{
               CallableStatement cs = con.prepareCall("CALL deletePatient(?)");
               cs.setInt(1, id);
               int row = cs.executeUpdate();
               if (row > 0){
                   System.out.println("Patient record is deleted successfully");
               }else{
                   System.out.println("Something went wrong!");
               }
               cs.close();
               con.close();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }else{
            System.out.println("Invalid id");
        }
    }

    @Override
    public void updatePatient() {
        try{
            System.out.println("Enter patient name");
            String name = scan.nextLine();
            System.out.println("Enter patient age");
            int age = scan.nextInt();
            System.out.println("Enter patient disease");
            String disease = scan.next();
            System.out.println("Enter patient phone number");
            String phoneNumber = scan.next();
            System.out.println("Enter patient id");
            int id = scan.nextInt();
            if (id > 0){
            CallableStatement cs = con.prepareCall("CALL updatePatient(?,?,?,?,?)");
            cs.setString(1, name);
            cs.setInt(2, age);
            cs.setString(3, disease);
            cs.setString(4, phoneNumber);
            cs.setInt(5,id);
            int row = cs.executeUpdate();
            if(row > 0){
                System.out.println("Patient record is updated successfully ...");
            }else{
                System.out.println("Something went wrong!");
            }
            cs.close();
            con.close();
            } else {
                System.out.println("Invalid id");
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
}
