package com.zlingchun.mybatis.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson2.JSON;
import com.zlingchun.mybatis.entity.dto.esayExcel.read.ReaderData;
import com.zlingchun.mybatis.listener.BaseListener;
import com.zlingchun.mybatis.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author achun
 * @create 2022/6/30
 * @description descrip
 */
@Slf4j
@SpringBootTest
public class EasyExcelReaderTest {
    @Resource
    BaseService dataService;
    private static String[] titles = {"iPhone", "华为", "小米"};
    private static BigDecimal[] prices = {BigDecimal.valueOf(100.21), BigDecimal.valueOf(20000.1), BigDecimal.valueOf(3000)};

    private List<ReaderData> data() {
        List<ReaderData> list = ListUtils.newArrayList();
        for (int i = 0; i < 10000; i++) {
            list.add(ReaderData.builder().title(titles[new Random().nextInt(3)]+i)
                    .price(prices[new Random().nextInt(3)].multiply(BigDecimal.valueOf(new Random().nextFloat())).setScale(2, RoundingMode.HALF_UP))
                    .proDate(LocalDateTime.now())
                    .build());
        }
        return list;
    }

    @Test
    void simpleWriter(){
        // 写法2
        String fileName = TestFileUtil.getPath() + File.separator + "demo.xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, ReaderData.class).sheet("模板").doWrite(data());
    }

    /**
     * 最简单的读
     * 1. 创建excel对应的实体对象 参照{@link ReaderData}
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link BaseListener}
     * 3. 直接读即可
     */
    @Test
    void simpleRead() {
        // 写法1：JDK8+ ,不用额外写一个BaseListener
        // since: 3.0.0-beta1
        String fileName = TestFileUtil.getPath() + "demo" +  File.separator + "demo.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        // 这里每次会读取100条数据 然后返回过来 直接调用使用数据就行
        EasyExcel.read(fileName, ReaderData.class, new PageReadListener<ReaderData>(dataList -> {
            for (ReaderData demoData : dataList) {
                log.info("读取到一条数据{}", JSON.toJSONString(demoData));
            }
        })).sheet().doRead();

        // 写法2：
        // 匿名内部类 不用额外写一个BaseListener
        fileName = TestFileUtil.getPath() + "demo" +  File.separator + "demo.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, ReaderData.class, new ReadListener<ReaderData>() {
            /**
             * 单次缓存的数据量
             */
            public static final int BATCH_COUNT = 100;
            /**
             *临时存储
             */
            private List<ReaderData> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
            @Override
            public void invoke(ReaderData data, AnalysisContext context) {
                cachedDataList.add(data);
                if (cachedDataList.size() >= BATCH_COUNT) {
                    saveData();
                    // 存储完成清理 list
                    cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
                }
            }
            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                saveData();
            }

            /**
             * 加上存储数据库
             */
            private void saveData() {
                log.info("{}条数据，开始存储数据库！", cachedDataList.size());
                log.info("存储数据库成功！");
            }
        }).sheet().doRead();

        // 有个很重要的点 BaseListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
        // 写法3：
        fileName = TestFileUtil.getPath() + "demo" +  File.separator + "demo.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, ReaderData.class, new BaseListener<ReaderData>(dataService)).sheet().doRead();

        // 写法4
        fileName = TestFileUtil.getPath() + "demo" +  File.separator + "demo.xlsx";
        // 一个文件一个reader
        ExcelReader excelReader = EasyExcel.read(fileName, ReaderData.class, new BaseListener(dataService)).build();
        // 构建一个sheet 这里可以指定名字或者no
        ReadSheet readSheet = EasyExcel.readSheet(0).build();
        // 读取一个sheet
        excelReader.read(readSheet);
    }

    /**
     * 指定列的下标或者列名
     * <p>1. 创建excel对应的实体对象,并使用{@link com.alibaba.excel.annotation.ExcelProperty}注解. 参照{@link ReaderData}
     * <p>2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link BaseListener}
     * <p>3. 直接读即可
     */
    @Test
    public void indexOrNameRead() {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // 这里默认读取第一个sheet
        EasyExcel.read(fileName, ReaderData.class, new BaseListener(dataService)).sheet().doRead();
    }

    /**
     * 读多个或者全部sheet,这里注意一个sheet不能读取多次，多次读取需要重新读取文件
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link ReaderData}
     * <p>
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link BaseListener}
     * <p>
     * 3. 直接读即可
     */
    @Test
    public void repeatedRead() {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // 读取全部sheet
        // 这里需要注意 BaseListener的doAfterAllAnalysed 会在每个sheet读取完毕后调用一次。然后所有sheet都会往同一个BaseListener里面写
        EasyExcel.read(fileName, ReaderData.class, new BaseListener(dataService)).doReadAll();
        // 读取部分sheet
        fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";

        // 写法1
        ExcelReader excelReader = EasyExcel.read(fileName).build();
        // 这里为了简单 所以注册了 同样的head 和Listener 自己使用功能必须不同的Listener
        ReadSheet readSheet1 =
                EasyExcel.readSheet(0).head(ReaderData.class).registerReadListener(new BaseListener(dataService)).build();
        ReadSheet readSheet2 =
                EasyExcel.readSheet(1).head(ReaderData.class).registerReadListener(new BaseListener(dataService)).build();
        // 这里注意 一定要把sheet1 sheet2 一起传进去，不然有个问题就是03版的excel 会读取多次，浪费性能
        excelReader.read(readSheet1, readSheet2);
    }

    /**
     * 日期、数字或者自定义格式转换
     * 默认读的转换器{@link com.alibaba.excel.converters.DefaultConverterLoader#loadDefaultReadConverter()}
     * <p>1. 创建excel对应的实体对象 参照{@link ReaderData}.里面可以使用注解{@link com.alibaba.excel.annotation.format.DateTimeFormat}、{@link com.alibaba.excel.annotation.format.NumberFormat}或者自定义注解
     * <p>2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link BaseListener}
     * <p>3. 直接读即可
     */
    @Test
    public void converterRead() {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet
        EasyExcel.read(fileName, ReaderData.class, new BaseListener(dataService))
                // 这里注意 我们也可以registerConverter来指定自定义转换器， 但是这个转换变成全局了， 所有java为string,excel为string的都会用这个转换器。
                // 如果就想单个字段使用请使用@ExcelProperty 指定converter
                // .registerConverter(new CustomStringConverter())
                // 读取sheet
                .sheet().doRead();
    }

    /**
     * 多行头
     *
     * <p>1. 创建excel对应的实体对象 参照{@link ReaderData}
     * <p>2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link BaseListener}
     * <p>3. 设置headRowNumber参数，然后读。 这里要注意headRowNumber如果不指定， 会根据你传入的class的{@link com.alibaba.excel.annotation.ExcelProperty#value()}里面的表头的数量来决定行数，
     * 如果不传入class则默认为1.当然你指定了headRowNumber不管是否传入class都是以你传入的为准。
     */
    @Test
    public void complexHeaderRead() {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet
        EasyExcel.read(fileName, ReaderData.class, new BaseListener(dataService)).sheet()
                // 这里可以设置1，因为头就是一行。如果多行头，可以设置其他值。不传入也可以，因为默认会根据ReaderData 来解析，他没有指定头，也就是默认1行
                .headRowNumber(1).doRead();
    }

    /**
     * 同步的返回，不推荐使用，如果数据量大会把数据放到内存里面
     */
    @Test
    public void synchronousRead() {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 同步读取会自动finish
        List<ReaderData> list = EasyExcel.read(fileName).head(ReaderData.class).sheet().doReadSync();
        for (ReaderData data : list) {
            log.info("读取到数据:{}", JSON.toJSONString(data));
        }
        // 这里 也可以不指定class，返回一个list，然后读取第一个sheet 同步读取会自动finish
        List<Map<Integer, String>> listMap = EasyExcel.read(fileName).sheet().doReadSync();
        for (Map<Integer, String> data : listMap) {
            // 返回每条数据的键值对 表示所在的列 和所在列的值
            log.info("读取到数据:{}", JSON.toJSONString(data));
        }
    }

    /**
     * 读取表头数据
     * 1. 创建excel对应的实体对象 参照{@link ReaderData}
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link BaseListener}
     * 3. 直接读即可
     */
    @Test
    public void headerRead() {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet
        EasyExcel.read(fileName, ReaderData.class, new BaseListener(dataService)).sheet().doRead();
    }
}
