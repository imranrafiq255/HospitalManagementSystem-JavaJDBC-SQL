
package DTOS;

import DAO.DoctorDao;
import Database.Database;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.Scanner;
public class DoctorDto implements DoctorDao {
    private Scanner scan;
    private Connection con;
    public DoctorDto()
    {
        scan = new Scanner(System.in);
        try{
            con =  Database.getConnection();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    @Override
    public void loadAllDoctors() {
    try {
        CallableStatement cs = con.prepareCall("CALL displayAllDoctors();"); 

        ResultSet rs = cs.executeQuery(); 

        while (rs.next()) {
            int docId = rs.getInt("docId");
            String docName = rs.getString("docName");
            String docQualification = rs.getString("docQualification");
            String docDesignation = rs.getString("docDesignation");
            int docSalary = rs.getInt("docSalary");

            System.out.println("Doctor ID: " + docId);
            System.out.println("Doctor Name: " + docName);
            System.out.println("Qualification: " + docQualification);
            System.out.println("Designation: " + docDesignation);
            System.out.println("Salary: " + docSalary);
            System.out.println("-------------------------");
        }

        rs.close();
        cs.close();
    } catch (Exception ex) {
        ex.printStackTrace(); 
    }
}
    @Override
    public void insertDoctor()
    {
        try{
            System.out.println("Enter doctor name");
            String name = scan.next();
            System.out.println("Enter doctor qualification");
            String qualification = scan.next();
            System.out.println("Enter doctor designation");
            String designation = scan.next();
            System.out.println("Enter doctor salary");
            int salary = scan.nextInt();
            System.out.println("Enter doctor department");
            String department = scan.next();
            CallableStatement cs = con.prepareCall("CALL insertDoctor(?,?,?,?,?)");
            cs.setString(1, name);
            cs.setString(2, qualification);
            cs.setString(3, designation);
            cs.setInt(4, salary);
            cs.setString(5, department);
            int row = cs.executeUpdate();
            cs.close();
            con.close();
            if (row>0)
            {
                System.out.println("Doctor record inserted successfully...");
            }else{
                System.out.println("Something went wrong!");
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
    }

    @Override
    public void deleteDoctor() {
        try{
        System.out.println("Enter doctor id to delete");
        int id = scan.nextInt();
        if (id > 0)
        {
            CallableStatement cs = con.prepareCall("CALL deleteDoctor(?)");
            cs.setInt(1, id);
            int row = cs.executeUpdate();
            if (row > 0)
            {
                System.out.println("Doctor record is deleted successfully...");
            }else{
                System.out.println("Something went wrong");
            }
        }else {
            System.out.println("Invalid id");
        } 
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void updateDoctor() {
            try{
            System.out.println("Enter doctor name");
            String name = scan.next();
            System.out.println("Enter doctor qualification");
            String qualification = scan.nextLine();
            System.out.println("Enter doctor designation");
            String designation = scan.nextLine();
            System.out.println("Enter doctor salary");
            int salary = scan.nextInt();
            System.out.println("Enter doctor department");
            String department = scan.nextLine(); 
            System.out.println("Enter doctor id");
            int id = scan.nextInt();
            if (id > 0){
                CallableStatement cs = con.prepareCall("CALL updateDoctorRecord(?,?,?,?,?,?)");
                cs.setString(1, name);
                cs.setString(2, qualification);
                cs.setString(3, designation);
                cs.setInt(4, salary);
                cs.setString(5, department);
                cs.setInt(6, id);
                int row = cs.executeUpdate();
                cs.close();
                con.close();
                if (row > 0){
                    System.out.println("Doctor record is updated successfully ...");
                }else{
                    System.out.println("Something went wrong!");
                }
            }else {
                System.out.println("Invalid id");
            }
            }catch(Exception ex)
            {
                ex.printStackTrace();
            }
    }

}
