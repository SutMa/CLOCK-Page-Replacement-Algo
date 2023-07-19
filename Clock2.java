
import java.io.*;
import java.util.*;

public class Clock2{
    static int memorySize = 0;
    static int swapincost = 0;
    static int swapoutcost = 0;

    static int pageReferences = 0;
    static int clockHand = 0;
    static int readPageFaults = 0;
    static int writePageFaults = 0;
    static int time_swap_in = 0;
    static int time_swap_out = 0;

    static FileWriter outfile;

    public static void clock(int ref_number, boolean write, Page[] pages) throws IOException {
        pageReferences++;
        String output = pageReferences + ": ";
        int index = -2;
        if (write) {
            output += "W ";
        } else {
            output += "R ";
        }
        output += ref_number + " ";
        for (int i = 0; i < memorySize; i++) {
            if (pages[i].number == ref_number) {
                index = i;
                break;
            }
        }
        if (index == -2) {
            output += "F ";
            if (write) {
                writePageFaults++;
            } else {
                readPageFaults++;
            }
            while (true) {
                if (clockHand >= memorySize) {
                    clockHand = 0;
                }
                if (!pages[clockHand].rb) {

                    if (pages[clockHand].number == -1) {
                        output += "-1 ";
                    } else {
                        output += pages[clockHand].number + " ";
                    }
                    output += swapincost + " ";
                    if (pages[clockHand].mb) {
                        time_swap_out += swapoutcost;
                        output += swapoutcost + " ";
                    } else {
                        output += "0 ";
                    }
                    index = clockHand;
                    break;
                } else {
                    pages[clockHand].rb = false;
                    clockHand++;
                }
            }
            time_swap_in += swapincost;
            pages[index].number = ref_number;
            pages[index].rb = true;
            if (write) {
                pages[index].mb = true;
            } else {
                pages[index].mb = false;
            }
            clockHand++;
        } else {
            pages[index].rb = true;
            if (write) {
                pages[index].mb = true;
            }
            output += "H -1 0 0";
        }
        outfile.write(output + "\n");
    }
   
    public static void main(String[] args) throws IOException {
        if (args.length != 5) {
            System.err.println("Error: expected 3 integers and 2 string inputs.");
            System.exit(1);
        }
        memorySize = Integer.parseInt(args[0]);
        swapincost = Integer.parseInt(args[1]);
        swapoutcost = Integer.parseInt(args[2]);
        String input_file = args[3];
        String output_file = args[4];
        if (memorySize < 0 || swapincost < 0 || swapoutcost < 0) {
            System.err.println("Error: invalid input parameters");
            System.exit(1);
        }
        Page[] pages = new Page[memorySize];
        for (int i = 0; i < memorySize; i++) {
            pages[i] = new Page();
            pages[i].number = -1;
            pages[i].rb = false;
            pages[i].mb = false;
        }
        try {
            outfile = new FileWriter(output_file);
        } catch (IOException e) {
            System.err.println("Error opening output file");
            System.exit(1);
        }
        File file = new File(input_file);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                boolean write = false;
                System.out.println(line);
                if (line.charAt(0) == 'W') {
                    write = true;
                }
                clock(Character.getNumericValue(line.charAt(2)), write, pages);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error opening input file");
            System.exit(1);
        }
        outfile.write("Number of page faults on read: " + readPageFaults + "\n");
        outfile.write("Number of page faults on write: " + writePageFaults + "\n");
        outfile.write("Time spent swapping in pages: " + time_swap_in + "\n");
        outfile.write("Time spent swapping out pages: " + time_swap_out + "\n");
        outfile.close();
    }
   
    static class Page{
        public int number = -1;
        public boolean rb = false;
        public boolean mb = false;
    }
}



