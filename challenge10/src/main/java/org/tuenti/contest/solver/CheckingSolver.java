package org.tuenti.contest.solver;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import sun.security.provider.MD5;

import java.io.*;
import java.security.MessageDigest;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: robertcorujo
 */
public class CheckingSolver {

    private static final int BUFFER_SIZE = 2048;
    private static final byte[] buffer = new byte[BUFFER_SIZE];
    private static final char[] charBuffer = new char[BUFFER_SIZE];

    public String decode(String input) {
        try {
           String strEncode = processInput(input);
            String filename = strEncode.substring(1);
            String md5 = getMd5HexFile(filename);
            FileUtils.deleteQuietly(new File(filename));
            return md5;
        } catch (Exception e) {
            throw new Error("Error computing output",e);
        }
    }


    private String processInput(String input) throws IOException {
        Stack<String> stack = new Stack<String>();
        StringBuilder currentItem = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {

            switch (input.charAt(i)) {
                case '[':
                    if (currentItem.length() > 0) {
                        pushStringInFile(stack, currentItem.toString());
                        currentItem = new StringBuilder();
                    }

                    stack.push("[");
                    break;
                case ']':
                    if (currentItem.length() > 0) {
                        pushStringInFile(stack, currentItem.toString());
                        currentItem = new StringBuilder();
                    }
                    evaluateExpression(stack);
                    break;
                default:
                    if (StringUtils.isNumeric(input.charAt(i)+"")) {
                        if (currentItem.length() > 0) {
                            if (!StringUtils.isNumeric(currentItem)) {
                                pushStringInFile(stack, currentItem.toString());
                                currentItem = new StringBuilder();
                            }
                        }
                    }
                    currentItem.append(input.charAt(i));
                    break;

            }
        }

        if (currentItem.length() > 0) {
            pushStringInFile(stack, currentItem.toString());
        }

        evaluateExpression(stack);

        return stack.pop();
    }


    private void pushStringInFile(Stack<String> stack, String str) {
        try {
            if (StringUtils.isNumeric(str)) {
                stack.push(str);
            } else {
                File file = File.createTempFile("ch10",".txt");
                FileUtils.writeStringToFile(file,str);
                stack.push("#"+file.getAbsolutePath());
            }
        } catch (IOException e) {
            throw new Error("Error handling files");
        }

    }

    private void evaluateExpression(Stack<String> stack) throws IOException {
        boolean bracketFound = false;
        while (!bracketFound && stack.size() > 1) {
            String str1 = stack.pop();
            String str2 = stack.pop();


            if ("[".equals(str2)) {
                bracketFound = true;
                if (!stack.isEmpty() && StringUtils.isNumeric(stack.peek())) {
                    int repeat = Integer.parseInt(stack.pop());
                    String newFile = repeatFile(str1,repeat);
                    stack.push("#"+newFile);
                } else {
                    stack.push(str1);
                }
            } else if (!StringUtils.isNumeric(str1) && StringUtils.isNumeric(str2)) {
                int repeat = Integer.parseInt(str2.toString());
                String newFile = repeatFile(str1,repeat);
                stack.push("#"+newFile);
            } else if (!StringUtils.isNumeric(str1) && !StringUtils.isNumeric(str2)) {
                String newFilename = concatenateFiles(str2,str1);
                stack.push("#"+newFilename);
            } else {
                stack.push(str2);
                stack.push(str1);
            }
        }
    }

    private String concatenateFiles(String prefix, String text) throws IOException {
        String firstFilename = prefix.substring(1);
        String secondFilename = text.substring(1);

        File firstFile = new File(firstFilename);
        File secondFile = new File(secondFilename);

        BufferedWriter writer = null;
        OutputStream outputstream = FileUtils.openOutputStream(firstFile,true);
        InputStream inputstream = FileUtils.openInputStream(secondFile);
        try {
            appendContent(inputstream,outputstream);
            FileUtils.forceDelete(new File(secondFilename));
        } finally {
            IOUtils.closeQuietly(outputstream);
            IOUtils.closeQuietly(inputstream);
        }

        return firstFilename;
    }

    private String repeatFile(String str1, int repeat) throws IOException {
        File file = File.createTempFile("ch10",".txt");
        String originalFilename = str1.substring(1);

        OutputStream outputStream = FileUtils.openOutputStream(file);

        for (int i = 0; i < repeat; i++) {
            InputStream input = FileUtils.openInputStream(new File(originalFilename));
            appendContent(input, outputStream);
            IOUtils.closeQuietly(input);
        }

        IOUtils.closeQuietly(outputStream);
        FileUtils.forceDelete(new File(originalFilename));
        return file.getAbsolutePath();
    }

    private void appendBufferedContent(String sourceFilename, BufferedWriter writer) throws IOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(FileUtils.openInputStream(new File(sourceFilename))));
            int readBytes = reader.read(charBuffer);
            while (readBytes > 0) {
                writer.write(charBuffer,0,readBytes);
                readBytes = reader.read(charBuffer);
            }
        } finally {
            if (reader != null) {
                IOUtils.closeQuietly(reader);
            }
        }
    }

    private void appendContent(InputStream input, OutputStream output) throws IOException {
        int readBytes = input.read(buffer);
        while (readBytes > 0) {
            output.write(buffer,0,readBytes);
            readBytes = input.read(buffer);
        }
    }


    private String getMd5Hex(String input) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(input.getBytes());

        byte[] mdbytes = md5.digest();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mdbytes.length; i++) {
            sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    private String getMd5HexFile(String inputFile) throws Exception {
        InputStream stream = null;
        try {
            stream = FileUtils.openInputStream(new File(inputFile));
        return DigestUtils.md5Hex(stream);
        } finally {
            if (stream != null) {
                IOUtils.closeQuietly(stream);
            }
        }
    }
}
