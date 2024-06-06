package com.example.utils;

import java.util.*;

public class HuffmanUtil {

    static class HuffmanNode implements Comparable<HuffmanNode> {
        char ch;
        int freq;
        HuffmanNode left;
        HuffmanNode right;

        HuffmanNode(char ch, int freq) {
            this.ch = ch;
            this.freq = freq;
        }

        @Override
        public int compareTo(HuffmanNode node) {
            return this.freq - node.freq;
        }
    }

    // 生成哈夫曼编码表
    private static void buildCode(Map<Character, String> huffmanCode, HuffmanNode root, String str) {
        if (root == null) {
            return;
        }

        // 找到叶子节点
        if (root.left == null && root.right == null) {
            huffmanCode.put(root.ch, str);
        }

        buildCode(huffmanCode, root.left, str + "0");
        buildCode(huffmanCode, root.right, str + "1");
    }

    // 压缩方法
    public static Map<String, Object> compress(String text) {
        // 统计字符频率
        Map<Character, Integer> freq = new HashMap<>();
        for (char ch : text.toCharArray()) {
            freq.put(ch, freq.getOrDefault(ch, 0) + 1);
        }

        // 构建哈夫曼树
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>();
        for (var entry : freq.entrySet()) {
            pq.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        while (pq.size() > 1) {
            HuffmanNode left = pq.poll();
            HuffmanNode right = pq.poll();
            int sum = 0;
            if (right != null) {
                sum = left.freq + right.freq;
            }
            HuffmanNode node = new HuffmanNode('\0', sum);
            node.left = left;
            node.right = right;
            pq.add(node);
        }

        HuffmanNode root = pq.peek();
        Map<Character, String> huffmanCode = new HashMap<>();
        buildCode(huffmanCode, root, "");

        // 生成压缩后的二进制比特串
        StringBuilder sb = new StringBuilder();
        for (char ch : text.toCharArray()) {
            sb.append(huffmanCode.get(ch));
        }

        // 计算未满8位的填充位数
        int padding = 8 - (sb.length() % 8);
        if (padding == 8) {
            padding = 0;
        }

        // 在二进制字符串末尾添加填充位
        sb.append("0".repeat(padding));

        // 将二进制字符串转换为字节数组
        byte[] byteArray = binaryStringToByteArray(sb.toString());

        Map<String, Object> result = new HashMap<>();
        result.put("encodedBytes", byteArray);
        result.put("huffmanCode", huffmanCode);
        result.put("padding", padding); // 保存填充位数

        return result;
    }

    // 将二进制字符串转换为字节数组
    private static byte[] binaryStringToByteArray(String binaryString) {
        int len = binaryString.length();
        int byteLen = (len + 7) / 8; // 每8位一个字节，计算需要多少字节
        byte[] byteArray = new byte[byteLen];

        for (int i = 0; i < len; i++) {
            if (binaryString.charAt(i) == '1') {
                byteArray[i / 8] |= (byte) (1 << (7 - (i % 8)));
            }
        }

        return byteArray;
    }

    // 解压方法
    public static String decompress(byte[] encodedBytes, Map<Character, String> huffmanCode, int padding) {
        // 将字节数组转换回二进制字符串
        StringBuilder binaryString = new StringBuilder();
        for (byte b : encodedBytes) {
            binaryString.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
        }

        // 去掉末尾填充位
        binaryString.setLength(binaryString.length() - padding);

        Map<String, Character> reverseHuffmanCode = new HashMap<>();
        for (var entry : huffmanCode.entrySet()) {
            reverseHuffmanCode.put(entry.getValue(), entry.getKey());
        }

        StringBuilder result = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        for (char bit : binaryString.toString().toCharArray()) {
            temp.append(bit);
            if (reverseHuffmanCode.containsKey(temp.toString())) {
                result.append(reverseHuffmanCode.get(temp.toString()));
                temp.setLength(0);
            }
        }

        return result.toString();
    }
}
