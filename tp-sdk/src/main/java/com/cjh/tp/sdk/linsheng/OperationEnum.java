package com.cjh.tp.sdk.linsheng;

/**
 * @program: tp
 * @description:
 * @author: chenjiehan
 * @create: 2020-10-29 13:44
 **/
public enum OperationEnum {

    SUM(){
        @Override
        public double doResult(double a,double b) {
            return a+b;
        }
    },
    MAX(){
        @Override
        public double doResult(double a,double b) {
            return Math.max(a,b);
        }
    };

    public abstract double doResult(double a,double b);

    OperationEnum(){

    }
}
