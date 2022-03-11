package mao.spring_mvc_file_download;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Project name(项目名称)：Spring_MVC_file_download
 * Package(包名): mao.spring_mvc_file_download
 * Class(类名): FileDownController
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/3/11
 * Time(创建时间)： 18:23
 * Version(版本): 1.0
 * Description(描述)： 无
 */
@Controller
public class FileDownController
{
    // 得到一个用来记录日志的对象，在打印时标记打印的是哪个类的信息
    private static final Log logger = LogFactory.getLog(FileDownController.class);

    //路径
    public static String path = "D:\\";


    /**
     * 显示要下载的文件
     *
     * @param model model
     * @return String
     */
    @RequestMapping("showDownFiles")
    public String show(Model model)
    {
        String realpath = path;
        File dir = new File(realpath);
        File[] files = dir.listFiles();
        // 获取该目录下的所有文件名
        ArrayList<String> fileName = new ArrayList<>();
        for (int i = 0; i < Objects.requireNonNull(files).length; i++)
        {
            if (files[i].isFile())
            {
                fileName.add(files[i].getName());
            }
        }
        model.addAttribute("files", fileName);
        return "showDownFiles";
    }


    /**
     * 下载文件
     *
     * @param filename the filename
     * @param response the response
     * @return the string（null）
     */
    @RequestMapping("down")
    public String down(@RequestParam String filename, HttpServletResponse response)
    {
        String aFilePath = null; // 要下载的文件路径
        FileInputStream fileInputStream = null; // 输入流
        ServletOutputStream servletOutputStream = null; // 输出流
        try
        {
            aFilePath = path;
            //设置下载文件使用的报头
            response.setHeader("Content-Type", "application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment; filename=" + toUTF8String(filename));
            // 读入文件
            fileInputStream = new FileInputStream(aFilePath + "\\" + filename);
            // 得到响应对象的输出流，用于向客户端输出二进制数据
            servletOutputStream = response.getOutputStream();
            servletOutputStream.flush();
            int aRead = 0;
            byte[] buffer = new byte[1024];
            while ((aRead = fileInputStream.read(buffer)) != -1)
            {
                servletOutputStream.write(buffer, 0, aRead);
            }
            servletOutputStream.flush();
            fileInputStream.close();
            servletOutputStream.close();
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
        logger.info("文件\"" + filename + "\"下载成功");
        return null;
    }


    /**
     * 下载保存时中文文件名的字符编码转换方法
     *
     * @param str 要转换的字符串（Unicode编码）
     * @return UTF-8格式的字符串
     */
    public String toUTF8String(String str)
    {
        @SuppressWarnings("all")
        StringBuffer stringBuffer = new StringBuffer();
        int len = str.length();
        for (int i = 0; i < len; i++)
        {
            // 取出字符中的每个字符
            char c = str.charAt(i);
            // Unicode码值为0~255时，不做处理
            if (c <= 255)
            {
                stringBuffer.append(c);
            }
            else
            { // 转换 UTF-8 编码
                byte[] b;
                try
                {
                    b = Character.toString(c).getBytes("UTF-8");
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                    b = null;
                }
                // 转换为%HH的字符串形式
                for (int value : b)
                {
                    int k = value;
                    if (k < 0)
                    {
                        k &= 255;
                    }
                    stringBuffer.append("%").append(Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return stringBuffer.toString();
    }
}
