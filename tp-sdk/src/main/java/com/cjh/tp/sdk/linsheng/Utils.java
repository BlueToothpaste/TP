package com.cjh.tp.sdk.linsheng;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class Utils {
    /**
     * Question1, sort by firstName + lastName + ext,
     * if firstName is the same then sort by lastName and
     * ext, please note lastName and ext can be empty
     * string or null.
     **/
    public static List<Extension> sortByName(List<Extension> extensions) {
        extensions.sort(new Comparator<Extension>() {
            @Override
            public int compare(Extension o1, Extension o2) {
                int i = compareTo(o1.getFirstName(), o2.getFirstName());
                if (0 == i) {
                    int j = compareTo(o1.getLastName(), o2.getLastName());
                    if (0 == j) {
                        return compareTo(o1.getExt(), o2.getExt());
                    }
                    return j;
                }
                return i;
            }
        });
        return extensions;
    }

    /**
     * Question2, sort extType, extType is a string and can
     * be "User", "Dept", "AO", "TMO", "Other",
     * sort by User > Dept > AO > TMO > Other;
     **/
    public static List<Extension> sortByExtType(List<Extension> extensions) {
        extensions.sort(new Comparator<Extension>() {
            @Override
            public int compare(Extension o1, Extension o2) {
                return compareByExtType(o1.getExtType(), o2.getExtType());
            }
        });
        return extensions;
    }

    /**
     * compare two string ignorecase
     **/
    public static int compareTo(String s1, String s2) {
        return StringUtils.isEmpty(s1) ? 1 : StringUtils.isEmpty(s2) ? -1 : s1.compareToIgnoreCase(s2);
    }

    /**
     * compare two string by extType
     **/
    public static int compareByExtType(String s1, String s2) {
        int idx1 = Constant.extTypeList.indexOf(s1);
        int idx2 = Constant.extTypeList.indexOf(s2);
        if (-1 == idx1 || -1 == idx2) {
            return -1;
        }
        return idx2 - idx1;
    }

    /**
     * Question3, sum all sales items by quarter
     **/
    public static List<QuarterSalesItem> sumByQuarter(List<SaleItem> saleItems) {
        return computeByQuarter(saleItems, OperationEnum.SUM);
    }

    /**
     * Question4, max all sales items by quarter
     **/
    public static List<QuarterSalesItem> maxByQuarter(List<SaleItem> saleItems) {
        return computeByQuarter(saleItems, OperationEnum.MAX);
    }

    public static List<QuarterSalesItem> computeByQuarter(List<SaleItem> saleItems, OperationEnum operationEnum) {
        Map<Integer, Object> map = new HashMap<>();
        for (SaleItem item : saleItems) {
            int quarter = getQuarter(item.getMonth());
            double salesNumbers = item.getSaleNumbers();
            if (map.containsKey(quarter)) {
                operationEnum.doResult(salesNumbers,(double) map.get(quarter));
            }
            map.put(quarter, salesNumbers);
        }
        return convertMapToList(map);
    }

    public static List<QuarterSalesItem> convertMapToList(Map<Integer, Object> map) {
        List<QuarterSalesItem> list = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            QuarterSalesItem quarterSalesItem = new QuarterSalesItem();
            quarterSalesItem.setQuarter(i);
            quarterSalesItem.setValue((Double) map.get(i));
            list.add(quarterSalesItem);
        }
        return list;
    }

    /**
     * get the quarter of the month
     *
     * @param month [1,2,....12]
     * @return
     */
    public static int getQuarter(int month) {
        return (month + 2) / 3;
    }

    /**
     * Question5
     * We have all Keys: 0-9;  Orderliness?
     * usedKeys is an array to store all used keys like :[2,3,4];
     * We want to get all unused keys, in this example it would be: [0,1,5,6,7,8,9,]
     * <p>
     * algorithm complexity is O(N)
     */
    public static int[] getUnUsedKeys(int[] allKeys, int[] usedKeys) {
        int k = 0;
        int [] unUsedKeys = new int[allKeys.length-usedKeys.length];
        BitSet bitSet = new BitSet();
        for (int i = 0; i < usedKeys.length; i++) {
            bitSet.set(usedKeys[i], true);
        }
        for (int j = 0; j < allKeys.length; j++) {
            if (!bitSet.get(j)) {
                unUsedKeys[k++] = allKeys[j];
            }
        }
        return unUsedKeys;
    }

}
