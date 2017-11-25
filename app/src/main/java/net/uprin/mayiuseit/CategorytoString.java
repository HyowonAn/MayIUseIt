package net.uprin.mayiuseit;

/**
 * Created by uPrin on 2017. 11. 26..
 */


public class CategorytoString {
    public String intToString(int input){
        String output="";

        switch (input) {
            case 216 :
                output = "식품";
                break;
            case 609 :
                output = "의약품";
                break;
            case 610 :
                output = "의료기기";
                break;
            case 611 :
                output = "화장품";
                break;
            case 215 :
                output = "자동차";
                break;
            case 217 :
                output = "공산품";
                break;
            case 840 :
                output = "축산물";
                break;
            case 841 :
                output = "먹는물";
                break;
            case 219 :
                output = "해외리콜";
                break;
        }
     return output;
    }
}
