package com.exodus.condition;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

// 自定义逻辑返回要导入的组件
public class MyImportSelector implements ImportSelector {

    /**
     * @param annotationMetadata 当前标注@Import注解的类的所有注解信息
     * @return 返回值就是要导入到容器中的组件全类名
     */
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{"com.exodus.bean.Yellow"};
//        return new String[]{"com.exodus.bean.Yellow", "com.exodus.bean.Blue"};
    }
}
