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

import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtSession;
import lombok.extern.slf4j.Slf4j;

/**
* ONNX运行时工具，基于微软开放的onnxruntime
*/
@Slf4j
public class ONNXRuntimeUtils {
    private static volatile OrtEnvironment environment;
    private static volatile OrtSession session;
    private static final Object lock = new Object();

    /**
    * 创建ONNX会话
    * @param modelPath 模型文件的存放地址
    * @return ONNX会话
    */
    public static OrtSession createSession(String modelPath) {
        if (session == null) {
            synchronized (lock) {
                if (session == null) {
                    try {
                        environment = OrtEnvironment.getEnvironment();
                        session = environment.createSession(modelPath);
                    } catch (Exception e) {
                        log.error("Failed to create ONNX session", e);
                        throw new RuntimeException("Failed to create ONNX session", e);
                    }
                }
            }
        }
        return session;
    }

    /**
     * 获取ONNX环境
     * @return ONNX环境
     */
    public static OrtEnvironment getEnvironment() {
        if (environment == null) {
            synchronized (lock) {
                if (environment == null) {
                    environment = OrtEnvironment.getEnvironment();
                }
            }
        }
        return environment;
    }

    /**
     * 关闭ONNX会话
     */
    public static void closeSession() {
        synchronized (lock) {
            if (session != null) {
                try {
                    session.close();
                } catch (Exception e) {
                    log.error("Failed to close ONNX session", e);
                }
                session = null;
            }
            if (environment != null) {
                try {
                    environment.close();
                } catch (Exception e) {
                    log.error("Failed to close ONNX environment", e);
                }
                environment = null;
            }
        }
    }
}