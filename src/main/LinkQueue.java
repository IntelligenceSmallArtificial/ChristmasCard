package main;

//Queue ����
public class LinkQueue{
    private QueueNode front;	//����
    private QueueNode rear;		//��β
    private int size;
    public LinkQueue() {
    	front=new QueueNode(null);
    	rear=front;
	    size=0;
    }

    // �ж϶����Ƿ�Ϊ�� 
    public boolean QueueEmpty(){
	    return size==0;
	}
    
    //���
	public void EnQueue(Object message){
	    QueueNode node = new QueueNode(message);
	    rear.next=node;
	    rear=node;
	    size++;
	}
	
	//����
	public Object DeQueue(){
	    if(QueueEmpty()) return null;
	    Object tempMessage;
	    if(front.next==rear) rear=front;
	    QueueNode p=front.next;
	    tempMessage=p.message;
	    front.next=p.next;
	    size--;
	    return tempMessage;
	}
	
	//���ض�ͷ 
	public Object GetHead(){
	    if(QueueEmpty()) return null;
	    return front.next.message;
	}
	
	//���ض�β 
	public Object GetRear(){
	    if(QueueEmpty()) return null;
	    return rear.message;
	}
	
	//QueueNode �ڲ��� ���н��
	class QueueNode{
	    private Object message;
	    private QueueNode next;
	    public QueueNode(Object message,QueueNode next) {
	    	this.message=message;
	    	this.next=next;
	    }
	    public QueueNode(Object message) {
	    	this.message=message;
	    	next=null;
	    }
	}
}
