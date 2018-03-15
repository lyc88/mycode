package com.whyuan.$2Concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class ForkJoinPoolDemo {
    public static void main(String[] args) {
        //创建的并行级别为4
        ForkJoinPool forkJoinPool=new ForkJoinPool(4);

        MyRecursiveTask myRecursiveTask = new MyRecursiveTask(128);
        long mergedResult = forkJoinPool.invoke(myRecursiveTask);//获取最终执行结果的
        System.out.println("mergedResult = " + mergedResult);
    }
}

//RecursiveAction表示 “行动”：没有任何返回值的任务。比如 写数据到磁盘，写完就退出了
class MyRecursiveAction extends RecursiveAction {
    private long workLoad = 0;

    public MyRecursiveAction(long workLoad) {
        this.workLoad = workLoad;
    }

    @Override
    protected void compute() {
        //if work is above threshold, break tasks up into smaller tasks
        if(this.workLoad > 16) {
            System.out.println("Splitting workLoad : " + this.workLoad);
            List<MyRecursiveAction> subtasks =
                    new ArrayList<MyRecursiveAction>();
            subtasks.addAll(createSubtasks());
            for(RecursiveAction subtask : subtasks){
                subtask.fork();
            }
        } else {
            System.out.println("Doing workLoad myself: " + this.workLoad);
        }
    }
    private List<MyRecursiveAction> createSubtasks() {
        List<MyRecursiveAction> subtasks =
                new ArrayList<MyRecursiveAction>();
        MyRecursiveAction subtask1 = new MyRecursiveAction(this.workLoad / 2);
        MyRecursiveAction subtask2 = new MyRecursiveAction(this.workLoad / 2);
        subtasks.add(subtask1);
        subtasks.add(subtask2);
        return subtasks;
    }
}

//RecursiveTask表示“任务”：执行完，返回结果
//它可以将自己的工作分割为若干更小任务，并将这些子任务的执行结果合并到一个集体结果
class MyRecursiveTask extends RecursiveTask<Long> {
    private long workLoad = 0;
    public MyRecursiveTask(long workLoad) {
        this.workLoad = workLoad;
    }
    protected Long compute() {
//if work is above threshold, break tasks up into smaller tasks
        if(this.workLoad > 16) {
            System.out.println("Splitting workLoad : " + this.workLoad);
            List<MyRecursiveTask> subtasks =
                    new ArrayList<MyRecursiveTask>();
            subtasks.addAll(createSubtasks());
            for(MyRecursiveTask subtask : subtasks){
                subtask.fork();//对子任务计划执行
            }
            long result = 0;
            for(MyRecursiveTask subtask : subtasks) {
                result += subtask.join();//收集子任务返回的结果
            }
            return result;
        } else {
            System.out.println("Doing workLoad myself: " + this.workLoad);
            return workLoad * 3;
        }
    }
    private List<MyRecursiveTask> createSubtasks() {
        List<MyRecursiveTask> subtasks =
                new ArrayList<MyRecursiveTask>();
        MyRecursiveTask subtask1 = new MyRecursiveTask(this.workLoad / 2);
        MyRecursiveTask subtask2 = new MyRecursiveTask(this.workLoad / 2);
        subtasks.add(subtask1);
        subtasks.add(subtask2);
        return subtasks;
    }
}