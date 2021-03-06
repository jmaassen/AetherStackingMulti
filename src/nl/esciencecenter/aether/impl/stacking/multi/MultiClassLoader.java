package nl.esciencecenter.aether.impl.stacking.multi;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import nl.esciencecenter.aether.ConfigurationException;
import nl.esciencecenter.aether.util.TypedProperties;

public class MultiClassLoader extends URLClassLoader {

    public MultiClassLoader(String ibisName, TypedProperties userProperties) throws ConfigurationException, IOException {
        super(new URL[0], Thread.currentThread().getContextClassLoader());

        String[] jarFiles = userProperties.getStringList(MultiAetherProperties.IMPLEMENTATION_JARS + ibisName);
        if (jarFiles == null || jarFiles.length == 0) {
            throw new ConfigurationException("Implementation jar files not specified in property: " + MultiAetherProperties.IMPLEMENTATION_JARS + ibisName);
        }
        for (String jarFile:jarFiles) {
            File implJarFile = new File(jarFile);
            if (!implJarFile.exists()) {
                throw new ConfigurationException("Implementation jar file: " + jarFile + " does not exist.");
            }
            super.addURL(implJarFile.toURI().toURL());
        }
    }
}
