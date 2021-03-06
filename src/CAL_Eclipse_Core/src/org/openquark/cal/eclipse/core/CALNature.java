/*******************************************************************************
 * Copyright (c) 2005 Business Objects Software Limited and others.
 * All rights reserved. 
 * This file is made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Business Objects Software Limited - initial API and implementation
 *******************************************************************************/

/*
 * CALNature.java
 * Creation date: Nov 1, 2005.
 * By: Edward Lam
 */
package org.openquark.cal.eclipse.core;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

/**
 * An eclipse nature representing a CAL project.
 * @author Edward Lam
 */
public class CALNature implements IProjectNature {

    /*
     * Based on code generated by the wizard.
     * See: Java project.
     */
    
    /** The project which contains this nature. */
    private IProject project;

    /**
     * {@inheritDoc}
     */
    public void configure() throws CoreException {
        IProjectDescription desc = project.getDescription();
        ICommand[] commands = desc.getBuildSpec();
        for (int i = 0; i < commands.length; ++i) {
            if (commands[i].getBuilderName().equals(CALEclipseCorePlugin.BUILDER_ID)) {
                return;
            }
        }
        ICommand[] newCommands = new ICommand[commands.length + 1];
        System.arraycopy(commands, 0, newCommands, 0, commands.length);
        ICommand command = desc.newCommand();
        command.setBuilderName(CALEclipseCorePlugin.BUILDER_ID);
        newCommands[newCommands.length - 1] = command;
        
        desc.setBuildSpec(newCommands);
        project.setDescription(desc, null);
    }

    /**
     * {@inheritDoc}
     */
    public void deconfigure() throws CoreException {
        IProjectDescription description = getProject().getDescription();
        ICommand[] commands = description.getBuildSpec();
        for (int i = 0; i < commands.length; ++i) {
            if (commands[i].getBuilderName().equals(CALEclipseCorePlugin.BUILDER_ID)) {
                ICommand[] newCommands = new ICommand[commands.length - 1];
                System.arraycopy(commands, 0, newCommands, 0, i);
                System.arraycopy(commands, i + 1, newCommands, i, commands.length - i - 1);
                description.setBuildSpec(newCommands);
                return;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public IProject getProject() {
        return project;
    }

    /**
     * {@inheritDoc}
     */
    public void setProject(IProject project) {
        this.project = project;
    }
}
