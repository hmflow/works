package com.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
 

/** 테스트*/
public class ClassLoaderForWas extends ClassLoader {
	private final String rootDir;

    public ClassLoaderForWas(ClassLoader parent, String rootDir) throws FileNotFoundException {
        super(parent);
        File f = new File(rootDir);
        if (f.isDirectory())
            this.rootDir = rootDir;
        else
            throw new FileNotFoundException("'" + rootDir + "' isn't a directory");
    }

    @Override
    public Class findClass(String name) throws ClassNotFoundException {
        String classFilePath = rootDir + File.separator + name.replace(".", File.separator) + ".class";

        try {
            FileInputStream fis = new FileInputStream(classFilePath);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);

            return defineClass(name, buffer, 0, buffer.length);
        } catch (IOException e) {
            throw new ClassNotFoundException();
        }
    }
    

     
}
