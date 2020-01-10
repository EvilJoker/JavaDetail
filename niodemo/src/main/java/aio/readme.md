这个模块分为client和server
老生常谈作为一个server端的动作有accept--->read--->write
无论BIO还是多路复用，都是有主线程去跟进处理socket的状态：
BIO:服务端接受accept后，会产生子线程跟进，I/O阻塞怎么办用挂起来解决不同线程的处理
NIO:服务器将socket分为多个状态，多路复用器selector, 作为一个管理者去保留不同socket的信息。常驻线程负责轮询selector，执行一个一个socket。
而一个socket的完成，也成了一个状态接一个状态的分布。
`AIO`是AIO的更近一步，不再由常驻线程显示的处理一个接一个的`挪移`socket的状态。而是给利用`回调`的思想，每一个阶段会定义一个处理器`handler`,
一个接一个的执行。

所以server示例中可见多个hanlder的定义