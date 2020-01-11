#nettydemo
BIO、NIO、AIO示例可以了解java的不同I/O模型，严格上来讲，AIO才算异步，其余都是同步。
netty作为java NIO2.0库的封装，提供了更上层的接口，节省了底层的调试成本
还是基于原先的NIO示例，现在看一下netty是怎么实现的

代码当做黑盒来看待，想要消化，需要理解netty的类结构