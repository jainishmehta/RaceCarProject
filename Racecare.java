mport java.io.*;
import java.lang.*;
import java.util.*;

public class CarRacing
{
    public static ArrayList<String> driver;
    public static ArrayList<String> carBrand;
    public static ArrayList<Double> rpm;
    public static ArrayList<Double> horsepower;
    public static ArrayList<Double> mass;
    public static ArrayList<Double> radius;
    public static ArrayList<Double> displacement;
    public static ArrayList<String> driver2;
    
    public CarRacing() // Creates a new race that different cars/drivers can be inputted into.
    {
        driver = new ArrayList<String>();
        carBrand = new ArrayList<String>();
        rpm = new ArrayList<Double>();
        horsepower = new ArrayList<Double>();
        mass = new ArrayList<Double>();
        radius = new ArrayList<Double>();
        displacement = new ArrayList<Double>();
        driver2 = new ArrayList<String>();
    }
    public void enterCarInRace () // To enter a car in the race with its specifications.
    {
        Scanner in = new Scanner (System.in);
        
        System.out.print("What is the name of the driver you would like to enter into the race (first name initial and last name only, e.g. D. Agbi)? ");
        String name = in.nextLine();
        driver.add(name);
        
        System.out.print("What is the car brand of the car that this driver will drive in the race? ");
        String brand = in.nextLine();
        carBrand.add(brand);
        
        System.out.print("What is the maximum rpm of the car in question? ");
        double max = in.nextDouble();
        rpm.add(max);
        in.nextLine(); // To prevent any problems with reading the input from the user, as Scanner.nextInt() and .nextDouble() tends to have issues.
        
        System.out.print("What is the maximum horsepower of the car in question? ");
        double hp = in.nextDouble();
        horsepower.add(hp);
        in.nextLine(); 
        
        System.out.print("What is the mass of one tire out the four that the car will use (in kg)? ");
        double m = in.nextDouble();
        mass.add(m);
        in.nextLine();
        
        System.out.print("What is the radius of one tire out of the four that the car will use (in meters)? ");
        double rad = in.nextDouble();
        radius.add(rad);
        in.nextLine();
        
        System.out.println();
        System.out.println("Thank you, the car and its specifications have been added to the race. ");
    }
    public void determineBet()
    {
        displacement.clear();
        driver2.clear();
        
        Scanner in = new Scanner(System.in);
        
        System.out.print("How much in total would you like to bet on this race? $");
        double bet = in.nextDouble();
        
        if (driver.size() >= 2)
        {
          for (int i = 0; i < driver.size(); i++) // This writes the information of each driver competing in the race to a separate text file. 
          {
                try
                {
                    File f = new File (driver.get(i) + ".txt"); 
                    if (!f.exists())     {
                        f.createNewFile();
                        BufferedWriter bw = new BufferedWriter(new FileWriter(f, true)); 
                        bw.write(String.valueOf(rpm.get(i)));
                        bw.newLine();
                        bw.write(String.valueOf(horsepower.get(i)));
                        bw.newLine();
                        bw.write(String.valueOf(mass.get(i)));
                        bw.newLine();
                        bw.write(String.valueOf(radius.get(i)));
                        bw.close();
                    }
                }
                catch (Exception e) // Just to be general and save time. The type of exception is still printed through e.printStackTrace();
                {
                    System.out.println("You have an error.");
                    e.printStackTrace();
                 } 
          }                  
           try
           {
               for (int i = 0; i < driver.size(); i++)
               {
                    BufferedReader reader1 = new BufferedReader(new FileReader(driver.get(i) + ".txt")); 
             
                    reader1.mark(10000); 
                    double r = Double.parseDouble(reader1.readLine());                
                    double h = Double.parseDouble(reader1.readLine());
                    double m = Double.parseDouble(reader1.readLine());
                    double rad = Double.parseDouble(reader1.readLine());
                    
                    double torque = (h * 745.7)/(r * (Math.PI/30.0)); 
                       
                    double angAccel = (2.0 * torque)/(m * Math.pow(rad, 2.0));
                    
                    double angDisp = (angAccel * Math.pow(2, 2.0))/2.0;
                    
                    double linDisp = angDisp * ((2.0 * Math.PI * rad)/(2.0 * Math.PI));
                    
                    displacement.add(linDisp);
                    
                }
           }
           catch (Exception e)
           {
               System.out.println("You have an error. ");
               e.printStackTrace();              
           }
          finally
          {   
              ArrayList<String> driver3 = new ArrayList<String>();
              ArrayList<Double> dis = new ArrayList<Double>();           
              driver3.addAll(driver);
              dis.addAll(displacement);
              
              while (dis.size() > 0)
              {
                  int index = -1;
                  double max = - 1;
                  String driverName = null;
                  for (int i = 0; i < dis.size(); i++)
                  {
                      if (dis.get(i) > max) 
                      /* To "sort" the new list created to print the drivers 
                       and their car specifications in order. */
                      {
                          max = dis.get(i);
                          index = i;
                          driverName = driver3.get(i);  
                      }
                  }
                  
                  driver2.add(driverName);
                  dis.remove(index);
                  driver3.remove(index);
              }
              
              int index = -1;
              
              System.out.println();
              System.out.println("Based on the drivers/cars in the race, this is what we think you should bet for each driver (information and betting percentage displayed below):");
              System.out.println();
              
              // All to format how the drivers and their car specifications are printed and displayed to the user.
              
              System.out.println("Rank:    , Name:            , Car Brand:      , Horsepower:    , rpm:         , displacement over in 2 s:    ,bet: ");
              System.out.println("---------------------------------------------------------------------------------------------------------------------------");
              for (int i = 0; i < driver2.size(); i++)
              {
                  for (int j = 0; j < driver.size(); j++)
                  {
                      if (driver2.get(i).equals(driver.get(j)))
                      {
                          index = j;   
                          break;
                      }
                  }
                  
                  int rank = i + 1;
                  System.out.printf("%-8d", rank);
                  System.out.print(" | ");
                  System.out.printf("%-16s", driver.get(index));
                  System.out.print(" | ");
                  System.out.printf("%-15s", carBrand.get(index));
                  System.out.print(" | ");
                  System.out.printf("%-14.2f", horsepower.get(index));
                  System.out.print(" | ");
                  System.out.printf("%-12.2f", rpm.get(index));
                  System.out.print(" | ");
                  System.out.printf("%-23.2f", displacement.get(index));
                  System.out.print(" | ");
                  System.out.print("$");
                  if (i == driver2.size() - 1)
                  {
                      double betamount = 1.0/(Math.pow(2, i)) * bet;
                      
                      System.out.printf("%-15.2f", betamount);
                  }
                  else
                  {
                        double betamount= 1.0/(Math.pow(2, i + 1)) * bet;
                      
                        System.out.printf("%-15.2f", betamount);
                  }
                  System.out.print("|");
                  System.out.println();
                  System.out.println("---------------------------------------------------------------------------------------------------------------------------");
              }
              
                for (int i = 0; i < driver.size(); i++)
                // Deletes the files upon completion of the program so that they don't occupy space on the computer of the user.
                {
                    File file = new File (driver.get(i) + ".txt");
            
                    if (file.exists()) 
                    {
                        file.delete();
                    }                        
                }
          }
        }
        else 
        {
            if (driver.size() == 0)
            {
                System.out.println("There is no one currently registered in this race to bet on.");   
            }
            else
            {
                System.out.println("There is only one person in this race: " + driver.get(0) + ". Therefore I would bet all your money on them!"); 
            }         
        }
    }
    public void removeCarFromRace()
    {
        Scanner in = new Scanner (System.in);
        System.out.print("What is the name of the driver that has been removed from the race (first name initial and last name only, e.g. D. Agbi)? ");
        String name = in.nextLine();
        
        int index = -1;
        
        for (int i = 0; i < driver.size(); i++)
        {
            if (driver.get(i).equals(name))
            {
                index = i;
                break;  
                // To find the index of the driver and remove all his car specs from the race.
            }
        }
        
        if (index < 0)
        {
            System.out.println("That driver is not currently registered in the race. ");
        }
        else
        {
            driver.remove(index);
            carBrand.remove(index);
            rpm.remove(index);
            horsepower.remove(index);
            mass.remove(index);
            radius.remove(index);
            
            System.out.println("Thank you, this driver has been removed from the race.");
        }
    }
    public void modifyCarSpecs()
    {
        Scanner in = new Scanner (System.in);
        int index = -1;
        
        System.out.print("What is the name of the car driver whose car specifications you would like to modify (first name initial and last name only, e.g. D. Agbi)? ");
        String name = in.nextLine();
        System.out.println();
        
        //To make sure that the driver actually exists.
        for (int i = 0; i < driver.size(); i++)
        {
            if (driver.get(i).equals(name))
            {
                index = i;
                break;
            }
        }
        
        if (index < 0)
        {
            System.out.println("That driver is not currently registered in the race. ");
        }
        else
        {
            System.out.println("To modify this driver's car brand, enter 1. ");
            System.out.println("To modify this driver's car's rpm, enter 2. ");
            System.out.println("To modify this driver's car's horsepower, enter 3. ");
            System.out.println("To modify this driver's car's tires' mass, enter 4.");
            System.out.println("To modify this driver's car's tires' radius, enter any other number.");
            System.out.println();
            System.out.print("What is your choice? ");
            //To modify a particular car specification for a particular driver.
            
            double choice = in.nextDouble();
            in.nextLine();
            
            if (choice == 1)
            {
                System.out.print("What is the updated car brand of the driver? ");
                String brand = in.nextLine();
                
                carBrand.set(index, brand); 
            }
            else if (choice == 2)
            {
                System.out.print("What is the updated rpm of the car? ");
                double r = in.nextDouble();
                in.nextLine();
                
                rpm.set(index, r);
            }
            else if (choice == 3)
            {
                System.out.print("What is the updated horsepower of the car? ");
                double h = in.nextDouble();
                in.nextLine();
                
                horsepower.set(index, h);
            }
            else if (choice == 4)
            {
                System.out.print("What is the new mass of one of the car's tires? ");
                double m = in.nextDouble();
                in.nextLine();
                
                mass.set(index, m);
            }
            else 
            {
                System.out.print("What is the new radius of one of the car's tires? ");
                double r = in.nextDouble();
                in.nextLine();
                
                radius.set(index, r);                
            }
            
            
            System.out.println("Thank you, that specification for the driver's car has been updated. ");  
        }       
    }
}
 
