package com.zaccoding.tracer.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * @author zacconding
 * @Date 2018-02-18
 * @GitHub : https://github.com/zacscoding
 */
public class WriteClassFileForDev {

    private static String root = "D:\\web-tracer\\classes";
    private static boolean hasError = false;

    static {
        initRootDir();
    }

    /**
     * Class의 bytecode변경이 존재하면, D:classes폴더에 클래스 파일을 쓴다. (디컴파일러 인텔리j, 이클립스, jd-gui 등으로 확인하면 됨)
     */

    public static void writeByteCode(byte[] bytes, final String className) {
        if (bytes == null || bytes.length == 0) {
            return;
        }
        final byte[] copy = new byte[bytes.length];
        System.arraycopy(bytes, 0, copy, 0, bytes.length);
        new Thread(new Runnable() {
            @Override
            public void run() {
                writeByteCodeInternal(copy, className);
            }
        }).start();
    }

    private static void writeByteCodeInternal(byte[] bytes, String className) {
        if (hasError) {
            System.out.println("Cant write class file");
            return;
        }

        if (bytes == null || className == null) {
            return;
        }

        className = parseClassNameToPath(className);
        File dir = null;

        String dirPath = root;
        int classDot = className.lastIndexOf(File.separatorChar);
        if (classDot > -1) {
            dirPath += (File.separator + className.substring(0, classDot));
        }

        dir = new File(dirPath);
        if (!dir.canWrite()) {
            dir.mkdirs();
        }

        if (!dir.canWrite()) {
            return;
        }

        String clazz = className.substring(classDot + 1) + ".class";
        BufferedOutputStream buffOut = null;
        try {
            buffOut = new BufferedOutputStream(new FileOutputStream(new File(dir, clazz)));
            buffOut.write(bytes);
            buffOut.flush();
            buffOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String parseClassNameToPath(String className) {
        if (className == null) {
            return null;
        }
        if (className.length() == 0) {
            return "";
        }

        char[] copy = new char[className.length()];

        for (int i = 0; i < copy.length; i++) {
            char cur = className.charAt(i);
            if (cur == '.' || cur == '/') {
                cur = File.separatorChar;
            }
            copy[i] = cur;
        }

        return new String(copy);
    }

    private static void initRootDir() {
        try {
            File rootDir = new File(root);
            if (!rootDir.canWrite()) {
                rootDir.mkdirs();
            }

            File[] prevs = rootDir.listFiles();
            for (File file : prevs) {
                if (".idea".equals(file.getName())) {
                    continue;
                }

                if (file.isFile()) {
                    file.delete();
                } else {
                    deleteAll(file);
                }
            }

            if (!rootDir.canWrite()) {
                rootDir.mkdirs();
            }
            if (!rootDir.canWrite()) {
                hasError = true;
            }
        } catch (Exception e) {
            hasError = true;
        }
    }

    private static void deleteAll(File dir) {
        if (dir == null) {
            return;
        }
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                file.delete();
            } else {
                deleteAll(file);
                file.delete();
            }
        }
        dir.delete();
    }
}
