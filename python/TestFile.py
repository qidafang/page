#coding: utf-8

#引入其他模块
from test.TestModule import MyClass, myfunction

#main函数
if __name__ == "__main__":
    #使用引入的类
    #实例化
    myobject = MyClass("427studio");
    #调用实例方法
    myResult = myobject.myMethod();
    
    #使用引入的方法
    myfunction(1);
    
    print myResult;