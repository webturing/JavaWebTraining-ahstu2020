# 24点小游戏的设计

by ZHAO Jing 



## 第一讲:概述

## 1 项目准备

- GIT建仓库
- 项目同步
- 整体框架

## 2解决方案：
- 用后缀表达式来解决中缀产生的括号问题
- 后缀表达式的计算和合法性的判断
- 随机后缀表达式的搜索和控制
- 二叉树的生成和中序遍历
- 括号的优化处理
- 数据库的存储和优化
- 移动端的界面设计


## 第2讲:后缀表达式引擎的实现
- 符号的类型判断:isNumber
```java
public static boolean isNumber(String tok) {
        try {
            Double.parseDouble(tok);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
```
- 后缀表达式判断和计算：栈

```java
public static double eval(List<String> exp) {
      Stack<Double> S = new Stack<>();
      for (String tok : exp) {
          if (isNumber(tok)) {
              S.push(Double.parseDouble(tok));
          } else {
              if (S.isEmpty()) {
                  return ERROR;
              }
              double b = S.peek();
              S.pop();
              if (S.isEmpty()) {
                  return ERROR;
              }
              double a = S.peek();
              S.pop();
              double c = 0;
              switch (tok) {
                  case "+":
                      c = a + b;
                      break;
                  case "-":
                      c = a - b;
                      break;
                  case "*":
                      c = a * b;
                      break;
                  case "/":
                      if (b == 0) return ERROR;
                      c = a / b;
                      break;

              }
              S.push(c);
          }

      }
      return S.size() != 1 ? ERROR : S.peek();
  }

  public static double eval(String[] exp) {
      return eval(Arrays.asList(exp));
  }

  public static double eval(String s) {
      return eval(s.split("\\s+"));

  }
```

  

## 第3讲:随机搜索器的设计和优化

- java方法设计中的委托delegate*

- 随即求解器的基本思想:shuffle

- 随机运算符的产生

- 搜索的时间控制（找不到解的处理，最大搜索次数）

  ```java
  private static String randSearcher(int[] a) {
          for (int j = 0; j < 4 * 4 * 4; j++) {
              String[] ops = new String[3];
              int x = j / 16;
              int y = j % 16 / 4;
              int z = j % 4;
              ops[0] = OPS[x];
              ops[1] = OPS[y];
              ops[2] = OPS[z];
  
              List<String> exp = new Vector<>();
              for (Integer i : a) {
                  exp.add(Integer.toString(i));
              }
              for (String op : ops) {
                  exp.add(op);
  
              }
  
              int tot = 0;
              while (++tot < MAX_RAND_SEARCH_COUNT) {
                  Collections.shuffle(exp);
                  double result = RPolandExpression.eval(exp);
                  if (result == 24) {
                      return (exp).toString();
                  }
              }
          }
          return "No solution";
      }
  
      public static String solve24(int a, int b, int c, int d) {
  		return randSearcher(new int[]{a,b,c,d});
      }
  ```

  
