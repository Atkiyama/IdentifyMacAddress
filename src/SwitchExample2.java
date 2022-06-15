public class SwitchExample2 {

  public static void main(String[] args) {
    if( args.length != 3 ) {
        // 引数の数が３つ以外の場合はエラーメッセージを表示する
        System.out.println("USAGE:\n% java SwitchExample operand1 operator operand2\nOperator is +, -, x, /, or %.\n");

    } else {
        double operand1 = Double.parseDouble(args[0]); // １つ目の引数
        char operator = args[1].charAt(0); // ２つ目の引数
        double operand2 = Double.parseDouble(args[2]); // ３つ目の引数
        double result; // 計算結果を代入する変数
    }
        if(operator){
        // 2つ目の引数が適切な演算子（+または-またはxまたは/または%）ならば以下を実行
        /* (1) Calculatorクラスのインスタンスを作成 */
        Calculator answer = new Calculator(operand1, operand2);
        / (2) calcResultインスタンスを呼び出して結果をresult変数に代入/
        result = answer.calcResult();
        /* (3) 計算結果が少数部分をもつ場合と持たない場合で計算結果の表示方法を変更 */
        /*     持たない場合：整数部分のみ表示 */
        if(Math.round(result) == result){
            System.out.printf("%d",result);
        /     それ以外（持つ場合）：少数点以下２桁まで表示/
        }else
            System.out.printf("%6.2f",result);

        } else
        // エラーメッセージの表示（演算子の種類が適切でない）
        System.out.println("USAGE:\n% java SwitchExample operand1 operator operand2\nOperator is +, -, x, /, or %.\n");

        }

   }
