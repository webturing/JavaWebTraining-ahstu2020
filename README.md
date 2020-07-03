# 24点小游戏的设计

授课教师:安徽科技大学 赵老师[33470027@qq.com](33470027@qq.com)

# 第一讲:概述

## 1 项目准备

- GIT建仓库
- 项目同步
- 整体框架

## 2解决方案：
- 用后缀表达式来解决中缀产生的括号问题
- 后缀表达式的计算和合法性的判断
- 随机后缀表达式的搜索和控制
- 高效的确定搜索器
- 二叉树的生成和中序遍历
- 括号的优化处理
- 括号终极处理
- 数据库的存储和优化
- 移动端的界面设计

# 第二讲:后缀表达式引擎的实现

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

  

## 随机搜索器的设计和优化

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
- 运算数判断

  ```java
  public static boolean isNumber(String s) {//巧妙利用异常机制来处理 也可以使用正则表达式来判断
  		try{
  			Double.parseDouble(s);
  			return true;
  		}catch(NumberFormatException e){
  			return false;
  		}
  }
  ```

  

## Core Java核心技术基础           

-  字符串String/StringBuffer/StringBuilder/char[]

-   输入输出 File/Scanner/….

- **异常处理 Exception Handling**
- 集合框架 java.uitl.  Stack<Double>

- 日期/高精度

-  随机数: //**游戏开发必备技能**

   -  Math.random()

   -  java.util.Random()

   -  Collections.shuffle(List s)

      ```java
      public class RandDemo {
      	static Random rand = new Random();
      	public static void main(String[] args) {
      		System.out.println(Math.random());//产生0-1之间的随机小数
      		System.out.println(rand.nextInt(10));//产生[0,10)之间的随机整数
      		List<Integer> balls = new ArrayList<Integer>();
      		for (int i = 1; i <= 15; i++)
      			balls.add(i);
      		System.out.println(balls);
      		Collections.shuffle(balls);
      		System.out.println(balls);
      		List<Integer> firstPrize = balls.subList(0, 6);
      		System.out.println("First Prize:" + firstPrize);
      	}
      }
      ```

      

### 通用类型函数的实现

- 重载所有基础类型
- 重载对象类型Object或者对应的接口

# 第三讲：24游戏的设计2-后缀转中缀（二叉树）
## 二叉树及性质
- 递归定义

- java实现链式结构非常方便
 ## 二叉树的实现

  ```java
public void midVisit() {
		System.out.print("(");
		if(left!=null)left.midVisit();
		System.out.print(root);
		if(right!=null)right.midVisit();	
		System.out.print(")");
	}
public BinaryTree(String root) {    this(root,null,null);//构造方法的重载}
public BinaryTree(String root, BinaryTree left, BinaryTree right) {
    this.root = root;
    this.left = left;
    this.right = right;
}
public String getRoot() {    return root;}
public void setRoot(String root) {    this.root = root;}
public BinaryTree getLeft() {    return left;}
public void setLeft(BinaryTree left) {    this.left = left;}
public BinaryTree getRight() {  return right;}
public void setRight(BinaryTree right) {    this.right = right;}

String root;
BinaryTree left;
BinaryTree right;
  ```

## 根据后缀转中缀核心算法：

### 建立二叉树算法

```java
/**
	 * 根据后缀式还原二叉树
	 * @param 后缀表达式exp
	 * @return 一个二叉树tree 其后序遍历为exp
	 */
public static BinaryTree createTree(String[] exp) {
    Stack<BinaryTree> stack=new Stack<BinaryTree>();
    for(String s:exp){
        if(Evaluator.isNumber(s)){
            BinaryTree t=new BinaryTree(s);
            stack.push(t);				
        }else if (Evaluator.isOperator(s)){
            BinaryTree t=new BinaryTree(s);// operator
            BinaryTree right=stack.pop();
            BinaryTree left=stack.pop();
            t.setLeft(left);
            t.setRight(right);
            stack.push(t);

        }
    }
    return stack.peek();
}
```

### 中序遍历（括号和移植)

#### 版本1 ：耦合了System.out, 冗余括号较多

```java
public void midVisit() {
    System.out.print("(");
    if(left!=null)left.midVisit();
    System.out.print(root);
    if(right!=null)right.midVisit();	
    System.out.print(")");
}
```

#### 版本2:  解决移植性

```java
public void midVisit(StringBuffer buffer) {
    if (braced)
        buffer.append("(");
    if (left != null)
        left.midVisit(buffer);
    buffer.append(root);
    if (right != null)
        right.midVisit(buffer);
    if (braced)
        buffer.append(")");
}
```

### 版本3：括号优化技术

#### 括号优化

```java
public void setLeft(BinaryTree left) {
    this.left = left;
    left.braced = Evaluator.less(left.root, root);
}
public void setRight(BinaryTree right) {
    this.right = right;
    right.braced = Evaluator.lessOrEqual(root, right.root);
}
```

#### 算符判定

```java
public static boolean lessOrEqual(String a, String b) {
    return Arrays.asList("-- -+ *+ *- */ /+ /- /* //".split(" ")).contains(
        a + b);
}

public static boolean less(String a, String b) {
    return Arrays.asList("+* +/ -* -/".split(" ")).contains(a + b);
}
```
# 第四讲：24游戏的改进III------确定性搜索器和数据库存储技术

## 康拓展开和全排列

###  康拓展开： 全排列 映射为 0~n!-1

###康拓逆展开:   0~n！-1 映射为 全排列 

```java
/**
	 * 逆康拓展开，根据数值直接生成排列
	 * @param x
	 * @param m
	 * @return
	 */
	static final int FAC[] = { 1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880 };
	public static int[] codel(int x, int m) {
		int[] label = new int[m];
		int[] n = new int[m];
		int cnt;
		for (int i = 0; i < m; i++)
			label[i] = 1;
		for (int i = 0; i < m; i++) {
			cnt = x / FAC[m - 1 - i];
			x = x % FAC[m - 1 - i];
			for (int j = 0; j < m; j++) {
				if (label[j] == 0)
					continue;
				if (cnt == 0) {
					label[j] = 0;
					n[i] = j;
					break;
				}
				cnt--;
			}
		}
		return n;
	}
```



## 改进的后缀式枚举：

### （4个运算数 三个算符XYZ组成的二叉树只有5种）

- { a, b, X, c, Y, d, Z },
- { a, b, c, X, Y, d, Z },
- { a, b, X, c, d, Y, Z },
- { a, b, c, X, d, Y, Z },
- { a, b, c, d, X, Y, Z }

### 算法实现

```java
public static List<String> bruteSearch(int[] arr) {
		 List<String> exp=new ArrayList<String>();
		for (int cc = 0; cc < Permutation.FAC[4]; cc++) {
			int[] idx = Permutation.codel(cc, 4);
			String a = String.valueOf(arr[idx[0]]);
			String b = String.valueOf(arr[idx[1]]);
			String c = String.valueOf(arr[idx[2]]);
			String d = String.valueOf(arr[idx[3]]);
			for (int i = 0; i < 4; i++)
				for (int j = 0; j < 4; j++)
					for (int k = 0; k < 4; k++) {
						String X = Point24.OPS[i];
						String Y = Point24.OPS[j];
						String Z = Point24.OPS[k];
						for (String[] ee : new String[][] {
								{ a, b, X, c, Y, d, Z },
								{ a, b, c, X, Y, d, Z },
								{ a, b, X, c, d, Y, Z },
								{ a, b, c, X, d, Y, Z },
								{ a, b, c, d, X, Y, Z }, }) {
							if (Evaluator.eval(ee) == Point24.GOAL) {
								exp.addAll(Arrays.asList(ee));
								return exp;
							}
						}
					}
		}
		exp.clear();
		return exp;
	}
```

## 记忆化搜索和数据库技术:

### 动态规划入门：记忆化搜索


#### solve计算流程

```java
int[] arr = new int[]{a, b, c, d};
Arrays.sort(arr);
String key = Arrays.toString(arr);
String result = query(key);//先查询数据库
if (result == null) {
  System.err.println("重新计算结果并写入数据库");
  result = ai.Engine.solve24(a, b, c, d);
  save(key, result);//保留计算结果
}
long end=System.currentTimeMillis();
request.getSession().setAttribute("result", result);
request.getSession().setAttribute("elapse", (end-start)/1000.0);
response.sendRedirect("index.jsp");
```

#### 数据库配置：

```java
public class SolveServlet extends HttpServlet {
    private String diverClass;
    private String userName;
    private String password;
    private String url;
    private Connection connection;
    private Statement stmt;

    @Override
    public void init() throws ServletException {
        diverClass = /* getServletConfig(). */getServletContext().getInitParameter("driver");
        userName = /* getServletConfig(). */getServletContext().getInitParameter("username");
        password = /* getServletConfig(). */getServletContext().getInitParameter("password");
        url = /* getServletConfig(). */getServletContext().getInitParameter("url");

        try {
            Class.forName(diverClass);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```

Web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
         version="3.1">
    <context-param>
        <param-name>driver</param-name>
        <param-value>com.mysql.jdbc.Driver</param-value>
    </context-param>
    <context-param>
        <param-name>url</param-name>
        <param-value>jdbc:mysql://#############:3306/ahstu</param-value>
    </context-param>
    <context-param>
        <param-name>username</param-name>
        <param-value>#####</param-value>
    </context-param>
    <context-param>
        <param-name>password</param-name>
        <param-value>######</param-value>
    </context-param>
</web-app>
```



#### 查询

```java
private String query(String key) {
        String ans = null;

        try {
            connection = DriverManager.getConnection(url, userName, password);
            stmt = connection.createStatement();//key
            PreparedStatement ps = connection.prepareStatement("select solution from point24 where numbers=? LIMIT 1");
            ps.setString(1, key);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ans = rs.getString(1);
                System.err.println("数据已经存在，直接读取数据库");

            }

            rs.close();
            stmt.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("数据库链接失败");
        }
        return ans;
    }
```

#### 数据库写入

```java
    private boolean save(String key, String result) {
        try {
            connection = DriverManager.getConnection(url, userName, password);
            stmt = connection.createStatement();
            PreparedStatement ps = connection.prepareStatement("insert into point24(numbers,solution) value(?,?)");

            ps.setString(1, key);
            ps.setString(2, result);
            int x = ps.executeUpdate();
            System.err.println("写入数据库成功");
            stmt.close();
            connection.close();
            return x > 0;

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("数据库链接失败");
            return false;
        }
    }
```



# 第五讲：24游戏的持续改进

## 存储所有结果

## 排序结果散列

## 网络数据库和本地数据库的同步

### 网络/蓝牙对战模式？





# 总结：本案例所设计的知识要点：

1. Java 核心编程技术
2. 数据结构（二叉树、栈）
3. 算法和编译原理： 表达式计算、算符优先关系计算
4. 数据库技术
5. Jsp/servlet开发基础  
6. web前端优化（HTML5）
7. 软件工程GIT/GITHUB和文档Markdown

  
