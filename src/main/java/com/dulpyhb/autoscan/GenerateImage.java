package com.dulpyhb.autoscan;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
public class GenerateImage {

    /**
     * 编辑图片,往指定位置添加文字
     * @param srcImgPath    :源图片路径
     * @param targetImgPath :保存图片路径
     * @param list          :文字集合
     */
    public static void writeImage(String srcImgPath, String targetImgPath, List<ImageDTO> list) {
        FileOutputStream outImgStream = null;
        try {
            //读取原图片信息
            File srcImgFile = new File(srcImgPath);//得到文件
            Image srcImg = ImageIO.read(srcImgFile);//文件转化为图片
            int srcImgWidth = srcImg.getWidth(null);//获取图片的宽
            int srcImgHeight = srcImg.getHeight(null);//获取图片的高

            //添加文字:
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            for (ImageDTO imgDTO : list) {
                g.setColor(imgDTO.getColor());                                  //根据图片的背景设置水印颜色
                g.setFont(imgDTO.getFont());                                    //设置字体
                g.drawString(imgDTO.getText(), imgDTO.getX(), imgDTO.getY());   //画出水印
            }
            g.dispose();

            // 输出图片
            outImgStream = new FileOutputStream(targetImgPath);
            ImageIO.write(bufImg, "jpg", outImgStream);
        } catch (Exception e) {
            log.error("==== 系统异常::{} ====",e);
        }finally {
            try {
                if (null != outImgStream){
                    outImgStream.flush();
                    outImgStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建ImageDTO, 每一个对象,代表在该图片中要插入的一段文字内容:
     * @param text  : 文本内容;
     * @param color : 字体颜色(前三位)和透明度(第4位,值越小,越透明);
     * @param font  : 字体的样式和字体大小;
     * @param x     : 当前字体在该图片位置的横坐标;
     * @param y     : 当前字体在该图片位置的纵坐标;
     * @return
     */
    public static ImageDTO createImageDTO(String text,Color color,Font font,int x,int y){
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setText(text);
        imageDTO.setColor(color);
        imageDTO.setFont(font);
        imageDTO.setX(x);
        imageDTO.setY(y);
        return imageDTO;
    }


    /**
     * main方法:
     * @param args
     */
    public static void main(String[] args) {

        //=========================================自行发挥================================
        //todo 自己真实的地址:(如果项目中使用的阿里云,则自行改造'writeImage'方法,接受流对象就好了);
        String srcImgPath="/Users/I564407/Project/AutoScan/AutoScan/a1Ean5TcPp0Hd748900cc38af556b54cc186a46353a9.jpg";    //源图片地址
        String tarImgPath="/Users/I564407/Project/AutoScan/AutoScan/tmp_1d975d02b6c419c230fa7f3aa9c54a3e.jpg";   //目标图片的地址
        //==============================================================================

        //获取数据集合；
        ArrayList<ImageDTO> list = new ArrayList<>();
        list.add(createImageDTO("今日美食",new Color(102,102,102,100),new Font("微软雅黑", Font.PLAIN, 24), 78, 160));
        list.add(createImageDTO("账户名称",new Color(102,102,102,100),new Font("微软雅黑", Font.PLAIN, 24), 178, 226));
        list.add(createImageDTO("1111111",new Color(102,102,102,100),new Font("微软雅黑", Font.PLAIN, 24), 710, 226));
        list.add(createImageDTO("上海银行",new Color(102,102,102,100),new Font("微软雅黑", Font.PLAIN, 24), 178, 290));
        list.add(createImageDTO("这是用途",new Color(102,102,102,100),new Font("微软雅黑", Font.PLAIN, 24), 710, 290));
        list.add(createImageDTO("￥50.00",new Color(255,59,48),new Font("微软雅黑", Font.PLAIN, 36), 270, 366));

        //操作图片:
        GenerateImage.writeImage(srcImgPath, tarImgPath, list);

        //这句代码,自己项目中可以不用加,在这里防止main方法报错的;
        System.exit(0);
    }
}


/**
 * 存放文本内容的类
 */
@Setter
@Getter
class ImageDTO{
    //文字内容
    private String text;
    //字体颜色和透明度
    private Color color;
    //字体和大小
    private Font font;
    //所在图片的x坐标
    private int x;
    //所在图片的y坐标
    private int y;
}
