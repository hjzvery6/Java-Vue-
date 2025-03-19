package com.example.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;

import com.example.common.Result;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 文件上传接口
@RestController
@RequestMapping("/files") // 为该控制器的所有接口统一设置路径前缀
public class FileController {

    // 文件上传存储路径
    private static final String filePath = System.getProperty("user.dir") + "/file/";
    // 定义文件访问的HTTP路径前缀，用于返回文件的访问链接
    private static final String http = "http://localhost:8080/files/";

    // 文件上传 -> 对应有头像上传或者商品图片上传等需要上传的功能
    @PostMapping("/upload") // 接收/files/upload请求
    public Result upload(MultipartFile file) { // 接收前端上传的文件
        synchronized (FileController.class) {
            String flag = System.currentTimeMillis() + ""; // 生成时间戳标识符
            String fileName = file.getOriginalFilename(); // 获取上传文件的原始名称
            try {
                // 检查存储路径是否存在，不存在则创建目录
                if (!FileUtil.isDirectory(filePath)) {
                    FileUtil.mkdir(filePath);
                }
                // 保存文件，命名规则为 时间戳-文件原名
                FileUtil.writeBytes(file.getBytes(), filePath + flag + "-" + fileName);
                System.out.println(fileName + "--上传成功");
                Thread.sleep(1L);
            } catch (Exception e) {
                System.err.println(fileName + "--文件上传失败");
            }
            // 返回一个包含文件访问链接的成功响应
            return Result.success(http + flag + "-" + fileName);
        }
    }

    // 接收文件 -> 对应前后台所有头像、图片的展示
    @GetMapping("/{flag}") // 接收/files/{flag}请求
    public void avatarPath(@PathVariable String flag, HttpServletResponse response) { // flag是从URL中提取的路径参数，标识要下载的文件，response用于设置响应
        // 检查是否有目录
        if (!FileUtil.isDirectory(filePath)) {
            FileUtil.mkdir(filePath);
        }
        OutputStream os;
        // 获取存储目录下的所有文件名
        List<String> fileNames = FileUtil.listFileNames(filePath);
        // 遍历文件名列表找到包含flag的文件名，找到则返回该文件名，否则返回空字符串
        String avatar = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");
        try {
            if (StrUtil.isNotEmpty(avatar)) {
                // 设置响应头
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(avatar, "UTF-8"));
                response.setContentType("application/octet-stream");
                // 读取文件内容
                byte[] bytes = FileUtil.readBytes(filePath + avatar);
                // 写入响应流
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            System.out.println("文件下载失败");
        }
    }

//    // 删除文件
//    @DeleteMapping("/{flag}") // 接收/files/{flag}请求
//    public void delFile(@PathVariable String flag) {
//        // 检查目录是否存在
//        if (!FileUtil.isDirectory(filePath)) {
//            FileUtil.mkdir(filePath);
//        }
//        // 获取存储路径下所有文件名
//        List<String> fileNames = FileUtil.listFileNames(filePath);
//        // 遍历文件名列表找到包含flag的文件名，找到则返回该文件名，否则返回空字符串
//        String filename = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");
//        // 删除文件
//        FileUtil.del(filePath + filename);
//        System.out.println("删除文件" + filename + "成功");
//    }

	// 用于处理wang-editor富文本编辑器文件上传的接口 -> 对应的是管理员后台商品信息管理和热门商品管理中，在新增和编辑之后里面的富文本编辑器的上传
	@PostMapping("/wang/upload") // 接收/files/wang/upload请求
	public Map<String, Object> wangEditorUpload(MultipartFile file) {
		String flag = System.currentTimeMillis() + ""; // 生成时间戳标识符
		String fileName = file.getOriginalFilename(); // 获取上传文件的原始名称
		try {
            // 保存文件，命名规则为 时间戳-文件原名
			FileUtil.writeBytes(file.getBytes(), filePath + flag + "-" + fileName);
			System.out.println(fileName + "--上传成功");
			Thread.sleep(1L);
		} catch (Exception e) {
			System.err.println(fileName + "--文件上传失败");
		}
		Map<String, Object> resMap = new HashMap<>();
		// wangEditor上传图片成功后， 需要返回的参数
		resMap.put("errno", 0);
		resMap.put("data", CollUtil.newArrayList(Dict.create().set("url", http + flag)));
		return resMap;
	}
}
