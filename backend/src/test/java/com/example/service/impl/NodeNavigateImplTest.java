package com.example.service.impl;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class NodeNavigateImplTest {
    private final String typeTwo = "short";
    private final String typeSome = "time";
    @Resource
    NodeNavigateImpl nodeNavigate;

    @BeforeEach
    void setUp() {
        System.out.println("测试开始");
    }

    /**
     * 测试包含非数字字符的字符串
     */
    @Test
    void testFindShortestPathWithTwo_invalidString1() {
        System.out.println("测试包含非数字字符的字符串");
        List<Integer> list = nodeNavigate.findShortestPathWithTwo("dadad1", "dadeee27", typeTwo);
        assertTrue(list != null && list.isEmpty(), "非法输入应返回空列表或null");
        System.out.println("最短路径为：" + list);
    }

    /**
     * 测试有效的from和to字符串
     */
    @Test
    void testFindShortestPathWithTwo_validString1() {
        System.out.println("测试有效的from和to字符串");
        List<Integer> list = nodeNavigate.findShortestPathWithTwo("address10", "location27", typeTwo);
        if (list != null) System.out.println("最短路径为：" + list);
    }

    /**
     * 测试长度不足的字符串
     */
    @Test
    void testFindShortestPathWithTwo_shortString1() {
        System.out.println("测试长度不足的字符串");
        List<Integer> list = nodeNavigate.findShortestPathWithTwo("a1", "a2", typeTwo);
        assertTrue(list != null && list.isEmpty(), "非法输入应返回空列表或null");
        System.out.println("最短路径为：" + list);
    }

    /**
     * 测试包含无效数字的字符串
     */
    @Test
    void testFindShortestPathWithTwo_invalidNumber1() {
        System.out.println("测试包含无效数字的字符串");
        List<Integer> list = nodeNavigate.findShortestPathWithTwo("data100", "data-1", typeTwo);
        assertTrue(list != null && list.isEmpty(), "非法输入应返回空列表或null");
        System.out.println("最短路径为：" + list);
    }

    /**
     * 测试不包含数字的字符串
     */
    @Test
    void testFindShortestPathWithTwo_nonNumeric1() {
        System.out.println("测试不包含数字的字符串");
        List<Integer> list = nodeNavigate.findShortestPathWithTwo("dataaa", "datab2", typeTwo);
        assertTrue(list != null && list.isEmpty(), "非法输入应返回空列表");
        System.out.println("最短路径为：" + list);
    }

    /**
     * 测试边界情况的字符串（00到99）
     */
    @Test
    void testFindShortestPathWithTwo_edgeCase1() {
        System.out.println("测试边界情况的字符串（00到99");
        List<Integer> list = nodeNavigate.findShortestPathWithTwo("node00", "node99", typeTwo);
        if (list != null) System.out.println("最短路径为：" + list);
    }

    /**
     * 测试空字符串
     */
    @Test
    void testFindShortestPathWithTwo_emptyString1() {
        System.out.println("测试空字符串");
        List<Integer> list = nodeNavigate.findShortestPathWithTwo("", "", typeTwo);
        assertTrue(list != null && list.isEmpty(), "非法输入应返回空列表");
        System.out.println("最短路径为：" + list);
    }

    /**
     * 测试单字符字符串
     */
    @Test
    void testFindShortestPathWithTwo_singleChar1() {
        System.out.println("测试单字符字符串");
        List<Integer> list = nodeNavigate.findShortestPathWithTwo("a", "b", typeTwo);
        assertTrue(list != null && list.isEmpty(), "非法输入应返回空列表");
        System.out.println("最短路径为：" + list);
    }

    /**
     * 测试有效的from和to字符串组合
     */
    @Test
    void testFindShortestPathWithTwo_validString2() {
        System.out.println("测试有效的from和to字符串组合");
        List<Integer> list = nodeNavigate.findShortestPathWithTwo("start01", "end50", typeTwo);
        if (list != null) System.out.println("最短路径为：" + list);
    }

    /**
     * 测试另一组边界情况的字符串（99到00）
     */
    @Test
    void testFindShortestPathWithTwo_edgeCase2() {
        System.out.println("测试另一组边界情况的字符串（99到00)");
        List<Integer> list = nodeNavigate.findShortestPathWithTwo("start99", "end00", typeTwo);
        if (list != null) System.out.println("最短路径为：" + list);
    }

    /**
     * 测试另一组包含无效字符的字符串
     */
    @Test
    void testFindShortestPathWithTwo_invalidString2() {
        System.out.println("测试另一组包含无效字符的字符串");
        List<Integer> list = nodeNavigate.findShortestPathWithTwo("startXX", "endYY", typeTwo);
        assertTrue(list != null && list.isEmpty(), "非法输入应返回空列表");
        System.out.println("最短路径为：" + list);
    }

    /**
     * 测试包含非数字字符的字符串（typeSome）
     */
    @Test
    void testFindShortestPathWithTwo_invalidString3() {
        System.out.println("测试包含非数字字符的字符串（typeSome）");
        List<Integer> list = nodeNavigate.findShortestPathWithTwo("dadad1", "dadeee27", typeSome);
        assertTrue(list != null && list.isEmpty(), "非法输入应返回空列表");
        System.out.println("最短路径为：" + list);
    }

    /**
     * 测试有效的from和to字符串（typeSome）
     */
    @Test
    void testFindShortestPathWithTwo_validString4() {
        System.out.println("测试有效的from和to字符串（typeSome）");
        List<Integer> list = nodeNavigate.findShortestPathWithTwo("address10", "location27", typeSome);
        if (list != null) System.out.println("最短路径为：" + list);
    }

    /**
     * 测试长度不足的字符串（typeSome）
     */
    @Test
    void testFindShortestPathWithTwo_shortString5() {
        System.out.println("测试长度不足的字符串（typeSome）");
        List<Integer> list = nodeNavigate.findShortestPathWithTwo("a1", "a2", typeSome);
        assertTrue(list != null && list.isEmpty(), "非法输入应返回空列表");
        System.out.println("最短路径为：" + list);
    }

    /**
     * 测试包含无效数字的字符串（typeSome）
     */
    @Test
    void testFindShortestPathWithTwo_invalidNumber2() {
        System.out.println("测试包含无效数字的字符串（typeSome）");
        List<Integer> list = nodeNavigate.findShortestPathWithTwo("data100", "data-1", typeSome);
        assertTrue(list != null && list.isEmpty(), "非法输入应返回空列表");
        System.out.println("最短路径为：" + list);
    }

    /**
     * 测试不包含数字的字符串（typeSome）
     */
    @Test
    void testFindShortestPathWithTwo_nonNumeric3() {
        System.out.println("测试不包含数字的字符串（typeSome）");
        List<Integer> list = nodeNavigate.findShortestPathWithTwo("dataaa", "datab2", typeSome);
        assertTrue(list != null && list.isEmpty(), "非法输入应返回空列表");
        System.out.println("最短路径为：" + list);
    }

    /**
     * 测试边界情况的字符串（00到99）（typeSome）
     */
    @Test
    void testFindShortestPathWithTwo_edgeCase3() {
        System.out.println("测试边界情况的字符串（00到99）（typeSome) ");
        List<Integer> list = nodeNavigate.findShortestPathWithTwo("node00", "node99", typeSome);
        if (list != null) System.out.println("最短路径为：" + list);
    }

    /**
     * 测试空字符串（typeSome）
     */
    @Test
    void testFindShortestPathWithTwo_emptyString2() {
        System.out.println("测试空字符串（typeSome）");
        List<Integer> list = nodeNavigate.findShortestPathWithTwo("", "", typeSome);
        assertTrue(list != null && list.isEmpty(), "非法输入应返回空列表");
        System.out.println("最短路径为：" + list);
    }

    /**
     * 测试单字符字符串（typeSome）
     */
    @Test
    void testFindShortestPathWithTwo_singleChar2() {
        System.out.println("测试单字符字符串（typeSome）");
        List<Integer> list = nodeNavigate.findShortestPathWithTwo("a", "b", typeSome);
        assertTrue(list != null && list.isEmpty(), "非法输入应返回空列表");
        System.out.println("最短路径为：" + list);
    }

    /**
     * 测试有效的from和to字符串组合（typeSome）
     */
    @Test
    void testFindShortestPathWithTwo_validString3() {
        System.out.println("测试有效的from和to字符串组合（typeSome）");
        List<Integer> list = nodeNavigate.findShortestPathWithTwo("start01", "end50", typeSome);
        if (list != null) System.out.println("最短路径为：" + list);
    }

    /**
     * 测试另一组边界情况的字符串（99到00）（typeSome）
     */
    @Test
    void testFindShortestPathWithTwo_edgeCase4() {
        System.out.println("测试另一组边界情况的字符串（99到00）（typeSome）");
        List<Integer> list = nodeNavigate.findShortestPathWithTwo("start99", "end00", typeSome);
        if (list != null) System.out.println("最短路径为：" + list);
    }

    /**
     * 测试另一组包含无效字符的字符串（typeSome）
     */
    @Test
    void testFindShortestPathWithTwo_invalidString4() {
        System.out.println("测试另一组包含无效字符的字符串（typeSome）");
        List<Integer> list = nodeNavigate.findShortestPathWithTwo("startXX", "endYY", typeSome);
        assertTrue(list != null && list.isEmpty(), "非法输入应返回空列表");
        System.out.println("最短路径为：" + list);
    }

    /**
     * 测试边界情况的字符串（98到99）（typeSome）
     */
    @Test
    void testFindShortestPathWithSome_edgeCase1() {
        System.out.println("测试边界情况的字符串（98到99）（typeSome）");
        List<Integer> list = nodeNavigate
                .findShortestPathWithSome("start98 end99", "time");
        if (list != null) System.out.println("最短路径为：" + list);
    }

    /**
     * 测试另一组边界情况的字符串（99到00）（typeSome）
     */
    @Test
    void testFindShortestPathWithSome_edgeCase2() {
        System.out.println("测试另一组边界情况的字符串（99到00）（typeSome）");
        List<Integer> list = nodeNavigate
                .findShortestPathWithSome("start99 end00", "time");
        if (list != null) System.out.println("最短路径为：" + list);
    }

    /**
     * 测试包含无效字符的字符串（typeSome）
     */
    @Test
    void testFindShortestPathWithSome_invalidString1() {
        System.out.println("测试包含无效字符的字符串（typeSome）");
        List<Integer> list = nodeNavigate
                .findShortestPathWithSome("startXX endYY", "time");
        assert list != null && list.isEmpty() : "非法输入应返回空列表";
        System.out.println("最短路径为：" + list);
    }

    /**
     * 测试另一组包含无效字符的字符串（typeSome）
     */
    @Test
    void testFindShortestPathWithSome_invalidString2() {
        System.out.println("测试另一组包含无效字符的字符串（typeSome）");
        List<Integer> list = nodeNavigate
                .findShortestPathWithSome("startXX endYY", "time");
        assert list != null && list.isEmpty() : "非法输入应返回空列表";
        System.out.println("最短路径为：" + list);
    }

    /**
     * 测试空字符串输入（typeSome）
     */
    @Test
    void testFindShortestPathWithSome_emptyString() {
        System.out.println("测试空字符串输入（typeSome）");
        List<Integer> list = nodeNavigate
                .findShortestPathWithSome("", "time");
        assert list != null && list.isEmpty() : "空输入应返回空列表";
        System.out.println("最短路径为：" + list);
    }

    /**
     * 测试混合有效和无效字符串（typeSome）
     */
    @Test
    void testFindShortestPathWithSome_mixedInvalidAndValid() {
        System.out.println("测试混合有效和无效字符串（typeSome）");
        List<Integer> list = nodeNavigate
                .findShortestPathWithSome("start01 endYY", "time");
        assert list != null && list.isEmpty() : "混合输入应返回空列表";
        System.out.println("最短路径为：" + list);
    }

    /**
     * 测试单个无效字符串（typeSome）
     */
    @Test
    void testFindShortestPathWithSome_singleInvalidString() {
        System.out.println("测试单个无效字符串（typeSome）");
        List<Integer> list = nodeNavigate
                .findShortestPathWithSome("invalidYY", "time");
        assert list != null && list.isEmpty() : "单个无效输入应返回空列表";
        System.out.println("最短路径为：" + list);
    }

    /**
     * 测试长度不足2的字符串（typeSome）
     */
    @Test
    void testFindShortestPathWithSome_shortString() {
        System.out.println("测试长度不足2的字符串（typeSome）");
        List<Integer> list = nodeNavigate
                .findShortestPathWithSome("a b", "time");
        assert list != null && list.isEmpty() : "长度不足的输入应返回空列表";
        System.out.println("最短路径为：" + list);
    }

    /**
     * 测试字符串中带有空格（typeSome）
     */
    @Test
    void testFindShortestPathWithSome_stringWithSpaces() {
        System.out.println("测试字符串中带有空格（typeSome）");
        List<Integer> list = nodeNavigate
                .findShortestPathWithSome("start 01 end 02", "time");
        assert list != null && list.isEmpty() : "带空格的输入应返回空列表";
        System.out.println("最短路径为：" + list);
    }

    /**
     * 测试起始和结束节点相同（typeSome）
     */
    @Test
    void testFindShortestPathWithSome_sameStartAndEnd() {
        System.out.println("测试起始和结束节点相同（typeSome）");
        List<Integer> list = nodeNavigate
                .findShortestPathWithSome("start01 start01", "time");
        if (list != null) System.out.println("最短路径为：" + list);
    }

    /**
     * 测试边界情况的字符串（98到99）（typeTime）
     */
    @Test
    void testFindShortestPathWithSome_edgeCase1_time() {
        System.out.println("测试边界情况的字符串（98到99）（typeTime）");
        List<Integer> list = nodeNavigate.findShortestPathWithSome("start98 end99", "time");
        if (list != null) System.out.println("最短路径为：" + list);
    }

    /**
     * 测试另一组边界情况的字符串（99到00）（typeTime）
     */
    @Test
    void testFindShortestPathWithSome_edgeCase2_time() {
        System.out.println("测试另一组边界情况的字符串（99到00）（typeTime）");
        List<Integer> list = nodeNavigate.findShortestPathWithSome("start99 end00", "time");
        if (list != null) System.out.println("最短路径为：" + list);
    }

    /**
     * 测试包含无效字符的字符串（typeTime）
     */
    @Test
    void testFindShortestPathWithSome_invalidString1_time() {
        System.out.println("测试包含无效字符的字符串（typeTime）");
        List<Integer> list = nodeNavigate.findShortestPathWithSome("startXX endYY", "time");
        assert list != null && list.isEmpty() : "非法输入应返回空列表";
        System.out.println("最短路径为：" + list);
    }

    /**
     * 测试另一组包含无效字符的字符串（typeTime）
     */
    @Test
    void testFindShortestPathWithSome_invalidString2_time() {
        System.out.println("测试另一组包含无效字符的字符串（typeTime）");
        List<Integer> list = nodeNavigate.findShortestPathWithSome("startXX endYY", "time");
        assert list != null && list.isEmpty() : "非法输入应返回空列表";
        System.out.println("最短路径为：" + list);
    }

    /**
     * 测试空字符串输入（typeTime）
     */
    @Test
    void testFindShortestPathWithSome_emptyString_time() {
        System.out.println("测试空字符串输入（typeTime）");
        List<Integer> list = nodeNavigate.findShortestPathWithSome("", "time");
        assert list != null && list.isEmpty() : "空输入应返回空列表";
        System.out.println("最短路径为：" + list);
    }

    /**
     * 测试混合有效和无效字符串（typeTime）
     */
    @Test
    void testFindShortestPathWithSome_mixedInvalidAndValid_time() {
        System.out.println("测试混合有效和无效字符串（typeTime）");
        List<Integer> list = nodeNavigate.findShortestPathWithSome("start01 endYY", "time");
        assert list != null && list.isEmpty() : "混合输入应返回空列表";
        System.out.println("最短路径为：" + list);
    }

    /**
     * 测试单个无效字符串（typeTime）
     */
    @Test
    void testFindShortestPathWithSome_singleInvalidString_time() {
        System.out.println("测试单个无效字符串（typeTime）");
        List<Integer> list = nodeNavigate.findShortestPathWithSome("invalidYY", "time");
        assert list != null && list.isEmpty() : "单个无效输入应返回空列表";
        System.out.println("最短路径为：" + list);
    }

    /**
     * 测试长度不足2的字符串（typeTime）
     */
    @Test
    void testFindShortestPathWithSome_shortString_time() {
        System.out.println("测试长度不足2的字符串（typeTime）");
        List<Integer> list = nodeNavigate.findShortestPathWithSome("a b", "time");
        assert list != null && list.isEmpty() : "长度不足的输入应返回空列表";
        System.out.println("最短路径为：" + list);
    }

    /**
     * 测试字符串中带有空格（typeTime）
     */
    @Test
    void testFindShortestPathWithSome_stringWithSpaces_time() {
        System.out.println("测试字符串中带有空格（typeTime）");
        List<Integer> list = nodeNavigate.findShortestPathWithSome("start 01 end 02", "time");
        assert list != null && list.isEmpty() : "带空格的输入应返回空列表";
        System.out.println("最短路径为：" + list);
    }

    /**
     * 测试起始和结束节点相同（typeTime）
     */
    @Test
    void testFindShortestPathWithSome_sameStartAndEnd_time() {
        System.out.println("测试起始和结束节点相同（typeTime）");
        List<Integer> list = nodeNavigate.findShortestPathWithSome("start01 start01", "time");
        if (list != null) System.out.println("最短路径为：" + list);
    }
}