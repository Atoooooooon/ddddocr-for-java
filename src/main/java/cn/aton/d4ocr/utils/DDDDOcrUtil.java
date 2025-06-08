/*
 * Copyright © 2022 <a href="mailto:zhang.h.n@foxmail.com">Zhang.H.N</a>.
 *
 * Licensed under the Apache License, Version 2.0 (thie "License");
 * You may not use this file except in compliance with the license.
 * You may obtain a copy of the License at
 *
 *       http://wwww.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language govering permissions and
 * limitations under the License.
 */
package cn.aton.d4ocr.utils;



import cn.aton.d4ocr.OCREngine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

/**
 * DDDDOcr识别验证码工具类
 */
public class DDDDOcrUtil {
    private static final OCREngine ocrEngine = OCREngine.instance();

    /**
     * 获取识别的验证码
     *
     * @param base64 验证码base64字符串
     * @return
     */
    public static String getCode(String base64) {
        byte[] bytes = Base64.getDecoder().decode(base64);

        try(ByteArrayInputStream bis = new ByteArrayInputStream(bytes)) {
            BufferedImage bufferedImage = ImageIO.read(bis);
            return ocrEngine.recognize(bufferedImage);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取识别的验证码
     *
     * @param inputStream 验证码图片输入流
     * @return
     */
    public static String getCode(InputStream inputStream) {
        try {
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            return ocrEngine.recognize(bufferedImage);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
