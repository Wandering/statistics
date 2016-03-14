package cn.thinkjoy.jx.statistics;

import cn.thinkjoy.jx.statistics.util.DownloadUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2016/2/3.
 */
public class CSVUtil {
    public static void exportFile(HttpServletResponse response, String csvFilePath, String fileName)
            throws IOException {
        response.setContentType("application/csv;charset=UTF-8");
        response.setHeader("Content-Disposition",
                "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));

        InputStream in = null;
        in = new FileInputStream(csvFilePath+fileName+".csv");
        if(in!=null)
        {
            try {
                DownloadUtil.downloadCsv(response,fileName,in);
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }finally
            {
                in.close();
            }
        }

       /* try {
            in = new FileInputStream(csvFilePath+fileName);
            int len = 0;
            byte[] buffer = new byte[1024];
            response.setCharacterEncoding("UTF-8");
            OutputStream out = response.getOutputStream();
            while ((len = in.read(buffer)) > 0) {
                out.write(new byte[1024]);
                out.write(buffer, 0, len);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }*/
    }

    /**
     * 删除该目录filePath下的所有文件
     * @param filePath
     *      文件目录路径
     */
    public static void deleteFiles(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {

            file.delete();


        }
    }
    public static String getCSVDataStr(String dataStr)
    {
        String result = "";
        if(dataStr == null || dataStr.trim().equals(""))
        {
            result = "null";
        }
        else if(dataStr.contains("\""))
        {
            dataStr.replace("\"","");
        }else
        {
            result = dataStr;
        }
        return result;
    }
}
