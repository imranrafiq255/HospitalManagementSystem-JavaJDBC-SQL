
package DTOS;
import DAO.AppointmentDao;
import java.sql.Connection;
import java.util.Scanner;
import Database.Database;
import java.sql.CallableStatement;
import java.sql.ResultSet;
public class AppointmentDto implements AppointmentDao {
    private Connection con;
    private Scanner scan;
    public AppointmentDto(){
        try{
            scan = new Scanner(System.in);
           con = Database.getConnection();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    @Override
    public void takeAppointment() {
        try{
           CallableStatement cs = con.prepareCall("CALL insertAppointment(?,?,?);");
            System.out.println("Enter doctor id");
            int doctorId = scan.nextInt();
            System.out.println("Enter patient id");
            int patientId = scan.nextInt();
            System.out.println("Enter appointment type");
            String appointType = scan.next();
            cs.setInt(1, doctorId);
            cs.setInt(2, patientId);
            cs.setString(3, appointType);
            int row = cs.executeUpdate();
            if (row > 0){
                System.out.println("Appointment is added successfully");
            }else {
                System.out.println("Something went wrong!");
            }
            cs.close();
            con.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void displayAllOppointments() {
        try{
           CallableStatement cs = con.prepareCall("CALL displayAllAppointment();");
           ResultSet rs = cs.executeQuery();
           while(rs.next()){
               System.out.println("Doctor name: " + rs.getString("docName"));
               System.out.println("Patient name: " + rs.getString("patName"));
               System.out.println("-----------------------------------");
           }
           rs.close();
           cs.close();
           con.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void displayAppointmentsDoctorWise() {
        try{
            System.out.println("Enter doctor id ");
            int id = scan.nextInt();
            if (id >0){
            CallableStatement cs = con.prepareCall("CALL displayAppointmentDoctorWise(?);");
            cs.setInt(1, id);
           ResultSet rs = cs.executeQuery();
           while(rs.next()){
               System.out.println("Doctor name: " + rs.getString("docName"));
               System.out.println("Patient name: " + rs.getString("patName"));
               System.out.println("-----------------------------------");
           }
           rs.close();
           cs.close();
           con.close();
            }else{
                System.out.println("Invalid id");
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
}
