package controller;

import bean.WangEditor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class EditorController {

    @RequestMapping(value="upload",method = RequestMethod.POST)
    @ResponseBody
    public WangEditor uploadFile(@RequestParam("myFile") MultipartFile multipartFile, HttpServletRequest request)  {
        try {
            // 获取项目路径
            String realPath = request.getSession().getServletContext()
                    .getRealPath("");
            System.out.println("realpath:-----"+realPath);
            InputStream inputStream = multipartFile.getInputStream();
            String contextPath = request.getContextPath();
            // 服务器根目录的路径
            String path = realPath.replace(contextPath.substring(1), "");

            // 根目录下新建文件夹upload，存放上传图片
            String uploadPath = path + "upload";
            // 获取文件名称
            String filename = getFileName();
            System.out.println(uploadPath);
            // 将文件上传的服务器根目录下的upload文件夹
            File file = new File(uploadPath, filename);
            FileUtils.copyInputStreamToFile(inputStream, file);
            // 返回图片访问路径
            String url = request.getScheme() + "://" + request.getServerName()
                    + ":80"  +"/images/" + filename;

            String[] str = { url };

            WangEditor we = new WangEditor(str);
            return we;
        }

     catch (IOException e) {
        System.out.println(e);
    }
		return null;

    }
    public String getFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timeStr = sdf.format(new Date());
        String str = RandomStringUtils.random(5,
                "abcdefghijklmnopqrstuvwxyz1234567890");
        String name = timeStr + str + ".jpg";
        return name;
    }

}
