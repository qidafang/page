#coding: utf-8

#定义方法
def myfunction(param):
    print __name__ == "__main__";
    
    #if
    if param > 0:
        print "正数";
    else:
        print "非正数";
        
    #for
    list = [1, 2, 3];
    for l in list:
        print l;
    
    #局部变量直接声明
    myVar = 1;
    
    #python中的map
    #http://woodpecker.org.cn/diveintopython/native_data_types/index.html
    myMap = {"name" : "427studio"};
    myMap.keys();
    myMap.values();
    myMap.items();
    print "字典：" + myMap["name"];
    
    #python中的list
    #http://woodpecker.org.cn/diveintopython/native_data_types/lists.html
    myList = [myMap];
    print "数组：" + myList[0]["name"];
    
    #python中的tuple
    #http://woodpecker.org.cn/diveintopython/native_data_types/tuples.html
    myTuple = ("abc", "def");
    print "元组：" + myTuple[0];
    
    #格式化字符串
    str = "%s=%s" % ("abc", "def");
    print str;

    #语法糖
    params = {"server":"mpilgrim", "database":"master", "uid":"sa", "pwd":"secret"};
    paramsList = ["%s=%s" % (k, v) for k, v in params.items()]
    print paramsList;
    
#定义类
class MyClass:
    #静态变量
    myField = "";
    def __init__(self, name):
        #实例变量
        self.name = name;
    
    def myMethod(self):
        return self.name;