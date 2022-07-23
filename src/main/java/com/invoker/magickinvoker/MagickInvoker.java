/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.invoker.magickinvoker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 *
 * @author zico
 */
public class MagickInvoker {

    public static void main(String[] args) throws MalformedURLException, IOException {
        
        System.out.println( System.getProperty("user.dir"));

        try ( InputStream in = 
                new URL("https://cdn.7tv.app/emote/60e6ff484af5311ddcadae45/4x")
                        .openStream()) 
        {
            
            Files.copy(in, Paths.get("temp.webp"), StandardCopyOption.REPLACE_EXISTING);
            
            convertWebpToGif("temp.webp", "temp.gif");
        }
        

    }

    public static void convertWebpToGif(String inputFile, String ouputFile) {
        ProcessBuilder processBuilder = new ProcessBuilder();

        if (getOsName().startsWith("Windows")) {

            processBuilder.command("cmd.exe", "/c", String.format("ImageMagick-7.1.0\\magick.exe %s %s ", inputFile, ouputFile));
            //processBuilder.command("cmd.exe", "/c", "magick C:\\Users\\joseg\\Pictures\\doo.webp C:\\Users\\joseg\\Pictures\\fucker.gif");
        } else {
            processBuilder.command("bash", "-c", String.format("magick %s %s ", inputFile, ouputFile));
        }

        try {

            Process process = processBuilder.start();
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("Success!");
                System.out.println(output);
                System.exit(0);
            } else {
                //abnormal...
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String getOsName() {
        String OS = null;
        if (OS == null) {
            OS = System.getProperty("os.name");
        }
        return OS;
    }

}
