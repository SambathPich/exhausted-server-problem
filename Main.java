package com.sambath;

import java.util.concurrent.Semaphore;
import java.util.Scanner;

public class Main {

    //Declare and initiate semaphores and make it FCFS
    static Semaphore semaphoreDoor = new Semaphore(15, true);
    static Semaphore semaphoreNap = new Semaphore(0, true);
    static boolean firstCustomer;   //Used to identify first customer

    public static void main(String[] args) throws InterruptedException {

        // Prompt user to hit Enter
        Scanner user_input = new Scanner(System.in);
        Boolean hitEnter = false;
        while(!hitEnter) {
            System.out.println("\nHit enter to start the rush stimulation\n");
            if (user_input.nextLine().isEmpty()) {
                hitEnter = true;
            }
        }

        //Start Server Object
        System.out.println(".....[Server is SLEEPING].....");   //Initially, server is sleeping because there is no customer yet
        ServerThread serverThread = new ServerThread();
        serverThread.start();

        //Create ThreadGroup for both Rush and Show Hours
        ThreadGroup rushHourTG = new ThreadGroup("Rush_Hour");
        ThreadGroup slowHourTG = new ThreadGroup("Slow_Hour");
        CustomerThread[] threadList = new CustomerThread[100];

        //Start threads in Rush_Hour Group
        for (int x = 0; x < 50; x++) {
            threadList[x] = new CustomerThread(rushHourTG,Integer.toString(x + 1));
            threadList[x].start();
            Thread.sleep((long)(Math.random() * 200));  //Sleep between 0 and 200 milliseconds
        }

        while (rushHourTG.activeCount() > 0) {
            //Keep waiting until threads in this group have finished
        }

        // Prompt user to hit Enter
        Thread.sleep(3000); //Sleep for 3 seconds to make sure all threads have finished
        hitEnter = false;
        while(!hitEnter) {
            System.out.println("\n\nHit enter to start the slow stimulation\n");
            if (user_input.nextLine().isEmpty()) {
                hitEnter = true;
            }
        }


        //Start threads in Slow_Hour Group
        for (int x = 50; x < 100; x++) {
            threadList[x] = new CustomerThread(slowHourTG,Integer.toString(x + 1));
            threadList[x].start();
            Thread.sleep((long)(Math.random() * 2000));     //Sleep between 0 and 20000 milliseconds
        }


        //Terminate Program Manually
        Thread.sleep(3000);
        System.exit(0);

    }


    static class CustomerThread extends Thread {

        String customerName = "";
        CustomerThread(ThreadGroup tg,  String name) {
            super(String.valueOf(tg));
            this.customerName = name;
        }

        public void run() {
            try {
                if (semaphoreDoor.availablePermits() > 0) {
                    System.out.println("New Customer attempting to enter restaurant => Available Seats: " + semaphoreDoor.availablePermits());

                    //Check if it is the first customer
                    if(semaphoreDoor.availablePermits() == 15) {
                        firstCustomer = true;
                    } else {
                        firstCustomer = false;
                    }
                } else {
                    System.out.println("New Customer attempting to enter restaurant => Available Seats: "
                            + semaphoreDoor.availablePermits()
                            + " => They have to wait outside");
                }


                semaphoreDoor.acquire();

                try {
                    for (int i = 1; i <= 3; i++) {
                        if (i == 1) {
                            System.out.println("Customer " + customerName + " has entered restaurant and is seated");

                            //Server is sleeping because there is no customer
                            //Awake the server if the first customer arrives.
                            if(firstCustomer == true) {
                                semaphoreNap.release();
                                System.out.println(".....[Server is now AWAKE].....");
                            }
                        } else if (i == 2) {
                            System.out.println("Customer " + customerName + " is waiting for the server");
                        } else {
                            System.out.println("Customer " + customerName + " has been served");
                        }

                        Thread.sleep((long)(Math.random() * 1000));     //Sleep between 0 and 1000 milliseconds
                    }

                //After Customer has finished eating successfully
                } finally {

                    System.out.println("Customer " + customerName + " is leaving");
                    semaphoreDoor.release();

                    //If there is no customer => Server takes a quick NAP
                    if(semaphoreDoor.availablePermits() == 15) {
                        System.out.println(".....[Server is SLEEPING].....");
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    static class ServerThread extends Thread {

        public void run() {

            while (true) {
                //If there is no customer => returns FALSE
                // Thus, Server is able to take a quick NAP
                if (!semaphoreNap.tryAcquire()) {
                    try {
                        semaphoreNap.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    Thread.sleep((long)(Math.random() * 1000));     //Sleep between 0 and 1000 milliseconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
