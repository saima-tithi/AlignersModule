/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alignersmodule;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Map;

/**
 *
 * @author saima
 */
public class RunAligners {
    private Map<String, Aligner> alignerMap;
    
    public RunAligners(Map mapperMap)
    {
        this.alignerMap = mapperMap;
    }
    
    public String runAllAligners()
    {
        for (Aligner aligner : alignerMap.values())
        {
            if(aligner.getCheckbox().isSelected())
            {
               runAligner(aligner);
            }
        }
        String userHome = System.getProperty("user.home");
        String outputString = "BWA output: " + userHome + "/Aligners-output/bwa-output.sam, "
                + "BWA-MEM output: " + userHome + "/Aligners-output/bwamem-output.sam, "
                + "Bowtie2 output: " + userHome + "/Aligners-output/bowtie2-output.sam";
        return outputString;
    }
    
    private void runAligner(Aligner aligner)
    {
        try{
            
            String command = "cd ~" + "\n" + "mkdir Aligners-output" + "\n" + "cd Aligners-output" + "\n" + aligner.getRunCommand();
            String userHome = System.getProperty("user.home");
            FileWriter shellFileWriter = new FileWriter(userHome + "/aligner.sh");
            shellFileWriter.write("#!/bin/bash\n");
            shellFileWriter.write(command);
            shellFileWriter.close();

            ProcessBuilder builder = new ProcessBuilder("sh", userHome + "/aligner.sh");
            builder.redirectError(new File(userHome + "/aligner_log.txt"));
            Process process = builder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((reader.readLine()) != null) {}
            process.waitFor();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
}
