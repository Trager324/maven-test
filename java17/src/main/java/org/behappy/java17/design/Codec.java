package org.behappy.java17.design;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class Codec {
    HuffmanTree tree;
    int size;

    public static void main(String[] args) {
        Codec codec = new Codec();
        String longUrl = "http://www.google.com";
        String shortUrl = codec.encode(longUrl);
        System.out.println(shortUrl);
        System.out.println(codec.decode(shortUrl));
    }

    public String encode(String longUrl) {
        char[] cs = longUrl.toCharArray();
        tree = new HuffmanTree(cs);
        return new String(tree.getBucket().data, StandardCharsets.ISO_8859_1);
    }

    public String decode(String shortUrl) {
        return tree.decode();
    }

    static class HuffmanTree {
        HuffmanNode root;
        Map<Character, String> map;
        char[] cs;
        Bucket bucket;
        HuffmanTree(char[] cs) {
            this.cs = cs;
            int[] cnt = new int[256];
            for (char c : cs) {
                cnt[c]++;
            }
            PriorityQueue<HuffmanNode> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.v));
            for (int i = 0; i < 256; i++) {
                if (cnt[i] > 0) {
                    HuffmanNode node = new HuffmanNode();
                    node.v = cnt[i];
                    node.c = (char)i;
                    pq.offer(node);
                }
            }
            while (pq.size() > 1) {
                HuffmanNode l = pq.poll();
                HuffmanNode r = pq.poll();
                HuffmanNode node = new HuffmanNode();
                node.l = l;
                node.r = r;
                node.v = l.v + r.v;
                pq.offer(node);
            }
            root = pq.poll();
            map = buildHuffmanMap(root);
            bucket = buildBucket();
        }

        Bucket getBucket() {
            return bucket;
        }

        Bucket buildBucket() {
            Bucket bucket = new Bucket();
            StringBuilder sb = new StringBuilder();
            for (char c : cs) {
                sb.append(getCode(c));
            }
            int size = sb.length(), i = 0;
            bucket.size = sb.length();
            List<Byte> list = new ArrayList<>(size >>> 3 + 1);
            for (; i < size; i += 8) {
                byte b = 0;
                for (int j = 0; j < 8 && i + j < size; j++) {
                    b <<= 1;
                    if (sb.charAt(i + j) == '1') {
                        b |= 1;
                    }
                }
                list.add(b);
            }
            byte[] bytes = new byte[list.size()];
            for (i = 0; i < list.size(); i++) {
                bytes[i] = list.get(i);
            }
            bucket.data = bytes;
            return bucket;
        }

        String getCode(Character c) {
            return map.get(c);
        }

        Map<Character, String> buildHuffmanMap(HuffmanNode root) {
            Map<Character, String> map = new HashMap<>();
            dfs(root, new StringBuilder(), map);
            return map;
        }

        void dfs(HuffmanNode node, StringBuilder sb, Map<Character, String> map) {
            if (node.l == null && node.r == null) {
                map.put(node.c, sb.toString());
                return;
            }
            if (node.l != null) {
                sb.append('0');
                dfs(node.l, sb, map);
                sb.deleteCharAt(sb.length() - 1);
            }
            if (node.r != null) {
                sb.append('1');
                dfs(node.r, sb, map);
                sb.deleteCharAt(sb.length() - 1);
            }
        }

        HuffmanNode stateMachine(HuffmanNode node, byte b, int len, int mask, StringBuilder sb) {
            for (int j = 0; j < len; j++) {
                if ((b & mask) > 0) {
                    node = node.r;
                } else {
                    node = node.l;
                }
                if (node.l == null && node.r == null) {
                    sb.append(node.c);
                    node = root;
                }
                b <<= 1;
            }
            return node;
        }

        String decode() {
            StringBuilder sb = new StringBuilder();
            byte[] bytes = bucket.data;
            int i = 0, size = bucket.size, mask = 1 << 7, remain = size & 7, remainMask = 1 << (remain - 1);
            HuffmanNode node = root;
            for (; i + 8 < size; i += 8) {
                byte b = bytes[i >>> 3];
                node = stateMachine(node, b, 8, mask, sb);
            }
            byte b = bytes[bytes.length - 1];
            stateMachine(node, b, remain, remainMask, sb);
            return sb.toString();
        }

        static class Bucket {
            int size;
            byte[] data;
        }
    }

    static class HuffmanNode {
        HuffmanNode l, r;
        int v;
        char c;
    }
}

