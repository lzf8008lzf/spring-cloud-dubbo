//package com.enjoy.core.filter;
//
//import org.apache.tomcat.util.http.fileupload.IOUtils;
//
//import javax.servlet.ReadListener;
//import javax.servlet.ServletInputStream;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletRequestWrapper;
//import java.io.*;
//
//public class ReadTwiceHttpServletRequestWrapper extends HttpServletRequestWrapper {
//
//    private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//    public ReadTwiceHttpServletRequestWrapper(HttpServletRequest request) {
//        super(request);
//        try {
//            request.setCharacterEncoding("UTF-8");
//
//            IOUtils.copy(request.getInputStream(), outputStream);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public BufferedReader getReader() throws IOException {
//        return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(outputStream.toByteArray())));
//    }
//
//    @Override
//    public ServletInputStream getInputStream() throws IOException {
//        final ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
//        return new ServletInputStream() {
//
//            @Override
//            public int readLine(byte[] b, int off, int len) throws IOException {
//                return inputStream.read(b, off, len);
//            }
//
//            @Override
//            public boolean isFinished() {
//                return inputStream.available() > 0;
//            }
//
//            @Override
//            public boolean isReady() {
//                return true;
//            }
//
//            @Override
//            public void setReadListener(ReadListener arg0) {
//                // TODO Auto-generated method stub
//            }
//
//            @Override
//            public int read() throws IOException {
//                return inputStream.read();
//            }
//        };
//    }
//
//    public void setBody(String body) {
//        outputStream = new ByteArrayOutputStream();
//        try {
//            outputStream.write(body.getBytes());
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }finally {
//            if(outputStream != null){
//                try{
//                    outputStream.close();
//                }catch (Exception ee){
//                    ee.printStackTrace();
//                }
//            }
//        }
//    }
//
//    public String getBody() {
//        String str = new String(outputStream.toByteArray());
//        if(outputStream != null){
//            try{
//                outputStream.close();
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//        return str;
//    }
//
//}
