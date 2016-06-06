/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alignersmodule;

import java.util.List;
import javax.swing.JCheckBox;

/**
 *
 * @author saima
 */
public class Aligner {
    private String toolName;
    private String directoryPath;
    private String runCommand;
    private JCheckBox checkbox;

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public String getRunCommand() {
        return runCommand;
    }
    
    public void createRunCommand(List<String> cmds)
    {
        String cmd = "";
        for (String str : cmds)
        {
            cmd = cmd + this.getDirectoryPath() + str + "\n";
        }
        this.runCommand = cmd;
    }

    public void setRunCommand(String runCommand) {      
        this.runCommand = runCommand;
    }

    public JCheckBox getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(JCheckBox checkbox) {
        this.checkbox = checkbox;
    }
    
}
