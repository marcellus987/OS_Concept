// Author: Marcellus Von Sacramento
// Last date modified: 03/14/2025
// Assignment: 3
//


// Driver.
public class OfficeHour {
    
    public static void main(String[] args) {

        if(args.length < 1) {
            System.out.println("Syntax: java <programName> <numberOfStudents>");
        }
        else {

        
            int q = Integer.parseInt(args[0]);
            Professor professor = new Professor("Xavier", q);
            Student []students = new Student[q];

            for(int i = 0; i < q; ++i) {
                students[i] = new Student("Student " + (i+1), professor);
            }

            // // Hard-coded for testing.
            // int q = 2;
            // Professor professor = new Professor("Xavier", q);
            // Student []students = new Student[q];
            
            // for(int i = 0; i < q; ++i) {
            //     students[i] = new Student("Student " + (i+1), professor);
            // }
        }
    } // End of main().

} // End of class OfficeHour.