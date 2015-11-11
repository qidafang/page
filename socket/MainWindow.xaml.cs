using System;
using System.IO;
using System.Net;
using System.Text;
using System.Windows;
using System.Runtime.Serialization.Json;
using System.Collections.Generic;
using System.Data.Common;

using MySql.Data;
using MySql.Data.MySqlClient;
using System.Data;
using System.Net.Sockets;
using System.Threading;
using System.Windows.Controls;

namespace qdf_test_wpf_1
{
    
    public partial class MainWindow : Window
    {
        Thread receiveThread;
        Socket clientSocket;
        byte[] socketResult = new byte[1024];
    
        public MainWindow()
        {
            InitializeComponent();
            
            this.Closing += doClose;//添加关闭窗口时的行为

            //设定服务器IP地址
            IPAddress ip = IPAddress.Parse("182.92.10.238");
            clientSocket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
            clientSocket.Connect(new IPEndPoint(ip, 427)); //建立连接
            receiveThread = new Thread(doReceive);//启动接收服务器消息的线程
            receiveThread.Start();
        }

        private void doClose(object o, System.ComponentModel.CancelEventArgs e) {
            clientSocket.Close();
            receiveThread.Abort();
        }
        
        private void buttonClicked(object sender, RoutedEventArgs e)
        {
            try
            {
                string sendMessage = this.textBox.Text + "\n";
                clientSocket.Send(Encoding.UTF8.GetBytes(sendMessage));
                Console.WriteLine("向服务器发送消息：" + sendMessage);
            }
            catch
            {
                clientSocket.Shutdown(SocketShutdown.Both);
                clientSocket.Close();
            }
        }

        //接收服务器消息的线程的线程体
        void doReceive() {
            while (true) {
                int receiveLength = clientSocket.Receive(socketResult);
                String msg = Encoding.UTF8.GetString(socketResult, 0, receiveLength);

                //改变控件内容
                this.Dispatcher.BeginInvoke(System.Windows.Threading.DispatcherPriority.Normal, (ThreadStart)delegate ()
                {
                    theLabel.Content = msg;
                });

            }
        }
    }
}
