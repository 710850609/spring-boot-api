package me.linbo.web.api;

import com.power.common.util.DateTimeUtil;
import com.power.doc.builder.ApiDocBuilder;
import com.power.doc.model.ApiConfig;
import com.power.doc.model.ApiDataDictionary;
import com.power.doc.model.ApiReqHeader;
import com.power.doc.model.RevisionLog;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

/**
 * @author LinBo
 * @date 2019-11-26 15:49
 */
public class SmartDoc {

    @Test
    public void testBuilderControllersApi() {
        ApiConfig config = new ApiConfig();
        config.setServerUrl("http://{domain}");

        //设置为严格模式，Smart-doc将降至要求每个Controller暴露的接口写上标准文档注释
        config.setStrict(true);

        //当把AllInOne设置为true时，Smart-doc将会把所有接口生成到一个Markdown、HHTML或者AsciiDoc中
        config.setAllInOne(false);

        //Set the api document output path.
        String curClassPath = SmartDoc.class.getClassLoader().getResource("").getPath();
        File projectFile = new File(curClassPath).getParentFile().getParentFile();
        String path = projectFile.getPath() + "/docs";
        config.setOutPath(path);

        // 设置接口包扫描路径过滤，如果不配置则Smart-doc默认扫描所有的接口类
        // 配置多个报名有英文逗号隔开
        config.setPackageFilters("me.linbo.web.api.controller");

        //since 1.7.5
        //如果该选项的值为false,则smart-doc生成allInOne.md文件的名称会自动添加版本号
        config.setCoverOld(true);
        //since 1.7.5
        //设置项目名(非必须)，如果不设置会导致在使用一些自动添加标题序号的工具显示的序号不正常
        config.setProjectName("spring-boot-api接口");

        //设置公共请求头.如果不需要请求头，则无需设置
        config.setRequestHeaders(
                ApiReqHeader.header().setName("access_token").setType("string")
                        .setDesc("Basic auth credentials").setRequired(true).setSince("v1.1.0"),
                ApiReqHeader.header().setName("user_uuid").setType("string").setDesc("User Uuid key")
        );

        //设置错误错列表，遍历自己的错误码设置给Smart-doc即可
//        List<ApiErrorCode> errorCodeList = new ArrayList<>();
//        for (ErrorCodeEnum codeEnum : ErrorCodeEnum.values()) {
//            ApiErrorCode errorCode = new ApiErrorCode();
//            errorCode.setValue(codeEnum.getCode()).setDesc(codeEnum.getDesc());
//            errorCodeList.add(errorCode);
//        }
        //不需要显示错误码，则可以设置
//        config.setErrorCodes(errorCodeList);


        //设置文档变更记录，没有需要可以不设置
        config.setRevisionLogs(
                RevisionLog.getLog().setRevisionTime("2018/12/15").setAuthor("chen").setRemarks("test").setStatus("create").setVersion("V1.0"),
                RevisionLog.getLog().setRevisionTime("2018/12/16").setAuthor("chen2").setRemarks("test2").setStatus("update").setVersion("V2.0")
        );

        //since 1.7.5
        //文档添加数据字典，非必须，根据项目决定
        config.setDataDictionaries(
//                ApiDataDictionary.dict().setTitle("订单状态").setEnumClass(OrderEnum.class).setCodeField("code").setDescField("desc"),
//                ApiDataDictionary.dict().setTitle("订单状态1").setEnumClass(OrderEnum.class).setCodeField("code").setDescField("desc")
        );

        long start = System.currentTimeMillis();
        //生成Markdown文件
        ApiDocBuilder.builderControllersApi(config);

        long end = System.currentTimeMillis();
        DateTimeUtil.printRunTime(end, start);
    }
}
