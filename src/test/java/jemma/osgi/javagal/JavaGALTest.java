/**
 * This file is part of JEMMA - http://jemma.energy-home.org
 * (C) Copyright 2013 Telecom Italia (http://www.telecomitalia.it)
 *
 * JEMMA is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License (LGPL) version 3
 * or later as published by the Free Software Foundation, which accompanies
 * this distribution and is available at http://www.gnu.org/licenses/lgpl.html
 *
 * JEMMA is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License (LGPL) for more details.
 *
 * 
 * 
 * @author Patrick Facco <facco@csp.it> CSP s.c. a r.l. - "Innovazione nelle ICT"
 * 
 */

package jemma.osgi.javagal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.energy_home.jemma.zgd.GalExtenderProxyFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.osgi.framework.BundleContext;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;

import static org.junit.Assert.*;
import static org.ops4j.pax.exam.CoreOptions.*;
import org.osgi.framework.Bundle;



@RunWith(JUnit4TestRunner.class)
public class JavaGALTest
{          
    //Context Injection   
    @Inject
    BundleContext bc;
    
    //GAL Factory Injection
    @Inject
    GalExtenderProxyFactory galfactory; 
     
    //TO-DO  move this path to configuration file.
    public String folderPath = "/home/patrick/Sviluppo/javagal-paxexam/dump_multi";
       
    /**
     * Environment and bundles configurationa   
     * @return option array to inject into junit
     */               
    @Configuration
    public Option[] config()
    {
        return options(
            junitBundles(),  //autoprovision of junit bunldes                      
            //--------------------system properties------------------------------------
            systemProperty("eclipse.ignoreApp").value("true"),
            systemProperty("osgi.noShutdown").value("true"),
            systemProperty("it.telecomitalia.ah.driver.autoinstall").value("true"),
            systemProperty("zgd.dongle.uri").value("/dev/ttyUSB0"),  //device file path
            systemProperty("zgd.dongle.speed").value("115200"),      //device speed
            systemProperty("zgd.dongle.type").value("freescale"),    //device type
            systemProperty("osgi.instance.area").value("/tmp/"),     //folder for temp storage
            systemProperty("org.energy_home.jemma.ah.configuration.file").value("EmptyConfig"), 
            systemProperty("org.ops4j.pax.logging.DefaultServiceLog.level").value("DEBUG"),   //log level                     
            //------------------------------------------------------------------
            equinox(),     //autoprovision bundles for equinox 
            mavenBundle("org.eclipse.equinox", "org.eclipse.equinox.common", "3.6.0.v20100503"),
            mavenBundle("org.apache.commons", "commons-lang3", "3.3.2"),
            mavenBundle("org.eclipse.osgi", "org.eclipse.osgi.services", "3.4.0"),
            mavenBundle("org.ops4j.pax.logging", "pax-logging-api", "1.7.2"),
            mavenBundle("org.ops4j.pax.logging", "pax-logging-service", "1.7.2"),
            mavenBundle("org.eclipse.jetty.orbit", "javax.servlet", "3.0.0.v201112011016"),
            mavenBundle("org.rxtx", "org.rxtx", "1.0.0"),
            mavenBundle("javax.xml", "javax.xml", "1.3.4"),                                 
            mavenBundle("org.apache.felix", "gogo.runtime", "0.10.0"),
            mavenBundle("org.apache.felix", "gogo.shell", "0.10.0"),
            mavenBundle("org.energy-home", "jemma.osgi.javagal", "2.0.1")    //GAL Bundle 
        );
    }
    
    
    /**
     * Context injection test
     */
    @Test
    public void checkInjection()
    {   
        assertNotNull(bc);        
    }

     
     /**
     * Print name and state of each bundle deployed into the environment
     */
    @Test
    public void getBundleslist()
    {
            System.out.println("=======================================================================================");
            System.out.println("printing bundle states..");
            
            for(Bundle b : bc.getBundles())
            {
                    
                    System.out.print("Bundle " + b.getBundleId() + ":" + b.getSymbolicName() + " is " + b.getVersion());
                    if(b.getState() == Bundle.ACTIVE)
                        System.out.println("...................................................ACTIVE");
                    if(b.getState() == Bundle.INSTALLED)
                        System.out.println("................................................INSTALLED");
                    if(b.getState() == Bundle.RESOLVED)
                        System.out.println("................................................RESOLVED");                    
            }
            
            System.out.println("=======================================================================================");
    }    
    
    /**
     * Check if gal factory is properly injected
     */
    @Test
    public void checkGalFactoryInjection()
    {
        assertNotNull(galfactory);
    }    
    
   
    /**
     * Check response to binary packet write
     */
    @Test
    public void checkMessage()
    {
        try
        {            
            GatewayEventListenerForTest gel = new GatewayEventListenerForTest();
            System.out.println("SET LISTENER------------------------------------------");
            galfactory.createGatewayInterfaceObject().setGatewayEventListener(gel);
            System.out.println("END SET LISTENER--------------------------------------");
            int count = 0;
                        
            File dumpDirW = new File(folderPath + File.separator + 'w');
            if(!dumpDirW.exists())
            {
                Logger.getLogger(JavaGALTest.class.getName()).log(Level.SEVERE, "Dump directory " + folderPath + " not found.", "");
                return;
            }
            String[] filesW = dumpDirW.list();
            Arrays.sort(filesW);
            for(String filesW1 : filesW)
            {
                galfactory.writeWrapper(fileTobyte(dumpDirW + File.separator + filesW1)); //>>>>>>>>>>>>>DA MODIFICARE<<<<<<<<<<<<<
                
                System.out.println("FILE ---> " + dumpDirW + File.separator + filesW1);
                Thread.sleep(1000); //sleep needed for synchronization
                
                if(gel.getCallback().equals("notifyZDPCommand"))
                {
                    count++;
                }             
            }
            assertTrue(count > 0);
        } 
        catch(Exception ex)
        {
            Logger.getLogger(JavaGALTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Utility method for file to byte array conversion
     * @param filePath absolute file path as a String
     * @return an array of byte readed from the file specified. 
     */
    public byte[] fileTobyte(String filePath)
    {        
        FileInputStream fis = null; 
        byte[] res = null;
        try
        {            
            res = new byte[(int)new File(filePath).length()];
            fis = new FileInputStream(filePath);             
            fis.read(res);
            fis.close();              
        } 
        catch(FileNotFoundException ex)
        {
            Logger.getLogger(JavaGALTest.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch(IOException ex)
        {
            Logger.getLogger(JavaGALTest.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return res;
    }
    
    /**
     * Convert a bytes in a char
     * @param i bytes to convert
     * @return a char that rapresents i 
     */
    public static char toHexChar(long i) 
    {
        if ((0 <= i) && (i <= 9)) 
        {
            return (char) ('0' + i);
	}
        else 
        {
            return (char) ('A' + (i - 10));
	}
    }
    
    /**
     * Converts an array of bytes in an hexadecimal rapresentation as a String
     * @param buff the byte buffer to convert
     * @return an hexadecimal rapresentation as a String
     */
    public String toHexString(byte[] buff) 
    {
        StringBuilder res = new StringBuilder();
	byte[] vect = buff;
	for (int i = 0; i < buff.length; i++) 
        {
            res.append(toHexChar((vect[i] >>> 4) & 0x0F));
            res.append(toHexChar(vect[i] & 0x0F));
	}
	return res.toString();
    }
}
